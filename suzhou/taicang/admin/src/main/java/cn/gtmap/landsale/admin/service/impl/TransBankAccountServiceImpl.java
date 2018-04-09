package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.service.TransBankAccountService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 虚拟子账户的开户信息
 * Created by jibo1_000 on 2015/5/9.
 */
public class TransBankAccountServiceImpl extends HibernateRepo<TransBankAccount, String> implements TransBankAccountService {
    @Override
    @Transactional
    public void deleteTransBankAccountById(String accountId){
        deleteById(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransBankAccount getTransBankAccount(String accountId) {
        return get(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransBankAccount getTransBankAccountByApplyId(String applyId) {
        org.hibernate.Query query = hql("from TransBankAccount t where t.applyId=?");
        query.setString(0,applyId);
        List<TransBankAccount> queryResult= query.list();
        if (queryResult!=null && queryResult.size()>0)
            return queryResult.get(0);
        else
            return null;
    }

    @Override
    @Transactional(readOnly = true)
    public TransBankAccount getTransBankAccountByApplyNo(String applyNo) {
        org.hibernate.Query query = hql("from TransBankAccount t where t.applyNo='"+ applyNo+"' ");
        List<TransBankAccount> queryResult= query.list();
        if (queryResult.size()>0)
            return queryResult.get(0);
        else
            return null;
    }

    @Override
    @Transactional(readOnly = true)
    public TransBankAccount getTransBankAccountByAccountCode(String accountCode) {
        org.hibernate.Query query = hql("from TransBankAccount t where t.accountCode='"+ accountCode+"' and close=0");
        List<TransBankAccount> queryResult= query.list();
        if (queryResult.size()>0)
            return queryResult.get(0);
        else
            return null;
    }

    @Override
    @Transactional
    public TransBankAccount saveTransBankAccount(TransBankAccount transBankAccount) {
        return saveOrUpdate(transBankAccount);
    }

    @Override
    @Transactional
    public String getNextApplyNo(){
        SQLQuery query=sql("select trans_apply_id.nextval from dual");
        Object result=query.uniqueResult();
        return  getFormateString(result.toString(),8);// 鉴于常州这边银行备注只支持8位字符，应吉总要求在trunk中统一改竞买号为八位【王建明 2015/6/23 13:35】
    }

    @Override
    @Transactional
    public TransBankAccount createOrGetTransBankAccount(String applyId){
        TransBankAccount  transBankAccount= getTransBankAccountByApplyId(applyId);
        if (transBankAccount==null){
            transBankAccount=new TransBankAccount();
            transBankAccount.setApplyNo(getNextApplyNo());
            transBankAccount.setApplyId(applyId);
            return saveTransBankAccount(transBankAccount);
        }else{
            return transBankAccount;
        }
    }


    private String getFormateString(String str,int length){
        int zeroLen=length- str.length();
        String result=str;
        for(int i=0;i<zeroLen;i++){
            result="0" + result;
        }
        return result;
    }
}
