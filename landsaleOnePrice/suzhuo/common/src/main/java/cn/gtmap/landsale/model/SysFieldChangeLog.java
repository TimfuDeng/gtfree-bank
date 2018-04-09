package cn.gtmap.landsale.model;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 业务数据扩展表-中止表
 * Created by trr on 2016/8/9.
 */
@Entity
@Table(name = "SYS_FIELD_CHANGE_LOG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysFieldChangeLog {

    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String refTableName;//trans_target
    @Column(length = 50)
    private String refId;
    @Column(length = 50)
    private String fieldName;//is_suspend
    @Column(length = 100)
    private String oldValue;
    @Column(length = 100)
    private String newValue;
    @Column(length = 4000)
    private String extend1;
    @Column(length = 4000)
    private String extend2;
    @Column(length = 4000)
    private String extend3;
    @Column(length = 50)
    private String changeUserId;
    @Column(length = 4000)
    private String changeCause;
    @Column(length = 100)
    private String changeBatch;
    @Column
    private Date changeDate;
    @Column(length = 200)
    private String changeType;
    @Column(length = 50)
    private String createUserId;
    @Column(columnDefinition = "number(1) default '1'")
    private int isValid;
    @Column(length = 100)
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefTableName() {
        return refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getChangeUserId() {
        return changeUserId;
    }

    public void setChangeUserId(String changeUserId) {
        this.changeUserId = changeUserId;
    }

    public String getChangeCause() {
        return changeCause;
    }

    public void setChangeCause(String changeCause) {
        this.changeCause = changeCause;
    }

    public String getChangeBatch() {
        return changeBatch;
    }

    public void setChangeBatch(String changeBatch) {
        this.changeBatch = changeBatch;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
