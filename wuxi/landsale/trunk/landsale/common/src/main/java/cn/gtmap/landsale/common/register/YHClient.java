package cn.gtmap.landsale.common.register;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.YHAgree;
import cn.gtmap.landsale.common.model.YHResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 摇号相关
 * @author lq
 * @version v1.0, 2017/12/27
 */
@FeignClient(name = "core-server")
public interface YHClient {

    /**
     * 获取参与摇号信息
     * @param agreeId
     * @return
     */
    @RequestMapping(value = "/yh/getYHAgree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    YHAgree getYHAgree(@RequestParam(value = "agreeId") String agreeId);

    /**
     * 根据地块id获取参与摇号信息列表
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/yh/getYHAgreeByResourceId",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<YHAgree> getYHAgreeByResourceId(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 根据地块id和用户id获取参与摇号信息
     * @param resourceId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/yh/getYHAgreeByResourceIdAndUserId",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    YHAgree getYHAgreeByResourceIdAndUserId(@RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "userId") String userId);

    /**
     * 保存或更新参与摇号信息
     * @param yhAgree
     * @return
     */
    @RequestMapping(value = "/yh/saveOrUpdateYHAgree",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<YHAgree> saveOrUpdateYHAgree(@RequestBody YHAgree yhAgree);

    /**
     * 获取摇号结果
     * @param resultId
     * @return
     */
    @RequestMapping(value = "/yh/getYHResult",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    YHResult getYHResult(@RequestParam(value = "resultId") String resultId);

    /**
     * 根据参与摇号信息获取摇号结果
     * @param agreeId
     * @return
     */
    @RequestMapping(value = "/yh/getYHResultByYHAgreeId",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    YHResult getYHResultByYHAgreeId(@RequestParam(value = "agreeId") String agreeId);

    /**
     * 仅保存摇号信息，发布时再修改地块信息
     * @param resourceId
     * @param successPrice
     * @param offerUserId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/yh/saveOrUpdateYHResult",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<YHResult> saveOrUpdateYHResult(@RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "successPrice") String successPrice, @RequestParam(value = "offerUserId") String offerUserId, @RequestParam(value = "userId") String userId);

    /**
     * 发布摇号信息，同时修改地块状态
     * @param yhResultId
     * @return
     */
    @RequestMapping(value = "/yh/postYHResult",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<YHResult> postYHResult(@RequestParam(value = "yhResultId") String yhResultId);

    /**
     * 通过resourceId获取摇号结果
     * @param resourceId
     * @return
     */
    @RequestMapping(value= "/yh/getYHResultByResourceId",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    YHResult getYHResultByResourceId(@RequestParam(value = "resourceId") String resourceId);

}
