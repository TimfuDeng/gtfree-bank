package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.i18n.NLS;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.ServiceUtils;
import cn.gtmap.landsale.service.TransResourceService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
@RequestMapping(value = "console")
public class IndexController {

    @Autowired
    TransResourceService transResourceService;

    @RequestMapping("index")
    public String index(Model model) throws Exception{
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Long countChengjiao = transResourceService.countByResourcesStatus(Constants.ResourceStatusChengJiao,regions);
        Long countGongGao = transResourceService.countByResourcesStatusAndEditStatus(Constants.ResourceStatusGongGao,Constants.ResourceEditStatusRelease, regions);
        Long countGuaPai = transResourceService.countByResourcesStatusAndEditStatus(Constants.ResourceStatusGuaPai,Constants.ResourceEditStatusRelease, regions);
        model.addAttribute("countChengjiao",countChengjiao!=null?countChengjiao:0);
        model.addAttribute("countGongGao",countGongGao!=null?countGongGao:0);
        model.addAttribute("countGuaPai",countGuaPai!=null?countGuaPai:0);
        model.addAttribute("sumOfDeal",transResourceService.sumOfDeal(regions));
        return "/index";
    }
}

