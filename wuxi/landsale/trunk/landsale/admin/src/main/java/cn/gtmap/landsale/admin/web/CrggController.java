package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.core.TransResourceContainer;
import cn.gtmap.landsale.admin.register.*;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 出让公告服务
 * @author zsj
 * @version v1.0, 2017/10/19
 */
@Controller
@RequestMapping("/crgg")
public class CrggController {

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransMaterialClient transMaterialClient;

    @Autowired
    TransCrggMaterialClient transCrggMaterialClient;

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransResourceContainer transResourceTimer;


    private static final String PERSONAL_MATERIAL = "PERSONAL";

    private static final String GROUP_MATERIAL = "GROUP";

    @RequestMapping("/index")
    public String index(Model model, String title, @PageDefault(value = 10) Pageable pageable, HttpServletRequest request) {
        String regionCodes = null;
        if (!SecUtil.isAdmin()) {
            regionCodes = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransCrgg> transCrggList = transCrggClient.findTransCrgg(title, regionCodes, pageable);
        model.addAttribute("regionCodes", regionCodes);
        model.addAttribute("transCrggList", transCrggList);
        model.addAttribute("title", title);
        return "crgg/crgg-list";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        if (!SecUtil.isAdmin()) {
            String regionCodes = SecUtil.getLoginUserRegionCodes();
            model.addAttribute("regionCodes", regionCodes);
        }
        TransCrgg transCrgg=new TransCrgg();
        transCrgg.setGgBeginTime(new Date());
        transCrgg.setGgEndTime(new Date());
        transCrgg.setSignStartTime(new Date());
        transCrgg.setSignEndTime(new Date());
        transCrgg.setPostDate(new Date());
        transCrgg.setPassMan(SecUtil.getLoginUserViewName());
        model.addAttribute("transCrgg", transCrgg);
        return "crgg/crgg-add";
    }

    @RequestMapping("/addCrgg")
    @ResponseBody
    public ResponseMessage<TransCrgg> addTransBankConfig(TransCrgg transCrgg, @RequestParam(value = "materialIds",required = false) String materialIds) {
        transCrgg.setGgId(null);
        return transCrggClient.saveTransCrgg(transCrgg, materialIds);
    }

    @RequestMapping("/edit")
    public String edit(Model model, String crggId) {
        TransCrgg transCrgg = transCrggClient.getTransCrgg(crggId);
        TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(transCrgg.getRegionCode());
        model.addAttribute("transCrgg", transCrgg);
        model.addAttribute("transRegion", transRegion);
        return "crgg/crgg-add";
    }

    @RequestMapping("/editCrgg")
    @ResponseBody
    public ResponseMessage<TransCrgg> editCrgg(TransCrgg transCrgg, @RequestParam(value = "materialId", required = false) String materialIds) {
        return transCrggClient.saveTransCrgg(transCrgg, materialIds);
    }

    @RequestMapping("/deleteCrgg")
    @ResponseBody
    public ResponseMessage deleteCrgg(String crggIds) {
        return transCrggClient.deleteTransCrgg(crggIds);
    }

    @RequestMapping("/getPersonalLoad")
    public String getPersonalMaterialLoad(String regionCode,Model model){
        model.addAttribute("materialPersonList", transMaterialClient.getMaterialsBymaterialType(regionCode, PERSONAL_MATERIAL));
        return "crgg/material_list_person";
    }

    @RequestMapping("/getGroupLoad")
    public String getGroupMaterialLoad(String regionCode,Model model){
        model.addAttribute("materialGroupList", transMaterialClient.getMaterialsBymaterialType(regionCode, GROUP_MATERIAL));
        return "crgg/material_list_group";
    }

    @RequestMapping("/materials/checked")
    @ResponseBody
    public List<TransMaterial> listMaterials(String ggId,HttpServletResponse response){
        List<MaterialCrgg> materialCrggs = Lists.newArrayList();
        List<TransMaterial> transMaterials = Lists.newArrayList();
        if (StringUtils.isNotBlank(ggId)) {
            materialCrggs = transCrggMaterialClient.getMaterialCrggByCrggId(ggId);
            for(MaterialCrgg mc : materialCrggs){
                transMaterials.add(transMaterialClient.getMaterialsById(mc.getMaterialId()));
            }
        }
        return transMaterials;
    }

    /**
     * 发布 撤销 公告
     * @param crggId
     * @param status
     * @return
     */
    @RequestMapping("/status/change")
    @ResponseBody
    public ResponseMessage changeStatus(String crggId, int status) {
        if (StringUtils.isNotBlank(crggId)) {
            TransCrgg transCrgg = transCrggClient.getTransCrgg(crggId);
            transCrgg.setCrggStauts(status);
            return transCrggClient.updataTransCrgg(transCrgg);
        }
        return new ResponseMessage<>(false, "公告编号为空,请检查！");
    }

    /**
     * 发布公告下所有地块
     * @param crggId
     * @param status
     * @return
     */
    @RequestMapping("/status/resource/change")
    @ResponseBody
    public ResponseMessage changeStatusResource(String crggId, int status) {
        ResponseMessage responseMessage = new ResponseMessage<>(true);
        if (StringUtils.isNotBlank(crggId)) {
            List<TransResource> resourceList = transResourceClient.findTransResource(crggId);
            for(TransResource transResource : resourceList){
                if (responseMessage.getFlag()) {
                    transResource.setResourceEditStatus(status);
                    responseMessage = transResourceClient.saveTransResource(transResource);
                    try {
                        //检查资源线程
                        transResourceTimer.checkResource(transResource);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return responseMessage;
        }
        return new ResponseMessage<>(false, "公告编号为空,请检查！");
    }

}

