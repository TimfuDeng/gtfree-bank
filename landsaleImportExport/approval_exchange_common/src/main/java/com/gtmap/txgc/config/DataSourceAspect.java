package com.gtmap.txgc.config;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 *数据库切面
 * Created by liushaoshuai on 2018/1/4.
 */
@Deprecated
public class DataSourceAspect implements MethodBeforeAdvice,AfterReturningAdvice{

    /**
     * 方法调用完，清除数据源信息
     * @param o
     * @param method
     * @param objects
     * @param o1
     * @throws Throwable
     */
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        DataSourceContextHolder.clearDataSourceType();
    }

    /**
     * 设置每次调用方法的数据源
     * @param method
     * @param objects
     * @param o
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        if (method.isAnnotationPresent(DataSource.class)){
            DataSource dataSource =  method.getAnnotation(DataSource.class);
            DataSourceContextHolder.setDataSourceType(dataSource.name());
        }else{
            DataSourceContextHolder.setDataSourceType("commonDataSource");
        }

    }
}
