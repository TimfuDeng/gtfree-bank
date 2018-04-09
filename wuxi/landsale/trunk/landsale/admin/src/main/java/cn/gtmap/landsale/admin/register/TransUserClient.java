package cn.gtmap.landsale.admin.register;


import cn.gtmap.egovplat.core.data.PageImpl;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户服务
 * @author zsj
 * @version v1.0, 2017/9/12
 */
@FeignClient(name = "core-server")
public interface TransUserClient {

    /**
     * 获取用户分页服务
     * @param viewName 用户名
     * @param regionCodes 行政区代码
     * @param userType 用户类型
     * @return
     */
    @RequestMapping(value = "/user/findTransUserPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    PageImpl<TransUser> findTransUserPage(@RequestParam(value = "viewName", required = false) String viewName, @RequestParam(value = "userType", required = false) Integer userType, @RequestParam(value = "regionCodes", required = false)  String regionCodes);

    /**
     * 保存用户
     * @param transUser 用户对象
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value = "/user/saveTransUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransUser> saveTransUser(@RequestBody TransUser transUser, @RequestParam(value = "roleId") String roleId);

    /**
     * 删除用户
     * @param userIds 用户Ids
     * @return
     */
    @RequestMapping(value = "/user/deleteTransUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage deleteTransUser(@RequestParam(value = "userIds")  String userIds);
    
    /**
     * 验证用户名和密码
     * @param userName 用户名
     * @param password 密码
     * @param userType 用户类型
     * @return
     */
    @RequestMapping(value = "/user/validatePassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransUser> validatePassword(@RequestParam(value = "userName") String userName, @RequestParam(value = "password")  String password, @RequestBody Constants.UserType userType);

    /**
     * 根据用户的CA指纹获取用户
     * @param thumbprint
     * @return
     */
    @RequestMapping(value = "/user/getTransUserByCAThumbprint", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUser getTransUserByCAThumbprint(@RequestParam("thumbprint") String thumbprint);

    /**
     * 根据用户登录名获取用户对象
     * @param userName
     * @return
     */
    @RequestMapping(value = "/user/getTransUserByUserName", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUser getTransUserByUserName(@RequestParam("userName") String userName);

    /**
     * 根据用户获取用户对象
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getTransUserById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransUser getTransUserById(@RequestParam("userId") String userId);

    /**
     * 根据角色 获取用户对象列表
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/user/getTransUserListByRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransUser> getTransUserListByRole(@RequestParam("roleId") String roleId);
}

