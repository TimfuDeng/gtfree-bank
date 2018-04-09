package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.CaSignerX;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.CaSvsService;
import cn.gtmap.landsale.service.IdentityService;
import cn.gtmap.landsale.service.TransUserService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/10
 */
public class IdentityServiceImpl implements IdentityService {
    private TransUserService transUserService;

    CaSvsService caSvsService;

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
            SecUtil.setLoginUserIdToSession(request, transUser);
        }else
            throw new CaCertificateException(caSignerX.getCertFriendlyName());

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
