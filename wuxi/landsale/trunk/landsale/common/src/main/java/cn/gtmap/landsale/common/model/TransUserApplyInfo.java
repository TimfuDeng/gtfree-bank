package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "trans_user_apply_info")
public class TransUserApplyInfo implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String infoId;
    @Column(length = 32,nullable = false)
    @Field(value = "用户Id")
    private String userId;
    @Column(length = 32)
    @Field(value = "地块申请Id")
    private String applyId;
    @Column(length = 100)
    @Field(value = "联系人姓名")
    private String contactPerson;
    @Column(length = 500)
    @Field(value = "联系人地址")
    private String contactAddress;
    @Column(length = 50)
    @Field(value = "联系人电话")
    private String contactTelephone;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }
}