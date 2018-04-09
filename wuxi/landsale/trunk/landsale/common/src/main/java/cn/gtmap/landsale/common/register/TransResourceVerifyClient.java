package cn.gtmap.landsale.common.register;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceVerify;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 成交审核服务
 * @author zsj
 * @version v1.0, 2017/12/23
 */
@FeignClient(name = "core-server")
public interface TransResourceVerifyClient {

    /**
     * 根据Id获取 成家审核
     * @param verifyId 成家审核Id
     * @return 成家审核对象
     */
    @RequestMapping(value = "/verify/getTransVerifyById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceVerify getTransVerifyById(@RequestParam("verifyId") String verifyId);

    /**
     * 保存 成家审核对象
     * @param transResourceVerify 成家审核对象
     * @return  新的成家审核对象
     */
    @RequestMapping(value = "/verify/saveTransVerify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResourceVerify> saveTransVerify(@RequestBody TransResourceVerify transResourceVerify);

    /**
     * 根据 ResourceId 获取地块成交审核信息
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/verify/getTransVerifyListByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceVerify> getTransVerifyListByResourceId(@RequestParam("resourceId") String resourceId);

    /**
     * 根据 ResourceId 获取地块 最新成交审核信息
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/verify/getTransVerifyLastByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceVerify getTransVerifyLastByResourceId(@RequestParam("resourceId") String resourceId);

}
