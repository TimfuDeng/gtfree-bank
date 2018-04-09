package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2016/7/1.
 */
@Entity
@Table(name = "TRANS_TARGET_GOODS_REL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransTargetGoodsRel implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String targetId;
    @Column(length = 50)
    private String goodsId;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private int isMainGoods;
    @Column
    private Integer turn;
    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private int isValid;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Date createDate;
    @Column(length = 100)
    private String remark;
    @Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double scale;


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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getIsMainGoods() {
        return isMainGoods;
    }

    public void setIsMainGoods(int isMainGoods) {
        this.isMainGoods = isMainGoods;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
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

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }
}
