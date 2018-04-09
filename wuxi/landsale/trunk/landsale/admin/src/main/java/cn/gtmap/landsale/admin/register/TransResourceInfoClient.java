package cn.gtmap.landsale.admin.register;


import cn.gtmap.landsale.common.model.TransResourceInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 地块扩展信息资源 服務
 * @author zsj
 * @version v1.0, 2017/10/31
 */
@FeignClient(name = "core-server")
public interface TransResourceInfoClient {

    /**
     * 通过地块Id 获取 TransResourceInfo
     * @param resourceId
     * @return TransResourceInfo
     */
    @RequestMapping(value = "/resourceInfo/getTransResourceInfoByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceInfo getTransResourceInfoByResourceId(@RequestParam(value = "resourceId") String resourceId);

}
