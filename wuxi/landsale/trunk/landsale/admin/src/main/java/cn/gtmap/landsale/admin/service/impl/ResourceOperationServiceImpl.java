package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.landsale.admin.core.TransResourceContainer;
import cn.gtmap.landsale.admin.register.*;
import cn.gtmap.landsale.admin.service.ResourceOperationService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Jibo on 2015/6/19.
 */
@Service
public class ResourceOperationServiceImpl implements ResourceOperationService {
    private static Logger log = LoggerFactory.getLogger(ResourceOperationServiceImpl.class);

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceOfferClient transResourceOfferService;

    @Autowired
    TransResourceApplyClient transResourceApplyService;

    @Autowired
    TransBankAccountClient transBankAccountService;

    @Autowired
    TransResourceMinPriceClient transResourceMinPriceService;

    @Autowired
    TransResourceContainer transResourceContainer;

    @Autowired
    OneParamClient oneParamClient;

    @Autowired
    OneTargetClient oneTargetClient;

   /* ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }*/

    boolean ask = false;

    public void setAsk(boolean ask) {
        this.ask = ask;
    }

    @Override
    @Async
    public void toGG(TransResource transResource) {
//        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("transResourceContainer");
        try {
            // 当前地块状态 改为公告
            if (transResource.getResourceStatus() != Constants.RESOURCE_STATUS_GONG_GAO) {
                transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_GONG_GAO);
            }
        } finally {
            //下一步是挂牌
            transResource.setResourceStatus(Constants.RESOURCE_STATUS_GONG_GAO);
            transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.GP, transResource.getGpBeginTime());
        }
    }

    @Override
    @Async
    public void toGP(TransResource transResource) {
//        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("transResourceContainer");
        try {
            // 当前地块状态 改为挂牌
            if (transResource.getResourceStatus() != Constants.RESOURCE_STATUS_GUA_PAI) {
                transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_GUA_PAI);
            }

        } finally {
            //下一步是挂牌前1小时
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(transResource.getGpEndTime());
            gc.add(Calendar.HOUR, -1);
            transResource.setResourceStatus(Constants.RESOURCE_STATUS_GUA_PAI);
            transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.GPONEHOUR, gc.getTime());
        }
    }

    @Override
    @Async
    public void toGPOneHour(TransResource transResource) {
//        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("transResourceContainer");
        try {
            // 判断是否为最高限价 不是最高限价 当前地块状态 改为挂牌
            if (transResource.getResourceStatus() != Constants.RESOURCE_STATUS_MAX_OFFER) {
                transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_GUA_PAI);
            }

            /*TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
            if(offer!=null){
                // 最高报价的申请人 加入限时竞价
                TransResourceApply transResourceApply =
                        transResourceApplyService.getTransResourceApplyByUserId(offer.getUserId(),transResource.getResourceId());
                if (!transResourceApply.isLimitTimeOffer()){
                    transResourceApply.setLimitTimeOffer(true);
                    transResourceApplyService.saveTransResourceApply(transResourceApply);
                }
            }*/
        } finally {
            //下一步是限时
            transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.XS, transResource.getXsBeginTime());
        }
    }

    @Override
    @Async
    public void toXS(TransResource transResource) {
//        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("transResourceContainer");
        boolean enterXS = true;
        try {
            //判断是否进入限时
            if (ask) {
                List<TransResourceApply> transResourceApplyList =
                        transResourceApplyService.getEnterLimitTransResourceApply(transResource.getResourceId());
                if (transResourceApplyList.size() < 2) {
                    enterXS = false;
                }
            }
        } finally {
            // 报价人数 > 2
            if (enterXS) {
                // 1.判断地块是否达到最高限价 最高限价之后 不再监视地块
                // 2.判断最高限价之后的处理方式 如果是一次报价 判断一次报价的时间
                if (Constants.RESOURCE_STATUS_MAX_OFFER == transResource.getResourceStatus()) {
                    // 一次报价
                    if (Constants.MaxOfferChoose.YCBJ.getCode() == transResource.getMaxOfferChoose().getCode()) {
                        // 通过地块的 regionCode 获取 OneParam 最高限价间隔时间  TODO
                        OneParam oneParam = oneParamClient.getOneParamByRegionCode(transResource.getRegionCode());
                        if (oneParam == null) {
                            oneParam = new OneParam();
                            oneParam.setWaitTime(Constants.ONE_PARAM_DEFAULT_TIME.WAIT_TIME);
                            oneParam.setQueryTime(Constants.ONE_PARAM_DEFAULT_TIME.QUERY_TIME);
                            oneParam.setPriceTime(Constants.ONE_PARAM_DEFAULT_TIME.PRICE_TIME);
                            oneParam.setRegionCode(transResource.getRegionCode());
                            oneParamClient.saveOrUpdateOneParam(oneParam);
                        }
                        // 等待一次报价信息发布时添加 不再自动设置
                        // 通过 resourceId 获取 OneTarget
                        OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(transResource.getResourceId());
                        // 修改 OneTarget 等待时间 问询时间 报价时间 保存
                        oneTarget = getOneTargetValue(oneTarget, transResource, oneParam);
                        oneTargetClient.saveOneTarget(oneTarget);
                    }
                } else {
                    // 当前地块状态 改为限时竞价
                    transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_JING_JIA);
                    // 下一步 4分钟后结束
                    transResource.setResourceStatus(Constants.RESOURCE_STATUS_JING_JIA);
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(transResource.getXsBeginTime());
                    gc.add(Calendar.MINUTE, 4);
                    transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.OVER, gc.getTime());
                }
            } else {
                TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
                if (offer == null) {
                    //流拍
                    beginTransOut(transResource);
                } else {
                    beginTransOver(transResource, offer);
                }
            }
        }
    }

    /**
     * 获取地块一次报价时间
     *
     * @param oneTarget
     * @param transResource
     * @param oneParam
     * @return
     */
    private OneTarget getOneTargetValue(OneTarget oneTarget, TransResource transResource, OneParam oneParam) {
        if (oneTarget == null) {
            oneTarget = new OneTarget();
            oneTarget.setTransResourceId(transResource.getResourceId());
        }
        if (null != transResource.getXsBeginTime()) {
            // 可能已被管理员设置过时间
            if (oneTarget.getStopDate() == null) {
                //发布时间（限时开始时间）
                oneTarget.setStopDate(transResource.getXsBeginTime());
            }
            //等待开始时间（OneTarget stopDate）
            oneTarget.setWaitBeginDate(oneTarget.getStopDate());
            //等待结束时间（等待开始时间+等待时间）
            oneTarget.setWaitEndDate(DateUtils.addMinutes(oneTarget.getWaitBeginDate(), oneParam.getWaitTime()));
            //询问开始时间（等待结束时间）
            oneTarget.setQueryBeginDate(oneTarget.getWaitEndDate());
            //询问结束时间（询问开始时间+问询时间）
            oneTarget.setQueryEndDate(DateUtils.addMinutes(oneTarget.getQueryBeginDate(), oneParam.getQueryTime()));
            //报价开始时间（询问结束时间）
            oneTarget.setPriceBeginDate(oneTarget.getQueryEndDate());
            //报价结束时间（报价开始时间+报价时间）
            oneTarget.setPriceEndDate(DateUtils.addMinutes(oneTarget.getQueryEndDate(), oneParam.getPriceTime()));
        }
        oneTarget.setCreateDate(new Date());
        oneTarget.setCreateUserId(SecUtil.getLoginUserId());
        return oneTarget;
    }

    @Override
    @Async
    public void toOver(TransResource transResource) {
//        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("transResourceContainer");
        Date cDate = Calendar.getInstance().getTime();
        Date enterXS = null;
        try {
            TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
            // 如果在限时竞价
            if (transResource.getResourceStatus() == Constants.RESOURCE_STATUS_JING_JIA) {
                if (offer == null) {
                    // 限时竞价 4分钟内 无人报价 注：挂牌期无人报价
                    if ((cDate.getTime() - transResource.getXsBeginTime().getTime()) > 4 * 60 * 1000) {
                        //流拍
                        beginTransOut(transResource);
                    }
                } else {
                    // 如果最高报价在挂牌期 并且 4分钟内 无人报价 设置成交人为 最高报价人
                    if (offer.getOfferType() == Constants.OFFER_TYPE_GUA_PAI &&
                            (cDate.getTime() - transResource.getXsBeginTime().getTime()) > 4 * 60 * 1000) {
                        beginTransOver(transResource, offer);
                    } else {
                        // 4分钟内无人报价 设置成交 否则 重新开始4分钟
                        if ((cDate.getTime() - offer.getOfferTime()) > 4 * 60 * 1000) {
                            beginTransOver(transResource, offer);
                        } else {
                            enterXS = new Date(offer.getOfferTime());
                        }
                    }
                }
            } else {
                if (offer == null) {
                    //流拍
                    beginTransOut(transResource);
                } else {
                    //成交
                    beginTransOver(transResource, offer);
                }
            }

        } finally {
            // 4分钟内有报价  重新开始计时 截止时间为上次最高报价时间 +4分钟
            if (enterXS != null) {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(enterXS);
                gc.add(Calendar.MINUTE, 4);
                transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.OVER, gc.getTime());
            }
        }

    }

    /**
     * 准备成交
     *
     * @param offer
     */
    private void beginTransOver(TransResource transResource, TransResourceOffer offer) {
        //如果存在底价，判断是否大于底价 小于底价 流拍
        if (transResource.getMinOffer() == 1) {
            if (offer.getOfferPrice() < transResourceMinPriceService.getMinPriceByResourceId(transResource.getResourceId())) {
                beginTransOut(transResource);
                return;
            }
        }
        transResource.setResourceStatus(Constants.RESOURCE_STATUS_CHENG_JIAO);
        transResource.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_OVER);
        //成交
        transResource.setOfferId(offer.getOfferId());
        Calendar cal = Calendar.getInstance();
        // 最高报价在限时竞价期 设置成交时间为 最高报价后4分钟
        if (offer.getOfferType() == Constants.OFFER_JING_JIA) {
            cal.setTimeInMillis(offer.getOfferTime() + 4 * 60 * 1000);
            // 最高报价在挂牌期 设置成交时间为 限时竞价后4分钟
        } else {
            cal.setTimeInMillis(transResource.getXsBeginTime().getTime() + 4 * 60 * 1000);
        }
        //设置成交时间
        transResource.setOverTime(cal.getTime());
        // 设置成交方式
        transResource.setSuccessOfferChoose(Constants.SUCCESS_OFFER_CHOOSE.NORMAL);
        transResourceClient.saveTransResource(transResource);
        closeBankAccount(transResource);  //关闭该资源对应的所有账户
    }

    /**
     * 准备流拍
     */
    private void beginTransOut(TransResource transResource) {
        transResource.setResourceStatus(Constants.RESOURCE_STATUS_LIU_BIAO);
        transResource.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_OVER);
        //流拍
        transResource.setOfferId(null);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(transResource.getXsBeginTime().getTime() + 4 * 60 * 1000);
        //设置时间 为限时开始后 4分钟
        transResource.setOverTime(cal.getTime());
        transResourceClient.saveTransResource(transResource);
        //关闭该资源对应的所有账户
        closeBankAccount(transResource);
    }

    /**
     * 关闭该资源对应的所有账户
     */
    private void closeBankAccount(TransResource transResource) {
        // 结束地块 所有申请人
        List<TransResourceApply> transResourceApplyList =
                transResourceApplyService.getTransResourceApplyByResourceId(transResource.getResourceId());
        for (TransResourceApply transResourceApply : transResourceApplyList) {
            // 关闭申请人 银行子账号
            TransBankAccount transBankAccount =
                    transBankAccountService.getTransBankAccountByApplyId(transResourceApply.getApplyId());
            if (transBankAccount != null) {
                transBankAccount.setClose(true);
                transBankAccountService.saveTransBankAccount(transBankAccount);
            }
        }
    }


}
