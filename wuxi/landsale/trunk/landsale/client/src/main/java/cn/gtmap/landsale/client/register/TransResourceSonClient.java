package cn.gtmap.landsale.client.register;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceSon;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 地块多用途信息 服務
 * @author zsj
 * @version v1.0, 2017/10/31
 */
@FeignClient(name = "core-server")
public interface TransResourceSonClient {

    /**
     * 查询列表
     * @param resourceId 地块Id
     * @return
     */
    @RequestMapping(value = "/resourceSon/getTransResourceSonList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceSon> getTransResourceSonList(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 根据地块多用途Id 获取TransResourceSon
     * @param resourceSonId
     * @return
     */
    @RequestMapping(value = "/resourceSon/getTransResourceSon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceSon getTransResourceSon(@RequestParam("resourceSonId") String resourceSonId);

    /**
     * 保存地块多用途
     * @param transResourceSon
     * @return
     */
    @RequestMapping(value = "/resourceSon/saveTransResourceSon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResourceSon> saveTransResourceSon(@RequestBody TransResourceSon transResourceSon);

    /**
     * 修改地块多用途
     * @param transResourceSon
     * @return
     */
    @RequestMapping(value = "/resourceSon/updateTransResourceSon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResourceSon> updateTransResourceSon(@RequestBody TransResourceSon transResourceSon);

    /**
     * 根据地块多用途Id 删除
     * @param resourceSonId
     * @return
     */
    @RequestMapping(value = "/resourceSon/deleteTransResourceSon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransResourceSon(@RequestParam(value = "resourceSonId") String resourceSonId);

    /**
     * 根据地块Id 删除
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/resourceSon/deleteTransResourceSonByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransResourceSonByResourceId(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 根据宗地号和地块id查询
     * @param zdCode
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/resourceSon/getTransResourceSons", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceSon> getTransResourceSons(@RequestParam(value = "zdCode") String zdCode, @RequestParam(value = "resourceId") String resourceId);

}
