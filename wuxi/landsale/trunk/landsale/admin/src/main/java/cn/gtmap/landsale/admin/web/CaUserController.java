package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransCaRegisterClient;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.TransCaRegisterService;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;
/**
 * ca用户管理
 * @author cxm
 * @version v1.0, 2017/9/29
 */
@Controller
@RequestMapping("/ca-user")
public class CaUserController {

    @Autowired
    TransCaRegisterClient transCaRegisterClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransCaRegisterService transCaRegisterService;


    @RequestMapping("index")
    public String index(String contactUser, @PageDefault(value = 10) Pageable page, Model model) {
        //分页查询注册的CA用户
        Page<TransCaRegister> transCaRegisterList=transCaRegisterClient.findRegisterUser(contactUser,page);
        model.addAttribute("transCaRegisterList", transCaRegisterList);
        model.addAttribute("contactUser", contactUser);
        return "ca-user/ca-user-list";
    }


    @RequestMapping("/add")
    public String add() {
        return "ca-user/ca-user-add";
    }


    @RequestMapping("/addCaRegister")
    @ResponseBody
    public ResponseMessage<TransCaRegister> addCaRegister(TransCaRegister transCaRegister, TransUser transUser, String regionCode) {
        return  transCaRegisterService.saveTransCaRegister(transCaRegister, transUser,regionCode);
    }


    @RequestMapping("/edit")
    public String edit(Model model, String registerId) {
        //获取注册用户
        List<TransCaRegister> transCaRegisters = transCaRegisterClient.getTransCaRegisterByRegisterId(registerId);
        TransCaRegister transCaRegister = null;
        if (transCaRegisters != null && transCaRegisters.size() > 0){
            transCaRegister=transCaRegisters.get(0);
        }
        //获取注册用户的用户信息
        TransUser transUser = transUserClient.getTransUserById(transCaRegister.getUserId());
        //获取数字证书信息
        TransUser chackUser = transUserClient.getTransUserByCAThumbprint(transUser.getCaThumbprint());
        // 获取行政区
        TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(transCaRegister.getRegionCode());
        model.addAttribute("transCaRegister", transCaRegister);
        model.addAttribute("transRegion", transRegion);
        model.addAttribute("transUser", transUser);
        model.addAttribute("chackUser", chackUser);
        return "ca-user/ca-user-add";
    }


    @RequestMapping("/editCaRegister")
    @ResponseBody
    public ResponseMessage<TransCaRegister> editCaRegister(TransCaRegister transCaRegister) {
        transCaRegister.setRegisterTime(Calendar.getInstance().getTime());
        transCaRegister.setRegisterUser(SecUtil.getLoginUserViewName());
        return transCaRegisterClient.saveTransCaRegister(transCaRegister);
    }




}
