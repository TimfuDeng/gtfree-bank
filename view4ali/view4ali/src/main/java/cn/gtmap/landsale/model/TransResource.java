package cn.gtmap.landsale.model;



import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网上交易出让地块实体对象
 * Created by jiff on 14/12/14.
 */
public class TransResource implements Serializable {
    private String resourceId;

    private String ggId;

    private String resourceCode;

    private String resourceLocation;

    private String regionCode;  //所属行政区

    private int resourceType;   //资源类别

    private Date bmBeginTime;  //报名开始时间

    private Date bmEndTime;  //报名截至时间

    private Date gpBeginTime;  //挂牌开始时间

    private Date gpEndTime;  //挂牌截至时间

    private Date bzjBeginTime;  //保证金开始时间

    private Date bzjEndTime;  //保证金截至时间

    private Date xsBeginTime;  //限时开始时间

    private Double crArea; //出让面积

    private Double beginOffer; //起始价(万元)

    private Double fixedOffer; //保证金(万元)

    private Double addOffer; //竞价增价幅度(万元)

    private String landUse; //规划用途

    private String geometry; //地块坐标Geojson格式

    private Map attachments; //附件材料

    private Map others; //其他信息

    private String transUser;  //成交人

    private String overStatus;   //结束状态

    private double rjl; //计算容积率

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getGgId() {
        return ggId;
    }

    public void setGgId(String ggId) {
        this.ggId = ggId;
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

    public Date getBzjBeginTime() {
        return bzjBeginTime;
    }

    public void setBzjBeginTime(Date bzjBeginTime) {
        this.bzjBeginTime = bzjBeginTime;
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

    public String getLandUse() {
        return landUse;
    }

    public void setLandUse(String landUse) {
        this.landUse = landUse;
    }


    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public Map getAttachments() {
        return attachments;
    }

    public void setAttachments(Map attachments) {
        this.attachments = attachments;
    }

    public Map getOthers() {
        return others;
    }

    public void setOthers(Map others) {
        this.others = others;
    }

    public String getTransUser() {
        return transUser;
    }

    public void setTransUser(String transUser) {
        this.transUser = transUser;
    }

    public Date getXsBeginTime() {
        return xsBeginTime;
    }

    public void setXsBeginTime(Date xsBeginTime) {
        this.xsBeginTime = xsBeginTime;
    }

    public String getOverStatus() {
        return overStatus;
    }

    public void setOverStatus(String overStatus) {
        this.overStatus = overStatus;
    }

    public double getRjl() {
        return rjl;
    }

    public void setRjl(double rjl) {
        this.rjl = rjl;
    }
}
