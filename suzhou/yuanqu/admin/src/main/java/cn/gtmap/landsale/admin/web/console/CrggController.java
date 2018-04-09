package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.MaterialCrgg;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransMaterial;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 出让公告列表
 * Created by Jibo on 2015/4/25.
 */
@Controller
@RequestMapping(value = "console/crgg")
public class CrggController extends BaseController{

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransFileService transFileService;

    @Autowired
    RegionService regionService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransMaterialService transMaterialService;

    @Autowired
    MaterialCrggService materialCrggService;

    private static final String PERSONAL_MATERIAL = "PERSONAL";

    private static final String GROUP_MATERIAL = "GROUP";

    @ModelAttribute("crgg")
    public TransCrgg getRole(@RequestParam(value = "ggId", required = false) String ggId) {
        return StringUtils.isBlank(ggId) ? new TransCrgg() : transCrggService.getTransCrgg(ggId);
    }

    @RequestMapping("list")
            public String list(@PageDefault(value=10) Pageable page,String title,Model model) {
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransCrgg> transCrggList= transCrggService.findTransCrgg(title,regions, page);
        model.addAttribute("transCrggList", transCrggList);
        model.addAttribute("title", title);
        return "crgg-list";
    }

    @RequestMapping("edit")
    public String crgg(String ggId,String regionCode,Model model) {
        TransCrgg transCrgg=null;
        List<MaterialCrgg> materialCrggs = materialCrggService.getMaterialCrggByCrggId(ggId);
        model.addAttribute("materialCrggs",materialCrggs.size()==0?null:materialCrggs);
        if (StringUtils.isNotBlank(ggId)) {
            transCrgg = transCrggService.getTransCrgg(ggId);
            transCrgg.setAttachmentList(transFileService.getTransFileByKey(ggId));
            regionCode = transCrgg.getRegionCode();
        }else{
            transCrgg=new TransCrgg();
            transCrgg.setGgBeginTime(new Date());
            transCrgg.setGgEndTime(new Date());

            transCrgg.setSignStartTime(new Date());
            transCrgg.setSignEndTime(new Date());
          /*  transCrgg.setBidStartTime(new Date());
            transCrgg.setBidEndTime(new Date());
            transCrgg.setPaymentEndTime(new Date());*/
            if ( regionService.findAllDeptRegions().size()>0)
                regionCode=regionService.findAllDeptRegions().get(0)[0];

        }
        transCrgg.setPostDate(new Date());
        transCrgg.setPassMan(SecUtil.getLoginUserViewName());
        model.addAttribute("materialPersonList",transMaterialService.getMaterialsBymaterialType(regionCode, PERSONAL_MATERIAL));
        model.addAttribute("materialGroupList",transMaterialService.getMaterialsBymaterialType(regionCode, GROUP_MATERIAL));
        model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        model.addAttribute("transCrgg", transCrgg);
        return "crgg-edit";
    }

    @RequestMapping("/getPersonalLoad.f")
    public String getPersonalMaterialLoad(String regionCode,Model model){
        model.addAttribute("materialPersonList",transMaterialService.getMaterialsBymaterialType(regionCode, PERSONAL_MATERIAL));
        return "common/material_list_person";
    }

    @RequestMapping("/getGroupLoad.f")
    public String getGroupMaterialLoad(String regionCode,Model model){
        model.addAttribute("materialGroupList",transMaterialService.getMaterialsBymaterialType(regionCode, GROUP_MATERIAL));
        return "common/material_list_group";
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("crgg") TransCrgg transCrgg,RedirectAttributes ra,Model model,@RequestParam(value = "materialId",required = false)String materialIds) {
        if (StringUtils.isBlank(transCrgg.getGgId())) {
            transCrgg.setGgId(null);
        }
        transCrgg = transCrggService.saveTransCrgg(transCrgg);

        if(StringUtils.isNotBlank(materialIds)){
            String materialId = null;
            String[] id = materialIds.split(",");
            materialCrggService.deleteMaterialCrggList(transCrgg.getGgId());
            for(int i = 0;i < id.length;i++){
                materialId = id[i];
                MaterialCrgg materialCrgg = new MaterialCrgg();
                materialCrgg.setCrggId(transCrgg.getGgId());
                materialCrgg.setMaterialId(materialId);
                materialCrggService.saveMaterialCrgg(materialCrgg);
            }
        }

        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/console/crgg/edit?ggId=" + transCrgg.getGgId();
    }


    @RequestMapping("gygg.f")
    @ResponseBody
    public String getCrggByGyggId(@RequestParam(value = "gyggId", required = false) String gyggId){
        TransCrgg transCrgg = transCrggService.getTransCrggByGyggId(gyggId);
        return transCrgg!=null?transCrgg.getGgId():"";
    }



    @RequestMapping("delete.f")
    @ResponseBody
    public String delete(@RequestParam(value = "ggIds", required = false) String ggIds){
        transCrggService.deleteTransCrgg(Lists.newArrayList(ggIds.split(";")));
        return "true";
    }

    //console/crgg
    @RequestMapping("/status/change.f")
    public @ResponseBody Object changeStatus(String crggId,int status,Model model) {

        if (StringUtils.isNotBlank(crggId)) {
            TransCrgg transCrgg=null;
            transCrgg= transCrggService.getTransCrgg(crggId);
            transCrgg.setCrggStauts(status);
            transCrggService.saveTransCrgg(transCrgg);
            return success();
        }
        return fail("公告ID为空！");
    }

    @RequestMapping("/status/resource/change.f")
    public @ResponseBody Object changeStatusResource(String crggId,int status,Model model) {

        if (StringUtils.isNotBlank(crggId)) {
            TransCrgg transCrgg=null;
            List<TransResource> resourceList= transResourceService.findTransResource(crggId);
            for(TransResource transResource: resourceList){
                transResource.setResourceEditStatus(status);
                transResourceService.saveTransResource(transResource);
            }
            return success();
        }
        return fail("公告ID为空！");
    }


    @RequestMapping("/status/view.f")
    public String status(String crggId,Model model) {
        TransCrgg transCrgg=null;
        if (StringUtils.isNotBlank(crggId)) {
            transCrgg = transCrggService.getTransCrgg(crggId);
        }
        model.addAttribute("crgg", transCrgg);
        return "common/crgg-status";
    }

    @RequestMapping("/materials/checked")
    @ResponseBody
    public void listMaterials(String ggId,HttpServletResponse response){
        List<MaterialCrgg> materialCrggs = Lists.newArrayList();
        List<TransMaterial> transMaterials = Lists.newArrayList();
        if(StringUtils.isNotBlank(ggId)){
            materialCrggs = materialCrggService.getMaterialCrggByCrggId(ggId);
            for(MaterialCrgg mc:materialCrggs){
                transMaterials.add(transMaterialService.getMaterialsById(mc.getMaterialId()));
            }
            try {
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(transMaterials));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ;
    }
}
