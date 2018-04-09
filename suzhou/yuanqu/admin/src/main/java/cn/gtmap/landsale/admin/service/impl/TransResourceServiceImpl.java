package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.core.TransResourceContainer;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.TransCrggService;
import cn.gtmap.landsale.service.TransFileService;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易地块服务
 * Created by jiff on 14/12/21.
 */
public class TransResourceServiceImpl extends HibernateRepo<TransResource, String> implements TransResourceService, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Autowired
    TransFileService transFileService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransCrggService transCrggService;


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
    /*@Cacheable(value = "ResourceCache", key = "'getTransResource'+#resourceId")*/
    public TransResource getTransResource(String resourceId) {
        return get(resourceId);
    }

    /**
     * 根据公告Id获取公告地块
     *
     * @param ggId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "ResourceCache", key = "'findTransResource'+#ggId")*/
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
    @Transactional
  /*  @Caching(evict = {
            @CacheEvict(value = "ResourceCache", allEntries = true),
            @CacheEvict(value = "ResourceQueryCache", allEntries = true)
    })*/
    public TransResource saveTransResource(TransResource resource) {
        TransResource transResource = saveOrUpdate(resource);
        try {
            //检查资源线程
            TransResourceContainer transResourceTimer = (TransResourceContainer) applicationContext.getBean("TransResourceContainer");
            transResourceTimer.checkResource(transResource);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return transResource;
    }

    @Override
    @Transactional
    /*@Caching(evict = {
            @CacheEvict(value = "ResourceCache", allEntries = true),
            @CacheEvict(value = "ResourceQueryCache", allEntries = true)
    })*/
    public TransResource saveTransResourceStatus(TransResource resource, int status) {
        resource.setResourceStatus(status);
        return saveOrUpdate(resource);
    }

    /**
     * 根据地块Id删除地块对象
     *
     * @param resourceIds
     */
    @Override
    @Transactional
   /* @Caching(evict = {
            @CacheEvict(value = "ResourceCache", allEntries = true),
            @CacheEvict(value = "ResourceQueryCache", allEntries = true)
    })*/
    public void deleteTransResource(Collection<String> resourceIds) {
        transFileService.deleteFilesByKey(resourceIds);
        deleteByIds(resourceIds);
    }

    /**
     * 根据公告Id删除地块对象
     *
     * @param ggIds
     */
    @Override
    @Transactional
    /*@Caching(evict = {
            @CacheEvict(value = "ResourceCache", allEntries = true),
            @CacheEvict(value = "ResourceQueryCache", allEntries = true)
    })*/
    public void deleteTransResourceByGgId(Collection<String> ggIds) {
        Iterator<String> iterator = ggIds.iterator();
        while (iterator.hasNext()) {
            List<TransResource> transResourceList = findTransResource(iterator.next());
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
    @Transactional
    /*@Caching(evict = {
            @CacheEvict(value = "ResourceCache", allEntries = true),
            @CacheEvict(value = "ResourceQueryCache", allEntries = true)

    })*/
    public void updateTransResourceDisplayStatus(String resourceId, int displayStatus) {
        TransResource transResource = getTransResource(resourceId);
        if (transResource != null) {
            transResource.setDisplayStatus(displayStatus);
            saveOrUpdate(transResource);
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
    /*@Cacheable(value = "ResourceCache", key = "'findTransResourcesByDisplayStatus'+#displayStatus")*/
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
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
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
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
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
        if (startTime != null)
            criterionList.add(Restrictions.ge("bzjEndTime", startTime));
        if (endTime != null)
            criterionList.add(Restrictions.lt("bzjEndTime", endTime));
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
        if (startTime != null)
            criterionList.add(Restrictions.ge("gpEndTime", startTime));
        if (endTime != null)
            criterionList.add(Restrictions.lt("gpEndTime", endTime));
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
    /*@Cacheable(value = "ResourceQueryCache", key = "'findTransResources'+#queryParam.hashCode+ #request.offset + #request.size+#regionCodes.hashCode()")*/
    public Page<TransResource> findTransResources(ResourceQueryParam queryParam, Collection<String> regionCodes, Pageable request) {
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
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));


        Criteria criteria = criteria(criterionList);

        String orderBy = "";
        if (queryParam.getOrderBy() == 1) {
            orderBy = " crArea ";
            criteria.addOrder(Order.asc("crArea"));
        } else if (queryParam.getOrderBy() == 2) {
            orderBy = " crArea  desc";
            criteria.addOrder(Order.desc("crArea"));
        } else if (queryParam.getOrderBy() == 3) {
            orderBy = " beginOffer ";
            criteria.addOrder(Order.asc("beginOffer"));
        } else if (queryParam.getOrderBy() == 4) {
            orderBy = " beginOffer  desc";
            criteria.addOrder(Order.desc("beginOffer"));
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "resourceStatus,resourceId desc";
            criteria.addOrder(Order.asc("resourceStatus"));
            criteria.addOrder(Order.desc("resourceId"));
        }

        return find(criteria, request);
    }

    /**
     * 查询资源，分页,依据公告id
     *
     * @param queryParam
     * @param regionCodes
     * @param ggIds
     * @param request     @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "ResourceQueryCache", key = "'findTransResources'+#queryParam.hashCode+ #request.offset + #request.size+#regionCodes.hashCode()+#ggIds.hashCode()")*/
    public Page<TransResource> findTransResourcesByCrggIds(ResourceQueryParam queryParam, Collection<String> regionCodes, Collection<String> ggIds, Pageable request) {
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
        if (StringUtils.isNotBlank(queryParam.getTdyt())) {
            if("320503001".equalsIgnoreCase(queryParam.getGgLx())&&"12".equals(queryParam.getTdyt())){
                criterionList.add(Restrictions.gt("landUseMuli", "08"));
            }else{
                criterionList.add(Restrictions.like("landUseMuli", queryParam.getTdyt(), MatchMode.START));
            }
        }
        if (StringUtils.isNotBlank(queryParam.getUseType())) {
            String[] param = queryParam.getUseType().split(",");
            if(param.length==2){
                criterionList.add(Restrictions.or(Restrictions.eq("landUse",Constants.LandUse.valueOf(param[0])),Restrictions.eq("landUse",Constants.LandUse.valueOf(param[1]))));
            }else{
                criterionList.add(Restrictions.eq("landUse", Constants.LandUse.valueOf(queryParam.getUseType())));
            }
        }
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));

        if (ggIds != null && !ggIds.isEmpty()) {
            criterionList.add(Restrictions.in("ggId", ggIds));
        } else {
            criterionList.add(Restrictions.isNull("ggId"));
        }


        Criteria criteria = criteria(criterionList);

        String orderBy = "";
        if (queryParam.getOrderBy() == 1) {
            orderBy = " crArea ";
            criteria.addOrder(Order.asc("crArea"));
        } else if (queryParam.getOrderBy() == 2) {
            orderBy = " crArea  desc";
            criteria.addOrder(Order.desc("crArea"));
        } else if (queryParam.getOrderBy() == 3) {
            orderBy = " beginOffer ";
            criteria.addOrder(Order.asc("beginOffer"));
        } else if (queryParam.getOrderBy() == 4) {
            orderBy = " beginOffer  desc";
            criteria.addOrder(Order.desc("beginOffer"));
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "resourceStatus,resourceId desc";
            criteria.addOrder(Order.asc("resourceStatus"));
            criteria.addOrder(Order.desc("resourceId"));
        }

        return find(criteria, request);
    }

    /**
     * 获取可以显示在大屏幕上的地块
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "ResourceQueryCache", key = "'findDisplayResource'+#title+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")*/
    public Page<TransResource> findDisplayResource(String title, String displayStatus, Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.or(Restrictions.eq("resourceEditStatus", 2), Restrictions.eq("resourceEditStatus", 9)));
        if (StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        if (StringUtils.isNotBlank(displayStatus) && !displayStatus.equals("-1"))
            criterionList.add(Restrictions.eq("displayStatus", Integer.valueOf(displayStatus)));
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        return find(criteria(criterionList).addOrder(Order.desc("beginOffer")), request);
    }

    /**
     * 获取正在挂牌，已结束的地块
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findResource(String title, Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.or(Restrictions.eq("resourceEditStatus", 2), Restrictions.eq("resourceEditStatus", 9)));
        if (StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        return find(criteria(criterionList).addOrder(Order.asc("resourceEditStatus")), request);
    }


    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "ResourceQueryCache", key = "'findTransResourcesByEditStatus'+#title+#status+#ggId+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")*/
    public Page<TransResource> findTransResourcesByEditStatus(String title, int status, String ggId, Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        if (status > -1)
            criterionList.add(Restrictions.eq("resourceEditStatus", status));
        if (StringUtils.isNotBlank(ggId))
            criterionList.add(Restrictions.eq("ggId", ggId));
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
        return find(criteria(criterionList).addOrder(Order.desc("resourceId")), request);
    }

    /**
     * 统计未审核，报名中，已通过
     *
     * @param transResourcePage
     * @return
     */
    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "ResourceQueryCache", key = "'findTransResourcesByEditStatus'+#title+#status+#resourceStatus+#ggId+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")*/
    public Page<TransResource> findDealTransResourcesByEditStatus(String title, int status, int resourceStatus, String ggId, Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
        if (status > -1)
            criterionList.add(Restrictions.eq("resourceEditStatus", status));
        if (StringUtils.isNotBlank(ggId))
            criterionList.add(Restrictions.eq("ggId", ggId));
        if (resourceStatus > -1)
            criterionList.add(Restrictions.eq("resourceStatus", resourceStatus));
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));
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
    //@Cacheable(value="ResourceQueryCache",key="'findTransResourcesByUser'+#userId+ #status +#regionCodes.hashCode()+ #request.offset + #request.size")
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
        org.hibernate.Query query = hql("from TransResource t where t.resourceEditStatus=" + Constants.ResourceEditStatusRelease + " order by t.gpBeginTime");
        return query.list();
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
        String hql = "from TransResource t where t.resourceCode=:resourceCode";
        Query query = getSession().createQuery(hql);
        query.setString("resourceCode", transResourceCode);
        if (query.list().size() > 0)
            return (TransResource) query.list().get(0);
        else {
            return null;
        }
    }

    /**
     * 根据条件查找已成交的地块 view用
     *
     * @param resourceCode
     * @param resourceLocation
     * @param resourcePurpose
     * @param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "OnlineQueryCache")*/
    public Page<TransResource> findDealResourceByCondiction(String resourceCode, String resourceLocation, String resourcePurpose, String beginTime, String endTime, Pageable request) {
        Map<String, Object> mapParam = Maps.newHashMap();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        StringBuffer sql = new StringBuffer();
        sql.append("select t1.* from trans_resource t1 , trans_resource_offer t2  where t1.offer_id = t2.offer_id and 1=1");
        if (StringUtils.isNotBlank(resourceCode)) {
            sql.append(" and resource_code like :resourceCode ");
            mapParam.put("resourceCode", "%" + resourceCode + "%");
        }
        if (StringUtils.isNotBlank(resourceLocation)) {
            sql.append(" and resource_location like :resourceLocation ");
            mapParam.put("resourceLocation", "%" + resourceLocation + "%");
        }
        if (StringUtils.isNotBlank(resourcePurpose)) {
            sql.append(" and land_use = :resourcePurpose ");
            mapParam.put("resourcePurpose", resourcePurpose);
        }
        if (StringUtils.isNotBlank(beginTime) && StringUtils.isBlank(endTime)) {
            try {
                Date beginDate = date.parse(beginTime);
                long beginLongTime = beginDate.getTime();
                sql.append(" and offer_time >= :beginLongTime ");
                mapParam.put("beginLongTime", beginLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(endTime) && StringUtils.isBlank(beginTime)) {
            try {
                Date endDate = date.parse(endTime);
                long endLongTime = endDate.getTime();
                sql.append(" and offer_time <= :endLongTime ");
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(beginTime)) {
            try {
                SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date beginDate = date1.parse(beginTime+" 00:00:00");
                Date endDate = date1.parse(endTime+" 23:59:59");
                long beginLongTime = beginDate.getTime();
                long endLongTime = endDate.getTime();
                sql.append(" and offer_time between :beginLongTime ");
                sql.append(" and :endLongTime ");
                mapParam.put("beginLongTime", beginLongTime);
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        sql.append(" and resource_status = 30 ");
        sql.append(" order by offer_time desc ");
        return findBySql(sql.toString(), mapParam, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResource> findDealResourceByCondiction2List(String resourceCode, String resourceLocation, String resourcePurpose, String beginTime, String endTime, Pageable request) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> mapParam = Maps.newHashMap();
        String sql = "select t1.* from trans_resource t1 , trans_resource_offer t2  where t1.offer_id = t2.offer_id and 1=1 ";
        Query query = getSession().createSQLQuery(sql).addEntity(TransResource.class);
        if (StringUtils.isNotBlank(resourceCode)) {
            sql += " and resource_code like :resourceCode ";
            mapParam.put("resourceCode", "%" + resourceCode + "%");
        }
        if (StringUtils.isNotBlank(resourceLocation)) {
            sql += " and resource_location like :resourceLocation ";
            mapParam.put("resourceLocation", "%" + resourceLocation + "%");
        }
        if (StringUtils.isNotBlank(resourcePurpose)) {
            sql += " and land_use = :resourcePurpose ";
            mapParam.put("resourcePurpose", resourcePurpose);
        }
        if (StringUtils.isNotBlank(beginTime)) {
            try {
                Date beginDate = date.parse(beginTime);
                long beginLongTime = beginDate.getTime();
                sql += " and offer_time >= :beginLongTime ";
                mapParam.put("beginLongTime", beginLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            try {
                Date endDate = date.parse(endTime);
                long endLongTime = endDate.getTime();
                sql += " and offer_time <= :endLongTime ";
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sql += " and resource_status = 30 ";
        sql += " order by t2.offer_time desc ";
        return sql(sql, mapParam).addEntity(TransResource.class).list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findDealResourceByCondiction2ResourceIds(String resourceCode, String resourceLocation, String resourcePurpose, String beginTime, String endTime, Pageable request, String zdCode) {
        Map<String, Object> mapParam = Maps.newHashMap();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuffer sql = new StringBuffer();
        sql.append("select t1.* from trans_resource t1 , trans_resource_offer t2  where t1.offer_id = t2.offer_id and 1=1");
        if (StringUtils.isNotBlank(resourceCode)) {
            sql.append(" and resource_code like :resourceCode ");
            mapParam.put("resourceCode", "%" + resourceCode + "%");
        }

        if (StringUtils.isNotBlank(zdCode)) {
            sql.append(" and t1.resource_id in (select distinct t.resource_id from trans_resource_son t where t.zd_code like'%" + zdCode + "%') ");


        }

        if (StringUtils.isNotBlank(resourceLocation)) {
            sql.append(" and resource_location like :resourceLocation ");
            mapParam.put("resourceLocation", "%" + resourceLocation + "%");
        }
        if (StringUtils.isNotBlank(resourcePurpose)) {
            sql.append(" and land_use = :resourcePurpose ");
            mapParam.put("resourcePurpose", resourcePurpose);
        }
        if (StringUtils.isNotBlank(beginTime)) {
            try {
                Date beginDate = date.parse(beginTime);
                long beginLongTime = beginDate.getTime();
                sql.append(" and offer_time >= :beginLongTime ");
                mapParam.put("beginLongTime", beginLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            try {
                Date endDate = date.parse(endTime);
                long endLongTime = endDate.getTime();
                sql.append(" and offer_time <= :endLongTime ");
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sql.append(" and resource_status = 30 ");
        sql.append(" order by t2.offer_time desc ");
        return findBySql(sql.toString(), mapParam, request);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransResource> findDealResourceByCondiction2ResourceIds2(String resourceCode, String resourceLocation, String resourcePurpose, String beginTime, String endTime, Pageable request, String zdCode) {
        Map<String, Object> mapParam = Maps.newHashMap();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        StringBuffer sql = new StringBuffer();
        sql.append("select t1.* from trans_resource t1 , trans_resource_offer t2  where t1.offer_id = t2.offer_id and 1=1");
        if (StringUtils.isNotBlank(resourceCode)) {
            sql.append(" and resource_code like :resourceCode ");
            mapParam.put("resourceCode", "%" + resourceCode + "%");
        }

        if (StringUtils.isNotBlank(zdCode)) {
            sql.append(" and t1.resource_id in (select distinct t.resource_id from trans_resource_son t where t.zd_code like'%" + zdCode + "%') ");


        }

        if (StringUtils.isNotBlank(resourceLocation)) {
            sql.append(" and resource_location like :resourceLocation ");
            mapParam.put("resourceLocation", "%" + resourceLocation + "%");
        }
        if (StringUtils.isNotBlank(resourcePurpose)) {
            sql.append(" and land_use = :resourcePurpose ");
            mapParam.put("resourcePurpose", resourcePurpose);
        }
        if (StringUtils.isNotBlank(beginTime)) {
            try {
                Date beginDate = date.parse(beginTime);
                long beginLongTime = beginDate.getTime();
                sql.append(" and offer_time >= :beginLongTime ");
                mapParam.put("beginLongTime", beginLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            try {
                Date endDate = date.parse(endTime);
                long endLongTime = endDate.getTime();
                sql.append(" and offer_time <= :endLongTime ");
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotBlank(endTime) && StringUtils.isNotBlank(beginTime)) {
            try {
                SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date beginDate = date1.parse(beginTime+" 00:00:00");
                Date endDate = date1.parse(endTime+" 23:59:59");
                long beginLongTime = beginDate.getTime();
                long endLongTime = endDate.getTime();
                sql.append(" and offer_time between :beginLongTime ");
                sql.append(" and :endLongTime ");
                mapParam.put("beginLongTime", beginLongTime);
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        sql.append(" and resource_status = 30 ");
        sql.append(" order by t2.offer_time desc ");
        return findBySql(sql.toString(), mapParam, request);
    }



    @Override
    @Transactional(readOnly = true)
    public List<TransResource> findDealResourceByCondiction2ResourceIds2List(String resourceCode, String resourceLocation, String resourcePurpose, String beginTime, String endTime, Pageable request, String zdCode) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> mapParam = Maps.newHashMap();
        String sql = " select t1.* from trans_resource t1 , trans_resource_offer t2  where t1.offer_id = t2.offer_id and 1=1 ";
        Query query = getSession().createSQLQuery(sql).addEntity(TransResource.class);
        if (StringUtils.isNotBlank(resourceCode)) {
            sql += " and resource_code like :resourceCode ";
            mapParam.put("resourceCode", "%" + resourceCode + "%");
        }

        if (StringUtils.isNotBlank(zdCode)) {
            sql += " and t1.resource_id in (select distinct t.resource_id from trans_resource_son t where t.zd_code like'%" + zdCode + "%') ";


        }

        if (StringUtils.isNotBlank(resourceLocation)) {
            sql += " and resource_location like :resourceLocation ";
            mapParam.put("resourceLocation", "%" + resourceLocation + "%");
        }
        if (StringUtils.isNotBlank(resourcePurpose)) {
            sql += " and land_use = :resourcePurpose ";
            mapParam.put("resourcePurpose", resourcePurpose);
        }
        if (StringUtils.isNotBlank(beginTime)) {
            try {
                Date beginDate = date.parse(beginTime);
                long beginLongTime = beginDate.getTime();
                sql += " and offer_time >= :beginLongTime ";
                mapParam.put("beginLongTime", beginLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.isNotBlank(endTime)) {
            try {
                Date endDate = date.parse(endTime);
                long endLongTime = endDate.getTime();
                sql += " and offer_time <= :endLongTime ";
                mapParam.put("endLongTime", endLongTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sql += " and resource_status = 30 ";
        sql += " order by t2.offer_time desc ";
        return sql(sql, mapParam).addEntity(TransResource.class).list();
    }

    /**
     * 查询勾选了底价的地块
     *
     * @param queryParam
     * @param regionCodes
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
   /* @Cacheable(value = "ResourceQueryCache", key = "'findTransResourcesByMinOffer'+#queryParam.hashCode()+ #request.offset + #request.size+#regionCodes.hashCode()")*/
    public Page<TransResource> findTransResourcesByMinOffer(ResourceQueryParam queryParam, Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.gt("resourceStatus", 0));
        criterionList.add(Restrictions.eq("minOffer", true));
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
        if (regionCodes != null && !regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode", regionCodes));

        Criteria criteria = criteria(criterionList);

        String orderBy = "";
        if (queryParam.getOrderBy() == 1) {
            orderBy = " crArea ";
            criteria.addOrder(Order.asc("crArea"));
        } else if (queryParam.getOrderBy() == 2) {
            orderBy = " crArea  desc";
            criteria.addOrder(Order.desc("crArea"));
        } else if (queryParam.getOrderBy() == 3) {
            orderBy = " beginOffer ";
            criteria.addOrder(Order.asc("beginOffer"));
        } else if (queryParam.getOrderBy() == 4) {
            orderBy = " beginOffer  desc";
            criteria.addOrder(Order.desc("beginOffer"));
        }
        if (StringUtils.isBlank(orderBy)) {
            orderBy = "resourceStatus,resourceId desc";
            criteria.addOrder(Order.asc("resourceStatus"));
            criteria.addOrder(Order.desc("resourceId"));
        }

        return find(criteria, request);
    }
}
