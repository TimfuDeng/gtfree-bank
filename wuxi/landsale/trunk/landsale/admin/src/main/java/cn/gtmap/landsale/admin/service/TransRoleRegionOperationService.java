package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.model.TransRoleRegionOperation;

import java.util.List;

/**
 * 角色操作菜单Service
 * @author zsj
 * @version v1.0, 2017/9/30
 */
public interface TransRoleRegionOperationService {

    /**
     * 获取角色操作菜单列表
     * @param roleId
     * @return
     */
    List<TransRoleRegionOperation> findTransRoleRegionOperationByRoleId(String roleId);

    /**
     * 保存角色操作菜单
     * @param transRoleRegionOperationList
     * @return
     */
    List<TransRoleRegionOperation> saveTransRoleRegionOperation(List<TransRoleRegionOperation> transRoleRegionOperationList);

    /**
     * 删除角色操作菜单
     * @param transRoleRegionOperationList
     */
    void deleteTransRoleRegionOperation(List<TransRoleRegionOperation> transRoleRegionOperationList);


}
