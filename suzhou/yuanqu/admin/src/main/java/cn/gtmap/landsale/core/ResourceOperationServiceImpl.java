package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.ResourceOperationService;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Jibo on 2015/6/19.
 */
public class ResourceOperationServiceImpl implements ResourceOperationService,ApplicationContextAware {
    private static Logger log = LoggerFactory.getLogger(ResourceOperationServiceImpl.class);
    TransResourceService transResourceService;

    TransResourceOfferService transResourceOfferService;

    TransResourceApplyService transResourceApplyService;

    TransBankAccountService transBankAccountService;

    TransResourceMinPriceService transResourceMinPriceService;

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }


    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setTransResourceMinPriceService(TransResourceMinPriceService transResourceMinPriceService) {
        this.transResourceMinPriceService = transResourceMinPriceService;
    }

    boolean ask=false;

    public void setAsk(boolean ask) {
        this.ask = ask;
    }

    @Override
    @Async
    public void toGG(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        try{
            if (transResource.getResourceStatus()!= Constants.ResourceStatusGongGao)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGongGao);
        }finally {
            //下一步是挂牌
            transResource.setResourceStatus(Constants.ResourceStatusGongGao);
            transResourceContainer.addResourceStatus(transResource,Constants.ResourceOperateStep.GP,transResource.getGpBeginTime());
        }
    }

    @Override
    @Async
    public void toGP(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        try{
            if (transResource.getResourceStatus()!= Constants.ResourceStatusGuaPai)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGuaPai);
        } finally {
            //下一步是挂牌前1小时
            GregorianCalendar gc=new GregorianCalendar();
            gc.setTime(transResource.getGpEndTime());
            gc.add(Calendar.HOUR,-1);
            transResource.setResourceStatus(Constants.ResourceStatusGuaPai);
            transResourceContainer.addResourceStatus(transResource,Constants.ResourceOperateStep.GPONEHOUR,gc.getTime());
            //进入限时竞价到挂牌一小时之前这个时间内段
            /*GregorianCalendar gc=new GregorianCalendar();
            transResource.setResourceStatus(Constants.ResourceStatusGuaPai);
            transResourceContainer.addResourceStatus(transResource,Constants.ResourceOperateStep.GPONEHOUR,transResource.getXsBeginTime());*/
        }
    }

    @Override
    @Async
    public void toGPOneHour(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        try{
            if (transResource.getResourceStatus()!= Constants.ResourceStatusGuaPai)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGuaPai);
            //2016-10-11 自由报价不进行询问，所有缴纳保证金的人都进入限时竞价
            /*//自由报价不进行询问，所有报价人都进入限时竞价
            List<String> userIdList=transResourceOfferService.getUserIdListByResource2Alone(transResource.getResourceId());
            for(String userId:userIdList){
                TransResourceApply transResourceApply=
                        transResourceApplyService.getTransResourceApplyByUserId(userId,transResource.getResourceId());
                if (!transResourceApply.isLimitTimeOffer()){
                    transResourceApply.setLimitTimeOffer(true);
                    transResourceApply.setLimitTimeOfferConfirmDate(Calendar.getInstance().getTime());
                    transResourceApplyService.saveTransResourceApply(transResourceApply);
                }
            }*/
          /* TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
            //自由报价阶段最高价自动进入限时竞价
            if(offer!=null){
                TransResourceApply transResourceApply=
                        transResourceApplyService.getTransResourceApplyByUserId(offer.getUserId(),transResource.getResourceId());
                if (!transResourceApply.isLimitTimeOffer()){
                    transResourceApplyService.enterLimitTransResourceApply(transResourceApply.getApplyId());
                }
            }*/
        }finally {
            //下一步是限时
            //transResourceContainer.addResourceStatus(transResource,Constants.ResourceOperateStep.XS,transResource.getGpEndTime());
            transResourceContainer.addResourceStatus(transResource,Constants.ResourceOperateStep.XS,transResource.getXsBeginTime());
        }
    }

    @Override
    @Async
    public void toXS(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        boolean enterXS=false;
        try {
            //判断是否进入限时
//            if (ask) {
//                List<TransResourceApply> transResourceApplyList =
//                        transResourceApplyService.getEnterLimitTransResourceApply(transResource.getResourceId());
//                if (transResourceApplyList.size() <2) {
//                    enterXS=false;
//                }
//            }
            List<TransResourceApply> transResourceApplyList =
                    transResourceApplyService.getEnterLimitTransResourceApply(transResource.getResourceId());

            if (transResourceApplyList.size() >=1) {
                enterXS=true;
            }
        }finally {
            if(enterXS) {
                if (transResource.getResourceStatus()!= Constants.ResourceStatusJingJia)
                    transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusJingJia);
                transResource.setResourceStatus(Constants.ResourceStatusJingJia);
                GregorianCalendar gc=new GregorianCalendar();
                gc.setTime(transResource.getXsBeginTime());
                gc.add(Calendar.MINUTE,4);
                transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.OVER, gc.getTime());
            }else{
                TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
                if (offer==null){
                    //流拍
                    beginTransOut(transResource);
                }else{
                    beginTransOver(transResource, offer);
                }
            }
        }
    }

    @Override
    @Async
    public void toOver(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        Date cDate= Calendar.getInstance().getTime();
        Date enterXS=null;
        try{
            TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
            if(transResource.getResourceStatus()==Constants.ResourceStatusJingJia){
                if (null==offer){
                    if ((cDate.getTime() - transResource.getGpEndTime().getTime()) > 4 * 60 * 1000) {
                        //流拍
                        beginTransOut(transResource);
                    }
                }else{
                    if(offer.getOfferType()==Constants.OfferTypeGuaPai &&
                            (cDate.getTime() - transResource.getGpEndTime().getTime()) > 4 * 60 * 1000){
                        beginTransOver(transResource, offer);
                    }else {
                        if ((cDate.getTime() - offer.getOfferTime()) > 4 * 60 * 1000) {
                            beginTransOver(transResource, offer);
                        } else {
                            enterXS = new Date(offer.getOfferTime());
                        }
                    }
                }
            }else{
                if (null==offer){
                    //流拍
                    beginTransOut(transResource);
                }else{
                    //成交
                    beginTransOver(transResource,offer);
                }
            }

        }finally {
            if (enterXS!=null) {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(enterXS);
                gc.add(Calendar.MINUTE, 4);
                transResourceContainer.addResourceStatus(transResource, Constants.ResourceOperateStep.OVER, gc.getTime());
            }
        }

    }
    /**
     * 准备成交
     * @param offer
     */
    private void beginTransOver(TransResource transResource ,TransResourceOffer offer){
        //如果纯在底价，判断是否大于底价
        if (transResource.isMinOffer()){
            if (offer.getOfferPrice()< transResourceMinPriceService.getMinPriceByResourceId(transResource.getResourceId())){
                beginTransOut(transResource);
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
            cal.setTimeInMillis(transResource.getXsBeginTime().getTime()+4*60*1000);
        }
        transResource.setOverTime(cal.getTime());   //设置成交时间
        transResourceService.saveTransResource(transResource);
        closeBankAccount(transResource);  //关闭该资源对应的所有账户
    }

    /**
     * 准备流拍
     */
    private void beginTransOut(TransResource transResource ){
        transResource.setResourceStatus(Constants.ResourceStatusLiuBiao);
        transResource.setResourceEditStatus(Constants.ResourceEditStatusOver);
        transResource.setOfferId(null);    //流拍
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(transResource.getGpEndTime().getTime()+4*60*1000);
        transResource.setOverTime(cal.getTime());   //设置时间
        transResourceService.saveTransResource(transResource);
        closeBankAccount(transResource);  //关闭该资源对应的所有账户
    }

    /**
     * 关闭该资源对应的所有账户
     */
    private void closeBankAccount(TransResource transResource){
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
