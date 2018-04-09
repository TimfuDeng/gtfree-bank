package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网上交易银行的实体对象
 * Created by jiff on 14/12/24.
 */
@Entity
@Table(name = "trans_bank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransBank implements Serializable {
    @Id
    @Column(length = 32)
    private String bankId;

    @Column(length = 32)
    private String bankCode;

    @Column(nullable = false,length = 100)
    private String bankName;  //银行名称

    @Column(length = 50)
    private String accountName;//开户名称

    @Column(length = 50)
    private String accountCode;  //开户帐号

    @Column(length = 10)
    private String regionCode; //货行政区

    @Column(length = 5)
    private String moneyUnit;  //货币单位

    @Column(length = 50)
    private String interfaceIp;

    @Column(length = 10)
    private String interfacePort;

    @Column(length = 50)
    private String telephone;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getInterfaceIp() {
        return interfaceIp;
    }

    public void setInterfaceIp(String interfaceIp) {
        this.interfaceIp = interfaceIp;
    }

    public String getInterfacePort() {
        return interfacePort;
    }

    public void setInterfacePort(String interfacePort) {
        this.interfacePort = interfacePort;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getMoneyUnit() {
        return moneyUnit;
    }

    public void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
