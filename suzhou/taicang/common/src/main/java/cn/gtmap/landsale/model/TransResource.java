package cn.gtmap.landsale.model;


import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网上交易出让地块实体对象
 * Created by jiff on 14/12/14.
 */
@Entity
@Table(name = "trans_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransResource implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String resourceId;

    @Column(length = 32)
    private String ggId;

    @Column(nullable = false,length = 50)
    private String resourceCode;

    @Column(nullable = false)
    private String resourceLocation;

    @Column(length = 50)
    private String regionCode;  //所属行政区

    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int resourceEditStatus=0;  //资源编辑状态

    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int resourceStatus;

    @Column(nullable = false,columnDefinition ="number(2) default '0'")
    private int resourceType;   //资源类别

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date bmBeginTime;  //报名开始时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date bmEndTime;  //报名截至时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date gpBeginTime;  //挂牌开始时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date gpEndTime;  //挂牌截至时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date bzjBeginTime;  //保证金开始时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date bzjEndTime;  //保证金截至时间

    @Column(precision = 1, nullable = false)
    private Constants.QualificationType qualificationType = Constants.QualificationType.PRE_TRIAL;//资格前审或资格后审,默认资格前审

    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double crArea; //出让面积

    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double beginOffer; //起始价(万元)

    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double fixedOffer; //保证金(万元)

    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double fixedOfferUsd; //保证金美元(美元)

    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double addOffer; //竞价增价幅度(万元)

    @Column(precision = 18,scale =6,columnDefinition ="number(18,6)")
    private Double maxOffer; //最高限价

    @Column
    private boolean minOffer; //是否有底价

    @Column(nullable = false,columnDefinition ="number(1) default '0'")
    private int offerUnit;  //报价单位，0-万元（总价） 1-元/平方米 2-万元/亩

    @Column(columnDefinition ="number(1) default '0'")
    private int publicHouse;  //公租房竞价类型 0-平方米（面积） 1-万元（资金）

    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double beginHouse; //起始公租房

    @Column(precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double addHouse; //公租房增幅

    @Column(precision = 2)
    private Constants.LandUse landUse; //规划用途

    @Column(length = 100)
    private String banks;  //保证金缴纳银行

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date showTime; //公告时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date overTime; //结束时间

    @Column
    private String offerId;  //成交信息

    @Column(nullable = true,columnDefinition ="CLOB")
    private String geometry; //地块坐标Geojson格式

    @Column(nullable = true,columnDefinition ="number(1) default '0'")
    private int displayStatus=0;  //资源是否显示在大屏幕上

    @Column(length = 200)
    private String ownershipUnit; //出让单位

    @Column(length = 20)
    private Constants.OfferType offerType=Constants.OfferType.LISTING; //交易方式

    @Column(nullable = true,columnDefinition ="number(1) default '0'")
    private Constants.BidRule bidRule= Constants.BidRule.JGZD; //竞价规则

    @Column(nullable = true,columnDefinition ="number(1) default '0'")
    private Constants.BidType bidType= Constants.BidType.ZJ_WANYUAN;//出价方式

	@Column(nullable = true,columnDefinition ="number(6) default '0'")
    private int passedTrialNum;   //资格审核通过人数
    
    @Column(nullable = true,columnDefinition ="number(6) default '0'")
    private int pendingTrialNum;   //待审核人数
    
    @Column(nullable = true,columnDefinition ="number(6) default '0'")
    private int bailPaidNum;   //已缴纳保证金人数
    
    @Column(nullable = true,columnDefinition ="number(6) default '0'")
    private int enrolledNum;   //已申请人数
    
    @Column(nullable = true,columnDefinition ="number(6) default '0'")
    private int enrolledOfferNum;   //已报名人数（报名且报价）

    @Column(nullable = true,columnDefinition ="number(6) default '240'")
    private Integer countDownSec;   //已报名人数（报名且报价）

	@Transient
    private List<TransFile> attachmentList;

    @Transient
    private List<TransResourceInfo> transResourceInfoList;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

    public Date getBzjEndTime() {
        return bzjEndTime;
    }

    public void setBzjEndTime(Date bzjEndTime) {
        this.bzjEndTime = bzjEndTime;
    }

    public Constants.QualificationType getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(Constants.QualificationType qualificationType) {
        this.qualificationType = qualificationType;
    }

    public Double getCrArea() {
        return crArea;
    }

    public void setCrArea(Double crArea) {
        this.crArea = crArea;
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

    public Double getMaxOffer() {
        return maxOffer;
    }

    public void setMaxOffer(Double maxOffer) {
        this.maxOffer = maxOffer;
    }

    public Constants.LandUse getLandUse() {
        return landUse;
    }

    public void setLandUse(Constants.LandUse landUse) {
        this.landUse = landUse;
    }

    public boolean isMinOffer() {
        return minOffer;
    }

    public void setMinOffer(boolean minOffer) {
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

    public void setDisplayStatus(int displayStatus) {
        this.displayStatus = displayStatus;
    }

    public List<TransResourceInfo> getTransResourceInfoList() {
        return transResourceInfoList;
    }

    public void setTransResourceInfoList(List<TransResourceInfo> transResourceInfoList) {
        this.transResourceInfoList = transResourceInfoList;
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
    
    public int getPassedTrialNum() {
		return passedTrialNum;
	}

	public void setPassedTrialNum(int passedTrialNum) {
		this.passedTrialNum = passedTrialNum;
	}

	public int getPendingTrialNum() {
		return pendingTrialNum;
	}

	public void setPendingTrialNum(int pendingTrialNum) {
		this.pendingTrialNum = pendingTrialNum;
	}

	public int getBailPaidNum() {
		return bailPaidNum;
	}

	public void setBailPaidNum(int bailPaidNum) {
		this.bailPaidNum = bailPaidNum;
	}

	public int getEnrolledNum() {
		return enrolledNum;
	}

	public void setEnrolledNum(int enrolledNum) {
		this.enrolledNum = enrolledNum;
	}
	
    public int getEnrolledOfferNum() {
		return enrolledOfferNum;
	}

	public void setEnrolledOfferNum(int enrolledOfferNum) {
		this.enrolledOfferNum = enrolledOfferNum;
	}

    public Integer getCountDownSec() {
        return countDownSec;
    }

    public void setCountDownSec(Integer countDownSec) {
        this.countDownSec = countDownSec;
    }
}
