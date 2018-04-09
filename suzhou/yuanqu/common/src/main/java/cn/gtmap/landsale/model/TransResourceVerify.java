package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2015/10/9.
 */
@Entity
@Table(name = "trans_resource_verify")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransResourceVerify implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String verifyId;

    @Column(nullable = false,columnDefinition ="number(1) default '0'")
    private int verifyType;//审核类型 0：未审核 1：已审核

    @Column(nullable = false ,columnDefinition ="number(1) default '0'")
    private int verifyStatus;//审核状态 0：未通过 1：已通过

    @Column(nullable = true,length=32)
    private String auditor;//审核人

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date verifyTime;//审核时间

    @Column(nullable = true,length = 256)
    private String verifySuggestion;//审核意见

    public String getVerifyId() {
        return verifyId;
    }

    public void setVerifyId(String verifyId) {
        this.verifyId = verifyId;
    }

    public int getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(int verifyType) {
        this.verifyType = verifyType;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifySuggestion() {
        return verifySuggestion;
    }

    public void setVerifySuggestion(String verifySuggestion) {
        this.verifySuggestion = verifySuggestion;
    }
}
