package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;

import cn.gtmap.landsale.model.OneRole;
import cn.gtmap.landsale.model.SysUser;

import cn.gtmap.landsale.service.OneRoleService;

import cn.gtmap.landsale.service.SysUserService;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户管理
 *
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/15
 */
@Controller
@RequestMapping(value = "oneprice/user")
public class UserController {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    OneRoleService oneRoleService;



    @ModelAttribute("user")
    public SysUser getUser(@RequestParam(value = "userId", required = false) String userId) {
        return StringUtils.isBlank(userId) ? new SysUser() : sysUserService.getSysUser(userId);
    }
    @RequestMapping("list")
    public String list(@PageDefault(value = 10) Pageable page, String userAlias, @RequestParam(value = "userType", required = false, defaultValue = "0") Integer userType,
            Model model) {
        Page<SysUser> sysUserList=sysUserService.findSysUser(userAlias,userType,page);
        model.addAttribute("sysUserList", sysUserList);
        model.addAttribute("userAlias", userAlias);
        model.addAttribute("userType", userType);
        return "admin/user-list";
    }
    @RequestMapping("grant")
    public String grant(String userId, Model model) {
        SysUser sysUser = sysUserService.getSysUser(userId);
        OneRole oneRole = oneRoleService.getOneRoleByTransUserId(sysUser.getId());
        model.addAttribute("sysUser", sysUser);
        model.addAttribute("urlResources", sysUserService.getAvailableUrlResources());
        if (null!=oneRole)
        setPrivileges2Model(model, oneRole.getPrivilege());
        return "admin/user-grant";
    }
    @RequestMapping("grant/save")
    public String saveGrant(@RequestParam(value = "userId", required = true) String userId, String privileges, RedirectAttributes ra) {
        SysUser sysUser = sysUserService.getSysUser(userId);
        oneRoleService.updateUserPrivileges(sysUser.getId(), privileges);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/oneprice/user/grant?userId=" + userId;
    }
    private void setPrivileges2Model(Model model, String value) {
        Map<String, String> resourcePrivileges = Maps.newHashMap();
        if (StringUtils.isNotBlank(value)) {
            Map privilegeMap = JSON.parseObject(value);
            List<Map> resources = (List<Map>) privilegeMap.get("resources");
            for (Map resource : resources) {
                resourcePrivileges.put(String.valueOf(resource.get("name")), String.valueOf(resource.get("url")));
            }
        }
        model.addAttribute("resourcePrivileges", resourcePrivileges);
    }
}
