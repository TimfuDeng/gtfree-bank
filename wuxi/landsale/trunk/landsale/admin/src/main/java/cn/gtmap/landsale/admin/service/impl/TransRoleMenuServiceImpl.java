package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransRoleMenuService;
import cn.gtmap.landsale.common.model.TransRoleMenu;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色菜单ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Service
public class TransRoleMenuServiceImpl extends HibernateRepo<TransRoleMenu, String> implements TransRoleMenuService {

    /**
     * 获取角色菜单列表
     * @param roleId
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRoleMenu> findTransRoleMenuByRoleId(String roleId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(roleId)) {
            criterionList.add(Restrictions.eq("roleId", roleId));
        }
        return list(criteria(criterionList));
    }

    /**
     * 保存角色菜单表
     * @param transRoleMenuList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TransRoleMenu> saveTransRoleMenu(List<TransRoleMenu> transRoleMenuList) {
        return save(transRoleMenuList);
    }

    /**
     * 删除角色菜单表
     * @param transRoleMenuList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransRoleMenu(List<TransRoleMenu> transRoleMenuList) {
        delete(transRoleMenuList);
    }

}
