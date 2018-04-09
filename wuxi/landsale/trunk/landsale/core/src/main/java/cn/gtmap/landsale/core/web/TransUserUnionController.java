package cn.gtmap.landsale.core.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUserUnion;
import cn.gtmap.landsale.core.service.TransUserUnionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 联合竞买服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@RestController
@RequestMapping("/union")
public class TransUserUnionController {

    @Autowired
    TransUserUnionService transUserUnionService;

    /**
     * 根据Id获取 联合竞买
     * @param unionId 联合竞买Id
     * @return 联合竞买对象
     */
    @RequestMapping("/getTransUserUnion")
    public TransUserUnion getTransUserUnion(@RequestParam("unionId") String unionId) {
        return transUserUnionService.getTransUserUnion(unionId);
    }

    /**
     * 获取 联合竞买列表
     * @param applyId 地块申请Id
     * @return 出让公告对象
     */
    @RequestMapping("/findTransUserUnion")
    public List<TransUserUnion> findTransUserUnion(@RequestParam("applyId") String applyId) {
        return transUserUnionService.findTransUserUnion(applyId);
    }

    /**
     * 被联合竞买列表
     * @param userName
     * @param request
     * @return
     */
    @RequestMapping("/findTransUserUnionByUserName")
    public Page<TransUserUnion> findTransUserUnionByUserName(@RequestParam(value = "userName", required = false) String userName, @RequestBody Pageable request) {
        return transUserUnionService.findTransUserUnionByUserName(userName, request);
    }

    /**
     * 保存联合竞买 信息
     * @param transUserUnion
     * @return
     */
    @RequestMapping("/saveTransUserUnion")
    public ResponseMessage<TransUserUnion> saveTransUserUnion(@RequestBody TransUserUnion transUserUnion) {
        return transUserUnionService.saveTransUserUnion(transUserUnion);
    }

    /**
     * 删除 联合竞买 对象
     * @param unionId 联合竞买Id
     * @return
     */
    @RequestMapping("/deleteTransUserUnion")
    public ResponseMessage deleteTransUserUnion(@RequestParam("unionId") String unionId) {
        return transUserUnionService.deleteTransUserUnion(unionId);
    }

    /**
     * 获取当前用户是被联合人的地块的联合竞买记录 (已被联合人确认的)
     * @param userName 被联合人姓名，是姓名不是用户名
     * @param resourceId 地块Id
     * @return
     */
    @RequestMapping("/getResourceTransUserUnionByUserName")
    public List<TransUserUnion> getResourceTransUserUnionByUserName(@RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "resourceId", required = false) String resourceId) {
        return transUserUnionService.getResourceTransUserUnionByUserName(userName, resourceId);
    }

}
