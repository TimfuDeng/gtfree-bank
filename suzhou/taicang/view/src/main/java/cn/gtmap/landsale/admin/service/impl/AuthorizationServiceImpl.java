package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.util.RequestUtils;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.service.AuthorizationService;
import cn.gtmap.landsale.service.TransUserService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户授权服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
public class AuthorizationServiceImpl implements AuthorizationService {
    private TransUserService transUserService;

    protected PathMatcher pathMatcher = RequestUtils.PATH_MATCHER;

    public void setTransUserService(TransUserService transUserService) {
        this.transUserService = transUserService;
    }

    /**
     * 用户是否有权限对当前路径进行操作
     *
     * @param userId    用户Id
     * @param path      资源路径
     * @param operation 操作
     * @return
     */
    @Override
    //@Cacheable(value="AuthorizationCache",key="'isPermitted_UserId'+#userId+'path'+#path+'operation'+#operation+'resourceName'+#resourceName")
    public boolean isPermitted(String userId, String path,String resourceName,String operation) {
        return isPermitted(userId,path,resourceName,Sets.newHashSet(operation));
    }

    /**
     * 用户是否有权限对当前路径进行操作
     *
     * @param userId    用户Id
     * @param path      资源路径
     * @param operations 操作
     * @return
     */
    @Override
    //@Cacheable(value="AuthorizationCache",key="'isPermittedExt_UserId'+#userId+'path'+#path+'operations'+#operations+'resourceName'+#resourceName")
    public boolean isPermitted(String userId, String path,String resourceName, Collection<String> operations) {
        if (CollectionUtils.isEmpty(operations)) {
            return false;
        }
        Set<String> userOps = getPermittedOperations(userId,path,resourceName);
        for (String op : operations) {
            if (!userOps.contains(op)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取用户对path所有的操作对象
     *
     * @param userId 用户Id
     * @param path   路径
     * @return
     */
    @Override
    //@Cacheable(value="AuthorizationCache",key="'getPermittedOperations_UserId'+#userId+'path'+#path+'resourceName'+#resourceName")
    public Set<String> getPermittedOperations(String userId, String path,String resourceName) {
        Set<String> operations =Sets.newHashSet();
        TransUser transUser = transUserService.getTransUser(userId);
        if(transUser!=null&& StringUtils.isNotBlank(transUser.getPrivilege())){
            Map privilegeMap = JSON.parseObject(transUser.getPrivilege());
            List<Map> resourcePrivileges = (List<Map>)privilegeMap.get("resources");
            for(Map privilege:resourcePrivileges){
                if(StringUtils.isBlank(resourceName)){
                    if(pathMatcher.match(String.valueOf(privilege.get("url")),path))
                        operations.add(String.valueOf(privilege.get("operation")));
                }else{
                    if(pathMatcher.match(String.valueOf(privilege.get("url")),path)&&resourceName.equals(privilege.get("name")))
                        operations.add(String.valueOf(privilege.get("operation")));
                }

            }
        }
        return operations;
    }

    /**
     * 获取用户所允许的行政区代码
     *
     * @param userId
     * @return
     */
    @Override
    //@Cacheable(value="AuthorizationCache",key="'getPermittedRegions_UserId'+#userId")
    public Set<String> getPermittedRegions(String userId) {
        Set<String> regions =Sets.newHashSet();
        TransUser transUser = transUserService.getTransUser(userId);
        if(transUser!=null&& StringUtils.isNotBlank(transUser.getPrivilege())){
            Map privilegeMap = JSON.parseObject(transUser.getPrivilege());
            regions = Sets.newHashSet((List) privilegeMap.get("regions"));
        }
        return regions;
    }
}
