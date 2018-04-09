package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import cn.gtmap.landsale.core.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 交易地块服务
 * Created by jiff on 14/12/21.
 */
@Service
public class TransResourceServiceImpl extends HibernateRepo<TransResource, String> implements TransResourceService, ApplicationContextAware {

    @Autowired
    TransResourceInfoService transResourceInfoService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransResourceSonService transResourceSonService;

    ApplicationContext applicationContext;

    @Autowired
    TransFileService transFileService;

    @Autowired
    LandUseDictSerivce landUseDictSerivce;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获得出让地块
     *
     * @param resourceId 地块Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="ResourceCache",key="'getTransResource'+#resourceId")*/
    public TransResource getTransResource(String resourceId) {
        return get(resourceId);
    }

    /**
     * 获得出让地块
     * @param resourceCode 地块Code
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransResource getTransResourceByCode(String resourceCode) {
        return StringUtils.isNotBlank(resourceCode) ? get(criteria(Restrictions.eq("resourceCode", resourceCode))) : null;
    }

    /**
     * 获得出让地块集合
     * @param resourceCode 地块Code
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResource> getResourcesByCode(String resourceCode) {
        return StringUtils.isNotBlank(resourceCode) ?
                list(criteria(Restrictions.eq("resourceCode", resourceCode))) : null;
    }

    /**
     * 锁住地块资源
     *
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransResource getTransResourceForUpdate(String resourceId) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> mapParam = Maps.newHashMap();
        sql.append("select t.* from Trans_Resource t where 1=1");
        if (StringUtils.isNotBlank(resourceId)) {
            sql.append(" and t.resource_id=:resourceId");
            mapParam.put("resourceId", resourceId);
        }
        sql.append(" for update ");
        List<TransResource> transResourceList = sql(sql.toString(), mapParam).addEntity(TransResource.class).list();
        return transResourceList.get(0);
    }

    /**
     * 根据公告Id获取公告地块
     *
     * @param ggId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="ResourceCache",key="'findTransResource'+#ggId")*/
    public List<TransResource> findTransResource(String ggId) {
        return StringUtils.isNotBlank(ggId) ? list(getSession().
                createCriteria(TransResource.class).add(Restrictions.eq("ggId", ggId))) : null;
    }

    /**
     * 保存出让地块对象
     *
     * @param resource 出让地块
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResource> saveTransResource(TransResource resource) {
        // 保存地块信息
        LandUseDict landUseDict = landUseDictSerivce.getLandUseDict(resource.getTdytCode());
        if (landUseDict != null) {
            resource.setTdytName(landUseDict.getName());
        }
        TransResource transResource = saveOrUpdate(resource);
        // 查找是否存在 地块扩展信息
        TransResourceInfo transResourceInfoOld = transResourceInfoService.getTransResourceInfoByResourceId(transResource.getResourceId());
        TransResourceInfo transResourceInfo = resource.getTransResourceInfo();
        // 判断 是否存在地块扩展信息
        if (transResourceInfo != null) {
            // 如果存在 修改
            if (transResourceInfoOld != null) {
                transResourceInfo.setInfoId(transResourceInfoOld.getInfoId());
                transResourceInfoService.updateTransResourceInfo(transResourceInfo);
            } else {
                // 新增
                transResourceInfo.setInfoId(null);
                transResourceInfo.setResourceId(transResource.getResourceId());
                transResourceInfoService.saveTransResourceInfo(transResourceInfo);
            }
        }
        try {
            //检查资源线程
            /*TransResourceContainer transResourceTimer = (TransResourceContainer) applicationContext.getBean("transResourceContainer");
            transResourceTimer.checkResource(transResource);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseMessage(true, transResource);
    }

    /**
     * 修改出让地块对象
     * @param transResource 出让地块
     * @return
     */
    @Override
    public ResponseMessage<TransResource> updateTransResource(TransResource transResource) {
        transResource = merge(transResource);
        return new ResponseMessage(true, transResource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
   /* @Caching(evict={
            @CacheEvict(value="ResourceCache",allEntries = true),
            @CacheEvict(value="ResourceQueryCache", allEntries = true)
    })*/
    public ResponseMessage<TransResource> saveTransResourceStatus(TransResource resource, int status) {
        resource.setResourceStatus(status);
        return new ResponseMessage(true,saveOrUpdate(resource));
    }

    /**
     * 根据地块Id删除地块对象
     *
     * @param resourceIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
 /*   @Caching(evict={
            @CacheEvict(value="ResourceCache",allEntries=true),
            @CacheEvict(value="ResourceQueryCache", allEntries = true)
    })*/
    public void deleteTransResource(Collection<String> resourceIds) {
        // 删除 地块附件
        transFileService.deleteFilesByKey(resourceIds);
        for(String resourceId : resourceIds) {
            // 删除 地块扩展信息
            transResourceInfoService.deleteTransResourceInfo(resourceId);
            // 删除 多用途信息
            transResourceSonService.deleteTransResourceSonByResourceId(resourceId);
        }
        // 删除地块
        deleteByIds(resourceIds);
    }

    /**
     * 根据公告Id删除地块对象
     *
     * @param ggIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
  /*  @Caching(evict={
            @CacheEvict(value="ResourceCache",allEntries=true),
            @CacheEvict(value="ResourceQueryCache", allEntries = true)
    })*/
    public void deleteTransResourceByGgId(String ggIds) {
        for (String ggId : ggIds.split(",")) {
            List<TransResource> transResourceList = findTransResource(ggId);
            List<String> resourceIds = Lists.newArrayList();
            Iterator<TransResource> transResourceIterator = transResourceList.iterator();
            while (transResourceIterator.hasNext()) {
                resourceIds.add(transResourceIterator.next().getResourceId());
            }
            deleteTransResource(resourceIds);
        }
    }

    /**
     * 更新地块在交易大屏幕上的显示状态
     *
     * @param resourceId
     * @param displayStatus
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
  /*  @Caching(evict={
            @CacheEvict(value="ResourceCache",allEntries = true),
            @CacheEvict(value="ResourceQueryCache", allEntries = true)

    })*/
    public ResponseMessage<TransResource> updateTransResourceDisplayStatus(String resourceId, int displayStatus) {
        TransResource transResource = getTransResource(resourceId);
        if (transResource != null) {
            transResource.setDisplayStatus(displayStatus);
            saveOrUpdate(transResource);
            if (displayStatus==1){
                transResource.setDisplayStatus(displayStatus);
                saveOrUpdate(transResource);
                return new ResponseMessage(true, "已推送到大屏!");
            }else {
                transResource.setDisplayStatus(displayStatus);
                saveOrUpdate(transResource);
                return new ResponseMessage(true, "已取消推送!");
            }
        }else {
            return null;
        }
    }


    /**
     * 根据显示状态查找地块
     *
     * @param displayStatus
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="ResourceCache",key="'findTransResourcesByDisplayStatus'+#displayStatus")*/
    public List<TransResource> findTransResourcesByDisplayStatus(int displayStatus) {
        return list(getSession().
                createCriteria(TransResource.class).add(Restrictions.eq("displayStatus", displayStatus)));
    }

    /**
     * 统计不同状态的地块数量
     *
     * @param status
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Long countByResourcesStatus(int status, Collection<String> regionCodes) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (regionCodes != null && !regionCodes.isEmpty()) {
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        }
        criterionList.add(Restrictions.eq("resourceStatus", status));
        return count(criteria(criterionList));
    }

    /**
     * 统计不同状态和编辑状态的地块数量
     *
     * @param resourceStatus     地块状态
     * @param resourceEditStatus 地块编辑状态
     * @param regionCodes        行政区代码
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Long countByResourcesStatusAndEditStatus(int resourceStatus, int resourceEditStatus, Collection<String> regionCodes) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (regionCodes != null && !regionCodes.isEmpty()) {
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        }
        criterionList.add(Restrictions.eq("resourceEditStatus", resourceEditStatus));
        criterionList.add(Restrictions.eq("resourceStatus", resourceStatus));
        return count(criteria(criterionList));
    }

    /**
     * 成交总额统计
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal sumOfDeal(Collection<String> regionCodes) {
        Map params = Maps.newHashMap();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select sum(a.price) from (select max(t.offer_price) price from trans_resource_offer t " +
                " left join trans_resource t1 on t.resource_id = t1.resource_id " +
                " where t1.resource_status = 30 ");
        if (regionCodes != null && !regionCodes.isEmpty()) {
            sqlBuilder.append(" and t1.region_code in :regionCodes");
            params.put("regionCodes", regionCodes);
        }
        sqlBuilder.append(" group by t.resource_id) a");
        List<BigDecimal> results = sql(sqlBuilder.toString(), params).list();
        return (results != null && results.size() > 0) ? results.get(0) : BigDecimal.ZERO;
    }

    /**
     * 根据保证金截止时间查询
     *
     * @param startTime 查询起事件
     * @param endTime   查询至时间
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResource> findTransResourceByBzjEndTime(Date startTime, Date endTime) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (startTime != null) {
            criterionList.add(Restrictions.ge("bzjEndTime", startTime));
        }
        if (endTime != null) {
            criterionList.add(Restrictions.lt("bzjEndTime", endTime));
        }
        return list(criteria(criterionList));
    }

    /**
     * 根据挂牌截止时间查询
     *
     * @param startTime 查询起事件
     * @param endTime   查询至时间
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResource> findTransResourceByGpEndTime(Date startTime, Date endTime) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (startTime != null) {
            criterionList.add(Restrictions.ge("gpEndTime", startTime));
        }
        if (endTime != null) {
            criterionList.add(Restrictions.lt("gpEndTime", endTime));
        }
        return list(criteria(criterionList));
    }


    /**
     * 查询资源，分页,client用
     *
     * @param queryParam
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="ResourceQueryCache",key="'findTransResources'+#queryParam.hashCode+ #request.offset + #request.size+#regionCodes.hashCode()")*/
    public Page<TransResource> findTransResources(ResourceQueryParam queryParam, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.gt("resourceStatus", 0));

        if (queryParam.getResourceEditStatus() > 0) {
            criterionList.add(Restrictions.eq("resourceEditStatus", queryParam.getResourceEditStatus()));
        }
        if (queryParam.getGtResourceEditStatus() > 0) {
            criterionList.add(Restrictions.gt("resourceEditStatus", queryParam.getGtResourceEditStatus()));
        }
        if (StringUtils.isNotBlank(queryParam.getTitle())) {
            criterionList.add(Restrictions.like("resourceCode", queryParam.getTitle(), MatchMode.ANYWHERE));
        }
        if (queryParam.getResourceStatus() > 0) {
            criterionList.add(Restrictions.eq("resourceStatus", queryParam.getResourceStatus()));
        }
        if (queryParam.getGtResourceStatus() > 0) {
            criterionList.add(Restrictions.gt("resourceStatus", queryParam.getGtResourceStatus()));
        }

        if (queryParam.getDisplayStatus() >= 0) {
            criterionList.add(Restrictions.eq("displayStatus", queryParam.getDisplayStatus()));
        }

        if (StringUtils.isNotBlank(queryParam.getUseType())) {
            criterionList.add(Restrictions.eq("landUse", Constants.LandUse.valueOf(queryParam.getUseType())));
        }
        if (regionCodes != null && !regionCodes.isEmpty()) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        Criteria criteria = criteria(criterionList);

        int orderCrArea = 3;
        int orderCrAreaDesc = 4;
        int orderBeginOffer = 1;
        int orderBeginOfferDesc = 2;

        String orderBy = "";
        if (queryParam.getOrderBy() == orderBeginOffer) {
            orderBy = " beginOffer ";
            criteria.addOrder(Order.asc("beginOffer"));
        } else if (queryParam.getOrderBy() == orderBeginOfferDesc) {
            orderBy = " beginOffer  desc";
            criteria.addOrder(Order.desc("beginOffer"));
        } else if (queryParam.getOrderBy() == orderCrArea) {
            orderBy = " crArea ";
            criteria.addOrder(Order.asc("crArea"));
        } else if (queryParam.getOrderBy() == orderCrAreaDesc) {
            orderBy = " crArea  desc";
            criteria.addOrder(Order.desc("crArea"));
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "resourceStatus,resourceId desc";
            criteria.addOrder(Order.asc("resourceEditStatus"));
            criteria.addOrder(Order.asc("resourceStatus"));
            criteria.addOrder(Order.desc("resourceId"));
        }

        return find(criteria, request);
    }

    /**
     * 查询资源，分页,client用 当前用户参与地块
     * @param queryParam
     * @param regionCodes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findTransResourcesByUser(ResourceQueryParam queryParam, String userId, String regionCodes, Pageable request) {
        Map<String, Object> args = Maps.newHashMap();
        StringBuilder hql = new StringBuilder();
        hql.append("select tr from TransResource tr, TransResourceApply tra where tr.resourceId = tra.resourceId ");
        hql.append(" and tr.resourceStatus >:gtResourceStatus ");
        args.put("gtResourceStatus", 0);
        hql.append(" and tra.userId =:userId ");
        args.put("userId", userId);
        if (queryParam.getResourceEditStatus() > 0) {
            hql.append(" and tr.resourceEditStatus =:resourceEditStatus");
            args.put("resourceEditStatus", queryParam.getResourceEditStatus());
        }
        if (queryParam.getGtResourceEditStatus() > 0) {
            hql.append(" and tr.resourceEditStatus >:gtResourceEditStatus");
            args.put("gtResourceEditStatus", queryParam.getGtResourceEditStatus());
        }
        if (StringUtils.isNotBlank(queryParam.getTitle())) {
            hql.append(" and tr.resourceCode like:resourceCode");
            args.put("resourceCode", "%" + queryParam.getTitle() + "%");
        }
        if (queryParam.getResourceStatus() > 0) {
            hql.append(" and tr.resourceStatus =:resourceStatus");
            args.put("resourceStatus", queryParam.getResourceStatus());
        }
        if (queryParam.getGtResourceStatus() > 0) {
            hql.append(" and tr.resourceStatus >:gtResourceStatus");
            args.put("gtResourceStatus", queryParam.getGtResourceStatus());
        }
        if (queryParam.getDisplayStatus() >= 0) {
            hql.append(" and tr.displayStatus =:displayStatus");
            args.put("displayStatus", queryParam.getDisplayStatus());
        }
        if (StringUtils.isNotBlank(queryParam.getUseType())) {
            hql.append(" and tr.landUse =:landUse");
            args.put("landUse", Constants.LandUse.valueOf(queryParam.getUseType()));
        }
        if (regionCodes != null && !regionCodes.isEmpty()) {
            if (regionCodes.split(",").length > 1) {
                hql.append(" and tr.regionCode in (:regionCode)");
                args.put("regionCode", regionCodes.split(","));
            } else {
                hql.append(" and tr.regionCode like:regionCode");
                args.put("regionCode", regionCodes + "%");
            }
        }

        int orderCrArea = 1;
        int orderCrAreaDesc = 2;
        int orderBeginOffer = 3;
        int orderBeginOfferDesc = 4;

        String orderBy = "";
        if (queryParam.getOrderBy() == orderCrArea) {
            orderBy = " tr.crArea asc ";
        } else if (queryParam.getOrderBy() == orderCrAreaDesc) {
            orderBy = " tr.crArea desc ";
        } else if (queryParam.getOrderBy() == orderBeginOffer) {
            orderBy = " tr.beginOffer asc ";
        } else if (queryParam.getOrderBy() == orderBeginOfferDesc) {
            orderBy = " tr.beginOffer desc ";
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = " tr.resourceEditStatus asc, tr.resourceStatus asc, tr.resourceId desc ";
        }
        hql.append(" order by " + orderBy);
        return findByHql(hql.toString(), args, request);
    }

    /**
     * 查询资源 client用 当前用户 可以报价的地块
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResource> findResourcesForPriceByUser(String userId) {
        Map<String, Object> args = Maps.newHashMap();
        StringBuilder hql = new StringBuilder();
        hql.append("select tr from TransResource tr, TransResourceApply tra where tr.resourceId = tra.resourceId ");
        // 发布 或 中止地块
        hql.append(" and tr.resourceEditStatus in(:resourceEditStatus) ");
        args.put("resourceEditStatus", new Object[]{Constants.RESOURCE_EDIT_STATUS_RELEASE, Constants.RESOURCE_EDIT_STATUS_BREAK});
        // 正在竞价 或 挂牌 或最高限价
        hql.append(" and tr.resourceStatus in(:resourceStatus) ");
        args.put("resourceStatus", new Object[]{Constants.RESOURCE_STATUS_JING_JIA, Constants.RESOURCE_STATUS_GUA_PAI, Constants.RESOURCE_STATUS_MAX_OFFER});
        // 已缴纳保证金
        hql.append(" and tra.applyStep =:applyStep ");
        args.put("applyStep", Constants.STEP_OVER);
        // 当前用户
        hql.append(" and tra.userId =:userId ");
        args.put("userId", userId);
        return list(hql(hql.toString(), args));
    }

    /**
     * 获取可以显示在大屏幕上的地块
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="ResourceQueryCache",key="'findDisplayResource'+#title+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")*/
    public Page<TransResource> findDisplayResource(String title, String displayStatus, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.or(Restrictions.eq("resourceEditStatus", 2), Restrictions.eq("resourceEditStatus", 9)));
        if (StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(displayStatus) && !"-1".equals(displayStatus)) {
            criterionList.add(Restrictions.eq("displayStatus", Integer.valueOf(displayStatus)));
        }
        if (regionCodes != null && !regionCodes.isEmpty()) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        return find(criteria(criterionList).addOrder(Order.desc("beginOffer")), request);
    }

    /**
     * 查询资源、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
   /* @Cacheable(value="ResourceQueryCache",key="'findTransResourcesByEditStatus'+#title+#status+#ggId+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")*/
    public Page<TransResource> findTransResourcesByEditStatus(String title, int status, String ggId, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        }
        if (status > -1) {
            criterionList.add(Restrictions.eq("resourceEditStatus", status));
        }
        if (StringUtils.isNotBlank(ggId)) {
            criterionList.add(Restrictions.eq("ggId", ggId));
        }
        if (StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        return find(criteria(criterionList).addOrder(Order.desc("resourceId")), request);
    }

    /**
     * 查询用户所竞拍的地块列表
     *
     * @param userId  用户Id
     * @param status
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="ResourceQueryCache",key="'findTransResourcesByUser'+#userId+ #status +#regionCodes.hashCode()+ #request.offset + #request.size")*/
    public Page<TransResource> findTransResourcesByUser(String userId, int status, Collection<String> regionCodes, Pageable request) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> mapParam = Maps.newHashMap();
        sql.append("select t.* from Trans_Resource t left join trans_resource_apply t1 on t.resource_id = t1.resource_id where 1=1");
        if (status > -1) {
            sql.append(" and t.resource_edit_status=:status");
            mapParam.put("status", status);
        }
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" and t1.user_id = :userId ");
            mapParam.put("userId", userId);
        }
        if (regionCodes != null && !regionCodes.isEmpty()) {
            sql.append(" and t.region_code in :regionCodes");
            mapParam.put("regionCodes", regionCodes);
        }
        sql.append(" order by t.RESOURCE_ID desc");
        return findBySql(sql.toString(), mapParam, request);
    }

    /**
     * 获得正在交易的地块，成交和流拍的除外
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResource> getTransResourcesOnRelease() {
        org.hibernate.Query query = hql("from TransResource t where t.resourceEditStatus=" + Constants.RESOURCE_EDIT_STATUS_RELEASE + " order by t.gpBeginTime");
        return query.list();
    }

    /**
     * 统计未审核，报名中，已通过
     *
     * @param transResourcePage
     * @return
     */
    @Override
    @Transactional
    public Page<TransResource> countPassOrUnpass(Page<TransResource> transResourcePage) {
        for (TransResource transResource : transResourcePage.getItems()) {
            transResource.setUnpass(0);
            transResource.setPassed(0);
            transResource.setUnVerif(0);
            List<TransResourceApply> transResourceApplyList = transResourceApplyService.getTransResourceApplyByResourceId(transResource.getResourceId());
            for (TransResourceApply transResourceApply : transResourceApplyList) {
                if (transResourceApply.getApplyStep() == 2) {
                    transResource.setUnVerif(transResource.getUnVerif() + 1);
                } else if (transResourceApply.getApplyStep() == 3) {
                    transResource.setPassed(transResource.getPassed() + 1);
                } else if (transResourceApply.getApplyStep() == 4) {
                    transResource.setPassed(transResource.getPassed() + 1);
                } else if (transResourceApply.getApplyStep() == 1) {
                    transResource.setUnpass(transResource.getUnpass() + 1);
                }
            }
        }
        return transResourcePage;
    }


    /**
     * 根据地块号获得资源
     *
     * @param transResourceCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransResource getResourceByCode(String transResourceCode) {
        return StringUtils.isNotEmpty(transResourceCode) ? get(criteria(Restrictions.eq("resourceCode", transResourceCode))) : null;
    }

    /**
     * 查询摇号资源、分页，Admin用
     *
     * @param title
     * @param status
     * @param ggId
     * @param regionCodes
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findYhResourcesByEditStatus(String title, int status, String ggId, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        //地块编号resourceCode
        if (StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        }
        if (status>-1) {
            criterionList.add(Restrictions.eq("resourceEditStatus", status));
        }
        if(status == Constants.RESOURCE_EDIT_STATUS_OVER) {
            criterionList.add(Restrictions.eq("successOfferChoose", Constants.SUCCESS_OFFER_CHOOSE.YH));
        }
        //存在最高限价
            criterionList.add(Restrictions.eq("maxOfferExist", 1));
        if (StringUtils.isNotBlank(ggId)) {
            criterionList.add(Restrictions.eq("ggId", ggId));
        }
        if (StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        //最高报价处理方式
        criterionList.add(Restrictions.eq("maxOfferChoose",Constants.MaxOfferChoose.YH));
        Date now = new Date();
        //在限时竞价开始时间之后
        criterionList.add(Restrictions.lt("xsBeginTime",now));
        return find(criteria(criterionList).addOrder(Order.desc("resourceId")), request);
    }



    /**
     * 查询一次报价地块列表、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findYCBJResourcesByEditStatus(String title, int status, String ggId, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        }
        if (status > -1) {
            criterionList.add(Restrictions.eq("resourceEditStatus", status));
        }
        //存在最高限价
        criterionList.add(Restrictions.eq("maxOfferExist", 1));
//        if (StringUtils.isNotBlank(ggId))
//            criterionList.add(Restrictions.eq("ggId", ggId));
//        if (StringUtils.isNotBlank(regionCodes)) {
//            if (regionCodes.split(",").length > 1) {
//                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
//            } else {
//                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
//            }
//        }
        //最高报价处理方式
        criterionList.add(Restrictions.eq("maxOfferChoose",Constants.MaxOfferChoose.YCBJ));
        return find(criteria(criterionList).addOrder(Order.desc("resourceId")), request);
    }


    /**
     * 查询已成交资源、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findDealTransResourcesByEditStatus(String title, int status, int resourceStatus, String ggId, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        }
        if (status > -1) {
            criterionList.add(Restrictions.eq("resourceEditStatus", status));
        }
        if (StringUtils.isNotBlank(ggId)) {
            criterionList.add(Restrictions.eq("ggId", ggId));
        }
        if (resourceStatus > -1) {
            criterionList.add(Restrictions.eq("resourceStatus", resourceStatus));
        }
        if (StringUtils.isNotEmpty(regionCodes)) {
            criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
        }
        return find(criteria(criterionList).addOrder(Order.desc("resourceId")), request);
    }

}
