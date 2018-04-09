package cn.gtmap.landsale.client.register;


import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 地块（主）服务
 * Created by liushaoshuai on 2017/7/6.
 */

@FeignClient(name = "core-server")
public interface TransResourceClientBak {

    @RequestMapping(value = "/resource/view", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ModelMap view(@RequestParam("id")String id,@RequestParam("userId")String userId) throws Exception;


    @RequestMapping(value = "/resource/index", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResource> index(@RequestBody Map map)  throws Exception;

    @RequestMapping(value = "/resource/content", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ModelMap detail(String resourceId) throws Exception;

    @RequestMapping(value = "/resource/offer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceOffer acceptResourceOffer(@RequestParam("userId")String userId,@RequestParam("id") String id,@RequestParam("offer") String offer,@RequestParam("type") int type) throws Exception;

    @RequestMapping(value = "/resource/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResource getResource(@RequestParam("resourceId") String resourceId);
}
