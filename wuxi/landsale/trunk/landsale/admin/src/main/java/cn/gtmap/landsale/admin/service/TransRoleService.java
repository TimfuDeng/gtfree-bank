package cn.gtmap.landsale.admin.service;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.TransRole;

import java.util.List;
import java.util.Map;

/**
 * 角色Service
 * @author zsj
 * @version v1.0, 2017/9/7
 */
public interface TransRoleService {

    /**
     * 获取角色列表 分页
     * @param request
     * @param roleName 角色名
     * @return
     */
    Page<TransRole> getTransRolePage(String roleName, Pageable request);


    /**
     * 获取角色
     * @return
     */
    List<TransRole> getTransRole();

    /**
     * 通过行政区 获取角色
     * @param regionCode
     * @return
     */
    List<TransRole> getTransRoleByRegion(String regionCode);

    /**
     * 获取角色
     * @param roleId 角色id
     * @return
     */
    TransRole getTransRoleById(String roleId);

    /**
     * 通过用户 获取角色
     * @param userId 用户id
     * @return
     */
    TransRole getTransRoleByUserId(String userId);

    /**
     * 添加角色
     * @param transRole
     * @param organizeId
     * @return
     */
    TransRole addTransRole(TransRole transRole, String organizeId);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    Map<String, Object> deleteTransRole(String roleId);

    /**
     * 修改角色
     * @param transRole
     * @param organizeId
     * @return
     */
    TransRole updateTransRole(TransRole transRole, String organizeId);

    /**
     * 角色授权
     * @param menuIds
     * @param roleId
     * @param regionCodes 行政区代码
     * @return
     */
    Map<String, Object> grantRole(String menuIds, String roleId, String regionCodes);

}
