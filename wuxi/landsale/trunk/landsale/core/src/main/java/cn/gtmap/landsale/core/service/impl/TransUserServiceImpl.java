package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.core.service.TransCaRegisterService;
import cn.gtmap.landsale.core.service.TransRegionService;
import cn.gtmap.landsale.core.service.TransUserRoleService;
import cn.gtmap.landsale.core.service.TransUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * 用户服务
 * @author zsj
 * @version v1.0, 2017/9/18
 */
@Service
public class TransUserServiceImpl extends HibernateRepo<TransUser, String> implements TransUserService {

    private Set<Map> urlResources= Sets.newHashSet();

    @Autowired
    TransUserRoleService transUserRoleService;

    @Autowired
    TransRegionService transRegionService;

    @Autowired
    TransCaRegisterService transCaRegisterService;



    /**
     * 获取用户分页服务
     *
     * @param viewName
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransUser> findTransUserPage(String viewName, Integer userType, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(viewName)) {
            criterionList.add(Restrictions.like("viewName", viewName, MatchMode.ANYWHERE));
        }
        if (regionCodes != null && !regionCodes.isEmpty()) {
            criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
        }
        if (userType != null) {
            if (userType == 1) {
                criterionList.add(Restrictions.eq("type", Constants.UserType.MANAGER));
            } else {
                criterionList.add(Restrictions.eq("type", Constants.UserType.CLIENT));
            }
        }
        return find(criteria(criterionList).addOrder(Order.desc("createAt")), request);
    }

    /**
     * 获取用户对象列表
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<TransUser> getTransUserListByRole(String roleId) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TU.* FROM TRANS_USER TU ");
        sql.append(" LEFT JOIN TRANS_USER_ROLE TUR ON TU.USER_ID = TUR.USER_ID ");
        sql.append(" LEFT JOIN TRANS_ROLE TR ON TUR.ROLE_ID = TR.ROLE_ID WHERE 1=1 ");
        if (StringUtils.isNotBlank(roleId)) {
            sql.append(" AND TR.ROLE_ID =:roleId");
            params.put("roleId", roleId);
        }
        return entitySql(sql.toString(), params).list();
    }

    /**
     * 根据用户Id获取用户对象
     *
     * @param userId 用户id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransUser getTransUserById(String userId) throws EntityNotFoundException {
        return get(userId);
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

    /**
     * 删除用户
     *
     * @param userIds 用户Ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransUser(String userIds) {
        // 删除用户角色
        for (String userId : userIds.split(",")) {
            List<TransUserRole> transUserRoleList = transUserRoleService.findTransUserRoleByUserId(userId);
            if (transUserRoleList != null && transUserRoleList.size() > 0) {
                transUserRoleService.deleteTransUserRole(transUserRoleList);
            }
        }
        deleteByIds(userIds.split(","));
        return new ResponseMessage(true);
    }

    /**
     * 删除jmr
     * @param userIds 用户Ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteJmr(String userIds) {
        //CA管理中已注册的用户不可删除
        for(String id:userIds.split(",")){
            List<TransCaRegister> caList=transCaRegisterService.getTransCaRegisterByUserId(id);
            if (caList != null && caList.size()>0){
                return new ResponseMessage(false, caList.get(0).getContactUser()+"用户已在CA注册，不允许被删除！");
            }else {
                deleteById(id);
            }
        }

//        deleteByIds(userIds.split(","));
        return new ResponseMessage(true);
    }


    /**
     * 保存用户
     *
     * @param transUser 用户对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransUser> saveTransUser(TransUser transUser, String roleId) {
        if (StringUtils.isBlank(transUser.getUserId())||!transUser.getPassword().equals(getTransUserById(transUser.getUserId()).getPassword())){
            transUser.setPassword(encodePassword(transUser.getPassword()));
        }
        if (transUser.getCreateAt()==null) {
            transUser.setCreateAt(Calendar.getInstance().getTime());
        }
        if (StringUtils.isBlank(transUser.getUserId())) {
            save(transUser);
            TransUserRole transUserRole = new TransUserRole();
            transUserRole.setRoleId(roleId);
            transUserRole.setUserId(transUser.getUserId());
            transUserRoleService.saveTransUserRole(transUserRole);
        } else {
            // 根据 用户查找原有的用户角色关系
            List<TransUserRole> transUserRoleList = transUserRoleService.findTransUserRoleByUserId(transUser.getUserId());
            if (transUserRoleList != null && transUserRoleList.size() > 0) {
                transUserRoleService.deleteTransUserRole(transUserRoleList);
            }
            // 添加新关系
            TransUserRole transUserRole = new TransUserRole();
            transUserRole.setRoleId(roleId);
            transUserRole.setUserId(transUser.getUserId());
            transUserRoleService.saveTransUserRole(transUserRole);
            // 通过角色查找 角色所属的行政区
            List<TransRegion> transRegionList = transRegionService.findTransRegionByRole(roleId);
            if (transRegionList != null && transRegionList.size() > 0) {
                transUser.setRegionCode(transRegionList.get(0).getRegionCode());
            }
            merge(transUser);
        }
        return new ResponseMessage(true, transUser);
    }

    /**
     * 保存竞买人
     *
     * @param transUser 用户对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransUser> addJmr(TransUser transUser) {
        if (StringUtils.isBlank(transUser.getUserId())){
            transUser.setPassword(encodePassword(transUser.getPassword()));
        }
        if (transUser.getCreateAt() == null) {
            transUser.setCreateAt(Calendar.getInstance().getTime());
        }
        if (StringUtils.isBlank(transUser.getUserId())) {
            save(transUser);
        } else {
            merge(transUser);
        }
        return new ResponseMessage(true, transUser);
    }

    /**
     * 修改竞买人
     * @param transUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransUser> editJmr(TransUser transUser) {
//        TransUser user = getTransUserById(transUser.getUserId());
//        if(transUser.getCaThumbprint().equals(user.getCaThumbprint())){
//            transUser = merge(transUser);
//            return new ResponseMessage(true, transUser);
//        } else {
//            return new ResponseMessage(false, "CA指纹信息不一致，不允许修改！");
//        }
        transUser.setCreateAt(new Date());
        saveOrUpdate(transUser);
        return new ResponseMessage(true, transUser);
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseMessage<TransUser> validatePassword(String userName, String password,Constants.UserType userType) {
        TransUser transUser = getTransUserByUserName(userName);
        if(transUser==null) {
            return new ResponseMessage(false, "用户" + userName + "未找到", transUser);
        }
        if (transUser.getStatus() != Status.ENABLED) {
            return new ResponseMessage(false, "用户" + userName + "未启用", transUser);
        }
        if (!StringUtils.equals(transUser.getPassword(), encodePassword(password))) {
            return new ResponseMessage(false, "用户" + userName + "密码错误", transUser);
        }
        if(userType!=null){
            if (transUser.getType() != userType) {
                return new ResponseMessage(false, "用户" + userName + "权限错误", transUser);
            }
        }

        return new ResponseMessage(true, "验证成功", transUser);
    }

    /**
     * 验证用户名和CA证书信息
     *
     * @param userName   用户名
     * @param thumbPrint CA证书指纹
     * @return
     * @throws cn.gtmap.egovplat.security.ex.UserNotFoundException
     * @throws cn.gtmap.egovplat.security.ex.UserLockedException
     * @throws cn.gtmap.egovplat.security.ex.PasswordException
     */
    @Override
    @Transactional(readOnly = true)
    public TransUser validateCA(String userName, String thumbPrint) throws UserNotFoundException, UserLockedException, CaCertificateException {
        TransUser transUser = getTransUserByUserName(userName);
        if(transUser==null) {
            throw new UserNotFoundException(userName);
        }
        if (transUser.getStatus() != Status.ENABLED) {
            throw new UserLockedException(userName);
        }
        if (!StringUtils.equals(transUser.getCaThumbprint(), thumbPrint)) {
            throw new CaCertificateException(userName);
        }
        return transUser;
    }

    /**
     * 修改密码
     *
     * @param userId   用户Id
     * @param password 新密码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransUser updatePassword(String userId, String password) {
        TransUser transUser = getTransUserById(userId);
        transUser.setPassword(encodePassword(password));
        return saveOrUpdate(transUser);
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

    /**
     * 更新用户的权限
     *
     * @param userId     用户Id
     * @param privileges 权限JSON格式字符串
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict={
            @CacheEvict(value="AuthorizationCache",allEntries=true)
    })
    public TransUser updateUserPrivileges(String userId, String privileges) {
        TransUser transUser = getTransUserById(userId);
        transUser.setPrivilege(privileges);
        return saveOrUpdate(transUser);
    }

    @Override
    public String encodePassword(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
