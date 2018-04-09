package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.admin.register.TransResourceMinPriceClient;
import cn.gtmap.landsale.admin.service.TransFloorPriceService;
import cn.gtmap.landsale.admin.service.TransResourceService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Date;
import java.util.List;

/**
 * 底价管理
 * @author cxm
 * @version v1.0, 2017/11/06
 */
@Controller
@RequestMapping("/price")
public class PriceController {
    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransFloorPriceService transFloorPriceService;

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceMinPriceClient transResourceMinPriceClient;

    @RequestMapping("/index")
    public String index(@PageDefault(value=8) Pageable page,Model model) {
        ResourceQueryParam param=new ResourceQueryParam();
        param.setResourceStatus(Constants.RESOURCE_STATUS_GUA_PAI);
        param.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_RELEASE);
        param.setDisplayStatus(-1);
        //获取当前登录用户得的权限(不同的行政区不同用途的地块)
        String regions;
        String userId="";
        if (SecUtil.isAdmin()) {
            regions=null;
            Page<TransResource> transResourcePage= transResourceService.findTransResourcesByMinOffer(param,regions, page);
            model.addAttribute("transResourcePage", transResourcePage);
        }else {
            regions = SecUtil.getLoginUserRegionCodes();
            userId=SecUtil.getLoginUserId();
            TransFloorPrice transFloorPrice=transFloorPriceService.getTransFloorPrice(userId);
            String tdytDictCodes=transFloorPrice.getTdytDictCode();
            param.setRegionCode(regions);
            param.setTdytDictCodes(tdytDictCodes);
            Page<TransResource> transResourcePage= transResourceService.findTransResourcesByMinOffer(param,regions, page);
            model.addAttribute("transResourcePage", transResourcePage);
        }
        return "price/price-resource-list";
    }

    @RequestMapping("/edit")
    public String applylist(String resourceId,Model model) {
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceClient.getMinPriceListByResourceId(resourceId);
        TransResourceMinPrice minPrice=new  TransResourceMinPrice();
        for(TransResourceMinPrice transResourceMinPrice: transResourceMinPriceList){
            if (transResourceMinPrice.getUserId().equals(SecUtil.getLoginUserId())) {
                minPrice = transResourceMinPrice;
                break;
            }
        }
        TransResource transResource= transResourceClient.getTransResource(resourceId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("transResource", transResource);
        model.addAttribute("resourceId", resourceId);
        return "price/price-edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResponseMessage<TransResourceMinPrice> save( String resourceId,double price,RedirectAttributes ra){
        List<TransResourceMinPrice> transResourceMinPriceList =
                transResourceMinPriceClient.getMinPriceListByResourceId(resourceId);
        TransResourceMinPrice minPrice=new  TransResourceMinPrice();
        // 判断当前登录人 之前是否设置过底价 已设置在原有的基础上修改
        for(TransResourceMinPrice transResourceMinPrice: transResourceMinPriceList){
            if (transResourceMinPrice.getUserId().equals(SecUtil.getLoginUserId())) {
                minPrice = transResourceMinPrice;
                break;
            }
        }
        TransResource transResource= transResourceClient.getTransResource(resourceId);
        if (transResource.getBeginOffer()>price){
            return new ResponseMessage(false,"底价低于起始价！");
        }else {
            minPrice.setResourceId(resourceId);
            minPrice.setUserId(SecUtil.getLoginUserId());
            minPrice.setPrice(price);
            minPrice.setCreateTime(new Date());
            return transResourceMinPriceClient.saveTransResourceInfo(minPrice);
        }

    }



}
