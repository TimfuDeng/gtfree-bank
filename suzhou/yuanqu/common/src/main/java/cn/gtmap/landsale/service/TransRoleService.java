package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransDept;
import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.model.TransUser;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2015/10/12.
 */
public interface TransRoleService {
    /**
     * 查寻组织机构列表
     * @param title 部门名称
     * @param request
     * @return 角色page对象
     */
    public Page<TransRole> findTransRole(String title,Pageable request);

    /**
     * 查询所有角色
     * @return 角色对象list
     */
    public List<TransRole> findAllTransRole();

    /**
     * 保存或者更新角色
     * @param transRole
     * @return 角色对象
     */
    public TransRole saveTransRole(TransRole transRole);

    /**
     * 删除角色
     * @param roleIds
     */
    public void deleteTransRole(Collection<String> roleIds);

    /**
     * 根据角色Id获取一个角色
     * @param roleId
     * @return 角色对象
     */
    public TransRole getTransRole(String roleId);

    /**
     * 得到所有角色，并确定那些角色被添加到用户
     * @param userId
     * @return
     */
    List<TransRole> getTransRoleByTransRoleUser(String userId);

    List<TransRole> getRoleList();

    /**
     * 更新角色权限
     * @param roleId
     * @param privileges
     * @return
     */
    TransRole updateRolePrivileges(String roleId,String privileges);



}
