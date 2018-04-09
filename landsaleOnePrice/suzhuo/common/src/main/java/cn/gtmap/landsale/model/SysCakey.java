package cn.gtmap.landsale.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2016/7/19.
 */
@Entity
@Table(name = "SYS_CAKEY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysCakey implements Serializable{
    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 100)
    private String keyType;
    @Column(length = 200)
    private String key;
    @Column(length = 200)
    private String owner;
    @Column
    private Date beginDate;
    @Column
    private Date endDate;
    @Column(columnDefinition = "number(1) default '1'")
    private Integer isValid;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Date createDate;
    @Column(length = 100)
    private String remark;
    @Column(length = 50)
    private String userId;
    @Column(columnDefinition = "number(1) default '0'")
    private Integer isscan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsscan() {
        return isscan;
    }

    public void setIsscan(Integer isscan) {
        this.isscan = isscan;
    }
}
