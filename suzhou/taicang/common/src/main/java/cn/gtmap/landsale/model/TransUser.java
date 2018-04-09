package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 网上交易用户
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/15
 */
@Entity
@Table(name = "trans_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransUser implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String userId;

    @Column(length = 200,nullable = false)
    @Field("用户名")
    private String userName;

    @Column(length = 200)
    @Field("显示名")
    private String viewName;

    @Column(length = 100)
    @Field("密码")
    private String password;

    @Column(precision = 20)
    @Field("编号")
    private String userCode;

    @Column(precision = 2)
    @Field("性别")
    private String gender;

    @Column(length = 16)
    @Field(value = "手机号")
    private String mobile;

    @Column(length = 2000)
    @Field(value = "描述")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Field(value = "创建时间")
    private Date createAt;

    @Column(precision = 1, nullable = false)
    @Field(value = "状态")
    private Status status = Status.ENABLED;

    @Column(precision = 1, nullable = false)
    @Field(value = "用户类型")
    private Constants.UserType type=Constants.UserType.MANAGER;

    @Column(length = 200)
    @Field("CA名称")
    private String caName;

    @Column(length = 50)
    @Field(value = "CA数字证书指纹")
    private String caThumbprint;

    @Column(columnDefinition ="CLOB")
    @Field(value = "CA数字证书内容")
    private String caCertificate;

    @Temporal(TemporalType.TIMESTAMP)
    @Field(value = "CA证书启用时间")
    private Date caNotBeforeTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Field(value = "CA证书失效时间")
    private Date caNotAfterTime;

    @Column(length = 2000)
    @Field(value = "用户所具有的权限")
    private String privilege;

    @Column(length = 20)
    @Field("行政区代码")
    private String regionCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getCaName() {
        return caName;
    }

    public void setCaName(String caName) {
        this.caName = caName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Constants.UserType getType() {
        return type;
    }

    public void setType(Constants.UserType type) {
        this.type = type;
    }

    public String getCaThumbprint() {
        return caThumbprint;
    }

    public void setCaThumbprint(String caThumbprint) {
        this.caThumbprint = caThumbprint;
    }

    public String getCaCertificate() {
        return caCertificate;
    }

    public void setCaCertificate(String caCertificate) {
        this.caCertificate = caCertificate;
    }

    public Date getCaNotBeforeTime() {
        return caNotBeforeTime;
    }

    public void setCaNotBeforeTime(Date caNotBeforeTime) {
        this.caNotBeforeTime = caNotBeforeTime;
    }

    public Date getCaNotAfterTime() {
        return caNotAfterTime;
    }

    public void setCaNotAfterTime(Date caNotAfterTime) {
        this.caNotAfterTime = caNotAfterTime;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
