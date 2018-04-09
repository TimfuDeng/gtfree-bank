package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 网上交易银行的实体对象
 * @author jiff on 14/12/24.
 */
@Entity
@Table(name = "trans_bank")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransBank implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String bankId;

    @Column(length = 32)
    private String bankCode;

    /**
     * 银行名称
     */
    @Column(nullable = false,length = 100)
    private String bankName;

    /**
     * 银行图标
     */
    @Column(nullable = false, length = 100)
    private String bankIcon;

    /**
     * 银行图标名称
     */
    @Column(nullable = false, length = 100)
    private String bankIconName;

    /**
     * 开户名称
     */
    @Column(length = 50)
    private String accountName;

    /**
     * 开户帐号
     */
    @Column(length = 50)
    private String accountCode;

    /**
     * 行政区
     */
    @Column(length = 10)
    private String regionCode;

    /**
     * 货币单位
     */
    @Column(length = 5)
    private String moneyUnit;

    @Column(length = 50)
    private String interfaceIp;

    @Column(length = 10)
    private String interfacePort;

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

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon;
    }

    public String getBankIconName() {
        return bankIconName;
    }

    public void setBankIconName(String bankIconName) {
        this.bankIconName = bankIconName;
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
}
