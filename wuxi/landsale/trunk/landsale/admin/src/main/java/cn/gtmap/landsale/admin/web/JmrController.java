package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransJmrClient;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.TransRoleService;
import cn.gtmap.landsale.admin.service.TransUserService;
import cn.gtmap.landsale.admin.service.TransUserViewService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.model.TransUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 竞买人管理
 * @author cxm
 * @version v1.0, 2017/9/29
 */
@Controller
@RequestMapping("/jmr")
public class JmrController {
    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransJmrClient transJmrClient;

    @Autowired
    TransUserService transUserService;

    @Autowired
    TransRoleService transRoleService;

    @Autowired
    TransUserViewService transUserViewService;

    @RequestMapping("index")
    public String index(HttpServletRequest request, String viewName, Integer userType, String regionCodes, @PageDefault(value = 10) Pageable page, Model model) {
        Page<TransUserView> transUserList = transUserViewService.findTransUserPage(viewName, userType, regionCodes, page);
        model.addAttribute("transUserList", transUserList);
        model.addAttribute("viewName", viewName);
        model.addAttribute("userType", userType);
        model.addAttribute("regionCodes", regionCodes);
        return "jmr/jmr-list";
    }

    @RequestMapping("/add")
    public String add() {
        return "jmr/jmr-add";
    }

    @RequestMapping("/addJmr")
    @ResponseBody
    public ResponseMessage<TransUser> addJmr(TransUser transUser) {
        return transJmrClient.addJmr(transUser);
    }

    @RequestMapping("/edit")
    public String edit(Model model, String userId) {
        TransUserView transUserView = transUserViewService.getTransUserViewById(userId);
        model.addAttribute("transUser", transUserView);
        return "jmr/jmr-add";
    }

    @RequestMapping("/editJmr")
    @ResponseBody
    public ResponseMessage<TransUser> editJmr(TransUser transUser) {
        return transJmrClient.editJmr(transUser);
    }

    @RequestMapping("/deleteJmr")
    @ResponseBody
    public ResponseMessage<TransUser> deleteJmr(String userIds) {
        return transJmrClient.deleteJmr(userIds);
    }

}
