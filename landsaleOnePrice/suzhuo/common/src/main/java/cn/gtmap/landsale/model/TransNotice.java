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
 * Created by trr on 2016/6/30.
 */
@Entity
@Table(name = "TRANS_NOTICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransNotice implements Serializable{

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    private  int noticeType;

    @Column(length = 100)
    private  String no;

    @Column(length = 200)
    private  String name;

    //@Temporal(TemporalType.DATE)//将实体映射时的时间从java.sql下面修改成java.until包下面，同时规定时间精确到纳秒
    @Column
    private Date noticeDate;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private int targetReject;

    @Column(length = 50)
    private String parentId;

    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private int isValid;

    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private int status;

    @Column(length = 50)
    private String createUserId;

    @Column
    private Date createDate;

    @Column(length = 100)
    private String remark;

    @Column(length = 50)
    private  String businessType;

    @Column(length = 50)
    private String organId;

    @Column(length = 50)
    private String checkUserId;

    @Column
    private Date checkDate;

    @Column(length = 50)
    private String publishUserId;

    @Column
    private Date publishDate;

    @Column(length = 50)
    private String ggguid;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public int getTargetReject() {
        return targetReject;
    }

    public void setTargetReject(int targetReject) {
        this.targetReject = targetReject;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getGgguid() {
        return ggguid;
    }

    public void setGgguid(String ggguid) {
        this.ggguid = ggguid;
    }
}
