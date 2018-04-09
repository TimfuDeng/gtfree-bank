package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 地块多用途信息
 * Created by trr on 2015/12/17.
 */
@Entity
@Table(name="trans_resource_son")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransResourceSon implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid",strategy = UUIDHexGenerator.TYPE)
    private String sonId;

    @Column(precision = 2)
    @Field("地块多用途")
    private Constants.LandUse sonLandUse;

    @Column(length = 50)
    private String sonLandUseMuli; //新的规划用途

    @Column
    @Field("年限")
    private String sonYearCount;

    @Column
    @Field("宗地号")
    private String zdCode;


    @Column
    @Field("宗地面积")
    private String zdArea;

    @Column
    @Field("地块编号")
    private String resourceId;


//    @ManyToOne(cascade={CascadeType.ALL})
//    @JoinColumn(name="resourceId")
//    private TransResource transResource;


    public String getSonLandUseMuli() {
        return sonLandUseMuli;
    }

    public void setSonLandUseMuli(String sonLandUseMuli) {
        this.sonLandUseMuli = sonLandUseMuli;
    }

    public String getZdArea() {
        return zdArea;
    }

    public void setZdArea(String zdArea) {
        this.zdArea = zdArea;
    }

    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }

    public Constants.LandUse getSonLandUse() {
        return sonLandUse;
    }

    public void setSonLandUse(Constants.LandUse sonLandUse) {
        this.sonLandUse = sonLandUse;
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

//    public TransResource getTransResource() {
//        return transResource;
//    }
//
//    public void setTransResource(TransResource transResource) {
//        this.transResource = transResource;
//    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
