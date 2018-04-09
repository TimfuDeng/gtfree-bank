package cn.gtmap.landsale.admin.service.impl;
import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransNotice;
import cn.gtmap.landsale.service.TransNoticeService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/5.
 */
public class TransNoticeServiceImpl extends HibernateRepo<TransNotice,String> implements TransNoticeService {
    private static Logger logger= LoggerFactory.getLogger(TransNoticeServiceImpl.class);
    @Override
    @Transactional(readOnly = true)
    public Page<TransNotice> findTransNoticeList(String title, Pageable request) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("no",title, MatchMode.ANYWHERE));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),request);
    }

    @Override
    @Transactional
    public List<TransNotice> findTransNoticeListByNo(String no) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(no)){
            list.add(Restrictions.eq("no",no));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));

    }



    @Override
    @Transactional
    public TransNotice saveTransNotice(TransNotice transNotice){
        return save(transNotice);

    }

    @Override
    @Transactional
    public TransNotice getTransNotice(String id){
        return get(id);
    }

    /**
     * 集合之外的公告
     *
     * @param noticeIds
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransNotice> findTransNoticeListNotInNoticeIds(Collection<String> noticeIds) {
        List<Criterion> list= Lists.newArrayList();
        if(null!=noticeIds&&noticeIds.size()>0){
            list.add(Restrictions.not(Restrictions.in("id",noticeIds)));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }
}
