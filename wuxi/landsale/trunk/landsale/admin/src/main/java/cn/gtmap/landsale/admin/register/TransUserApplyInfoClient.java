package cn.gtmap.landsale.admin.register;

import cn.gtmap.landsale.common.model.TransUserApplyInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 资格审核 服務
 * @author cxm
 * @version v1.0, 2017/11/08
 */
@FeignClient(name = "core-server")
public interface TransUserApplyInfoClient {

    /**
     * 根据Id获取人员申请信息
     *
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/userApplyInfo/getTransUserApplyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUserApplyInfo getTransUserApplyInfo(@RequestParam(value = "infoId") String infoId);


}
