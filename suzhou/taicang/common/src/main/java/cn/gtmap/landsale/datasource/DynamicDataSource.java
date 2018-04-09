package cn.gtmap.landsale.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 15:24
 * @描述 —— 动态数据源选择器
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSourceType();
	}
}
