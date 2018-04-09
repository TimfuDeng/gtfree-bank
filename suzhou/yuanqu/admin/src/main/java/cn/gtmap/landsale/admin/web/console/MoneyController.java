package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransBankPay;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransBankPayService;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import cn.gtmap.landsale.service.TransUserService;
import com.google.common.collect.Sets;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * 财务管理
 * Created by Jibo on 2015/6/2.
 */
@Controller
@RequestMapping(value = "console/money")
public class MoneyController extends BaseController {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransBankPayService transBankPayService;

    @Autowired
    TransUserService transUserService;

    @RequestMapping("list")
    public String list(@PageDefault(value=10) Pageable page,Model model) {
        ResourceQueryParam queryParam=new ResourceQueryParam();
        queryParam.setGtResourceEditStatus(Constants.ResourceEditStatusPreRelease);
        //queryParam.setDisplayStatus(-1);
//        queryParam.setGtResourceStatus(29);
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResourcePage= transResourceService.findTransResources(queryParam,regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        return "money/money-resource-list";
    }

    @RequestMapping("money-list")
    public String applylist(String resourceId,Model model) {
        model.addAttribute("resource", transResourceService.getTransResource(resourceId));
        model.addAttribute("transResourceApplyList", transResourceApplyService.getTransResourceApplyByResourceId(resourceId));
        model.addAttribute("resourceId", resourceId);
        return "money/money-list";
    }

    //错转款list
    @RequestMapping("wrong-trun")
    public String wrongTrun(String resourceId,Model model){
        List<TransBankPay> transBankPayList = transBankPayService.getTransPaysByAccointIdIsNULL("accountId");
        model.addAttribute("transBankPayList",transBankPayList);
        model.addAttribute("resourceId",resourceId);
        return "money/moneywrong-list";
    }

    @RequestMapping(value = "/excel.f")
    public String export(String resourceId,HttpServletResponse response,Model model){
        try {
            TransResource transResource= transResourceService.getTransResource(resourceId);
            // set output header
//            ServletOutputStream os = response.getOutputStream();
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename=\"myexcel.xls\"");
            List<TransResourceApply> transResourceApplyList= transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
            model.addAttribute("transResourceApplyList", transResourceApplyList);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+transResource.getResourceCode()+".xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/money/money-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/status/change.f")
    public @ResponseBody
    Object changeStatus(String applyId,Boolean status,Model model) {
        TransResourceApply transResourceApply=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
            transResourceApply.setFixedOfferBack(status);
            transResourceApplyService.saveTransResourceApply(transResourceApply);
            return success();
        }
        return fail("ID为空！");
    }

    @RequestMapping("/money-status.f")
    public String MoneyStatus(String applyId,Model model) {
        TransResourceApply transResourceApply=null;
        TransResource transResource=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
            transResource=transResourceService.getTransResource(transResourceApply.getResourceId());
        }
        model.addAttribute("resourceApply", transResourceApply);
        model.addAttribute("resource", transResource);
        return "money/money-status";
    }
    @RequestMapping("/offer-status.f")
    public String OfferStatus(String applyId,Model model) {
        TransResourceApply transResourceApply=null;
        TransResource transResource=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
            transResource=transResourceService.getTransResource(transResourceApply.getResourceId());
        }
        model.addAttribute("resourceApply", transResourceApply);
        model.addAttribute("resource", transResource);
        return "money/offer-status";
    }
}
