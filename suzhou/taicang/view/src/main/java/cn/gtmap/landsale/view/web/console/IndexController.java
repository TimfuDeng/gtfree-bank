package cn.gtmap.landsale.view.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.ServiceUtils;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Sets;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    ServiceUtils serviceUtils;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("index")
    public String index(ResourceQueryParam param,@PageDefault(value=12) Pageable page,Model model) throws Exception{
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.ResourceEditStatusPreRelease);
        if(param.getResourceStatus()==Constants.ResourceStatusChengJiao){
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.ResourceStatusGongGao);
        }
        Set<String> regions = Sets.newHashSet();
        Page<TransResource> transResourcePage= transResourceService.findTransResources(param, regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("param", param);
        return "/index";
    }

    @RequestMapping("content.f")
    public String detail(String resourceId,Model model) throws Exception{
        TransResource resource=transResourceService.getTransResource(resourceId);
        model.addAttribute("resource", resource);
        return "/common/resource-content";
    }


    @RequestMapping("help")
    public String help(Model model, @RequestParam(value = "tag", required = false) String tag) {
        model.addAttribute("_tag", tag);
        return "/help";
    }


    /**
     * 获取服务器时间
     * @return
     */
    @RequestMapping("getServerTime.f")
    public @ResponseBody String getServerTime(){
        return serviceUtils.getServerTime();
    }
}

