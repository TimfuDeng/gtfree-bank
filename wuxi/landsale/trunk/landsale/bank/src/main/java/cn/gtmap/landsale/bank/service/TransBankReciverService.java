package cn.gtmap.landsale.bank.service;


import cn.gtmap.landsale.common.model.TransBankAccount;
import cn.gtmap.landsale.common.model.TransBankPay;

/**
 * 接受银行返回信息测试 接口
 * @author zsj
 * @version v1.0, 2017/8/29
 */
public interface TransBankReciverService {


    /**
     * G00001
     * 模拟银行发送到账通知，手动发送到账通知用
     * @param transBankPay
     * @return
     */
    String sendBankPayTest(TransBankPay transBankPay);

    /**
     * G00012
     * 模拟银行发送退款通知，手动发送退款通知用
     * @param transBankAccount
     * @return
     */
    String sendBankRefundTest(TransBankAccount transBankAccount);

    /**
     * socket server test接受到的信息 测试用 只模拟返回XML 不处理业务
     * @param receiveXml
     * @return
     */
    String socketServerReceiveTest(String receiveXml);
}
