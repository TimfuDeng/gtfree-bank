package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.common.format.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 一次报价标的
 * @author jiff on 14/12/24.
 */
@Entity
@Table(name = "one_target")
public class OneTarget implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 50)
    private String id;
    @Column(length = 100)
    private String transNo;//标的名称
    @Column(length = 500)
    private String transName;//标的名称
    @Column(length = 50,nullable = false,unique = true)
    private String transResourceId;//标的Id
    @Column(length = 500)
    private String transAddress;//标的地址
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date stopDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date waitBeginDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date waitEndDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date queryBeginDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date queryEndDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date priceBeginDate;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date priceEndDate;
    @Column
    private Long priceMin;//最小中止金额
    @Column
    private Long priceMax;
    @Column(precision = 18,scale =2,columnDefinition ="number(18,2) default '0'")
    private Double priceAvg;//成交平均价
    @Column(length = 50)
    private String transUserId;//成交者id
    @Column
    private Date createDate;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Long priceGuid;//市场指导价
    @Column(columnDefinition ="number(1) default '0'")
    private Integer isStop;//0-位发布 1-已发布
    @Column(length = 100)
    private String transArea;
    @Column(length = 200)
    private String transUseLand;

    @Column(length = 200)
    private String successUnit;
    @Column
    private Long successPrice;

    @Column(length = 50)
    private String extend1;
    @Column(length = 50)
    private String extend2;



    public String getSuccessUnit() {
        return successUnit;
    }

    public void setSuccessUnit(String successUnit) {
        this.successUnit = successUnit;
    }

    public Long getSuccessPrice() {
        return successPrice;
    }

    public void setSuccessPrice(Long successPrice) {
        this.successPrice = successPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getTransResourceId() {
        return transResourceId;
    }

    public void setTransResourceId(String transResourceId) {
        this.transResourceId = transResourceId;
    }

    public String getTransAddress() {
        return transAddress;
    }

    public void setTransAddress(String transAddress) {
        this.transAddress = transAddress;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public Date getWaitBeginDate() {
        return waitBeginDate;
    }

    public void setWaitBeginDate(Date waitBeginDate) {
        this.waitBeginDate = waitBeginDate;
    }

    public Date getWaitEndDate() {
        return waitEndDate;
    }

    public void setWaitEndDate(Date waitEndDate) {
        this.waitEndDate = waitEndDate;
    }

    public Date getQueryBeginDate() {
        return queryBeginDate;
    }

    public void setQueryBeginDate(Date queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public Date getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(Date queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public Date getPriceBeginDate() {
        return priceBeginDate;
    }

    public void setPriceBeginDate(Date priceBeginDate) {
        this.priceBeginDate = priceBeginDate;
    }

    public Date getPriceEndDate() {
        return priceEndDate;
    }

    public void setPriceEndDate(Date priceEndDate) {
        this.priceEndDate = priceEndDate;
    }

    public Long getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Long priceMin) {
        this.priceMin = priceMin;
    }

    public Long getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Long priceMax) {
        this.priceMax = priceMax;
    }

    public Double getPriceAvg() {
        return priceAvg;
    }

    public void setPriceAvg(Double priceAvg) {
        this.priceAvg = priceAvg;
    }

    public String getTransUserId() {
        return transUserId;
    }

    public void setTransUserId(String transUserId) {
        this.transUserId = transUserId;
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

    public Long getPriceGuid() {
        return priceGuid;
    }

    public void setPriceGuid(Long priceGuid) {
        this.priceGuid = priceGuid;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public String getTransArea() {
        return transArea;
    }

    public void setTransArea(String transArea) {
        this.transArea = transArea;
    }

    public String getTransUseLand() {
        return transUseLand;
    }

    public void setTransUseLand(String transUseLand) {
        this.transUseLand = transUseLand;
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
