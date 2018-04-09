package cn.gtmap.landsale.client.register;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 竞买人服务
 * @author cxm
 * @version v1.0, 2017/9/29
 */
@FeignClient(name = "core-server")
public interface TransJmrClient {

    /**
     * 保存
     * @param transUser 用户对象
     * @return
     */
    @RequestMapping(value = "/jmr/addJmr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransUser> addJmr(@RequestBody TransUser transUser);

    /**
     * b修改
     * @param transUser 用户对象
     * @return
     */
    @RequestMapping(value = "/jmr/editJmr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransUser> editJmr(@RequestBody TransUser transUser);

    /**
     * 删除用户
     * @param userIds 用户Ids
     * @return
     */
    @RequestMapping(value = "/jmr/deleteJmr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteJmr(@RequestParam(value = "userIds") String userIds);

}
