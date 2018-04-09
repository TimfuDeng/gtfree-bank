package cn.gtmap.landsale.bank.register;

import cn.gtmap.landsale.common.model.TransResourceApply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 竞买人申购服务注册
 * @author zsj
 * @version v1.0, 2017/8/29
 */
@FeignClient(name = "core-server")
public interface TransResourcesApplyClient {

    /**
     * 获取报名记录
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/console/resource-apply/getTransResourceApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceApply getTransResourceApply(@RequestParam(value = "applyId",required = true) String applyId);

    /**
     * 保存
     * @param transResourceApply
     * @return
     */
    @RequestMapping(value = "/console/resource-apply/saveTransResourceApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceApply saveTransResourceApply(@RequestBody TransResourceApply transResourceApply);

}
