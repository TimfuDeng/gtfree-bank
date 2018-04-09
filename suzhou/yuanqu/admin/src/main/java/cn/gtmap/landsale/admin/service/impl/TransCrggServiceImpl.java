package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.EntityNotFoundException;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.service.TransCrggService;
import cn.gtmap.landsale.service.TransFileService;
import cn.gtmap.landsale.service.TransResourceService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.SetUtils;
import org.apache.commons.collections.functors.StringValueTransformer;
import org.apache.commons.collections.set.TransformedSet;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 网上交易出让公告服务
 * Created by jiff on 14/12/21.
 */
public class TransCrggServiceImpl extends HibernateRepo<TransCrgg, String> implements TransCrggService {


    @Autowired
    TransFileService transFileService;
    @Autowired
    TransResourceService transResourceService;

    /**
     * 根据Id获取出让公告
     * @param ggId 公告Id
     * @return 出让公告对象
     */
    @Override
    @Transactional(readOnly = true)
    public TransCrgg getTransCrgg(String ggId) throws EntityNotFoundException {
        return get(ggId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransCrgg getTransCrggByGyggId(String gyggId) {
        return StringUtils.isNotBlank(gyggId)?get(criteria(Restrictions.eq("gyggId", gyggId))):null;
    }

    /**
     * 保存出让公告对象
     * @param transCrgg 出让公告对象
     * @return  新的出让公告对象
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="CrggCache",allEntries = true),
            @CacheEvict(value="ResourceQueryCache",allEntries = true),
            @CacheEvict(value="ResourceCache",allEntries = true)


    })
    public TransCrgg saveTransCrgg(TransCrgg transCrgg) {
        return saveOrUpdate(transCrgg);
    }

    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="CrggCache",allEntries = true),
            @CacheEvict(value="ResourceQueryCache",allEntries = true),
            @CacheEvict(value="ResourceCache",allEntries = true)
    })
    public void deleteTransCrgg(Collection<String> ggIds) {
        transResourceService.deleteTransResourceByGgId(ggIds);
        deleteByIds(ggIds);
        transFileService.deleteFilesByKey(ggIds);
    }


    /**
     * 以分页的形式获取出让公告列表
     * @param title 公告标题
     * @param request 分页请求对象
     * @return 分页列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> findTransCrgg(String title,Collection<String> regionCodes, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("ggTitle", title, MatchMode.ANYWHERE));
        if(regionCodes!=null&&!regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode",regionCodes));
        return find(criteria(criterionList).addOrder(Order.desc("ggBeginTime")), request);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransCrgg> findTransCrggByStatus(String title, Collection<String> regionCodes, Pageable request, int crggStauts) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("crggStauts", crggStauts));
        if(StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("ggTitle", title, MatchMode.ANYWHERE));
        if(regionCodes!=null&&!regionCodes.isEmpty())
            criterionList.add(Restrictions.in("regionCode",regionCodes));
        return find(criteria(criterionList).addOrder(Order.desc("ggBeginTime")), request);
    }

    /**
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    /*@Cacheable(value="CrggCache",key="'findTransCrggIds'")*/
    public Collection<String> findTransCrggIds() {
        Collection<String> crggIds=sql("select t.gg_id from TRANS_CRGG t where t.crgg_stauts=1").list();
        return crggIds;
    }
}
