package cn.gtmap.landsale.client.register;

import cn.gtmap.landsale.common.model.TransUserApplyInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 资格审核 服務
 * @author cxm
 * @version v1.0, 2017/11/08
 */
@FeignClient(name = "core-server")
public interface TransUserApplyInfoClient {

    /**
     * 根据Id获取人员申请信息
     * @param infoId
     * @return
     */
    @RequestMapping(value = "/userApplyInfo/getTransUserApplyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUserApplyInfo getTransUserApplyInfo(@RequestParam(value = "infoId") String infoId);

    /**
     * 根据用户Id获取人员申请信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userApplyInfo/getTransUserApplyInfoByUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransUserApplyInfo> getTransUserApplyInfoByUser(@RequestParam(value = "userId") String userId);

    /**
     * 保存人员申请信息
     * @param transUserApplyInfo
     * @return
     */
    @RequestMapping(value = "/userApplyInfo/saveTransUserApplyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUserApplyInfo saveTransUserApplyInfo(@RequestBody TransUserApplyInfo transUserApplyInfo);

    /**
     * 删除人员申请信息
     * @param infoIds
     * @return
     */
    @RequestMapping(value = "/userApplyInfo/deleteTransUserApplyInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUserApplyInfo deleteTransUserApplyInfo(@RequestParam(value = "infoIds") String infoIds);

    /**
     * 根据人员Id删除其申请信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userApplyInfo/deleteTransUserApplyInfoByUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUserApplyInfo deleteTransUserApplyInfoByUser(@RequestParam(value = "userId") String userId);

}
