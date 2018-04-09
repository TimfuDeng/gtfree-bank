package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceMinPrice;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceMinPriceService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * 底价管理
 * Created by Jibo on 2015/6/2.
 */
@Controller
@RequestMapping(value = "console/price")
public class PriceController {

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransResourceMinPriceService transResourceMinPriceService;

    @RequestMapping("list")
    public String list(@PageDefault(value=8) Pageable page,Model model) {
        ResourceQueryParam param=new ResourceQueryParam();
        param.setResourceStatus(Constants.ResourceStatusGuaPai);
        param.setResourceEditStatus(Constants.ResourceEditStatusRelease);
        param.setDisplayStatus(-1);
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResourcePage= transResourceService.findTransResourcesByMinOffer(param,regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        return "price-resource-list";
    }

    @RequestMapping("price-edit")
    public String applylist(String resourceId,Model model) {
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceService.getMinPriceListByResourceId(resourceId);
        TransResourceMinPrice minPrice=new  TransResourceMinPrice();
        for(TransResourceMinPrice transResourceMinPrice: transResourceMinPriceList){
            if (transResourceMinPrice.getUserId().equals(SecUtil.getLoginUserId())) {
                minPrice = transResourceMinPrice;
                break;
            }
        }
        TransResource transResource= transResourceService.getTransResource(resourceId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("transResource", transResource);
        model.addAttribute("resourceId", resourceId);
        return "price-edit";
    }

    @RequestMapping("save")
    public String save( String resourceId,double price,RedirectAttributes ra){
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceService.getMinPriceListByResourceId(resourceId);
        TransResourceMinPrice minPrice=new  TransResourceMinPrice();
        for(TransResourceMinPrice transResourceMinPrice: transResourceMinPriceList){
            if (transResourceMinPrice.getUserId().equals(SecUtil.getLoginUserId())) {
                minPrice = transResourceMinPrice;
                break;
            }
        }
        TransResource transResource= transResourceService.getTransResource(resourceId);
        if (transResource.getBeginOffer()>price){
            ra.addFlashAttribute("_result", false);
            ra.addFlashAttribute("_msg", "底价低于起始价！");
        }else {
            minPrice.setResourceId(resourceId);
            minPrice.setUserId(SecUtil.getLoginUserId());
            minPrice.setPrice(price);
            minPrice.setCreateTime(Calendar.getInstance().getTime());
            transResourceMinPriceService.saveTransResourceInfo(minPrice);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "保存成功！溢价：" + Math.round(
                    (price - transResource.getBeginOffer())*100 / transResource.getBeginOffer()) + "%");
        }
        return "redirect:/console/price/price-edit?resourceId=" + resourceId;
    }
}
