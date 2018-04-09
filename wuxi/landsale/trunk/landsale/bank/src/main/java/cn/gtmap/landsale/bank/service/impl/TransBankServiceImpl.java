package cn.gtmap.landsale.bank.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.bank.service.TransBankService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBank;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 银行ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/28
 */
@Service
public class TransBankServiceImpl extends HibernateRepo<TransBank, String> implements TransBankService {


    /**
     * 获取银行分页 列表
     * @param regionCodes
     * @param pageable
     * @return
     */
    @Override
    public Page<TransBank> findBankPage(String bankName, String regionCodes, Pageable pageable) {
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        if(!StringUtils.isEmpty(bankName)) {
            criterionList.add(Restrictions.like("bankName", bankName, MatchMode.ANYWHERE));
        }
        if(!StringUtils.isEmpty(regionCodes)) {
            if  (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else if (regionCodes.split(",").length == 1) {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        return find(criteria(criterionList), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBank> getBankList() {
        org.hibernate.Query query = hql("from TransBank t order by bankId desc");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public TransBank getBank(String bankId){
        return get(bankId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransBank> saveTransBank(TransBank transBank) {
        transBank = saveOrUpdate(transBank);
        return new ResponseMessage(true, transBank);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBank> getBankListByRegion(String regionCodes){
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        if(!StringUtils.isEmpty(regionCodes)) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }

        return list(criteria(criterionList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransBank> deleteBank(String bankId){
        deleteById(bankId);
        return new ResponseMessage(true);
    }

    @Override
    public TransBank getBankByCodeAndRegion(String bankCode, String regionCode) {
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        if(!StringUtils.isEmpty(bankCode)) {
            criterionList.add(Restrictions.eq("bankCode", bankCode));
        }
        if(!StringUtils.isEmpty(regionCode)) {
            criterionList.add(Restrictions.eq("regionCode", regionCode));
        }
        Criteria criteria = null;
        if(criterionList.size()>0) {
            criteria = criteria(criterionList);
        } else {
            criteria = criteria();
        }
        criteria.addOrder(Order.desc("bankId"));
        List<TransBank> queryResult = list(criteria);
        if (queryResult.size() > 0) {
            return queryResult.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TransBank findByCode(String code){
        Criteria criteria = getSession().createCriteria(TransBank.class);
        criteria.add(Restrictions.eq("bankCode", code));
        return get(criteria);
    }
}
