package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerationStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 互动交流表
 * Created by www on 2015/9/29.
 */

@Entity
@Table(name = "trans_interact_communion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransInteractCommunion implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid",strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String communionId;

    @Column(length = 20)
    @Field(value = "群众问题编号，形式（年+月+日+序列，例如201509291）")
    private String publicNo;

    @Column(nullable = false,length = 255)
    @Field(value = "问题标题")
    private String publicTitle;

    @Column(columnDefinition = "CLOB")
    @Field(value = "问题内容")
    private String publicContent;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field(value = "接受时间")
    private Date publicTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Field(value = "回复时间")
    private Date replyTime;

    @Column(columnDefinition = "CLOB")
    @Field(value = "回复内容")
    private String replyContent;

    @Column(length = 32)
    @Field(value = "群众联系人")
    private String contacter;

    @Column(length = 32)
    @Field(value = "联系方式")
    private String phoneNum;

    @Column(length = 32)
    @Field(value = "回复管理员Id")
    private String userId;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field("0-未审核，1-未回复，2-已回复")
    private int replyStatus;

    public String getCommunionId() {
        return communionId;
    }

    public void setCommunionId(String communionId) {
        this.communionId = communionId;
    }

    public String getPublicNo() {
        return publicNo;
    }

    public void setPublicNo(String publicNo) {
        this.publicNo = publicNo;
    }

    public String getPublicTitle() {
        return publicTitle;
    }

    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }

    public String getPublicContent() {
        return publicContent;
    }

    public void setPublicContent(String publicContent) {
        this.publicContent = publicContent;
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(int replyStatus) {
        this.replyStatus = replyStatus;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
