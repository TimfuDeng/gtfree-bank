package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.service.TransBankService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Jibo on 2015/4/25.
 */
public class TransBankServiceImpl extends HibernateRepo<TransBank, String> implements TransBankService {

    @Override
    @Transactional(readOnly = true)
    //@Cacheable(value="BankServiceCache",key="'getBankList'")
    public List<TransBank> getBankList() {
        org.hibernate.Query query = hql("from TransBank t order by bankId desc");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    //@Cacheable(value="BankServiceCache",key="'getBank'+#bankId")
    public TransBank  getBank(String bankId){
        return get(bankId);
    }

    @Override
    @Transactional
    //@Caching(evict={
            //@CacheEvict(value="BankServiceCache",allEntries=true)
    //})
    public TransBank save(TransBank transBank) {
        return saveOrUpdate(transBank);
    }

    @Override
    @Transactional(readOnly = true)
    //@Cacheable(value="BankServiceCache",key="'getBankListByRegion'+#regionCode")
    public List<TransBank> getBankListByRegion(String regionCode){
        org.hibernate.Query query = hql("from TransBank t where t.regionCode='"+regionCode+"' order by bankId desc");
        return query.list();
    }

    @Override
    @Transactional
    //@Caching(evict={
            //@CacheEvict(value="BankServiceCache",allEntries=true)
    //})
    public void del(String bankId){
        deleteById(bankId);
    }
}
