package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.TransResourceOfferService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 出让地块报价服务
 * Created by jiff on 14/12/21.
 */
public class TransResourceOfferServiceImpl extends HibernateRepo<TransResourceOffer, String>
        implements TransResourceOfferService {

    /**
     * 根据offerId获得报价
     * @param offerId 报价对象Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="TransResourceOfferServiceImplCache",key="'getTransResourceOffer'+#offerId")*/
    public TransResourceOffer getTransResourceOffer(String offerId) {
        return get(offerId);
    }

    /**
     * 增加报价
     * @param offer 报价对象
     * @return
     */

    @Override
    @Transactional
   /* @Caching(evict={
            @CacheEvict(value="TransResourceOfferServiceImplCache",allEntries = true),
            @CacheEvict(value="TransResourceOfferServicePageCache", allEntries = true)
    })*/
    public TransResourceOffer addTransResourceOffer(TransResourceOffer offer) {
        return save(offer);
    }

    /**
     * 获得一个资源所有的报价
     * @param resourceId 出让地块Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="TransResourceOfferServiceImplCache",key="'getOfferListByResource'+#resourceId")*/
    public List<TransResourceOffer> getOfferListByResource(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId order by t.offerTime desc");
        query.setString("resourceId",resourceId);
        return query.list();
    }

    /**
     * 获得一个资源所有的报价,每个用户报价不重复
     *
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
   /* @Cacheable(value="TransResourceOfferServiceImplCache",key="'getOfferListByResource2Alone'+#resourceId")*/
    public List<String> getUserIdListByResource2Alone(String resourceId) {
        return sql("select distinct t1.user_id from trans_resource_offer t1 where t1.resource_id='"+resourceId+"'").list();
    }

    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="TransResourceOfferServicePageCache")*/
    public Page<TransResourceOffer> getResourceOfferPage(String resourceId,Pageable request) {
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("resourceId", resourceId);
        String hql = "from TransResourceOffer t where t.resourceId=:resourceId  order by t.offerTime desc";
        return findByHql(hql, mapParam, request);
    }

    /**
     * 获得最新的报价，time：-1表示所有的报价,去掉一次报价
     *
     * @param resourceId
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResourceOffer> getResourceOfferPage2(String resourceId, Pageable request) {
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("resourceId", resourceId);
        mapParam.put("offerType",-1);
        String hql = "from TransResourceOffer t where t.resourceId=:resourceId and t.offerType!=:offerType order by t.offerTime desc";
        return findByHql(hql, mapParam, request);
    }

    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="TransResourceOfferServicePageCache",key="'getResourceOfferPageByUserId'+#resourceId +#userId+','+#request.size +',' + #request.offset")*/
    public Page<TransResourceOffer> getResourceOfferPageByUserId(String resourceId,String userId,Pageable request){
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("resourceId", resourceId);
        mapParam.put("userId", userId);
        String hql = "from TransResourceOffer t where t.resourceId=:resourceId and userId=:userId order by t.offerTime desc";
        return findByHql(hql, mapParam, request);
    }
    /**
     * 是否多人同意进行限时竞价
     * @param resourceId 出让地块Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean enterjjProcess(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId and offerPrice<0");
        query.setString("resourceId",resourceId);
        List<TransResourceOffer> offerList= query.list();
        return offerList.size()>1;
    }


    /**
     * 判断最高价(第一个出价的)
     * @param resourceId 出让地块Id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    /*@Cacheable(value="TransResourceOfferServiceImplCache",key="'getMaxOffer'+#resourceId")*/
    public TransResourceOffer getMaxOffer(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId  order by offerTime desc ");
        query.setString("resourceId",resourceId);
        List<TransResourceOffer> offerList= query.setMaxResults(1).list();
        return (offerList.size()>0) ? offerList.get(0):null;
    }

    @Transactional(readOnly = true)
    @Override
    /*@Cacheable(value="TransResourceOfferServiceImplCache",key="'getMaxOfferFormPrice'+#resourceId")*/
    public TransResourceOffer getMaxOfferFormPrice(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId and offerType<2  order by offerPrice desc ");
        query.setString("resourceId",resourceId);
        List<TransResourceOffer> offerList= query.setMaxResults(1).list();
        return (offerList.size()>0) ? offerList.get(0):null;
    }
    /**
     * 获取地块的报价次数
     *
     * @param resourceId 出让地块Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
   /* @Cacheable(value="TransResourceOfferServiceImplCache",key="'getResourceOfferFrequency'+#resourceId")*/
    public Long getResourceOfferFrequency(String resourceId) {
        return count(criteria(Restrictions.eq("resourceId", resourceId)));
    }

    /**
     * 获取当前用户对当前地块已经报价的次数
     *
     * @param resourceId 出让地块Id
     * @param userId     当前用户Id
     * @return 第几次竞价
     */
    @Override
    @Transactional(readOnly = true)
    public int getUserResourceOfferCount(String resourceId, String userId) {
        List<Criterion> criterionList= Lists.newArrayList();
       /* if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("userId",userId));
        }*/
        if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }

        int offerCount= list(criteria(criterionList)).size()+1;
        return offerCount;
    }

    /**
     * 获取地块和用户报价，确定此地块是否已经报过相同价格
     *
     * @param resourceId 出让地块Id
     * @param offerPrice 用户报价offer
     * @return
     */
    @Override
    @Transactional
    public TransResourceOffer getOnlyPriceOffer(String resourceId, Double offerPrice) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("resourceId",resourceId));
        }
        if(null!=offerPrice){
            criterionList.add(Restrictions.eq("offerPrice",offerPrice));
        }
        return get(criteria(criterionList));
    }
}
