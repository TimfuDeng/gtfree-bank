package cn.gtmap.landsale.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CommonDateParseUtil {
	public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDD_HH_MM = "yyyy/MM/dd HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String HH_MM = "HH:mm";
	public static final String YYYY = "yyyy";
	public static final String MM = "MM";
	public static final String HH = "HH";

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-7-13
	 * @创建时间 下午12:22:40
	 * @描述 —— 格式化日期对象
	 */
	public static Date date2date(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String str = sdf.format(date);
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
		return date;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-7-13
	 * @创建时间 下午12:24:19
	 * @描述 —— 时间对象转换成字符串
	 */
	public static String date2string(Date date, String formatStr) {
		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		strDate = sdf.format(date);
		return strDate;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-7-13
	 * @创建时间 下午12:24:19
	 * @描述 —— sql时间对象转换成字符串
	 */
	public static String timestamp2string(Timestamp timestamp, String formatStr) {
		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		strDate = sdf.format(timestamp);
		return strDate;
	}

	/**
	 * @param dateString
	 * @param formatStr
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-7-13
	 * @创建时间 下午1:09:24
	 * @描述 —— 字符串转换成时间对象
	 */
	public static Date string2date(String dateString, String formatStr) {
		Date formateDate = null;
		DateFormat format = new SimpleDateFormat(formatStr);
		try {
			formateDate = format.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
		return formateDate;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-10-10
	 * @创建时间 上午09:18:36
	 * @描述 —— Date类型转换为Timestamp类型
	 */
	public static Timestamp date2timestamp(Date date) {
		if (date == null)
			return null;
		return new Timestamp(date.getTime());
	}

	/**
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-9-13
	 * @创建时间 下午05:02:57
	 * @描述 —— 获得当前年份
	 */
	public static String getNowYear() {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
		return sdf.format(new Date());
	}

	/**
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-9-13
	 * @创建时间 下午05:03:15
	 * @描述 —— 获得当前月份
	 */
	public static String getNowMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat(MM);
		return sdf.format(new Date());
	}

	/**
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-04-26
	 * @创建时间 13:30:33
	 * @描述 —— 获得当前小时
	 */
	public static int getNowHour() {
		SimpleDateFormat sdf = new SimpleDateFormat(HH);
		return Integer.valueOf(sdf.format(new Date()));
	}

	/**
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-04-26
	 * @创建时间 13:30:33
	 * @描述 —— 获得当前星期(1-7 MON-SUN)
	 */
	public static int getWeekOfDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = (cal.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : cal
				.get(Calendar.DAY_OF_WEEK) - 1;
		return w;
	}

	/**
	 * @param time
	 * @return
	 * @作者 王建明
	 * @创建日期 2012-6-17
	 * @创建时间 上午10:19:31
	 * @描述 —— 指定时间距离当前时间的中文信息
	 */
	public static String getLnow(long time) {
		Calendar cal = Calendar.getInstance();
		long timel = cal.getTimeInMillis() - time;
		if (timel / 1000 < 60) {
			return "1分钟以内";
		} else if (timel / 1000 / 60 < 60) {
			return timel / 1000 / 60 + "分钟前";
		} else if (timel / 1000 / 60 / 60 < 24) {
			return timel / 1000 / 60 / 60 + "小时前";
		} else {
			return timel / 1000 / 60 / 60 / 24 + "天前";
		}
	}

	/**
	 * @param currentDate
	 * @param num
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-6-8
	 * @创建时间 下午2:44:24
	 * @描述 —— 获取指定日期距离num天的日期
	 */
	public static Date getDateFromSourceDate(Date currentDate, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, num);
		return cal.getTime();
	}

	/**
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-01-24
	 * @创建时间 08:53:51
	 * @描述 —— 获取本月第一天
	 */
	public static String firstDayOfMonth() {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date beginTime = cal.getTime();
		String beginTime1 = datef.format(beginTime) + " 00:00:00";
		return beginTime1;
	}

	/**
	 * @param year
	 * @param month
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-07-03
	 * @创建时间 15:53:21
	 * @描述 —— 获取某年某月的第一天
	 */
	public static Date getFirstDayOfYearMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * @param year
	 * @param quarter
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-07-03
	 * @创建时间 16:02:56
	 * @描述 —— 获取某年某季度的第一天
	 */
	public static Date getFirstDayOfYearQuarter(int year, int quarter) {
		int month = 1;
		if (quarter == 1)
			month = 1;
		else if (quarter == 2)
			month = 4;
		else if (quarter == 3)
			month = 7;
		else if (quarter == 4)
			month = 10;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		// 当前月的第一天
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-01-24
	 * @创建时间 08:54:06
	 * @描述 —— 获取本月最后一天
	 */
	public static String lastDayOfMonth() {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		Date endTime = cal.getTime();
		String endTime1 = datef.format(endTime) + " 23:59:59";
		return endTime1;
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @作者 陈康平
	 * @创建日期 Jul 3, 2013
	 * @创建时间 8:55:52 PM
	 * @描述 —— 获取当前日期在本周内的最后一天(周日)
	 */
	public static int lastDayOfWeek(Date date) {
		/**
		 * 详细设计： 1.如果date是星期日，则加0天 2.如果date是星期一，则加6天 3.如果date是星期二，则加5天
		 * 4.如果date是星期三，则加4天 5.如果date是星期四，则加3天 6.如果date是星期五，则加2天
		 * 7.如果date是星期六，则加1天
		 */
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY  ):
			cal.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY  ):
			cal.add(Calendar.DATE, 6);
			break;
		case (Calendar.TUESDAY  ):
			cal.add(Calendar.DATE, 5);
			break;
		case (Calendar.WEDNESDAY  ):
			cal.add(Calendar.DATE, 4);
			break;
		case (Calendar.THURSDAY  ):
			cal.add(Calendar.DATE, 3);
			break;
		case (Calendar.FRIDAY  ):
			cal.add(Calendar.DATE, 2);
			break;
		case (Calendar.SATURDAY  ):
			cal.add(Calendar.DATE, 1);
			break;
		}
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * @param date
	 * @return
	 * @作者 陈康平
	 * @创建日期 Jul 3, 2013
	 * @创建时间 9:23:40 PM
	 * @描述 —— 获取当前日期在本周内的第一天(周一)
	 */
	public static int firstDayOfWeek(Date date) {

		/**
		 * 详细设计： 1.如果date是星期日，则减6天 2.如果date是星期一，则减0天 3.如果date是星期二，则减1天
		 * 4.如果date是星期三，则减2天 5.如果date是星期四，则减3天 6.如果date是星期五，则减4天
		 * 7.如果date是星期六，则减5天
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY  ):
			gc.add(Calendar.DATE, -6);
			break;
		case (Calendar.MONDAY  ):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.TUESDAY  ):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.WEDNESDAY  ):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.THURSDAY  ):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.FRIDAY  ):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.SATURDAY  ):
			gc.add(Calendar.DATE, -5);
			break;
		}
		return gc.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param year
	 * @param month
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-07-03
	 * @创建时间 15:56:02
	 * @描述 —— 获取某年某月的最后一天
	 */
	public static Date getLastDayOfYearMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * @param year
	 * @param quarter
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-07-03
	 * @创建时间 16:04:04
	 * @描述 —— 获取某年某季度的最后一天
	 */
	public static Date getLastDayOfYearQuarter(int year, int quarter) {
		int month = 1;
		if (quarter == 1)
			month = 3;
		else if (quarter == 2)
			month = 6;
		else if (quarter == 3)
			month = 9;
		else if (quarter == 4)
			month = 12;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		// 当前月的最后一天
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * @param date1
	 * @param date2
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-20
	 * @创建时间 14:08:37
	 * @描述 —— 计算两个日期相隔多少天
	 */
	public static long differ(Date date1, Date date2) {
		Long differLong = date2.getTime() / 86400000 - date1.getTime()
				/ 86400000; // 用立即数，减少乘法计算的开销
		return Math.abs(differLong);
	}

	/**
	 * @param date1
	 * @param date2
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-20
	 * @创建时间 14:08:37
	 * @描述 —— 计算两个日期相隔多少分钟
	 */
	public static long differMinute(Date date1, Date date2) {
		Long differLong = (date2.getTime() - date1.getTime()) / 60000; // 用立即数，减少乘法计算的开销
		return Math.abs(differLong);
	}
	
	/**
	 * @param currentDate
	 * @param num
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-07-06
	 * @创建时间 20:10:16
	 * @描述 —— 获取相距指定时间n分钟的时刻
	 */
	public static Date getDateFromSourceDateWithMinute(Date currentDate, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.MINUTE, num);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-06
	 * @创建时间 10:47:36
	 * @描述 —— 获取日期为该月的第几周
	 */
	public static int getWeekInMonthNum(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(getDateFromSourceDate(date, -1));
		int week = cal.get(Calendar.WEEK_OF_MONTH);// 获取是本月的第几周
		return week;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-17
	 * @创建时间 17:23:50
	 * @描述 —— 根据日期获取月份值
	 */
	public static int getMonthByDate(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-17
	 * @创建时间 17:22:56
	 * @描述 —— 根据日期获取相应的季度值
	 */
	public static int getQuarterByDate(Date date) {
		int quarter;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		if (month == 1 || month == 2 || month == 3) {
			quarter = 1;
		} else if (month == 4 || month == 5 || month == 6) {
			quarter = 2;
		} else if (month == 7 || month == 8 || month == 9) {
			quarter = 3;
		} else
			quarter = 4;
		return quarter;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-17
	 * @创建时间 17:42:46
	 * @描述 —— 根据日期获取相应的年份
	 */
	public static int getYearByDate(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-17
	 * @创建时间 17:23:50
	 * @描述 —— 根据日期获取月份值
	 */
	public static int getMonthByDateForWeek(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int lastDay = CommonDateParseUtil.lastDayOfWeek(date);
		if (lastDay < day) {
			cal.add(Calendar.MONTH, 1);
			return cal.get(Calendar.MONTH) + 1;
		} else {
			return month;
		}

	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-17
	 * @创建时间 17:22:56
	 * @描述 —— 根据日期获取相应的季度值
	 */
	public static int getQuarterByDateForWeek(Date date) {
		int quarter;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		int increment = 0;
		if (month == 3 || month == 6 || month == 9 || month == 12) {
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int lastday = CommonDateParseUtil.lastDayOfWeek(date);
			if (lastday < day) {
				increment++;
			}
		}
		if (month == 1 || month == 2 || month == 3) {
			quarter = 1 + increment;
		} else if (month == 4 || month == 5 || month == 6) {
			quarter = 2 + increment;
		} else if (month == 7 || month == 8 || month == 9) {
			quarter = 3 + increment;
		} else
			quarter = 4 + increment;
		if (quarter > 4) {
			quarter = 1;
		}
		return quarter;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-17
	 * @创建时间 17:42:46
	 * @描述 —— 根据日期获取相应的年份
	 */
	public static int getYearByDateForWeek(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		// int week = cal.get(Calendar.WEEK_OF_MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (month == 12) {
			int lastDay = CommonDateParseUtil.lastDayOfWeek(date);
			if (lastDay < day) {
				year++;
			}
		}
		return year;
	}

	/**
	 * @param date
	 * @return
	 * @作者 王建明
	 * @创建日期 2013-06-06
	 * @创建时间 10:47:36
	 * @描述 —— 获取日期为该月的第几周
	 */
	public static int getWeekByDateForWeek(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(getDateFromSourceDate(date, -1));
		int week = cal.get(Calendar.WEEK_OF_MONTH);// 获取是本月的第几周
		int day = cal.get(Calendar.DAY_OF_MONTH);
		//
		// if(week == 1){
		// int firstDay = CommonDateParseUtil.firstDayOfWeek(date);
		// if(firstDay > day){
		//				
		// }
		// }else{
		int lastDay = CommonDateParseUtil.lastDayOfWeek(date);
		if (lastDay < day) {
			week = 1;
		}
		// }
		return week;
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
		String date = "2013-07-01";
		System.out.println("输入日期为：" + date);
		System.out.println("获取日期为该月的第几周:  "
				+ CommonDateParseUtil.getWeekByDateForWeek(datef.parse(date)));
		System.out.println("获取日期获取相应的年份:  "
				+ CommonDateParseUtil.getYearByDateForWeek(datef.parse(date)));
		System.out.println("获取日期获取相应的季度:  "
				+ CommonDateParseUtil
						.getQuarterByDateForWeek(datef.parse(date)));
		System.out.println("获取日期获取月份:  "
				+ CommonDateParseUtil.getMonthByDateForWeek(datef.parse(date)));
	}
}
