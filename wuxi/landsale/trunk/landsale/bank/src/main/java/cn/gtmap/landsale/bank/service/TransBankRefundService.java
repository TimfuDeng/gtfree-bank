package cn.gtmap.landsale.bank.service;


import cn.gtmap.landsale.common.model.TransBankRefund;

import java.util.List;

/**
 * 退款申请Service
 * @author zsj
 * @version v1.0, 2017/8/30
 */
public interface TransBankRefundService {

    /**
     * 获取退款申请
     * @param refundId
     * @return
     */
    TransBankRefund getTransBankRefundById(String refundId);

    /**
     * 依据payId获取
     * @param payId
     * @return
     */
    TransBankRefund getTransBankRefundByPayId(String payId);

    /**
     *
     * @param accountId
     * @param batchNo
     * @return
     */
    List<TransBankRefund> getTransBankPaysByAccountAndBatch(String accountId, String batchNo);

    TransBankRefund saveTransBankRefund(TransBankRefund transBankRefund);

    TransBankRefund updateTransBankRefund(TransBankRefund transBankRefund);

}
