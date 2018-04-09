package cn.gtmap.landsale.admin.freemarker;

import cn.gtmap.landsale.core.BankAllList;
import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransBankPay;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.*;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/6/2.
 */
public class UserApplyUtil {

    TransBankAccountService transBankAccountService;

    TransBankPayService transBankPayService;

    TransBankService transBankService;

    BankAllList bankAllList;

    public void setTransBankService(TransBankService transBankService) {
        this.transBankService = transBankService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setTransBankPayService(TransBankPayService transBankPayService) {
        this.transBankPayService = transBankPayService;
    }

    public void setBankAllList(BankAllList bankAllList) {
        this.bankAllList = bankAllList;
    }

    public TransBankAccount getBankAccount(String applyId){
        return transBankAccountService.getTransBankAccountByApplyId(applyId);
    }

    public List<TransBankPay> getBankAccountPayList(String accountId){
        List<TransBankPay>  list=transBankPayService.getTransBankPaysByAccountId(accountId);
        return list;
    }

    /**
     * 根据银行code得到配置文件银行名称
     * @param code
     * @return
     */
    public String getBankName(String code){
        return bankAllList.getBankName(code);
    }

    /**
     * 更具银行Id获取银行名称，数据库中的银行名称
     * @param Id
     * @return
     */
    public String getBnakNameById(String Id){
       TransBank bank= transBankService.getBank(Id);
        if(null!=bank){
            return bank.getBankName();
        }
        return null;
    }

    /**
     * 根据银行id获取币种
     * @param Id
     * @return
     */
    public String getMoneyUnitById(String Id){
        if(null!=Id){
            TransBank bank= transBankService.getBank(Id);
            if(null!=bank){
                return bank.getMoneyUnit();
            }
        }
        return Id;
    }
}


