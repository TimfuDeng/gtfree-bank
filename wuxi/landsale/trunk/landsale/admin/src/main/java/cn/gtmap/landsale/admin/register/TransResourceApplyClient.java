package cn.gtmap.landsale.admin.register;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceApply;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资格审核
 * @author cxm
 * @version v1.0, 2017/11/08
 */
@FeignClient(name = "core-server")
public interface TransResourceApplyClient {

    /**
     * 获取申请人信息
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "console/resource-apply/getTransResourceApplyByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceApply> getTransResourceApplyByResourceId(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 获取
     * @param applyId
     * @return
     */
    @RequestMapping(value = "console/resource-apply/getTransResourceApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceApply getTransResourceApply(@RequestParam(value = "applyId",required = true) String applyId);

    /**
     * 根据用户id获取
     * @param userId
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "console/resource-apply/getTransResourceApplyByUserId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceApply getTransResourceApplyByUserId(@RequestParam(value = "userId",required = true) String userId,@RequestParam(value = "resourceId",required = true) String resourceId);

    /**
     * 保存
     * @param transResourceApply
     * @return
     */
    @RequestMapping(value = "console/resource-apply/saveTransResourceApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResourceApply> saveTransResourceApply(@RequestBody TransResourceApply transResourceApply);

    /**
     * 报名查询
     * @param resourceId
     * @param applyStep
     * @return
     */
    @RequestMapping(value = "console/resource-apply/getTransResourceApplyStep", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceApply> getTransResourceApplyStep(@RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "applyStep") int applyStep);

    /**
     * 获得参与限时竞买的人
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "console/resource-apply/getEnterLimitTransResourceApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceApply> getEnterLimitTransResourceApply(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 删除报名 及相关信息
     * @param applyId
     * @return
     */
    @RequestMapping(value = "console/resource-apply/deleteApply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteApply(@RequestParam(value = "applyId") String applyId);

}
