package cn.gtmap.landsale.client.web;

import cn.gtmap.landsale.client.register.TransRegionClient;
import cn.gtmap.landsale.client.register.TransResourceClientBak;
import cn.gtmap.landsale.client.register.TransTimeClient;
import cn.gtmap.landsale.client.register.TransUserClient;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.security.SecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
public class LoginController {

    @Autowired
    TransTimeClient transTimeClient;

    @Autowired
    TransResourceClientBak transResourceClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransRegionClient transRegionClient;

   /* @Autowired
    CaSvsService caSvsService;*/

   /* @Value("${ca.login.enabled}")
    Boolean caEnabled;*/



    @RequestMapping(value = {"/index", "/"})
    public String index(Model model, @ModelAttribute("url") String url) throws Exception {
        model.addAttribute("redirectUrl", url);
        return "/index";
    }

    @RequestMapping("/getRegion")
    public String getRegion(String regionCode, Integer regionLevel, Model model) throws Exception {
        List<TransRegion> transRegionList = transRegionClient.findTransRegionListByCodeOrLeave(regionCode, regionLevel);
        model.addAttribute("transRegionList", transRegionList);
        return "common/index-region";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value = "url", required = false) String url) {
        model.addAttribute("url", url);
        model.addAttribute("caEnabled",false);
        return "/login";
    }

    @RequestMapping("/help")
    public String help(Model model, @RequestParam(value = "tag", required = false) String tag) {
        model.addAttribute("_tag", tag);
        return "/help";
    }

   /* @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "client用户登录")*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,
                        @RequestParam(value = "username") String name,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "url", defaultValue = "/") String url, Model model) {
        try {
            ResponseMessage<TransUser> responseMessage = transUserClient.validatePassword(name, password);
            if (responseMessage.getFlag()) {
                TransUser transUser = responseMessage.getEmpty();
                SecUtil.setLoginUserToSession(req, transUser);
                SecUtil.setLoginUserToLocal(transUser, req);
            } else {
                ra.addFlashAttribute("_msg", responseMessage.getMessage());
                return "redirect:/login?url=" + url;
            }
        } catch (Exception e) {
            System.out.println(e);
            ra.addFlashAttribute("_msg", e.getMessage());
            return "redirect:/login?url=" + url;
        }
//        if (null!=transUser){
//            return "redirect:" + "/resource/index?userId="+transUser.getUserId();
//        }else {
//            return "redirect:" + "/resource/index";
//        }
        ra.addFlashAttribute("url", url);
        return "redirect:" + "/index";
    }

   /* *//**
     * ca登录
     * @param req
     * @param ra
     * @param resp
     * @param caSignerX
     * @param url
     * @return
     *//*
    @RequestMapping(value = "/calogin", method = RequestMethod.POST)
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "clientCA用户登录")
    public String calogin(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,CaSignerX caSignerX,
                        @RequestParam(value = "url", defaultValue = "/") String url) {
        String errorMsg ="数字证书信息错误！";
        try {
            if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){
                TransUser transUser = transUserService.getTransUserByCAThumbprint(caSignerX.getCertThumbprint());
                if(transUser==null){
                    transUser = new TransUser();
                    transUser.setUserId(caSignerX.getCertThumbprint());
                    transUser.setUserName(caSignerX.getCertFriendlyName());
                    transUser.setViewName(caSignerX.getCertFriendlyName());
                }
                SecUtil.setLoginUserIdToSession(req, transUser);
            }else{
                ra.addFlashAttribute("_msg", errorMsg);
                return "redirect:/login?url=" + url;
            }
        } catch (Exception e) {
            ra.addFlashAttribute("_msg", errorMsg);
            return "redirect:/login?url=" + url;
        }
        return "redirect:" + url;
    }*/

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        SecUtil.logout4Session(req);
        String url = req.getParameter("url");
        url = (url == null ? "" : "?url=" + url);
        return "redirect:/index";
    }

    /**
     * 获取服务器时间
     * @return
     */
    @RequestMapping("/getServerTime.f")
    public @ResponseBody
    String getServerTime(){
        return transTimeClient.getServiceTime();
    }


}

