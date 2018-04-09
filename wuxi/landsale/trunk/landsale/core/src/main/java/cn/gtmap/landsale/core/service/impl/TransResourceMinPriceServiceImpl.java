package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.log.AuditServiceLog;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceMinPrice;
import cn.gtmap.landsale.core.service.TransResourceMinPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 底价ServiceImpl
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@Service
public class TransResourceMinPriceServiceImpl extends HibernateRepo<TransResourceMinPrice, String> implements TransResourceMinPriceService {


    /**
     * 通过低价Id 获取 TransResourceMinPrice
     * @param priceId
     * @return TransResourceMinPrice
     */
    @Override
    @Transactional(readOnly = true)
    public TransResourceMinPrice getTransResourceMinPrice(String priceId) {
        return get(priceId);
    }

    /**
     * 获取 地块底价
     * @param resourceId
     * @return
     */
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

    /**
     * 获取 地块底价 通过 resourceId
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResourceMinPrice> getMinPriceListByResourceId(String resourceId) {
        org.hibernate.Query query = hql("from TransResourceMinPrice t where t.resourceId=?");
        query.setString(0, resourceId);
        return query.list();
    }

    /**
     * 保存底价
     * @param resourceMinPrice
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @AuditServiceLog(category = Constants.LogCategory.DATA_SAVE,producer = Constants.LogProducer.ADMIN,
            description = "录入底价")
    public ResponseMessage<TransResourceMinPrice> saveTransResourceInfo(TransResourceMinPrice resourceMinPrice) {
        saveOrUpdate(resourceMinPrice);
        return new ResponseMessage(true,resourceMinPrice);
    }

}
