package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 申购资格审核表
 * Created by trr on 2015/10/13.
 */
@Entity
@Table(name = "trans_buy_qualified")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransBuyQualified implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String qualifiedId;

    @Column(nullable = false ,columnDefinition ="number(1) default '0'")
    @Field("审核状态 0：未通过 1：通过  ；被强制退回：2")
    private int qualifiedStatus;//审核状态

    @Column(nullable = true,length=32)
    @Field("审核人")
    private String qualifiedAuditor;//审核人

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @Field("审核时间")
    private Date qualifiedTime;//审核时间

    @Column(nullable = true,length = 255)
    @Field("不通过原因")
    private String qualifiedReason;//原因

    @Column(length = 32)
    @Field("transuserinfo表，申购人基本信息id 1-1")
    private String infoId;

    @Column(length = 32)
    @Field("地块表Id n-1")
    private String resourceId;

    @Column(length = 32)
    private String contacter;

    @Column(length = 32)
    @Field("竞买人申购表Id 1-1")
    private String applyId;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getQualifiedId() {
        return qualifiedId;
    }

    public void setQualifiedId(String qualifiedId) {
        this.qualifiedId = qualifiedId;
    }

    public int getQualifiedStatus() {
        return qualifiedStatus;
    }

    public void setQualifiedStatus(int qualifiedStatus) {
        this.qualifiedStatus = qualifiedStatus;
    }

    public String getQualifiedAuditor() {
        return qualifiedAuditor;
    }

    public void setQualifiedAuditor(String qualifiedAuditor) {
        this.qualifiedAuditor = qualifiedAuditor;
    }

    public Date getQualifiedTime() {
        return qualifiedTime;
    }

    public void setQualifiedTime(Date qualifiedTime) {
        this.qualifiedTime = qualifiedTime;
    }

    public String getQualifiedReason() {
        return qualifiedReason;
    }

    public void setQualifiedReason(String qualifiedReason) {
        this.qualifiedReason = qualifiedReason;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }
}
