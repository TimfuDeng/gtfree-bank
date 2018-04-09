package cn.gtmap.landsale.client.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 王建明
 * @创建日期 2016/12/19 0019
 * @创建时间 下午 3:29
 * @版本号 V 1.0
 */
public class HTMLSpirit {
	public static String delHTMLTag(String htmlStr){
		if(StringUtils.isBlank(htmlStr)) {
			return "";
		}
		String regExScript="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
		String regExStyle="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
		String regExHtml="<[^>]+>"; //定义HTML标签的正则表达式

		Pattern pScript=Pattern.compile(regExScript,Pattern.CASE_INSENSITIVE);
		Matcher mScript=pScript.matcher(htmlStr);
		htmlStr=mScript.replaceAll(""); //过滤script标签

		Pattern pStyle=Pattern.compile(regExStyle,Pattern.CASE_INSENSITIVE);
		Matcher mStyle=pStyle.matcher(htmlStr);
		htmlStr=mStyle.replaceAll(""); //过滤style标签

		Pattern pHtml=Pattern.compile(regExHtml,Pattern.CASE_INSENSITIVE);
		Matcher mHtml=pHtml.matcher(htmlStr);
		htmlStr=mHtml.replaceAll(""); //过滤html标签

		htmlStr = htmlStr.replaceAll("[(/>)<\"\\;\']", "");
		return htmlStr.trim(); //返回文本字符串
	}
}
