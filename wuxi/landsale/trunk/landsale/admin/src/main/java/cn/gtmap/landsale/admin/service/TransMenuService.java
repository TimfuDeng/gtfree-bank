package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.model.TransMenu;

import java.util.List;

/**
 * 菜单Service
 * @author zsj
 * @version v1.0, 2017/9/7
 */
public interface TransMenuService {

    /**
     * 获的所有的菜单
     * @return
     */
    List<TransMenu> getTransMenuList();

    /**
     * 通过角色获取菜单
     * @param roleId 角色id
     * @return
     */
    List<TransMenu> getTransMenuListByRole(String roleId);

    /**
     * 获的所有的菜单 及菜单按钮
     * @return
     */
    List<TransMenu> getTransMenuButtonList();

    /**
     * 通过角色获取菜单 及菜单按钮
     * @param roleId 角色id
     * @return
     */
    List<TransMenu> getTransMenuButtonListByRole(String roleId);

}
