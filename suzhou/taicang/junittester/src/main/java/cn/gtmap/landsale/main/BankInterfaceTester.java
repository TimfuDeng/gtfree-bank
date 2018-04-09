package cn.gtmap.landsale.main;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.ClientSocketUtil;
import cn.gtmap.landsale.util.CommonDateParseUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @作者 王建明
 * @创建日期 2015-11-05
 * @创建时间 9:37
 * @版本号 V 1.0
 */
public class BankInterfaceTester {
	private static BufferedReader br;

	/**
	 * @作者 王建明
	 * @创建日期 2015/6/30
	 * @创建时间 16:26
	 * @描述 —— 银行接口调试专用
	 */
	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		System.out.print("操作列表：\n1——链路测试\n2——子账号请求\n3——到帐通知监听\n4——查询交易明细\n请输入操作数字:");
		str = br.readLine();

		if ("1".equals(str)) {
			postForLianLu();
		} else if ("2".equals(str)) {
			postForSubAccount();
		} else if ("3".equals(str)) {
			System.out.println("等待银行发送到帐通知，测试结束后请按Ctrl+C退出当前窗口！");
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");

			while (true) {
				Thread.sleep(500000);
			}
		} else if ("4".equals(str)) {
			postForGetDetail();
		}

		System.out.println("测试完成，退出调试程序。");

		br.close();
		System.exit(0);
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015-11-06
	 * @创建时间 9:40
	 * @描述 —— 链路请求
	 */
	private static void postForLianLu() throws Exception {
		String postInfo = "<?xml version='1.0' encoding='GBK'?><root><head><TransCode>G00009</TransCode><TransDate>20120706</TransDate><TransTime>155842</TransTime><SeqNo>50370</SeqNo></head><body></body></root>";
		postInfo = stringLengthFormate(postInfo.getBytes(Charset.forName("GBK")).length) + postInfo;

		String host = null;
		System.out.print("请输入IP地址:");
		host = br.readLine();
		System.out.print("请输入端口号:");
		String portStr = br.readLine();
		int port = Integer.parseInt(portStr);
		ClientSocketUtil clientSocketUtil = new ClientSocketUtil(host, port);
		clientSocketUtil.send(postInfo);
		try {
			String returnXml = clientSocketUtil.recieve();
			System.out.println("银行返回数据解析后的最后结果：" + returnXml);
			Document doc = DocumentHelper.parseText(returnXml);
			if("00".equals(getElementValue(doc, "//body/Result"))){
				System.out.println("链路测试成功！");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			clientSocketUtil.close();
		}
	}

	private static String getElementValue(Document doc,String path){
		Element element=(Element) doc.selectSingleNode(path);
		if (element!=null){
			return element.getTextTrim();
		}else{
			return null;
		}
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015-08-24
	 * @创建时间 12:09
	 * @描述 —— 请求银行子账号测试
	 */
	private static void postForSubAccount() throws Exception {
		String host = null;
		System.out.print("请输入IP地址:");
		host = br.readLine();
		System.out.print("请输入端口号:");
		String portStr = br.readLine();
		int port = Integer.parseInt(portStr);

		System.out.print("请输入银行主账号:");
		String bankCode = br.readLine();

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");

		ClientService clientService = applicationContext.getBean("ClientServiceImpl", ClientService.class);
		TransResourceApplyService transResourceApplyService = applicationContext.getBean("TransResourceApplyServiceImpl", TransResourceApplyService.class);
		TransResourceService transResourceService = applicationContext.getBean("TransResourceServiceImpl", TransResourceService.class);
		TransBankService transBankService = applicationContext.getBean("TransBankServiceImpl", TransBankService.class);
		TransBankAccountService transBankAccountService = applicationContext.getBean("TransBankAccountServiceImpl", TransBankAccountService.class);
		RegionService regionService = applicationContext.getBean("RegionServiceImpl", RegionService.class);


		Date nowDate = new Date();
		String regionCode = regionService.getDefaultRegionCode();

		TransResource transResource = transResourceService.getTransResource("0");
		if (transResource == null) {
			transResource = new TransResource();
			transResource.setResourceId("0");
		}
		transResource.setRegionCode(regionCode);
		transResource.setAddOffer(10.0);
		transResource.setBeginOffer(10.0);
		transResource.setCrArea(10.0);
		transResource.setFixedOffer(10.0);
		transResource.setFixedOfferUsd(10.0);
		transResource.setMaxOffer(10.0);
		transResource.setBeginHouse(10.0);
		transResource.setAddHouse(10.0);
		transResource.setGpBeginTime(nowDate);
		Date endDate = CommonDateParseUtil.getDateFromSourceDate(nowDate, 365);
		transResource.setGpEndTime(endDate);
		transResource.setBzjBeginTime(nowDate);
		transResource.setBzjEndTime(endDate);
		transResource.setBmBeginTime(nowDate);
		transResource.setBmEndTime(endDate);
		transResource.setResourceCode("测试编号");
		transResource.setResourceLocation("测试位置");
		transResource.setShowTime(nowDate);
		transResourceService.saveTransResource(transResource);

		TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApply("0");
		if (transResourceApply == null) {
			transResourceApply = new TransResourceApply();
			transResourceApply.setApplyId("0");
		}
		transResourceApply.setResourceId("0");
		transResourceApply.setMoneyUnit(Constants.MoneyCNY);
		transResourceApply.setBankCode("AAAAA");
		transResourceApply.setApplyDate(nowDate);
		transResourceApply.setUserId("0");

		transResourceApplyService.saveTransResourceApply(transResourceApply);

		TransBank transBank = transBankService.getBank("0");
		if (transBank != null) {
			transBankService.del("0");
		}

		transBank = new TransBank();

		transBank.setBankId("0");
		transBank.setAccountCode(bankCode);
		transBank.setAccountName("测试账户");
		transBank.setBankName("测试银行");
		transBank.setBankCode("AAAAA");
		transBank.setMoneyUnit(Constants.MoneyCNY);
		transBank.setRegionCode(regionCode);
		transBank.setInterfaceIp(host);
		transBank.setInterfacePort(portStr);

		transBankService.save(transBank);

		TransBankAccount transBankAccount = transBankAccountService.getTransBankAccountByApplyId("0");
		if (transBankAccount != null)
			transBankAccountService.deleteTransBankAccountById(transBankAccount.getAccountId());
		transBankAccount = clientService.applyBankAccount("0");
		System.out.println("本次申请获取到的银行子账号为：" + transBankAccount.getAccountCode());
	}

	/**
	 * @作者 王建明
	 * @创建日期 2015/7/1
	 * @创建时间 10:46
	 * @描述 —— 查询明细账接口请求
	 */
	private static void postForGetDetail() throws Exception {
		String host = null;
		System.out.print("请输入IP地址:");
		host = br.readLine();
		System.out.print("请输入端口号:");
		String portStr = br.readLine();
		int port = Integer.parseInt(portStr);

		System.out.print("请输入银行主账号:");
		String bankCode = br.readLine();

		System.out.print("请输入银行子账号:");
		String subBankCode = br.readLine();


		String stringBuffer = "<?xml version='1.0' encoding='GBK'?><root><head><TransCode>G00005</TransCode><TransDate>20151105</TransDate><TransTime>093547</TransTime><SeqNo>NJ50356</SeqNo></head><body><InstCode>" + bankCode + "</InstCode><InMemo>" + subBankCode + "</InMemo></body></root>";
		ClientSocketUtil clientSocketUtil = new ClientSocketUtil(host, port);
		System.out.println("发送给银行的数据:" + stringBuffer);
		clientSocketUtil.send(stringLengthFormate(stringBuffer.toString().getBytes(Charset.forName("GBK")).length) + stringBuffer.toString());
		try {
			String returnXml = clientSocketUtil.recieve();
			System.out.println("银行返回的数据:" + returnXml);
			//得到结果
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			clientSocketUtil.close();
		}
	}

	private static String formateDateString(String format, Date time) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(time);
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
