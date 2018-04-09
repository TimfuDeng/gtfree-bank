package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 组织机构
 * Created by trr on 2015/10/9.
 */

@Entity
@Table(name = "trans_dept")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransDept implements Serializable{

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid",strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    @Field(value = "组织机构Id")
    private String deptId;

    @Column(length = 65)
    @Field(value = "组织机构名称")
    private String deptName;

    @Column(nullable = true)
    @Field(value = "组织机构编号")
    private String deptNo;

    @Column
    @Field(value = "组织机构地址")
    private String deptAddress;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field(value = "组织机构类型 0-请选择 1-国土局 2-土地储备中心 3-金融机构 4-监管部门 5-其它机构")
    private int deptType;

    //组织机构类型名称
    private String deptTypeName;

    @Column(length = 25)
    @Field(value = "组织机构负责人姓名")
    private String deptChargeName;

    @Column(length = 25)
    @Field(value = "组织机构负责人联系方式")
    private String deptChargePhone;

    @Column(length = 100)
    @Field(value = "所辖岗位")
    private String deptJurisdictionPosition;

    @Column(length = 500)
    @Field(value = "备注信息")
    private String deptComment;

    public String getDeptTypeName() {
        return deptTypeName;
    }

    public void setDeptTypeName(String deptTypeName) {
        this.deptTypeName = deptTypeName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptAddress() {
        return deptAddress;
    }

    public void setDeptAddress(String deptAddress) {
        this.deptAddress = deptAddress;
    }

    public int getDeptType() {
        return deptType;
    }

    public void setDeptType(int deptType) {
        this.deptType = deptType;
    }

    public String getDeptChargeName() {
        return deptChargeName;
    }

    public void setDeptChargeName(String deptChargeName) {
        this.deptChargeName = deptChargeName;
    }

    public String getDeptChargePhone() {
        return deptChargePhone;
    }

    public void setDeptChargePhone(String deptChargePhone) {
        this.deptChargePhone = deptChargePhone;
    }

    public String getDeptJurisdictionPosition() {
        return deptJurisdictionPosition;
    }

    public void setDeptJurisdictionPosition(String deptJurisdictionPosition) {
        this.deptJurisdictionPosition = deptJurisdictionPosition;
    }

    public String getDeptComment() {
        return deptComment;
    }

    public void setDeptComment(String deptComment) {
        this.deptComment = deptComment;
    }
}
