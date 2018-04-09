package cn.gtmap.landsale.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 竞买人申购表
 * Created by jiff on 14/12/14.
 */
@Entity
@Table(name = "trans_resource_apply")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransResourceApply implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String applyId;

    @Column(nullable = false,length = 32)
    private String resourceId;

    @Column(nullable = false,length =32)
    private String userId;

    @Column(nullable = false,columnDefinition ="number(2,0) default '0'")
    private int applyStep;

    @Column(columnDefinition ="number(1,0) default '0'")
    private int applyType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date applyDate;

    @Column(precision = 1, nullable = false)
    private Constants.TrialType trialType = Constants.TrialType.NONE_COMMIT_TRIAL;//审核状态

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date trialDate;//审核日期

    @Column(length =2000)
    private String trialReason;//审核描述,主要填写未通过原因

    @Column(length = 50)
    private String createNewComName;   //如果申请类型为成立新公司（2），需要填写新公司的名称

    @Column(length =10)
    private String moneyUnit;  //货币单位 ，默认CNY

    @Column(length =10)
    private String bankCode;  //选择银行

    private boolean limitTimeOffer;  //是否进入限时竞价

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getApplyStep() {
        return applyStep;
    }

    public void setApplyStep(int applyStep) {
        this.applyStep = applyStep;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Constants.TrialType getTrialType() {
        return trialType;
    }

    public void setTrialType(Constants.TrialType trialType) {
        this.trialType = trialType;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public String getTrialReason() {
        return trialReason;
    }

    public void setTrialReason(String trialReason) {
        this.trialReason = trialReason;
    }

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public String getCreateNewComName() {
        return createNewComName;
    }

    public void setCreateNewComName(String createNewComName) {
        this.createNewComName = createNewComName;
    }

    public String getMoneyUnit() {
        return moneyUnit;
    }

    public void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isLimitTimeOffer() {
        return limitTimeOffer;
    }

    public void setLimitTimeOffer(boolean limitTimeOffer) {
        this.limitTimeOffer = limitTimeOffer;
    }
}
