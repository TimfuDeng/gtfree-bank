package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import cn.gtmap.landsale.core.register.ResourceContainerClient;
import cn.gtmap.landsale.core.register.TransBankAccountClient;
import cn.gtmap.landsale.core.register.TransBankClient;
import cn.gtmap.landsale.core.register.TransBankInterfaceClient;
import cn.gtmap.landsale.core.service.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jiff on 15/5/6.
 */
@Service
public class ClientServiceImpl implements ClientService {
    private static Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransResourceLockService transResourceLockService;

    @Autowired
    TransBankClient transBankService;

    @Autowired
    TransBankAccountClient transBankAccountService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransBankInterfaceClient transBankInterfaceService;

    @Autowired
    OneParamService oneParamService;

    @Autowired
    OneTargetService oneTargetService;

    @Autowired
    ResourceContainerClient resourceContainerClient;


    @Override
    @Transactional(readOnly = true)
    public List<TransResourceOffer> getOfferList(String resourceId,long timeValue){
        Page<TransResourceOffer> resourceOffers=
                transResourceOfferService.getResourceOfferPage(resourceId, new PageRequest(0, 15));
        if (timeValue>0) {
            List<TransResourceOffer> resourceOfferList= Lists.newArrayList();
            for(TransResourceOffer resourceOffer:resourceOffers){
                if (resourceOffer.getOfferTime()>timeValue) {
                    resourceOfferList.add(resourceOffer);
                } else {
                    break;
                }
            }
            return resourceOfferList;
        }else {
            return  resourceOffers.getItems();
        }
    }

    /**
     * 获取地块报价
     *
     * @param resourceId
     * @return
     */
    @Override
    public Page<TransResourceOffer> findTransResourceOffers(Pageable request,String resourceId) {
        return transResourceOfferService.getResourceOfferPage(resourceId, request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceOffer> acceptResourceOffer(String userId, String resourceId, double offerPrice,int offerType) throws Exception {
        //1、添加行级锁
        //2、比较报价数据是否十分合法
        //3、更新价格
        Date cDate = Calendar.getInstance().getTime();
        TransResourceLock offerLock = null;
        TransResourceOffer offer =  new TransResourceOffer();
        try {
            // 地块信息错误
            if (StringUtils.isNotBlank(resourceId)) {
                // 行锁 锁定当前地块报价信息
                System.out.println(System.currentTimeMillis());
                // 当前最新的报价信息
                offerLock = transResourceLockService.getResourceLock4Update(resourceId);
                // 第一次报价
                if (offerLock == null) {
                    TransResourceLock transResourceLock = new TransResourceLock();
                    transResourceLock.setResourceId(resourceId);
                    updateOfferLock(transResourceLock, userId, offerType, cDate, 0);
                    offerLock = transResourceLockService.getResourceLock4Update(resourceId);
                }
                System.out.println(System.currentTimeMillis());
                TransResource transResource = transResourceService.getTransResource(resourceId);
                TransResourceOffer resourceMaxOffer = transResourceOfferService.getMaxOffer(resourceId);
                // 上次最高报价 < 当前报价
                if (offerLock.getCurrentOffer() < offerPrice) {
                    // 地块处于发布期间
                    if (transResource.getResourceEditStatus() == Constants.RESOURCE_EDIT_STATUS_RELEASE) {
                        //如果时间在挂牌期间
                        if (cDate.after(transResource.getGpBeginTime()) && cDate.before(transResource.getGpEndTime())){
                            //挂牌前1个小时不接受报价
                            if ((transResource.getGpEndTime().getTime() - cDate.getTime()) < 1000 * 60 * 60) {
                                return new ResponseMessage<>(false, Constants.OFFER_ERROR_LIMIT);
                            } else {
                                // 挂牌期 已达到最高限价后的报价 不太可能 除非管理员后台 改最高限价
                                if (offerType == Constants.OFFER_XIANJIA) {
                                    log.debug("异常情况,resourceMaxOffer：" + JSON.toJSONString(resourceMaxOffer) + "transResource:" + JSON.toJSONString(transResource));
                                    return new ResponseMessage<>(false, Constants.OFFER_ERROR_TOP);
                                } else {
                                    // 本次报价之前 已存在最高报价 掉线 延时 等等情况
                                    if (checkTopPrice(resourceMaxOffer, transResource)) {
                                        return new ResponseMessage<>(false, Constants.OFFER_ERROR_TOP);
                                    } else {
                                        if (checkOffer(resourceMaxOffer, transResource, offerPrice)) {
                                            System.out.println(System.currentTimeMillis());
                                            offerLock = updateOfferLock(offerLock, userId, offerType, cDate, offerPrice);
                                            System.out.println(System.currentTimeMillis());
                                            log.info("update userId: " + userId + " offerTime: " + offerLock.getCurrentOverTime().getTime() + " offer:" + offerLock.getCurrentOffer());
                                            // 挂牌期 判断 该地块是否存在最高报价 且 当前报价 >= 最高限价
                                            if ((Constants.Whether.YES.equals(transResource.getMaxOfferExist()))
                                                    && (offerPrice >= transResource.getMaxOffer())) {
                                                System.out.println(System.currentTimeMillis());
                                                ResponseMessage<TransResourceOffer> offerResponse = addOffer(resourceId, userId, Constants.OFFER_XIANJIA, cDate, offerPrice);
                                                System.out.println(System.currentTimeMillis());
                                                // 修改地块状态 为最高限价
                                                transResource.setResourceStatus(Constants.RESOURCE_STATUS_MAX_OFFER);
                                                transResourceService.updateTransResource(transResource);
                                                return offerResponse;
                                            } else {
                                                System.out.println(System.currentTimeMillis());
                                                ResponseMessage<TransResourceOffer> offerResponse = addOffer(resourceId, userId, Constants.OFFER_TYPE_GUA_PAI, cDate, offerPrice);
                                                System.out.println(System.currentTimeMillis());
                                                return offerResponse;
                                            }
                                        } else {
                                            return new ResponseMessage<>(false, Constants.OFFER_ERROR_MIN);
                                        }
                                    }
                                }
                            }
                        }
                        //如果时间在竞价期
                        if (cDate.after(transResource.getXsBeginTime()) && transResource.getResourceStatus()==Constants.RESOURCE_STATUS_JING_JIA){
                            // 竞价期 已达到最高限价后的报价 不太可能 除非管理员后台 改最高限价
                            if (offerType == Constants.OFFER_XIANJIA){
                                log.debug("异常情况,resourceMaxOffer：" + JSON.toJSONString(resourceMaxOffer) + "transResource:" + JSON.toJSONString(transResource));
                                return new ResponseMessage<>(false, Constants.OFFER_ERROR_TOP);
                            } else {
                                // 判断是否达到 最高限价 掉线 延时 等等情况
                                if (checkTopPrice(resourceMaxOffer, transResource)) {
                                    return new ResponseMessage<>(false, Constants.OFFER_ERROR_TOP);
                                } else {
                                    // 当前报价是否低于最高价
                                    if (!checkOffer(resourceMaxOffer, transResource, offerPrice)) {
                                        return new ResponseMessage<>(false, Constants.OFFER_ERROR_MIN);
                                        // 当前处于竞价期 上一次最高报价为挂牌期报价 当前时间距离限时开始时间超过4分钟
                                    } else if (resourceMaxOffer!=null && resourceMaxOffer.getOfferType() == Constants.OFFER_TYPE_GUA_PAI && (cDate.getTime() - transResource.getXsBeginTime().getTime()) > 4 * 60 * 1000) {//(cDate.getTime() - transResource.getGpEndTime().getTime()) > 4 * 60 * 1000
                                        return new ResponseMessage<>(false, Constants.OFFER_ERROR_OUT_TIME);
                                    } else {
                                        System.out.println(System.currentTimeMillis());
                                        offerLock = updateOfferLock(offerLock, userId, offerType, cDate, offerPrice);
                                        System.out.println(System.currentTimeMillis());
                                        log.info("update userId: " + userId + " offerTime: " + offerLock.getCurrentOverTime().getTime() + " offer:" + offerLock.getCurrentOffer());
                                        // 判断 该地块是否存在最高报价 且 当前报价 >= 最高限价
                                        if ((Constants.Whether.YES.equals(transResource.getMaxOfferExist()))
                                                && (offerPrice >= transResource.getMaxOffer())) {
                                            System.out.println(System.currentTimeMillis());
                                            ResponseMessage<TransResourceOffer> offerResponse = addOffer(resourceId, userId, Constants.OFFER_XIANJIA, cDate, offerPrice);
                                            System.out.println(System.currentTimeMillis());
                                            // 修改地块状态 为最高限价
                                            transResource.setResourceStatus(Constants.RESOURCE_STATUS_MAX_OFFER);
                                            transResourceService.updateTransResource(transResource);
                                            // 一次报价
                                            if (Constants.MaxOfferChoose.YCBJ.getCode() == transResource.getMaxOfferChoose().getCode()) {
                                                // 通过地块的 regionCode 获取 OneParam 最高限价间隔时间
                                                OneParam oneParam = oneParamService.getOneParamByRegionCode(transResource.getRegionCode());
                                                if (oneParam == null) {
                                                    oneParam = new OneParam();
                                                    oneParam.setWaitTime(Constants.ONE_PARAM_DEFAULT_TIME.WAIT_TIME);
                                                    oneParam.setQueryTime(Constants.ONE_PARAM_DEFAULT_TIME.QUERY_TIME);
                                                    oneParam.setPriceTime(Constants.ONE_PARAM_DEFAULT_TIME.PRICE_TIME);
                                                    oneParam.setRegionCode(transResource.getRegionCode());
                                                    oneParamService.saveOrUpdateOneParam(oneParam);
                                                }
                                                // 等待一次报价信息发布时添加 不再自动设置
                                                // 通过 resourceId 获取 OneTarget
                                                OneTarget oneTarget = oneTargetService.getOneTargetByTransResource(transResource.getResourceId());
                                                // 修改 OneTarget 等待时间 问询时间 报价时间 保存 （限时开始时间+最高限价间隔时间）
                                                oneTarget = getOneTargetValue(oneTarget, transResource, oneParam);
                                                oneTargetService.saveOneTarget(oneTarget);
                                            }
                                            // 监视地块队列中 移除当前地块
                                            resourceContainerClient.remove(resourceId);
                                            return offerResponse;
                                        } else {
                                            System.out.println(System.currentTimeMillis());
                                            ResponseMessage<TransResourceOffer> offerResponse = addOffer(resourceId, userId, Constants.OFFER_JING_JIA, cDate, offerPrice);
                                            System.out.println(System.currentTimeMillis());
                                            return offerResponse;
                                        }
                                    }
                                }
                            }
                        }
                        return new ResponseMessage<>(false, Constants.OFFER_ERROR_CHANGE);
                    } else {
                        return new ResponseMessage<>(false, Constants.OFFER_ERROR_CHANGE);
                    }
                } else {
                    return new ResponseMessage<>(false, Constants.OFFER_ERROR_MIN);
                }
            }else {
                return new ResponseMessage<>(false, Constants.OFFER_ERROR_LACK_RRESOURCE_ID);
            }
        } finally {
            if (offerLock != null) {
                transResourceLockService.save(offerLock);
            }
        }
    }

    private TransResourceLock updateOfferLock(TransResourceLock resourceLock,String userId,int offerType,Date offerTime,double offerPrice){
        resourceLock.setCurrentOffer(offerPrice);
        resourceLock.setUserId(userId);
        resourceLock.setCurrentOverTime(offerTime);
        resourceLock.setCurrentStatus(offerType);
        return transResourceLockService.save(resourceLock);


    }

    private ResponseMessage<TransResourceOffer> addOffer(String resourceId, String userId, int offerType, Date offerTime, double offerPrice)
            throws Exception {
        //检查该用户是否交纳保证金
     /*   TransResourceApply transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
        if (transResourceApply==null || transResourceApply.getApplyStep()!=Constants.STEP_OVER){
            throw new Exception(Constants.OFFER_ERROR_NO_PAY);
        }*/
        TransResourceOffer offer=new TransResourceOffer();
        offer.setUserId(userId);
        offer.setOfferTime(offerTime.getTime());
        offer.setOfferType(offerType);
        offer.setResourceId(resourceId);
        offer.setOfferPrice(offerPrice);
        if (Constants.OFFER_XIANJIA == offerType) {
            offer.setMaxOffer(true);
        }
        return transResourceOfferService.addTransResourceOffer(offer);
    }

    /**
     * 判断报价的合法性
     * @return
     */
    private boolean checkOffer(TransResourceOffer resourceMaxOffer,TransResource transResource,double offerPrice){
        if(resourceMaxOffer==null){
            return compareIntCrease(offerPrice, transResource.getBeginOffer()-transResource.getAddOffer(), transResource.getAddOffer());
        }else{
            return compareIntCrease(offerPrice,resourceMaxOffer.getOfferPrice(),transResource.getAddOffer());
        }
    }

    /**
     * 是否达到最高限价
     * @param resourceMaxOffer
     * @param transResource
     * @return
     */
    private boolean checkTopPrice(TransResourceOffer resourceMaxOffer,TransResource transResource){
        if(resourceMaxOffer!=null){
            if (resourceMaxOffer.getOfferType()==Constants.OFFER_XIANJIA){
                return true;
            } else {
                if (transResource.getMaxOffer() != null && transResource.getMaxOffer() > 0) {
                    if (resourceMaxOffer.getOfferPrice() >= transResource.getMaxOffer()) {
                        return true;
                    }
                    /*if (resourceMaxOffer.getOfferPrice() == transResource.getMaxOffer() ||
                            (resourceMaxOffer.getOfferPrice() + transResource.getAddOffer()) > transResource.getMaxOffer())
                        return true;*/
                }
            }
        }
        return false;
    }

    private boolean checkArea(TransResourceOffer resourceMaxOffer,TransResource transResource,double offerPrice){
        if(resourceMaxOffer.getOfferType()==Constants.OFFER_XIANJIA){
            return compareIntCrease(offerPrice,resourceMaxOffer.getOfferPrice(),transResource.getAddHouse());
        }else{
            return compareIntCrease(offerPrice,transResource.getBeginHouse()-transResource.getAddHouse(),transResource.getAddHouse());
        }
    }
    /**
     * 判断整数倍数
     * @param offerValue
     * @param maxOffer
     * @param addOffer
     * @return
     */
    private boolean compareIntCrease(double offerValue, double maxOffer, double addOffer){
        double offerDiff=sub(offerValue,maxOffer);
        double value=div(offerDiff,addOffer,4);
        int intCrease=(int)value;
        if (intCrease>0 && (offerDiff-intCrease*addOffer)<=0.00001){
            return true;
        }else{
            return false;
        }
    }

    private double div(double d1,double d2,int scale){
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide
                (bd2,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private double sub(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    private double mul(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    private String getElementValue(Document doc,String path){
        Element element=(Element) doc.selectSingleNode(path);
        if (element!=null){
            return element.getTextTrim();
        }else{
            return null;
        }
    }

    private TransBank getBankByRegionCode(String regionCode,String bankCode,String moneyUnit){
        List<TransBank> bankList = transBankService.getBankListByRegion(regionCode);
        for (TransBank bank : bankList){
            if (bank.getBankCode().equals(bankCode) && bank.getMoneyUnit().equals(moneyUnit)){
                return bank;
            }
        }
        return null;
    }


    /**
     * 获取地块一次报价时间
     * @param oneTarget
     * @param transResource
     * @param oneParam
     * @return
     */
    private OneTarget getOneTargetValue(OneTarget oneTarget, TransResource transResource, OneParam oneParam){
        if (oneTarget == null) {
            oneTarget = new OneTarget();
            oneTarget.setTransResourceId(transResource.getResourceId());
        }
        if(null != transResource.getXsBeginTime()){
            // 可能已被管理员设置过时间
            if (oneTarget.getStopDate() == null) {
                oneTarget.setStopDate(transResource.getXsBeginTime());//发布时间（限时开始时间）
            }
            oneTarget.setWaitBeginDate(oneTarget.getStopDate());//等待开始时间（OneTarget stopDate）
            oneTarget.setWaitEndDate(DateUtils.addMinutes(oneTarget.getWaitBeginDate(), oneParam.getWaitTime()));//等待结束时间（等待开始时间+等待时间）
            oneTarget.setQueryBeginDate(oneTarget.getWaitEndDate());//询问开始时间（等待结束时间）
            oneTarget.setQueryEndDate(DateUtils.addMinutes(oneTarget.getQueryBeginDate(), oneParam.getQueryTime()));//询问结束时间（询问开始时间+问询时间）
            oneTarget.setPriceBeginDate(oneTarget.getQueryEndDate());//报价开始时间（询问结束时间）
            oneTarget.setPriceEndDate(DateUtils.addMinutes(oneTarget.getQueryEndDate(),oneParam.getPriceTime()));//报价结束时间（报价开始时间+报价时间）
        }
        oneTarget.setCreateDate(new Date());
        oneTarget.setCreateUserId(SecUtil.getLoginUserId());
        return  oneTarget;
    }

}
