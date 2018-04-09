package cn.gtmap.landsale.admin.register;

import cn.gtmap.landsale.common.model.OneTarget;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 一次报价标的
 * Created by lq on 2017/11/17.
 */
@FeignClient(name = "core-server")
public interface OneTargetClient {

    /**
     * 获取 OneTarget
     * @param id
     * @return
     */
    @RequestMapping(value = "/oneTarget/getOneTarget",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneTarget getOneTarget(@RequestParam(value = "id") String id);

    /**
     * 通过 resourceId 获取 OneTarget
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/oneTarget/getOneTargetByTransResource",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneTarget getOneTargetByTransResource(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 保存
     * @param oneTarget
     * @return
     */
    @RequestMapping(value = "/oneTarget/saveOneTarget",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<OneTarget> saveOneTarget(@RequestBody OneTarget oneTarget);

    /**
     * 统一事务,修改地块状态,插入出价记录
     * @param oneTarget
     * @param offerType
     * @param logId
     * @param offerId
     * @return
     */
    @RequestMapping(value = "/oneTarget/saveOneTargetAndUpdateResource",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<OneTarget> saveOneTargetAndUpdateResource(@RequestBody OneTarget oneTarget,@RequestParam(value="offerType") String offerType,@RequestParam(value="logId",required = false) String logId,@RequestParam(value="offerId",required = false) String offerId);
}
