package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import cn.gtmap.landsale.Constants;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Jibo on 2015/5/25.
 */
@Entity
@Table(name = "trans_user_union")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransUserUnion implements Serializable {
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String unionId;

    @Column(length = 32)
    private String applyId;

    @Column(length = 150)
    private String userName;

    @Column(precision = 1, nullable = false)
    @Field(value = "被联合人类型")
    private Constants.UserClass type=Constants.UserClass.COMPANY;

    @Column(nullable = false,length = 50)
    private String userCode;

    @Column(length = 150)
    private String userAddress;

    @Column(length = 50)
    private String  linkMan;

    @Column(length = 50)
    private String  linkManTel;

    @Column(length = 10)
    private String legalPerson;  //法人

    @Column(nullable = false,columnDefinition ="number(6,2) default '0'")
    private double amountScale;  //出资比例

    @Column
    private boolean agree;  //是否同意

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Constants.UserClass getType() {
        return type;
    }

    public void setType(Constants.UserClass type) {
        this.type = type;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkManTel() {
        return linkManTel;
    }

    public void setLinkManTel(String linkManTel) {
        this.linkManTel = linkManTel;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public double getAmountScale() {
        return amountScale;
    }

    public void setAmountScale(double amountScale) {
        this.amountScale = amountScale;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
