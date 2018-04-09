package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransAuditLog;

import java.util.Date;

/**
 * 审计日志服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/9
 */
public interface AuditLogService {

    /**
     * 获取日志对象
     * @param auditId
     * @return
     */
    TransAuditLog getTransAuditLog(String auditId);
    /**
     * 分页获取日志信息
     * @param beginTime 查询条件
     * @param endTime
     * @param request
     * @param producer 来源
     * @param category 类别
     * @return
     */
    Page<TransAuditLog> findTransAuditLogs(Date beginTime, Date endTime,Constants.LogProducer producer,
                                           Constants.LogCategory category,Pageable request);

    /**
     * 清除日志
     * @param startTime 起时间
     * @param endTime  至时间
     */
    void clearAuditLogs(Date startTime, Date endTime);

    /**
     * 保存审计日志
     * @param transAuditLog 日志对象
     */
    void saveAuditLog(TransAuditLog transAuditLog);

}
