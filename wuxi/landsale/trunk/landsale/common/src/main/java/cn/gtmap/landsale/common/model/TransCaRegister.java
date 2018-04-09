package cn.gtmap.landsale.common.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.common.Constants;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author M150237 on 2017-10-10.
 */
@Entity
@Table(name = "trans_ca_register")
public class TransCaRegister implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String registerId;

    @Column(length = 32,nullable = false)
    private String userId;

    @Column(length = 50)
    @Field(value ="联系人")
    private String contactUser;

    @Column(length = 50)
    @Field(value ="联系电话")
    private String contactPhone;

    @Column(precision = 1, nullable = false)
    @Field(value = "注册类型")
    private Constants.RegisterType registerType= Constants.RegisterType.COMPANY;

    @Column(length = 50)
    @Field(value = "编号")
    private String identityNum;

    @Column(precision = 1, nullable = false)
    @Field(value = "状态")
    private Status registerStatus = Status.ENABLED;

    @Column(length = 500)
    @Field(value = "描述")
    private String  registerDescribe;

    @Column(length = 20,nullable = false)
    @Field(value = "行政区代码")
    private  String regionCode;

    @Column(length = 32)
    @Field(value = "注册人")
    private  String registerUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Field(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Constants.RegisterType getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Constants.RegisterType registerType) {
        this.registerType = registerType;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public Status getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Status registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getRegisterDescribe() {
        return registerDescribe;
    }

    public void setRegisterDescribe(String registerDescribe) {
        this.registerDescribe = registerDescribe;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(String registerUser) {
        this.registerUser = registerUser;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
