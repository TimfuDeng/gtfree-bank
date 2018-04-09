package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransMenuService;
import cn.gtmap.landsale.common.model.TransMenu;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Service
public class TransMenuServiceImpl extends HibernateRepo<TransMenu, String> implements TransMenuService {

    /**
     * 获的所有的菜单
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransMenu> getTransMenuList() {
        return list(criteria().addOrder(Order.asc("menuLeave")).addOrder(Order.asc("menuOrder")));
    }

    /**
     * 通过角色获取菜单
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransMenu> getTransMenuListByRole(String roleId) {
        Query query = hql("select tm from TransMenu tm, TransRoleMenu trm where tm.menuId = trm.menuId and trm.roleId='"
                + roleId + "' order by tm.menuLeave, tm.menuOrder");
        return query.list();
    }

    /**
     * 获的所有的菜单 及菜单按钮
     * @return
     */
    @Override
    public List<TransMenu> getTransMenuButtonList() {
        return formatMenuList(list());
    }

    /**
     * 通过角色获取菜单 及菜单按钮
     * @return
     */
    @Override
    public List<TransMenu> getTransMenuButtonListByRole(String roleId) {
        StringBuilder hql = new StringBuilder();
        hql.append("select tm from TransMenu tm, TransRoleMenu trm where tm.menuId = trm.menuId ");
        if (StringUtils.isNotBlank(roleId)) {
            hql.append(" and trm.roleId='"+ roleId + "' order by tm.menuOrder, tm.menuOrder");
        }
        Query query = hql(hql.toString());
        List<TransMenu> menuList = formatMenuList(query.list());

        return menuList;
    }

    private List<TransMenu> formatMenuList(List<TransMenu> menuList) {
        Map<String, TransMenu> menuMap = Maps.newHashMap();
        Map<String, List<TransMenu>> subMenuMap = Maps.newHashMap();
        List<TransMenu> formatList = Lists.newArrayList();
        if (menuList != null && menuList.size() > 0) {
            for (TransMenu transMenu : menuList) {
                if (transMenu.getMenuType() != 9) {
                    menuMap.put(transMenu.getMenuId(), transMenu);
                } else {
                    if (subMenuMap.containsKey(transMenu.getMenuParentId())) {
                        subMenuMap.get(transMenu.getMenuParentId()).add(transMenu);
                    } else {
                        subMenuMap.put(transMenu.getMenuParentId(), Lists.newArrayList());
                        subMenuMap.get(transMenu.getMenuParentId()).add(transMenu);
                    }
                }
            }
        }
        menuMap.entrySet().stream().filter(menuEntry ->
                subMenuMap.containsKey(menuEntry.getKey())).forEach(menuEntry ->
                    menuEntry.getValue().setSubMenuList(subMenuMap.get(menuEntry.getKey())));
        formatList.addAll(menuMap.values().stream().collect(Collectors.toList()));
        Collections.sort(formatList);
        return formatList;
    }

}
