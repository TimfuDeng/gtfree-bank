package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 网上交易银行的实体对象
 * Created by jiff on 14/12/24.
 */
@Entity
@Table(name = "trans_bank_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransBankAccount implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String accountId;

    @Column(length = 32,nullable = false)
    private String applyId;

    @Column(length = 50,nullable = false)
    private String applyNo;  //流水号，竞买号

    @Column(length = 100)
    private String accountCode;

    @Column(length = 255)
    private String reuslt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date receiveDate;

    @Column
    private boolean close;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getReuslt() {
        return reuslt;
    }

    public void setReuslt(String reuslt) {
        this.reuslt = reuslt;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
