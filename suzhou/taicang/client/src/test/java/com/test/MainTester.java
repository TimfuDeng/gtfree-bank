package com.test;

import cn.gtmap.landsale.client.util.HTMLSpirit;
import org.springframework.web.util.HtmlUtils;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 王建明
 * @创建日期 2016/12/19 0019
 * @创建时间 下午 3:30
 * @版本号 V 1.0
 */
public class MainTester {
	public static void main(String[] args) throws Exception {
		String aaa = "?州双友汽?零部件有限公司91320585MA1NX5QY7H";
		String regEx = "[A-Za-z0-9]+$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(aaa);
		while (mat.find()) {
			String data = mat.group(0);
			System.out.println(data);
		}

		if(true)
			return;

		String title = "GGDK_GUID=4162CFA23AD2453BE055000000000001%22;%3C/script%3E%3Cscript%3Ealert(474)%3C/script%3E;;;'''''";
		title = URLDecoder.decode(title, "UTF-8");
		System.out.println(HTMLSpirit.delHTMLTag(title));
		System.out.println("over");
	}
}
