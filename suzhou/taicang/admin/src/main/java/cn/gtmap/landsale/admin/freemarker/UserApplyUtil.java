package cn.gtmap.landsale.admin.freemarker;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.core.BankAllList;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/6/2.
 */
public class UserApplyUtil {

    TransBankAccountService transBankAccountService;

    TransBankPayService transBankPayService;

    TransResourceService transResourceService;

    TransResourceApplyService transResourceApplyService;

    ClientService clientService;

    BankAllList bankAllList;

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setTransBankPayService(TransBankPayService transBankPayService) {
        this.transBankPayService = transBankPayService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setBankAllList(BankAllList bankAllList) {
        this.bankAllList = bankAllList;
    }

    public TransBankAccount getBankAccount(String applyId){
        return transBankAccountService.getTransBankAccountByApplyId(applyId);
    }

    public List<TransBankPay> getBankAccountPayList(String accountId){
        return transBankPayService.getTransBankPaysByAccountId(accountId);
    }

    /**
     * @作者 王建明
     * @创建日期 2015-11-15
     * @创建时间 12:05
     * @描述 —— 获取地块竞得人是否通过资格审核
     */
    public Constants.TrialType getMaxApplyTrialType(String resourceId){
        TransResource transResource = transResourceService.getTransResource(resourceId);

        List<TransResourceOffer> resourceOffers= clientService.getOfferList(resourceId,-1);
        TransResourceOffer maxOffer= (resourceOffers.size()>0) ? maxOffer=resourceOffers.get(0):null;
        if(transResource.getResourceStatus() == 30){
            TransResourceApply transResourceApplyMaxPrice = transResourceApplyService.getTransResourceApplyByUserId(maxOffer.getUserId(),resourceId);
            if(transResourceApplyMaxPrice != null)
                return transResourceApplyMaxPrice.getTrialType();
        }

        return null;
    }

    public String getBankName(String code){
        return bankAllList.getBankName(code);
    }


}
