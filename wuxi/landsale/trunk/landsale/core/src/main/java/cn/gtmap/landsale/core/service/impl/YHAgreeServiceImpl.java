package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.YHAgree;
import cn.gtmap.landsale.core.service.YHAgreeService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class YHAgreeServiceImpl extends HibernateRepo<YHAgree, String> implements YHAgreeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<YHAgree> saveOrUpdateYHAgree(YHAgree yhAgree) {
       return new ResponseMessage<>(true,saveOrUpdate(yhAgree));
    }

    @Override
    @Transactional(readOnly = true)
    public YHAgree getYHAgree(String id) {
        return get(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<YHAgree> getYHAgreeByResourceId(String resourceId) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("agreeStatus", Constants.Whether.YES));
        if (StringUtils.isNotBlank(resourceId)) {
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        return list(criteria(criterionList).addOrder(Order.desc("agreeTime")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<YHAgree> getAllYHAgreeByResourceId(String resourceId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(resourceId)) {
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        return list(criteria(criterionList).addOrder(Order.desc("agreeTime")));
    }

    @Override
    @Transactional(readOnly = true)
    public YHAgree getYHAgreeByResourceIdAndUserId(String resourceId,String userId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(resourceId)) {
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        if (StringUtils.isNotBlank(resourceId)) {
            criterionList.add(Restrictions.eq("userId", userId));
        }
        return get(criteria(criterionList));
    }
}
