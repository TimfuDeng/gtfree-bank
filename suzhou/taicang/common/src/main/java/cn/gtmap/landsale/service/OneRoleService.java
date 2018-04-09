package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.OneRole;

/**
 * Created by trr on 2016/8/10.
 */
public interface OneRoleService {
    OneRole getOneRoleByTransUserId(String transUserId);
    OneRole updateUserPrivileges(String userId, String privileges);
}
