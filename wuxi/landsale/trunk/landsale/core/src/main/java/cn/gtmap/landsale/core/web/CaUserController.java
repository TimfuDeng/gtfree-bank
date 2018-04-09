package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCaRegister;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.core.service.TransCaRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author M150237 on 2017-10-11.
 */
@RestController
@RequestMapping(value = "/register")
public class CaUserController {
    @Autowired
    TransCaRegisterService transCaRegisterService;

    /**
     * 获取CA用户分页服务
     * @param contactUser 联系人
     * @param request
     * @return
     */
    @RequestMapping("/findRegisterUser")
    public Page<TransCaRegister> findRegisterUser(@RequestParam(value = "contactUser", required = false) String contactUser, @PageDefault(value = 10) Pageable request) {
        return transCaRegisterService.findRegisterUser(contactUser,request);
    }


    /**
     * 保存
     * @param transCaRegister
     * @return
     */
    @RequestMapping("/saveTransCaRegister")
    public ResponseMessage<TransCaRegister> saveTransCaRegister(@RequestBody TransCaRegister transCaRegister) {
        return transCaRegisterService.saveTransCaRegister(transCaRegister);
    }

    /**
     * 根据注册id获取CA用户
     * @param registerId 注册id
     * @return
     */
    @RequestMapping("/getTransCaRegisterByRegisterId")
    public List<TransCaRegister> getTransCaRegisterByRegisterId(@RequestParam("registerId") String registerId) {
        return transCaRegisterService.getTransCaRegisterByRegisterId(registerId);
    }

    /**
     * 根据用户ID获取CA用户
     * @param userId 用户ID
     * @return
     */
    @RequestMapping("/getTransCaRegisterByUserId")
    public List<TransCaRegister> getTransCaRegisterByUserId(@RequestParam("userId") String userId) {
        return transCaRegisterService.getTransCaRegisterByUserId(userId);
    }

    /**
     * 根据用户ID和行政区代码获取CA用户
     * @param userId 用户ID
     * @param regionCode  行政区代码
     * @return
     */
    @RequestMapping("/getCaRegisterByUserIdAndCode")
    public List<TransCaRegister> getCaRegisterByUserIdAndCode(@RequestParam("userId") String userId, @RequestParam(value = "regionCode")  String regionCode) {
        return transCaRegisterService.getCaRegisterByUserIdAndCode(userId,regionCode);
    }

}
