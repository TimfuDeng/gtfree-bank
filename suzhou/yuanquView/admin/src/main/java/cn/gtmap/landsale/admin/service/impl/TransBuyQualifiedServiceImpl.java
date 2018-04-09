package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransBuyQualified;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.TransBuyQualifiedService;
import cn.gtmap.landsale.service.TransResourceApplyService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * 资格审查
 * Created by trr on 2015/10/13.
 */
public class TransBuyQualifiedServiceImpl extends HibernateRepo<TransBuyQualified,String> implements TransBuyQualifiedService{
    @Autowired
    TransResourceApplyService transResourceApplyService;
    @Override
    @Transactional(readOnly = true)
    public TransBuyQualified getTransBuyQualifiedById(String qualifiedId) {
        return get(qualifiedId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransBuyQualified getTransBuyQualifiedByApplyId(String applyId) {
        List<Integer> statusList= new ArrayList();
        statusList.add(2);
        statusList.add(3);
        List<Criterion> criterion= Lists.newArrayList();
        if(StringUtils.isNotBlank(applyId))
            criterion.add(Restrictions.eq("applyId",applyId));
        if(StringUtils.isNotBlank(applyId))
            criterion.add(Restrictions.in("qualifiedStatus",statusList));
        if(list(criteria(criterion)).size()>0){
            return list(criteria(criterion)).get(0);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBuyQualified> getListTransBuyQualifiedByApplyId(String applyId) {
        List<Criterion> criterion= Lists.newArrayList();
        if(StringUtils.isNotBlank(applyId))
            criterion.add(Restrictions.eq("applyId",applyId));
        return list(criteria(criterion).addOrder(Order.desc("qualifiedTime")));
    }

    @Override
    @Transactional
    public void saveTransBuyQualifiedAndTransResourceApply(TransBuyQualified transBuyQualified, TransResourceApply transResourceApply) {
        transBuyQualified=saveOrUpdate(transBuyQualified);
        transResourceApply.setQualifiedId(transBuyQualified.getQualifiedId());
        transResourceApplyService.saveTransResourceApply(transResourceApply);

    }

    @Override
    @Transactional
    public TransBuyQualified saveTransBuyQualified(TransBuyQualified transBuyQualified) {
        return saveOrUpdate(transBuyQualified);
    }
}
