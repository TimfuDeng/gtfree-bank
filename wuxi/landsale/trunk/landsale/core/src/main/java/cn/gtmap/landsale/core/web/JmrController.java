package cn.gtmap.landsale.core.web;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.core.service.TransUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用户管理
 * @author cxm
 * @version v1.0, 2017/9/29
 */
@RestController
@RequestMapping("/jmr")
public class JmrController {

    @Autowired
    TransUserService transUserService;

    @RequestMapping("/addJmr")
    public ResponseMessage<TransUser> addJmr(@RequestBody TransUser transUser) {
        transUser.setUserId(null);
        return transUserService.addJmr(transUser);
    }

    @RequestMapping("/editJmr")
    public ResponseMessage<TransUser> editJmr(@RequestBody TransUser transUser) {
        return transUserService.editJmr(transUser);
    }

    @RequestMapping("/deleteJmr")
    public ResponseMessage deleteJmr(@RequestParam(value = "userIds") String userIds) {
        return transUserService.deleteJmr(userIds);
    }

}
