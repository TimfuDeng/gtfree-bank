package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.core.TransResourceContainer;
import cn.gtmap.landsale.core.TransResourceTimer;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.TransFileService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.types.Predicate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 交易地块服务
 * Created by jiff on 14/12/21.
 */
public class TransResourceServiceImpl extends HibernateRepo<TransResource, String> implements TransResourceService, ApplicationContextAware {

	ApplicationContext applicationContext;

	@Autowired
	TransFileService transFileService;

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
	//@Cacheable(value = "ResourceCache", key = "'getTransResource'+#resourceId")
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
	//@Cacheable(value = "ResourceCache", key = "'findTransResource'+#ggId")
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
	//@Caching(evict = {
			//@CacheEvict(value = "ResourceCache", allEntries = true),
			//@CacheEvict(value = "ResourceQueryCache", allEntries = true)
	//})
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
	//@Caching(evict = {
			//@CacheEvict(value = "ResourceCache", allEntries = true),
			//@CacheEvict(value = "ResourceQueryCache", allEntries = true)
	//})
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
	//@Caching(evict = {
			//@CacheEvict(value = "ResourceCache", allEntries = true),
			//@CacheEvict(value = "ResourceQueryCache", allEntries = true)
	//})
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
	//@Caching(evict = {
			//@CacheEvict(value = "ResourceCache", allEntries = true),
			//@CacheEvict(value = "ResourceQueryCache", allEntries = true)
	//})
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
	//@Caching(evict = {
			//@CacheEvict(value = "ResourceCache", allEntries = true),
			//@CacheEvict(value = "ResourceQueryCache", allEntries = true)

	//})
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
	//@Cacheable(value = "ResourceCache", key = "'findTransResourcesByDisplayStatus'+#displayStatus")
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
	//@Cacheable(value = "ResourceQueryCache", key = "'findTransResources'+#queryParam.hashCode+ #request.offset + #request.size+#regionCodes.hashCode()")
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
		} else if (queryParam.getOrderBy() == 5) {
			orderBy = " gpEndTime  desc";
			criteria.addOrder(Order.desc("gpEndTime"));
		}
		if (StringUtils.isBlank(orderBy)) {
			orderBy = "resourceStatus,resourceId desc";
			criteria.addOrder(Order.asc("resourceStatus"));
			criteria.addOrder(Order.desc("resourceCode"));
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
	//@Cacheable(value = "ResourceQueryCache", key = "'findDisplayResource'+#title+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")
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


	@Override
	@Transactional(readOnly = true)
	//@Cacheable(value = "ResourceQueryCache", key = "'findTransResourcesByEditStatus'+#title+#status+#ggId+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")
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

	@Override
	@Transactional(readOnly = true)
	//@Cacheable(value = "ResourceQueryCache", key = "'findTransResourcesByEditStatusAndType'+#queryType+#title+#status+#ggId+ #request.offset + #request.size + #displayStatus+#regionCodes.hashCode()")
	public Page<TransResource> findTransResourcesByEditStatusAndType(String queryType, String title, int status, String ggId, Collection<String> regionCodes, Pageable request) {
		List<Criterion> criterionList = Lists.newArrayList();
		if (StringUtils.isNotBlank(title)) {
			if ("mianji".equals(queryType)) {
				try {
					criterionList.add(Restrictions.eq("crArea", Double.parseDouble(title)));
				} catch (Exception e) {
					//e.printStackTrace();
				}
			} else if ("weizhi".equals(queryType)) {
				criterionList.add(Restrictions.like("resourceLocation", title, MatchMode.ANYWHERE));
			} else {
				criterionList.add(Restrictions.like("resourceCode", title, MatchMode.ANYWHERE));
			}
		}
		if (status > -1)
			criterionList.add(Restrictions.eq("resourceEditStatus", status));
		if (StringUtils.isNotBlank(ggId))
			criterionList.add(Restrictions.eq("ggId", ggId));
		if (regionCodes != null && !regionCodes.isEmpty())
			criterionList.add(Restrictions.in("regionCode", regionCodes));
		return find(criteria(criterionList).addOrder(Order.desc("gpEndTime")), request);
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
	//@Cacheable(value = "ResourceQueryCache", key = "'findTransResourcesByUser'+#userId+ #status +#regionCodes.hashCode()+ #request.offset + #request.size")
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
}
