package cn.gtmap.landsale.service;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.CaSignerX;

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
    public void login(String userName,String password,Integer userType);

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
}
