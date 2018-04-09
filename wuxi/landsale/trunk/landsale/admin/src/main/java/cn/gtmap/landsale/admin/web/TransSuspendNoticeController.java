package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransCrggClient;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.admin.register.TransSuspendNoticeClient;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransSuspendNotice;
import cn.gtmap.landsale.common.security.SecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 中止公告服务
 * @author zsj
 * @version v1.0, 2017/10/19
 */
@Controller
@RequestMapping("/suspendNotice")
public class TransSuspendNoticeController {

    @Autowired
    TransSuspendNoticeClient transSuspendNoticeClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransResourceClient transResourceClient;

    private static final int NOTICE_DEPLOYED=1;//公告发布

    private static final int NOTICE_UNDO=0;//公告撤销


    @RequestMapping("/index")
    public String index(Model model, String noticeTitle, @PageDefault(value = 10) Pageable pageable) {
        String regionCodes = null;
        if (!SecUtil.isAdmin()) {
            regionCodes = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransCrgg> transSuspendCrggList = transCrggClient.findSuspendNotice(noticeTitle, regionCodes ,pageable);
        model.addAttribute("transSuspendNoticeList", transSuspendCrggList);
        model.addAttribute("noticeTitle", noticeTitle);
        return "suspend/suspend-list";
    }

    @RequestMapping("/add")
     public String add(Model model) {
        model.addAttribute("suspendNotice", new TransCrgg());
        return "suspend/suspend-add";
    }

    @RequestMapping("/addByResource")
    public String addByResource(Model model, String resourceId) {
        // 通过地块Id 查找地块
        TransResource transResource = transResourceClient.getTransResource(resourceId);
//        TransSuspendNotice suspendNotice = new TransSuspendNotice();
//        suspendNotice.setResourceId(transResource.getResourceId());
//        suspendNotice.setResourceCode(transResource.getResourceCode());
        TransCrgg transCrgg = new TransCrgg();
        transCrgg.setResourceCode(transResource.getResourceCode());
        model.addAttribute("suspendNotice", transCrgg);
        return "suspend/suspend-add";
    }

    @RequestMapping("/addSuspend")
    @ResponseBody
    public ResponseMessage<TransCrgg> addSuspend(TransCrgg transCrgg,String afficheType) {
        // 通过地块编号 查找地块
        TransResource transResource = transResourceClient.getTransResourceByCode(transCrgg.getResourceCode());
        if (transResource == null) {
            return new ResponseMessage<>(false, "未通过地块编号找到地块,请检查！");
        }
        List<TransResource>  resourceList = new ArrayList<>();
        resourceList.add(transResource);
        transCrgg.setResourceList(resourceList);
        transCrgg.setAfficheType(Integer.valueOf(afficheType));
        transCrgg.setGgBeginTime(new Date());
        transCrgg.setGgEndTime(new Date());
        transCrgg.setSignStartTime(new Date());
        transCrgg.setSignEndTime(new Date());
        transCrgg.setGgId(null);
        return transCrggClient.saveTransCrgg(transCrgg,null);
    }

    @RequestMapping("/edit")
    public String edit(String noticeId, Model model) {
        TransCrgg transCrgg = transCrggClient.getTransCrgg(noticeId);
        model.addAttribute("suspendNotice", transCrgg);
        return "suspend/suspend-add";
    }

    @RequestMapping("/detail")
    public String detail(String noticeId, Model model) {
        TransCrgg transCrgg = transCrggClient.getTransCrgg(noticeId);
        model.addAttribute("suspendNotice", transCrgg);
        return "suspend/suspend-detail";
    }

    @RequestMapping("/editSuspend")
    @ResponseBody
    public ResponseMessage<TransCrgg> editSuspend(TransCrgg transCrgg,String afficheType) {
        // 通过地块编号 查找地块
        TransResource transResource = transResourceClient.getTransResourceByCode(transCrgg.getResourceCode());
        if (transResource == null) {
            return new ResponseMessage<>(false, "未通过地块编号找到地块,请检查！");
        }
        List<TransResource>  resourceList = new ArrayList<>();
        resourceList.add(transResource);
        transCrgg.setResourceList(resourceList);
        transCrgg.setGgBeginTime(new Date());
        transCrgg.setGgEndTime(new Date());
        transCrgg.setSignStartTime(new Date());
        transCrgg.setSignEndTime(new Date());
        transCrgg.setAfficheType(Integer.valueOf(afficheType));
        return transCrggClient.saveTransCrgg(transCrgg,null);
    }

    @RequestMapping("/deleteSuspend")
    @ResponseBody
    public ResponseMessage<TransCrgg> deleteSuspend(String noticeIds) {
        return transCrggClient.deleteSuspendCrgg(noticeIds);
    }

    @RequestMapping("/deploy")
    @ResponseBody
    public ResponseMessage<TransCrgg> deploy(String noticeId){
        TransCrgg transCrgg = transCrggClient.getTransCrgg(noticeId);
        transCrgg.setCrggStauts(NOTICE_DEPLOYED);
        return transCrggClient.saveTransCrgg(transCrgg,null);
    }

    @RequestMapping("/revoke")
    @ResponseBody
    public ResponseMessage<TransCrgg> revoke(String noticeId){
        TransCrgg transCrgg = transCrggClient.getTransCrgg(noticeId);
        transCrgg.setCrggStauts(NOTICE_UNDO);
        return transCrggClient.saveTransCrgg(transCrgg,null);
    }

}

