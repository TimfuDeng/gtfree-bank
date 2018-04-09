package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.client.util.HTMLSpirit;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.CaSignerX;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
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
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
public class IndexController {

    @Autowired
    ServiceUtils serviceUtils;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransUserService transUserService;

    @Autowired
    ClientService clientService;

    @Autowired
    CaSvsService caSvsService;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;


    @RequestMapping("/index")
    public String index(ResourceQueryParam param,@PageDefault(value=12) Pageable page,Model model) throws Exception{
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.ResourceEditStatusPreRelease);
        if(param.getResourceStatus()==Constants.ResourceStatusChengJiao){
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.ResourceStatusGongGao);
        }
        if(StringUtils.isNotBlank(param.getTitle())){
            try {
                param.setTitle(HTMLSpirit.delHTMLTag(URLDecoder.decode(param.getTitle(), "UTF-8")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Set<String> regions = Sets.newHashSet();
        Page<TransResource> transResourcePage= transResourceService.findTransResources(param, regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("param", param);
        return "/index";
    }

    @RequestMapping("/content.f")
    public String detail(String resourceId,Model model) throws Exception{
        TransResource resource=transResourceService.getTransResource(resourceId);
        model.addAttribute("resource", resource);
        return "/common/resource-content";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value = "url", required = false) String url) {
        model.addAttribute("_url", url);
        model.addAttribute("caEnabled",caEnabled);
        return "/login";
    }

    @RequestMapping("/help")
    public String help(Model model, @RequestParam(value = "tag", required = false) String tag) {
        model.addAttribute("_tag", tag);
        return "/help";
    }

    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "client用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,
                        @RequestParam(value = "username") String name,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "url", defaultValue = "/") String url) {
        try {
            TransUser transUser = transUserService.validatePassword(name, password,null);
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
    @RequestMapping(value = "/calogin")
    @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
            description = "clientCA用户登录")
    public String calogin(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,CaSignerX caSignerX,
                        @RequestParam(value = "url", defaultValue = "/") String url) {
        TransCaUser transCaUser = clientService.getTransCaUserByCAThumbprint(caSignerX.getCertThumbprint());
        if(transCaUser == null){
            ra.addFlashAttribute("_msg", "该数字证书用户未注册，请联系管理员！");
            return "redirect:/login?url=" + url;
        }
        if(!transCaUser.getStatus().isEnabled()){
            ra.addFlashAttribute("_msg", "该数字证书用户已被禁用，请联系管理员！");
            return "redirect:/login?url=" + url;
        }

        String errorMsg ="数字证书信息错误！";
        try {
            if(caSvsService.validateCertificate(caSignerX.getSxcertificate())){
                TransUser transUser = transUserService.getTransUserByKeyInfo(caSignerX.getCertFriendlyName(), caSignerX.getCertThumbprint());
                if (transUser == null) {
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
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        SecUtil.logout4Session(req);
//        String url = req.getParameter("url");
//        url = url == null ? "" : "?url=" + url;
        return "redirect:/index";
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

