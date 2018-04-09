package cn.gtmap.landsale.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 权限服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
public interface AuthorizationService {

    /**
     * 用户是否有权限对当前路径进行操作
     * @param userId 用户Id
     * @param path  资源路径
     * @param operation 操作
     * @return
     */
    public boolean isPermitted(String userId,String path,String resourceName,String operation);

    /**
     * 用户是否有权限对当前路径进行操作
     * @param userId 用户Id
     * @param path 资源路径
     * @param operations 操作
     * @return
     */
    public boolean isPermitted(String userId,String path,String resourceName,Collection<String> operations);


    /**
     * 获取用户对path所有的操作对象
     * @param userId 用户Id
     * @param path 路径
     * @param resourceName 资源名称
     * @return
     */
    public Set<String> getPermittedOperations(String userId, String path,String resourceName);

    /**
     * 获取用户所允许的行政区代码
     * @param userId
     * @return
     */
    public Set<String> getPermittedRegions(String userId);
}
