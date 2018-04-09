package cn.gtmap.landsale.bank.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.bank.service.TransBankRefundService;
import cn.gtmap.landsale.common.model.TransBankRefund;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退款申请ServiceImpl
 * @author zsj
 * @version v1.0, 2017/8/30
 */
@Service
public class TransBankRefundServiceImpl extends HibernateRepo<TransBankRefund, String> implements TransBankRefundService {


    @Override
    @Transactional(readOnly = true)
    public TransBankRefund getTransBankRefundById(String refundId) {
        return get(refundId);
    }

    @Override
    public TransBankRefund getTransBankRefundByPayId(String payId) {
        org.hibernate.Query query = hql("from TransBankRefund t where t.payId='" + payId + "'");
        List<TransBankRefund> queryResult = query.list();
        if (queryResult.size() > 0) {
            return queryResult.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<TransBankRefund> getTransBankPaysByAccountAndBatch(String accountId, String batchNo) {
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        if(!StringUtils.isEmpty(accountId)) {
            criterionList.add(Restrictions.eq("accountId", accountId));
        }
        if(!StringUtils.isEmpty(batchNo)) {
            criterionList.add(Restrictions.eq("batchNo", batchNo));
        }
        Criteria criteria = null;
        if(criterionList.size()>0) {
            criteria = criteria(criterionList);
        } else {
            criteria = criteria();
        }
        criteria.addOrder(Order.desc("refundTime"));
        return list(criteria);
    }

    @Override
    public TransBankRefund saveTransBankRefund(TransBankRefund transBankRefund) {
        return saveOrUpdate(transBankRefund);
    }

    @Override
    public TransBankRefund updateTransBankRefund(TransBankRefund transBankRefund) {
        return merge(transBankRefund);
    }
}
