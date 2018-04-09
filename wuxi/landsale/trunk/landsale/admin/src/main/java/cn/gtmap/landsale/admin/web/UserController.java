package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.TransRoleService;
import cn.gtmap.landsale.admin.service.TransUserService;
import cn.gtmap.landsale.admin.service.TransUserViewService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRole;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.model.TransUserView;
import cn.gtmap.landsale.common.security.SecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户管理
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransUserService transUserService;

    @Autowired
    TransUserViewService transUserViewService;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransRoleService transRoleService;

    @RequestMapping("index")
    public String index(String viewName, Integer userType, String regionCodes, @PageDefault(value = 10) Pageable page, Model model) {

        // TODO 登陆后处理 不是系统管理员 根据所属的部门查找行政区 系统管理员 查找所有
//        if (!SecUtil.isAdmin()) { 1556
//            model.addAttribute("regionAllList", transRegionClient.findTransRegionByOrganize(organizeId));
//        } else {
//            model.addAttribute("regionAllList", transRegionClient.getRegionTree(null));
//        }
        Page<TransUserView> transUserList = transUserViewService.findTransUserPage(viewName, userType, regionCodes, page);
        model.addAttribute("transUserList", transUserList);
        model.addAttribute("viewName", viewName);
        model.addAttribute("userType", userType);
        model.addAttribute("regionCodes", regionCodes);
        return "user/user-list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        if (SecUtil.isAdmin()) {
            List<TransRole> roleList = transRoleService.getTransRole();
            model.addAttribute("roleList", roleList);
        } else {
            String regionCodes = SecUtil.getLoginUserRegionCodes();
            // 通过当前用户部门所属的行政区 查找所有的角色
            List<TransRole> roleList = transRoleService.getTransRoleByRegion(regionCodes);
            model.addAttribute("roleList", roleList);
        }
        return "user/user-add";
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public ResponseMessage<TransUser> addUser(TransUser transUser, String roleId) {
        return transUserService.saveTransUser(transUser, roleId);
    }

    @RequestMapping("/edit")
    public String edit(Model model, String userId) {
        TransUser transUser = transUserClient.getTransUserById(userId);
        if (SecUtil.isAdmin()) {
            List<TransRole> roleList = transRoleService.getTransRole();
            model.addAttribute("roleList", roleList);
        } else {
            String regionCodes = SecUtil.getLoginUserRegionCodes();
            // 通过当前用户部门所属的行政区 查找所有的角色
            List<TransRole> roleList = transRoleService.getTransRoleByRegion(regionCodes);
            model.addAttribute("roleList", roleList);
        }
        TransUserView transUserView = transUserViewService.getTransUserViewById(userId);
        model.addAttribute("transUser", transUserView);
        return "user/user-add";
    }

    @RequestMapping("/editUser")
    @ResponseBody
    public ResponseMessage<TransUser> editUser(TransUser transUser, String roleId) {
        return transUserService.updateTransUser(transUser, roleId);
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public ResponseMessage<TransUser> deleteUser(String userIds) {
        return transUserService.deleteTransUser(userIds);
    }


}

