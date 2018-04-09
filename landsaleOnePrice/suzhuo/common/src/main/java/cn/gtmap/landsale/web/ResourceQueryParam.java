package cn.gtmap.landsale.web;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 客户端查询对象
 * Created by jiff on 15/5/6.
 */
public class ResourceQueryParam implements Serializable {
    String title;
    int resourceStatus;
    int resourceEditStatus;
    String regionCode;
    String useType;
    int orderBy=0;    //面积：1-表示正序 2-表示倒序  价格：3-表示正序 4-表示倒序
    int displayStatus;
    int gtResourceStatus;
    int gtResourceEditStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(int resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public int getResourceEditStatus() {
        return resourceEditStatus;
    }

    public void setResourceEditStatus(int resourceEditStatus) {
        this.resourceEditStatus = resourceEditStatus;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(int displayStatus) {
        this.displayStatus = displayStatus;
    }

    public int getGtResourceStatus() {
        return gtResourceStatus;
    }

    public void setGtResourceStatus(int gtResourceStatus) {
        this.gtResourceStatus = gtResourceStatus;
    }

    public int getGtResourceEditStatus() {
        return gtResourceEditStatus;
    }

    public void setGtResourceEditStatus(int gtResourceEditStatus) {
        this.gtResourceEditStatus = gtResourceEditStatus;
    }

    public String getHashCode(){
        return getCode(title) + ","
                +resourceStatus + ","
                +resourceEditStatus + ","
                +getCode(regionCode) + ","
                +getCode(useType) + ","
                +orderBy + ","
                +displayStatus + ","
                +gtResourceStatus + ","
                +gtResourceEditStatus ;
    }

    private int getCode(String value){
        return StringUtils.isBlank(value)?0:value.hashCode();
    }
}
