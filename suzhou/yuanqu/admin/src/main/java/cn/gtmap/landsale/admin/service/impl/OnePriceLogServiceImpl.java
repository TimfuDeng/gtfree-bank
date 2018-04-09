package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.OnePriceLog;
import cn.gtmap.landsale.service.OnePriceLogService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by trr on 2016/8/13.
 */
public class OnePriceLogServiceImpl extends HibernateRepo<OnePriceLog,String> implements OnePriceLogService {
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogList'+#transTargetId")*/
    public List<OnePriceLog> findOnePriceLogList(String transTargetId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(transTargetId)){
            list.add(Restrictions.eq("transTargetId",transTargetId));

        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional
   /* @Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogListByTransTargetIdTransUserId'+#transTargetId+#transUserId")*/
    public OnePriceLog findOnePriceLogListByTransTargetIdTransUserId(String transTargetId, String transUserId) {
        List<Criterion> list=Lists.newArrayList();
        if(StringUtils.isNotBlank(transTargetId)&&StringUtils.isNotBlank(transUserId)){
            list.add(Restrictions.eq("transTargetId",transTargetId));
            list.add(Restrictions.eq("transUserId",transUserId));
            return  get(criteria(list));
        }

        return null;
    }

    @Override
    @Transactional
    /*@Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogListByTransTargetIdPrice'+#transTargetId+#price")*/
    public OnePriceLog findOnePriceLogListByTransTargetIdPrice(String transTargetId, Long price) {
        List<Criterion> list=Lists.newArrayList();
        if(StringUtils.isNotBlank(transTargetId)&&null!=price){
            list.add(Restrictions.eq("transTargetId",transTargetId));
            list.add(Restrictions.eq("price",price));
            return  get(criteria(list));
        }

        return null;
    }

    @Override
    @Transactional
    /*@Caching(evict = {
            @CacheEvict(value = "OnePriceLogCache",allEntries = true)
    })*/
    public OnePriceLog saveOnePriceLog(OnePriceLog onePriceLog) {
        return save(onePriceLog);
    }

    @Override
    @Transactional
    public OnePriceLog getOnePriceLog(String applyId) {
        return org.apache.commons.lang.StringUtils.isNotBlank(applyId)?get(criteria(Restrictions.eq("applyId", applyId))):null;
    }
}
