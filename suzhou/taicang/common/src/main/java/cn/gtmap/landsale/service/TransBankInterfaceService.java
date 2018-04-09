package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransBankPay;
import cn.gtmap.landsale.model.TransResourceApply;

/**
 * Created by Jibo on 2015/5/21.
 */
public interface TransBankInterfaceService {

    /**
     * 向银行发送开户申请
     * @param transResourceApply
     * @return
     */
    String sendBankApplyAccount(TransResourceApply transResourceApply);

    /**
     * 向银行发送到账通知，手动发送到账通知用
     * @param transBankPay
     * @return
     */
    String sendBankPayTest(TransBankPay transBankPay);

    /**
     * 链路监测xml
     * @return
     */
    String sendTestXml();
    /**
     * 接收银行返回信息
     * @param info
     * @return
     */
    Object receiveBankBack(String info);

    /**
     * socket server接受到的信息
     * @param info
     * @return
     */
    String socketServerReceive(String info);
}
