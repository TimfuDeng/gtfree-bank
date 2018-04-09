package cn.gtmap.landsale.log;

import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.RequestUtils;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransAuditLog;
import cn.gtmap.landsale.security.SecUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
import java.util.concurrent.Executors;

/**
 *
 *
 *
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/10
 */
@Aspect
public class AuditLogServiceAspect {
    /*private AuditLogService auditLogService;*/
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    /*public void setAuditLogService(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }*/

    @Pointcut("@annotation(cn.gtmap.landsale.log.AuditServiceLog)")
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
                /*auditLogService.saveAuditLog(transAuditLog);*/
            }
        });
    }

    private String getAuditContent(JoinPoint joinPoint,AuditServiceLog auditServiceLog){
        Map contentMap = Maps.newHashMap();
        String description = null;
        if(StringUtils.isNotBlank(auditServiceLog.description())){
            description = auditServiceLog.description();
        }else
            description = "操作内容";
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
                    || arg instanceof HttpServletRequest)
                continue;
            arguments.add(arg);
        }
        return arguments;
    }

}
