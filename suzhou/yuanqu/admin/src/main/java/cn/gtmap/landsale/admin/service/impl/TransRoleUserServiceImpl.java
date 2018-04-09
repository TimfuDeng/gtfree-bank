package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.model.TransRoleUser;
import cn.gtmap.landsale.service.TransRoleService;
import cn.gtmap.landsale.service.TransRoleUserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 用户角色关系表
 * Created by trr on 2015/10/16.
 */
public class TransRoleUserServiceImpl extends HibernateRepo<TransRoleUser,String> implements TransRoleUserService{

    @Autowired
    TransRoleService transRoleService;
    /**
     * 保存或者修改用户权限
     *
     * @param transRoleUser 用户角色
     * @return
     */
    @Override
    @Transactional
    public TransRoleUser saveTransRoleUser(TransRoleUser transRoleUser) {
        return saveOrUpdate(transRoleUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransRoleUser> findTransRoleUser(TransRoleUser transRoleUser) {
        List<Criterion> criterion= Lists.newArrayList();
        if(StringUtils.isNotBlank(transRoleUser.getRoleId()))
        criterion.add(Restrictions.eq("roleId",transRoleUser.getRoleId()));
        if(StringUtils.isNotBlank(transRoleUser.getUserId()))
        criterion.add(Restrictions.eq("userId",transRoleUser.getUserId()));
        return  list(criteria(criterion));
    }

    /**
     * 通过角色Id来删除
     *
     * @param roleId 角色Id
     */
    @Override
    @Transactional
    public void deleteTransRoleUserByRoleId(String roleId) {
        Query hql=hql("delete from TransRoleUser t where t.roleId='"+roleId+"'");

        hql.executeUpdate();
    }

    /**
     * 通过用户Id来删除
     *
     * @param userId
     */
    @Override
    @Transactional
    public void deleteTransRoleUserByUserId(String userId) {
        Query hql=hql("delete from TransRoleUser t where t.userId='"+userId+"'");
        hql.executeUpdate();
    }

    /**
     * 批量保存和更新用户角色关系
     *
     * @param transRole  角色
     * @param userIds 多个用户id
     */
    @Override
    @Transactional
    public void batchSaveTransRoleUser(TransRole transRole, String userIds) {
        transRole=transRoleService.saveTransRole(transRole);
        deleteTransRoleUserByRoleId(transRole.getRoleId());
        if(StringUtils.isNotBlank(userIds)){
            TransRoleUser transRoleUser=null;
            String[] userId= userIds.split(",");
            if(userId.length>0){
                for(String id:userId){
                    transRoleUser=new TransRoleUser();
                    transRoleUser.setId(null);
                    transRoleUser.setUserId(id);
                    transRoleUser.setRoleId(transRole.getRoleId());
                    saveTransRoleUser(transRoleUser);
                }

            }
        }
    }

    @Override
    @Transactional
    public void batchTransRoleUser(String roleId, String userIds) {
        if(StringUtils.isNotBlank(roleId)){
            deleteTransRoleUserByRoleId(roleId);
            if(StringUtils.isNotBlank(userIds)){
                TransRoleUser transRoleUser=null;
                String[] userId= userIds.split(",");
                if(userId.length>0){
                    for(String id:userId){
                        transRoleUser=new TransRoleUser();
                        transRoleUser.setId(null);
                        transRoleUser.setUserId(id);
                        transRoleUser.setRoleId(roleId);
                        saveTransRoleUser(transRoleUser);
                    }

                }
            }
        }

    }

    /**
     * 一个用户绑定多个角色
     *
     * @param userId
     * @param roleIds
     */
    @Override
    @Transactional
    public void batchTransRoleUserByUser(String userId, String roleIds) {

        if(StringUtils.isNotBlank(userId)){
            deleteTransRoleUserByUserId(userId);
            if(StringUtils.isNotBlank(roleIds)){
                TransRoleUser transRoleUser=null;
                String[] roleId= roleIds.split(",");
                if(roleId.length>0){
                    for(String id:roleId){
                        transRoleUser=new TransRoleUser();
                        transRoleUser.setId(null);
                        transRoleUser.setRoleId(id);
                        transRoleUser.setUserId(userId);
                        saveTransRoleUser(transRoleUser);
                    }

                }
            }
        }
    }

    /**
     * 根据userid或者roleid来查找角色用户关系
     *
     * @param userId
     * @param roleId
     * @return 用户关系list
     */
    @Override
    @Transactional
    public List<TransRoleUser> findTransRoleUserList(String userId, String roleId) {
        List<Criterion> criterion= Lists.newArrayList();
        if(StringUtils.isNotBlank(roleId))
            criterion.add(Restrictions.eq("roleId",roleId));
        if(StringUtils.isNotBlank(userId))
            criterion.add(Restrictions.eq("userId",userId));
        return  list(criteria(criterion));
    }
}
