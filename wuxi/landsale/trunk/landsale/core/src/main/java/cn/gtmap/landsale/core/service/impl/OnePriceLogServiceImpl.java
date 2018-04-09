package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.OnePriceLog;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OnePriceLogService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by trr on 2016/8/13.
 */
@Service
public class OnePriceLogServiceImpl extends HibernateRepo<OnePriceLog,String> implements OnePriceLogService {
    @Override
    @Transactional(readOnly = true)
    //@Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogList'+#transTargetId")
    public List<OnePriceLog> findOnePriceLogList(String transResourceId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(transResourceId)){
            list.add(Restrictions.eq("transResourceId",transResourceId));

        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional(readOnly = true)
    //@Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogList'+#transTargetId")
    public List<OnePriceLog> findOnePriceLogListOrderByBj(String transResourceId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(transResourceId)){
            list.add(Restrictions.eq("transResourceId",transResourceId));

        }
        return list(criteria(list).addOrder(Order.desc("price")));
    }

    @Override
    @Transactional
    //@Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogListByTransTargetIdTransUserId'+#transTargetId+#transUserId")
    public OnePriceLog findOnePriceLogListByTransResourceIdTransUserId(String transResourceId, String transUserId) {
        List<Criterion> list=Lists.newArrayList();
        if(StringUtils.isNotBlank(transResourceId)&&StringUtils.isNotBlank(transUserId)){
            list.add(Restrictions.eq("transResourceId",transResourceId));
            list.add(Restrictions.eq("transUserId",transUserId));
            return  get(criteria(list));
        }

        return null;
    }

    @Override
    @Transactional
    //@Cacheable(value = "OnePriceLogCache",key = "'findOnePriceLogListByTransTargetIdPrice'+#transTargetId+#price")
    public OnePriceLog findOnePriceLogListByTransResourceIdPrice(String transResourceId, Long price) {
        List<Criterion> list=Lists.newArrayList();
        if(StringUtils.isNotBlank(transResourceId)&&null!=price){
            list.add(Restrictions.eq("transResourceId",transResourceId));
            list.add(Restrictions.eq("price",price));
            return  get(criteria(list));
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //@Caching(evict = {
            //@CacheEvict(value = "OnePriceLogCache",allEntries = true)
    //})
    public ResponseMessage<OnePriceLog> saveOnePriceLog(OnePriceLog onePriceLog) {
        return new ResponseMessage(true, save(onePriceLog));
    }

    @Override
    @Transactional
    public OnePriceLog getOnePriceLogByLogId(String logId) {
        return get(logId);
    }

    @Override
    @Transactional
    public OnePriceLog getOnePriceLog(String applyId) {
        return org.apache.commons.lang.StringUtils.isNotBlank(applyId)?get(criteria(Restrictions.eq("applyId", applyId))):null;
    }
}
