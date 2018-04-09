package cn.gtmap.landsale.bank.register;

import cn.gtmap.landsale.common.model.TransResource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 竞买人申购服务注册
 * @author zsj
 * @version v1.0, 2017/8/29
 */
@FeignClient(name = "core-server")
public interface TransResourcesClient {

    /**
     * 获取地块信息
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/resource/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResource getTransResource(@RequestParam(value = "resourceId") String resourceId);

}
