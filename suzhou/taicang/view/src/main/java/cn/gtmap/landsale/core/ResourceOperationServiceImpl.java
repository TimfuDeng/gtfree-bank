package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.ResourceOperationService;
import cn.gtmap.landsale.core.TransResourceContainer;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
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
        }
    }

    @Override
    @Async
    public void toGPOneHour(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        try{
            if (transResource.getResourceStatus()!= Constants.ResourceStatusGuaPai)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGuaPai);
            TransResourceOffer offer = transResourceOfferService.getMaxOffer(transResource.getResourceId());
            if(offer!=null){
                TransResourceApply transResourceApply=
                        transResourceApplyService.getTransResourceApplyByUserId(offer.getUserId(),transResource.getResourceId());
                if (!transResourceApply.isLimitTimeOffer()){
                    transResourceApply.setLimitTimeOffer(true);
                    transResourceApplyService.saveTransResourceApply(transResourceApply);
                }
            }
        }finally {
            //下一步是限时
            transResourceContainer.addResourceStatus(transResource,Constants.ResourceOperateStep.XS,transResource.getGpEndTime());
        }
    }

    @Override
    @Async
    public void toXS(TransResource transResource) {
        TransResourceContainer transResourceContainer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
        boolean enterXS=true;
        try {
            //判断是否进入限时
            if (ask) {
                List<TransResourceApply> transResourceApplyList =
                        transResourceApplyService.getEnterLimitTransResourceApply(transResource.getResourceId());
                if (transResourceApplyList.size() <2) {
                    enterXS=false;
                }
            }
            //判断只有一人报价，不进入限时竞价阶段
            if(isOneUser(transResource.getResourceId())){
            	enterXS=false;
            }
        }finally {
            if(enterXS) {
                if (transResource.getResourceStatus()!= Constants.ResourceStatusJingJia)
                    transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusJingJia);
                transResource.setResourceStatus(Constants.ResourceStatusJingJia);

                //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                GregorianCalendar gc=new GregorianCalendar();
                gc.setTime(transResource.getGpEndTime());
                //System.out.println("toXS挂牌截止===>" + df.format(gc.getTime()));
                if(transResource.getCountDownSec() == null || transResource.getCountDownSec() == 240){
                    gc.add(Calendar.MINUTE, 4);
                } else {
                    gc.add(Calendar.SECOND, transResource.getCountDownSec());
                }
                //System.out.println("toXS彻底截止===>" + df.format(gc.getTime()));
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
                if (offer==null){
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
                if (offer==null){
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

                if(transResource.getCountDownSec() != null && transResource.getCountDownSec() != 240){
                    transResource.setCountDownSec(240);
                    transResourceService.saveTransResource(transResource);
                }
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
            cal.setTimeInMillis(transResource.getGpEndTime().getTime()+4*60*1000);
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
    
    /**
     * 判断是否只有一人报价
     */
    public boolean isOneUser(String resourceid){
        List<TransResourceOffer> transResourceOfferList= transResourceOfferService.getOfferListByResource(resourceid);
        HashSet<String> userSet = new HashSet<String>();
        for(Iterator<TransResourceOffer> ite = transResourceOfferList.iterator(); ite.hasNext();){
        	TransResourceOffer transResourceOffer = ite.next();
        	userSet.add(transResourceOffer.getUserId());
        }
        if(userSet.size() < 2){
        	return true;
        }
		return false;
    }
}
