package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 10:27
 * @描述 CA注册用户验证专用库对象
 */
@Entity
@Table(name = "trans_ca_user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransCaUser implements Serializable {
	@Id
	@GeneratedValue(generator = "sort-uuid")
	@GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
	@Column(length = 32)
	private String userId;

	@Column(length = 200,nullable = false)
	@Field("联系人")
	private String userName;

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
	private Constants.UserClass type=Constants.UserClass.COMPANY;

	@Column(length = 200)
	@Field("CA名称")
	private String caName;

	@Column(length = 50)
	@Field(value = "CA数字证书指纹")
	private String caThumbprint;

	@Column(length = 200)
	@Field("CA组织机构代码")
	private String caOrganizationCode;

	@Column(columnDefinition ="CLOB")
	@Field(value = "CA数字证书内容")
	private String caCertificate;

	@Temporal(TemporalType.TIMESTAMP)
	@Field(value = "CA证书启用时间")
	private Date caNotBeforeTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Field(value = "CA证书失效时间")
	private Date caNotAfterTime;

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

	public Constants.UserClass getType() {
		return type;
	}

	public void setType(Constants.UserClass type) {
		this.type = type;
	}

	public String getCaName() {
		return caName;
	}

	public void setCaName(String caName) {
		this.caName = caName;
	}

	public String getCaThumbprint() {
		return caThumbprint;
	}

	public void setCaThumbprint(String caThumbprint) {
		this.caThumbprint = caThumbprint;
	}

	public String getCaOrganizationCode() {
		return caOrganizationCode;
	}

	public void setCaOrganizationCode(String caOrganizationCode) {
		this.caOrganizationCode = caOrganizationCode;
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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
}
