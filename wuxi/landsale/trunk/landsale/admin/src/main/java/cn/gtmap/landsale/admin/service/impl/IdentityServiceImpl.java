package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.security.ex.CaCertificateException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.admin.register.TransOrganizeClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.CaSvsService;
import cn.gtmap.landsale.admin.service.IdentityService;
import cn.gtmap.landsale.admin.service.TransMenuService;
import cn.gtmap.landsale.admin.service.TransRoleService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.log.AuditServiceLog;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户登录服务
 * @author zsj
 * @version v1.0, 2017/10/17
 */
@Service
public class IdentityServiceImpl implements IdentityService {

    private static Set<Map> urlResources= Sets.newHashSet();

    private static Set regionDeptsPrivileges= Sets.newHashSet();

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransMenuService transMenuService;

    @Autowired
    TransRoleService transRoleService;

    @Autowired
    TransOrganizeClient transOrganizeClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    CaSvsService caSvsService;

    @Override
    public  Set<Map> getUrlResources() {
        return urlResources;
    }

    @Override
    public Set getRegionDeptsPrivileges() {
        return regionDeptsPrivileges;
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
    public ResponseMessage<TransUser> login(String userName, String password, Constants.UserType userType) {
        ResponseMessage<TransUser> responseMessage = transUserClient.validatePassword(userName, password, Constants.UserType.MANAGER);
        if (responseMessage.getFlag()) {
            TransUser transUser = responseMessage.getEmpty();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SecUtil.setLoginUserToSession(request, transUser);
            SecUtil.setLoginUserToLocal(transUser, request);
            TransRole transRole = transRoleService.getTransRoleByUserId(transUser.getUserId());
            if (transRole != null) {
                setIdentity(request, transRole);
            } else {
                return new ResponseMessage(false, "用户" + userName + "未分配角色,无法登录系统!", transUser);
            }
            return new ResponseMessage(true, "登录成功", transUser);
        }
        return responseMessage;
    }

    private void setIdentity(HttpServletRequest request, TransRole transRole) {

        SecUtil.setLoginRoleToSession(request, transRole);
        // 根据角色获取所属 组织
        List<TransOrganize> transOrganizeList = transOrganizeClient.findTransOrganizeByRole(transRole.getRoleId());
        SecUtil.setLoginOrganizeToSession(request, transOrganizeList);
        // 根据角色 获取行政区
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByRole(transRole.getRoleId());
        SecUtil.setLoginRegionToSession(request, transRegionList);
        // 根据角色 获取按钮权限
        List<String> menuIconList = getMenuIcon(transRole);
        SecUtil.setLoginButtonToSession(request, menuIconList);
    }

    private List<String> getMenuIcon(TransRole transRole) {
        List<TransMenu> transMenuList = Lists.newArrayList();
        if (SecUtil.isAdmin()) {
            transMenuList = transMenuService.getTransMenuList();
        } else {
            transMenuList = transMenuService.getTransMenuListByRole(transRole.getRoleId());
        }
        List<String> menuIconList = Lists.newArrayList();
        menuIconList.addAll(transMenuList.stream().filter(transMenu -> transMenu.getMenuType() == 9).map(TransMenu::getMenuIcon).collect(Collectors.toList()));
        return menuIconList;
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
            TransUser transUser = transUserClient.getTransUserByCAThumbprint(caSignerX.getCertThumbprint());
            if(transUser==null){
                throw new UserNotFoundException(caSignerX.getCertFriendlyName());
            }else if(transUser.getType()!= Constants.UserType.MANAGER){
                throw new UserLockedException(caSignerX.getCertFriendlyName());
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            transUser = getRolePrivilege(transUser);
            SecUtil.setLoginUserToSession(request, transUser);

        }else {
            throw new CaCertificateException(caSignerX.getCertFriendlyName());
        }

    }

    /**
     * 用户角色的权限进行合并
     * @param transUser
     * @return
     */
    private TransUser getRolePrivilege(TransUser transUser){
        Map setPrivilege=new HashMap();
        /*List<Map> resourcePrivileges=new ArrayList<Map>();
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
        String jsonPrivilege= JSON.toJSONString(setPrivilege);
        transUser.setPrivilege(jsonPrivilege);*/
        return transUser;
    }

    @Override
    /*@AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "客户系统CA用户登录")*/
    public void clientCaLogin(CaSignerX caSignerX) throws Exception{
        if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){
            TransUser transUser = transUserClient.getTransUserByCAThumbprint(caSignerX.getCertThumbprint());
            if(transUser==null){
                transUser = new TransUser();
                transUser.setUserId(caSignerX.getCertThumbprint());
                transUser.setUserName(caSignerX.getCertFriendlyName());
                transUser.setViewName(caSignerX.getCertFriendlyName());
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SecUtil.setLoginUserToSession(request, transUser);
        }else {
            throw new CaCertificateException(caSignerX.getCertFriendlyName());
        }
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
