package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.web.ResourceQueryParam;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 出让地块服务
 * Created by jiff on 14/12/21.
 */
public interface TransResourceService {

    /**
     * 获得出让地块
     * @param resourceId 地块Id
     * @return
     */
    TransResource getTransResource(String resourceId);

    /**
     * 根据公告Id获取公告地块
     * @param ggId
     * @return
     */
    List<TransResource> findTransResource(String ggId);


    /**
     * 保存出让地块对象
     * @param resource 出让地块
     * @return
     */
    TransResource saveTransResource(TransResource resource);

    /**
     * 查询资源，分页
     * @param queryParam
     * @param request
     * @return
     */
    Page<TransResource> findTransResources(ResourceQueryParam queryParam,Collection<String> regionCodes, Pageable request);

    /**
     * 查询资源，分页,依据公告id
     * @param queryParam
     * @param regionCodes
     * @param request
     * @return
     */
    Page<TransResource> findTransResourcesByCrggIds(ResourceQueryParam queryParam,Collection<String> regionCodes,Collection<String> ggIds, Pageable request);

    /**
     * 获取可以显示在大屏幕上的地块
     * @param title
     * @param displayStatus
     * @param regionCodes
     * @param request
     * @return
     */
    Page<TransResource> findDisplayResource(String title,String displayStatus,Collection<String> regionCodes,Pageable request);

    /**
     * 查询资源、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    Page<TransResource> findTransResourcesByEditStatus(String title, int status,String ggId,Collection<String> regionCodes, Pageable request);

    /**
     * 统计未审核，报名中，已通过
     * @param transResourcePage
     * @return
     */
    Page<TransResource> countPassOrUnpass(Page<TransResource> transResourcePage);
    /**
     * 获取正在挂牌，已结束的地块
     *
     * @param request
     * @return
     */
    Page<TransResource> findResource(String title,Collection<String> regionCodes,Pageable request);
    /**
     * 查询已成交资源、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    Page<TransResource> findDealTransResourcesByEditStatus(String title, int status,int resourceStatus,String ggId,Collection<String> regionCodes, Pageable request);

    /**
     * 查询用户所竞拍的地块列表
     * @param userId 用户Id
     * @param status
     * @param regionCodes 行政区代码
     * @param request
     * @return
     */
    Page<TransResource> findTransResourcesByUser(String userId,int status,Collection<String> regionCodes,Pageable request);
    /**
     * 获得正在交易的地块，编辑状态为发布的
     * @return
     */
    List<TransResource> getTransResourcesOnRelease();


    /**
     * 更新地块状态
     * @param resource
     * @param status
     * @return
     */
    TransResource saveTransResourceStatus(TransResource resource,int status);


    /**
     * 根据地块Id删除地块对象
     * @param resourceIds
     */
    void deleteTransResource(Collection<String> resourceIds);

    /**
     * 根据公告Id删除地块对象
     * @param ggIds
     */
    void deleteTransResourceByGgId(Collection<String> ggIds);

    /**
     * 更新地块在交易大屏幕上的显示状态
     * @param resourceId
     * @param displayStatus
     */
    void updateTransResourceDisplayStatus(String resourceId,int displayStatus);

    /**
     * 根据显示状态查找地块
     * @param displayStatus
     * @return
     */
    List<TransResource> findTransResourcesByDisplayStatus(int displayStatus);

    /**
     * 统计不同状态的地块数量
     * @param resourceStatus 地块状态
     * @param regionCodes 行政区代码
     * @return
     */
    Long countByResourcesStatus(int resourceStatus,Collection<String> regionCodes);

    /**
     * 统计不同状态和编辑状态的地块数量
     * @param resourceStatus 地块状态
     * @param resourceEditStatus 地块编辑状态
     * @param regionCodes 行政区代码
     * @return
     */
    Long countByResourcesStatusAndEditStatus(int resourceStatus,int resourceEditStatus,Collection<String> regionCodes);

    /**
     * 成交总额统计
     * @param regionCodes 行政区代码
     * @return
     */
    BigDecimal sumOfDeal(Collection<String> regionCodes);

    /**
     * 根据保证金截止时间查询
     * @param startTime 查询起事件
     * @param endTime 查询至时间
     * @return
     */
    List<TransResource> findTransResourceByBzjEndTime(Date startTime,Date endTime);

    /**
     * 根据挂牌截止时间查询
     * @param startTime 查询起事件
     * @param endTime 查询至时间
     * @return
     */
    List<TransResource> findTransResourceByGpEndTime(Date startTime,Date endTime);

    /**
     * 根据地块号获得资源
     * @param transResourceCode
     * @return
     */
    TransResource getResourceByCode(String transResourceCode);

    /**
     * 查询勾选了底价的地块
     * @param queryParam
     * @param regionCodes
     * @param request
     * @return
     */
    Page<TransResource> findTransResourcesByMinOffer(ResourceQueryParam queryParam,Collection<String> regionCodes, Pageable request);

    /**
     * 根据条件查找已成交的地块 view用
     * @param resourceCode
     * @param resourceLocation
     * @param resourcePurpose
     * @param
     * @return
     */
    Page<TransResource> findDealResourceByCondiction(String resourceCode,String resourceLocation,String resourcePurpose,String beginTime,String endTime,Pageable request);

    List<TransResource> findDealResourceByCondiction2List(String resourceCode,String resourceLocation,String resourcePurpose,String beginTime,String endTime,Pageable request);

    /**
     * 根据条件查找已成交的地块 统计查询
     * @param resourceCode
     * @param resourceLocation
     * @param resourcePurpose
     * @param beginTime
     * @param endTime
     * @param request
     * @param zdCode
     * @return
     */
    Page<TransResource> findDealResourceByCondiction2ResourceIds(String resourceCode,String resourceLocation,String resourcePurpose,String beginTime,String endTime,Pageable request,String zdCode);

    /**
     * 根据条件查找已成交的地块 统计查询(只精确到天)
     * @param resourceCode
     * @param resourceLocation
     * @param resourcePurpose
     * @param beginTime
     * @param endTime
     * @param request
     * @param zdCode
     * @return
     */
    Page<TransResource> findDealResourceByCondiction2ResourceIds2(String resourceCode,String resourceLocation,String resourcePurpose,String beginTime,String endTime,Pageable request,String zdCode);
    List<TransResource> findDealResourceByCondiction2ResourceIds2List(String resourceCode,String resourceLocation,String resourcePurpose,String beginTime,String endTime,Pageable request,String zdCode);
}
