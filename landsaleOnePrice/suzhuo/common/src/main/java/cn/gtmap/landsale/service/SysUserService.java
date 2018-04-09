package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.security.ex.PasswordException;
import cn.gtmap.egovplat.security.ex.UserLockedException;
import cn.gtmap.egovplat.security.ex.UserNotFoundException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.SysUser;

import java.util.Map;
import java.util.Set;

/**
 * Created by trr on 2016/8/10.
 */
public interface SysUserService {

    SysUser getSysUser(String id);

    SysUser geSysUserByUserName(String userName);

    Page<SysUser> findSysUser(String viewName,Integer userType,Pageable request);

    SysUser validatePassword(String userName, String password,Integer userType) throws UserNotFoundException, UserLockedException, PasswordException;

    Set<Map> getAvailableUrlResources();
}
