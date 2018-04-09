package cn.gtmap.landsale.view.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.ServiceUtils;
import cn.gtmap.landsale.service.TransCrggService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransResourceSonService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
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

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransResourceSonService transResourceSonService;

    @RequestMapping("index")
    public String index(ResourceQueryParam param,@PageDefault(value=12) Pageable page,String regionCode,Model model) throws Exception{
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.ResourceEditStatusPreRelease);
        if(param.getResourceStatus()==Constants.ResourceStatusChengJiao){
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.ResourceStatusGongGao);
        }
        Set<String> regions = Sets.newHashSet();
        //Page<TransResource> transResourcePage= transResourceService.findTransResources(param, regions, page);
        Collection<String> ggIds= transCrggService.findTransCrggIds();
        //Page<TransResource> transResourcePage= transResourceService.findTransResources(param, regions, page);
        regions.add(regionCode);//分为国土环保是工业和土地储备是经营性地块
        Page<TransResource> transResourcePage= transResourceService.findTransResourcesByCrggIds(param, regions,ggIds, page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("param", param);
        model.addAttribute("regionCode",regionCode);
        return "/index";
    }

    @RequestMapping("/index/type")
    public String indexType() throws Exception{
        return "/index-type";
    }

    @RequestMapping("content.f")
    public String detail(String resourceId,Model model) throws Exception{
        TransResource resource=transResourceService.getTransResource(resourceId);
        model.addAttribute("resource", resource);
        return "/common/resource-content";
    }


    @RequestMapping("navi")
    public String navi(){
        return "/navi";
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

