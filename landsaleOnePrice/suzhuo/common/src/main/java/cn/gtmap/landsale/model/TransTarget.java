package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2016/6/30.
 */
@Entity
@Table(name = "TRANS_TARGET")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransTarget implements Serializable{
    @Id
    @Column(length = 50)
    private String id;

    @Column(length = 500)
    private String name;

    @Column(length = 50)
    private String businessType;

    @Column(length = 50)
    private String transType;

    @Column(nullable = true,precision = 18,scale =2,columnDefinition ="number(18,2) default '0'")
    private Double reservePrice;

    @Column(nullable = true,precision = 18,scale =2,columnDefinition ="number(18,2) default '0'")
    private Double beginPrice;

    @Column(nullable = true,precision = 18,scale =2,columnDefinition ="number(18,2) default '0'")
    private  Double priceStep;

    @Column(nullable = true,precision = 18,scale =2,columnDefinition ="number(18,2) default '0'")
    private Double  earnestMoney;

    @Column(length = 4000)
    private String transCondition;

    @Column(length = 100)
    private  String conditionOrganName;

    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private  Integer isNetTrans;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isLimitTrans;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer allowLive;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer allowUnion;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private  Integer allowTrust;

    @Column(length = 50)
    private String rejectId;

    @Column
    private Date beginNoticeTime;

    @Column
    private Date endNoticeTime;

    @Column
    private Date beginApplyTime;

    @Column
    private  Date endApplyTime;

    @Column
    private Date beginEarnestTime;

    @Column
    private Date endEarnestTime;

    @Column
    private Date beginListTime;

    @Column
    private Date endListTime;

    @Column
    private Date beginFocusTime;


    @Column
    private Date endFocusTime;


    @Column
    private Date beginLimitTime;

    @Column
    private Date endTransTime;

    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double transPrice;

    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private  Integer isValid;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isSuspend;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isStop;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer status;

    @Column(length = 50)
    private String createUserId;

    @Column
    private Date createDate;

    @Column(length = 100)
    private String remark;

    @Column(length = 100)
    private String no;

    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double finalPrice;

    @Column(length = 50)
    private String unit;

    @Column(length = 100)
    private String transTypeName;

    @Column(length = 100)
    private String transTypeLabel;

    @Column(length = 50)
    private String transOrganId;

    @Column(length = 50)
    private String organId;

    @Column(length = 50)
    private String TransTypeId;

    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double unitPrice;

    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double unitPrice2;

    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double buildingPrice;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private  Integer isAfterCheck;

    @Column(length = 400)
    private String abortReson;

    @Column
    private Date afterCheckDate;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer afterCheck;

    @Column(length = 50)
    private String succLicenseId;

    @Column(length = 4000)
    private String succTransPrice;

    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private Integer isOnline;

    @Column
    private Integer isscan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(Double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Double getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(Double beginPrice) {
        this.beginPrice = beginPrice;
    }

    public Double getPriceStep() {
        return priceStep;
    }

    public void setPriceStep(Double priceStep) {
        this.priceStep = priceStep;
    }

    public Double getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(Double earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public String getTransCondition() {
        return transCondition;
    }

    public void setTransCondition(String transCondition) {
        this.transCondition = transCondition;
    }

    public String getConditionOrganName() {
        return conditionOrganName;
    }

    public void setConditionOrganName(String conditionOrganName) {
        this.conditionOrganName = conditionOrganName;
    }

    public Integer getIsNetTrans() {
        return isNetTrans;
    }

    public void setIsNetTrans(Integer isNetTrans) {
        this.isNetTrans = isNetTrans;
    }

    public Integer getIsLimitTrans() {
        return isLimitTrans;
    }

    public void setIsLimitTrans(Integer isLimitTrans) {
        this.isLimitTrans = isLimitTrans;
    }

    public Integer getAllowLive() {
        return allowLive;
    }

    public void setAllowLive(Integer allowLive) {
        this.allowLive = allowLive;
    }

    public Integer getAllowUnion() {
        return allowUnion;
    }

    public void setAllowUnion(Integer allowUnion) {
        this.allowUnion = allowUnion;
    }

    public Integer getAllowTrust() {
        return allowTrust;
    }

    public void setAllowTrust(Integer allowTrust) {
        this.allowTrust = allowTrust;
    }

    public String getRejectId() {
        return rejectId;
    }

    public void setRejectId(String rejectId) {
        this.rejectId = rejectId;
    }

    public Date getBeginNoticeTime() {
        return beginNoticeTime;
    }

    public void setBeginNoticeTime(Date beginNoticeTime) {
        this.beginNoticeTime = beginNoticeTime;
    }

    public Date getEndNoticeTime() {
        return endNoticeTime;
    }

    public void setEndNoticeTime(Date endNoticeTime) {
        this.endNoticeTime = endNoticeTime;
    }

    public Date getBeginApplyTime() {
        return beginApplyTime;
    }

    public void setBeginApplyTime(Date beginApplyTime) {
        this.beginApplyTime = beginApplyTime;
    }

    public Date getEndApplyTime() {
        return endApplyTime;
    }

    public void setEndApplyTime(Date endApplyTime) {
        this.endApplyTime = endApplyTime;
    }

    public Date getBeginEarnestTime() {
        return beginEarnestTime;
    }

    public void setBeginEarnestTime(Date beginEarnestTime) {
        this.beginEarnestTime = beginEarnestTime;
    }

    public Date getEndEarnestTime() {
        return endEarnestTime;
    }

    public void setEndEarnestTime(Date endEarnestTime) {
        this.endEarnestTime = endEarnestTime;
    }

    public Date getBeginListTime() {
        return beginListTime;
    }

    public void setBeginListTime(Date beginListTime) {
        this.beginListTime = beginListTime;
    }

    public Date getEndListTime() {
        return endListTime;
    }

    public void setEndListTime(Date endListTime) {
        this.endListTime = endListTime;
    }

    public Date getBeginFocusTime() {
        return beginFocusTime;
    }

    public void setBeginFocusTime(Date beginFocusTime) {
        this.beginFocusTime = beginFocusTime;
    }

    public Date getEndFocusTime() {
        return endFocusTime;
    }

    public void setEndFocusTime(Date endFocusTime) {
        this.endFocusTime = endFocusTime;
    }

    public Date getBeginLimitTime() {
        return beginLimitTime;
    }

    public void setBeginLimitTime(Date beginLimitTime) {
        this.beginLimitTime = beginLimitTime;
    }

    public Date getEndTransTime() {
        return endTransTime;
    }

    public void setEndTransTime(Date endTransTime) {
        this.endTransTime = endTransTime;
    }

    public Double getTransPrice() {
        return transPrice;
    }

    public void setTransPrice(Double transPrice) {
        this.transPrice = transPrice;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsSuspend() {
        return isSuspend;
    }

    public void setIsSuspend(Integer isSuspend) {
        this.isSuspend = isSuspend;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTransTypeName() {
        return transTypeName;
    }

    public void setTransTypeName(String transTypeName) {
        this.transTypeName = transTypeName;
    }

    public String getTransTypeLabel() {
        return transTypeLabel;
    }

    public void setTransTypeLabel(String transTypeLabel) {
        this.transTypeLabel = transTypeLabel;
    }

    public String getTransOrganId() {
        return transOrganId;
    }

    public void setTransOrganId(String transOrganId) {
        this.transOrganId = transOrganId;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getTransTypeId() {
        return TransTypeId;
    }

    public void setTransTypeId(String transTypeId) {
        TransTypeId = transTypeId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getUnitPrice2() {
        return unitPrice2;
    }

    public void setUnitPrice2(Double unitPrice2) {
        this.unitPrice2 = unitPrice2;
    }

    public Double getBuildingPrice() {
        return buildingPrice;
    }

    public void setBuildingPrice(Double buildingPrice) {
        this.buildingPrice = buildingPrice;
    }

    public Integer getIsAfterCheck() {
        return isAfterCheck;
    }

    public void setIsAfterCheck(Integer isAfterCheck) {
        this.isAfterCheck = isAfterCheck;
    }

    public String getAbortReson() {
        return abortReson;
    }

    public void setAbortReson(String abortReson) {
        this.abortReson = abortReson;
    }

    public Date getAfterCheckDate() {
        return afterCheckDate;
    }

    public void setAfterCheckDate(Date afterCheckDate) {
        this.afterCheckDate = afterCheckDate;
    }

    public Integer getAfterCheck() {
        return afterCheck;
    }

    public void setAfterCheck(Integer afterCheck) {
        this.afterCheck = afterCheck;
    }

    public String getSuccLicenseId() {
        return succLicenseId;
    }

    public void setSuccLicenseId(String succLicenseId) {
        this.succLicenseId = succLicenseId;
    }

    public String getSuccTransPrice() {
        return succTransPrice;
    }

    public void setSuccTransPrice(String succTransPrice) {
        this.succTransPrice = succTransPrice;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getIsscan() {
        return isscan;
    }

    public void setIsscan(Integer isscan) {
        this.isscan = isscan;
    }
}
