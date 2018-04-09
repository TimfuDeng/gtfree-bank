package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.model.TransRoleMenu;

import java.util.List;

/**
 * 角色菜单Service
 * @author zsj
 * @version v1.0, 2017/9/7
 */
public interface TransRoleMenuService {

    /**
     * 获取角色菜单列表
     * @param roleId
     * @return
     */
    List<TransRoleMenu> findTransRoleMenuByRoleId(String roleId);

    /**
     * 保存角色菜单表
     * @param transRoleMenuList
     * @return
     */
    List<TransRoleMenu> saveTransRoleMenu(List<TransRoleMenu> transRoleMenuList);

    /**
     * 删除角色菜单表
     * @param transRoleMenuList
     */
    void deleteTransRoleMenu(List<TransRoleMenu> transRoleMenuList);


}
