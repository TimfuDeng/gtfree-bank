package cn.gtmap.landsale.support;/*
 * Project:  hydroplat-parent
 * Module:   hydroplat-common
 * File:     cn.gtmap.landsale.support.FastjsonHttpMessageConverter.java
 * Modifier: yangxin
 * Modified: 2014-06-11 10:38
 *
 * Copyright (c) 2014 Mapjs All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent
 * or the registration of a utility model, design or code.
 */

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 11-12-29
 */
public class FastjsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final String UTF8 = "UTF-8".intern();

    public static final Charset DEFAULT_CHARSET = Charset.forName(UTF8);

    private String jsonpParameterName = "callback";

    public void setJsonpParameterName(String jsonpParameterName) {
        this.jsonpParameterName = jsonpParameterName;
    }

    public FastjsonHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(FileCopyUtils.copyToByteArray(inputMessage.getBody()), clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Helper.render(o, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(jsonpParameterName), new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET));
    }
}
