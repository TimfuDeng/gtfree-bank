package cn.gtmap.landsale.common.register;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBuyQualified;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 资格审核
 * @author cxm
 * @version v1.0, 2017/11/13
 */
@FeignClient(name = "core-server")
public interface TransBuyQualifiedClient {

    /**
     * 查找审核信息
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/qualified/getListTransBuyQualifiedByApplyId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransBuyQualified> getListTransBuyQualifiedByApplyId(@RequestParam(value = "applyId") String applyId);

    /**
     * 保存
     * @param transBuyQualified
     * @return
     */
    @RequestMapping(value = "/qualified/saveTransBuyQualified", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransBuyQualified> saveTransBuyQualified(@RequestBody TransBuyQualified transBuyQualified);

    /**
     * 根据ApplyId获取 当前申购资格审核信息
     * @param applyId
     * @return
     */
    @RequestMapping(value = "/qualified/getTransBuyQualifiedForCurrent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransBuyQualified getTransBuyQualifiedForCurrent(@RequestParam(value = "applyId") String applyId);


}
