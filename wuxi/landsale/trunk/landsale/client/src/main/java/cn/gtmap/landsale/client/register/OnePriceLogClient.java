package cn.gtmap.landsale.client.register;

import cn.gtmap.landsale.common.model.OnePriceLog;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 一次报价记录
 */
@FeignClient(name = "core-server")
public interface OnePriceLogClient {

    /**
     * 根据地块id获取一次报价列表
     * @param transResourceId
     * @return
     */
    @RequestMapping(value = "/onePriceLog/findOnePriceLogList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<OnePriceLog> findOnePriceLogList(@RequestParam(value = "transResourceId") String transResourceId);

    /**
     * 以报价排序查询一次报价列表
     * @param transResourceId
     * @return
     */
    @RequestMapping(value = "/onePriceLog/findOnePriceLogListOrderByBj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<OnePriceLog> findOnePriceLogListOrderByBj(@RequestParam(value = "transResourceId") String transResourceId);

    /**
     * 根据地块id和用户id获取
     * @param transResourceId
     * @param transUserId
     * @return
     */
    @RequestMapping(value = "/onePriceLog/findOnePriceLogListByTransResourceIdTransUserId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OnePriceLog findOnePriceLogListByTransResourceIdTransUserId(@RequestParam(value = "transResourceId") String transResourceId, @RequestParam(value = "transUserId") String transUserId);

    /**
     * 根据地块id和报价获取
     * @param transResourceId
     * @param price
     * @return
     */
    @RequestMapping(value = "/onePriceLog/findOnePriceLogListByTransResourceIdPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OnePriceLog findOnePriceLogListByTransResourceIdPrice(@RequestParam(value = "transResourceId") String transResourceId, @RequestParam(value = "price") Long price);

    /**
     * 保存
     * @param onePriceLog
     * @return
     */
    @RequestMapping(value = "/onePriceLog/saveOnePriceLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<OnePriceLog> saveOnePriceLog(@RequestBody OnePriceLog onePriceLog);

    /**
     * 根据applyId获取一次报价记录
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/onePriceLog/getOnePriceLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OnePriceLog getOnePriceLog(@RequestParam(value = "applyId") String applyId);

}
