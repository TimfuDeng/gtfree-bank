package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**中止公告
 * Created by trr on 2015/11/2.
 */
@Entity
@Table(name = "trans_suspend_notice")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SuspendNotice implements Serializable{
    @Id
    @GeneratedValue(generator="sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String noticeId;

    @Column(length = 255,nullable = false)
    @Field("公告标题")
    private String noticeTitle;


    @Column(length = 255,nullable = false)
    @Field("公告编号")
    private String noticeNum;

    @Column(length = 32,nullable = false)
    @Field("地块编号")
    private String resourceCode;



    @Column(length = 32)
    @Field("公告作者")
    private String noticeAuthor;

    @Column(length = 32)
    @Temporal(TemporalType.TIMESTAMP)
    @Field("发布时间")
    private Date deployTime;

    @Column(length = 32,columnDefinition = "CLOB")
    @Field("发布内容")
    private String noticeContent;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field(value = "0-未发布 1-发布")
    private int deployStatus;

    @Transient
    private String resourceId;

    public SuspendNotice() {
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(String noticeNum) {
        this.noticeNum = noticeNum;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getNoticeAuthor() {
        return noticeAuthor;
    }

    public void setNoticeAuthor(String noticeAuthor) {
        this.noticeAuthor = noticeAuthor;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public int getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(int deployStatus) {
        this.deployStatus = deployStatus;
    }
}
