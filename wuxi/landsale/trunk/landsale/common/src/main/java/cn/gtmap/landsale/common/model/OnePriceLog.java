package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 一次报价竞买流程列表
 * @author jiff on 14/12/24.
 */
@Entity
@Table(name = "one_price_log")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OnePriceLog implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 50)
    private String id;
    @Column(length = 32)
    private String applyId;
    @Column(length = 50)
    private String transUserId;
    @Column
    private Long price;
    @Column
    private Date priceDate;
    @Column(length = 200)
    private String priceUnit;
    @Column
    private Date createDate;
    @Column(length = 50)
    private  String transResourceId;//原来标的Id

    @Column
    private String extend1;
    @Column
    private  String extend2;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getTransUserId() {
        return transUserId;
    }

    public void setTransUserId(String transUserId) {
        this.transUserId = transUserId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTransResourceId() {
        return transResourceId;
    }

    public void setTransResourceId(String transResourceId) {
        this.transResourceId = transResourceId;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }
}
