package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.RegionService;
import cn.gtmap.landsale.service.TransCrggService;
import cn.gtmap.landsale.service.TransFileService;
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

import java.util.Date;
import java.util.Set;

/**
 * 出让公告列表
 * Created by Jibo on 2015/4/25.
 */
@Controller
@RequestMapping(value = "console/crgg")
public class CrggController {

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransFileService transFileService;

    @Autowired
    RegionService regionService;

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
    public String crgg(String ggId,Model model) {
        TransCrgg transCrgg=null;
        if (StringUtils.isNotBlank(ggId)) {
            transCrgg = transCrggService.getTransCrgg(ggId);
            transCrgg.setAttachmentList(transFileService.getTransFileByKey(ggId));
        }else{
            transCrgg=new TransCrgg();
            transCrgg.setGgBeginTime(new Date());
            transCrgg.setGgEndTime(new Date());
        }
        model.addAttribute("regionAllList", regionService.findAllRegions());
        model.addAttribute("transCrgg", transCrgg);
        return "crgg-edit";
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("crgg") TransCrgg transCrgg,RedirectAttributes ra,Model model) {
        if (StringUtils.isBlank(transCrgg.getGgId())) {
            transCrgg.setGgId(null);
        }
        transCrggService.saveTransCrgg(transCrgg);
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


}
