package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 网上交易出让地块报价实体对象
 * @author jiff on 14/12/14.
 */
@Entity
@Table(name = "trans_resource_offer")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransResourceOffer implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String offerId;

    @Column(nullable = false,length = 32)
    private String resourceId;

    @Column(nullable = false,length =32)
    private String userId;

    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double offerPrice;

    @Column(nullable = false,columnDefinition ="number(18,0) default '0'")
    private long offerTime;

    @Column(nullable = false)
    private int offerType;

    /**
     * 溢价率
     */
    @Transient
    private BigDecimal premiumRate;

    /**
     * 平均地价
     */
    @Transient
    private BigDecimal avgPrice;

    /**
     * 楼门价
     */
    @Transient
    private BigDecimal gatePrice;

    /**
     * 总价
     */
    @Transient
    private BigDecimal totalPrice;

    @Transient
    private boolean maxOffer;

    public boolean isMaxOffer() {
        return maxOffer;
    }

    public void setMaxOffer(boolean maxOffer) {
        this.maxOffer = maxOffer;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Double offerPrice) {
        this.offerPrice = offerPrice;
    }

    public long getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(long offerTime) {
        this.offerTime = offerTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getOfferType() {
        return offerType;
    }

    public void setOfferType(int offerType) {
        this.offerType = offerType;
    }

    public BigDecimal getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(BigDecimal premiumRate) {
        this.premiumRate = premiumRate;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getGatePrice() {
        return gatePrice;
    }

    public void setGatePrice(BigDecimal gatePrice) {
        this.gatePrice = gatePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
