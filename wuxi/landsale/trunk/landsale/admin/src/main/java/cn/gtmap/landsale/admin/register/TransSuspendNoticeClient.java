package cn.gtmap.landsale.admin.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransSuspendNotice;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 中止公告 服務
 * @author zsj
 * @version v1.0, 2017/11/3
 */
@FeignClient(name = "core-server")
public interface TransSuspendNoticeClient {

    /**
     * 获取所有的中止公告对象
     * @param pageable
     * @param title
     * @param regionCodes
     * @return
     */
    @RequestMapping(value = "/suspendNotice/findAllSuspendNotices", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransSuspendNotice> findAllSuspendNotices(@RequestBody Pageable pageable, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "regionCodes", required = false) String regionCodes);

    /**
     * 保存中止公告
     * @param transSuspendNotice
     * @return
     */
    @RequestMapping(value = "/suspendNotice/saveSuspendNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransSuspendNotice> saveSuspendNotice(@RequestBody TransSuspendNotice transSuspendNotice);

    /**
     * 删除中止公告
     * @param noticeIds
     * @return
     */
    @RequestMapping(value = "/suspendNotice/deleteSuspendNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteSuspendNotice(@RequestParam(value = "noticeIds") String noticeIds);

    /**
     * 根据noticeId获取中止公告对象
     * @param noticeId
     * @return
     */
    @RequestMapping(value = "/suspendNotice/getNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransSuspendNotice getNotice(@RequestParam(value = "noticeId") String noticeId);

    /**
     * 根据发布状态查询中止公告
     * @param pageable
     * @param deployStatus
     * @return
     */
    @RequestMapping(value = "/suspendNotice/findAllSuspendNoticesByStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransSuspendNotice> findAllSuspendNoticesByStatus(@RequestBody Pageable pageable, @RequestParam(value = "deployStatus", required = false) int deployStatus);

}
