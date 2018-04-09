package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.CaSignerX;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;

import java.util.Map;
import java.util.Set;

/**
 * 用户认证服务
 * @author zsj
 * @version v1.0, 2017/9/18
 */
public interface IdentityService {

    /**
     * 登录
     * @param userName
     * @param password
     * @param userType
     * @return
     */
    public ResponseMessage<TransUser> login(String userName, String password, Constants.UserType userType);

    /**
     * 管理系统CA登录
     * @param caSignerX
     * @throws Exception
     */
    public void adminCaLogin(CaSignerX caSignerX) throws Exception;

    /**
     * 客户系统CA用户登录
     * @param caSignerX
     * @throws Exception
     */
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
