package cn.gtmap.landsale.admin.config;

import cn.gtmap.egovplat.core.entity.EntityFilter;
import cn.gtmap.egovplat.core.entity.EntityFilterChain;
import cn.gtmap.egovplat.core.support.hibernate.EntityFilterListener;
import cn.gtmap.egovplat.core.support.jpa.SessionFactoryFactoryBean;
import cn.gtmap.egovplat.core.support.spring.ConversionService;
import cn.gtmap.egovplat.core.support.spring.DataPageableHandlerMethodArgumentResolver;
import cn.gtmap.egovplat.core.support.spring.DateToStringConverter;
import cn.gtmap.egovplat.core.support.spring.StringToDateConverter;
import cn.gtmap.landsale.common.log.AuditEntityFilter;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecContextInterceptor;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Set;

/**
* Bean注册
* @author zsj
* @version v1.0, 2017/9/8
*/
@Configuration
public class BeanResolverConfiguer extends WebMvcConfigurerAdapter {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(getDataPageableHandlerMethodArgumentResolver());
    }

    @Bean
    public DataPageableHandlerMethodArgumentResolver getDataPageableHandlerMethodArgumentResolver() {
        return new DataPageableHandlerMethodArgumentResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SecContextInterceptor secContextInterceptor = new SecContextInterceptor();
        secContextInterceptor.setRedirectUrl("/login");
        secContextInterceptor.setNeedLogins(new String[] {"/**"});
        secContextInterceptor.setExcludes(new String[] {"/login", "/logout", "/file/**", "/report/**", "/resourceContainer/*"});
        registry.addInterceptor(secContextInterceptor);
        super.addInterceptors(registry);
    }

    @Bean
    public SessionFactoryFactoryBean getSessionFactoryFactoryBean() {
        SessionFactoryFactoryBean sessionFactoryFactoryBean = new SessionFactoryFactoryBean();
        sessionFactoryFactoryBean.setEntityManagerFactory(entityManagerFactory);
        return sessionFactoryFactoryBean;
    }

    @Bean
    public EntityFilterListener getEntityFilterListener() {
        EntityFilterListener entityFilterListener = new EntityFilterListener();
        EntityFilterChain entityFilterChain = new EntityFilterChain();

        List<EntityFilter> filters = Lists.newArrayList();
        filters.add(getAuditEntityFilter());

        entityFilterChain.setFilters(filters);
        entityFilterListener.setFilter(entityFilterChain);
        return entityFilterListener;
    }

    @Bean
    public AuditEntityFilter getAuditEntityFilter() {
        AuditEntityFilter auditEntityFilter = new AuditEntityFilter();
        List<Class> auditClasses = Lists.newArrayList();
        auditClasses.add(TransRole.class);
        auditClasses.add(TransUser.class);
        auditClasses.add(TransBankConfig.class);
        auditClasses.add(TransBank.class);
        auditClasses.add(TransUserRole.class);
        auditClasses.add(TransRoleRegionOperation.class);
        auditClasses.add(TransResource.class);
        auditClasses.add(TransCrgg.class);
        auditEntityFilter.setAuditClasses(auditClasses);
        return auditEntityFilter;
    }

    @Bean
    public ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer() {
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = new ConfigurableWebBindingInitializer();
        configurableWebBindingInitializer.setConversionService(getConversionService());
        return configurableWebBindingInitializer;
    }

    @Bean
    public ConversionService getConversionService() {
        Set<Converter> converters = Sets.newHashSet();
        ConversionService conversionService = new ConversionService();
        converters.add(new DateToStringConverter());
        converters.add(new StringToDateConverter());
        conversionService.setConverters(converters);
        return conversionService;
    }

}