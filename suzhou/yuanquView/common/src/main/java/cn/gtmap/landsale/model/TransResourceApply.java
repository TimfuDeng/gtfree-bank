package cn.gtmap.landsale.model;


import cn.gtmap.egovplat.core.annotation.Field;
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
    @Field("地块Id,一个地块有多次申购")
    private String resourceId;

    @Column(nullable = false,length =32)
    @Field("账号Id,一个账号有多次地块申购")
    private String userId;

    @Column(length =32)
    @Field("竞买人Id,一个竞买人信息对应一次申购")
    private String infoId;

    @Column(nullable = false,columnDefinition ="number(2,0) default '0'")
    private int applyStep;//竞买步骤

    @Column(columnDefinition ="number(1,0) default '0'")
    private int applyType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date applyDate;

    @Column(length = 2)
    private String isComName;//是否成立项目公司

    @Column(length = 50)
    private String createNewComName;   //如果申请类型为成立新公司（2），需要填写新公司的名称

    @Column(length =10)
    private String moneyUnit= Constants.MoneyCNY;  //货币单位 ，默认CNY

    @Column(length =10)
    private String bankCode;  //选择银行

    @Column(columnDefinition ="number(1,0) default '0'")
    private boolean limitTimeOffer;  //是否进入限时竞价

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field(value = "记录是否进入限时竞价询问的确认时间")
    private Date limitTimeOfferConfirmDate; //记录是否进入限时竞价询问的确认时间

    @Column(columnDefinition ="number(3,0) default '0'")
    @Field(value = "有效号牌")
    private int applyNumber; //有效号牌

    @Column(length = 32)
    @Field("申购资格审核Id-外键")
    private String qualifiedId;

    @Column(length = 32)
    @Field("银行Id-外键")
    private String bankId;

    @Column(columnDefinition ="number(1,0) default '0'")
    private boolean fixedOfferBack;  //是否退还保证金


    @Transient
    private Boolean fixedOfferEnough=false;//保证金是否交足够

    private TransUserApplyInfo transUserApplyInfo;//竞买人信息

    public boolean isFixedOfferBack() {
        return fixedOfferBack;
    }

    public void setFixedOfferBack(boolean fixedOfferBack) {
        this.fixedOfferBack = fixedOfferBack;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Boolean getFixedOfferEnough() {
        return fixedOfferEnough;
    }

    public void setFixedOfferEnough(Boolean fixedOfferEnough) {
        this.fixedOfferEnough = fixedOfferEnough;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public TransUserApplyInfo getTransUserApplyInfo() {
        return transUserApplyInfo;
    }

    public void setTransUserApplyInfo(TransUserApplyInfo transUserApplyInfo) {
        this.transUserApplyInfo = transUserApplyInfo;
    }

    public String getQualifiedId() {
        return qualifiedId;
    }

    public void setQualifiedId(String qualifiedId) {
        this.qualifiedId = qualifiedId;
    }

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

    public int getApplyNumber() {
        return applyNumber;
    }

    public void setApplyNumber(int applyNumber) {
        this.applyNumber = applyNumber;
    }

    public Date getLimitTimeOfferConfirmDate() {
        return limitTimeOfferConfirmDate;
    }

    public void setLimitTimeOfferConfirmDate(Date limitTimeOfferConfirmDate) {
        this.limitTimeOfferConfirmDate = limitTimeOfferConfirmDate;
    }

    public String getIsComName() {
        return isComName;
    }

    public void setIsComName(String isComName) {
        this.isComName = isComName;
    }
}
