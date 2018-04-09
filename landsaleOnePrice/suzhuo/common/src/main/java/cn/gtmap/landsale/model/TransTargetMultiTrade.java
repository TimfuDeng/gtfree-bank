package cn.gtmap.landsale.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by trr on 2016/7/19.
 */
@Entity
@Table(name = "trans_target_multi_trade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransTargetMultiTrade implements Serializable{
    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String targetId;
    @Column(length = 255)
    private String name;
    @Column(length = 50)
    private String type;
    @Column(length = 50)
    private String unit;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double initValue;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double finalValue;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double step;
    @Column(length = 4000)
    private String enterFlag;
    @Column
    private Integer firstWait;
    @Column(nullable = false)
    private Integer limitWait;
    @Column
    private Integer lastWait;
    @Column
    private Integer turn;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double successValue;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer status;
    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private Integer isValid;
    @Column(length = 200)
    private String remark;
    @Column(length = 200)
    private String memo;
    @Column(length = 50)
    private String ptype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getInitValue() {
        return initValue;
    }

    public void setInitValue(Double initValue) {
        this.initValue = initValue;
    }

    public Double getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Double finalValue) {
        this.finalValue = finalValue;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public String getEnterFlag() {
        return enterFlag;
    }

    public void setEnterFlag(String enterFlag) {
        this.enterFlag = enterFlag;
    }

    public Integer getFirstWait() {
        return firstWait;
    }

    public void setFirstWait(Integer firstWait) {
        this.firstWait = firstWait;
    }

    public Integer getLimitWait() {
        return limitWait;
    }

    public void setLimitWait(Integer limitWait) {
        this.limitWait = limitWait;
    }

    public Integer getLastWait() {
        return lastWait;
    }

    public void setLastWait(Integer lastWait) {
        this.lastWait = lastWait;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Double getSuccessValue() {
        return successValue;
    }

    public void setSuccessValue(Double successValue) {
        this.successValue = successValue;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }
}
