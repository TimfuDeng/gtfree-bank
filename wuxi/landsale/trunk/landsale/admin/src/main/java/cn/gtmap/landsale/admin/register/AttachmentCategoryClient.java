package cn.gtmap.landsale.admin.register;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * 附件类型 服务
 * @author zsj
 * @version v1.0, 2017/11/1
 */
@FeignClient(name = "core-server")
public interface AttachmentCategoryClient {

    /**
     * 地块报名时上传服务分类
     * @return
     */
    @RequestMapping(value = "/attachment/getTransResourceApplyAttachmentCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Map getTransResourceApplyAttachmentCategory();

    /**
     * 地块报名时上传服务分类
     * @return
     */
    @RequestMapping(value = "/attachment/getTransResourceAttachmentCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Map getTransResourceAttachmentCategory();

}
