package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.TransInteractCommunion;
import cn.gtmap.landsale.service.TransInteractCommunionService;
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

import java.util.Date;

/**
 * 互动交流
 * Created by www on 2015/9/29.
 */
@Controller
@RequestMapping(value = "/console/communion")
public class CommunionController extends BaseController{
    @Autowired
    TransInteractCommunionService transInteractCommunionService;

    @RequestMapping(value = "list")
    public String list(@PageDefault(value = 10)Pageable page,String title,Model model){
        Page<TransInteractCommunion>  pageInteractCommunion=transInteractCommunionService.findTransInteractCommunionPage(title, page);
        model.addAttribute("pageInteractCommunion",pageInteractCommunion);
        model.addAttribute("title",title);
        return "interact-communion/interact-communion-list";
    }

    @RequestMapping("/status/change.f")
    public @ResponseBody
    Object changeStatus(String communionId,int status,Model model) {

        if (StringUtils.isNotBlank(communionId)) {
            TransInteractCommunion transInteractCommunion=null;
            transInteractCommunion= transInteractCommunionService.getTransInteractCommunionById(communionId);
            transInteractCommunion.setReplyStatus(status);
            transInteractCommunionService.saveTransInteractCommunion(transInteractCommunion);
            return success();
        }
        return fail("来信ID为空！");
    }

    @RequestMapping("reply.f")
    public @ResponseBody
    Object reply(String communionId,int status,Date replyTime,String replyContent,Model model) {

        if (StringUtils.isNotBlank(communionId)) {
            TransInteractCommunion transInteractCommunion=null;
            transInteractCommunion= transInteractCommunionService.getTransInteractCommunionById(communionId);
            transInteractCommunion.setReplyStatus(status);
            transInteractCommunion.setReplyTime(replyTime);
            transInteractCommunion.setReplyContent(replyContent);
            transInteractCommunionService.saveTransInteractCommunion(transInteractCommunion);
            return success();
        }
        return fail("来信ID为空！");
    }




    @RequestMapping("/status/view.f")
    public String status(String communionId,Model model) {
        TransInteractCommunion transInteractCommunion=null;
        if (StringUtils.isNotBlank(communionId)) {
            transInteractCommunion= transInteractCommunionService.getTransInteractCommunionById(communionId);
        }
        model.addAttribute("communion", transInteractCommunion);
        return "common/communion-status";
    }

    @RequestMapping("view.f")
    public String view(String communionId,Model model) {
        TransInteractCommunion transInteractCommunion=new TransInteractCommunion();
        if (StringUtils.isNotBlank(communionId)) {
            transInteractCommunion= transInteractCommunionService.getTransInteractCommunionById(communionId);
            transInteractCommunion.setReplyTime(new Date());
        }
        model.addAttribute("communion", transInteractCommunion);
        return "interact-communion/interact-communion";
    }


    @ModelAttribute("transInteractCommunion")
    public TransInteractCommunion getRole(@RequestParam(value = "communionId",required = false)String communionId){
        return StringUtils.isBlank(communionId)? new TransInteractCommunion():transInteractCommunionService.getTransInteractCommunionById(communionId);
    }

    /**
     * 测试新增接口
     * @param transInteractCommunion
     * @param ra
     * @param model
     * @return
     */
    @RequestMapping(value = "save")
    public String save (@ModelAttribute("transInteractCommunion")TransInteractCommunion transInteractCommunion,RedirectAttributes ra,Model model){
        if(StringUtils.isBlank(transInteractCommunion.getCommunionId())){
            transInteractCommunion.setCommunionId(null);

        }

        String pubNo=transInteractCommunionService.getNextPublicNo();
        transInteractCommunion.setPublicNo(pubNo);
        transInteractCommunion.setPublicTime(new Date());
        transInteractCommunionService.saveTransInteractCommunion(transInteractCommunion);

        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg","保存成功");
        return "redirect:/console/communion/list";

    }


    @RequestMapping(value = "delete.f")
    @ResponseBody
    public String deleteTreansNews(@RequestParam(value = "communionIds",required = false) String communionIds){
        transInteractCommunionService.deleteTransInteractCommunion(Lists.newArrayList(communionIds.split(";")));
        return "true";
    }


    @RequestMapping(value = "edit")
    public String transInteractCommunion( String communionId,Model model){
        TransInteractCommunion transInteractCommunion=null;
        if(StringUtils.isNotBlank(communionId)){
            transInteractCommunion= transInteractCommunionService.getTransInteractCommunionById(communionId);
            transInteractCommunion.setReplyTime(new Date());
        }else{
            transInteractCommunion=new TransInteractCommunion();
            transInteractCommunion.setReplyTime(new Date());
            transInteractCommunion.setPublicTime(new Date());
        }

        model.addAttribute("communion",transInteractCommunion);
        return "interact-communion/interact-communion-edit";
    }

}
