package cn.gtmap.landsale.admin.util;

import cn.gtmap.landsale.admin.register.*;
import cn.gtmap.landsale.common.model.TransBank;
import cn.gtmap.landsale.common.model.TransBankAccount;
import cn.gtmap.landsale.common.model.TransBankPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lq on 2017/11/17.
 */
@Component
public class UserApplyUtil {

    @Autowired
    TransBankAccountClient transBankAccountClient;

    @Autowired
    TransBankPayClient transBankPayClient;

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    ClientClient clientClient;

    @Autowired
    TransBankClient transBankClient;

//    BankAllList bankAllList;
//    public void setBankAllList(BankAllList bankAllList) {
//        this.bankAllList = bankAllList;
//    }

    public TransBankAccount getBankAccount(String applyId){
        return transBankAccountClient.getTransBankAccountByApplyId(applyId);
    }

    public List<TransBankPay> getBankAccountPayList(String accountId){
        return transBankPayClient.getTransBankPaysByAccountId(accountId);
    }

    /**
     * @作者 王建明
     * @创建日期 2015-11-15
     * @创建时间 12:05
     * @描述 —— 获取地块竞得人是否通过资格审核
     */
//    public Constants.TrialType getMaxApplyTrialType(String resourceId){
//        TransResource transResource = transResourceClient.getTransResource(resourceId);
//
//        List<TransResourceOffer> resourceOffers= clientClient.getOfferList(resourceId,-1);
//        TransResourceOffer maxOffer= (resourceOffers.size()>0) ? maxOffer=resourceOffers.get(0):null;
//        if(transResource.getResourceStatus() == 30){
//            TransResourceApply transResourceApplyMaxPrice = transResourceApplyClient.getTransResourceApplyByUserId(maxOffer.getUserId(),resourceId);
//            if(transResourceApplyMaxPrice != null)
//                return transResourceApplyMaxPrice.getTrialType();
//        }
//
//        return null;
//    }

    public String getBankNameById(String bankId){
        return transBankClient.getBankById(bankId).getBankName();
    }

    public String getBankName(String code){
        return transBankClient.findByCode(code).getBankName();
    }

    /**
     * 根据银行id获取币种
     * @param id
     * @return
     */
    public String getMoneyUnitById(String id){
        if(null!= id){
            TransBank bank= transBankClient.getBankById(id);
            if(null!=bank){
                return bank.getMoneyUnit();
            }
        }
        return id;
    }
}
