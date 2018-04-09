package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.OneParam;
import cn.gtmap.landsale.service.OneParamService;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2016/8/11.
 */
public class OneParamServiceImpl extends HibernateRepo<OneParam,String> implements OneParamService {

    @Override
    @Transactional
    public OneParam getOneParam(String id) {
        return get(id);
    }

    @Override
    @Transactional
    public OneParam saveOrUpdateOneParam(OneParam oneParam) {
        return saveOrUpdate(oneParam);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OneParam> findOneParam(Pageable request) {
        return find(request);
    }


}
