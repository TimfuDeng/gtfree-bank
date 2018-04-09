package com.gtmap.txgc.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by liushaoshuai on 2018/1/4.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * calls to one of various target DataSources based on a lookup key
     *重新设置数据源
     * 通过数据源的key值找找到对应的数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceType();
    }


}
