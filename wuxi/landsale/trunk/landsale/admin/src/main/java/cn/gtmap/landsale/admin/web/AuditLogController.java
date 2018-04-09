package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.AuditLogClient;
import cn.gtmap.landsale.common.model.TransAuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.unbescape.html.HtmlEscape;

/**
 * 日志管理
 *
 * @author cxm
 * @version v1.0, 2017/10/17
 */
@Controller
@RequestMapping("/log")
public class AuditLogController {
    @Autowired
    AuditLogClient auditLogClient;

    @RequestMapping("index")
    public String index(@PageDefault(value = 10) Pageable page, String beginTime, String endTime, String producer,
                       String category, Model model) {
        Page<TransAuditLog> transAuditLogList = auditLogClient.findTransAuditLogs(beginTime, endTime, producer, category, page);
        model.addAttribute("transAuditLogList", transAuditLogList);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);
        if (producer != null) {
            model.addAttribute("producer", producer);
        }
        if (category != null) {
            model.addAttribute("category", category);
        }
        return "log/log-list";
    }

    @RequestMapping("content")
    public String logContent(Model model, @RequestParam(value = "auditId", required = true) String auditId) {
        TransAuditLog transAuditLog = auditLogClient.getTransAuditLog(auditId);
        model.addAttribute("content", transAuditLog != null ? HtmlEscape.escapeHtml4Xml(transAuditLog.getContent()) : "");
        return "log/log-content";
    }

}
