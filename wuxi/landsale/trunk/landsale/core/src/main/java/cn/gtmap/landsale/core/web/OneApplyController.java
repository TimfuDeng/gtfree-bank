package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneApply;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OneApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报价流程服务
 * @author zsj
 * @version v1.0, 2017/12/8
 */
@RestController
@RequestMapping(value = "/oneApply")
public class OneApplyController {

    @Autowired
    OneApplyService oneApplyService;

    /**
     * 保存一次报价流程
     * @param oneApply
     * @return
     */
    @RequestMapping(value = "/saveOneApply")
    ResponseMessage<OneApply> saveOneApply(@RequestBody OneApply oneApply) {
        return oneApplyService.saveOneApply(oneApply);
    }

    /**
     * 根据报价设置信息Id 用户编号查找 报价流程
     * @param targetId
     * @param transUserId
     * @return
     */
    @RequestMapping(value = "/getOnApply")
    OneApply getOnApply(@RequestParam(value = "targetId") String targetId, @RequestParam(value = "transUserId") String transUserId) {
        return oneApplyService.getOnApply(targetId, transUserId);
    }

    /**
     * 获取报价流程列表
     * @param transUserId
     * @param page
     * @return
     */
    @RequestMapping(value = "/findOneApply")
    Page<OneApply> findOneApply(@RequestParam(value = "transUserId") String transUserId, @RequestBody Pageable page) {
        return oneApplyService.findOneApply(transUserId, page);
    }

}
