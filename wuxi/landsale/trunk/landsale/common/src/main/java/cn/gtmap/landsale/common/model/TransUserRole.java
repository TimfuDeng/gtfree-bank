package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色表实体对象
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Entity
@Table(name = "trans_user_role")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransUserRole implements Serializable {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String userRoleId;

    @Column(length = 32)
    private String userId;

    @Column(length = 32)
    private String roleId;

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
