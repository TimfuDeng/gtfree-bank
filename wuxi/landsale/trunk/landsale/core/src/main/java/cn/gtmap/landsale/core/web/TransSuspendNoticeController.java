package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransSuspendNotice;
import cn.gtmap.landsale.core.service.TransSuspendNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中止公告 服务
 * @author zsj
 * @version v1.0, 2017/11/3
 */
@RestController
@RequestMapping(value = "/suspendNotice")
public class TransSuspendNoticeController {

    @Autowired
    TransSuspendNoticeService transSuspendNoticeService;

    /**
     * 获取所有的中止公告对象
     * @param pageable
     * @param title
     * @return
     */
    @RequestMapping("/findAllSuspendNotices")
    public Page<TransSuspendNotice> findAllSuspendNotices(@RequestBody Pageable pageable, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "regionCodes", required = false) String regionCodes) {
        return transSuspendNoticeService.findAllSuspendNotices(pageable, title, regionCodes);

    }

    /**
     * 保存中止公告
     * @param transSuspendNotice
     * @return
     */
    @RequestMapping("/saveSuspendNotice")
    public ResponseMessage<TransSuspendNotice> saveSuspendNotice(@RequestBody TransSuspendNotice transSuspendNotice) {
        return transSuspendNoticeService.saveSuspendNotice(transSuspendNotice);
    }

    /**
     * 删除中止公告
     * @param noticeIds
     */
    @RequestMapping("/deleteSuspendNotice")
    public ResponseMessage deleteSuspendNotice(@RequestParam(value = "noticeIds") String noticeIds) {
        return transSuspendNoticeService.deleteSuspendNotice(noticeIds);
    }

    /**
     * 根据noticeId获取中止公告对象
     * @param noticeId
     * @return
     */
    @RequestMapping("/getNotice")
    public TransSuspendNotice getNotice(@RequestParam(value = "noticeId") String noticeId) {
        return transSuspendNoticeService.getNotice(noticeId);
    }

    /**
     * 根据发布状态查询中止公告
     * @param pageable
     * @param deployStatus
     * @return
     */
    @RequestMapping("/findAllSuspendNoticesByStatus")
    public Page<TransSuspendNotice> findAllSuspendNoticesByStatus(@RequestBody Pageable pageable, @RequestParam(value = "deployStatus", required = false) int deployStatus) {
        return transSuspendNoticeService.findAllSuspendNoticesByStatus(pageable, deployStatus);
    }

}
