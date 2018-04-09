package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.DealNoticeClient;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.common.model.DealNotice;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResource;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 成交公示
 * @author cxm
 * @version v1.0, 2017/11/14
 */
@Controller
@RequestMapping("/dealNotice")
public class DealNoticeController {

    private static final int NOTICE_DEPLOYED=1;//公告发布

    private static final int NOTICE_UNDO=0;//公告撤销

    @Autowired
    DealNoticeClient dealNoticeClient;

    @Autowired
    TransResourceClient transResourceClient;

    @ModelAttribute("dealNotice")
    public DealNotice getNotice(@RequestParam(value="noticeId",required = false) String noticeId){
        return StringUtils.isBlank(noticeId)? new DealNotice() : dealNoticeClient.getNotice(noticeId);
    }

    /**
     * 获取所有的成交公告对象
     * @param page
     * @param noticeTitle
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(@PageDefault(value = 10)Pageable page,String noticeTitle,Model model){
        Page<DealNotice> dealNoticeList = dealNoticeClient.findAllDealNotices(page, noticeTitle);
        model.addAttribute("dealNoticeList",dealNoticeList);
        model.addAttribute("noticeTitle", noticeTitle);
        return "dealNotice/deal-notice-list";
    }

    /**
     * 跳转新增公示页面
     * @param noticeId
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(String noticeId,String resourceCode,Model model){
//        if (StringUtils.isNotBlank(noticeId)){
//            TransResource resource=transResourceClient.getTransResource(resourceId);
//            if (resource!=null){
//                model.addAttribute("resourceCode",resource.getResourceCode());
//            }
//        }
        DealNotice dealNotice = null;
        if(StringUtils.isNotBlank(noticeId)){
            dealNotice = dealNoticeClient.getNotice(noticeId);
        }else{
            dealNotice = new DealNotice();
        }
        model.addAttribute("dealNotice",dealNotice);
        model.addAttribute("resourceCode",resourceCode);
        return "dealNotice/deal-notice-edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public ResponseMessage<DealNotice> save(@ModelAttribute("dealModel")DealNotice dealNotice, Model model){
        if(StringUtils.isBlank(dealNotice.getNoticeId())){
            dealNotice.setNoticeId(null);
        }
        return dealNoticeClient.saveDealNotice(dealNotice);
    }


    @RequestMapping("/detail")
    public String detail(@RequestParam(value="noticeId",required = false)String noticeId,Model model){
        DealNotice dealNotice;
        try {
            dealNotice = dealNoticeClient.getNotice(noticeId);
        } catch (Exception e) {
            dealNotice = new DealNotice();
        }
        model.addAttribute("dealNotice",dealNotice);
        return "dealNotice/deal-notice-detail";
    }


    @RequestMapping("/deleteDealNotice")
    @ResponseBody
    public ResponseMessage<DealNotice> delete(@RequestParam(value="noticeIds",required = false)String noticeIds){
        List<String> noticeIdList = Lists.newArrayList(noticeIds.split(";"));
        //获取成交公示
        for(String noticeId:noticeIdList){
            DealNotice dealNotice = dealNoticeClient.getNotice(noticeId);
            String resourceCode = dealNotice.getResourceCode();
            TransResource transResource = null;
            try {
                transResource = transResourceClient.getTransResourceByCode(resourceCode);
                transResource.setSuspendNoticeId(null);
                transResourceClient.saveTransResource(transResource);
            } catch (Exception e) {
                continue;
            }
        }
        return dealNoticeClient.deleteDealNotice(noticeIds);

    }


    /**
     * 发布成交通知
     * @param noticeId
     * @param model
     * @return
     */
    @RequestMapping("/deploy")
    @ResponseBody
    public ResponseMessage<DealNotice> deploy(@RequestParam(value="noticeId",required = false) String noticeId,Model model){
        DealNotice dealNotice = dealNoticeClient.getNotice(noticeId);
        dealNotice.setDeployStatus(NOTICE_DEPLOYED);
        return dealNoticeClient.saveDealNotice(dealNotice);
    }

    /**
     * 撤回成交通知
     * @param noticeId
     * @param model
     * @return
     */
    @RequestMapping("/revoke")
    @ResponseBody
    public ResponseMessage<DealNotice> revoke(String noticeId,Model model){
        DealNotice dealNotice;
        try {
            dealNotice = dealNoticeClient.getNotice(noticeId);
            dealNotice.setDeployStatus(NOTICE_UNDO);
            dealNoticeClient.saveDealNotice(dealNotice);
        } catch (Exception e) {
            dealNotice = new DealNotice();
        }
        model.addAttribute("notice",dealNotice);
        return new ResponseMessage(true,"撤回成功！");
    }


}
