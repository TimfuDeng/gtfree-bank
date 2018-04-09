package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.datasource.DataSourceHolder;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.util.ClientSocketUtil;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jiff on 15/5/6.
 */
public class ClientServiceImpl implements ClientService {
    private static Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    TransResourceService transResourceService;

    TransResourceOfferService transResourceOfferService;

    TransBankService transBankService;

    TransBankAccountService transBankAccountService;

    TransResourceApplyService transResourceApplyService;

    TransBankInterfaceService transBankInterfaceService;

    TransCaUserService transCaUserService;

    public void setTransBankInterfaceService(TransBankInterfaceService transBankInterfaceService) {
        this.transBankInterfaceService = transBankInterfaceService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public void setTransBankService(TransBankService transBankService) {
        this.transBankService = transBankService;
    }

    public void setTransCaUserService(TransCaUserService transCaUserService) {
        this.transCaUserService = transCaUserService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceOffer> getOfferList(String resourceId,long timeValue){
        Page<TransResourceOffer> resourceOffers=
                transResourceOfferService.getResourceOfferPage(resourceId, new PageRequest(0, 15));
        if (timeValue>0) {
            List<TransResourceOffer> resourceOfferList=Lists.newArrayList();
            for(TransResourceOffer resourceOffer:resourceOffers){
                if (resourceOffer.getOfferTime()>timeValue)
                    resourceOfferList.add(resourceOffer);
                else
                    break;
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
    @Transactional
    public TransResourceOffer acceptResourceOffer(String userId, String resourceId, double offerPrice,int offerType) throws Exception {
        Date cDate= Calendar.getInstance().getTime();
        //加同步锁，避免多线程错误
            TransResource transResource= transResourceService.getTransResource(resourceId);
            TransResourceOffer resourceMaxOffer= transResourceOfferService.getMaxOffer(resourceId);
            if (transResource.getResourceEditStatus()== Constants.ResourceEditStatusRelease) {
                //如果时间在挂牌期间
                if (cDate.after(transResource.getGpBeginTime()) && cDate.before(transResource.getGpEndTime())){

                    if ((transResource.getGpEndTime().getTime() - cDate.getTime()) < 1000 * 60 * 60) {  //挂牌前1个小时不接受报价
                        throw new Exception(Constants.OfferErrorLimit);
                    } else {
                        if (offerType==2){
                            //保障房面积
                            if(checkTopPrice(resourceMaxOffer, transResource)){
                                if (checkArea(resourceMaxOffer, transResource, offerPrice)) {
                                    return addOffer(resourceId, userId, Constants.OfferXianjia, cDate, offerPrice);
                                } else {
                                    throw new Exception(Constants.OfferErrorMin);
                                }
                            }else{
                                throw new Exception(Constants.OfferErrorNoTop);
                            }
                        }else {
                            if(checkTopPrice(resourceMaxOffer, transResource)) {
                                throw new Exception(Constants.OfferErrorTop);
                            }else{
                                if (checkOffer(resourceMaxOffer, transResource, offerPrice)) {
                                    return addOffer(resourceId, userId, Constants.OfferTypeGuaPai, cDate, offerPrice);
                                } else {
                                    throw new Exception(Constants.OfferErrorMin);
                                }
                            }
                        }
                    }
                }
                //如果时间在竞价期
                if (cDate.after(transResource.getGpEndTime()) && transResource.getResourceStatus()==Constants.ResourceStatusJingJia){
                    if (offerType==2){
                        //保障房面积
                        if(checkTopPrice(resourceMaxOffer, transResource)){
                            if (checkArea(resourceMaxOffer, transResource, offerPrice)) {
                                return addOffer(resourceId, userId, Constants.OfferXianjia, cDate, offerPrice);
                            } else {
                                throw new Exception(Constants.OfferErrorMin);
                            }
                        }else{
                            throw new Exception(Constants.OfferErrorNoTop);
                        }
                    }else {
                        if(checkTopPrice(resourceMaxOffer, transResource)) {
                            throw new Exception(Constants.OfferErrorTop);
                        }else{
                            if (!checkOffer(resourceMaxOffer, transResource, offerPrice)) {
                                throw new Exception(Constants.OfferErrorMin);
                            } /*else if (resourceMaxOffer!=null && resourceMaxOffer.getOfferType() == Constants.OfferJingJia && (cDate.getTime() - resourceMaxOffer.getOfferTime()) > 4 * 60 * 1000) {
                                throw new Exception(Constants.OfferErrorOutTime);
                            } else if (resourceMaxOffer!=null && resourceMaxOffer.getOfferType() == Constants.OfferTypeGuaPai && (cDate.getTime() - transResource.getGpEndTime().getTime()) > 4 * 60 * 1000) {
                                throw new Exception(Constants.OfferErrorOutTime);
                            } */else {
                                return addOffer(resourceId, userId, Constants.OfferJingJia, cDate, offerPrice);
                            }
                        }
                    }
                }
                throw new Exception(Constants.OfferErrorChange);
            }else{
                throw new Exception(Constants.OfferErrorChange);
            }
    }

    private TransResourceOffer addOffer(String resourceId,String userId,int offerType,Date offerTime,double offerPrice)
            throws Exception{
        //检查该用户是否交纳保证金
        TransResourceApply transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
        if (transResourceApply==null || transResourceApply.getApplyStep()!=Constants.StepOver){
            throw new Exception(Constants.OfferErrorNoPay);
        }
        TransResourceOffer offer=new TransResourceOffer();
        offer.setUserId(userId);
        offer.setOfferTime(offerTime.getTime());
        offer.setOfferType(offerType);
        offer.setResourceId(resourceId);
        offer.setOfferPrice(offerPrice);
        
        //报名（报名且报价）人数加1
        /*List<TransResourceOffer> offerList = transResourceOfferService.getOfferListByResource(resourceId);
        TransResource transResource = transResourceService.getTransResource(resourceId);
        HashSet<String> offerSet = new HashSet<String>();
        if(offerList.size() == 0){
        	transResource.setEnrolledOfferNum(1);
        }else{
        	offerSet.add(userId);
        	for(int i = 0; i < offerList.size(); i++){
        		offerSet.add(offerList.get(i).getUserId());
        	}
        	transResource.setEnrolledOfferNum(offerSet.size());
        }
        transResourceService.saveTransResource(transResource);*/
        
        return transResourceOfferService.addTransResourceOffer(offer);
    }

    /**
     * 判断报价的合法性
     * @return
     */
    private boolean checkOffer(TransResourceOffer resourceMaxOffer,TransResource transResource,double offerPrice){
        if(resourceMaxOffer==null){
            return CompareIntCrease(offerPrice,transResource.getBeginOffer()-transResource.getAddOffer(),transResource.getAddOffer());
        }else{
            return CompareIntCrease(offerPrice,resourceMaxOffer.getOfferPrice(),transResource.getAddOffer());
        }
    }

    private boolean checkTopPrice(TransResourceOffer resourceMaxOffer,TransResource transResource){
        if(resourceMaxOffer!=null){
            if (resourceMaxOffer.getOfferType()==2){
                return true;
            }else{
                if (transResource.getMaxOffer()!=null && transResource.getMaxOffer()>0) {
                    if (resourceMaxOffer.getOfferPrice() == transResource.getMaxOffer() ||
                            (resourceMaxOffer.getOfferPrice() + transResource.getAddOffer()) > transResource.getMaxOffer())
                        return true;
                }
            }
        }
        return false;
    }

    private boolean checkArea(TransResourceOffer resourceMaxOffer,TransResource transResource,double offerPrice){
        if(resourceMaxOffer.getOfferType()==2){
            return CompareIntCrease(offerPrice,resourceMaxOffer.getOfferPrice(),transResource.getAddHouse());
        }else{
            return CompareIntCrease(offerPrice,transResource.getBeginHouse()-transResource.getAddHouse(),transResource.getAddHouse());
        }
    }
    /**
     * 判断整数倍数
     * @param offerValue
     * @param maxOffer
     * @param addOffer
     * @return
     */
    private boolean CompareIntCrease (double offerValue,double maxOffer,double addOffer){
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
                (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
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

    @Override
    @Transactional
    public TransBankAccount applyBankAccount(String applyId){
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        TransResource transResource= transResourceService.getTransResource(transResourceApply.getResourceId());
        String moneyUnit=
                StringUtils.isNotBlank(transResourceApply.getMoneyUnit())?transResourceApply.getMoneyUnit():Constants.MoneyCNY;
        TransBank transBank=getBankByRegionCode(transResource.getRegionCode(), transResourceApply.getBankCode(), moneyUnit);
        try {
            String host = transBank.getInterfaceIp();
            int port =Integer.parseInt(transBank.getInterfacePort());
            TransBankAccount transBankAccount=
                    transBankAccountService.createOrGetTransBankAccount(applyId);
            String strBuffer=transBankInterfaceService.sendBankApplyAccount(transResourceApply);
            //向银行发送socket请求
            ClientSocketUtil clientSocketUtil = new ClientSocketUtil(host, port);
            log.debug("send bank:" + strBuffer);
            clientSocketUtil.send(strBuffer);
            try {
                String returnXml = clientSocketUtil.recieve();
                log.debug("get data from bank:" + returnXml);
                //得到结果
                return (TransBankAccount)transBankInterfaceService.receiveBankBack(returnXml);
            } catch (Exception ex) {
                log.error(ex.getMessage(),ex);
            }finally {
                clientSocketUtil.close();
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
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
        List<TransBank> bankList=transBankService.getBankListByRegion(regionCode);
        for(TransBank bank:bankList){
            if (bank.getBankCode().equals(bankCode) && bank.getMoneyUnit().equals(moneyUnit)){
                return bank;
            }
        }
        return null;
    }

    /**
     * @作者 王建明
     * @创建日期 2015-10-27
     * @创建时间 15:45
     * @描述 —— 将此方法封装在这里的原因是动态切换数据源声明必须要在紧邻的service操作前声明
     */
    @Override
    public TransCaUser getTransCaUserByCAThumbprint(String thumbprint){
        DataSourceHolder.setDataSourceType(TransCaUserService.USER_VALIDATE_DATASOURCE);
        return transCaUserService.getTransCaUserByCAThumbprint(thumbprint);
    }
}
