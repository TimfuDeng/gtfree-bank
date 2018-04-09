package cn.gtmap.landsale.support;/*
 * Project:  hydroplat-parent
 * Module:   hydroplat-common
 * File:     cn.gtmap.landsale.support.Helper.java
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

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 13-5-9
 */
public class Helper {
    public static void render(Object obj, String jsonpCallback, Writer writer) throws IOException {
        SerializeWriter out = new SerializeWriter();
        out.config(SerializerFeature.DisableCircularReferenceDetect, true);
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.write(obj);
        try {
            if (StringUtils.isEmpty(jsonpCallback)) {
                out.writeTo(writer);
            } else {
                writer.write(jsonpCallback + "(");
                out.writeTo(writer);
                writer.write(");");
            }
            writer.flush();
        } finally {
            out.close();
        }
    }
}
