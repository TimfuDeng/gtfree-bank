package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.bean.Status;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.PasswordException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransRoleUser;
import cn.gtmap.landsale.model.TransUser;

import java.util.Collection;
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
     * @param userType 用户类型
     * @param regionCodes 行政区代码
     * @return
     */
    Page<TransUser> findTransUser(String viewName,Integer userType,Collection<String> regionCodes, Pageable request);

    /**
     * 根据用户类型获取分页服务
     * @param viewName
     * @param userType
     * @param regionCodes
     * @param request
     * @return
     */
    Page<TransUser> findTransUserByType(String viewName,Integer userType,Collection<String> regionCodes, Pageable request);

    /**
     * 根据用户Id获取用户对象
     * @param userId 用户id
     * @return
     */
    TransUser getTransUser(String userId);

    /**
     * 得到所有用户
     * @return
     */
    List<TransUser> getTransUserList();

    /**
     * 得到所有用户，并确定那些用户被添加到角色
     * @param roleId
     * @return
     */
    List<TransUser> getTransUserByTransRoleUser(String roleId);

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



    public TransUser validatePassword(String userName, String password,Constants.UserType userType);



    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    String encodePassword(String password);
}
