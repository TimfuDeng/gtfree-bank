package cn.gtmap.landsale.core.config;

import cn.gtmap.egovplat.core.support.http.HttpClientFactoryBean;
import cn.gtmap.egovplat.core.support.spring.DataPageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Bean注册
 * @author zsj
 * @version v1.0, 2017/9/8
 */
@Configuration
public class BeanResolverConfiguer extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(getDataPageableHandlerMethodArgumentResolver());
    }

    @Bean
    DataPageableHandlerMethodArgumentResolver getDataPageableHandlerMethodArgumentResolver() {
        return new DataPageableHandlerMethodArgumentResolver();
    }

    @Bean
    HttpClientFactoryBean httpClientFactoryBean() {
        HttpClientFactoryBean httpClientFactoryBean = new HttpClientFactoryBean();
        httpClientFactoryBean.setMaxTotalConnections(200);
        httpClientFactoryBean.setTimeout(600000);
        return httpClientFactoryBean;
    }

//    @Bean
//    public CommonsMultipartResolver getCommonsMultipartResolver() {
//        return new CommonsMultipartResolver();
//    }

}