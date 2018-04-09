package cn.gtmap.landsale.model;



import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * 网上交易出让地块报价实体对象
 * Created by jiff on 14/12/14.
 */
public class TransResourceOffer implements Serializable,Comparable<TransResourceOffer> {
    private String offerId;

    private String resourceId;

    private String userName;

    private Double offerPrice;

    private long offerTime;

    private int offerType;

    private int offerNum;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getOfferNum() {
        return offerNum;
    }

    public void setOfferNum(int offerNum) {
        this.offerNum = offerNum;
    }

    public int compareTo(TransResourceOffer resourceOffer) {
        return this.getOfferTime()>resourceOffer.getOfferTime() ? 0:1 ;
    }
}
