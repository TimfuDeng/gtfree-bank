package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransResourceOfferService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransUserService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 交易大屏幕管理
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/23
 */
@Controller
@RequestMapping(value = "console/trans")
public class TransController {
    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransUserService transUserService;

    @RequestMapping("list")
    public String list(@PageDefault(value=10) Pageable page,String title,Model model,@RequestParam(value = "displayStatus", required = false,defaultValue = "-1")String displayStatus) {
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResource= transResourceService.findDisplayResource(title,displayStatus,regions,page);
        model.addAttribute("transList", transResource);
        model.addAttribute("displayStatus",Integer.parseInt(displayStatus));
        return "trans-list";
    }

    @RequestMapping("view")
    public String view(Model model) {
        return "guest-view";
    }

    @RequestMapping("view_2")
    public String view_2(Model model) {
        return "guest-view-2";
    }

    @RequestMapping("/view-resource/view_left")
    public String view_left(@PageDefault(value=10) Pageable page,String title,Model model,@RequestParam(value = "displayStatus", required = false,defaultValue = "-1")String displayStatus) {
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResource= transResourceService.findDisplayResource(title,displayStatus,regions,page);
        model.addAttribute("transList", transResource);
        return "guest-view-left";
    }

    @RequestMapping("/list/status")
    public String add2List(Model model,String resourceId,@RequestParam(value = "displayStatus", required = false,defaultValue = "-1")int displayStatus){
        transResourceService.updateTransResourceDisplayStatus(resourceId,displayStatus);
        return "redirect:/console/trans/list";
    }

    @RequestMapping("resource")
    @ResponseBody
    public Object getResource(Model model){
        Map result = Maps.newHashMap();
        List<TransResource> transResources = transResourceService.findTransResourcesByDisplayStatus(1);
        List<Map> transMap = Lists.newArrayList();
        int i = 0;
        for(TransResource transResource:transResources){
            i++;
            Map tmp = Maps.newHashMap();
            tmp.put("itemcount",transResources.size());
            tmp.put("currentindex",i);
            tmp.put("splittime","5000");
            tmp.put("resource",transResource);
            Page<TransResourceOffer> resourceOffers=
                    transResourceOfferService.getResourceOfferPage(transResource.getResourceId(), new PageRequest(0, 5));
            Double maxOffer = (resourceOffers!=null&&resourceOffers.getItems().size()>0)?resourceOffers.getItems().get(0).getOfferPrice():transResource.getBeginOffer();
            tmp.put("maxOffer", maxOffer);
            tmp.put("offerTime", transResource.getGpEndTime());
            tmp.put("offerList", resourceOffers.getItems());
            tmp.put("offerFrequency", transResourceOfferService.getResourceOfferFrequency(transResource.getResourceId()));
            if(StringUtils.isNotBlank(transResource.getOfferId())){
                TransResourceOffer transResourceOffer = transResourceOfferService.getTransResourceOffer(transResource.getOfferId());
                tmp.put("offerUser", transUserService.getTransUser(transResourceOffer.getUserId()).getViewName());
            }
            transMap.add(tmp);
        }
        result.put("dikuai",transMap);
        return result;
    }

}
