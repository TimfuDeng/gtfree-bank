package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 成交审核
 * @author zsj
 * @version v1.0, 2017/12/23
 */
@Entity
@Table(name = "trans_resource_verify")
public class TransResourceVerify implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String verifyId;

    @Column(nullable = false, length = 32)
    private String resourceId;

    @Column(nullable = false, length = 32)
    private String offerId;

    @Column(nullable = false, length = 32)
    private String userId;

    @Column(nullable = false,columnDefinition ="number(1) default '0'")
    private int currentStatus;//当前状态 0：最新状态 1：历史状态

    @Column(nullable = false ,columnDefinition ="number(1) default '0'")
    private int verifyStatus;//审核状态 0：未审核 1：未通过 2：已通过

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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
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

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
