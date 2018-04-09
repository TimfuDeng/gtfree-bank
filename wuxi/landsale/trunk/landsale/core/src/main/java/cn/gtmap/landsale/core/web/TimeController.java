package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.core.service.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liushaoshuai on 2017/7/10.
 */
@Controller
public class TimeController {
    @Autowired
    ServiceUtils serviceUtils;

    private static Logger log = LoggerFactory.getLogger(TimeController.class);


    @RequestMapping("/time")
    @ResponseBody
    public String getServiceTime(){
        return serviceUtils.getServerTime();
    }
}
