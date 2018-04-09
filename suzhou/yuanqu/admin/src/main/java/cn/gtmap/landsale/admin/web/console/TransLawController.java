package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.TransLaw;
import cn.gtmap.landsale.service.TransLawService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by trr on 2016/1/27.
 */
/*@Controller
@RequestMapping("console/transLaw")
public class TransLawController extends BaseController{

    @Autowired
    private TransLawService transLawService;

    private static final int LAW_DEPLOYED=1;//公告发布

    private static final int LAW_UNDO=0;//公告撤销
    
    @ModelAttribute("transLaw")
    public TransLaw getLaw(@RequestParam(value = "lawId",required = false)String lawId){
        return StringUtils.isBlank(lawId)? new TransLaw():transLawService.getById(lawId);
    }

    @RequestMapping("/edit")
    public String add(String lawId,Model model){
        TransLaw transLaw = null;
        if(StringUtils.isNotBlank(lawId)){
            transLaw = transLawService.getById(lawId);
        }else{
            transLaw = new TransLaw();
        }
        model.addAttribute("transLaw",transLaw);
        return "transLaw/law-edit";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute("transLaw")TransLaw transLaw,RedirectAttributes ra){
        if(StringUtils.isBlank(transLaw.getLawId())){
            transLaw.setLawId(null);
        }
        transLaw = transLawService.save(transLaw);
        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg", "保存成功");
        return "redirect:/console/transLaw/edit?lawId="+transLaw.getLawId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam(value="lawIds",required = false)String lawIds){
        transLawService.delete(Lists.newArrayList(lawIds.split(";")));
        return "true";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam(value="lawId",required = false)String lawId,Model model){
        TransLaw transLaw;
        try {
            transLaw = transLawService.getById(lawId);
        } catch (Exception e) {
            transLaw = new TransLaw();
        }
        model.addAttribute("transLaw",transLaw);
        return "transLaw/law-detail";
    }

    @RequestMapping("/list")
    public String list(@PageDefault(value = 10)Pageable page,String title,Model model){
        Page<TransLaw> transLawList = transLawService.findByPage(title,page);
        model.addAttribute("transLawList",transLawList);
        return "transLaw/law-list";
    }

    @RequestMapping("/deploy.f")
    public String deploy(String lawId,Model model){
        TransLaw transLaw;
        try {
            transLaw = transLawService.getById(lawId);
            transLaw.setLawStauts(LAW_DEPLOYED);
            transLaw = transLawService.save(transLaw);
        } catch (Exception e) {
            transLaw = new TransLaw();
        }
        model.addAttribute("transLaw",transLaw);
        return "common/law-status";
    }

    @RequestMapping("/revoke.f")
    public String revoke(String lawId,Model model){
        TransLaw transLaw;
        try {
            transLaw = transLawService.getById(lawId);
            transLaw.setLawStauts(LAW_UNDO);
            transLaw = transLawService.save(transLaw);
        } catch (Exception e) {
            transLaw = new TransLaw();
        }
        model.addAttribute("transLaw",transLaw);
        return "common/law-status";
    }
    
}*/
