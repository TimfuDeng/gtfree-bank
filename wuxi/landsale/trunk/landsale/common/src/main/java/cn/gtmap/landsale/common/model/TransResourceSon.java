package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.common.Constants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地块多用途信息
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@Entity
@Table(name="trans_resource_son")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransResourceSon implements Serializable{

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid",strategy = UUIDHexGenerator.TYPE)
    private String sonId;

    @Column
    @Field("地块编号")
    private String resourceId;

    @Column(precision = 2)
    @Field("地块多用途")
    private Constants.LandUse sonLandUse;

    @Column(length = 50)
    @Field("用途")
    private String tdytCode; //新的规划用途

    @Column(length = 50)
    @Field("用途名称")
    private String tdytName; //用途名称

    @Column
    @Field("年限")
    private String sonYearCount;

    @Column
    @Field("宗地号")
    private String zdCode;

    @Column
    @Field("宗地面积")
    private String zdArea;

    //======================苏州土地地块调整字段
    /**
     * 容积率：
     */
    @Column(length = 50)
    private String plotRatio;

    /**
     * 绿化率：
     */
    @Column(length = 50)
    private String greeningRate;

    /**
     * 建筑密度：
     */
    @Column(length = 50)
    private String buildingDensity;

    /**
     * 建筑限高（米）
     */
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

    @Column(length = 5)
    @Field("容积率下限关系符号：00-小于，01-小于等于，02-大于，03大于等于")
    private String minRjlTag;

    @Column(length = 5)
    @Field("容积率上限关系符号：00-小于，01-小于等于，02-大于，03大于等于")
    private String maxRjlTag;

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

    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Constants.LandUse getSonLandUse() {
        return sonLandUse;
    }

    public void setSonLandUse(Constants.LandUse sonLandUse) {
        this.sonLandUse = sonLandUse;
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

    public String getSonYearCount() {
        return sonYearCount;
    }

    public void setSonYearCount(String sonYearCount) {
        this.sonYearCount = sonYearCount;
    }

    public String getZdCode() {
        return zdCode;
    }

    public void setZdCode(String zdCode) {
        this.zdCode = zdCode;
    }

    public String getZdArea() {
        return zdArea;
    }

    public void setZdArea(String zdArea) {
        this.zdArea = zdArea;
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

    public String getMinRjlTag() {
        return minRjlTag;
    }

    public void setMinRjlTag(String minRjlTag) {
        this.minRjlTag = minRjlTag;
    }

    public String getMaxRjlTag() {
        return maxRjlTag;
    }

    public void setMaxRjlTag(String maxRjlTag) {
        this.maxRjlTag = maxRjlTag;
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
}
