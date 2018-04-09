package cn.gtmap.landsale.bank.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.bank.service.TransBankPayService;
import cn.gtmap.landsale.common.model.TransBankPay;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jibo1_000 on 2015/5/14.
 */
@Service
public class TransBankPayServiceImpl extends HibernateRepo<TransBankPay, String> implements TransBankPayService {


    @Override
    @Transactional(readOnly = true)
    public TransBankPay getTransBankPay(String payId) {
        return get(payId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransBankPaysByAccountCodeAndPayNo(String accountCode,String payNo) {
        return list(criteria(Restrictions.eq("accountCode", accountCode)).add(Restrictions.eq("payNo", payNo)));
    }

	/**
	 * @作者 王建明
	 * @创建日期 2015/7/1
	 * @创建时间 17:08
	 * @描述 —— 退款说明书中的退款金额需要根据子账号和付款用户去查询所有的付款记录
	 */
	@Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransBankPaysByAccountCodeAndAccountId(String accountCode,String accountId) {
        return list(criteria(Restrictions.eq("accountCode", accountCode)).add(Restrictions.eq("accountId", accountId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransBankPaysByAccountId(String accountId){
        return list(criteria(Restrictions.eq("accountId", accountId)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransBankPay saveTransBankPay(TransBankPay transBankTrans) {
        return saveOrUpdate(transBankTrans);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransBankPaysByAccountCode(String accountCode) {
        org.hibernate.Query query = hql("from TransBankPay t where t.accountCode='"+accountCode+"' order by payTime desc");
        return query.list();
    }

    /**
     * 错转款  accountId is null
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransPaysByAccointIdIsNULL(String accountId) {
        List<TransBankPay> list=null;
        Query query=hql("from TransBankPay t where t.accountId is null");
        list= query.list();
        return list;
    }
}
