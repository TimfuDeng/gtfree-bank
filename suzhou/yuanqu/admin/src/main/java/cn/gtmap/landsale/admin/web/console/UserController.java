package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value = "console/user")
public class UserController {
    @Autowired
    TransUserService transUserService;

    @Autowired
    RegionService regionService;
    @Autowired
    TransDeptService transDeptService;

    @Autowired
    TransRoleService transRoleService;

    @Autowired
    TransRoleUserService transRoleUserService;

    @ModelAttribute("user")
    public TransUser getUser(@RequestParam(value = "userId", required = false) String userId) {
        return StringUtils.isBlank(userId) ? new TransUser() : transUserService.getTransUser(userId);
    }

    @RequestMapping("list")
    public String list(@PageDefault(value = 10) Pageable page, String userAlias, @RequestParam(value = "userType", required = false) Integer userType,
            String regionCode,Model model) {
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin()) {
            regions = SecUtil.getPermittedRegions();
            //model.addAttribute("regionAllList", regionService.findRegionsByRegionCode(regions));
            model.addAttribute("regionAllList", regionService.findRegionsByRegionCode(regions));
        }else{
            //model.addAttribute("regionAllList", regionService.findAllRegions());
            model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        }

        /*if(StringUtils.isNotBlank(regionCode)){
            regions.clear();
            regions.add(regionCode);
        }*/
        Page<TransUser> transUserList = transUserService.findTransUserByType(userAlias, userType,regions, page);
        model.addAttribute("transUserList", transUserList);
        model.addAttribute("userAlias", userAlias);
        model.addAttribute("userType", userType);
        model.addAttribute("regionCode", regionCode);
        if(userType==0){
            return "user-list-view";
        }else{
            return "user-list";
        }

    }

    @RequestMapping("edit")
    public String user(String userId, Model model) {
        TransUser transUser = null;
        if (StringUtils.isNotBlank(userId)) {
            transUser = transUserService.getTransUser(userId);
        } else {
            transUser = new TransUser();
        }

        model.addAttribute("allTransDept", transDeptService.findAllTransDept());
        //model.addAttribute("regionAllList", regionService.findAllRegions());
        model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        model.addAttribute("transUser", transUser);
        return "user-edit";
    }



    @RequestMapping("editForView")
    public String userForView(String userId, Model model) {
        TransUser transUser = null;
        List<String []> regionAllList =regionService.findAllDeptRegions();
        if (StringUtils.isNotBlank(userId)) {
            transUser = transUserService.getTransUser(userId);
        } else {
            transUser = new TransUser();
            transUser.setRegionCode(regionAllList.get(0)[0]);
        }

        model.addAttribute("allTransDept", transDeptService.findAllTransDept());
        //model.addAttribute("regionAllList", regionService.findAllRegions());
        model.addAttribute("regionAllList", regionAllList);
        model.addAttribute("transUser", transUser);
        return "user-editForView";
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("user") TransUser transUser, RedirectAttributes ra, Model model) {
        if (StringUtils.isNotBlank(transUser.getPassword())) {
            if (StringUtils.isBlank(transUser.getUserId())) {
                transUser.setUserId(null);
                transUser.setCreateAt(Calendar.getInstance().getTime());
                TransUser tmpTransUser = transUserService.getTransUserByCAThumbprint(transUser.getCaThumbprint());
                if (tmpTransUser != null) {
                    ra.addFlashAttribute("_result", false);
                    ra.addFlashAttribute("_msg", "CA数字证书信息已存在！");
                    return "redirect:/console/user/edit";
                }
                tmpTransUser = transUserService.getTransUserByUserName(transUser.getUserName());
                if (tmpTransUser != null) {
                    ra.addFlashAttribute("_result", false);
                    ra.addFlashAttribute("_msg", "用户登录名已存在！");
                    return "redirect:/console/user/edit";
                }
            }

            transUser = transUserService.saveTransUser(transUser);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "保存成功！");
        } else {
            ra.addFlashAttribute("_result", false);
            ra.addFlashAttribute("_msg", "密码为空！");
        }
        return "redirect:/console/user/edit?userId=" + transUser.getUserId();
    }

    @RequestMapping("front/save")
    public String saveFront(@ModelAttribute("user") TransUser transUser, RedirectAttributes ra, Model model) {
            if (StringUtils.isBlank(transUser.getUserId())) {
                transUser.setUserId(null);
                transUser.setCreateAt(Calendar.getInstance().getTime());
                TransUser tmpTransUser = transUserService.getTransUserByCAThumbprint(transUser.getCaThumbprint());
                if (tmpTransUser != null) {
                    ra.addFlashAttribute("_result", false);
                    ra.addFlashAttribute("_msg", "CA数字证书信息已存在！");
                    return "redirect:/console/user/edit";
                }
                tmpTransUser = transUserService.getTransUserByUserName(transUser.getUserName());
                if (tmpTransUser != null) {
                    ra.addFlashAttribute("_result", false);
                    ra.addFlashAttribute("_msg", "用户登录名已存在！");
                    return "redirect:/console/user/edit";
                }
            }

            transUser = transUserService.saveTransUser(transUser);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "保存成功！");

        return "redirect:/console/user/editForView?userId=" + transUser.getUserId();
    }

    @RequestMapping("delete.f")
    @ResponseBody
    public String delete(@RequestParam(value = "userIds", required = false) String userIds) {
        List<String> userIdList = Lists.newArrayList(userIds.split(";"));
        /*for(String id:userIdList){
            List<TransRoleUser> transRoleUserList= transRoleUserService.findTransRoleUserList(id, null);
            if(transRoleUserList.size()>0){
                return "false";
            }
        }*/
        if(userIdList.contains("0"))
            throw new AppException(9101);
        String loginUserId = SecUtil.getLoginUserId();
        if(userIdList.contains(loginUserId))
            throw new AppException(9102);
        transUserService.deleteTransUser(userIdList);
        return "true";
    }

    @RequestMapping("grant")
    public String grant(String userId, Model model) {
        TransUser transUser = transUserService.getTransUser(userId);
        model.addAttribute("transUser", transUser);
        model.addAttribute("urlResources", transUserService.getAvailableUrlResources());
        model.addAttribute("regionAllList", regionService.findAllRegions());
        setPrivileges2Model(model, transUser.getPrivilege());
        return "user-grant";
    }

    @RequestMapping("grant/save")
    public String saveGrant(@RequestParam(value = "userId", required = true) String userId, String privileges, RedirectAttributes ra) {
        transUserService.updateUserPrivileges(userId, privileges);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/console/user/grant?userId=" + userId;
    }

    private void setPrivileges2Model(Model model, String value) {
        Map<String, String> resourcePrivileges = Maps.newHashMap();
        Set<String> regions = Sets.newHashSet();
        if (StringUtils.isNotBlank(value)) {
            Map privilegeMap = JSON.parseObject(value);
            List<Map> resources = (List<Map>) privilegeMap.get("resources");
            regions = Sets.newHashSet((List) privilegeMap.get("regions"));
            for (Map resource : resources) {
                resourcePrivileges.put(String.valueOf(resource.get("name")), String.valueOf(resource.get("url")));
            }

        }
        model.addAttribute("resourcePrivileges", resourcePrivileges);
        model.addAttribute("regionPrivileges", regions);
    }

    @RequestMapping(value = "getTransUserRole.f")
    public String getTransUserRole(String userId,Model model){
        List<TransRole> transRoleList= transRoleService.getTransRoleByTransRoleUser(userId);
        model.addAttribute("transRoleList",transRoleList);
        return "/common/role-list";
    }

    @RequestMapping(value = "changeTransUserRole.f")
    @ResponseBody
    public String changeTransUserRole(String userId,String roleIds,Model model){
        try {
            transRoleUserService.batchTransRoleUserByUser(userId,roleIds);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

    }
}
