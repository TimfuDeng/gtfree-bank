package cn.gtmap.landsale.support;/*
 * Project:  hydroplat-parent
 * Module:   hydroplat-common
 * File:     cn.gtmap.landsale.support.FastjsonViewResolver.java
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

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 13-5-8
 */
public class FastjsonViewResolver implements ViewResolver {
    private View view = new FastjsonView();

    public void setView(View view) {
        this.view = view;
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }
}
