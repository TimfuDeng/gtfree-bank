package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.model.TransRoleUser;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.service.RegionService;
import cn.gtmap.landsale.service.TransRoleService;
import cn.gtmap.landsale.service.TransRoleUserService;
import cn.gtmap.landsale.service.TransUserService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** 角色管理
 * Created by trr on 2015/10/12.
 */
@Controller
@RequestMapping(value = "console/role")
public class RoleController extends BaseController {
    @Autowired
    TransRoleService transRoleService;
    @Autowired
    TransUserService transUserService;
    @Autowired
    TransRoleUserService transRoleUserService;
    @Autowired
    RegionService regionService;

    @RequestMapping(value = "list")
    public String list(@PageDefault(value = 10) Pageable page,String title,Model model){
        Page<TransRole> transRolePage=null;
        transRolePage= transRoleService.findTransRole(title,page);
        model.addAttribute("transRolePage",transRolePage);
        model.addAttribute("title",title);

        return "role/role-list";
    }



    @ModelAttribute("transRole")
    public TransRole getRole(@RequestParam(value = "roleId",required = false)String roleId){
        return StringUtils.isBlank(roleId)? new TransRole():transRoleService.getTransRole(roleId);
    }

    @RequestMapping(value = "save")
    public String save (@ModelAttribute("transRole")TransRole transRole,String userIds,RedirectAttributes ra,Model model){
        if(StringUtils.isBlank(transRole.getRoleId())){
            transRole.setRoleId(null);
        }
        if(StringUtils.isNotBlank(userIds)){
            transRoleUserService.batchSaveTransRoleUser(transRole,userIds);
        }else{
            transRoleService.saveTransRole(transRole);
        }

        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg","保存成功");
        return "redirect:/console/role/edit?roleId="+transRole.getRoleId();

    }

    @RequestMapping(value = "edit")
    public String transRoles( String roleId,Model model){
        TransRole transRole=null;
        List<TransRoleUser> transRoleUserList=null;
        if(StringUtils.isNotBlank(roleId)){
            transRole=transRoleService.getTransRole(roleId);
        }else{
            transRole=new TransRole();
        }
        List<TransUser> transUserList= transUserService.getTransUserByTransRoleUser(roleId);

        model.addAttribute("transRole", transRole);
        model.addAttribute("transUserList", transUserList);

        return "role/role-edit";
    }
    @RequestMapping(value = "getTransUserRole.f")
    public String getTransUserRole(String roleId,Model model){
        List<TransUser> transUserList= transUserService.getTransUserByTransRoleUser(roleId);
        model.addAttribute("transUserList",transUserList);
        return "/common/user-list";
    }

    @RequestMapping(value = "changeTransUserRole.f")
    @ResponseBody
    public String changeTransUserRole(String roleId,String userIds,Model model){
        try {
            transRoleUserService.batchTransRoleUser(roleId, userIds);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

    }

    @RequestMapping(value = "view")
    public String viewTransNews( String roleId,Model model){
        TransRole transRole=null;
        transRole=transRoleService.getTransRole(roleId);
        List<TransUser> transUserList= transUserService.getTransUserByTransRoleUser(roleId);
        model.addAttribute("transRole",transRole);
        model.addAttribute("transUserList",transUserList);
        return "role/role";
    }


    @RequestMapping(value = "delete.f")
    @ResponseBody
    public String deleteTreansRole(@RequestParam(value = "roleIds",required = false) String roleIds){
        List<String> roleId= Lists.newArrayList(roleIds.split(";"));
        for(String id:roleId){
           List<TransRoleUser> transRoleUserList= transRoleUserService.findTransRoleUserList(null, id);
            if(transRoleUserList.size()>0){
                return "false";
            }
        }
        transRoleService.deleteTransRole(roleId);
        return "true";
    }

    @RequestMapping("grant")
    public String grant(String roleId, Model model) {
        TransRole transRole = transRoleService.getTransRole(roleId);
        model.addAttribute("transRole", transRole);
        model.addAttribute("urlResources", transUserService.getAvailableUrlResources());
        model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        setPrivileges2Model(model, transRole.getPrivilege());
        return "role/role-grant";
    }

    private void setPrivileges2Model(Model model, String value) {
        Map<String, String> resourcePrivileges = Maps.newHashMap();
        Set<String> regionDepts = Sets.newHashSet();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(value)) {
            Map privilegeMap = JSON.parseObject(value);
            List<Map> resources = (List<Map>) privilegeMap.get("resources");
            regionDepts = Sets.newHashSet((List) privilegeMap.get("regionDepts"));
            for (Map resource : resources) {
                resourcePrivileges.put(String.valueOf(resource.get("name")), String.valueOf(resource.get("url")));
            }

        }
        model.addAttribute("resourcePrivileges", resourcePrivileges);
        model.addAttribute("regionDeptsPrivileges", regionDepts);
    }

    @RequestMapping("grant/save")
    public String saveGrant(@RequestParam(value = "roleId", required = true) String roleId, String privileges, RedirectAttributes ra) {
        transRoleService.updateRolePrivileges(roleId,privileges);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/console/role/grant?roleId=" + roleId;
    }

}
