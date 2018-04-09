package cn.gtmap.landsale.common.web;


import cn.gtmap.egovplat.core.data.Pageable;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 客户端查询对象
 * @author jiff on 15/5/6.
 */
public class ResourceQueryParam implements Serializable {
    String title;
    int resourceStatus;
    int resourceEditStatus;
    String regionCode;
    String useType;

    /**
     * 面积：1-表示正序 2-表示倒序  价格：3-表示正序 4-表示倒序
     */
    int orderBy=0;
    int displayStatus;
    int gtResourceStatus;
    int gtResourceEditStatus;
    Pageable page;
    String tdytDictIds;
    String tdytDictCodes;

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }

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

    public String getTdytDictIds() {
        return tdytDictIds;
    }

    public void setTdytDictIds(String tdytDictIds) {
        this.tdytDictIds = tdytDictIds;
    }

    public String getTdytDictCodes() {
        return tdytDictCodes;
    }

    public void setTdytDictCodes(String tdytDictCodes) {
        this.tdytDictCodes = tdytDictCodes;
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
        return StringUtils.isEmpty(value)?0:value.hashCode();
    }
}
