package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.DealNotice;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.core.service.DealNoticeService;
import cn.gtmap.landsale.core.service.TransCrggService;
import cn.gtmap.landsale.core.service.TransResourceService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 成交公示
 * @author cxm
 * @version v1.0, 2017/11/14
 */
@Service
public class DealNoticeServiceImpl extends HibernateRepo<DealNotice,String> implements DealNoticeService {


    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransCrggService transCrggService;
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
        if(StringUtils.isNotBlank(title)) {
            criterionList.add(Restrictions.like("noticeTitle", title, MatchMode.ANYWHERE));
        }
        Page<DealNotice> dealNoticeNoticesList = find(criteria(criterionList).addOrder(Order.desc("deployTime")),request);
        return dealNoticeNoticesList;
    }

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
     * 保存成交公告
     *
     * @param dealNotice
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<DealNotice> saveDealNotice(DealNotice dealNotice) {
        if (transResourceService.getTransResourceByCode(dealNotice.getResourceCode())!=null){
            saveOrUpdate(dealNotice);
            if(StringUtils.isNotBlank(dealNotice.getResourceCode())){
                TransResource transResource = transResourceService.getTransResourceByCode(dealNotice.getResourceCode());
                transResource.setDealNoticeId(dealNotice.getNoticeId());
                transResourceService.updateTransResource(transResource);
            }
            return new ResponseMessage(true, dealNotice);
        }else {
            return new ResponseMessage(false, "请填写正确的地块编号!");
        }

    }


    /**
     * 删除成交公告
     *
     * @param noticeIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<DealNotice> deleteDealNotice(String noticeIds) {
        for (String noticeId:noticeIds.split(";")){
            if (StringUtils.isNotBlank(noticeId)){
                deleteById(noticeId);
            }
        }
        return new ResponseMessage(true, "删除成功！");
    }

    /**
     * 根据地块编号查询成交公告
     * @param resourceCode
     */
    @Override
    @Transactional
    public List<DealNotice> findDealNoticeByResourceCode(String resourceCode) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(resourceCode)) {
            criterionList.add(Restrictions.eq("resourceCode", resourceCode));
        }
        List<DealNotice> dealNoticeNoticesList = list(criteria(criterionList).addOrder(Order.desc("deployTime")));
        return dealNoticeNoticesList;
    }

}
