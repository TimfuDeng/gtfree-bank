package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/4.
 */
@Deprecated
public class TransResourceTask extends Thread {
    private static Logger log = LoggerFactory.getLogger(TransResourceTask.class);

    TransResourceService transResourceService;

    TransResourceOfferService transResourceOfferService;

    TransResourceApplyService transResourceApplyService;

    TransBankAccountService transBankAccountService;

    TransCrggService transCrggService;

    TransResourceMinPriceService transResourceMinPriceService;

    long IntervalTime=1000;  //默认一秒

    TransResource transResource;

    boolean stop=false;

    final  static long _OutTime=1000*60*4;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public TransResourceTask(TransResource transResource,
                             TransResourceService transResourceService,
                             TransResourceOfferService transResourceOfferService,
                             TransResourceApplyService transResourceApplyService,
                             TransBankAccountService transBankAccountService,
                             TransCrggService transCrggService,
                             TransResourceMinPriceService transResourceMinPriceService){
        this.transResource=transResource;
        this.transResourceService=transResourceService;
        this.transResourceOfferService=transResourceOfferService;
        this.transResourceApplyService=transResourceApplyService;
        this.transBankAccountService=transBankAccountService;
        this.transCrggService=transCrggService;
        this.transResourceMinPriceService=transResourceMinPriceService;
    }

    @Override
    public void run(){
        try {
            SimpleDateFormat df=new SimpleDateFormat("MM-dd hh:mm:ss");
            while(!Thread.currentThread().isInterrupted() && !stop) {
                Thread.currentThread().setName(transResource.getResourceId());
                log.debug("----正在交易:{}---" +df.format(Calendar.getInstance().getTime()) , transResource.getResourceId());
                checkStatus();
                Thread.currentThread().sleep(IntervalTime);
            }
        } catch (InterruptedException e) {

        }
        log.debug("----结束交易:{}---" , transResource.getResourceId());
    }

    public void checkStatus(){
        IntervalTime=1000;
        Date cDate= Calendar.getInstance().getTime();
        //如果不在公告期内，状态就不在 正在公告
        if (StringUtils.isNotBlank(transResource.getGgId())){
            TransCrgg transCrgg= transCrggService.getTransCrgg(transResource.getGgId());
            if (transCrgg!=null && cDate.before(transCrgg.getGgBeginTime())){
                if (transResource.getResourceStatus()!=0)
                    transResourceService.saveTransResourceStatus(transResource, 0);
                return;
            }
        }


        if (cDate.before(transResource.getGpBeginTime())){
            //挂牌前
            IntervalTime=   transResource.getGpBeginTime().getTime()- cDate.getTime()-1000;
            //设置状态为公告中
            if (transResource.getResourceStatus()!=Constants.ResourceStatusGongGao)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGongGao);
        }else if(cDate.before(transResource.getGpEndTime())){
            //设置状态为挂牌中
            if (transResource.getResourceStatus() != Constants.ResourceStatusGuaPai)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGuaPai);
            if ((transResource.getGpEndTime().getTime()-cDate.getTime())< 60 * 60 * 1000) {
                //挂牌前1个小时内
                IntervalTime = transResource.getGpEndTime().getTime() - cDate.getTime() - 1000;
                TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
                if(offer!=null){
                    TransResourceApply transResourceApply=
                            transResourceApplyService.getTransResourceApplyByUserId(offer.getUserId(),transResource.getResourceId());
                    if (!transResourceApply.isLimitTimeOffer()){
                        transResourceApply.setLimitTimeOffer(true);
                        transResourceApplyService.saveTransResourceApply(transResourceApply);
                    }
                }
            }else{
                IntervalTime = transResource.getGpEndTime().getTime() - cDate.getTime() - 1000- 60 * 60 * 1000;
            }
        }else{
            //判断是否有多人参与限时
//            List<TransResourceApply> transResourceApplyList=
//                    transResourceApplyService.getEnterLimitTransResourceApply(transResource.getResourceId());
//            if (transResourceApplyList.size()>1) {
                //限时竞价中
                if (transResource.getResourceStatus() != Constants.ResourceStatusJingJia)
                    transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusJingJia);
                //判断是否成交
                TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
                if (offer == null) {
                    if ((cDate.getTime() - transResource.getGpEndTime().getTime()) > 4 * 60 * 1000) {
                        beginTransOut();
                        IntervalTime = 0;
                        stop = true;
                    } else {
                        IntervalTime = (transResource.getGpEndTime().getTime() + _OutTime) - cDate.getTime() - 1000;
                    }
                } else if (offer.getOfferType() == Constants.OfferTypeGuaPai) {
                    if ((cDate.getTime() - transResource.getGpEndTime().getTime()) > 4 * 60 * 1000) {
                        beginTransOver(offer);
                        IntervalTime = 0;
                        stop = true;
                    } else {
                        IntervalTime = (transResource.getGpEndTime().getTime() + _OutTime) - cDate.getTime() - 1000;
                    }
                } else if (offer.getOfferType() == Constants.OfferJingJia) {
                    if ((cDate.getTime() - offer.getOfferTime()) > 4 * 60 * 1000) {
                        beginTransOver(offer);
                        IntervalTime = 0;
                        stop = true;
                    } else {
                        IntervalTime = (offer.getOfferTime() + _OutTime) - cDate.getTime() - 1000;
                    }
                }
//            }else{
//                //少于1个人
//                TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
//                if (offer == null) {
//                    beginTransOut();
//                }else{
//                    beginTransOver(offer);
//                }
//            }
        }
        if (IntervalTime<0) IntervalTime=200;
    }

    /**
     * 准备成交
     * @param offer
     */
    private void beginTransOver(TransResourceOffer offer){
        //如果纯在底价，判断是否大于底价
        if (transResource.isMinOffer()){
            if (offer.getOfferPrice()< transResourceMinPriceService.getMinPriceByResourceId(transResource.getResourceId())){
                beginTransOut();
                return;
            }
        }
        transResource.setResourceStatus(Constants.ResourceStatusChengJiao);
        transResource.setResourceEditStatus(Constants.ResourceEditStatusOver);
        transResource.setOfferId(offer.getOfferId());    //成交
        Calendar cal=Calendar.getInstance();
        if(offer.getOfferType()==Constants.OfferJingJia){
            cal.setTimeInMillis(offer.getOfferTime()+4*60*1000);
        }else{
            cal.setTimeInMillis(transResource.getGpEndTime().getTime()+4*60*1000);
        }
        transResource.setOverTime(cal.getTime());   //设置成交时间
        transResourceService.saveTransResource(transResource);
        closeBankAccount();  //关闭该资源对应的所有账户
    }

    /**
     * 准备流拍
     */
    private void beginTransOut(){
        transResource.setResourceStatus(Constants.ResourceStatusLiuBiao);
        transResource.setResourceEditStatus(Constants.ResourceEditStatusOver);
        transResource.setOfferId(null);    //流拍
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(transResource.getGpEndTime().getTime()+4*60*1000);
        transResource.setOverTime(cal.getTime());   //设置时间
        transResourceService.saveTransResource(transResource);
        closeBankAccount();  //关闭该资源对应的所有账户
    }

    /**
     * 关闭该资源对应的所有账户
     */
    private void closeBankAccount(){
        List<TransResourceApply> transResourceApplyList=
                transResourceApplyService.getTransResourceApplyByResourceId(transResource.getResourceId());
        for(TransResourceApply transResourceApply:transResourceApplyList){
            TransBankAccount transBankAccount=
                    transBankAccountService.getTransBankAccountByApplyId(transResourceApply.getApplyId());
            if(transBankAccount!=null){
                transBankAccount.setClose(true);
                transBankAccountService.saveTransBankAccount(transBankAccount);
            }
        }
    }
}



