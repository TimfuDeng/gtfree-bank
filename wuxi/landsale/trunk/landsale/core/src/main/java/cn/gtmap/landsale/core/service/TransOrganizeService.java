package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganize;

import java.util.List;

/**
 * 组织 部门Service
 * @author zsj
 * @version v1.0, 2017/9/6
 */
public interface TransOrganizeService {

    /**
     * 获取组织 分页服务
     * @param organizeName 组织名称
     * @param regionCodes 所属行政区
     * @param request 分页请求
     * @return
     */
    Page<TransOrganize> findTransOrganizePage(String organizeName, String regionCodes, Pageable request);

    /**
     * 获取组织 列表服务
     * @param organizeName 组织名称
     * @param regionCode 组织代码
     * @return
     */
    List<TransOrganize> findTransOrganizeList(String organizeName, String regionCode);

    /**
     * 通过角色 获取组织 列表服务
     * @param roleId 角色编号
     * @return
     */
    List<TransOrganize> findTransOrganizeByRole(String roleId);

    /**
     * 根据组织Id 获取组织对象
     * @param organizeId 组织Id
     * @return
     */
    TransOrganize getTransOrganizeById(String organizeId);


    /**
     * 保存组织
     * @param transOrganize 组织对象
     * @param regionCodes 所属行政区
     * @return
     */
    ResponseMessage<TransOrganize> saveTransOrganize(TransOrganize transOrganize, String regionCodes);

    /**
     * 删除组织
     * @param transOrganize 组织
     * @return
     */
    ResponseMessage deleteTransOrganize(TransOrganize transOrganize);

    /**
     * 修改组织
     * @param transOrganize 组织对象
     * @param regionCodes 所属行政区
     * @return
     */
    ResponseMessage<TransOrganize> updateTransOrganize(TransOrganize transOrganize, String regionCodes);

}
