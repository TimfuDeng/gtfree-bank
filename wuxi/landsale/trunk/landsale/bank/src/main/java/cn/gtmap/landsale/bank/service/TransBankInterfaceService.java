package cn.gtmap.landsale.bank.service;


import cn.gtmap.landsale.common.model.TransBank;
import cn.gtmap.landsale.common.model.TransBankAccount;
import cn.gtmap.landsale.common.model.TransResourceApply;

/**
 * 银行接口
 * @author zsj
 * @version v1.0, 2017/8/29
 */
public interface TransBankInterfaceService {

    /**
     * G00003
     * 向银行发送开户申请 申请保证金子账号
     * @param transResourceApply
     * @return
     */
    String sendBankApplyAccount(TransResourceApply transResourceApply);

    /**
     * G00004
     * 向银行发送注销子账号申请
     * @param transBankAccount
     * @return
     */
    String sendBankCancelAccount(TransBankAccount transBankAccount);

    /**
     * G00005
     * 向银行发送保证金到账明细查询
     * @param transBankAccount
     * @return
     */
    String sendBankPayDetail(TransBankAccount transBankAccount);

    /**
     * G00009
     * 链路监测xml
     * @return
     */
    String sendTestXml();

    /**
     * G00010
     * 向银行发送保证金退款申请
     * @param transBankAccount
     * @return
     */
    String sendBankRefund(TransBankAccount transBankAccount);

    /**
     * G00011
     * 向银行发送保证金退款明细查询
     * @param batchNo 批次号 必传
     * @param accountCode 子账号 必传
     * @param payBankAccount 付款账号 不必传 传入作为查询条件
     * @param payNo 原到账交易流水号 不必传 传入作为查询条件
     * @param amount 交易金额 不必传 传入作为查询条件
     * @return
     */
    String sendBankRefundDetail(String batchNo, String accountCode, String payBankAccount, String payNo, String amount);

    /**
     * G00013
     * 向银行发送子账号剩余情况查询
     * @param transBank
     * @return
     */
    String sendBankAccountSurplus(TransBank transBank);

    /**
     * 接收银行返回信息
     * @param info
     * @return
     */
    Object receiveBankBack(String info);

}
