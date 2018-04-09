package cn.gtmap.landsale.datasource;

/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 15:07
 * @描述 —— 设置动态数据源
 */
public class DataSourceHolder {
	private static ThreadLocal<String> ds = new ThreadLocal<String>();

	public static void setDataSourceType(String type) {
		ds.set(type);
	}

	public static String getDataSourceType() {
		return ds.get();
	}

	public static void clearDataSourceType() {
		ds.remove();
	}
}
