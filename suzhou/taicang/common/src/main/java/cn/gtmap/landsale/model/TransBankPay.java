package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 银行的到账信息
 * Created by jibo1_000 on 2015/5/14.
 */
@Entity
@Table(name = "trans_bank_pay")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransBankPay implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String payId;

    @Column(length = 50)
    private String payNo;   //流水号，不好重复

    @Column(length = 20)
    private String bankCode;   //银行编号

    @Column(length = 50)
    private String payBank;   //交款银行

    @Column(length = 100)
    private String payBankAddress;   //交款银行网点

    @Column(length = 50)
    private String payBankAccount;   //交款银行账号(

    @Column(length = 50)
    private String payName;   //交款户名

    @Column(length = 50)
    private String accountCode;   //中心账号

    @Column(length = 50)
    private String accountName;   //中心户名

    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private double amount;  //交易金额

    @Column(precision = 18,scale =6,columnDefinition ="number(18,6)")
    private double rate;  //汇率(当天汇率)

    @Column(nullable = false,length = 50)
    private String accountId;

    @Column(length = 5)
    private String moneyUnit;  //货币单位

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date payTime;

    @Column(length = 255)
    private String remark;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getPayBankAddress() {
        return payBankAddress;
    }

    public void setPayBankAddress(String payBankAddress) {
        this.payBankAddress = payBankAddress;
    }

    public String getPayBankAccount() {
        return payBankAccount;
    }

    public void setPayBankAccount(String payBankAccount) {
        this.payBankAccount = payBankAccount;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }



    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMoneyUnit() {
        return moneyUnit;
    }

    public void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
