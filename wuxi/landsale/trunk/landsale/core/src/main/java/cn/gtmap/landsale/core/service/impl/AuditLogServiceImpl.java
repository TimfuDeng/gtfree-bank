package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.data.dsl.DSL;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransAuditLog;
import cn.gtmap.landsale.core.service.AuditLogService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日志服务
 * @author zsj
 * @version v1.0, 2017/10/11
 */
@Service
public class AuditLogServiceImpl extends HibernateRepo<TransAuditLog, String> implements AuditLogService {

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
    public Page<TransAuditLog> findTransAuditLogs(String beginTime, String endTime,Constants.LogProducer producer,
                                           Constants.LogCategory category,Pageable request) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNoneBlank(beginTime)) {
            criterionList.add(Restrictions.gt("createAt", simpleDateFormat.parse(beginTime)));
        }
        if(StringUtils.isNoneBlank(endTime)) {
            criterionList.add(Restrictions.lt("createAt", simpleDateFormat.parse(endTime)));
        }
        if(producer!=null) {
            criterionList.add(Restrictions.eq("producer", producer));
        }
        if(category!=null) {
            criterionList.add(Restrictions.eq("category", category));
        }

        Criteria criteria = null;
        if(criterionList.size()>0) {
            criteria = criteria(criterionList);
        } else {
            criteria = criteria();
        }
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
    @Transactional(rollbackFor = Exception.class)
    public void clearAuditLogs(Date startTime, Date endTime) {
        execute(d().where(DSL.gt("createAt", startTime), DSL.lt("createAt", endTime)));
    }

    /**
     * 保存审计日志
     *
     * @param transAuditLog 日志对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAuditLog(final TransAuditLog transAuditLog) {
        if(transAuditLog.getCreateAt()==null) {
            transAuditLog.setCreateAt(new Date());
        }
       save(transAuditLog);
    }
}
