package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.DealNotice;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.DealNoticeService;
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

import java.util.Date;
import java.util.List;

/**
 * Created by trr on 2015/11/3.
 */
@Controller
@RequestMapping("console/dealNotice")
public class DealNoticeController extends BaseController{
    @Autowired
    DealNoticeService dealNoticeService;
    @Autowired
    TransResourceService transResourceService;

    private static final int NOTICE_DEPLOYED=1;//公告发布

    private static final int NOTICE_UNDO=0;//公告撤销

    @ModelAttribute("dealNotice")
    public DealNotice getNotice(@RequestParam(value="noticeId",required = false) String noticeId){
        return StringUtils.isBlank(noticeId)? new DealNotice() : dealNoticeService.getNotice(noticeId);
    }


    @RequestMapping("/edit")
    public String add(String noticeId,Model model){
        DealNotice dealNotice = null;
        if(StringUtils.isNotBlank(noticeId)){
            dealNotice = dealNoticeService.getNotice(noticeId);
        }else{
            dealNotice = new DealNotice();
            dealNotice.setDeployTime(new Date());
        }
        model.addAttribute("dealNotice",dealNotice);
        return "dealNotice/deal-notice-edit";
    }

    @RequestMapping("/editByResourceCode")
    public String addByResourceCode(String resourceId,Model model){
        DealNotice dealNotice = new DealNotice();
        TransResource transResource = transResourceService.getTransResource(resourceId);
        String resourceCode = transResource.getResourceCode();
        dealNotice.setResourceCode(resourceCode);
        //通过地块新增公告时默认发布
        dealNotice.setDeployStatus(1);
        dealNotice.setDeployTime(new Date());
        model.addAttribute("dealNotice",dealNotice);
        model.addAttribute("resourceId",resourceId);
        return "dealNotice/deal-notice-edit";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute("dealModel")DealNotice dealNotice,String resourceId,RedirectAttributes ra){
        if(StringUtils.isBlank(dealNotice.getNoticeId())){
            dealNotice.setNoticeId(null);
        }
        TransResource transResource = null;
        if(StringUtils.isNotBlank(resourceId)){
            transResource = transResourceService.getTransResource(resourceId);
        }
        handleNoticeTable(dealNotice);
        dealNotice = dealNoticeService.save(dealNotice);
        if(transResource!=null && transResource.getDealNoticeId()==null){
            transResource.setDealNoticeId(dealNotice.getNoticeId());
            transResourceService.saveTransResource(transResource);
        }
        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg", "保存成功");
        return "redirect:/console/dealNotice/edit?noticeId="+dealNotice.getNoticeId();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam(value="noticeIds",required = false)String noticeIds){
        List noticeIdList = Lists.newArrayList(noticeIds.split(";"));
        for(Object noticeId:noticeIdList){
            DealNotice dealNotice = dealNoticeService.getNotice(noticeId.toString());
            String resourceCode = dealNotice.getResourceCode();

            TransResource transResource = null;
            try {
                transResource = transResourceService.getResourceByCode(resourceCode);
                transResource.setSuspendNoticeId(null);
                transResourceService.saveTransResource(transResource);
            } catch (Exception e) {
                continue;
            }
        }
        dealNoticeService.delete(noticeIdList);
        return "true";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam(value="noticeId",required = false)String noticeId,Model model){
        DealNotice dealNotice;
        try {
            dealNotice = dealNoticeService.getNotice(noticeId);
        } catch (Exception e) {
            dealNotice = new DealNotice();
        }
        model.addAttribute("dealNotice",dealNotice);
        return "dealNotice/deal-notice-detail";
    }

    @RequestMapping("/list")
    public String list(@PageDefault(value = 10)Pageable page,String noticeTitle,Model model){
        Page<DealNotice> dealNoticeList = dealNoticeService.findAllDealNotices(page, noticeTitle);
        model.addAttribute("dealNoticeList",dealNoticeList);
        return "dealNotice/deal-notice-list";
    }

    @RequestMapping("/deploy.f")
    public String deploy(String noticeId,Model model){
        DealNotice dealNotice;
        try {
            dealNotice = dealNoticeService.getNotice(noticeId);
            dealNotice.setDeployStatus(NOTICE_DEPLOYED);
            dealNotice = dealNoticeService.save(dealNotice);
        } catch (Exception e) {
            dealNotice = new DealNotice();
        }
        model.addAttribute("notice",dealNotice);
        return "common/deal-notice-status";
    }

    @RequestMapping("/revoke.f")
    public String revoke(String noticeId,Model model){
        DealNotice dealNotice;
        try {
            dealNotice = dealNoticeService.getNotice(noticeId);
            dealNotice.setDeployStatus(NOTICE_UNDO);
            dealNotice = dealNoticeService.save(dealNotice);
        } catch (Exception e) {
            dealNotice = new DealNotice();
        }
        model.addAttribute("notice",dealNotice);
        return "common/deal-notice-status";
    }
    
    private void handleNoticeTable(DealNotice dealNotice){
    	if(dealNotice!=null){
    		String content = dealNotice.getNoticeContent();
    		if(content!=null&&content.length()>0){
    			String regex = "(?<=<table.{0,200}width=\"?)(\\d+)(?=\"?.+>)";
    			content = content.replaceAll(regex,"100%");
    			dealNotice.setNoticeContent(content);
    		}
    	}
    }
    
}
