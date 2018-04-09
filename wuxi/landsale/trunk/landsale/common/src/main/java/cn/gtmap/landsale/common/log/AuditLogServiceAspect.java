package cn.gtmap.landsale.common.log;

import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.RequestUtils;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransAuditLog;
import cn.gtmap.landsale.common.register.AuditLogClient;
import cn.gtmap.landsale.common.security.SecUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 日志切面
 * @author zsj
 * @version v1.0, 2017/10/11
 */
@Aspect
@Component
public class AuditLogServiceAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuditLogClient auditLogClient;

//    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private ExecutorService executor = new ScheduledThreadPoolExecutor(1,
        new BasicThreadFactory.Builder().namingPattern("logClient-%d").daemon(true).build());

    @Pointcut("@annotation(cn.gtmap.landsale.common.log.AuditServiceLog)")
    public void serviceAspect(){
    }

    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint){
        AuditServiceLog auditControllerLog = getControllerLog(joinPoint);
        auditLog(auditControllerLog.producer(), auditControllerLog.category(), getAuditContent(joinPoint,auditControllerLog));
    }

    public AuditServiceLog getControllerLog(JoinPoint joinPoint){
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        return method.getAnnotation(AuditServiceLog.class);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void auditLog(Constants.LogProducer producer,Constants.LogCategory category,String content){
        final TransAuditLog transAuditLog = new TransAuditLog();
        transAuditLog.setProducer(producer);
        transAuditLog.setCategory(category);
        transAuditLog.setContent(content);
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (sra != null) {
            transAuditLog.setIp(RequestUtils.getClientIP(sra.getRequest()));
        }
        transAuditLog.setUserId(SecUtil.getLoginUserId());
        transAuditLog.setUserViewName(SecUtil.getLoginUserViewName());
        executor.submit(new Runnable() {
            @Override
            public void run() {
                auditLogClient.saveAuditLog(transAuditLog);
            }
        });
    }

    private String getAuditContent(JoinPoint joinPoint,AuditServiceLog auditServiceLog){
        Map contentMap = Maps.newHashMap();
        String description = null;
        if(StringUtils.isNotBlank(auditServiceLog.description())){
            description = auditServiceLog.description();
        }else {
            description = "操作内容";
        }
        contentMap.put(description,filterArguments(joinPoint.getArgs()));
        return JSON.toJSONString(contentMap);
    }

    /**
     * 过滤掉一些无关的参数
     * @param args
     * @return
     */
    private Object filterArguments(Object[] args){
        List<Object> arguments = Lists.newArrayList();
        for(Object arg: args){
            if(arg instanceof Model || arg instanceof Pageable || arg instanceof RedirectAttributes || arg instanceof HttpServletResponse
                    || arg instanceof HttpServletRequest) {
                continue;
            }
            arguments.add(arg);
        }
        return arguments;
    }

}
