package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransBankPay;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/14.
 */
public interface TransBankPayService {

    TransBankPay getTransBankPay(String payId);

    List<TransBankPay> getTransBankPaysByAccountCode(String accountCode,String payNo);

	/**
	 * @作者 王建明
	 * @创建日期 2015/7/1
	 * @创建时间 17:08
	 * @描述 —— 退款说明书中的退款金额需要根据子账号和付款用户去查询所有的付款记录
	 */
    List<TransBankPay> getTransBankPaysByAccountCodeAndAccountId(String accountCode,String accountId);

    List<TransBankPay> getTransBankPaysByAccountId(String accountId);

    TransBankPay saveTransBankPay(TransBankPay transBankTrans);


}
