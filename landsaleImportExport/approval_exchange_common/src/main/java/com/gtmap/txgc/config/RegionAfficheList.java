package com.gtmap.txgc.config;

import java.util.List;

/**
 * 公告类型和行政区
 * Created by liushaoshuai on 2018/1/5.
 */
public class RegionAfficheList {
    //行政区
    List<String> regionList;

    //公告类型 0工业用地公告，1经营性用地公告，2其它公告，3协议出让类（划拨）公告
    List<String> afficheList;

    public List<String> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String> regionList) {
        this.regionList = regionList;
    }

    public List<String> getAfficheList() {
        return afficheList;
    }

    public void setAfficheList(List<String> afficheList) {
        this.afficheList = afficheList;
    }




}
