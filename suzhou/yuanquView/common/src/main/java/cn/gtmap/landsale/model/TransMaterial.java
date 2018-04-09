package cn.gtmap.landsale.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by u on 2016/2/29.
 */
@Entity
@Table(name = "trans_material")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransMaterial implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String materialId;

    @Column(length = 100)
    private String materialName;//材料名称

    @Column(length = 32)
    private String materialCode;//材料代码

    @Column(length = 32)
    private String regionCode;//行政区代码

    @Column(length = 32)
    private String materialType;//材料类型(个人或者集体)

    @Transient
    private String personalMaterialId;

    @Transient
    private String groupMaterialId;

    public String getPersonalMaterialId() {
        return personalMaterialId;
    }

    public void setPersonalMaterialId(String personalMaterialId) {
        this.personalMaterialId = personalMaterialId;
    }

    public String getGroupMaterialId() {
        return groupMaterialId;
    }

    public void setGroupMaterialId(String groupMaterialId) {
        this.groupMaterialId = groupMaterialId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
}
