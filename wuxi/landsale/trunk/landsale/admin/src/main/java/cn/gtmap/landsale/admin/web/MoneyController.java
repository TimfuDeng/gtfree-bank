package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.admin.register.TransBankPayClient;
import cn.gtmap.landsale.admin.register.TransResourceApplyClient;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBankPay;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceApply;
import cn.gtmap.landsale.common.security.SecUtil;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
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

/**
 * 保证金
 * @author lq
 * @version v1.0, 2017/11/16
 */
@Controller
@RequestMapping(value = "money")
public class MoneyController extends BaseController {

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    TransBankPayClient transBankPayClient;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("list")
    public String list(@PageDefault(value=10) Pageable pageable, Model model) {
        ResourceQueryParam queryParam=new ResourceQueryParam();
        queryParam.setGtResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_PRE_RELEASE);
        queryParam.setPage(pageable);
        //queryParam.setDisplayStatus(-1);
//        queryParam.setGtResourceStatus(29);
        String regions = "";
        if(!SecUtil.isAdmin()) {
            regions = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResourcePage= transResourceClient.findTransResources(queryParam,regions);
        model.addAttribute("transResourcePage", transResourcePage);
        return "money/money-resource-list";
    }

    @RequestMapping("money-list")
    public String applylist(String resourceId,Model model) {
        model.addAttribute("resource", transResourceClient.getTransResource(resourceId));
        model.addAttribute("transResourceApplyList", transResourceApplyClient.getTransResourceApplyByResourceId(resourceId));
        model.addAttribute("resourceId", resourceId);
        return "money/money-list";
    }

    //错转款list
    @RequestMapping("wrong-trun")
    public String wrongTrun(String resourceId,Model model){
        List<TransBankPay> transBankPayList = transBankPayClient.getTransPaysByAccointIdIsNULL("accountId");
        model.addAttribute("transBankPayList",transBankPayList);
        model.addAttribute("resourceId",resourceId);
        return "money/moneywrong-list";
    }

    @RequestMapping(value = "/excel")
    public String export(String resourceId, HttpServletResponse response, Model model){
        try {
            TransResource transResource= transResourceClient.getTransResource(resourceId);
            // set output header
//            ServletOutputStream os = response.getOutputStream();
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename=\"myexcel.xls\"");
            List<TransResourceApply> transResourceApplyList= transResourceApplyClient.getTransResourceApplyByResourceId(resourceId);
            model.addAttribute("transResourceApplyList", transResourceApplyList);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+transResource.getResourceCode()+".xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/money/money-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/status/change")
    public @ResponseBody
    ResponseMessage changeStatus(String applyId,Boolean status,Model model) {
        TransResourceApply transResourceApply=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyClient.getTransResourceApply(applyId);
            transResourceApply.setFixedOfferBack(status);
            return transResourceApplyClient.saveTransResourceApply(transResourceApply);
        }
        return new ResponseMessage(false,"ID为空！");
    }

    @RequestMapping("/money-status")
    public String moneyStatus(String applyId, Model model) {
        TransResourceApply transResourceApply=null;
        TransResource transResource=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyClient.getTransResourceApply(applyId);
            transResource=transResourceClient.getTransResource(transResourceApply.getResourceId());
        }
        model.addAttribute("resourceApply", transResourceApply);
        model.addAttribute("resource", transResource);
        return "money/money-status";
    }

    @RequestMapping("/offer-status")
    public String offerStatus(String applyId, Model model) {
        TransResourceApply transResourceApply=null;
        TransResource transResource=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyClient.getTransResourceApply(applyId);
            transResource=transResourceClient.getTransResource(transResourceApply.getResourceId());
        }
        model.addAttribute("resourceApply", transResourceApply);
        model.addAttribute("resource", transResource);
        return "money/offer-status";
    }

}
