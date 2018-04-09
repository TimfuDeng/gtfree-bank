package cn.gtmap.landsale.client.web.console;


import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ServiceUtils serviceUtils;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysCakeyService sysCakeyService;
    @Autowired
    CaSvsService caSvsService;
    @Autowired
    TransBidderService transBidderService;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;




    @RequestMapping("/content.f")
    public String detail(String resourceId,Model model) throws Exception{


        return "/common/resource-content";
    }

    @RequestMapping("/index1")
    public String index1() throws Exception{
        return "oneprice/index1";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value = "url", required = false) String url) {
        model.addAttribute("_url", url);
        model.addAttribute("caEnabled",caEnabled);
        return "oneprice/login";
    }



    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "client用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,
                        @RequestParam(value = "username") String name,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "url", defaultValue = "/") String url) {
        try {
            SysUser sysUser =  sysUserService.validatePassword(name, password, 1);
            TransUser transUser = new TransUser();
            transUser.setUserId(sysUser.getId());
            transUser.setUserName(sysUser.getUserName());
            transUser.setViewName(sysUser.getUserName());
            SecUtil.setLoginUserIdToSession(req, transUser);
        } catch (Exception e) {
            ra.addFlashAttribute("_msg", e.getMessage());
            return "redirect:/login?url=" + url;
        }
        return "redirect:" + url;
    }

    /**
     * ca登录
     * @param req
     * @param ra
     * @param resp
     * @param caSignerX
     * @param url
     * @return
     */
    @RequestMapping(value = "/calogin", method = RequestMethod.POST)
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "clientCA用户登录")
    public String calogin(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,CaSignerX caSignerX,
                        @RequestParam(value = "url", defaultValue = "/") String url) {
        String errorMsg ="数字证书信息错误！";
        //caSignerX.setCertFriendlyName(stripscript(caSignerX.getCertFriendlyName()));
        try {
//caSvsService.validateCertificate(caSignerX.getSxcertificate())

            if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){
               // logger.error(caSignerX.getCertFriendlyName()+"login 验证通过=="+caSignerX.getSerialNumber().trim());
                SysCakey sysCakey=sysCakeyService.getSysCakey(caSignerX.getSerialNumber().trim());
                if (sysCakey==null){
                    throw new UserNotFoundException(caSignerX.getCertFriendlyName());
                }
                logger.error(caSignerX.getCertFriendlyName()+"sysCakey.getUserId()"+"=="+sysCakey.getUserId());
                SysUser sysUser = sysUserService.getSysUser(sysCakey.getUserId());
                if(sysUser==null){
                    throw new UserNotFoundException(caSignerX.getCertFriendlyName());
                }
                //找到trans_bidder表
               // logger.error(caSignerX.getCertFriendlyName()+"sysUser.getRefId()"+"=="+sysUser.getRefId());
                TransBidder bidder = transBidderService.getTransBidder(sysUser.getRefId());
               // logger.error(caSignerX.getCertFriendlyName()+"bidder.getId()"+"=="+bidder.getId());
                if (null!=bidder){
                    sysUser.setUserName(bidder.getName());
                }

                TransUser transUser = new TransUser();
                transUser.setUserId(sysUser.getId());
                transUser.setUserName(sysUser.getUserName());
                transUser.setViewName(sysUser.getUserName());
               // logger.error(sysUser.getUserName()+"TransUser"+"=="+transUser.getUserId());
               // logger.error(caSignerX.getCertFriendlyName()+"login 登录进来=="+caSignerX.getSerialNumber().trim());
                SecUtil.setLoginUserIdToSession(req, transUser);
            }else{
                ra.addFlashAttribute("_msg", errorMsg);
                return "redirect:/login?url=" + url;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            e.printStackTrace();
            ra.addFlashAttribute("_msg", errorMsg);
            return "redirect:/login?url=" + url;
        }
        return "redirect:" + url;
    }

    public static String stripscript(String s) {
        String regEx = "[java|javascript|script|alert|\'|\"|/|\\|<|>]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        s =  m.replaceAll("").trim();
        return s;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        SecUtil.logout4Session(req);
        return "redirect:/oneprice/index";
    }
    /**
     * 获取服务器时间
     * @return
     */
    @RequestMapping("/getServerTime.f")
    public @ResponseBody String getServerTime(){
        return serviceUtils.getServerTime();
    }


}



