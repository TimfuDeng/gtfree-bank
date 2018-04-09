package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class MoneyController {

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
        queryParam.setGtResourceEditStatus(Constants.ResourceEditStatusRelease);
        queryParam.setDisplayStatus(-1);
        queryParam.setOrderBy(5);
//        queryParam.setGtResourceStatus(29);
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResourcePage= transResourceService.findTransResources(queryParam,regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        return "money-resource-list";
    }

    @RequestMapping("money-list")
    public String applylist(String resourceId,Model model) {
        model.addAttribute("transResourceApplyList", transResourceApplyService.getTransResourceApplyByResourceId(resourceId));
        model.addAttribute("resource", transResourceService.getTransResource(resourceId));
        return "money-list";
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
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/money-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
