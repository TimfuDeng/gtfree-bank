package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransTargetEarnestMoney;
import cn.gtmap.landsale.service.TransTargetEarnestMoneyService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2016/7/7.
 */
public class TransTargetEarnestMoneyServiceImpl extends HibernateRepo<TransTargetEarnestMoney,String> implements TransTargetEarnestMoneyService{
    @Override
    @Transactional
    public void saveTransTargetEarnestMoney(TransTargetEarnestMoney TransTargetEarnestMoney) {
        save(TransTargetEarnestMoney);
    }
}
