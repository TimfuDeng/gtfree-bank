package cn.gtmap.landsale.main;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.service.*;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @作者 王建明
 * @创建日期 2015/6/30
 * @创建时间 14:43
 * @版本号 V 1.0
 */
public class MainTester {
	private final static String QUEUE_NAME = "gits_test";
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");
	private static ClientService clientService = applicationContext.getBean("ClientServiceImpl", ClientService.class);
	private static TransResourceService transResourceService = applicationContext.getBean("TransResourceServiceImpl", TransResourceService.class);
	private static TransUserService transUserService = applicationContext.getBean("TransUserServiceImpl", TransUserService.class);
	private static TransResourceApplyService transResourceApplyService = applicationContext.getBean("TransResourceApplyServiceImpl", TransResourceApplyService.class);
	private static TransBankAccountService transBankAccountService = applicationContext.getBean("TransBankAccountServiceImpl", TransBankAccountService.class);
	private static TransBankInterfaceService transBankInterfaceService = applicationContext.getBean("TransBankInterfaceServiceImpl", TransBankInterfaceService.class);
	private static RegionService regionService = applicationContext.getBean("RegionServiceImpl", RegionService.class);

	/**
	 * @作者 王建明
	 * @创建日期 2015/6/30
	 * @创建时间 16:26
	 * @描述 —— 银行接口调试专用
	 */
	public static void main(String[] args) throws Exception {
		// postForSubAccount();
		// postForGetDetail();

		// postDataToRabbitMQ();

		// testSQLLiteOperate();

		// testDaoZhangTongZhi();

		// postForCrggData();

		System.out.println(regionService.getDefaultRegionCode());

		System.exit(0);
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015-12-01
	 * @创建时间 15:47
	 * @描述 —— 请求获取出让公告数据
	 */
	private static void postForCrggData() {
		String requestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<LandjsRequest><Action>31</Action><BusinessType>12</BusinessType><PageSize>5</PageSize><BusinessWhere>XZQ_DM = '320583' order by AFFICHE_DATE desc</BusinessWhere><CurrentPage>1</CurrentPage><Data/></LandjsRequest>";

		String requestUrl = "http://www.landjs.com/LandjsInterface/LandjsStandard.asmx/LandjsRequest";
		String username = "gtmap";
		String password = "123456";

		Map params = new HashMap();
		params.put("userName",username);
		params.put("passWord",password);
		params.put("requestXML",requestXml);

		postForCrggInfo(requestXml, requestUrl, username, password);
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015-11-11
	 * @创建时间 10:32
	 * @描述 —— 获取出让公告内容
	 */
	private static void postForCrggInfo(String requestXml, String requestUrl, String username, String password) {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(30000, TimeUnit.MILLISECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);
		RequestConfig config = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(60000).build();
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpost = new HttpPost(requestUrl);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userName", username));
			params.add(new BasicNameValuePair("passWord", password));
			params.add(new BasicNameValuePair("requestXML", requestXml));
			httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = closeableHttpClient.execute(httpost);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(entity, "UTF-8");
				System.out.println(result);
				Document doc = DocumentHelper.parseText(result);
			} else {
				throw new AppException(8101);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015-11-06
	 * @创建时间 14:53
	 * @描述 —— 测试到帐通知
	 */
	private static void testDaoZhangTongZhi() {
		String receiveXml = "<?xml version='1.0' encoding='GBK'?><root><head><TransCode>G00001</TransCode><TransDate>01511</TransDate><TransTime>61443</TransTime><SeqNo>2015110614431300145</SeqNo></head><body><InDate>12</InDate><InstCode>4280900847889094232</InstCode><InMemo>99984788909423000613</InMemo><InAmount>3</InAmount><InName>4</InName><InAcct>5</InAcct><InBankFLCode>6</InBankFLCode></body></root>";
		receiveXml = stringLengthFormate(receiveXml.getBytes(Charset.forName("GBK")).length) + receiveXml;
		transBankInterfaceService.socketServerReceive(receiveXml);
	}

	private static String stringLengthFormate(int length) {
		String result = String.valueOf(length);
		if (String.valueOf(length).length() < 6) {
			for (int i = result.length(); i < 6; i++) {
				result = "0" + result;
			}
		} else {
			return String.valueOf(length).substring(0, 5);
		}
		return result;
	}
}
