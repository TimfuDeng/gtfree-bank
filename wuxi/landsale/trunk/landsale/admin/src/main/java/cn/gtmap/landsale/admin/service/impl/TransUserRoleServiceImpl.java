package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransUserRoleService;
import cn.gtmap.landsale.common.model.TransUserRole;
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
public class TransUserRoleServiceImpl extends HibernateRepo<TransUserRole, String> implements TransUserRoleService {

    /**
     * 获取用户角色列表
     * @param roleId
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransUserRole> findTransUserRoleByRoleId(String roleId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(roleId)) {
            criterionList.add(Restrictions.eq("roleId", roleId));
        }
        list(criteria(criterionList));
        return list(criteria(criterionList));
    }

    /**
     * 获取用户角色列表
     * @param userId
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransUserRole> findTransUserRoleByUserId(String userId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(userId)) {
            criterionList.add(Restrictions.eq("userId", userId));
        }
        list(criteria(criterionList));
        return list(criteria(criterionList));
    }

    /**
     * 保存用户角色表
     * @param transUserRole
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransUserRole saveTransUserRole(TransUserRole transUserRole) {
        return save(transUserRole);
    }

    /**
     * 删除用户角色表
     * @param transUserRoleList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransUserRole(List<TransUserRole> transUserRoleList) {
        delete(transUserRoleList);
    }
}
