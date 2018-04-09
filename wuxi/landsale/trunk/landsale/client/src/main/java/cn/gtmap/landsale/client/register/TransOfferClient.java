package cn.gtmap.landsale.client.register;

import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 报价服务
 * Created by liushaoshuai on 2017/7/6.
 */
@FeignClient(name = "core-server")
public interface TransOfferClient {

    /**
     * 报价
     * @param id
     * @param offer
     * @param type
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/offer/offer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String offer(@RequestParam("id") String id,@RequestParam("offer") String offer,@RequestParam("type") int type,@RequestParam("userId") String userId) throws Exception;

    /**
     * 获取报价列表
     * @param resourceId
     * @param timeValue
     * @return
     */
    @RequestMapping(value = "/offer/get/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceOffer> getOfferList(@RequestParam("resourceId") String resourceId,@RequestParam("timeValue") long timeValue);

}
