package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.SuspendNotice;
import cn.gtmap.landsale.service.SuspendNoticeService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2015/11/2.
 */
public class SuspendNoticeServiceImpl extends HibernateRepo<SuspendNotice,String> implements SuspendNoticeService{
    /**
     * 保存中止公告
     *
     * @param suspendNotice
     * @return
     */
    @Override
    @Transactional
    public SuspendNotice save(SuspendNotice suspendNotice) {
        return saveOrUpdate(suspendNotice);
    }

    /**
     * 删除中止公告
     *
     * @param noticeId
     */
    @Override
    @Transactional
    public void delete(Collection<String> noticeId) {
        deleteByIds(noticeId);
    }

    /**
     * 根据noticeId获取中止公告对象
     *
     * @param noticeId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public SuspendNotice getNotice(String noticeId) {
        return get(noticeId);
    }

    /**
     * 获取所有的中止公告对象
     *
     * @param request
     * @param title
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuspendNotice> findAllSuspendNotices(Pageable request, String title) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("noticeTitle",title, MatchMode.ANYWHERE));
        Page<SuspendNotice> suspendNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
        return suspendNoticesList;
    }

    /**
     * 根据发布状态查询中止公告
     *
     * @param request
     * @param deployStatus
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuspendNotice> findAllSuspendNoticesByStatus(Pageable request, int deployStatus) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("deployStatus",deployStatus));
        Page<SuspendNotice> suspendNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
        return suspendNoticesList;
    }
}
