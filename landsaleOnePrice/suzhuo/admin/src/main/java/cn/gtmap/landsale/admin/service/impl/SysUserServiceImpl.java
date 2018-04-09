package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.egovplat.security.ex.PasswordException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.SysUser;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.service.SysUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by trr on 2016/8/10.
 */
public class SysUserServiceImpl extends HibernateRepo<SysUser,String> implements SysUserService {


    private Boolean clientVisible;

    private Set<Map> urlResources= Sets.newHashSet();

    public void setUrlResources(Set<Map> urlResources) {
        this.urlResources = urlResources;
    }

    public void setClientVisible(Boolean clientVisible) {
        this.clientVisible = clientVisible;
    }
    @Override
    @Transactional(readOnly = true)
    public SysUser getSysUser(String id) {
        return get(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser geSysUserByUserName(String userName) {
        return StringUtils.isNotBlank(userName)?get(criteria(Restrictions.eq("userName", userName))):null;
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
    public Page<SysUser> findSysUser(String viewName,Integer userType,Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(viewName))
            criterionList.add(Restrictions.like("userName",viewName, MatchMode.ANYWHERE));
        if(!clientVisible)
            criterionList.add(Restrictions.eq("userType", userType));
        return find(criteria(criterionList),request);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser validatePassword(String userName, String password, Integer userType) throws UserNotFoundException, UserLockedException, PasswordException {
        SysUser sysUser=null;
        if(0==userType){//登录后台
            sysUser=geSysUserByUserName(userName);
        }else {//登录前台
            sysUser=geSysUserByUserName(userName);
            if (null==sysUser)
                sysUser=geSysUserByUserName("BIDDER_"+userName);
        }
        if(sysUser==null)
            throw new UserNotFoundException(userName);
        if (sysUser.getStatus() !=1) {
            throw new UserLockedException(userName);
        }
        if (!StringUtils.equals(sysUser.getPassword(), encodePassword(password))) {
            throw new PasswordException(userName);
        }
        /*if(userType!=null&&userType==0){
            if (sysUser.getUserType() != userType) {
                throw new UserLockedException(userName);
            }
        }*/
        return sysUser;
    }
    public String encodePassword(String password){
        return DigestUtils.md5Hex(password);
    }


    /**
     * 获取可以授权资源
     *
     * @return
     */
    @Override
    public Set<Map> getAvailableUrlResources() {
        return urlResources;
    }


}
