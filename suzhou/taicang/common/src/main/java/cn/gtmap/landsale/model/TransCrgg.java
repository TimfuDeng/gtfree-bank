package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网上交易出让公告实体对象
 * Created by jiff on 14/12/24.
 */
@Entity
@Table(name = "trans_crgg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransCrgg implements Serializable {
    private static final long serialVersionUID = -9137292468621763148L;
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String ggId;
    @Column(nullable = false,length = 255)
    private String ggTitle;  //公告标题

    @Column(length = 50)
    private String regionCode;  //所属行政区

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date ggBeginTime;  //公告开始时间

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date ggEndTime;  //公告结束时间

    @Column(nullable = false)
    private int ggType;  //公告类型

    @Column (nullable = true,columnDefinition ="CLOB")
    private String ggContent;  //公告内容

    @Column (nullable = false)
    private String ggNum;  //公告编号

    @Column
    private String gyggId;//土地市场网土地供应GUID

    @Transient
    private List<TransResource> resourceList;

    @Transient
    private List<TransFile> attachmentList;

    public String getGgId() {
        return ggId;
    }

    public void setGgId(String ggId) {
        this.ggId = ggId;
    }

    public String getGgTitle() {
        return ggTitle;
    }

    public void setGgTitle(String ggTitle) {
        this.ggTitle = ggTitle;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Date getGgBeginTime() {
        return ggBeginTime;
    }

    public void setGgBeginTime(Date ggBeginTime) {
        this.ggBeginTime = ggBeginTime;
    }

    public Date getGgEndTime() {
        return ggEndTime;
    }

    public void setGgEndTime(Date ggEndTime) {
        this.ggEndTime = ggEndTime;
    }

    public int getGgType() {
        return ggType;
    }

    public void setGgType(int ggType) {
        this.ggType = ggType;
    }

    public String getGgContent() {
        return ggContent;
    }

    public void setGgContent(String ggContent) {
        this.ggContent = ggContent;
    }

    public List<TransResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<TransResource> resourceList) {
        this.resourceList = resourceList;
    }

    public String getGgNum() {
        return ggNum;
    }

    public void setGgNum(String ggNum) {
        this.ggNum = ggNum;
    }

    public String getGyggId() {
        return gyggId;
    }

    public void setGyggId(String gyggId) {
        this.gyggId = gyggId;
    }

    public List<TransFile> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<TransFile> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
