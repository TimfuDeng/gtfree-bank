package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by www on 2015/9/23.
 */

@Entity
@Table(name = "trans_news")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransNews implements Serializable{

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String newsId;

    @Column(nullable = false,length = 255)
    @Field(value = "新闻主标题")
    private String newsTitle;

    @Column(nullable = true,length = 255)
    @Field(value = "新闻副标题")
    private String newsSubHead;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    @Field(value = "新闻发布时间")
    private Date newsReportTime;

    @Column(nullable = true,columnDefinition ="CLOB")
    @Field(value = "新闻内容")
    private String newsContent;



        @Column(length = 50)
        @Field(value = "新闻作者")
        private String newsAuthor;


    @Column(length = 32)
    @Field(value = "用户id")
    private String userId;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field(value = "0-未删除 1-删除")
    private int dataFlag;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field(value = "0-未发布 1-发布")
    private int newsStauts;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    @Field(value = "新闻增加 系统时间")
    private Date newsAddTime;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    @Field(value = "新闻修改 系统时间")
    private Date newsUpdateTime;

    public int getNewsStauts() {
        return newsStauts;
    }

    public void setNewsStauts(int newsStauts) {
        this.newsStauts = newsStauts;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }
    public Date getNewsAddTime() {
        return newsAddTime;
    }

    public void setNewsAddTime(Date newsAddTime) {
        this.newsAddTime = newsAddTime;
    }

    public Date getNewsUpdateTime() {
        return newsUpdateTime;
    }

    public void setNewsUpdateTime(Date newsUpdateTime) {
        this.newsUpdateTime = newsUpdateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(int dataFlag) {
        this.dataFlag = dataFlag;
    }

    @Transient
    private List<TransFile> attachmentList;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSubHead() {
        return newsSubHead;
    }

    public void setNewsSubHead(String newsSubHead) {
        this.newsSubHead = newsSubHead;
    }

    public Date getNewsReportTime() {
        return newsReportTime;
    }

    public void setNewsReportTime(Date newsReportTime) {
        this.newsReportTime = newsReportTime;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public List<TransFile> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<TransFile> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
