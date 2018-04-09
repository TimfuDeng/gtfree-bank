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
 * 摇号结果
 * @author lq
 * @version v1.0, 2017/12/27
 */
@Entity
@Table(name = "yh_result")
public class YHResult implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String resultId;

    //同意参加摇号Id
    @Column(length = 32)
    private String agreeId;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date resultTime;

    //中标id
    @Column(length = 32)
    private String resultUser;

    //中标价格
    @Column(nullable = false,precision = 18,scale =6,columnDefinition ="number(18,6) default '0'")
    private Double resultPrice;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date yhPostTime;

    @Column(columnDefinition ="number(1) default '0'")
    private int yhPostStatus;

    public YHResult() {
    }

    public YHResult(String agreeId, Date resultTime, String resultUser, Double resultPrice) {
        this.agreeId = agreeId;
        this.resultTime = resultTime;
        this.resultUser = resultUser;
        this.resultPrice = resultPrice;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getAgreeId() {
        return agreeId;
    }

    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }

    public String getResultUser() {
        return resultUser;
    }

    public void setResultUser(String resultUser) {
        this.resultUser = resultUser;
    }

    public Date getResultTime() {
        return resultTime;
    }

    public void setResultTime(Date resultTime) {
        this.resultTime = resultTime;
    }

    public Double getResultPrice() {
        return resultPrice;
    }

    public void setResultPrice(Double resultPrice) {
        this.resultPrice = resultPrice;
    }

    public Date getYhPostTime() {
        return yhPostTime;
    }

    public void setYhPostTime(Date yhPostTime) {
        this.yhPostTime = yhPostTime;
    }

    public int getYhPostStatus() {
        return yhPostStatus;
    }

    public void setYhPostStatus(int yhPostStatus) {
        this.yhPostStatus = yhPostStatus;
    }
}
