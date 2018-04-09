package cn.gtmap.landsale.admin.web;

import cn.gtmap.landsale.admin.service.IdentityService;
import cn.gtmap.landsale.admin.service.ServiceUtils;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.CaSignerX;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;
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

/**
 * 登录
 * @author zsj
 * @version v1.0, 2017/9/18
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
                        @RequestParam(value = "url", defaultValue = "/") String url) {
        try {
            ResponseMessage<TransUser> responseMessage = identityService.login(name, password, Constants.UserType.MANAGER);
            if (!responseMessage.getFlag()) {
                ra.addFlashAttribute("_msg", responseMessage.getMessage());
                return "redirect:/login?url=index";
//                return "redirect:/login?url=" + url;
            }
        } catch (Exception e) {
            ra.addFlashAttribute("_msg", e.getMessage());
            return "redirect:/login?url=index";
//            return "redirect:/login?url=" + url;
        }
        return "redirect:index";
//        return "redirect:" + url;
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
            identityService.adminCaLogin(caSignerX);
        } catch (Exception e) {
            ra.addFlashAttribute("_msg", e.getMessage());
            return "redirect:/login?url=" + url;
        }
        return "redirect:" + url;
    }


    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        identityService.logout();
        return "redirect:/login";
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
