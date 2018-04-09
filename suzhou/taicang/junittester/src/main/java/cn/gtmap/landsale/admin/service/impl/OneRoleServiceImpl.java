package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.OneRole;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.service.OneRoleService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2016/8/10.
 */
public class OneRoleServiceImpl extends HibernateRepo<OneRole,String> implements OneRoleService {
    @Override
    @Transactional
    public OneRole getOneRoleByTransUserId(String transUserId) {
        return StringUtils.isNotBlank(transUserId)?get(criteria(Restrictions.eq("transUserId", transUserId))):null;
    }

    /**
     * 更新用户的权限
     *
     * @param userId     用户Id
     * @param privileges 权限JSON格式字符串
     * @return
     */
    @Override
    @Transactional
    //@Caching(evict={
            //@CacheEvict(value="AuthorizationCache",allEntries=true)
    //})
    public OneRole updateUserPrivileges(String userId, String privileges) {
        OneRole oneRole = getOneRoleByTransUserId(userId);
        if (null!=oneRole){
            oneRole.setPrivilege(privileges);
        }else {
            oneRole=new OneRole();
            oneRole.setTransUserId(userId);
            oneRole.setPrivilege(privileges);

        }
        return saveOrUpdate(oneRole);
    }
}
