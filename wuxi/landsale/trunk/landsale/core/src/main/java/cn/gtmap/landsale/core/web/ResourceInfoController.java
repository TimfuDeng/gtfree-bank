package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.TransResourceInfo;
import cn.gtmap.landsale.core.service.TransResourceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地块扩展信息服务
 * @author zsj
 * @version v1.0, 2017/10/31
 */
@RestController
@RequestMapping(value = "/resourceInfo")
public class ResourceInfoController {

    @Autowired
    TransResourceInfoService transResourceInfoService;

    /**
     * 通过地块Id 获取 TransResourceInfo
     * @param resourceId
     * @return TransResourceInfo
     */
    @RequestMapping("/getTransResourceInfoByResourceId")
    public TransResourceInfo getTransResourceInfoByResourceId(@RequestParam("resourceId") String resourceId) {
        return transResourceInfoService.getTransResourceInfoByResourceId(resourceId);

    }
}
