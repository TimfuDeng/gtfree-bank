package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.DealNotice;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.DealNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 成交公示
 * @author cxm
 * @version v1.0, 2017/11/14
 */
@RestController
@RequestMapping("/dealNotice")
public class DealNoticeController {
    @Autowired
    DealNoticeService dealNoticeService;

    @RequestMapping("/findAllDealNotices")
    public Page<DealNotice> findAllDealNotices(@PageDefault(value = 10) Pageable request,@RequestParam(value = "noticeTitle",required = false) String noticeTitle) {
        return dealNoticeService.findAllDealNotices(request,noticeTitle);
    }

    @RequestMapping("/getNotice")
    public DealNotice getNotice(@RequestParam(value = "noticeId") String noticeId) {
        return dealNoticeService.getNotice(noticeId);
    }

    @RequestMapping("/saveDealNotice")
    public ResponseMessage<DealNotice> saveDealNotice(@RequestBody DealNotice dealNotice) {
        return dealNoticeService.saveDealNotice(dealNotice);
    }

    @RequestMapping("/deleteDealNotice")
    public ResponseMessage<DealNotice> deleteDealNotice(@RequestParam(value = "noticeIds") String noticeIds) {
        return dealNoticeService.deleteDealNotice(noticeIds);
    }


}
