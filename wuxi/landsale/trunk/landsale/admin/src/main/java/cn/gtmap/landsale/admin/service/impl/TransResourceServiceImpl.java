package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransResourceService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 底价管理
 * @author cxm
 * @version v1.0, 2017/11/06
 */
@Service
public class TransResourceServiceImpl extends HibernateRepo<TransResource, String> implements TransResourceService {

    /**
     * 查询勾选了底价的地块
     *
     * @param queryParam
     * @param regionCodes
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Page<TransResource> findTransResourcesByMinOffer(ResourceQueryParam queryParam, String regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("minOffer", 1));
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

        if(StringUtils.isNotBlank(queryParam.getTdytDictCodes())) {
            criterionList.add(Restrictions.in("tdyt", queryParam.getTdytDictCodes().split(",")));
        }

        if (StringUtils.isNotBlank(queryParam.getRegionCode())) {
            criterionList.add(Restrictions.eq("regionCode", queryParam.getRegionCode()));
        }


        Criteria criteria = criteria(criterionList);

        int orderCrArea = 1;
        int orderCrAreaDesc = 2;
        int orderBeginOffer = 3;
        int orderBeginOfferDesc = 4;

        String orderBy = "";
        if (queryParam.getOrderBy() == orderCrArea) {
            orderBy = " crArea ";
            criteria.addOrder(Order.asc("crArea"));
        } else if (queryParam.getOrderBy() == orderCrAreaDesc) {
            orderBy = " crArea  desc";
            criteria.addOrder(Order.desc("crArea"));
        } else if (queryParam.getOrderBy() == orderBeginOffer) {
            orderBy = " beginOffer ";
            criteria.addOrder(Order.asc("beginOffer"));
        } else if (queryParam.getOrderBy() == orderBeginOfferDesc) {
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
     * 获得正在交易的地块，成交和流拍的除外
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public List<TransResource> getTransResourcesOnRelease() {
        org.hibernate.Query query = hql("from TransResource t where t.resourceEditStatus=" + Constants.RESOURCE_EDIT_STATUS_RELEASE + " order by t.gpBeginTime");
        return query.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResource> saveTransResourceStatus(TransResource resource, int status) {
        resource.setResourceStatus(status);
        return new ResponseMessage(true,saveOrUpdate(resource));
    }
}
