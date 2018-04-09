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
 * 同意参加摇号记录
 * @author lq
 * @version v1.0, 2017/12/27
 */
@Entity
@Table(name = "yh_agree")
public class YHAgree implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String agreeId;

    /**
     * 资源id
     */
    @Column(length = 32)
    private String resourceId;

    /**
     * 用户id
     */
    @Column(length = 32)
    private String userId;

    /**
     * 是否同意 0.否 1.是
     */
    @Column
    private Integer agreeStatus;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = DateDeserializer.class)
    private Date agreeTime;

    public YHAgree() {
    }

    public YHAgree(String resourceId, String userId, Integer agreeStatus, Date agreeTime) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.agreeStatus = agreeStatus;
        this.agreeTime = agreeTime;
    }

    public String getAgreeId() {
        return agreeId;
    }

    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAgreeStatus() {
        return agreeStatus;
    }

    public void setAgreeStatus(Integer agreeStatus) {
        this.agreeStatus = agreeStatus;
    }

    public Date getAgreeTime() {
        return agreeTime;
    }

    public void setAgreeTime(Date agreeTime) {
        this.agreeTime = agreeTime;
    }
}
