package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 出让地块,扩展信息
 * Created by Jibo on 2015/5/18.
 */
@Entity
@Table(name = "trans_resource_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransResourceInfo implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String infoId;

    @NaturalId
    @Column(length = 32)
    private String resourceId;

    //建筑面积（平方米）
    @Column(length = 50)
    private String buildingArea;

    //出让年限（年）
    @Column(length = 10)
    private String yearCount;

    //办公与服务设施用地比例（%）
    @Column(length = 50)
    private String officeRatio;

    //投资强度：
    @Column(length = 50)
    private String investmentIntensity;

    //建设内容
    @Column(length = 500)
    private String constructContent;



    //======================苏州土地地块调整字段
    //容积率：
    @Column(length = 50)
    private String plotRatio;

    //绿化率：
    @Column(length = 50)
    private String greeningRate;

    //建筑密度：
    @Column(length = 50)
    private String buildingDensity;

    //建筑限高（米）
    @Column(length = 50)
    private String buildingHeight;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("容积率下限")
    private Double minRjl;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("容积率上限")
    private Double maxRjl;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("建筑密度下限")
    private Double minJzMd;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("建筑密度上限")
    private Double maxJzMd;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("绿化率下限")
    private Double minLhl;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("绿化率上限")
    private Double maxLhl;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("建筑限高下限")
    private Double minJzxg;

    @Column(precision = 18,scale = 3,columnDefinition = "number(18,3) default '0'")
    @Field("建筑限高上限")
    private Double maxJzxg;


    //========================苏州土地地块附加信息字段调整

    @Column(precision = 18,scale = 6,columnDefinition = "number(18,6) default '0'")
    @Field("代征面积")
    private Double daizhArea;


    @Column(precision=18,scale=6,columnDefinition = "number(18,6) default '0'")
    @Field("商业建筑面积")
    private Double shconArea;

    @Column(precision = 18,scale = 6,columnDefinition = "number(18,6) default '0'")
    @Field("住宅建筑面积")
    private Double zzconArea;

    @Column(precision = 18,scale = 6,columnDefinition = "number(18,6) default '0'")
    @Field("办公建筑面积")
    private Double bgconArea;

    @Column(length = 1500)
    @Field("现状土地条件-土地交付条件")
    private String deliverTerm;

    @Column(length = 1500)
    @Field("备注")
    private String memo;


    @Column(length = 1500)
    @Field("周边环境")
    private String surroundings;

    @Column(length = 1500)
    @Field("规划要点")
    private String layoutOutline;

    @Column(length = 1500)
    @Field("地块介绍")
    private String description;

    @Column(length = 50)
    @Field("约定土地交易条件")
    private String tdTjXx;

    @Column(length = 250)
    @Field("场地平整")
    private String cdPz;

    @Column(length = 500)
    @Field("基础设施")
    private String jcSs;

    @Column(length = 5)
    @Field("容积率下限关系符号：00-小于，01-小于等于，02-大于，03大于等于")
    private String selrjl;

    @Column(length = 5)
    @Field("容积率上限关系符号：00-小于，01-小于等于，02-大于，03大于等于")
    private String selrj2;

    @Column(length = 5)
    @Field("建筑密度下限标识：00-小于，01-小于等于，02-大于，03大于等于")
    private String minJzMdTag;

   @Column(length = 5)
   @Field("建筑密度上限标识：00-小于，01-小于等于，02-大于，03大于等于")
   private String maxJzMdTag;

    @Column(length = 5)
    @Field("绿化率下限标识：00-小于，01-小于等于，02-大于，03大于等于")
    private String minLhlTag;

   @Column(length = 5)
   @Field("绿化率上限标识：00-小于，01-小于等于，02-大于，03大于等于")
   private String maxLhlTag;

   @Column(length = 5)
   @Field("建筑限高下限标识：00-小于，01-小于等于，02-大于，03大于等于")
   private String  minJzXgTag;

   @Column(length = 5)
   @Field("建筑限高上限标识：00-小于，01-小于等于，02-大于，03大于等于")
   private String maxJzXgTag;

    /**
     *@annotation 添加产业类型
     *@author liushaoshuai【liushaoshuai@gtmap.cn】
     *@date 2017/5/18 21:43
     *@param
     *@return
     */
    @Column(length = 300)
    @Field("产业用地")
    private String industryType;

    public Double getMinRjl() {
        return minRjl;
    }

    public void setMinRjl(Double minRjl) {
        this.minRjl = minRjl;
    }

    public Double getMaxRjl() {
        return maxRjl;
    }

    public void setMaxRjl(Double maxRjl) {
        this.maxRjl = maxRjl;
    }

    public Double getMinJzMd() {
        return minJzMd;
    }

    public void setMinJzMd(Double minJzMd) {
        this.minJzMd = minJzMd;
    }

    public Double getMaxJzMd() {
        return maxJzMd;
    }

    public void setMaxJzMd(Double maxJzMd) {
        this.maxJzMd = maxJzMd;
    }

    public Double getMinLhl() {
        return minLhl;
    }

    public void setMinLhl(Double minLhl) {
        this.minLhl = minLhl;
    }

    public Double getMaxLhl() {
        return maxLhl;
    }

    public void setMaxLhl(Double maxLhl) {
        this.maxLhl = maxLhl;
    }

    public Double getMinJzxg() {
        return minJzxg;
    }

    public void setMinJzxg(Double minJzxg) {
        this.minJzxg = minJzxg;
    }

    public Double getMaxJzxg() {
        return maxJzxg;
    }

    public void setMaxJzxg(Double maxJzxg) {
        this.maxJzxg = maxJzxg;
    }

    public Double getDaizhArea() {
        return daizhArea;
    }

    public void setDaizhArea(Double daizhArea) {
        this.daizhArea = daizhArea;
    }

    public Double getShconArea() {
        return shconArea;
    }

    public void setShconArea(Double shconArea) {
        this.shconArea = shconArea;
    }

    public Double getZzconArea() {
        return zzconArea;
    }

    public void setZzconArea(Double zzconArea) {
        this.zzconArea = zzconArea;
    }

    public Double getBgconArea() {
        return bgconArea;
    }

    public void setBgconArea(Double bgconArea) {
        this.bgconArea = bgconArea;
    }

    public String getDeliverTerm() {
        return deliverTerm;
    }

    public void setDeliverTerm(String deliverTerm) {
        this.deliverTerm = deliverTerm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(String surroundings) {
        this.surroundings = surroundings;
    }

    public String getLayoutOutline() {
        return layoutOutline;
    }

    public void setLayoutOutline(String layoutOutline) {
        this.layoutOutline = layoutOutline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTdTjXx() {
        return tdTjXx;
    }

    public void setTdTjXx(String tdTjXx) {
        this.tdTjXx = tdTjXx;
    }

    public String getCdPz() {
        return cdPz;
    }

    public void setCdPz(String cdPz) {
        this.cdPz = cdPz;
    }

    public String getJcSs() {
        return jcSs;
    }

    public void setJcSs(String jcSs) {
        this.jcSs = jcSs;
    }

    public String getSelrjl() {
        return selrjl;
    }

    public void setSelrjl(String selrjl) {
        this.selrjl = selrjl;
    }

    public String getSelrj2() {
        return selrj2;
    }

    public void setSelrj2(String selrj2) {
        this.selrj2 = selrj2;
    }

    public String getMinJzMdTag() {
        return minJzMdTag;
    }

    public void setMinJzMdTag(String minJzMdTag) {
        this.minJzMdTag = minJzMdTag;
    }

    public String getMaxJzMdTag() {
        return maxJzMdTag;
    }

    public void setMaxJzMdTag(String maxJzMdTag) {
        this.maxJzMdTag = maxJzMdTag;
    }

    public String getMinLhlTag() {
        return minLhlTag;
    }

    public void setMinLhlTag(String minLhlTag) {
        this.minLhlTag = minLhlTag;
    }

    public String getMaxLhlTag() {
        return maxLhlTag;
    }

    public void setMaxLhlTag(String maxLhlTag) {
        this.maxLhlTag = maxLhlTag;
    }

    public String getMinJzXgTag() {
        return minJzXgTag;
    }

    public void setMinJzXgTag(String minJzXgTag) {
        this.minJzXgTag = minJzXgTag;
    }

    public String getMaxJzXgTag() {
        return maxJzXgTag;
    }

    public void setMaxJzXgTag(String maxJzXgTag) {
        this.maxJzXgTag = maxJzXgTag;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getYearCount() {
        return yearCount;
    }

    public void setYearCount(String yearCount) {
        this.yearCount = yearCount;
    }

    public String getPlotRatio() {
        return plotRatio;
    }

    public void setPlotRatio(String plotRatio) {
        this.plotRatio = plotRatio;
    }

    public String getGreeningRate() {
        return greeningRate;
    }

    public void setGreeningRate(String greeningRate) {
        this.greeningRate = greeningRate;
    }

    public String getBuildingDensity() {
        return buildingDensity;
    }

    public void setBuildingDensity(String buildingDensity) {
        this.buildingDensity = buildingDensity;
    }

    public String getBuildingHeight() {
        return buildingHeight;
    }

    public void setBuildingHeight(String buildingHeight) {
        this.buildingHeight = buildingHeight;
    }

    public String getOfficeRatio() {
        return officeRatio;
    }

    public void setOfficeRatio(String officeRatio) {
        this.officeRatio = officeRatio;
    }

    public String getInvestmentIntensity() {
        return investmentIntensity;
    }

    public void setInvestmentIntensity(String investmentIntensity) {
        this.investmentIntensity = investmentIntensity;
    }

    public String getConstructContent() {
        return constructContent;
    }

    public void setConstructContent(String constructContent) {
        this.constructContent = constructContent;
    }
}
