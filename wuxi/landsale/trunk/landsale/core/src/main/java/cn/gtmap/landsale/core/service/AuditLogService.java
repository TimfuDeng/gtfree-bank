package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransAuditLog;

import java.text.ParseException;
import java.util.Date;

/**
 * 日志服务
 * @author zsj
 * @version v1.0, 2017/10/11
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
     * @throws ParseException
     */
    Page<TransAuditLog> findTransAuditLogs(String beginTime, String endTime, Constants.LogProducer producer,
                                           Constants.LogCategory category, Pageable request) throws ParseException;

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
