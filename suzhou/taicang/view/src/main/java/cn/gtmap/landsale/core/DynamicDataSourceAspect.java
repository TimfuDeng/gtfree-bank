package cn.gtmap.landsale.core;

import cn.gtmap.landsale.datasource.DataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

/**
 * @作者 王建明
 * @创建日期 2015-10-26
 * @创建时间 10:21
 * @版本号 V 1.0
 */
@Aspect
public class DynamicDataSourceAspect {
	/**
	 * @作者 王建明
	 * @创建日期 2015-10-26
	 * @创建时间 15:14
	 * @描述 —— ca验证库service方法使用完毕之后清除数据源信息
	 */
	@AfterReturning(pointcut = "execution(* cn.gtmap.landsale.admin.service.impl.TransCaUserServiceImpl.*(..)))")
	public void doAfterReturning(JoinPoint jp) throws Throwable {
		DataSourceHolder.clearDataSourceType();
	}
}
