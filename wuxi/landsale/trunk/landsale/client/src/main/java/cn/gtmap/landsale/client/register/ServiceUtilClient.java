package cn.gtmap.landsale.client.register;


import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统时间服务
 * @author zsj
 * @version v1.0, 2017/12/8
 */
@FeignClient(name = "core-server")
public interface ServiceUtilClient {

    /**
     * 获取系统时间
     * @return
     */
    @RequestMapping(value = "/getServerTime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    String getServerTime();

}
