package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.format.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网上交易出让地块实体对象
 * @author jiff on 14/12/14.
 */
@Entity
@Table(name = "trans_resource")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransResource implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String resourceId;

    @Column(length = 32)
    private String ggId;

    /**
     * 成交公告id
     */
    @Column(length = 32)
    private String dealNoticeId;

    /**
     * 成交公告id
     */
    @Column(length = 32)
    private String suspendNoticeId;

    /**
     * 地块编号
     */
    @Column(nullable = false,length = 50)
    private String resourceCode;

    @Column(nullable = false)
    private String resourceLocation;

    /**
     * 所属行政区
     */
    @Column(length = 32)
    private String regionCode;

    /**
     * 所属组织
     */
    @Column(length = 50)
    private String organizeId;

    /**
     * 资源编辑状态
     */
    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int resourceEditStatus=0;

    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int resourceStatus;

    /**
     * 资源类别
     */
    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int resourceType;

    /**
     * 报名开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date bmBeginTime;

    /**
     * 报名截至时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date bmEndTime;

    /**
     * 挂牌开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date gpBeginTime;

    /**
     * 挂牌截至时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date gpEndTime;

    /**
     * 限时竞价开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date xsBeginTime;

    /**
     * 有效报价截至时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date yxEndTime;

    /**
     * 保证金开始时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date bzjBeginTime;

    /**
     * 保证金截至时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date bzjEndTime;

    /**
     * 出让面积
     */
    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double crArea;

    /**
     * 建筑面积 报价单位为 楼面价时必填
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double buildingArea;

    /**
     * 起始价(万元)
     */
    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double beginOffer;

    /**
     * 是否资格前审
     */
    @Column(nullable = false)
    private Integer beforeBzjAudit;

    /**
     * 保证金(万元)
     */
    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double fixedOffer;

    /**
     * 保证金美元(美元)
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double fixedOfferUsd;

    /**
     * 保证金美元(美元)
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double fixedOfferHkd;

    /**
     * 竞价增价幅度(万元)
     */
    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double addOffer;

    /**
     * 是否有最高限价
     */
    @Column
    private Integer maxOfferExist;

    /**
     * 最高限价
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6)")
    private Double maxOffer;

    /**
     * 最高限价后的 选择的成交方式
     */
    @Column(precision = 2)
    private Constants.MaxOfferChoose maxOfferChoose;

    /**
     * 该地块最后的 成交方式
     */
    @Column(precision = 2)
    private Integer successOfferChoose;

    /**
     * 是否有底价 0否 1是
     */
    @Column
    private Integer minOffer;

    /**
     * 报价单位，0-万元（总价） 1-元/平方米 2-万元/亩
     */
    @Column(nullable = false,columnDefinition ="number(1) default '0'")
    private int offerUnit;

    /**
     * 公租房竞价类型 0-平方米（面积） 1-万元（资金）
     */
    @Column(columnDefinition ="number(1) default '0'")
    private int publicHouse;

    /**
     * 起始公租房
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double beginHouse;

    /**
     * 公租房增幅
     */
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double addHouse;

    /**
     * 规划用途
     */
    @Column(precision = 2)
    private Constants.LandUse landUse;

    /**
     * 新的规划用途
     */
    @Column(length = 50)
    private String tdytCode;

    /**
     * 新的规划用途名称
     */
    @Column(length = 50)
    private String tdytName;

    /**
     * 保证金缴纳银行
     */
    @Column(length = 100)
    private String banks;

    /**
     * 公告时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date showTime;

    /**
     * 结束时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date overTime;

    /**
     * 成交信息
     */
    @Column
    private String offerId;

    /**
     * 地块坐标Geojson格式
     */
    @Column(nullable = true,columnDefinition ="CLOB")
    private String geometry;

    /**
     * 资源是否显示在大屏幕上
     */
    @Column(nullable = true,columnDefinition ="number(1) default '0'")
    private int displayStatus=0;

    /**
     * 出让单位
     */
    @Column(length = 200)
    private String ownershipUnit;

    /**
     * 交易方式
     */
    @Column(length = 20)
    private Constants.OfferType offerType=Constants.OfferType.LISTING;

    /**
     * 竞价规则
     */
    @Column(nullable = true,columnDefinition ="number(1) default '0'")
    private Constants.BidRule bidRule= Constants.BidRule.JGZD;

    /**
     * 出价方式
     */
    @Column(nullable = true,columnDefinition ="number(1) default '0'")
    private Constants.BidType bidType= Constants.BidType.ZJ_WANYUAN;

 /*   //报价锁地块资源
    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private double currentOffer;//当前价格

    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int currentStatus=0;  //当前状态

    @Temporal(TemporalType.TIMESTAMP)
    private Date currentOverTime; //当前状态到期时间*/

    /**
     * 成交审核Id
     */
    @Column(length = 32)
    @Field("成交审核Id")
    private String resourceVerifyId;


    //==============================苏州园区地块字段调整
    @Column(length = 20)
    @Field("供地方式-00-出让")
    private String dealType;


    /**
     * 地块交易统计
     */
    @Transient
    private int unVerif=0;//未审核//未审核
    @Transient
    private int passed=0;//已通过
    @Transient
    private int unpass=0;//未通过

    @Transient
    private List<TransFile> attachmentList;

    //摇号地块信息
    @Temporal(TemporalType.TIMESTAMP)
    private Date yhBeginTime;//摇号开始时间
    @Column
    private String yhAddress;//摇号地址
    @Column
    private String yhLinkman;//摇号联系人
    @Column
    private String yhPhone;//摇号电话

    @Column(precision = 18,scale =6,columnDefinition ="number(18,6)")
    private Double yhMaxOffer; //摇号最高限价


    @Transient
    @OneToOne
    @JoinColumn(name = "resourceId")
    private TransResourceInfo transResourceInfo;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDealNoticeId() {
        return dealNoticeId;
    }

    public void setDealNoticeId(String dealNoticeId) {
        this.dealNoticeId = dealNoticeId;
    }

    public String getSuspendNoticeId() {
        return suspendNoticeId;
    }

    public void setSuspendNoticeId(String suspendNoticeId) {
        this.suspendNoticeId = suspendNoticeId;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public int getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(int resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public Date getBmBeginTime() {
        return bmBeginTime;
    }

    public void setBmBeginTime(Date bmBeginTime) {
        this.bmBeginTime = bmBeginTime;
    }

    public Date getBmEndTime() {
        return bmEndTime;
    }

    public void setBmEndTime(Date bmEndTime) {
        this.bmEndTime = bmEndTime;
    }

    public Date getGpBeginTime() {
        return gpBeginTime;
    }

    public void setGpBeginTime(Date gpBeginTime) {
        this.gpBeginTime = gpBeginTime;
    }

    public Date getGpEndTime() {
        return gpEndTime;
    }

    public void setGpEndTime(Date gpEndTime) {
        this.gpEndTime = gpEndTime;
    }

    public Date getXsBeginTime() {
        return xsBeginTime;
    }

    public void setXsBeginTime(Date xsBeginTime) {
        this.xsBeginTime = xsBeginTime;
    }

    public Date getYxEndTime() {
        return yxEndTime;
    }

    public void setYxEndTime(Date yxEndTime) {
        this.yxEndTime = yxEndTime;
    }

    public Date getBzjEndTime() {
        return bzjEndTime;
    }

    public void setBzjEndTime(Date bzjEndTime) {
        this.bzjEndTime = bzjEndTime;
    }

    public Double getCrArea() {
        return crArea;
    }

    public void setCrArea(Double crArea) {
        this.crArea = crArea;
    }

    public Double getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(Double buildingArea) {
        this.buildingArea = buildingArea;
    }

    public Double getBeginOffer() {
        return beginOffer;
    }

    public void setBeginOffer(Double beginOffer) {
        this.beginOffer = beginOffer;
    }

    public Double getFixedOffer() {
        return fixedOffer;
    }

    public void setFixedOffer(Double fixedOffer) {
        this.fixedOffer = fixedOffer;
    }

    public Double getAddOffer() {
        return addOffer;
    }

    public void setAddOffer(Double addOffer) {
        this.addOffer = addOffer;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getGgId() {
        return ggId;
    }

    public void setGgId(String ggId) {
        this.ggId = ggId;
    }

    public int getResourceEditStatus() {
        return resourceEditStatus;
    }

    public void setResourceEditStatus(int resourceEditStatus) {
        this.resourceEditStatus = resourceEditStatus;
    }

    public String getBanks() {
        return banks;
    }

    public Integer getBeforeBzjAudit() {
        return beforeBzjAudit;
    }

    public void setBeforeBzjAudit(Integer beforeBzjAudit) {
        this.beforeBzjAudit = beforeBzjAudit;
    }

    public void setBanks(String banks) {
        this.banks = banks;
    }

    public Date getBzjBeginTime() {
        return bzjBeginTime;
    }

    public void setBzjBeginTime(Date bzjBeginTime) {
        this.bzjBeginTime = bzjBeginTime;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public List<TransFile> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<TransFile> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Integer getMaxOfferExist() {
        return maxOfferExist;
    }

    public void setMaxOfferExist(Integer maxOfferExist) {
        this.maxOfferExist = maxOfferExist;
    }

    public Double getMaxOffer() {
        return maxOffer;
    }

    public void setMaxOffer(Double maxOffer) {
        this.maxOffer = maxOffer;
    }

    public Constants.MaxOfferChoose getMaxOfferChoose() {
        return maxOfferChoose;
    }

    public void setMaxOfferChoose(Constants.MaxOfferChoose maxOfferChoose) {
        this.maxOfferChoose = maxOfferChoose;
    }

    public Integer getSuccessOfferChoose() {
        return successOfferChoose;
    }

    public void setSuccessOfferChoose(Integer successOfferChoose) {
        this.successOfferChoose = successOfferChoose;
    }

    public Constants.LandUse getLandUse() {
        return landUse;
    }

    public void setLandUse(Constants.LandUse landUse) {
        this.landUse = landUse;
    }

    public String getTdytCode() {
        return tdytCode;
    }

    public void setTdytCode(String tdytCode) {
        this.tdytCode = tdytCode;
    }

    public String getTdytName() {
        return tdytName;
    }

    public void setTdytName(String tdytName) {
        this.tdytName = tdytName;
    }

    public Integer getMinOffer() {
        return minOffer;
    }

    public void setMinOffer(Integer minOffer) {
        this.minOffer = minOffer;
    }

    public int getOfferUnit() {
        return offerUnit;
    }

    public void setOfferUnit(int offerUnit) {
        this.offerUnit = offerUnit;
    }

    public int getPublicHouse() {
        return publicHouse;
    }

    public void setPublicHouse(int publicHouse) {
        this.publicHouse = publicHouse;
    }

    public Double getBeginHouse() {
        return beginHouse;
    }

    public void setBeginHouse(Double beginHouse) {
        this.beginHouse = beginHouse;
    }

    public Double getAddHouse() {
        return addHouse;
    }

    public void setAddHouse(Double addHouse) {
        this.addHouse = addHouse;
    }

    public int getDisplayStatus() {
        return displayStatus;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public void setDisplayStatus(int displayStatus) {
        this.displayStatus = displayStatus;
    }


    public String getOwnershipUnit() {
        return ownershipUnit;
    }

    public void setOwnershipUnit(String ownershipUnit) {
        this.ownershipUnit = ownershipUnit;
    }

    public Constants.OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(Constants.OfferType offerType) {
        this.offerType = offerType;
    }

    public Constants.BidRule getBidRule() {
        return bidRule;
    }

    public void setBidRule(Constants.BidRule bidRule) {
        this.bidRule = bidRule;
    }

    public Constants.BidType getBidType() {
        return bidType;
    }

    public void setBidType(Constants.BidType bidType) {
        this.bidType = bidType;
    }

    public Double getFixedOfferUsd() {
        return fixedOfferUsd;
    }

    public void setFixedOfferUsd(Double fixedOfferUsd) {
        this.fixedOfferUsd = fixedOfferUsd;
    }

    public Double getFixedOfferHkd() {
        return fixedOfferHkd;
    }

    public void setFixedOfferHkd(Double fixedOfferHkd) {
        this.fixedOfferHkd = fixedOfferHkd;
    }

    public TransResourceInfo getTransResourceInfo() {
        return transResourceInfo;
    }

    public void setTransResourceInfo(TransResourceInfo transResourceInfo) {
        this.transResourceInfo = transResourceInfo;
    }

    public String getResourceVerifyId() {
        return resourceVerifyId;
    }

    public void setResourceVerifyId(String resourceVerifyId) {
        this.resourceVerifyId = resourceVerifyId;
    }

    public int getUnVerif() {
        return unVerif;
    }

    public void setUnVerif(int unVerif) {
        this.unVerif = unVerif;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getUnpass() {
        return unpass;
    }

    public void setUnpass(int unpass) {
        this.unpass = unpass;
    }

    public Date getYhBeginTime() {
        return yhBeginTime;
    }

    public void setYhBeginTime(Date yhBeginTime) {
        this.yhBeginTime = yhBeginTime;
    }

    public String getYhAddress() {
        return yhAddress;
    }

    public void setYhAddress(String yhAddress) {
        this.yhAddress = yhAddress;
    }

    public String getYhLinkman() {
        return yhLinkman;
    }

    public void setYhLinkman(String yhLinkman) {
        this.yhLinkman = yhLinkman;
    }

    public String getYhPhone() {
        return yhPhone;
    }

    public void setYhPhone(String yhPhone) {
        this.yhPhone = yhPhone;
    }

    public Double getYhMaxOffer() {
        return yhMaxOffer;
    }

    public void setYhMaxOffer(Double yhMaxOffer) {
        this.yhMaxOffer = yhMaxOffer;
    }
}
