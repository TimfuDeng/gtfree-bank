package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.CaSignerX;
import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.model.TransRoleUser;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.security.SecurityContext;
import cn.gtmap.landsale.service.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian,liushaoshuai</a>
 * @version 1.0, 2015/6/10
 *
 * 用户权限修改为用户角色权限
 */
public class IdentityServiceImpl implements IdentityService {
    private static Set<Map> urlResources= Sets.newHashSet();
    private static Set regionDeptsPrivileges= Sets.newHashSet();

    private TransUserService transUserService;

    private TransRoleUserService transRoleUserService;
    private TransRoleService transRoleService;
    CaSvsService caSvsService;

    public  Set<Map> getUrlResources() {
        return urlResources;
    }

    public Set getRegionDeptsPrivileges() {
        return regionDeptsPrivileges;
    }

    public void setTransRoleUserService(TransRoleUserService transRoleUserService) {
        this.transRoleUserService = transRoleUserService;
    }

    public void setTransRoleService(TransRoleService transRoleService) {
        this.transRoleService = transRoleService;
    }

    public void setTransUserService(TransUserService transUserService) {
        this.transUserService = transUserService;
    }

    public void setCaSvsService(CaSvsService caSvsService) {
        this.caSvsService = caSvsService;
    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @param userType
     */
    @Override
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.ADMIN,
            description = "用户登录")
    public void login(String userName, String password, Constants.UserType userType) {
        TransUser transUser = transUserService.validatePassword(userName, password, Constants.UserType.MANAGER);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        transUser = getRolePrivilege(transUser);
        SecUtil.setLoginUserIdToSession(request, transUser);

    }

    /**
     * 管理系统CA登录
     *
     * @param caSignerX
     */
    @Override
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.ADMIN,
            description = "管理系统CA用户登录")
    public void adminCaLogin(CaSignerX caSignerX) throws Exception {
        if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){
            TransUser transUser = transUserService.getTransUserByCAThumbprint(caSignerX.getCertThumbprint());
            if(transUser==null){
                throw new UserNotFoundException(caSignerX.getCertFriendlyName());
            }else if(transUser.getType()!= Constants.UserType.MANAGER){
                throw new UserLockedException(caSignerX.getCertFriendlyName());
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            transUser = getRolePrivilege(transUser);
            SecUtil.setLoginUserIdToSession(request, transUser);

        }else
            throw new CaCertificateException(caSignerX.getCertFriendlyName());

    }

    /**
     * 用户角色的权限进行合并
     * @param transUser
     * @return
     */
    private TransUser getRolePrivilege(TransUser transUser){
        Map setPrivilege=new HashMap();
        List<Map> resourcePrivileges=new ArrayList<Map>();
        List region=new ArrayList();
        List regionDepts=new ArrayList();
        List<TransRoleUser> listRoleUser= transRoleUserService.findTransRoleUserList(transUser.getUserId(), null);
        List<TransRole> listRole=new ArrayList<TransRole>();
        for(TransRoleUser transRoleUser:listRoleUser){
           TransRole transRole = transRoleService.getTransRole(transRoleUser.getRoleId());
            if (StringUtils.isNotBlank(transRole.getPrivilege())) {
                Map privilegeMap = JSON.parseObject(transRole.getPrivilege());
                if (privilegeMap.containsKey("regions")){
                    List regionSub=(List)privilegeMap.get("regions");
                    region.addAll(regionSub);
                }
                 if (privilegeMap.containsKey("regionDepts")){
                     List regionDeptSub=(List)privilegeMap.get("regionDepts");
                     regionDepts.addAll(regionDeptSub);
                 }
                if (privilegeMap.containsKey("resources")) {
                    List<Map> resourcePrivilegeSub =(List<Map>) privilegeMap.get("resources");
                    resourcePrivileges.addAll(resourcePrivilegeSub);
                }

            }
        }
        setPrivilege.put("regions", Sets.newHashSet(region));
        regionDeptsPrivileges=Sets.newHashSet(regionDepts);
        setPrivilege.put("regionDepts", regionDeptsPrivileges);
        urlResources=Sets.newHashSet(resourcePrivileges);
        setPrivilege.put("resources",  urlResources);
        String jsonPrivilege=JSON.toJSONString(setPrivilege);
        transUser.setPrivilege(jsonPrivilege);
        return transUser;
    }

    @Override
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "客户系统CA用户登录")
    public void clientCaLogin(CaSignerX caSignerX) throws Exception{
        if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){
            TransUser transUser = transUserService.getTransUserByCAThumbprint(caSignerX.getCertThumbprint());
            if(transUser==null){
                transUser = new TransUser();
                transUser.setUserId(caSignerX.getCertThumbprint());
                transUser.setUserName(caSignerX.getCertFriendlyName());
                transUser.setViewName(caSignerX.getCertFriendlyName());
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SecUtil.setLoginUserIdToSession(request, transUser);
        }else
            throw new CaCertificateException(caSignerX.getCertFriendlyName());
    }

    /**
     * 登出
     */
    @Override
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGOUT,producer = Constants.LogProducer.ADMIN,
            description = "用户登出")
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SecUtil.logout4Session(request);
    }
}
