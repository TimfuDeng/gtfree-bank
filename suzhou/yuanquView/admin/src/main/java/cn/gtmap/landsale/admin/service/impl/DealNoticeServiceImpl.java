package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.DealNotice;
import cn.gtmap.landsale.service.DealNoticeService;
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
 * Created by trr on 2015/11/3.
 */
public class DealNoticeServiceImpl extends HibernateRepo<DealNotice,String> implements DealNoticeService {



    /**
     * 根据noticeId获取成交公告对象
     *
     * @param noticeId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DealNotice getNotice(String noticeId) {
        return get(noticeId);
    }

    /**
     * 获取所有的成交公告对象
     *
     * @param request
     * @param title
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DealNotice> findAllDealNotices(Pageable request, String title) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title))
            criterionList.add(Restrictions.like("noticeTitle", title, MatchMode.ANYWHERE));
        Page<DealNotice> dealNoticeNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
        return dealNoticeNoticesList;
    }

    /**
     * 根据发布状态查询成交公告
     *
     * @param request
     * @param deployStatus
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DealNotice> findAllDealNoticesByStatus(Pageable request, int deployStatus) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("deployStatus",deployStatus));
        Page<DealNotice> dealNoticeNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
        return dealNoticeNoticesList;
    }
}
