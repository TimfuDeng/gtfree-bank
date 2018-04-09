package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public class TransResourceApplyServiceImpl extends HibernateRepo<TransResourceApply, String> implements TransResourceApplyService {

    @Autowired
    TransUserService transUserService;

    @Override
    @Transactional(readOnly = true)
    public TransResourceApply getTransResourceApply(String applyId) {
        return get(applyId);
    }



    @Override
    @Transactional(readOnly = true)
    public TransResourceApply getTransResourceApplyByUserId(String userId, String resourceId) {
        return get(criteria(Restrictions.eq("resourceId",resourceId),Restrictions.eq("userId",userId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransResourceApply> getTransResourceApplyPageByUserId(String userId,Pageable request){
        Map<String,Object> mapParam= Maps.newHashMap();
        String hql="from TransResourceApply t where userId =:userId order by applyId desc";
        mapParam.put("userId",userId);
        return findByHql(hql, mapParam, request);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransResourceApply> getTransResourceApplyByresourceId(String resourceId, Pageable request,String userName) {
        Page<TransResourceApply> list=null;
        if(StringUtils.isNotBlank(resourceId)){
            List<Criterion> criterionList= Lists.newArrayList();
            criterionList.add(Restrictions.eq("resourceId",resourceId));
            if(StringUtils.isNotBlank(userName)){
                if(transUserService.getTransUserByUserName(userName)!=null){
                    String  userId = transUserService.getTransUserByUserName(userName).getUserId();
                    criterionList.add(Restrictions.eq("userId",userId));
                }
            }
            list=find(criteria(criterionList).addOrder(Order.desc("applyDate")),request);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransResourceApply> getTransResourceApplyPageByresourceId(String resourceId, Pageable request) {
        Page<TransResourceApply> list=null;
        if(StringUtils.isNotBlank(resourceId)){
            List<Criterion> criterionList= Lists.newArrayList();
            list=find(criteria(criterionList).addOrder(Order.desc("applyDate")),request);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceApply> getTransResourceApplyByResourceId(String resourceId){
        org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? order by t.applyDate desc");
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

    /**
     * 设置用户竞买进入
     *
     * @param applyId
     */
    @Override
    @Transactional
    public void enterLimitTransResourceApply(String applyId) {
        TransResourceApply transResourceApply = get(applyId);
        transResourceApply.setLimitTimeOffer(true);
        transResourceApply.setLimitTimeOfferConfirmDate(Calendar.getInstance().getTime());
        saveOrUpdate(transResourceApply);
    }

    /**
     * 竞买人是否已经确认过进入限时竞价阶段了
     *
     * @param applyId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isConfirmedEnterLimitOffer(String applyId) {
        TransResourceApply transResourceApply = get(applyId);
        return transResourceApply.getLimitTimeOfferConfirmDate()!=null?true:false;
    }

    /**
     * 获取到竞买地块已经使用过的号牌
     *
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllUsedApplyNumber(String resourceId) {
        org.hibernate.Query query = hql("select distinct applyNumber from TransResourceApply t where t.resourceId=?");
        query.setString(0,resourceId);
        return query.list();
    }

    /**
     * 判断是否为有效号牌
     *
     * @param resourceId
     * @param applyNumber
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isValidApplyNumber(String resourceId, int applyNumber) {
        org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.applyNumber=?");
        query.setString(0,resourceId);
        query.setInteger(1, applyNumber);
        List queryList = query.list();
        return queryList!=null&&queryList.size()>0?false:true;
    }

    @Override
    @Transactional
    public TransResourceApply saveTransResourceApply(TransResourceApply transResourceApply) {

        return saveOrUpdate(transResourceApply);
    }




}
