package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransRole;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransBankService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jibo on 2015/4/25.
 */
public class TransBankServiceImpl extends HibernateRepo<TransBank, String> implements TransBankService {

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="BankServiceCache",key="'getBankList'")
    public List<TransBank> getBankList() {
        org.hibernate.Query query = hql("from TransBank t order by bankId desc");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBank> getBankListByRegions(Set<String> regions) {
        org.hibernate.Query query=null;
        if(regions!=null&&!regions.isEmpty()) {
            String regionJson= JSON.toJSONString(regions).replace("[","(").replace("]",")").replace("\"","'");
            query = hql("from TransBank t where t.regionCode in "+ regionJson +" order by t.bankId desc");
        }else{
             query = hql("from TransBank t order by bankId desc");
        }
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBank> getBankList(String moneyUnit) {
        List<Criterion> criterionList= Lists.newArrayList();
        criterionList.add(Restrictions.eq("moneyUnit",moneyUnit));
        if(!SecUtil.isAdmin())
        criterionList.add(Restrictions.in("regionCode", SecUtil.getPermittedRegions()));
        List<TransBank> list=list(criteria(criterionList).addOrder(Order.desc("bankId")));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="BankServiceCache",key="'getBank'+#bankId")
    public TransBank  getBank(String bankId){
        return get(bankId);
    }

    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="BankServiceCache",allEntries=true)
    })
    public TransBank save(TransBank transBank) {
        return saveOrUpdate(transBank);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="BankServiceCache",key="'getBankListByRegion'+#regionCode")
    public List<TransBank> getBankListByRegion(String regionCode){
        org.hibernate.Query query = hql("from TransBank t where t.regionCode='"+regionCode+"' order by bankId desc");
        return query.list();
    }

    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="BankServiceCache",allEntries=true)
    })
    public void del(String bankId){
        deleteById(bankId);
    }
}
