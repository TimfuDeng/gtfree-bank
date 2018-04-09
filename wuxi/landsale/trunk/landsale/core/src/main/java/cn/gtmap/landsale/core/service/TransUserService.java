package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;

import java.util.List;
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
     * @param regionCodes 行政区代码
     * @param userType 用户类型
     * @return
     */
    Page<TransUser> findTransUserPage(String viewName, Integer userType, String regionCodes, Pageable request);

    /**
     * 获取用户对象列表
     * @param roleId 角色id
     * @return
     */
    List<TransUser> getTransUserListByRole(String roleId);

    /**
     * 根据用户Id获取用户对象
     * @param userId 用户id
     * @return
     */
    TransUser   getTransUserById(String userId);

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
     * @return
     */
    ResponseMessage deleteTransUser(String userIds);

    /**
     * 删除用户
     * @param userIds 用户Ids
     * @return
     */
    ResponseMessage deleteJmr(String userIds);


    /**
     * 保存用户
     * @param transUser 用户对象
     * @param roleId 角色id
     * @return
     */
    ResponseMessage<TransUser> saveTransUser(TransUser transUser, String roleId);

    /**
     * 保存用户
     * @param transUser 用户对象
     * @return
     */
    ResponseMessage<TransUser> addJmr(TransUser transUser);

    /**
     * 修改竞买人
     * @param transUser
     * @return
     */
    ResponseMessage<TransUser> editJmr(TransUser transUser);

    /**
     * 验证用户名和密码
     * @param userName 用户名
     * @param password 密码
     * @param userType 用户类型
     * @return
     */
    ResponseMessage<TransUser> validatePassword(String userName, String password, Constants.UserType userType);

    /**
     * 验证用户名和CA证书信息
     * @param userName 用户名
     * @param thumbPrint CA证书指纹
     * @return
     * @throws cn.gtmap.egovplat.security.ex.UserNotFoundException
     * @throws cn.gtmap.egovplat.security.ex.UserLockedException
     * @throws cn.gtmap.egovplat.security.ex.PasswordException
     * @throws CaCertificateException
     */
    TransUser validateCA(String userName, String thumbPrint) throws UserNotFoundException, UserLockedException, CaCertificateException;

    /**
     * 修改密码
     * @param userId 用户Id
     * @param password 新密码
     * @return
     */
    TransUser updatePassword(String userId, String password);

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
    TransUser updateUserPrivileges(String userId, String privileges);

    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    String encodePassword(String password);
}
