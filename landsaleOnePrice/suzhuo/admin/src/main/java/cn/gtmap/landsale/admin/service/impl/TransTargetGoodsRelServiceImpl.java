package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransNoticeTargetRel;
import cn.gtmap.landsale.model.TransTargetGoodsRel;
import cn.gtmap.landsale.service.TransTargetGoodsRelService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public class TransTargetGoodsRelServiceImpl extends HibernateRepo<TransTargetGoodsRel,String> implements TransTargetGoodsRelService {
    @Override
    @Transactional
    public void saveTransTargetGoodsRel(TransTargetGoodsRel transTargetGoodsRel) {
        save(transTargetGoodsRel);
    }

    @Override
    @Transactional(readOnly = true)
    public TransTargetGoodsRel findTransTargetGoodsRelByGoodsId(String goodsId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(goodsId)){
            list.add(Restrictions.eq("goodsId", goodsId));
        }
        if(list(criteria(list)).size()>0){
            return list(criteria(list)).get(0);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransTargetGoodsRel> findTransTargetGoodsRelTargetId(String targetId){
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(targetId)){
            list.add(Restrictions.eq("targetId", targetId));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }


}
