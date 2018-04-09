package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransAuditLog;
import cn.gtmap.landsale.core.service.AuditLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
* 日志服务
* @author zsj
* @version v1.0, 2017/10/11
*/
@RestController
@RequestMapping("/log")
public class AuditLogController {

    @Autowired
    AuditLogService auditLogService;

    /**
     * 获取日志对象
     * @param auditId
     * @return
     */
    @RequestMapping("/getTransAuditLog")
    public TransAuditLog getTransAuditLog(@RequestParam("auditId") String auditId) {
        return auditLogService.getTransAuditLog(auditId);
    }

    /**
     * 分页获取日志信息
     * @param beginTime 查询条件
     * @param endTime
     * @param request
     * @param producer 来源
     * @param category 类别
     * @return
     */
    @RequestMapping("/findTransAuditLogs")
    Page<TransAuditLog> findTransAuditLogs(@RequestParam(value = "beginTime",required = false) String beginTime, @RequestParam(value = "endTime",required = false) String endTime, @RequestParam(value = "producer",required = false) String producer,
                                           @RequestParam(value = "category",required = false) String category, @RequestBody Pageable request) throws ParseException {
        Constants.LogCategory logCategory = null;
        Constants.LogProducer logProducer = null;
        if (StringUtils.isNotBlank(producer)) {
            logProducer =  Constants.LogProducer.valueOf(producer);
        }
        if (StringUtils.isNotBlank(category)) {
            logCategory =  Constants.LogCategory.valueOf(category);
        }
        return auditLogService.findTransAuditLogs(beginTime, endTime, logProducer, logCategory, request);
    }

    /**
     * 清除日志
     * @param startTime 起时间
     * @param endTime  至时间
     */
    @RequestMapping("/clearAuditLogs")
    public void clearAuditLogs(@RequestParam("startTime") Date startTime, @RequestParam("endTime") Date endTime) {
        auditLogService.clearAuditLogs(startTime, endTime);
    }

    /**
     * 保存日志
     * @param transAuditLog 日志对象
     */
    @RequestMapping("/saveAuditLog")
    public void saveAuditLog(@RequestBody TransAuditLog transAuditLog) {
        auditLogService.saveAuditLog(transAuditLog);
    }

}

