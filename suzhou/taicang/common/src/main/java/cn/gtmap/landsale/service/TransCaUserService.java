package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.PasswordException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.model.TransUser;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 10:36
 * @描述 —— ca验证用户
 */
public interface TransCaUserService {
    public static final String USER_VALIDATE_DATASOURCE = "userValidateDataSource";

    /**
     * 获取用户分页服务
     * @param viewName 用户名
     * @param request 分页请求
     * @param userType 用户类型
     * @param regionCodes 行政区代码
     * @return
     */
    Page<TransCaUser> findTransCaUser(String userName, Integer userType, Collection<String> regionCodes, Pageable request);

    /**
     * 根据用户Id获取用户对象
     * @param userId 用户id
     * @return
     */
    TransCaUser getTransCaUser(String userId);

    /**
     * 根据用户登录名获取用户对象
     * @param userName
     * @return
     */
    TransCaUser getTransCaUserByUserName(String userName);

    /**
     * @作者 王建明
     * @创建日期 2017/7/3 0003
     * @创建时间 上午 9:02
     * @描述 —— 优先根据指纹取出用户信息，再根据用户名取出CA用户信息
     */
    TransCaUser getTransCaUserByKeyInfo(String userName, String thumbprint);

    /**
     * 根据用户的CA指纹获取用户
     * @param thumbprint
     * @return
     */
    TransCaUser getTransCaUserByCAThumbprint(String thumbprint);

    /**
     * 删除用户
     * @param userIds 用户Ids
     */
    void deleteTransCaUser(Collection<String> userIds);

    /**
     * 保存用户
     * @param transCaUser 用户对象
     * @return
     */
    TransCaUser saveTransCaUser(TransCaUser transCaUser);
}
