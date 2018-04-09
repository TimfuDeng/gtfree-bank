package cn.gtmap.landsale.admin.register;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.DealNotice;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 成交公示
 * @author cxm
 * @version v1.0, 2017/11/14
 */
@FeignClient(name = "core-server")
public interface DealNoticeClient {

    /**
     * 获取所有的成交公告对象
     *
     * @param request
     * @param noticeTitle
     * @return
     */
    @RequestMapping(value = "/dealNotice/findAllDealNotices", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<DealNotice> findAllDealNotices(@PageDefault(value = 10) Pageable request,@RequestParam(value = "noticeTitle",required = false) String noticeTitle);

    /**
     * 根据noticeId获取成交公告对象
     *
     * @param noticeId
     * @return
     */
    @RequestMapping(value = "/dealNotice/getNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    DealNotice getNotice(@RequestParam(value = "noticeId") String noticeId);

    /**
     * 发布成交通知
     * @param dealNotice
     * @return
     */
    @RequestMapping(value = "/dealNotice/saveDealNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<DealNotice> saveDealNotice(@RequestBody DealNotice dealNotice);

    /**
     * 编辑
     * @param dealNotice
     * @return
     */
    @RequestMapping(value = "/dealNotice/editDealNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<DealNotice> editDealNotice(@RequestBody DealNotice dealNotice);

    /**
     * 删除成交公告
     *
     * @param noticeIds
     */
    @RequestMapping(value = "/dealNotice/deleteDealNotice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<DealNotice> deleteDealNotice(@RequestParam(value = "noticeIds") String noticeIds);

//    @RequestMapping(value = "/dealNotice/deploy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    ResponseMessage<DealNotice> deploy(@RequestParam(value = "noticeId") String noticeId);
//
//    @RequestMapping(value = "/dealNotice/revoke", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    ResponseMessage<DealNotice> revoke(@RequestParam(value = "noticeId") String noticeId);


}
