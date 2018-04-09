package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.landsale.model.CaSignerX;
import cn.gtmap.landsale.service.IdentityService;
import cn.gtmap.landsale.service.ServiceUtils;
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

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/23
 */
@Controller
@RequestMapping(value = "/")
public class AuthController {

    @Value("${ca.login.enabled}")
    Boolean caEnabled;
    @Autowired
    ServiceUtils serviceUtils;
    @Autowired
    IdentityService identityService;

    @RequestMapping("login")
    public String login(Model model, @RequestParam(value = "url", required = false) String url) {
        model.addAttribute("_url", url);
        model.addAttribute("caEnabled", caEnabled);
        return "/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp,
                        @RequestParam(value = "username") String name,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "url" ,defaultValue = "/") String url){
        try {
            identityService.login(name, password,0);
        } catch (Exception e) {
            ra.addFlashAttribute("_msg", e.getMessage());
            return "redirect:/login?"+ url;
        }
        return "redirect:" + url;
    }

    /**
     * ca登录
     *
     * @param req
     * @param ra
     * @param resp
     * @param caSignerX
     * @param url
     * @return
     */
    @RequestMapping(value = "calogin", method = RequestMethod.POST)
    public String calogin(HttpServletRequest req, RedirectAttributes ra, HttpServletResponse resp, CaSignerX caSignerX,
                          @RequestParam(value = "url", defaultValue = "/") String url) {
        try {
            caSignerX.setCertFriendlyName(stripscript(caSignerX.getCertFriendlyName()));
            identityService.adminCaLogin(caSignerX);
        } catch (Exception e) {
            ra.addFlashAttribute("_msg", e.getMessage());
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
        identityService.logout();
        return "redirect:/oneprice/resource/list";
    }

    /**
     * 获取服务器时间
     *
     * @return
     */
    @RequestMapping("getServerTime.f")
    public
    @ResponseBody
    String getServerTime() {
        return serviceUtils.getServerTime();
    }
}
