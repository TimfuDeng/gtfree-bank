package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.PasswordException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransRoleUser;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/15
 */
public class TransUserServiceImpl extends HibernateRepo<TransUser, String> implements TransUserService {

    private Boolean clientVisible;

    private Set<Map> urlResources= Sets.newHashSet();

    public void setUrlResources(Set<Map> urlResources) {
        this.urlResources = urlResources;
    }

    public void setClientVisible(Boolean clientVisible) {
        this.clientVisible = clientVisible;
    }

    /**
     * 获取用户分页服务
     *
     * @param viewName
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransUser> findTransUser(String viewName,Integer userType,Collection<String> regionCodes,Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(viewName))
            criterionList.add(Restrictions.like("viewName",viewName, MatchMode.ANYWHERE));
        if(regionCodes!=null&&!regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        if(!clientVisible)
            criterionList.add(Restrictions.eq("type", Constants.UserType.MANAGER));
        return find(criteria(criterionList),request);
    }

    /**
     * 根据用户类型获取分页服务
     *
     * @param viewName
     * @param userType
     * @param regionCodes
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransUser> findTransUserByType(String viewName, Integer userType, Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(viewName))
            criterionList.add(Restrictions.like("viewName",viewName, MatchMode.ANYWHERE));
        if(regionCodes!=null&&!regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        if(userType!=null){
            if(userType==1) {
                criterionList.add(Restrictions.eq("type", Constants.UserType.MANAGER));
            }else{
                criterionList.add(Restrictions.eq("type", Constants.UserType.CLIENT));
            }
        }
        return find(criteria(criterionList),request);
    }





    /**
     * 根据用户Id获取用户对象
     *
     * @param userId 用户id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransUser getTransUser(String userId) throws EntityNotFoundException {
        return get(userId);
    }

    /**
     * 得到所有用户
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransUser> getTransUserList() {
        List<Criterion> criterion=Lists.newArrayList();
        criterion.add(Restrictions.eq("type",Constants.UserType.MANAGER));
        criterion.add((Restrictions.eq("status",Status.ENABLED)));
        return list(criteria(criterion));
    }

    /**
     * 得到所有用户，并确定那些用户被添加到角色
     *
     * @param roleId
     * @return
     */
    @Override
    public List<TransUser> getTransUserByTransRoleUser(String roleId) {
        return null;
    }

    /**
     * 根据用户登录名获取用户对象
     *
     * @param userName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransUser getTransUserByUserName(String userName) {
        return StringUtils.isNotBlank(userName)?get(criteria(Restrictions.eq("userName", userName))):null;
    }

    /**
     * 根据用户的CA指纹获取用户
     *
     * @param thumbprint
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransUser getTransUserByCAThumbprint(String thumbprint) {
        return StringUtils.isNotBlank(thumbprint)?get(criteria(Restrictions.eq("caThumbprint", thumbprint))):null;
    }



    @Override
    @Transactional(readOnly = true)
    public TransUser validatePassword(String userName, String password,Constants.UserType userType) throws UserNotFoundException, UserLockedException, PasswordException {
        TransUser transUser = getTransUserByUserName(userName);
        if(transUser==null)
            throw new UserNotFoundException(userName);
        if (transUser.getStatus() != Status.ENABLED) {
            throw new UserLockedException(userName);
        }
        if (!StringUtils.equals(transUser.getPassword(), encodePassword(password))) {
            throw new PasswordException(userName);
        }
        if(userType!=null){
            if (transUser.getType() != userType) {
                throw new UserLockedException(userName);
            }
        }

        return transUser;
    }


    public String encodePassword(String password){
        return DigestUtils.md5Hex(password);
    }
}
