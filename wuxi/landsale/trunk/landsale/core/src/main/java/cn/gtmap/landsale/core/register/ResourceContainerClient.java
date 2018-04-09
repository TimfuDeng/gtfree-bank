package cn.gtmap.landsale.core.register;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * redis服务
 * Created by liushaoshuai on 2017/7/13.
 */
@FeignClient(name = "admin-server")
public interface ResourceContainerClient {

    /**
     * 移除
     * @param resourceId
     */
    @RequestMapping(value = "/resourceContainer/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void remove(@RequestParam("resourceId") String resourceId);


}
