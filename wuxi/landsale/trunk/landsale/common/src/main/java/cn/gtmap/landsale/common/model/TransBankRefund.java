package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 银行退款信息表
 * @author zsj
 * @version v1.0, 2017/8/30
 */
@Entity
@Table(name = "TRANS_BANK_REFUND")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransBankRefund implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String refundId;

    @Column(length = 32)
    private String payId;

    @Column(length = 32)
    private String accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date refundTime;

    @Column(length = 50)
    private String batchNo;//批次号

    @Column(length = 2)
    private String refundResult;  // 退款结果 00退款成功；09： 其它错误；99：系统错误；10：退款中（发起退款申请默认退款中）

    @Column(length = 4000)
    private String refundAddword; //处理结果 退款申请是银行返回的结果描述

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date refundEndTime;  //处理完成时间

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRefundResult() {
        return refundResult;
    }

    public void setRefundResult(String refundResult) {
        this.refundResult = refundResult;
    }

    public String getRefundAddword() {
        return refundAddword;
    }

    public void setRefundAddword(String refundAddword) {
        this.refundAddword = refundAddword;
    }

    public Date getRefundEndTime() {
        return refundEndTime;
    }

    public void setRefundEndTime(Date refundEndTime) {
        this.refundEndTime = refundEndTime;
    }
}
