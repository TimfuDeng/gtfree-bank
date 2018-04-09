package cn.gtmap.landsale.admin.service.impl;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.OneApply;
import cn.gtmap.landsale.service.OneApplyService;
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

/**
 * Created by trr on 2016/8/14.
 */
public class OneApplyServiceImpl extends HibernateRepo<OneApply,String> implements OneApplyService {
    @Override
    @Transactional
    public OneApply saveOneApply(OneApply oneApply) {
        return saveOrUpdate(oneApply);
    }

    @Override
    @Transactional
    public OneApply getOnApply(String targetId, String transUserId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(targetId)&&StringUtils.isNotBlank(transUserId)){
            list.add(Restrictions.eq("targetId",targetId));
            list.add(Restrictions.eq("transUserId",transUserId));
            List<OneApply> result = list(criteria(list));
            if(result != null && result.size() > 0)
                return result.get(0);
            return null;

        }
        return null;
    }

    @Override
    @Transactional
    public Page<OneApply> findOneApply(String transUserId, Pageable page) {
        List<Criterion> list=Lists.newArrayList();
        if (StringUtils.isNotBlank(transUserId)){
            list.add(Restrictions.eq("transUserId",transUserId));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),page);
    }
}
