package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.TransResourceMinPrice;
import cn.gtmap.landsale.service.TransResourceMinPriceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/6/2.
 */
public class TransResourceMinPriceServiceImpl extends HibernateRepo<TransResourceMinPrice, String> implements TransResourceMinPriceService {


    @Override
    @Transactional(readOnly = true)
    public TransResourceMinPrice getTransResourceMinPrice(String priceId) {
        return get(priceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getMinPriceByResourceId(String resourceId){
        Double result=0.0;
        List<TransResourceMinPrice> transResourceMinPriceList=getMinPriceListByResourceId(resourceId);
        for(TransResourceMinPrice minPrice:transResourceMinPriceList){
            result=result+minPrice.getPrice();
        }
        return transResourceMinPriceList.size()>0?result/transResourceMinPriceList.size():0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceMinPrice> getMinPriceListByResourceId(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceMinPrice t where t.resourceId=?");
        query.setString(0, resourceId);
        return query.list();
    }

    @Override
    @Transactional
    @AuditServiceLog(category = Constants.LogCategory.DATA_SAVE,producer = Constants.LogProducer.ADMIN,
            description = "录入底价")
    public TransResourceMinPrice saveTransResourceInfo(TransResourceMinPrice resourceMinPrice) {
        return saveOrUpdate(resourceMinPrice);
    }
}
