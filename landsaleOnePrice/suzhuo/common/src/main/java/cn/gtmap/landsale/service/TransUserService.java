package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.PasswordException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransUser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 用户服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/15
 */
public interface TransUserService {

    /**
     * 获取用户分页服务
     * @param viewName 用户名
     * @param request 分页请求
     * @param userType 用户类型
     * @param regionCodes 行政区代码
     * @return
     */
    Page<TransUser> findTransUser(String viewName,Integer userType,Collection<String> regionCodes, Pageable request);

    /**
     * 根据用户Id获取用户对象
     * @param userId 用户id
     * @return
     */
    TransUser getTransUser(String userId);

    /**
     * 根据用户登录名获取用户对象
     * @param userName
     * @return
     */
    TransUser getTransUserByUserName(String userName);

    /**
     * 根据用户的CA指纹获取用户
     * @param thumbprint
     * @return
     */
    TransUser getTransUserByCAThumbprint(String thumbprint);

    /**
     * 删除用户
     * @param userIds 用户Ids
     */
    void deleteTransUser(Collection<String> userIds);

    /**
     * 保存用户
     * @param transUser 用户对象
     * @return
     */
    TransUser saveTransUser(TransUser transUser);

    /**
     * 验证用户名和密码
     * @param userName 用户名
     * @param password 密码
     * @return
     * @throws UserNotFoundException
     * @throws UserLockedException
     * @throws PasswordException
     */
    TransUser validatePassword(String userName,String password,Constants.UserType userType) throws UserNotFoundException, UserLockedException, PasswordException;

    /**
     * 验证用户名和CA证书信息
     * @param userName 用户名
     * @param Thumbprint CA证书指纹
     * @return
     * @throws UserNotFoundException
     * @throws UserLockedException
     * @throws PasswordException
     */
    TransUser validateCA(String userName,String Thumbprint) throws UserNotFoundException, UserLockedException, CaCertificateException;

    /**
     * 修改密码
     * @param userId 用户Id
     * @param password 新密码
     * @return
     */
    TransUser updatePassword(String userId,String password);

    /**
     * 获取可以授权资源
     * @return
     */
    Set<Map> getAvailableUrlResources();

    /**
     * 更新用户的权限
     * @param userId 用户Id
     * @param privileges 权限JSON格式字符串
     * @return
     */
    TransUser updateUserPrivileges(String userId,String privileges);

    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    String encodePassword(String password);
}
