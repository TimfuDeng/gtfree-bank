package cn.gtmap.landsale.bank.service;


import cn.gtmap.landsale.common.model.TransBankPay;

import java.util.List;

/**
 * @author jibo1_000 on 2015/5/14.
 */
public interface TransBankPayService {

    /**
     * 获取银行到账信息
     * @param payId
     * @return
     */
    TransBankPay getTransBankPay(String payId);

    /**
     * 根据账户信息和流水号获取
     * @param accountCode
     * @param payNo
     * @return
     */
    List<TransBankPay> getTransBankPaysByAccountCodeAndPayNo(String accountCode, String payNo);

	/**
	 * @作者 王建明 6836
	 * @创建日期 2015/7/1
	 * @创建时间 17:08
     * @param accountCode
     * @param accountId
     * @return
	 * @描述 —— 退款说明书中的退款金额需要根据子账号和付款用户去查询所有的付款记录
	 */
    List<TransBankPay> getTransBankPaysByAccountCodeAndAccountId(String accountCode, String accountId);

    /**
     * 根据账户信息获取到账列表
     * @param accountId
     * @return
     */
    List<TransBankPay> getTransBankPaysByAccountId(String accountId);

    /**
     * 保存
     * @param transBankTrans
     * @return
     */
    TransBankPay saveTransBankPay(TransBankPay transBankTrans);

    /**
     * 根据账户信息获取到账列表
     * @param accountCode
     * @return
     */
    List<TransBankPay> getTransBankPaysByAccountCode(String accountCode);

    /**
     *错转款  accountId is null
     * @param accountId
     * @return
     */
    List<TransBankPay> getTransPaysByAccointIdIsNULL(String accountId);
}
