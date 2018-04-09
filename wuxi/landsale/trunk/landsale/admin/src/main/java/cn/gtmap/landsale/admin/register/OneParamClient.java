package cn.gtmap.landsale.admin.register;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneParam;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "core-server")
public interface OneParamClient {

    /**
     * 保存一次报价参数
     * @param oneParam
     * @return
     */
    @RequestMapping(value = "/oneParam/saveOrUpdateOneParam", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<OneParam> saveOrUpdateOneParam(@RequestBody OneParam oneParam);

    /**
     * 获取
     * @param id
     * @return
     */
    @RequestMapping(value = "/oneParam/getOneParam", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneParam getOneParam(@RequestParam(value = "id") String id);

    /**
     * 列表获取
     * @param request
     * @return
     */
    @RequestMapping(value = "/oneParam/findOneParam", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<OneParam> findOneParam(@RequestBody Pageable request);

    /**
     * 根据行政区代码获取
     * @param regionCode
     * @return
     */
    @RequestMapping(value = "/oneParam/getOneParamByRegionCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneParam getOneParamByRegionCode(@RequestParam(value = "regionCode") String regionCode);

}
