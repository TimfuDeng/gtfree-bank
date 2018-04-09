package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by light on 2015/9/23.
 */
@Entity
@Table(name = "trans_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransNotification {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid" , strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String noteId;

    @Column(nullable = false , length = 255)
    @Field(value = "通知标题")
    private String noteTitle;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Field(value = "通知时间")
    private Date noteTime;

    @Column(nullable = false)
    @Field(value = "通知时间")
    private String noteCreater;

    @Column(nullable = false , columnDefinition = "CLOB")
    @Field(value = "通知内容")
    private String noteContent;

    @Column(nullable = false)
    @Field(value = "发布状态 '0'表示撤回 '1'表示发布中")
    private String deployStatus;

    @Transient
    private List<TransFile> attachmentList;

    public List<TransFile> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<TransFile> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Date getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(Date noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(String deployStatus) {
        this.deployStatus = deployStatus;
    }

    public String getNoteCreater() {
        return noteCreater;
    }

    public void setNoteCreater(String noteCreater) {
        this.noteCreater = noteCreater;
    }
}
