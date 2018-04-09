package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.TransUserRole;

import java.util.List;

/**
 * 用户角色Service
 * @author zsj
 * @version v1.0, 2017/9/7
 */
public interface TransUserRoleService {

    /**
     * 获取用户角色列表
     * @param roleId
     * @return
     */
    List<TransUserRole> findTransUserRoleByRoleId(String roleId);

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<TransUserRole> findTransUserRoleByUserId(String userId);

    /**
     * 保存用户角色表
     * @param transUserRole
     * @return
     */
    TransUserRole saveTransUserRole(TransUserRole transUserRole);

    /**
     * 删除用户角色表
     * @param transUserRoleList
     */
    void deleteTransUserRole(List<TransUserRole> transUserRoleList);

}
