package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransAuditLog;
import cn.gtmap.landsale.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.unbescape.html.HtmlEscape;

import java.util.Date;

/**
 * 日志管理
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/12
 */
@Controller
@RequestMapping(value = "console/log")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @RequestMapping("list")
    public String list(@PageDefault(value=10) Pageable page,Date beginTime,Date endTime,Constants.LogProducer producer,
                       Constants.LogCategory category,Model model) {
        Page<TransAuditLog> transAuditLogList= auditLogService.findTransAuditLogs(beginTime,endTime,producer,
                category,page);
        model.addAttribute("transAuditLogList", transAuditLogList);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);
        if(producer!=null) model.addAttribute("producer",producer.name());
        if(category!=null) model.addAttribute("category", category.name());
        return "log-list";
    }

    @RequestMapping("content")
    public String logContent(Model model,@RequestParam(value = "auditId", required = true)String auditId) {
        TransAuditLog transAuditLog = auditLogService.getTransAuditLog(auditId);
        model.addAttribute("content", transAuditLog!=null? HtmlEscape.escapeHtml4Xml(transAuditLog.getContent()):"");
        return "log-content";
    }


}
