package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 网上交易银行的实体对象
 * @author jiff on 14/12/24.
 */
@Entity
@Table(name = "trans_bank_config")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransBankConfig implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String configId;

    /**
     * 银行Code
     */
    @Column(nullable = false, length = 100)
    private String bankCode;

    /**
     * 银行名称
     */
    @Column(nullable = false, length = 100)
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createTime;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
