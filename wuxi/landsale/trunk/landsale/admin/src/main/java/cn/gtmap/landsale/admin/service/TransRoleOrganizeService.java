package cn.gtmap.landsale.admin.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRoleOrganize;

import java.util.List;

/**
 * 角色 组织 关系Service
 * @author zsj
 * @version v1.0, 2017/9/6
 */
public interface TransRoleOrganizeService {

    /**
     * 获取角色组织关系 列表服务
     * @param roleId 角色Id
     * @param organizeId 组织Id
     * @return
     */
    List<TransRoleOrganize> findTransRoleOrganizeByRoleIdOrOrganizeId(String roleId, String organizeId);

    /**
     * 根据角色组织关系Id 获取角色组织关系对象
     * @param roleOrganizeId 角色组织关系Id
     * @return
     */
    TransRoleOrganize getTransRoleOrganizeById(String roleOrganizeId);


    /**
     * 保存角色组织关系
     * @param transRoleOrganize 角色组织关系对象
     * @return
     */
    TransRoleOrganize saveTransRoleOrganize(TransRoleOrganize transRoleOrganize);

    /**
     * 删除角色组织关系
     * @param transRoleOrganize 角色组织关系
     * @return
     */
    ResponseMessage deleteTransRoleOrganize(TransRoleOrganize transRoleOrganize);

    /**
     * 删除角色组织关系
     * @param transRoleOrganizeList 角色组织关系
     * @return
     */
    ResponseMessage deleteTransRoleOrganize(List<TransRoleOrganize> transRoleOrganizeList);

    /**
     * 修改角色组织关系
     * @param transRoleOrganize 角色组织关系
     * @return
     */
    TransRoleOrganize updateTransRoleOrganize(TransRoleOrganize transRoleOrganize);

}
