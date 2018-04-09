package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransNoticeTargetRel;
import cn.gtmap.landsale.service.TransNoticeTargetRelService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public class TransNoticeTargetRelServiceImpl extends HibernateRepo<TransNoticeTargetRel,String> implements TransNoticeTargetRelService {

    @Override
    @Transactional
    public void saveTransNoticeTargetRel(TransNoticeTargetRel transNoticeTargetRel) {
        save(transNoticeTargetRel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransNoticeTargetRel> findTransNoticeTargetRelBynoticeId(String noticeId){
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(noticeId)){
            list.add(Restrictions.eq("noticeId", noticeId));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    /**
     * 通过标的id寻找公告
     *
     * @param targetId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransNoticeTargetRel findTransNoticeTargetRelBytargetId(String targetId) {
        if(StringUtils.isNotBlank(targetId)){
            List<TransNoticeTargetRel> list= list(criteria(Restrictions.eq("targetId", targetId)));
            return (null==list||list.size()<=0)? null:list.get(0);
        }
        return null;
    }
}
