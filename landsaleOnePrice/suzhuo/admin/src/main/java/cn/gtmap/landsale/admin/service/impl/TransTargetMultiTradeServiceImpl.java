package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransTargetMultiTrade;
import cn.gtmap.landsale.service.TransTargetMultiTradeService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by trr on 2016/7/19.
 */
public class TransTargetMultiTradeServiceImpl extends HibernateRepo<TransTargetMultiTrade,String> implements TransTargetMultiTradeService {
    @Override
    @Transactional(readOnly = true)
    public List<TransTargetMultiTrade> getTransTargetMultiTradeList(String targetId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(targetId)){
            list.add(Restrictions.eq("targetId", targetId));

        }
        return list(criteria(list));
    }
}
