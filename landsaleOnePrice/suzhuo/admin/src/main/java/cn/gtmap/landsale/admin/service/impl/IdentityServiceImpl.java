package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/10
 */
public class IdentityServiceImpl implements IdentityService {

    private SysCakeyService sysCakeyService;
    private SysUserService sysUserService;
    private OneRoleService oneRoleService;
    CaSvsService caSvsService;



    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    public void setSysCakeyService(SysCakeyService sysCakeyService) {
        this.sysCakeyService = sysCakeyService;
    }

    public void setOneRoleService(OneRoleService oneRoleService) {
        this.oneRoleService = oneRoleService;
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
    public void login(String userName, String password, Integer userType) {
        SysUser sysUser =  sysUserService.validatePassword(userName, password, userType);
        OneRole oneRole =  oneRoleService.getOneRoleByTransUserId(sysUser.getId());
        TransUser transUser = new TransUser();
        transUser.setUserId(sysUser.getId());
        transUser.setUserName(sysUser.getUserName());
        transUser.setViewName(sysUser.getUserName());
        if (oneRole!=null){
            transUser.setPrivilege(oneRole.getPrivilege());
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
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
            SysCakey sysCakey=sysCakeyService.getSysCakey(caSignerX.getSerialNumber());
            if (sysCakey==null){
                throw new UserNotFoundException(caSignerX.getCertFriendlyName());
            }
            SysUser sysUser = sysUserService.getSysUser(sysCakey.getUserId());
            if(sysUser==null){
                throw new UserNotFoundException(caSignerX.getCertFriendlyName());
            }
            if(sysUser.getUserType()!=0){
                throw new UserLockedException(caSignerX.getCertFriendlyName());
            }
            OneRole oneRole =  oneRoleService.getOneRoleByTransUserId(sysUser.getId());
            TransUser transUser = new TransUser();
            transUser.setUserId(sysUser.getId());
            transUser.setUserName(sysUser.getUserName());
            transUser.setViewName(sysUser.getUserName());
            if (oneRole!=null){
                transUser.setPrivilege(oneRole.getPrivilege());
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SecUtil.setLoginUserIdToSession(request, transUser);
        }else
            throw new CaCertificateException(caSignerX.getCertFriendlyName());

    }

    @Override
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "客户系统CA用户登录")
    public void clientCaLogin(CaSignerX caSignerX) throws Exception{
        if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){

            SysCakey sysCakey=sysCakeyService.getSysCakey(caSignerX.getSerialNumber());
            if (sysCakey==null){
                throw new UserNotFoundException(caSignerX.getCertFriendlyName());
            }
            SysUser sysUser = sysUserService.getSysUser(sysCakey.getUserId());
            if(sysUser==null){
                throw new UserNotFoundException(caSignerX.getCertFriendlyName());
            }
            /*if(sysUser.getUserType()!=1){
                throw new UserLockedException(caSignerX.getCertFriendlyName());
            }*/
            TransUser transUser = new TransUser();
            transUser.setUserId(sysUser.getId());
            transUser.setUserName(sysUser.getUserName());
            transUser.setViewName(sysUser.getUserName());
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
