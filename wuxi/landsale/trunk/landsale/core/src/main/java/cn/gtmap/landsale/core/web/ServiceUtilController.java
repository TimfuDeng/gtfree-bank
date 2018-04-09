package cn.gtmap.landsale.core.web;


import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 获取系统时间服务
 * @author zsj
 * @version v1.0, 2017/12/8
 */
@RestController
public class ServiceUtilController {

    @Autowired
    ServiceUtils serviceUtils;

    /**
     * 获取系统时间
     * @return
     */
    @RequestMapping("/getServerTime")
    public String getServerTime() {
        return serviceUtils.getServerTime();
    }

}
