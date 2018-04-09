package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.naming.Name;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by trr on 2016/7/1.
 */
@Entity
@Table(name = "TRANS_GOODS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransGoods implements Serializable{
    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String goodsType;

    @Column(length = 100)
    private String no;
    @Column(length = 200)
    private String name;
    @Column(length = 50)
    private String trustId;
    @Column(length = 200)
    private  String kind;
    @Column(length = 200)
    private String goodsUse;
    @Column(length = 200)
    private String blemish;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double trustPercent;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double transPercent;
    @Column(nullable = true,precision=18,scale=2,columnDefinition="number(18,2) default '0'")
    private Double trustPrice;
    @Column(length = 50)
    private String createOrganId;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Date createDate;
    @Column(length = 100)
    private String remark;
    @Column(length = 50)
    private String cantonId;
    @Column(length = 50)
    private String code;
    @Column(length = 500)
    private String address;
    @Column(length = 100)
    private String area;
    @Column(length = 50)
    private String storage;
    @Column(length = 50)
    private String zdguid;

    @Transient
    private String address2Extend;//地块扩展字段，用于2块土地合并
    @Transient
    private Double area2Extend;
    @Transient
    private String landUse2Extend;

    public String getAddress2Extend() {
        return address2Extend;
    }

    public void setAddress2Extend(String address2Extend) {
        this.address2Extend = address2Extend;
    }

    public Double getArea2Extend() {
        return area2Extend;
    }

    public void setArea2Extend(Double area2Extend) {
        this.area2Extend = area2Extend;
    }

    public String getLandUse2Extend() {
        return landUse2Extend;
    }

    public void setLandUse2Extend(String landUse2Extend) {
        this.landUse2Extend = landUse2Extend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrustId() {
        return trustId;
    }

    public void setTrustId(String trustId) {
        this.trustId = trustId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGoodsUse() {
        return goodsUse;
    }

    public void setGoodsUse(String goodsUse) {
        this.goodsUse = goodsUse;
    }

    public String getBlemish() {
        return blemish;
    }

    public void setBlemish(String blemish) {
        this.blemish = blemish;
    }

    public Double getTrustPercent() {
        return trustPercent;
    }

    public void setTrustPercent(Double trustPercent) {
        this.trustPercent = trustPercent;
    }

    public Double getTransPercent() {
        return transPercent;
    }

    public void setTransPercent(Double transPercent) {
        this.transPercent = transPercent;
    }

    public Double getTrustPrice() {
        return trustPrice;
    }

    public void setTrustPrice(Double trustPrice) {
        this.trustPrice = trustPrice;
    }

    public String getCreateOrganId() {
        return createOrganId;
    }

    public void setCreateOrganId(String createOrganId) {
        this.createOrganId = createOrganId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCantonId() {
        return cantonId;
    }

    public void setCantonId(String cantonId) {
        this.cantonId = cantonId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getZdguid() {
        return zdguid;
    }

    public void setZdguid(String zdguid) {
        this.zdguid = zdguid;
    }
}
