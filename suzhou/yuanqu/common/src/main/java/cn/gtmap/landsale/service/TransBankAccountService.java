package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransResource;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public interface TransBankAccountService {

    /**
     * 获得银行子账户开户信息
     * @param accountId
     * @return
     */
    TransBankAccount getTransBankAccount(String accountId);

    /**
     * 根据applyNo获得银行子账户信息,流水号
     * @param applyNo
     * @return
     */
    TransBankAccount getTransBankAccountByApplyNo(String applyNo);

    /**
     * 获得正在使用的银行子帐号信息
     * @param accountCode
     * @return
     */
    TransBankAccount getTransBankAccountByAccountCode(String accountCode);

    /**
     * 根据子账号和竞买号获取银行账号
     * @param accountCode 子账号
     * @param applyNo 竞买流水号
     * @return
     */
    TransBankAccount getTransBankAccountByAccountCodeAndApplyNo(String accountCode,String applyNo);
    /**
     * 根据applyId获得银行子账户信息
     * @param applyId
     * @return
     */
    TransBankAccount getTransBankAccountByApplyId(String applyId);
    /**
     * 保存相关子账户信息
     * @param transBankAccount
     * @return
     */
    TransBankAccount saveTransBankAccount(TransBankAccount transBankAccount);


    String getNextApplyNo();

    TransBankAccount createOrGetTransBankAccount(String applyId);
}
