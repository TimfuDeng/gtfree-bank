package cn.gtmap.landsale.common.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.TransAuditLog;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * 日志 服务
 * @author zsj
 * @version v1.0, 2017/10/11
 */
@FeignClient(name = "core-server")
public interface AuditLogClient {

    /**
     * 获取日志对象
     * @param auditId
     * @return
     */
    @RequestMapping(value = "/log/getTransAuditLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransAuditLog getTransAuditLog(@RequestParam(value = "auditId") String auditId);

    /**
     * 分页获取日志信息
     * @param beginTime 查询条件
     * @param endTime
     * @param request
     * @param producer 来源
     * @param category 类别
     * @return
     */
    @RequestMapping(value = "/log/findTransAuditLogs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransAuditLog> findTransAuditLogs(@RequestParam("beginTime") Date beginTime, @RequestParam("endTime") Date endTime, @RequestParam("producer") String producer,
                                           @RequestParam("category") String category, @RequestBody Pageable request);

    /**
     * 清除日志
     * @param startTime 起时间
     * @param endTime  至时间
     */
    @RequestMapping(value = "/log/clearAuditLogs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void clearAuditLogs(@RequestParam("startTime") Date startTime, @RequestParam("endTime") Date endTime);

    /**
     * 保存日志
     * @param transAuditLog 日志对象
     */
    @RequestMapping(value = "/log/saveAuditLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void saveAuditLog(@RequestBody TransAuditLog transAuditLog);

}
