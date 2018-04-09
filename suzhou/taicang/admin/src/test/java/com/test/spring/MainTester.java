package com.test.spring;

import cn.gtmap.landsale.util.CommonDateParseUtil;

import java.util.Date;

/**
 * @作者 王建明
 * @创建日期 2017/5/3 0003
 * @创建时间 下午 4:33
 * @版本号 V 1.0
 */
public class MainTester {
	public static void main(String[] args) {
		Date lastDate = CommonDateParseUtil.getDateFromSourceDate(new Date(), -30);
		Date receiveDate = CommonDateParseUtil.string2date("2017-04-04",CommonDateParseUtil.YYYY_MM_DD);
		if (receiveDate.before(lastDate)) {
			System.out.println("ok");
		}
		System.out.println("over");
	}
}
