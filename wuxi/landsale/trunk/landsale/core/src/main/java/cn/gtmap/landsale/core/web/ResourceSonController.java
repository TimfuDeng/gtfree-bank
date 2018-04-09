package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceSon;
import cn.gtmap.landsale.core.service.TransResourceSonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地块多用途信息服务
 * @author zsj
 * @version v1.0, 2017/10/31
 */
@RestController
@RequestMapping(value = "/resourceSon")
public class ResourceSonController {

    @Autowired
    TransResourceSonService resourceSonService;

    /**
     * 查询列表
     * @param resourceId 地块Id
     * @return
     */
    @RequestMapping("/getTransResourceSonList")
    public List<TransResourceSon> getTransResourceSonList(@RequestParam("resourceId") String resourceId) {
        return resourceSonService.getTransResourceSonList(resourceId);
    }

    /**
     * 根据地块多用途Id 获取TransResourceSon
     * @param resourceSonId
     * @return
     */
    @RequestMapping("/getTransResourceSon")
    public TransResourceSon getTransResourceSon(@RequestParam("resourceSonId") String resourceSonId) {
        return resourceSonService.getTransResourceSon(resourceSonId);
    }

    /**
     * 保存地块多用途
     * @param transResourceSon
     * @return
     */
    @RequestMapping("/saveTransResourceSon")
    public ResponseMessage<TransResourceSon> saveTransResourceSon(@RequestBody TransResourceSon transResourceSon) {
        return resourceSonService.saveTransResourceSon(transResourceSon);
    }

    /**
     * 修改地块多用途
     * @param transResourceSon
     * @return
     */
    @RequestMapping("/updateTransResourceSon")
    public ResponseMessage<TransResourceSon> updateTransResourceSon(@RequestBody TransResourceSon transResourceSon) {
        return resourceSonService.updateTransResourceSon(transResourceSon);
    }

    /**
     * 根据地块多用途Id 删除
     * @param resourceSonId
     * @return
     */
    @RequestMapping("/deleteTransResourceSon")
    public ResponseMessage deleteTransResourceSon(@RequestParam("resourceSonId") String resourceSonId) {
        return resourceSonService.deleteTransResourceSon(resourceSonId);
    }

    /**
     * 根据地块Id 删除
     * @param resourceId
     * @return
     */
    @RequestMapping("/deleteTransResourceSonByResourceId")
    public ResponseMessage deleteTransResourceSonByResourceId(@RequestParam("resourceId") String resourceId) {
        return resourceSonService.deleteTransResourceSonByResourceId(resourceId);
    }

    /**
     * 根据宗地号和地块id查询
     * @param zdCode
     * @param resourceId
     * @return
     */
    @RequestMapping("/getTransResourceSons")
    public List<TransResourceSon> getTransResourceSons(@RequestParam("zdCode") String zdCode, @RequestParam("resourceId") String resourceId) {
        return resourceSonService.getTransResourceSons(zdCode, resourceId);
    }


}
