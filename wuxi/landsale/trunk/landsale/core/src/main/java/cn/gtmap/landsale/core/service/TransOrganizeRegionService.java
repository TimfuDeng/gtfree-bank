package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganizeRegion;

import java.util.List;

/**
 * 组织行政区关系 行政区 关系Service
 * @author zsj
 * @version v1.0, 2017/9/6
 */
public interface TransOrganizeRegionService {

    /**
     * 获取组织行政区关系 列表服务
     * @param organizeId 组织行政区关系Id
     * @param regionCode 行政区Code
     * @return
     */
    List<TransOrganizeRegion> findTransOrganizeRegionByOrganizeOrRegion(String organizeId, String regionCode);

    /**
     * 根据组织行政区关系Id 获取组织行政区关系对象
     * @param organizeRegionId 组织行政区关系Id
     * @return
     */
    TransOrganizeRegion getTransOrganizeRegionById(String organizeRegionId);


    /**
     * 保存组织行政区关系
     * @param transOrganizeRegion 组织行政区关系对象
     * @return
     */
    TransOrganizeRegion saveTransOrganizeRegion(TransOrganizeRegion transOrganizeRegion);

    /**
     * 删除组织行政区关系
     * @param transOrganizeRegion 组织行政区关系
     * @return
     */
    ResponseMessage deleteTransOrganizeRegion(TransOrganizeRegion transOrganizeRegion);

    /**
     * 删除组织行政区关系
     * @param transOrganizeRegionList 组织行政区关系
     * @return
     */
    ResponseMessage deleteTransOrganizeRegion(List<TransOrganizeRegion> transOrganizeRegionList);

    /**
     * 修改组织行政区关系
     * @param transOrganizeRegion 组织行政区关系
     * @return
     */
    TransOrganizeRegion updateTransOrganizeRegion(TransOrganizeRegion transOrganizeRegion);

}
