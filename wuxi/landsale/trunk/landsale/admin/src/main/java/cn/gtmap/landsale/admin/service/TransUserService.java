package cn.gtmap.landsale.admin.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUser;

/**
 * 用户服务
 * @author zsj
 * @version v1.0, 2017/9/18
 */
public interface TransUserService {

    /**
     * 保存用户
     * @param transUser 用户对象
     * @param roleId 角色id
     * @return
     */
    ResponseMessage<TransUser> saveTransUser(TransUser transUser, String roleId);

    /**
     * 竞买人添加
     * @param transUser
     * @return
     */
//    ResponseMessage<TransUser> saveTransUser(TransUser transUser);

    /**
     * 修改用户
     * @param transUser 用户对象
     * @param roleId 角色id
     * @return
     */
    ResponseMessage<TransUser> updateTransUser(TransUser transUser, String roleId);


    /**
     * 删除用户
     * @param userIds 用户Ids
     * @return
     */
    ResponseMessage deleteTransUser(String userIds);



}
