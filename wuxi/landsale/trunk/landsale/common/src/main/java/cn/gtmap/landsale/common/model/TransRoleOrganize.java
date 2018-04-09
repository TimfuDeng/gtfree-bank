package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色 组织 关系表实体对象
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Entity
@Table(name = "trans_role_organize")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransRoleOrganize implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String roleOrganizeId;

    @Column(length = 32)
    private String roleId;

    @Column(length = 32)
    private String organizeId;

    public String getRoleOrganizeId() {
        return roleOrganizeId;
    }

    public void setRoleOrganizeId(String roleOrganizeId) {
        this.roleOrganizeId = roleOrganizeId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }
}
