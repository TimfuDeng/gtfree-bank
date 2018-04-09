package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneTarget;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OneTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 一次报价标的
 * @author lq on 2017/11/17.
 */
@RestController
@RequestMapping(value = "/oneTarget")
public class OneTargetController {

    @Autowired
    OneTargetService oneTargetService;

    @RequestMapping("/getOneTarget")
    public OneTarget getOneTarget(@RequestParam(value = "id") String id) {
        return oneTargetService.getOneTarget(id);
    }

    @RequestMapping("/getOneTargetByTransResource")
    public OneTarget getOneTargetByTransResource(@RequestParam(value = "resourceId") String resourceId) {
        return oneTargetService.getOneTargetByTransResource(resourceId);
    }

    @RequestMapping("/saveOneTarget")
    public ResponseMessage<OneTarget> saveOneTarget(@RequestBody OneTarget oneTarget) {
        return oneTargetService.saveOneTarget(oneTarget);
    }

    @RequestMapping("/saveOneTargetAndUpdateResource")
    public ResponseMessage<OneTarget> saveOneTargetAndUpdateResource(@RequestBody OneTarget oneTarget,@RequestParam(value="offerType") String offerType,@RequestParam(value="logId",required = false) String logId,@RequestParam(value="offerId",required = false) String offerId) {
        return oneTargetService.saveOneTargetAndUpdateResource(oneTarget,offerType,logId,offerId);
    }

    @RequestMapping("/findOneTargetPage")
    public Page<OneTarget> findOneTargetPage(@RequestParam(value = "title", required = false) String title, @RequestBody Pageable page) {
        return oneTargetService.findOneTargetPage(title, page);
    }

    @RequestMapping("/findOneTargetPageByIsStop")
    public Page<OneTarget> findOneTargetPageByIsStop(@RequestParam(value = "title", required = false) String title, @RequestBody Pageable page, @RequestParam(value = "isStop") Integer isStop) {
        return oneTargetService.findOneTargetPageByIsStop(title, page, isStop);
    }

    @RequestMapping("/findMyOneApplyList")
    public Page<OneTarget> findMyOneApplyList(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "transUserId") String transUserId, @RequestBody Pageable page, @RequestParam(value = "isStop") Integer isStop) {
        return oneTargetService.findMyOneApplyList(title, transUserId, page, isStop);
    }
}
