package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.admin.register.TransResourceOfferClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.security.SecUtil;
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
 *
 * @author cxm
 * @version v1.0, 2017/10/20
 */
@Controller
@RequestMapping("/trans")
public class TransController {
    @Autowired
    TransResourceClient resourceClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @RequestMapping("index")
    public String index(@PageDefault(value = 10) Pageable page, String title, Model model,
                        @RequestParam(value = "displayStatus", required = false, defaultValue = "-1") String displayStatus) {
        String regions = null;
        if (!SecUtil.isAdmin()) {
            regions = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResource = resourceClient.findDisplayResource(title, displayStatus, regions, page);
        model.addAttribute("title", title);
        model.addAttribute("transList", transResource);
        model.addAttribute("displayStatus", Integer.parseInt(displayStatus));
        return "trans/trans-list";
    }

    @RequestMapping("view")
    public String view(Model model) {
        return "trans/guest-view";
    }

    @RequestMapping("view_2")
    public String view2(Model model) {
        return "trans/guest-view-2";
    }

    @RequestMapping("/index/status")
    @ResponseBody
    public ResponseMessage add2List(Model model, String resourceId, @RequestParam(value = "displayStatus", required = false, defaultValue = "-1") int displayStatus) {
        return resourceClient.updateTransResourceDisplayStatus(resourceId, displayStatus);
    }

    @RequestMapping("/status/view")
    public String add2List(Model model, String resourceId) {
        TransResource transResource = resourceClient.getTransResource(resourceId);
        model.addAttribute("transItem", transResource);
        return "trans/trans-list-btn";
    }

    /**
     * 大屏左边
     * @param page
     * @param title
     * @param model
     * @param displayStatus
     * @return
     */
    @RequestMapping("/view-resource/view_left")
    public String viewLeft(@PageDefault(value=10) Pageable page, String title, Model model,
                           @RequestParam(value = "displayStatus", required = false,defaultValue = "-1")String displayStatus) {
        String regions = null;
        if (!SecUtil.isAdmin()) {
            regions = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResource= resourceClient.findDisplayResource(title,displayStatus,regions,page);
        model.addAttribute("transList", transResource);
        return "trans/guest-view-left";
    }

    @RequestMapping("resource")
    @ResponseBody
    public Object getResource(Model model) {
        Map result = Maps.newHashMap();
        List<TransResource> transResources = resourceClient.findTransResourcesByDisplayStatus(1);
        if (transResources!=null && transResources.size()>0){
            List<Map> transMap = Lists.newArrayList();
            int i = 0;
            for (TransResource transResource : transResources) {
                i++;
                Map tmp = Maps.newHashMap();
                tmp.put("itemcount", transResources.size());
                tmp.put("currentindex", i);
                tmp.put("splittime", "5000");
                tmp.put("resource", transResource);
                Page<TransResourceOffer> resourceOffers =
                        transResourceOfferClient.getResourceOfferPage(transResource.getResourceId(), new PageRequest(0, 5));
                Double maxOffer = (resourceOffers != null && resourceOffers.getItems().size() > 0) ? resourceOffers.getItems().get(0).getOfferPrice() : transResource.getBeginOffer();
                tmp.put("maxOffer", maxOffer);
                tmp.put("offerTime", transResource.getGpEndTime());
                tmp.put("offerList", resourceOffers.getItems());
                tmp.put("offerFrequency", transResourceOfferClient.getResourceOfferFrequency(transResource.getResourceId()));
                if (StringUtils.isNotBlank(transResource.getOfferId())) {
                    TransResourceOffer transResourceOffer = transResourceOfferClient.getTransResourceOffer(transResource.getOfferId());
                    TransUser user=transUserClient.getTransUserById(transResourceOffer.getUserId());
                    if (user!=null){
                        tmp.put("offerUser", user.getViewName());
                    }
                }
                transMap.add(tmp);
            }
            result.put("dikuai", transMap);
        }
        return result;
    }

}
