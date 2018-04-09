package cn.gtmap.landsale.client.register;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneApply;
import cn.gtmap.landsale.common.model.OneParam;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 报价流程服务
 * @author zsj
 * @version v1.0, 2017/12/8
 */
@FeignClient(name = "core-server")
public interface OneApplyClient {

    /**
     * 保存一次报价流程
     * @param oneApply
     * @return
     */
    @RequestMapping(value = "/oneApply/saveOneApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<OneApply> saveOneApply(@RequestBody OneApply oneApply);

    /**
     * 根据报价设置信息Id 用户编号查找 报价流程
     * @param targetId
     * @param transUserId
     * @return
     */
    @RequestMapping(value = "/oneApply/getOnApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneApply getOnApply(@RequestParam(value = "targetId") String targetId, @RequestParam(value = "transUserId") String transUserId);

    /**
     * 获取报价流程列表
     * @param transUserId
     * @param page
     * @return
     */
    @RequestMapping(value = "/oneApply/findOneApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<OneApply> findOneApply(@RequestParam(value = "transUserId") String transUserId, @RequestBody Pageable page);

}
