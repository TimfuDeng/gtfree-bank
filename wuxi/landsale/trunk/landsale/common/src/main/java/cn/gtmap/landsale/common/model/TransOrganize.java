package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门组织表实体对象
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Entity
@Table(name = "trans_organize")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransOrganize implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String organizeId;

    @Column(length = 50)
    private String organizeName;

    @Column(length = 32)
    private String organizeParentId;

    @Column(length = 100)
    private String organizeAddress;

    /**
     * 负责人姓名
     */
    @Column(length = 50)
    private String organizeResponsibleName;

    /**
     * 负责人联系方式
     */
    @Column(length = 50)
    private String organizeResponsiblePhone;

    /**
     * 所辖岗位
     */
    @Column(length = 100)
    private String organizePost;

    @Column(length = 500)
    private String organizeDescribe;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createTime;

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getOrganizeParentId() {
        return organizeParentId;
    }

    public void setOrganizeParentId(String organizeParentId) {
        this.organizeParentId = organizeParentId;
    }

    public String getOrganizeAddress() {
        return organizeAddress;
    }

    public void setOrganizeAddress(String organizeAddress) {
        this.organizeAddress = organizeAddress;
    }

    public String getOrganizeResponsibleName() {
        return organizeResponsibleName;
    }

    public void setOrganizeResponsibleName(String organizeResponsibleName) {
        this.organizeResponsibleName = organizeResponsibleName;
    }

    public String getOrganizeResponsiblePhone() {
        return organizeResponsiblePhone;
    }

    public void setOrganizeResponsiblePhone(String organizeResponsiblePhone) {
        this.organizeResponsiblePhone = organizeResponsiblePhone;
    }

    public String getOrganizePost() {
        return organizePost;
    }

    public void setOrganizePost(String organizePost) {
        this.organizePost = organizePost;
    }

    public String getOrganizeDescribe() {
        return organizeDescribe;
    }

    public void setOrganizeDescribe(String organizeDescribe) {
        this.organizeDescribe = organizeDescribe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
