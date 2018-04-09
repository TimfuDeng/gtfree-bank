package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceOfferService;
import cn.gtmap.landsale.service.TransResourceService;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("list")
    public String list(String resourceId,Model model) {
        List<TransResourceApply> transResourceApplyList=
                transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
        model.addAttribute("transResourceApplyList",transResourceApplyList);
        model.addAttribute("resource",transResourceService.getTransResource(resourceId));
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
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("resourceId",resourceId);
        return "resource-apply-attachments";
    }

    /**
     * @作者 王建明
     * @创建日期 2015-10-28
     * @创建时间 14:46
     * @描述 —— 动态ajax进行审核操作
     */
    @RequestMapping("doTrial.f")
    @ResponseBody
    public String DoTrial(@ModelAttribute("tempTransUser") TransResourceApply transResourceApply,Model model) {
        String result = "fail";
        try {
            TransResourceApply  transResourceApplyTemp=
                    transResourceApplyService.getTransResourceApply(transResourceApply.getApplyId());

            if(transResourceApplyTemp.getTrialDate() == null){
                transResourceApplyTemp.setTrialDate(new Date());
            }

            transResourceApplyTemp.setTrialType(transResourceApply.getTrialType());
            transResourceApplyTemp.setTrialReason(transResourceApply.getTrialReason());

            transResourceApplyService.saveTransResourceApply(transResourceApplyTemp);
            
            //如果竞买的地块是资格前审
            TransResource transResource = transResourceService.getTransResource(transResourceApplyTemp.getResourceId());
            if(transResource.getQualificationType().equals(Constants.QualificationType.PRE_TRIAL)){
            	
                //更新待审核人数
                List<TransResourceApply> transResourceApplyCommitToList = transResourceApplyService.getCommitToTransResourceApply(transResourceApplyTemp.getResourceId());
                transResource.setPendingTrialNum(transResourceApplyCommitToList.size());
                //更新审核通过人数
                List<TransResourceApply> transResourceApplyPassedList = transResourceApplyService.getPassedTransResourceApply(transResourceApplyTemp.getResourceId());
                transResource.setPassedTrialNum(transResourceApplyPassedList.size());
                transResourceService.saveTransResource(transResource);
            }
            
            result = "success";
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return result;
    }

    /**
     * @作者 王建明
     * @创建日期 2015-10-28
     * @创建时间 14:47
     * @描述 —— 获取获取地块竞买的审核信息
     */
    @RequestMapping("getTrial.f")
    @ResponseBody
    public TransResourceApply getTrialInfo(@ModelAttribute("tempTransUser") TransResourceApply transResourceApply,Model model) {
        TransResourceApply  transResourceApplyTemp=
                transResourceApplyService.getTransResourceApply(transResourceApply.getApplyId());

        return transResourceApplyTemp;
    }

    /**
     * @作者 王建明
     * @创建日期 2017/5/3 0003
     * @创建时间 下午 1:41
     * @描述 —— 删除用户申请竞买信息
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map delete(@ModelAttribute("applyId") String applyId, Model model) {
        Map result = new HashMap();
        try {
            transResourceApplyService.delete(applyId);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }

        return result;
    }
}
