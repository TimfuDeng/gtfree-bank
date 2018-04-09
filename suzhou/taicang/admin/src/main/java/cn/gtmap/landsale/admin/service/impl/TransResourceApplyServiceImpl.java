package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.TransResourceApplyService;
import com.google.common.collect.Maps;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public class TransResourceApplyServiceImpl extends HibernateRepo<TransResourceApply, String> implements TransResourceApplyService {


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
    @Transactional
    public TransResourceApply saveTransResourceApply(TransResourceApply transResourceApply) {

        return saveOrUpdate(transResourceApply);
    }

	@Override
	@Transactional(readOnly = true)
	public List<TransResourceApply> getCommitTransResourceApply(String resourceId) {
		org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.trialType != ?");
		query.setParameter(0,resourceId);
		query.setParameter(1,Constants.TrialType.NONE_COMMIT_TRIAL);
		return query.list();
	}



	@Override
	@Transactional(readOnly = true)
	public List<TransResourceApply> getCommitToTransResourceApply(
			String resourceId) {
		org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.trialType = ?");
		query.setParameter(0,resourceId);
		query.setParameter(1, Constants.TrialType.COMMIT_TO_TRIAL);
		return query.list();
	}



	@Override
	@Transactional(readOnly = true)
	public List<TransResourceApply> getPassedTransResourceApply(
			String resourceId) {
		org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.trialType = ?");
		query.setParameter(0,resourceId);
		query.setParameter(1, Constants.TrialType.PASSED_TRIAL);
		return query.list();
	}



	@Override
	@Transactional(readOnly = true)
	public List<TransResourceApply> getBailPaidTransResourceApply(
			String resourceId) {
		org.hibernate.Query query = hql("from TransResourceApply t where t.resourceId=? and t.applyStep = ?");
		query.setParameter(0,resourceId);
		query.setParameter(1, Constants.StepOver);
		return query.list();
	}

    @Override
    @Transactional
    public void delete(String applyId) {
        deleteById(applyId);
    }
}
