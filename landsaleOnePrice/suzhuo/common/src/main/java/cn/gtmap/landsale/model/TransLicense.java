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
 * Created by trr on 2016/7/12.
 */
@Entity
@Table(name = "TRANS_LICENSE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransLicense implements Serializable{

    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String bidderId;
    @Column(length = 50)
    private String targetId;
    @Column(length = 50)
    private String licenseNo;
    @Column
    private Date applyDate;
    @Column
    private Integer cardNo;
    @Column(length = 50)
    private String applyNo;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double trustPrice;
    @Column(length = 50)
    private String trustType;
    @Column(length = 50)
    private String transNo;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double transPrice;
    @Column
    private Date transDate;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer confirmed;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer earnestMoneyPay;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer rejected;
    @Column
    private Integer status;
    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private Integer isValid;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Date createDate;
    @Column(length = 100)
    private String remark;
    @Column
    private Date confirmedDate;
    @Column(length = 4000)
    private String confirmedOpinion;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Double getTrustPrice() {
        return trustPrice;
    }

    public void setTrustPrice(Double trustPrice) {
        this.trustPrice = trustPrice;
    }

    public String getTrustType() {
        return trustType;
    }

    public void setTrustType(String trustType) {
        this.trustType = trustType;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Double getTransPrice() {
        return transPrice;
    }

    public void setTransPrice(Double transPrice) {
        this.transPrice = transPrice;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getEarnestMoneyPay() {
        return earnestMoneyPay;
    }

    public void setEarnestMoneyPay(Integer earnestMoneyPay) {
        this.earnestMoneyPay = earnestMoneyPay;
    }

    public Integer getRejected() {
        return rejected;
    }

    public void setRejected(Integer rejected) {
        this.rejected = rejected;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public String getConfirmedOpinion() {
        return confirmedOpinion;
    }

    public void setConfirmedOpinion(String confirmedOpinion) {
        this.confirmedOpinion = confirmedOpinion;
    }
}
