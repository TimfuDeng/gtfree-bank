package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.CaSignerX;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Sets;
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
import java.util.Collection;
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
    CaSvsService caSvsService;

    @Autowired
    TransCrggService transCrggService;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;


    @RequestMapping("/index")
    public String index(ResourceQueryParam param,@PageDefault(value=12) Pageable page,String regionCode,Model model) throws Exception{
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.ResourceEditStatusPreRelease);
        if(param.getResourceStatus()==Constants.ResourceStatusChengJiao){
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.ResourceStatusGongGao);
        }
        Set<String> regions = Sets.newHashSet();
        Collection<String> ggIds= transCrggService.findTransCrggIds();
        //Page<TransResource> transResourcePage= transResourceService.findTransResources(param, regions, page);
        regions.add(regionCode);//分为国土环保是工业和土地储备是经营性地块
        Page<TransResource> transResourcePage= transResourceService.findTransResourcesByCrggIds(param, regions,ggIds, page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("param", param);
        model.addAttribute("regionCode",regionCode);
        return "/index";
    }

    @RequestMapping("/index/type")
    public String indexType() throws Exception{
        return "/index-type";
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
                    ra.addFlashAttribute("_msg", "数字证书用户不存在！");
                    return "redirect:/login?url=" + url;
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
        return "redirect:/index/type";
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

