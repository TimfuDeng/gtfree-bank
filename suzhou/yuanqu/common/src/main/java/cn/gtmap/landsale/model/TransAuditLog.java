package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 交易审计对象
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/9
 */
@Entity
@Table(name = "trans_audit_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransAuditLog implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 64)
    private String auditId;
    @Column(length = 32)
    private String userId;
    @Column(length = 100)
    private String userViewName;
    @Column(precision = 1, nullable = false)
    @Field(value = "日志分类")
    private Constants.LogCategory category= Constants.LogCategory.OTHER;
    @Column(precision = 1, nullable = false)
    @Field(value = "日志产生者")
    private Constants.LogProducer producer= Constants.LogProducer.ADMIN;
    @Column(nullable = true,columnDefinition ="CLOB")
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Field(value = "创建时间")
    private Date createAt;
    @Column(length = 32)
    private String ip;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserViewName() {
        return userViewName;
    }

    public void setUserViewName(String userViewName) {
        this.userViewName = userViewName;
    }

    public Constants.LogCategory getCategory() {
        return category;
    }

    public void setCategory(Constants.LogCategory category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Constants.LogProducer getProducer() {
        return producer;
    }

    public void setProducer(Constants.LogProducer producer) {
        this.producer = producer;
    }
}
