package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.core.register.TransBankAccountClient;
import cn.gtmap.landsale.core.register.TransBankPayClient;
import cn.gtmap.landsale.core.service.*;
import com.google.common.collect.Maps;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
@Service
public class TransResourceApplyServiceImpl extends HibernateRepo<TransResourceApply, String> implements TransResourceApplyService {

    @Autowired
    TransBankAccountClient transBankAccountClient;

    @Autowired
    TransBankPayClient transBankPayClient;

    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;

    @Autowired
    TransBuyQualifiedService transBuyQualifiedService;

    @Autowired
    TransFileService transFileService;

    @Autowired
    TransUserUnionService transUserUnionService;

    @Override
    @Transactional(readOnly = true)
    public TransResourceApply getTransResourceApply(String applyId) {
        return get(applyId);
    }



    @Override
    @Transactional(readOnly = true)
    public TransResourceApply getTransResourceApplyByUserId(String userId, String resourceId) {
        return get(criteria(Restrictions.eq("resourceId", resourceId), Restrictions.eq("userId", userId)));
    }

    @Override
    @Transactional
    public List<TransResourceApply> getTransResourceApplyStep(String resourceId,int applyStep) {
        org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.applyStep=?");
        query.setString(0, resourceId);
        query.setInteger(1, applyStep);
        return query.list();

        //return list(criteria(Restrictions.eq("resourceId",resourceId),Restrictions.eq("applyStep",applyStep)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransResourceApply> getTransResourceApplyPageByUserId(String userId,Pageable request){
        Map<String,Object> mapParam= Maps.newHashMap();
        String hql="select t from TransResourceApply t, TransResource tr where t.resourceId = tr.resourceId and t.userId =:userId order by t.applyId desc";
        mapParam.put("userId", userId);
        return findByHql(hql, mapParam, request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceApply> getTransResourceApplyByResourceId(String resourceId){
        org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=?");
        query.setString(0, resourceId);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceApply> getEnterLimitTransResourceApply(String resourceId){
        org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.limitTimeOffer=?");
        query.setString(0,resourceId);
        query.setInteger(1,1);
        return query.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceApply> saveTransResourceApply(TransResourceApply transResourceApply) {

        return new ResponseMessage(true,saveOrUpdate(transResourceApply));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceApply> deleteApply(String applyId) {
        // 根据地块申请编号查找 地块申请信息
        TransResourceApply transResourceApply = getTransResourceApply(applyId);
        // 查找银行子账号
        TransBankAccount transBankAccount = transBankAccountClient.getTransBankAccountByApplyId(applyId);
        if (transBankAccount != null) {
            // 根据银行子账号 查找保证金
            List<TransBankPay> transBankPayList = transBankPayClient.getTransBankPaysByAccountId(transBankAccount.getAccountId());
            if (transBankPayList != null && transBankPayList.size() > 0) {
                return new ResponseMessage(false, "该竞买人已缴纳保证金,无法删除该竞买人！");
            }
        }
        // 查找地块申请Info
        TransUserApplyInfo transUserApplyInfo = transUserApplyInfoService.getTransUserApplyInfoByApplyId(applyId);
        // 查找联合竞买信息
        List<TransUserUnion> transUserUnionList = transUserUnionService.findTransUserUnion(applyId);
        // 查找审核信息
        List<TransBuyQualified> transBuyQualifiedList = transBuyQualifiedService.getListTransBuyQualifiedByApplyId(applyId);
        // 查找附件
        List<TransFile> transFileList = transFileService.getTransFileByKey(applyId);
        // 删除
        delete(transResourceApply);
        if (transBankAccount != null) {
            transBankAccount.setClose(true);
            transBankAccountClient.saveTransBankAccount(transBankAccount);
        }
        if (transUserApplyInfo != null) {
            transUserApplyInfoService.deleteTransUserApplyInfo(transUserApplyInfo.getInfoId());
        }
        if (transUserUnionList != null && transUserUnionList.size() > 0) {
            for (TransUserUnion transUserUnion : transUserUnionList) {
                transUserUnionService.deleteTransUserUnion(transUserUnion.getUnionId());
            }
        }
        if (transBuyQualifiedList != null && transBuyQualifiedList.size() > 0) {
            for (TransBuyQualified transBuyQualified : transBuyQualifiedList) {
                transBuyQualifiedService.deleteTransBuyQualified(transBuyQualified);
            }
        }
        if (transFileList != null && transFileList.size() > 0) {
            for (TransFile transFile : transFileList) {
                transFileService.deleteFile(transFile);
            }
        }
        return new ResponseMessage(true);
    }

}
