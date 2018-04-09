package cn.gtmap.landsale.client.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.client.register.*;
import cn.gtmap.landsale.client.service.ResourceOfferQueueService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.unbescape.html.HtmlEscape;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 地块详情
 * @author zsj
 * @version v1.0, 2017/12/27
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    private static final long TIMEOUT = 72 * 1000;  //1.2分钟超时时间，参考taobao
    private static Logger lOG = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    TransResourceClient transResourceClient;

//    @Autowired
//    TransOfferClient offerClient;

    @Autowired
    ResourceOfferQueueService resourceOfferQueueService;

    @Autowired
    TransResourceInfoClient transResourceInfoClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    TransOrganizeClient transOrganizeClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransFileClient transFileClient;

    @Autowired
    AttachmentCategoryClient attachmentCategoryClient;

    @Autowired
    ClientClient clientClient;

    @Autowired
    ViewClient viewClient;

    @Autowired
    OneParamClient oneParamClient;

    @Autowired
    OneTargetClient oneTargetClient;

    @Autowired
    YHClient yhClient;

    @Autowired
    TransResourceSonClient transResourceSonClient;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    /**
     * 首页
     *
     * @param regionCode
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public String index(String regionCode, Model model) throws Exception {
        model.addAttribute("regionCode", regionCode);
        return "resource/index";
    }

    /**
     * 首页列表
     * @param param
     * @param page
     * @param regionCode
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/content")
    public String detail(ResourceQueryParam param, @PageDefault(value = 12) Pageable page, String regionCode, ModelMap model) throws Exception {
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_PRE_RELEASE);
        if (param.getResourceStatus() == Constants.RESOURCE_STATUS_CHENG_JIAO) {
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.RESOURCE_STATUS_GONG_GAO);
        }
        param.setPage(page);
        Page<TransResource> transResourcePage = transResourceClient.findTransResources(param, regionCode);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("param", param);
        return "resource/resource-content";
    }


    @RequestMapping("/view")
     public String view(String resourceId, ModelMap model, HttpServletRequest request) throws Exception {

        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("resource", transResource);
        TransOrganize transOrganize = transOrganizeClient.getTransOrganizeById(transResource.getOrganizeId());
        model.addAttribute("transOrganize", transOrganize);
        TransResourceInfo transResourceInfo = transResourceInfoClient.getTransResourceInfoByResourceId(resourceId);
        if (transResourceInfo == null) {
            transResourceInfo = new TransResourceInfo();
        }
        model.addAttribute("resourceInfo", transResourceInfo);
        //获得报价列表（15条）
        Page<TransResourceOffer> resourceOffers = transResourceOfferClient.getResourceOfferPage(resourceId, new PageRequest(0, 10));
//        List<TransResourceOffer> resourceOffers = transResourceOfferClient.getOfferList(resourceId, -1);
        model.addAttribute("resourceOffers", resourceOffers);
        model.addAttribute("cDate", Calendar.getInstance().getTime().getTime());
        //获得当前最高报价+
        TransResourceOffer maxOffer = (resourceOffers.getItems().size() > 0) ? resourceOffers.getItems().get(0) : null;
        model.addAttribute("maxOffer", maxOffer);
        // 当前用户
        TransUser transUser = SecUtil.getLoginUserToSession(request);
        if (transUser != null) {
            model.addAttribute("userId", transUser.getUserId());
        }
        TransResourceOffer successOffer = null;
        if(null != transResource.getOfferId()){
            successOffer = transResourceOfferClient.getTransResourceOffer(transResource.getOfferId());
        }
        model.addAttribute("successOffer", successOffer);
        // 判断成交
        if (Constants.RESOURCE_STATUS_CHENG_JIAO == transResource.getResourceStatus()) {
            // 存在最高限价 判断成交方式 最高限价后成交
            if ((Constants.Whether.YES.equals(transResource.getMaxOfferExist()))
                    && (transResource.getMaxOffer().compareTo(maxOffer.getOfferPrice()) <= 0)) {
                // 判断成交方式 一次报价
                if (Constants.MaxOfferChoose.YCBJ.getCode() == transResource.getMaxOfferChoose().getCode()) {
                    OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(resourceId);
                    model.addAttribute("oneTarget", oneTarget);
                // 摇号
                } else if (Constants.MaxOfferChoose.YH.getCode() == transResource.getMaxOfferChoose().getCode()) {
                    YHResult yhResult = yhClient.getYHResultByResourceId(resourceId);
                    model.addAttribute("yhResult", yhResult);
                }
            }
        }
        model.addAttribute("ggNum", StringUtils.isNotBlank(transResource.getGgId()) ? transCrggClient.getTransCrgg(transResource.getGgId()).getGgNum() : "");
        model.addAttribute("attachments", transFileClient.getTransFileAttachments(resourceId));
        Map attachmentCategory = attachmentCategoryClient.getTransResourceAttachmentCategory();
        attachmentCategory.put(Constants.FileType.QT.getCode(), Constants.FileType.QT.toString());
        model.addAttribute("attachmentCategory", attachmentCategory);
        model.addAttribute("crggAttachments", transFileClient.getTransFileAttachments(transResource.getGgId()));
        // 地块多用途信息
        List<TransResourceSon> transResourceSonList = transResourceSonClient.getTransResourceSonList(resourceId);
        model.addAttribute("transResourceSonList", transResourceSonList);
        return "resource/resource-view";
    }

    /**
     * 地块的中止、终止和成交公告加载
     * @param resourceId
     * @return
     */
    @RequestMapping("/resourceNotice")
    public String resourceNotice(String resourceId,Model model){
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        List noticeList = transResourceClient.resourceNotice(transResource.getResourceId());
        if(noticeList == null) {
            noticeList = new ArrayList();
        }
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("transResource",transResource);
        return "common/resource-noticeList";
    }

    @RequestMapping("/view/crgg")
    public String viewCrgg(@RequestParam(value = "id", required = true) String id, Model model) {
        TransResource transResource = transResourceClient.getTransResource(id);
        String content = "";
        if (StringUtils.isNotBlank(transResource.getGgId())) {
            content = transCrggClient.getTransCrgg(transResource.getGgId()).getGgContent();
        }

        model.addAttribute("content", HtmlEscape.unescapeHtml(content));
//        model.addAttribute("crggAttachments", transFileClient.getTransFileAttachments(transResource.getGgId()));
        return "resource/resource-view-crgg";
    }

    @RequestMapping("/view/offer/list")
    public String offerList(@PageDefault(value = 10) Pageable page, @RequestParam(value = "resourceId", required = true) String resourceId, Model model) {
        Page<TransResourceOffer> transResourceOffers = transResourceOfferClient.getResourceOfferPage(resourceId, page);
        model.addAttribute("transResourceOffers", transResourceOffers);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("resource", transResourceClient.getTransResource(resourceId));
        model.addAttribute("userId", SecUtil.getLoginUserId());
        return "resource/resource-view-offer-list";
    }

    @RequestMapping("/view/geometry")
    @ResponseBody
    public Object viewResourceGeometry(@RequestParam(value = "id", required = true) String id, Model model) {
        return viewClient.viewResourceGeometry(id, model);
    }

    /**
        用于判断地块状态，以确定显示页面
     */
    @RequestMapping("/decideResourceView")
    public String decideResourceView(@RequestParam(value = "resourceId") String resourceId) {
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        //获得报价列表（15条）
        List<TransResourceOffer> resourceOffers = transResourceOfferClient.getOfferList(resourceId, -1);
        TransResourceOffer maxOffer = (resourceOffers.size() > 0) ? resourceOffers.get(0) : null;
        // 判断是否 达到最高限价
        if (maxOffer != null && Constants.RESOURCE_STATUS_MAX_OFFER == transResource.getResourceStatus()) {
            // 判断 最高限价后的 竞价方式
            if (Constants.MaxOfferChoose.YCBJ.getCode() == transResource.getMaxOfferChoose().getCode()) {
                // 如果在一次报价阶段，要判断当前时间
                Date cDate= Calendar.getInstance().getTime();
                OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(transResource.getResourceId());
                // 当前时间 < 一次报价开始时间
                if (oneTarget != null && cDate.after(oneTarget.getStopDate())) {
                    // 进入一次报价时间
                    return "redirect:/oneprice/view?id=" + oneTarget.getId();
                } else {
                    return "redirect:/resourceOffer/view?resourceId=" + resourceId;
                }
            } else {
                // 其他方式
                return "redirect:/resourceOffer/view?resourceId=" + resourceId;
            }
        } else {
            return "redirect:/resourceOffer/view?resourceId=" + resourceId;
        }
    }

}
