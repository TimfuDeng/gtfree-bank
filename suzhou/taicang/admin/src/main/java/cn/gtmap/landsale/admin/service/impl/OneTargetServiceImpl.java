package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.OneTarget;
import cn.gtmap.landsale.service.OneTargetService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 一次报价表标的
 * Created by trr on 2016/8/12.
 */
public class OneTargetServiceImpl extends HibernateRepo<OneTarget,String> implements OneTargetService {
    @Override
    @Transactional(readOnly = true)
    public OneTarget getOneTargetByTransTarget(String transTargetId) {
        return get(criteria(Restrictions.eq("transTargetId",transTargetId)));
    }

    @Override
    @Transactional(readOnly = true)
    public OneTarget getOneTarget(String id) {
        return get(id);
    }

    @Override
    @Transactional
    //@Caching(evict={
            //@CacheEvict(value="OneTargetQueryCache",allEntries=true),
            //@CacheEvict(value = "OnePriceLogCache",allEntries = true)
    //})
    public OneTarget saveOneTarget(OneTarget oneTarget) {
        return saveOrUpdate(oneTarget);
    }

    @Override
    @Transactional
    //@Cacheable(value = "OneTargetQueryCache",key = "'findOneTargetPage'+#title+#page.getOffset()+#page.getIndex()")
    public Page<OneTarget> findOneTargetPage(String title, Pageable page) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("transName",title));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),page);
    }

    @Override
    @Transactional
    //@Cacheable(value = "OneTargetQueryCache",key = "'findOneTargetPageByIsStop'+#title+#page.getOffset()+#page.getIndex()+#isStop")
    public Page<OneTarget> findOneTargetPageByIsStop(String title, Pageable page, Integer isStop) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("transName",title, MatchMode.ANYWHERE));
        }
        if (null!=isStop){
            list.add(Restrictions.like("isStop",isStop));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),page);
    }
}
