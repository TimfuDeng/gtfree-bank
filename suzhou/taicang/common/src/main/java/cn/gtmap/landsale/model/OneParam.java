package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 一次报价参数列表
 * Created by jiff on 14/12/24.
 */
@Entity
@Table(name = "one_param")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneParam implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 50)
    private String id;

    @Column
    private int waitTime;
    @Column
    private int queryTime;
    @Column
    private int priceTime;
    @Column
    private int percentMin;
    @Column
    private int percentMax;
    @Column
    private Date createDate;
    @Column(length = 50)
    private String createUserId;
    @Column(length = 50)
    private String extend1;
    @Column(length = 50)
    private String extend2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(int queryTime) {
        this.queryTime = queryTime;
    }

    public int getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(int priceTime) {
        this.priceTime = priceTime;
    }

    public int getPercentMin() {
        return percentMin;
    }

    public void setPercentMin(int percentMin) {
        this.percentMin = percentMin;
    }

    public int getPercentMax() {
        return percentMax;
    }

    public void setPercentMax(int percentMax) {
        this.percentMax = percentMax;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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
