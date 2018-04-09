package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBuyQualified;
import cn.gtmap.landsale.core.service.TransBuyQualifiedService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by M150237 on 2017-11-13.
 */
@Service
public class TransBuyQualifiedServiceImpl extends HibernateRepo<TransBuyQualified,String> implements TransBuyQualifiedService {
    @Override
    @Transactional(readOnly = true)
    public List<TransBuyQualified> getListTransBuyQualifiedByApplyId(String applyId) {
        List<Criterion> criterion= Lists.newArrayList();
        if(StringUtils.isNotBlank(applyId)) {
            criterion.add(Restrictions.eq("applyId", applyId));
        }
        return list(criteria(criterion).addOrder(Order.desc("qualifiedTime")));
    }

    /**
     * 保存申购资格申请
     * @param transBuyQualified
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransBuyQualified> saveTransBuyQualified(TransBuyQualified transBuyQualified) {
        return new ResponseMessage(true, saveOrUpdate(transBuyQualified));
    }

    /**
     * 根据ApplyId获取 当前申购资格审核信息
     *
     * @param applyId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransBuyQualified getTransBuyQualifiedForCurrent(String applyId) {
        List<Criterion> criterion= Lists.newArrayList();
        if(StringUtils.isNotBlank(applyId)) {
            criterion.add(Restrictions.eq("applyId", applyId));
        }
        criterion.add(Restrictions.eq("currentStatus", Constants.Whether.YES));
        return get(criteria(criterion));
    }

    @Override
    public TransBuyQualified getTransBuyQualifiedById(String qualifiedId) {
        return get(qualifiedId);
    }

    @Override
    public void deleteTransBuyQualified(TransBuyQualified transBuyQualified) {
        delete(transBuyQualified);
    }


}
