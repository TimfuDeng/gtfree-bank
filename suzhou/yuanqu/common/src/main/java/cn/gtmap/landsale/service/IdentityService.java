package cn.gtmap.landsale.service;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.CaSignerX;

import java.util.Map;
import java.util.Set;

/**
 * 用户认证服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/10
 */
public interface IdentityService {

    /**
     * 登录
     * @param userName
     * @param password
     * @param userType
     */
    public void login(String userName,String password,Constants.UserType userType);

    /**
     * 管理系统CA登录
     * @param caSignerX
     */
    public void adminCaLogin(CaSignerX caSignerX) throws Exception;

    public void clientCaLogin(CaSignerX caSignerX) throws Exception;

    /**
     * 登出
     */
    public void logout();

    /**
     * 得到权限url
     * @return
     */
    public Set<Map> getUrlResources();

    /**
     * 得到数据权限
     * @return
     */
    public Set getRegionDeptsPrivileges();
}
