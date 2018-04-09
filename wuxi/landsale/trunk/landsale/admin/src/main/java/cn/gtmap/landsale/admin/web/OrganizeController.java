package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransOrganizeClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.service.TransRoleOrganizeService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganize;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.TransRoleOrganize;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 组织管理
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Controller
@RequestMapping("/organize")
public class OrganizeController {

    @Autowired
    TransOrganizeClient transOrganizeClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransRoleOrganizeService transRoleOrganizeService;


    @RequestMapping("/index")
    public String index(Model model, String organizeName, @PageDefault(value = 4) Pageable pageable) {
        String regionCodes = null;
        if (!SecUtil.isAdmin()) {
            regionCodes = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransOrganize> transOrganizeList = transOrganizeClient.findTransOrganizePage(organizeName, regionCodes, pageable);
        model.addAttribute("transOrganizeList", transOrganizeList);
        model.addAttribute("organizeName", organizeName);
        return "organize/organize-list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "organize/organize-add";
    }

    @RequestMapping("/addOrganize")
    @ResponseBody
    public ResponseMessage<TransOrganize> addOrganize(TransOrganize transOrganize, String regionCodes) {
        return transOrganizeClient.saveTransOrganize(transOrganize, regionCodes);
    }

    @RequestMapping("/edit")
    public String edit(Model model, String organizeId) {
        // 查找组织
        TransOrganize transOrganize = transOrganizeClient.getTransOrganizeById(organizeId);
        // 查号组织所属行政区
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByOrganize(transOrganize.getOrganizeId());
        String regionCodes = "";
        String regionNames = "";
        for (TransRegion transRegion : transRegionList) {
            if (StringUtils.isEmpty(regionCodes)) {
                regionCodes = transRegion.getRegionCode();
                regionNames = transRegion.getRegionName();
            } else {
                regionCodes += "," + transRegion.getRegionCode();
                regionNames += "," + transRegion.getRegionName();
            }
            model.addAttribute("regionCodes", regionCodes);
            model.addAttribute("regionNames", regionNames);
        }
        model.addAttribute("transOrganize", transOrganize);
        return "organize/organize-add";
    }

    @RequestMapping("/editOrganize")
    @ResponseBody
    public ResponseMessage<TransOrganize> editOrganize(TransOrganize transOrganize, String regionCodes) {
        return transOrganizeClient.updateTransOrganize(transOrganize, regionCodes);
    }

    @RequestMapping("/deleteOrganize")
    @ResponseBody
    public ResponseMessage deleteOrganize(TransOrganize transOrganize) {
        // 根据组织 查找是否有角色 绑定该组织
        List<TransRoleOrganize> transRoleOrganizeList = transRoleOrganizeService.findTransRoleOrganizeByRoleIdOrOrganizeId(null, transOrganize.getOrganizeId());
        if (transRoleOrganizeList != null && transRoleOrganizeList.size() > 0) {
            return new ResponseMessage(false, "该组织尚有角色绑定,无法删除！");
        }
        return transOrganizeClient.deleteTransOrganize(transOrganize);
    }
}

