package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransGoods;
import cn.gtmap.landsale.service.TransGoodsService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public class TransGoodsServiceImpl extends HibernateRepo<TransGoods,String> implements TransGoodsService {
    @Override
    @Transactional(readOnly = true)
    public Page<TransGoods> findTransGoodsList(String title,Collection<String> goodsIds, Pageable request) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("no",title, MatchMode.ANYWHERE));
        }
        if(null!=goodsIds&&goodsIds.size()>0){
            list.add(Restrictions.in("id",goodsIds));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")), request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransGoods> findTransGoodsListByNo(String name) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(name)){
            list.add(Restrictions.eq("name", name));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional
    public TransGoods saveTransGoods(TransGoods transGoods) {
        return save(transGoods);
    }

    /**
     * 根据id得到地块
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransGoods getTransGoods(String id) {
        return get(id);
    }
}
