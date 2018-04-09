package cn.gtmap.landsale.common.register;


import cn.gtmap.landsale.common.model.OneTarget;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 一次报价标的
 * @author lq
 * @version v1.0, 2017/11/17
 */
@FeignClient(name = "core-server")
public interface OneTargetClient {

    /**
     * 获取一次报价标的
     * @param id
     * @return
     */
    @RequestMapping(value = "/oneTarget/getOneTarget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneTarget getOneTarget(@RequestParam(value = "id") String id);

    /**
     * 根据地块id获取一次报价标的
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/oneTarget/getOneTargetByTransResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneTarget getOneTargetByTransResource(@RequestParam(value = "resourceId") String resourceId);

}
