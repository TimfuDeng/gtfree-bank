package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.SuspendNotice;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.SuspendNoticeService;
import cn.gtmap.landsale.service.TransResourceService;
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

import java.util.List;

/**
 * Created by trr on 2015/11/2.
 */
@Controller
@RequestMapping("/console/suspendNotice")
public class SuspendNoticeController extends BaseController{
    @Autowired
    private SuspendNoticeService suspendNoticeService;
    @Autowired
    private TransResourceService transResourceService;

    private static final int NOTICE_DEPLOYED=1;//公告发布

    private static final int NOTICE_UNDO=0;//公告撤销


    @ModelAttribute("suspendNotice")
    public SuspendNotice getNotice(@RequestParam(value="noticeId",required = false) String noticeId){
        return StringUtils.isBlank(noticeId)? new SuspendNotice() : suspendNoticeService.getNotice(noticeId);
    }


    @RequestMapping("/edit")
    public String add(String noticeId,Model model){
        SuspendNotice suspendNotice = null;
        if(StringUtils.isNotBlank(noticeId)){
            suspendNotice = suspendNoticeService.getNotice(noticeId);
        }else{
            suspendNotice = new SuspendNotice();
        }
        model.addAttribute("suspendNoice",suspendNotice);
        return "suspendNotice/suspend-notice-edit";
    }

    @RequestMapping("/editByResourceCode")
    public String addByResourceCode(String resourceId,Model model){
        SuspendNotice suspendNotice = new SuspendNotice();
        TransResource transResource = transResourceService.getTransResource(resourceId);
        String resourceCode = transResource.getResourceCode();
        suspendNotice.setResourceCode(resourceCode);
        //通过地块新增公告时默认发布
        suspendNotice.setDeployStatus(1);
        model.addAttribute("suspendNotice",suspendNotice);
        model.addAttribute("resourceId",resourceId);
        return "suspendNotice/suspend-notice-edit";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute("suspendModel")SuspendNotice suspendNotice,String resourceId,RedirectAttributes ra){
        if(StringUtils.isBlank(suspendNotice.getNoticeId())){
            suspendNotice.setNoticeId(null);
        }
        suspendNotice = suspendNoticeService.save(suspendNotice);
        TransResource transResource = null;
        if(StringUtils.isNotBlank(resourceId)){
            transResource = transResourceService.getTransResource(resourceId);
        }
        if(transResource!=null && transResource.getSuspendNoticeId()==null){
            transResource.setSuspendNoticeId(suspendNotice.getNoticeId());
            transResourceService.saveTransResource(transResource);
        }
        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg", "保存成功");
        return "redirect:/console/suspendNotice/edit?noticeId="+suspendNotice.getNoticeId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam(value="noticeIds",required = false)String noticeIds){
        List noticeIdList = Lists.newArrayList(noticeIds.split(";"));
        for(Object noticeId:noticeIdList){
            SuspendNotice suspendNotice = suspendNoticeService.getNotice(noticeId.toString());
            String resourceCode = suspendNotice.getResourceCode();
            try {
                TransResource transResource = transResourceService.getResourceByCode(resourceCode);
                transResource.setSuspendNoticeId(null);
                transResourceService.saveTransResource(transResource);
            } catch (Exception e) {
                continue;
            }
        }
        suspendNoticeService.delete(noticeIdList);
        return "true";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam(value="noticeId",required = false)String noticeId,Model model){
        SuspendNotice suspendNotice;
        try {
            suspendNotice = suspendNoticeService.getNotice(noticeId);
        } catch (Exception e) {
            suspendNotice = new SuspendNotice();
        }
        model.addAttribute("suspendNotice",suspendNotice);
        return "suspendNotice/suspend-notice-detail";
    }

    @RequestMapping("/list")
    public String list(@PageDefault(value=10)Pageable page,String noticeTitle,Model model){
        Page<SuspendNotice> suspendNoticeList = suspendNoticeService.findAllSuspendNotices(page, noticeTitle);
        model.addAttribute("suspendNoticeList",suspendNoticeList);
        return "suspendNotice/suspend-notice-list";
    }

    @RequestMapping("/deploy.f")
    public String deploy(String noticeId,Model model){
        SuspendNotice suspendNotice;
        try {
            suspendNotice = suspendNoticeService.getNotice(noticeId);
            suspendNotice.setDeployStatus(NOTICE_DEPLOYED);
            suspendNotice = suspendNoticeService.save(suspendNotice);
        } catch (Exception e) {
            suspendNotice = new SuspendNotice();
        }
        model.addAttribute("notice",suspendNotice);
        return "common/suspend-notice-status";
    }

    @RequestMapping("/revoke.f")
    public String revoke(String noticeId,Model model){
        SuspendNotice suspendNotice;
        try {
            suspendNotice = suspendNoticeService.getNotice(noticeId);
            suspendNotice.setDeployStatus(NOTICE_UNDO);
            suspendNotice = suspendNoticeService.save(suspendNotice);
        } catch (Exception e) {
            suspendNotice = new SuspendNotice();
        }
        model.addAttribute("notice",suspendNotice);
        return "common/suspend-notice-status";
    }

}
