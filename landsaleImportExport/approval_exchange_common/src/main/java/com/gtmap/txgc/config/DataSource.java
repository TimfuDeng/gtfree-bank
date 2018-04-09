package com.gtmap.txgc.config;

import java.lang.annotation.*;

/**
 * Created by liushaoshuai on 2018/1/4.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    public static String industryDataSource = "industryDataSource";

    public static String commonDataSource = "commonDataSource";

    String name() default DataSource.commonDataSource;
}
