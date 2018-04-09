package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.core.service.TransUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 * @author zsj
 * @version v1.0, 2017/9/18
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    TransUserService transUserService;

    /**
     * 获取用户分页服务
     * @param viewName 用户名
     * @param request 分页请求
     * @param regionCodes 行政区代码
     * @return
     */
    @RequestMapping("/findTransUserPage")
    public Page<TransUser> findTransUserPage(@RequestParam(value = "viewName", required = false) String viewName, @RequestParam(value = "userType", required = false) Integer userType, @RequestParam(value = "regionCodes", required = false) String regionCodes, @PageDefault(value = 4) Pageable request) {
        return transUserService.findTransUserPage(viewName, userType, regionCodes, request);
    }

    /**
     * 保存用户
     * @param transUser 用户对象
     * @return
     */
    @RequestMapping("/saveTransUser")
    public ResponseMessage<TransUser> saveTransUser(@RequestBody TransUser transUser, @RequestParam(value = "roleId") String roleId) {
      return transUserService.saveTransUser(transUser, roleId);
    }

    /**
     * 保存用户
     * @param transUser 用户对象
     * @return
     */
    @RequestMapping("/addJmr")
    public ResponseMessage<TransUser> addJmr(@RequestBody TransUser transUser) {
        return transUserService.addJmr(transUser);
    }

    /**
     * 删除用户
     * @param userIds 用户Ids
     */
    @RequestMapping("/deleteTransUser")
    public ResponseMessage deleteTransUser(@RequestParam(value = "userIds") String userIds) {
        return transUserService.deleteTransUser(userIds);
    }

    /**
     * 验证用户名和密码
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping("/validatePassword")
    public ResponseMessage<TransUser> validatePassword(@RequestParam(value = "userName") String userName, @RequestParam(value = "password")  String password, @RequestBody(required = false) Constants.UserType userType) {
        return transUserService.validatePassword(userName, password, userType);
    }

    /**
     * 根据用户的CA指纹获取用户
     * @param thumbprint
     * @return
     */
    @RequestMapping("/getTransUserByCAThumbprint")
    public TransUser getTransUserByCAThumbprint(@RequestParam("thumbprint") String thumbprint) {
        return transUserService.getTransUserByCAThumbprint(thumbprint);
    }

    /**
     * 根据用户登录名获取用户对象
     * @param userName
     * @return
     */
    @RequestMapping("/getTransUserByUserName")
    public TransUser getTransUserByUserName(@RequestParam("userName") String userName) {
        return transUserService.getTransUserByUserName(userName);
    }

    /**
     * 根据用户Id获取用户对象
     * @param userId
     * @return
     */
    @RequestMapping("/getTransUserById")
    public TransUser getTransUserById(@RequestParam("userId") String userId) {
        return transUserService.getTransUserById(userId);
    }

    /**
     * 获取用户对象列表
     * @param roleId
     * @return
     */
    @RequestMapping("/getTransUserListByRole")
    public List<TransUser> getTransUserListByRole(@RequestParam("roleId") String roleId) {
        return transUserService.getTransUserListByRole(roleId);
    }



    /* @AuditServiceLog(category = Constants.LogCategory.USER_LOGIN,producer = Constants.LogProducer.CLIENT,
             description = "client用户登录")*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TransUser login(@RequestParam(value = "username") String name,
                        @RequestParam(value = "password") String password) {

            TransUser transUser = transUserService.validatePassword(name, password,null).getEmpty();

        return transUser;
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
  /*  @RequestMapping(value = "/calogin", method = RequestMethod.POST)
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
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        SecUtil.logout4Session(req);
//        String url = req.getParameter("url");
//        url = url == null ? "" : "?url=" + url;
        return "redirect:/index";
    }*/
}
