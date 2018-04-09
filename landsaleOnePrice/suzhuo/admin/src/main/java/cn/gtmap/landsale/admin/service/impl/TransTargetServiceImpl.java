package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.landsale.model.TransTarget;
import cn.gtmap.landsale.service.TransTargetService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public class TransTargetServiceImpl extends HibernateRepo<TransTarget,String> implements TransTargetService {

    private Logger logger= LoggerFactory.getLogger(TransTargetServiceImpl.class);

    @Value("${resource.date}")
    String resourceDate;

    @Override
    @Transactional
    //@Caching(evict = @CacheEvict(value = "TransTargetServiceCache",allEntries = true))
    public TransTarget saveTransTarget(TransTarget transTarget) {
            return saveOrUpdate(transTarget);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransTarget> findTransTarget(String title,Collection<String> targetIds, Pageable request)  {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("name",title, MatchMode.ANYWHERE));
        }
        if(null!=targetIds&&targetIds.size()>0){
            list.add(Restrictions.in("id", targetIds));
        }
        Date createDate= String2Date(resourceDate);
        if(createDate!=null)
            list.add(Restrictions.gt("createDate",createDate));
       /* list.add(Restrictions.eq("isSuspend", 1));*/
        return find(criteria(list).addOrder(Order.desc("createDate")),request);
    }

    private Date String2Date(String resourceDate){
        try {
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(resourceDate);
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransTarget> findTransTargetByTargetIds(Collection<String> targetIds) {
        List<Criterion> list= Lists.newArrayList();

        if(null!=targetIds&&targetIds.size()>0){
            list.add(Restrictions.in("id", targetIds));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransTarget> findTransTarget2Export(String title, Pageable request) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("name",title, MatchMode.ANYWHERE));
        }
        list.add(Restrictions.or(Restrictions.eq("status",5),Restrictions.eq("status",6),Restrictions.eq("isStop",1)));


        return find(criteria(list).addOrder(Order.desc("createDate")),request);
    }

    @Override
    @Transactional
    public List<TransTarget> findTransTargetListByNo(String name) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(name)){
            list.add(Restrictions.eq("no", name));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional
    public TransTarget getTransTarget(String id) {
        return get(id);
    }
}
