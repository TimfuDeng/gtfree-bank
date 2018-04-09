package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.data.dsl.DSL;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransAuditLog;
import cn.gtmap.landsale.service.AuditLogService;
import com.google.common.collect.Lists;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 审计日志服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/9
 */
public class AuditLogServiceImpl  extends HibernateRepo<TransAuditLog, String> implements AuditLogService {

    /**
     * 获取日志对象
     *
     * @param auditId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransAuditLog getTransAuditLog(String auditId) {
        return get(auditId);
    }

    /**
     * 分页获取日志信息
     *
     * @param beginTime 查询条件
     * @param endTime
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransAuditLog> findTransAuditLogs(Date beginTime, Date endTime,Constants.LogProducer producer,
                                           Constants.LogCategory category,Pageable request) {
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        if(beginTime!=null)
            criterionList.add(Restrictions.gt("createAt",beginTime));
        if(endTime!=null)
            criterionList.add(Restrictions.lt("createAt", endTime));
        if(producer!=null)
            criterionList.add(Restrictions.eq("producer", producer));
        if(category!=null)
            criterionList.add(Restrictions.eq("category",category));

        Criteria criteria = null;
        if(criterionList.size()>0)
            criteria = criteria(criterionList);
        else
            criteria = criteria();
        criteria.addOrder(Order.desc("createAt"));
        return find(criteria,request);
    }

    /**
     * 清除日志
     *
     * @param startTime 起时间
     * @param endTime   至时间
     */
    @Override
    @Transactional
    public void clearAuditLogs(Date startTime, Date endTime) {
        execute(d().where(DSL.gt("createAt", startTime), DSL.lt("createAt", endTime)));
    }

    /**
     * 保存审计日志
     *
     * @param transAuditLog 日志对象
     */
    @Override
    @Transactional
    public void saveAuditLog(final TransAuditLog transAuditLog) {
        if(transAuditLog.getCreateAt()==null)
            transAuditLog.setCreateAt(new Date());
       save(transAuditLog);
    }
}
