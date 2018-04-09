package cn.gtmap.landsale.client.register;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 时间服务
 * Created by liushaoshuai on 2017/7/10.
 */
@FeignClient(name = "core-server")
public interface TransTimeClient {

    /**
     * 获取时间
     * @return
     */
    @RequestMapping(value = "/time", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String getServiceTime();
}
