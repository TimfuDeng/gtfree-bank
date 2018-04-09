package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.SmsMessageService;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Jibo on 2015/6/15.
 */
@Aspect
public class SmsMessageAspect {
    private static Logger log = LoggerFactory.getLogger(SmsMessageAspect.class);
    SimpleDateFormat df=new SimpleDateFormat("MM月dd日HH时mm分");
    @Autowired
    SmsMessageService smsMessageService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;

    @Autowired
    TransUserService transUserService;

    @Autowired
    TransBankService transBankService;

    @Autowired
    TransBankAccountService transBankAccountService;

    @Autowired
    TransResourceOfferService transResourceOfferService;
    /**
     * 报名成功,生成银行帐号
     * @param transBankAccount
     */
    @AfterReturning(pointcut="execution(* cn.gtmap.landsale.admin.service.impl.ClientServiceImpl.applyBankAccount(..))",returning="transBankAccount")
    public void applySuccess(JoinPoint jp,TransBankAccount transBankAccount){
        if (transBankAccount!=null) {
            String applyId = transBankAccount.getApplyId();
            TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApply(applyId);
            TransResource resource = transResourceService.getTransResource(transResourceApply.getResourceId());
            List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoService.getTransUserApplyInfoByUser(transResourceApply.getUserId());
            if (transUserApplyInfoList.size() > 0) {
//                TransBank transBank=getBankByRegionCode(resource.getRegionCode(), transResourceApply.getBankCode(), transResourceApply.getMoneyUnit());
                String msg = MessageFormat.format("您已成功报名地块{0}，请务必在{1}之前，交纳保证金{2}万元！",
                        resource.getResourceCode(), df.format(resource.getBzjEndTime()), resource.getFixedOffer()
                );
                sendMessage(msg, transUserApplyInfoList.get(0).getContactTelephone());
            }
        }
    }

    private TransBank getBankByRegionCode(String regionCode,String bankCode,String moneyUnit){
        String mu= moneyUnit;
        if (StringUtils.isBlank(moneyUnit))   mu="CNY";
        List<TransBank> bankList=transBankService.getBankListByRegion(regionCode);
        for(TransBank bank:bankList){
            if (bank.getBankCode().equals(bankCode) && bank.getMoneyUnit().equals(mu)){
                return bank;
            }
        }
        return null;
    }

    /**
     * 保证金到账提醒
     * @param jp
     */
    @AfterReturning(pointcut="execution(* cn.gtmap.landsale.admin.service.impl.TransBankPayServiceImpl.saveTransBankPay(..))",returning="transBankPay")
    public void bankMoney(JoinPoint jp,TransBankPay transBankPay){
        TransBankAccount transBankAccount=
                transBankAccountService.getTransBankAccount(transBankPay.getAccountId());
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(transBankAccount.getApplyId());
        TransResource resource= transResourceService.getTransResource(transResourceApply.getResourceId());
        List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoService.getTransUserApplyInfoByUser(transResourceApply.getUserId());
        if(transUserApplyInfoList.size()>0) {
            String msg = MessageFormat.format("{0}收到您转账保证金{1}万元，转账人：{2}！本次竞买共需{3}万元，截止时间：{4}!",
                    df.format(transBankPay.getPayTime()), transBankPay.getAmount(),transBankPay.getPayName(),resource.getFixedOffer(),
                    df.format(resource.getBzjEndTime())
            );
            sendMessage(msg, transUserApplyInfoList.get(0).getContactTelephone());
        }
    }

    /**
     * 竞拍成功
     * @param jp
     */
    @AfterReturning(pointcut="execution(* cn.gtmap.landsale.admin.service.impl.TransResourceServiceImpl.saveTransResource(..))",returning="resource")
    public void resourceOver(JoinPoint jp,TransResource resource){
        String msg="";
        String msgUser="";
        String userId="";
        if(resource.getResourceEditStatus()== Constants.ResourceEditStatusStop){
            msg=MessageFormat.format("地块编号为{0}的网上竞买已终止，交易公告另行通知，请关注网上出让系统公告！",resource.getResourceCode());
        }else if(resource.getResourceEditStatus()== Constants.ResourceEditStatusOver){
            if(resource.getResourceStatus()== Constants.ResourceStatusLiuBiao){
                msg=MessageFormat.format("地块编号为{0}的网上竞买已流拍，感谢您的参与！",resource.getResourceCode());
            }else if(resource.getResourceStatus()== Constants.ResourceStatusChengJiao){
                String money="";
                TransResourceOffer maxOffer= transResourceOfferService.getTransResourceOffer(resource.getOfferId());
                userId=maxOffer.getUserId();
                if(maxOffer.getOfferType()==Constants.OfferXianjia){
                    TransResourceOffer maxPriceOffer= transResourceOfferService.getMaxOfferFormPrice(maxOffer.getResourceId());
                    money=maxPriceOffer.getOfferPrice() + "万元，公租房：" + +maxOffer.getOfferPrice() + "平方米";
                }else{
                    money=maxOffer.getOfferPrice() + "万元";
                }
                msg=MessageFormat.format("地块编号为{0}的网上竞买已成交，成交单位：{1}，成交金额{2}：感谢您的参与！",resource.getResourceCode(),
                        transUserService.getTransUser(maxOffer.getUserId()).getViewName(),money);
                msgUser=MessageFormat.format("恭喜您中标编号为{0}地块，成交金额{2}：，请登录网上出让系统>我的交易>打印成交通知书，感谢您的参与！",
                        resource.getResourceCode(),transUserService.getTransUser(maxOffer.getUserId()).getViewName(),money);
            }
        }

        List<TransResourceApply>  transResourceApplyList=
                transResourceApplyService.getTransResourceApplyByResourceId(resource.getResourceId());
        for(TransResourceApply transResourceApply:transResourceApplyList) {
            if (transResourceApply.getApplyStep()==Constants.StepOver) {   //已交纳保证金的
                List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoService.getTransUserApplyInfoByUser(transResourceApply.getUserId());
                if (transUserApplyInfoList.size() > 0) {
                    if (userId.equals(transResourceApply.getUserId())){
                        sendMessage(msgUser, transUserApplyInfoList.get(0).getContactTelephone());
                    }else {
                        sendMessage(msg, transUserApplyInfoList.get(0).getContactTelephone());
                    }
                }
            }
        }
    }

    @Async
    public void sendMessage(String msg,String mobile){
        try {
            if(StringUtils.isNotBlank(mobile))
                smsMessageService.send(msg, mobile);
        }catch (Exception ex){
            log.error("短信发送失败！",ex.getCause());
        }
    }

    @AfterReturning(pointcut="execution(* cn.gtmap.landsale.admin.service.NotifyTimerService.smsNotify())",returning="notification")
    public void notificationTimer(JoinPoint jp,Map<String,String> notification){
        if(!notification.isEmpty()){
            for(String key:notification.keySet()){
                sendMessage(notification.get(key),key);
            }
        }

    }
}
