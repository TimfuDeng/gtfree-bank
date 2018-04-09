package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.Tree;

import java.util.List;

/**
 * 行政区Service
 * @author zsj
 * @version v1.0, 2017/9/6
 */
public interface TransRegionService {

    /**
     * 获取行政区分页服务
     * @param regionCode 行政区编码
     * @param regionLevel 行政区级别
     * @param request 分页请求
     * @return
     */
    Page<TransRegion> findTransRegionByCodeOrLeave(String regionCode, Integer regionLevel, Pageable request);

    /**
     * 获取行政区列表服务
     * @param regionCode 行政区编码 查询行政区及其子集
     * @param regionLevel 行政区级别
     * @return
     */
    List<TransRegion> findTransRegionByCodeOrLeave(String regionCode, Integer regionLevel);

    /**
     * 根据所属组织 获取组织所属行政区列表服务
     * @param organizeId 获取组织编号
     * @return
     */
    List<TransRegion> findTransRegionByOrganize(String organizeId);

    /**
     * 根据所属角色 获取组织所属行政区列表服务
     * @param roleId 角色编号
     * @return
     */
    List<TransRegion> findTransRegionByRole(String roleId);

    /**
     * 根据行政区Code获取行政区对象
     * @param regionCode 行政区Code
     * @return
     */
    TransRegion getTransRegionByRegionCode(String regionCode);

    /**
     * 根据角色操作行政区权限 获取行政区
     * @param roleId
     * @return
     */
    List<TransRegion> findTransRegionByRoleRegionOperation(String roleId);

    /**
     * 保存行政区
     * @param transRegion 行政区对象
     * @return
     */
    ResponseMessage saveTransRegion(TransRegion transRegion);

    /**
     * 删除行政区
     * @param transRegion 行政区
     * @return
     */
    ResponseMessage deleteTransRegion(TransRegion transRegion);

    /**
     * 修改行政区
     * @param transRegion 行政区对象
     * @return
     */
    ResponseMessage updateTransRegion(TransRegion transRegion);

    /**
     * 获取行政区Tree结果
     * @param regionCode 传入查询其子集
     * @return
     */
    List<Tree> getRegionTree(String regionCode);

}
