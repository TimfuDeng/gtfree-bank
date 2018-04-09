package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;

import cn.gtmap.landsale.model.TransRole;

import cn.gtmap.landsale.model.TransRoleUser;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.service.TransRoleService;
import cn.gtmap.landsale.service.TransRoleUserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2015/10/12.
 */
public class TransRoleServiceImpl extends HibernateRepo<TransRole,String> implements TransRoleService{

    @Autowired
    TransRoleUserService transRoleUserService;

    @Override
    @Transactional(readOnly = true)
    public TransRole getTransRole(String roleId) {
        return get(roleId);
    }

    @Override
    @Transactional
    public void deleteTransRole(Collection<String> roleIds) {
        deleteByIds(roleIds);
    }

    @Override
    @Transactional
    public TransRole saveTransRole(TransRole transRole) {
        return saveOrUpdate(transRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransRole> findAllTransRole() {
        return list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransRole> findTransRole(String title, Pageable request) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("roleName", title, MatchMode.ANYWHERE));
        }
        Page<TransRole> transRoles=find(criteria(criterionList).addOrder(Order.desc("roleNo")),request);
        return transRoles;
    }


    @Override
    @Transactional(readOnly = true)
    public List<TransRole> getRoleList() {
        return list();
    }

    /**
     * 更新角色权限
     *
     * @param roleId
     * @param privileges
     * @return
     */
    /*@Caching(evict = {@CacheEvict(value = "AuthorizationCache",allEntries = true)})*/
    @Override
    @Transactional
    public TransRole updateRolePrivileges(String roleId, String privileges) {
        TransRole transRole = getTransRole(roleId);
        transRole.setPrivilege(privileges);
        transRole=saveOrUpdate(transRole);
        return transRole;
    }

    /**
     * 得到所有角色，并确定那些角色被添加到用户
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRole> getTransRoleByTransRoleUser(String userId) {
        List<TransRoleUser> transRoleUserList=null;
        if(StringUtils.isNotBlank(userId))
            transRoleUserList=transRoleUserService.findTransRoleUserList(userId, null);
        List<TransRole> transRoleList= getRoleList();
        for(TransRole transRole: transRoleList){
            if(null!=transRoleUserList)
                for(TransRoleUser transRoleUser:transRoleUserList){
                    if(transRole.getRoleId().equalsIgnoreCase(transRoleUser.getRoleId())){
                        transRole.setFoo(true);
                    }
                }
        }
        return transRoleList;
    }
}
