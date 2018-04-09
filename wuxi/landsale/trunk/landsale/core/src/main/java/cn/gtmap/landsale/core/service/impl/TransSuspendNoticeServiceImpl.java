package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransSuspendNotice;
import cn.gtmap.landsale.core.service.TransSuspendNoticeService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 中止公告
 * @author zsj
 * @version v1.0, 2017/11/3
 */
@Service
public class TransSuspendNoticeServiceImpl extends HibernateRepo<TransSuspendNotice,String> implements TransSuspendNoticeService {

    /**
     * 保存中止公告
     *
     * @param suspendNotice
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransSuspendNotice> saveSuspendNotice(TransSuspendNotice suspendNotice) {
        suspendNotice = saveOrUpdate(suspendNotice);
        return new ResponseMessage(true, suspendNotice);
    }

    /**
     * 删除中止公告
     * @param noticeIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteSuspendNotice(String noticeIds) {
        deleteByIds(noticeIds.split(","));
        return new ResponseMessage(true);
    }

    /**
     * 根据noticeId获取中止公告对象
     *
     * @param noticeId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransSuspendNotice getNotice(String noticeId) {
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
    public Page<TransSuspendNotice> findAllSuspendNotices(Pageable request, String title, String regionCodes) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("noticeTitle", title, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        Page<TransSuspendNotice> suspendNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
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
    public Page<TransSuspendNotice> findAllSuspendNoticesByStatus(Pageable request, int deployStatus) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("deployStatus", deployStatus));
        Page<TransSuspendNotice> suspendNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
        return suspendNoticesList;
    }

}
