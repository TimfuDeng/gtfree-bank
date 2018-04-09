package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.core.service.TransResourceOfferService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 出让地块报价服务
 * Created by jiff on 14/12/21.
 */
@Service
public class TransResourceOfferServiceImpl extends HibernateRepo<TransResourceOffer, String>
        implements TransResourceOfferService {

    /**
     * 根据offerId获得报价
     * @param offerId 报价对象Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
 /*   @Cacheable(value="TransResourceOfferServiceImplCache",key="'getTransResourceOffer'+#offerId")*/
    public TransResourceOffer getTransResourceOffer(String offerId) {
        return get(offerId);
    }

    /**
     * 增加报价
     * @param offer 报价对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
 /*   @Caching(evict={
            @CacheEvict(value="TransResourceOfferServiceImplCache",key="'getOfferListByResource' + #offer.resourceId"),
            @CacheEvict(value="TransResourceOfferServiceImplCache",key="'getResourceOfferFrequency' + #offer.resourceId"),
            @CacheEvict(value="TransResourceOfferServiceImplCache",key="'getMaxOffer' + #offer.resourceId"),
            @CacheEvict(value="TransResourceOfferServiceImplCache",key="'getMaxOfferFormPrice' + #offer.resourceId"),
            @CacheEvict(value="TransResourceOfferServicePageCache", allEntries = true)
    })*/
    public ResponseMessage<TransResourceOffer> addTransResourceOffer(TransResourceOffer offer) {
        return new ResponseMessage(true, save(offer));
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

    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="TransResourceOfferServicePageCache",key="'getResourceOfferPage'+#resourceId +#request.size +',' + #request.offset")*/
    public Page<TransResourceOffer> getResourceOfferPage(String resourceId,Pageable request) {
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("resourceId", resourceId);
        String hql = "from TransResourceOffer t where t.resourceId=:resourceId  order by t.offerTime desc";
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
     * 判断最高价(最后一个出价的)
     * @param resourceId 出让地块Id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
  /*  @Cacheable(value="TransResourceOfferServiceImplCache",key="'getMaxOffer'+#resourceId")*/
    public TransResourceOffer getMaxOffer(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId  order by offerTime desc ");
        query.setString("resourceId",resourceId);
        List<TransResourceOffer> offerList= query.setMaxResults(1).list();
        return (offerList.size()>0) ? offerList.get(0):null;
    }

    /**
     * 跟上一个方法一样 不用在意 用哪个都可以
     * @param resourceId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
   /* @Cacheable(value="TransResourceOfferServiceImplCache",key="'getMaxOfferFormPrice'+#resourceId")*/
    public TransResourceOffer getMaxOfferFormPrice(String resourceId) {
//        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId and offerType<2  order by offerPrice desc ");
        org.hibernate.Query query = hql("from TransResourceOffer t where t.resourceId=:resourceId order by offerPrice desc ");
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
     * 获得大于timevalue的最新报价，最多15条
     * @param resourceId
     * @param timeValue
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResourceOffer> getOfferList(String resourceId, long timeValue){
        Page<TransResourceOffer> resourceOffers = getResourceOfferPage(resourceId, new PageRequest(0, 15));
        if (timeValue > 0) {
            List<TransResourceOffer> resourceOfferList = Lists.newArrayList();
            for(TransResourceOffer resourceOffer : resourceOffers){
                if (resourceOffer.getOfferTime() > timeValue) {
                    resourceOfferList.add(resourceOffer);
                } else {
                    break;
                }
            }
            return resourceOfferList;
        }else {
            return resourceOffers.getItems();
        }
    }

    /**
     * 获取地块的报价大于等于offerPrice的列表
     *
     * @param offerPrice
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResourceOffer> findTransResourceOfferByOfferPrice(double offerPrice, String resourceId) {
        List<Criterion> list = Lists.newArrayList();
        if (offerPrice>0){
            list.add(Restrictions.ge("offerPrice",offerPrice));
        }
        if (StringUtils.isNotBlank(resourceId)){
            list.add(Restrictions.eq("resourceId",resourceId));
        }
        return list(criteria(list).addOrder(Order.desc("offerTime")));
    }

    /**
     * 添加或更新
     * @param offer 报价对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceOffer> saveOrUpdateTransResourceOffer(TransResourceOffer offer) {
        return new ResponseMessage(true, saveOrUpdate(offer));
    }
}
