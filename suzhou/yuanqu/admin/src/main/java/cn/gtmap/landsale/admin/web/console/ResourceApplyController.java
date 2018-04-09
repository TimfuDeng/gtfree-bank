package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地块竞买人信息
 * Created by jibo1_000 on 2015/5/25.
 */
@Controller
@RequestMapping(value = "console/resource-apply")
public class ResourceApplyController {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransUserService transUserService;

    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;

    @RequestMapping("list")
    public String list(String resourceId,Model model) {
        List<TransResourceApply> transResourceApplyList=
                transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
        model.addAttribute("transResourceApplyList",transResourceApplyList);
        model.addAttribute("resourceId",resourceId);
        return "resourceapply-list";
    }

    @RequestMapping("offerlist")
    public String OfferList(String resourceId,@PageDefault(value=15) Pageable page,Model model) {
        Page<TransResourceOffer> transResourceOfferPage=
                transResourceOfferService.getResourceOfferPage(resourceId, page);
        model.addAttribute("transResourceOfferPage",transResourceOfferPage);
        model.addAttribute("resourceId",resourceId);
        return "resourceoffer-list";
    }

    @RequestMapping("offerlist-excel.f")
    public String OfferList(String resourceId,HttpServletResponse response,Model model) {
        List<TransResourceOffer> transResourceOfferList=
                transResourceOfferService.getOfferListByResource(resourceId);
        model.addAttribute("transResourceOfferList",transResourceOfferList);
        model.addAttribute("resourceId",resourceId);
        try {
            TransResource transResource= transResourceService.getTransResource(resourceId);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+transResource.getResourceCode()+"offerlist.xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/resourceoffer-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("attachment")
    public String attachments(@RequestParam(value = "resourceId", required = true)String resourceId,@RequestParam(value = "userId", required = true)String userId,Model model){
        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
        TransUser transUser=transUserService.getTransUser(userId);
        TransUserApplyInfo transUserApplyInfo= transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("resourceId",resourceId);
        model.addAttribute("transUserApplyInfo",transUserApplyInfo);
        model.addAttribute("transUser",transUser);
        return "resource-apply-attachments";
    }
}
