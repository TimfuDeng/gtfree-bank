package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransOfferLog;
import cn.gtmap.landsale.service.TransOfferLogService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/19.
 */
public class TransOfferLogServiceImpl extends HibernateRepo<TransOfferLog,String> implements TransOfferLogService {
    @Override
    @Transactional
    public List<TransOfferLog> getOfferLogListByLicenseId(Collection<String> licenseIds) {
        List<Criterion> list= Lists.newArrayList();
        if(null!=licenseIds&&licenseIds.size()>0){
            list.add(Restrictions.in("licenseId",licenseIds));
            return list(criteria(list).addOrder(Order.desc("createDate")));
        }else {
            return Lists.newArrayList();
        }


    }
}
