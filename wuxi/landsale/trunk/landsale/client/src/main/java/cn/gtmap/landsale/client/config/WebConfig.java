package cn.gtmap.landsale.client.config;

import cn.gtmap.egovplat.core.support.fastjson.FastjsonHttpMessageConverter;
import cn.gtmap.egovplat.core.support.spring.DataPageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *web配置
 * Created by liushaoshuai on 2017/7/10.
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new DataPageableHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {


        List<MediaType> mediaTypeList = new ArrayList();
        mediaTypeList.add(new MediaType("text","html", Charset.defaultCharset()));
        mediaTypeList.add(new MediaType("text","plain", Charset.defaultCharset()));
        mediaTypeList.add(new MediaType("application","json", Charset.defaultCharset()));
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
        converters .add(stringHttpMessageConverter);
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new FastjsonHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

/*    *//**
     * {@inheritDoc}
     * <p>This implementation is empty.
     *
     * @param registry
     *//*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*//**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }*/
}
