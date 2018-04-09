package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransBankPay;
import cn.gtmap.landsale.service.TransBankPayService;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/14.
 */
public class TransBankPayServiceImpl extends HibernateRepo<TransBankPay, String> implements TransBankPayService {


    @Override
    @Transactional(readOnly = true)
    public TransBankPay getTransBankPay(String payId) {
        return get(payId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransBankPaysByAccountCode(String accountCode,String payNo) {
        return list(criteria(Restrictions.eq("accountCode", accountCode)).add(Restrictions.eq("payNo", payNo)));
    }

    /**
     * 根据子账号，竞买号，银行流水号查询到账记录
     *
     * @param accountCode
     * @param applyNo
     * @param payNo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransBankPay> getTransBankPaysByAccountCodeAndApplyNo(String accountCode, String applyNo, String payNo) {
        return list(criteria(Restrictions.eq("accountCode", accountCode)).add(Restrictions.eq("payNo", payNo)).add(Restrictions.eq("applyNo", applyNo)));
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
    @Transactional
    public TransBankPay saveTransBankPay(TransBankPay transBankTrans) {
        return saveOrUpdate(transBankTrans);
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
