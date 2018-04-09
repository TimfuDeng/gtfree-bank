package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.model.TransRoleUser;

import java.util.List;

/**
 * 用户角色表
 * Created by trr on 2015/10/16.
 */
public interface TransRoleUserService {
    /**
     * 保存或者修改用户权限
     *
     * @param transRoleUser 用户角色
     * @return
     */
    public TransRoleUser saveTransRoleUser(TransRoleUser transRoleUser);

    /**
     * 根据roleid和userid来查找transroleuser
     * @param transRoleUser
     * @return
     */
    public List<TransRoleUser> findTransRoleUser(TransRoleUser transRoleUser);

    /**
     * 通过角色Id来删除
     * @param roleId 角色Id
     */
    public void deleteTransRoleUserByRoleId(String roleId);

    /**
     * 通过用户Id来删除
     * @param userId
     */
    public void deleteTransRoleUserByUserId(String userId);



    /**
     * 批量保存和更新用户角色关系
     * @param transRole 角色
     * @param userIds 多个用户id
     */
    public void batchSaveTransRoleUser(TransRole transRole,String userIds);

    /**
     * 一个角色与多个用户绑定
     * @param roleId
     * @param userIds
     */
    public void batchTransRoleUser(String roleId,String userIds);

    /**
     * 一个用户绑定多个角色
     * @param userId
     * @param roleIds
     */
    public void batchTransRoleUserByUser(String userId,String roleIds);

    /**
     * 根据userid或者roleid来查找角色用户关系
     * @param userId
     * @param roleId
     * @return 用户关系list
     */
   public List<TransRoleUser> findTransRoleUserList(String userId,String roleId);

}
