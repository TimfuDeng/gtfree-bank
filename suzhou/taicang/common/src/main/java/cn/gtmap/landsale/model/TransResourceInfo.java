package cn.gtmap.landsale.model;

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

    //办公与服务设施用地比例（%）
    @Column(length = 50)
    private String officeRatio;

    //投资强度：
    @Column(length = 50)
    private String investmentIntensity;

    //建设内容
    @Column(length = 500)
    private String constructContent;

    //建设内容
    @Column(length = 500)
    private String constructRequirement;

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

    public String getConstructRequirement() {
        return constructRequirement;
    }

    public void setConstructRequirement(String constructRequirement) {
        this.constructRequirement = constructRequirement;
    }
}
