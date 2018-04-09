package cn.gtmap.landsale.core.register;

import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * redis服务
 * Created by liushaoshuai on 2017/7/13.
 */
@FeignClient(name = "redis-server")
public interface TransRedisClient {

    /**
     * 报价
     * @param offer
     * @throws Exception
     */
    @RequestMapping(value = "/redis/sendOffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void sendOffer(@RequestBody TransResourceOffer offer)throws Exception;


}
