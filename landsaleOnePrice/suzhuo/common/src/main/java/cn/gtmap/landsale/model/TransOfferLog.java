package cn.gtmap.landsale.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2016/7/19.
 */
@Entity
@Table(name = "TRANS_OFFER_LOG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransOfferLog implements Serializable{
    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String licenseId;
    @Column(length = 50)
    private String multiTradeId;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private  Double price;
    @Column
    private Date offerDate;
    @Column(length = 50)
    private String confirmUserId;
    @Column
    private Date confirmDate;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer status;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Date createDate;
    @Column(length = 100)
    private String remark;
    @Column
    private Integer increase;
    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private Integer type;
    @Column(length = 100)
    private String enPrice;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isTrust;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer kind;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getMultiTradeId() {
        return multiTradeId;
    }

    public void setMultiTradeId(String multiTradeId) {
        this.multiTradeId = multiTradeId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(Date offerDate) {
        this.offerDate = offerDate;
    }

    public String getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getIncrease() {
        return increase;
    }

    public void setIncrease(Integer increase) {
        this.increase = increase;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEnPrice() {
        return enPrice;
    }

    public void setEnPrice(String enPrice) {
        this.enPrice = enPrice;
    }

    public Integer getIsTrust() {
        return isTrust;
    }

    public void setIsTrust(Integer isTrust) {
        this.isTrust = isTrust;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }
}
