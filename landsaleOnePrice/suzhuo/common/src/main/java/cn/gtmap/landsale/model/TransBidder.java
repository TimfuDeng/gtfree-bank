package cn.gtmap.landsale.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2016/7/19.
 */
@Entity
@Table(name = "TRANS_BIDDER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransBidder implements Serializable{
    @Id
    @Column(length = 50)
    private String id;
    @Column(length = 100)
    private String no;
    @Column(length = 200)
    private String name;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer bidderType;
    @Column(length = 200)
    private String address;
    @Column(length = 50)
    private String cantonId;
    @Column(length = 50)
    private String postCode;
    @Column(length = 50)
    private String tel;
    @Column(length = 50)
    private String fax;
    @Column(length = 50)
    private String mobile;
    @Column(length = 100)
    private String email;
    @Column(length = 50)
    private String contact;
    @Column(length = 50)
    private String certificateType;
    @Column(length = 50)
    private String certificateNo;
    @Column(length = 50)
    private String regNo;
    @Column(length = 200)
    private String regAddress;
    /*@Column(nullable = true,precision = 18,scale = 2,columnDefinition = "number(18,2) default '0'")
    private Double regCapital;*/

    @Column(length = 100)
    private String regCapital;//注册资本修改企业工商注册号
    @Column(length = 50)
    private String regType;
    @Column(length = 200)
    private String regArea;
    @Column(length = 50)
    private String regCorporation;
    @Column(length = 50)
    private String regCorporationIdno;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isCompany;
    @Column
    private Integer turn;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer createType;
    @Column(length = 50)
    private String createUserId;
    @Column
    private Date createDate;
    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private Integer isValid;
    @Column(nullable = true,columnDefinition = "number(1) default '1'")
    private Integer status;
    @Column(length = 100)
    private String remark;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isOversea;
    @Column(nullable = true,columnDefinition = "number(1) default '0'")
    private Integer isListedcompany;
    @Column(length = 100)
    private String trustCorporation;
    @Column(length = 100)
    private String trustCorporationIdno;
    @Column
    private Date validDate;
    @Column(length = 50)
    private String confirmUserId;
    @Column
    private String confirmDate;
    @Column(length = 4000)
    private String confirmOpinion;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBidderType() {
        return bidderType;
    }

    public void setBidderType(Integer bidderType) {
        this.bidderType = bidderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCantonId() {
        return cantonId;
    }

    public void setCantonId(String cantonId) {
        this.cantonId = cantonId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getRegArea() {
        return regArea;
    }

    public void setRegArea(String regArea) {
        this.regArea = regArea;
    }

    public String getRegCorporation() {
        return regCorporation;
    }

    public void setRegCorporation(String regCorporation) {
        this.regCorporation = regCorporation;
    }

    public String getRegCorporationIdno() {
        return regCorporationIdno;
    }

    public void setRegCorporationIdno(String regCorporationIdno) {
        this.regCorporationIdno = regCorporationIdno;
    }

    public Integer getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Integer isCompany) {
        this.isCompany = isCompany;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsOversea() {
        return isOversea;
    }

    public void setIsOversea(Integer isOversea) {
        this.isOversea = isOversea;
    }

    public Integer getIsListedcompany() {
        return isListedcompany;
    }

    public void setIsListedcompany(Integer isListedcompany) {
        this.isListedcompany = isListedcompany;
    }

    public String getTrustCorporation() {
        return trustCorporation;
    }

    public void setTrustCorporation(String trustCorporation) {
        this.trustCorporation = trustCorporation;
    }

    public String getTrustCorporationIdno() {
        return trustCorporationIdno;
    }

    public void setTrustCorporationIdno(String trustCorporationIdno) {
        this.trustCorporationIdno = trustCorporationIdno;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmOpinion() {
        return confirmOpinion;
    }

    public void setConfirmOpinion(String confirmOpinion) {
        this.confirmOpinion = confirmOpinion;
    }
}
