package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceApply;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.core.service.TransResourceApplyService;
import cn.gtmap.landsale.core.service.TransResourceOfferService;
import cn.gtmap.landsale.core.service.TransResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地块竞买人信息
 * @author jibo1_000 on 2015/5/25.
 */
@RestController
@RequestMapping(value = "console/resource-apply")
public class ResourceApplyController {

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransResourceService transResourceService;

    @RequestMapping("list")
    public String list(String resourceId, Model model) {
        List<TransResourceApply> transResourceApplyList =
                transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
        model.addAttribute("transResourceApplyList", transResourceApplyList);
        model.addAttribute("resourceId", resourceId);
        return "resourceapply-list";
    }

    @RequestMapping("offerlist")
    public String offerList(String resourceId, @PageDefault(value = 15) Pageable page, Model model) {
        Page<TransResourceOffer> transResourceOfferPage =
                transResourceOfferService.getResourceOfferPage(resourceId, page);
        model.addAttribute("transResourceOfferPage", transResourceOfferPage);
        model.addAttribute("resourceId", resourceId);
        return "resourceoffer-list";
    }

    @RequestMapping("attachment")
    public String attachments(@RequestParam(value = "resourceId", required = true) String resourceId, @RequestParam(value = "userId", required = true) String userId, Model model) {
        TransResourceApply transResourceApply =
                transResourceApplyService.getTransResourceApplyByUserId(userId, resourceId);
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("resourceId", resourceId);
        return "resource-apply-attachments";
    }

    @RequestMapping("/getTransResourceApplyByUserId")
    public TransResourceApply getTransResourceApplyByUserId(@RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "resourceId", required = true) String resourceId) {
        return transResourceApplyService.getTransResourceApplyByUserId(userId, resourceId);

    }

    @RequestMapping("/getTransResourceApply")
    public TransResourceApply getTransResourceApply(@RequestParam(value = "applyId", required = true) String applyId) {
        return transResourceApplyService.getTransResourceApply(applyId);
    }

    @RequestMapping("/saveTransResourceApply")
    public ResponseMessage<TransResourceApply> saveTransResourceApply(@RequestBody TransResourceApply transResourceApply) {
        return transResourceApplyService.saveTransResourceApply(transResourceApply);
    }


    @RequestMapping("/getTransResourceApplyByResourceId")
    public List<TransResourceApply> getTransResourceApplyByResourceId(@RequestParam(value = "resourceId") String resourceId) {
        return transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
    }


    @RequestMapping("/getTransResourceApplyStep")
    public List<TransResourceApply> getTransResourceApplyStep(@RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "applyStep") int applyStep) {
        return transResourceApplyService.getTransResourceApplyStep(resourceId, applyStep);
    }

    /**
     * 获得参与限时竞买的人
     * @param resourceId
     * @return
     */
    @RequestMapping("/getEnterLimitTransResourceApply")
    public List<TransResourceApply> getEnterLimitTransResourceApply(@RequestParam(value = "resourceId") String resourceId) {
        return transResourceApplyService.getEnterLimitTransResourceApply(resourceId);
    }

    /**
     * 我的交易列表
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping("/getTransResourceApplyPageByUserId")
    public Page<TransResourceApply> getTransResourceApplyPageByUserId(@RequestParam(value = "userId", required = true) String userId,@RequestBody Pageable request) {
        return transResourceApplyService.getTransResourceApplyPageByUserId(userId,request);
    }

    /**
     * 删除报名信息
     * @param applyId
     * @return
     */
    @RequestMapping("/deleteApply")
    public ResponseMessage<TransResourceApply> deleteApply(@RequestParam(value = "applyId", required = true) String applyId) {
        return transResourceApplyService.deleteApply(applyId);
    }



}
