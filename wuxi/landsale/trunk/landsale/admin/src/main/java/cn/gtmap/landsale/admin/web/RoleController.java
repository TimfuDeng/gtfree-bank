package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransOrganizeClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.service.TransMenuService;
import cn.gtmap.landsale.admin.service.TransRoleMenuService;
import cn.gtmap.landsale.admin.service.TransRoleOrganizeService;
import cn.gtmap.landsale.admin.service.TransRoleService;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    TransRoleService transRoleService;

    @Autowired
    TransMenuService transMenuService;

    @Autowired
    TransRoleMenuService transRoleMenuService;

    @Autowired
    TransOrganizeClient transOrganizeClient;

    @Autowired
    TransRoleOrganizeService transRoleOrganizeService;

    @Autowired
    TransRegionClient transRegionClient;

    @RequestMapping("/index")
    public String index(Model model, @PageDefault(value = 4) Pageable page, String roleName, HttpServletRequest request) {
        Page<TransRole> transRoleList = transRoleService.getTransRolePage(roleName, page);
        model.addAttribute("transRoleList", transRoleList);
        model.addAttribute("roleName", roleName);
        SecUtil.getLoginUserToSession(request);
        SecUtil.getLoginUserViewName();
        return "role/role-list";
    }

    @RequestMapping("/getRolePage")
    @ResponseBody
    public Page<TransRole> getRolePage(@PageDefault(value = 10) Pageable page, String roleName) {
        Page<TransRole> transRoleList = transRoleService.getTransRolePage(roleName, page);
        return transRoleList;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        //TODO 根据当前用户所属行政区 获取组织
        List<TransOrganize> transOrganizeList = transOrganizeClient.findTransOrganizeList(null, "32");
        model.addAttribute("transOrganizeList", transOrganizeList);
        return "role/role-add";
    }


    @RequestMapping("/addRole")
    @ResponseBody
    public Map<String, Object> addRole(TransRole transRole, String organizeId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        transRole.setCreateTime(new Date());
        transRole.setRoleId(null);
        transRole = transRoleService.addTransRole(transRole, organizeId);
        resultMap.put("transRole", transRole);
        resultMap.put("organizeId", organizeId);
        resultMap.put("result", true);
        resultMap.put("msg", "保存成功！");
        return resultMap;
    }

    @RequestMapping("/edit")
    public String edit(Model model, String roleId) {
        TransRole transRole = transRoleService.getTransRoleById(roleId);
        // 根据角色查找组织编号
        List<TransRoleOrganize> transRoleOrganizeList = transRoleOrganizeService.findTransRoleOrganizeByRoleIdOrOrganizeId(roleId, null);
        if (transRoleOrganizeList != null && transRoleOrganizeList.size() == 1) {
            model.addAttribute("organizeId", transRoleOrganizeList.get(0).getOrganizeId());
        }
        //TODO 根据当前用户所属行政区 获取组织
        List<TransOrganize> transOrganizeList = transOrganizeClient.findTransOrganizeList(null, "32");
        model.addAttribute("transOrganizeList", transOrganizeList);
        model.addAttribute("transRole", transRole);
        return "role/role-add";
    }


    @RequestMapping("/editRole")
    @ResponseBody
    public Map<String, Object> editRole(TransRole transRole, String organizeId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        transRole.setCreateTime(new Date());
        transRole = transRoleService.updateTransRole(transRole, organizeId);
        resultMap.put("transRole", transRole);
        resultMap.put("organizeId", organizeId);
        resultMap.put("result", true);
        resultMap.put("msg", "保存成功！");
        return resultMap;
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public Map<String, Object> deleteRole(String roleId) {
        Map<String, Object> resultMap = transRoleService.deleteTransRole(roleId);
        return resultMap;
    }

    @RequestMapping("/grant")
    public String grant(Model model, String roleId, HttpServletRequest request) {
        // 角色信息
        TransRole transRole = transRoleService.getTransRoleById(roleId);
        // 授权人 所有的 菜单信息
        List<TransMenu> transRoleMenuList;
        if (SecUtil.isAdmin()) {
            transRoleMenuList = transMenuService.getTransMenuButtonList();
        } else {
            TransRole adminRole = SecUtil.getLoginRoleToSession(request);
            transRoleMenuList = transMenuService.getTransMenuButtonListByRole(adminRole.getRoleId());
        }
        // 已授权菜单信息
        List<TransMenu> transRoleMenuExists = transMenuService.getTransMenuListByRole(roleId);
        List<String> menuIdList = Lists.newArrayList();
        if (transRoleMenuExists != null && transRoleMenuExists.size() > 0) {
            transRoleMenuExists.forEach((transMenu) -> menuIdList.add(transMenu.getMenuId()));
        }
        // 已授权行政区
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByRoleRegionOperation(roleId);
        if (transRegionList != null && transRegionList.size() > 0) {
            StringBuilder regionName = new StringBuilder();
            StringBuilder regionCode = new StringBuilder();
            for (TransRegion transRegion : transRegionList) {
                if (StringUtils.isEmpty(regionCode.toString())) {
                    regionName.append(transRegion.getRegionName());
                    regionCode.append(transRegion.getRegionCode());
                } else {
                    regionName.append("," + transRegion.getRegionName());
                    regionCode.append("," + transRegion.getRegionCode());
                }
            }
            model.addAttribute("regionName", regionName);
            model.addAttribute("regionCode", regionCode);
        }
        // 当前用户所属的行政区
        if (!SecUtil.isAdmin()) {
            String regionCodes = SecUtil.getLoginUserRegionCodes();
            model.addAttribute("regionCodes", regionCodes);
        }
        model.addAttribute("transRole", transRole);
        model.addAttribute("transRoleMenuList", transRoleMenuList);
        model.addAttribute("menuIdList", menuIdList);
        return "role/role-grant";
    }

    @RequestMapping("/grantRole")
    @ResponseBody
    public Map<String, Object> grantRole(String menuIds, String roleId, String regionCodes) {
        Map<String, Object> resultMap = transRoleService.grantRole(menuIds, roleId, regionCodes);
        return resultMap;
    }


}

