package cn.gtmap.landsale.client.register;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneTarget;
import cn.gtmap.landsale.common.model.ResponseMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 一次报价标的
 * Created by lq on 2017/11/17.
 */
@FeignClient(name = "core-server")
public interface OneTargetClient {

    /**
     * 获取 OneTarget
     * @param id
     * @return
     */
    @RequestMapping(value = "/oneTarget/getOneTarget",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneTarget getOneTarget(@RequestParam(value = "id") String id);

    /**
     * 通过 resourceId 获取 OneTarget
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/oneTarget/getOneTargetByTransResource",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    OneTarget getOneTargetByTransResource(@RequestParam(value = "resourceId") String resourceId);

    /**
     * 保存
     * @param oneTarget
     * @return
     */
    @RequestMapping(value = "/oneTarget/saveOneTarget",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<OneTarget> saveOneTarget(@RequestBody OneTarget oneTarget);

    /**
     * 分页获取oneTarget列表
     * @param title 标题
     * @param page
     * @return
     */
    @RequestMapping(value = "/oneTarget/findOneTargetPage",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<OneTarget> findOneTargetPage(@RequestParam(value = "title", required = false) String title, @RequestBody Pageable page);

    /**
     * 分页获取oneTarget列表
     * @param title
     * @param page
     * @param isStop
     * @return
     */
    @RequestMapping(value = "/oneTarget/findOneTargetPageByIsStop",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<OneTarget> findOneTargetPageByIsStop(@RequestParam(value = "title", required = false) String title, @RequestBody Pageable page, @RequestParam(value = "isStop") Integer isStop);

    /**
     * 根据用户id获取一次报价报名记录
     * @param title
     * @param transUserId
     * @param page
     * @param isStop
     * @return
     */
    @RequestMapping(value = "/oneTarget/findMyOneApplyList",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<OneTarget> findMyOneApplyList(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "transUserId") String transUserId, @RequestBody Pageable page, @RequestParam(value = "isStop") Integer isStop);
}
