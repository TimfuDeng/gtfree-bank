package cn.gtmap.landsale.common.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUserUnion;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 联合竞买服务
 * @author zsj
 * @version v1.0, 2017/10/28
 */
@FeignClient(name = "core-server")
public interface TransUserUnionClient {

    /**
     * 根据Id获取 联合竞买
     * @param unionId 联合竞买Id
     * @return 联合竞买对象
     */
    @RequestMapping(value = "/union/getTransUserUnion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUserUnion getTransUserUnion(@RequestParam(value = "unionId") String unionId);

    /**
     * 获取 联合竞买列表
     * @param applyId 地块申请Id
     * @return 出让公告对象
     */
    @RequestMapping(value = "/union/findTransUserUnion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransUserUnion> findTransUserUnion(@RequestParam(value = "applyId") String applyId);

    /**
     * 被联合竞买列表
     * @param userName
     * @param request
     * @return
     */
    @RequestMapping(value = "/union/findTransUserUnionByUserName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransUserUnion> findTransUserUnionByUserName(@RequestParam(value = "userName", required = false) String userName, @RequestBody Pageable request);

    /**
     * 保存联合竞买 信息
     * @param transUserUnion
     * @return
     */
    @RequestMapping(value = "/union/saveTransUserUnion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransUserUnion> saveTransUserUnion(@RequestBody TransUserUnion transUserUnion);

    /**
     * 删除 联合竞买 对象
     * @param unionId 联合竞买Id
     * @return
     */
    @RequestMapping(value = "/union/deleteTransUserUnion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransUserUnion(@RequestParam("unionId") String unionId);

    /**
     * 获取当前用户是被联合人的地块的联合竞买记录
     * @param userName 被联合人姓名，是姓名不是用户名
     * @param resourceId 地块Id
     * @return
     */
    @RequestMapping(value = "/union/getResourceTransUserUnionByUserName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransUserUnion> getResourceTransUserUnionByUserName(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "resourceId", required = false) String resourceId);

}
