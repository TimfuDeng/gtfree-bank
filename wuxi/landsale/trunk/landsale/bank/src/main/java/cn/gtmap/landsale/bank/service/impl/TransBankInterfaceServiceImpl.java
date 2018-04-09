package cn.gtmap.landsale.bank.service.impl;

import cn.gtmap.landsale.bank.register.TransResourceClient;
import cn.gtmap.landsale.bank.register.TransResourcesApplyClient;
import cn.gtmap.landsale.bank.service.TransBankAccountService;
import cn.gtmap.landsale.bank.service.TransBankInterfaceService;
import cn.gtmap.landsale.bank.service.TransBankPayService;
import cn.gtmap.landsale.bank.service.TransBankService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.log.AuditServiceLog;
import cn.gtmap.landsale.common.model.*;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 银行接口
 *
 * @author zsj
 * @version v1.0, 2017/8/29
 */
@Service
public class TransBankInterfaceServiceImpl implements TransBankInterfaceService {
    private static Logger log = LoggerFactory.getLogger(TransBankInterfaceServiceImpl.class);

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransBankAccountService transBankAccountService;

    @Autowired
    TransBankService transBankService;

    @Autowired
    TransBankPayService transBankPayService;

    @Autowired
    TransResourcesApplyClient transResourcesApplyClient;

    /**
     * G00003
     * 向银行发送开户申请 申请保证金子账号
     *
     * @param transResourceApply
     * @return
     */
    @Override
    public String sendBankApplyAccount(TransResourceApply transResourceApply) {
        // 资源
        TransResource transResource = transResourceClient.getTransResource(transResourceApply.getResourceId());
        // 申请银行
//        TransBank transBank = getBankByRegionCode(transResource.getRegionCode(), transResourceApply.getBankCode(), transResourceApply.getMoneyUnit());
        TransBank transBank = transBankService.getBank(transResourceApply.getBankId());
        // 子账号
        TransBankAccount transBankAccount =
                transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00003</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        // 用户竞买号，相当于订单号，长度不超过12位。组成由交易日期 年月日，中间无分隔，如：20170825+序列号
        stringBuffer.append("<SeqNo>" + transBankAccount.getApplyNo() + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InstCode>" + transBank.getAccountCode() + "</InstCode>");
        stringBuffer.append("<InstSeq>" + transBankAccount.getApplyNo() + "</InstSeq>");
        stringBuffer.append("<MatuDay>" + formateDateString("yyyyMMddHHmmss", transResource.getGpEndTime()) + "</MatuDay>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00004
     * 向银行发送注销子账号申请
     *
     * @param transBankAccount
     * @return
     */
    @Override
    public String sendBankCancelAccount(TransBankAccount transBankAccount) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00004</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        // 用户竞买号，相当于订单号，长度不超过12位。组成由交易日期 年月日，中间无分隔，如：20170825+序列号
        stringBuffer.append("<SeqNo>" + transBankAccount.getApplyNo() + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InMemo>" + transBankAccount.getAccountCode() + "</InMemo>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00005
     * 向银行发送保证金到账明细查询
     *
     * @param transBankAccount
     * @return
     */
    @Override
    public String sendBankPayDetail(TransBankAccount transBankAccount) {
        // 资源申请
        TransResourceApply transResourceApply =
                transResourcesApplyClient.getTransResourceApply(transBankAccount.getApplyId());
        // 资源
        TransResource transResource = transResourceClient.getTransResource(transResourceApply.getResourceId());
        // 银行 银行CODE与所在行政区 可以唯一确定一家银行? TODO
        TransBank transBank = transBankService.getBankByCodeAndRegion(transResourceApply.getBankCode(), transResource.getRegionCode());
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00005</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        // 用户竞买号，相当于订单号，长度不超过12位。组成由交易日期 年月日，中间无分隔，如：20170825+序列号
        stringBuffer.append("<SeqNo>" + transBankAccount.getApplyNo() + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InstCode>" + transBank.getAccountCode() + "</InstCode>");
        stringBuffer.append("<InMemo>" + transBankAccount.getAccountCode() + "</InMemo>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00009
     * 链路监测xml
     *
     * @return
     */
    @Override
    public String sendTestXml() {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00009</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00010
     * 向银行发送保证金退款申请
     *
     * @param transBankAccount
     * @return
     */
    @Override
    public String sendBankRefund(TransBankAccount transBankAccount) {
        // 获取保证金信息
        List<TransBankPay> transBankPayList = transBankPayService.getTransBankPaysByAccountCodeAndAccountId(transBankAccount.getAccountCode(), transBankAccount.getAccountId());
        if (transBankPayList != null && transBankPayList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
            stringBuffer.append("<root>");
            stringBuffer.append("<head>");
            stringBuffer.append("<TransCode>G00010</TransCode>");
            stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
            stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
            stringBuffer.append("<SeqNo>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</SeqNo>");
            stringBuffer.append("</head>");
            stringBuffer.append("<body>");
            stringBuffer.append("<TotalNum>" + transBankPayList.size() + "</TotalNum>");
            stringBuffer.append("<TotalSum>" + getTotalSum(transBankPayList) + "</TotalSum>");
            stringBuffer.append("<InMemo>" + transBankAccount.getAccountCode() + "</InMemo>");
            stringBuffer.append("<listEntity>");
            for (int i = 0, len = transBankPayList.size(); i < len; i++) {
                stringBuffer.append("<Entity rowNum='" + i + "'>");
                stringBuffer.append("<InAcct>" + transBankPayList.get(i).getPayBankAccount() + "</InAcct>");
                stringBuffer.append("<InName>" + transBankPayList.get(i).getPayName() + "</InName>");
                stringBuffer.append("<InAcctName>" + transBankPayList.get(i).getPayBank() + "</InAcctName>");
                stringBuffer.append("<InAmount>" + transBankPayList.get(i).getAmount() + "</InAmount>");
                stringBuffer.append("<InBankFLCode>" + transBankPayList.get(i).getPayNo() + "</InBankFLCode>");
                stringBuffer.append("</Entity>");
            }
            stringBuffer.append("</listEntity>");
            stringBuffer.append("</body>");
            stringBuffer.append("</root>");
            return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
        }
        return null;
    }


    /**
     * G00011
     * 向银行发送保证金退款明细查询
     *
     * @param batchNo        批次号 必传
     * @param accountCode    子账号 必传
     * @param payBankAccount 付款账号 不必传 传入作为查询条件
     * @param payNo          原到账交易流水号 不必传 传入作为查询条件
     * @param amount         交易金额 不必传 传入作为查询条件
     * @return
     */
    @Override
    public String sendBankRefundDetail(String batchNo, String accountCode, String payBankAccount, String payNo, String amount) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00011</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<batchNo>" + batchNo + "</batchNo>");
        stringBuffer.append("<InMemo>" + accountCode + "</InMemo>");
        if (!StringUtils.isEmpty(payBankAccount)) {
            stringBuffer.append("<InAcct>" + payBankAccount + "</InAcct>");
        }
        if (!StringUtils.isEmpty(payNo)) {
            stringBuffer.append("<InBankFLCode>" + payNo + "</InBankFLCode>");
        }
        if (!StringUtils.isEmpty(amount)) {
            stringBuffer.append("<InAmount>" + amount + "</InAmount>");
        }
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00013
     * 向银行发送子账号剩余情况查询
     *
     * @param transBank
     * @return
     */
    @Override
    public String sendBankAccountSurplus(TransBank transBank) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00013</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InstCode>" + transBank.getAccountCode() + "</InstCode>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * 正式环境 只接受两种数据 到账通知 退款通知
     * 其余银行返回的信息 放到各自的业务逻辑里去写 不在银行接口中处理
     *
     * @param receiveXml
     * @return
     */
    @AuditServiceLog(category = Constants.LogCategory.DATA_RECEIVE, producer = Constants.LogProducer.ADMIN,
            description = "接收银行数据")
    @Override
    public Object receiveBankBack(String receiveXml) {
        if (StringUtils.isNotBlank(receiveXml)) {
            try {
                Document doc = DocumentHelper.parseText(receiveXml);
                String code = getElementValue(doc, "//head/TransCode");
                if ("G00001".equals(code)) {
                    // 到账通知
                    return receivePay(doc);
                } else if ("G00012".equals(code)) {
                    // 退款通知

                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex.getCause());
            }
        }
        return null;
    }

    /**
     * 接收银行到账通知业务处理
     *
     * @param doc
     * @return
     */
    private String receivePay(Document doc) {
        //到账信息
        String payNo = getElementValue(doc, "//body/InBankFLCode");
        String accountCode = getElementValue(doc, "//body/InMemo");
        String result = "00";
        String addWord = "成功处理";
        if (StringUtils.isNotBlank(payNo) && StringUtils.isNotBlank(accountCode)) {
            TransBankAccount transBankAccount = transBankAccountService.getTransBankAccountByAccountCode(accountCode);
            if (transBankAccount != null) {
                List<TransBankPay> transBankPayList = transBankPayService.getTransBankPaysByAccountCodeAndPayNo(accountCode, payNo);
                TransBankPay transBankPay = new TransBankPay();
                if (transBankPayList.size() > 0) {
                    transBankPay = transBankPayList.get(0);
                    result = "01";
                    addWord = "到账重复";
                } else {
                    transBankPay.setAccountId(transBankAccount.getAccountId());
//            transBankPay.setBankCode(getElementValue(doc, "//input/bankid"));
//            transBankPay.setPayBank(getElementValue(doc, "//input/draccbank"));
//            transBankPay.setPayBankAddress(getElementValue(doc, "//input/draccsubbank"));
                    transBankPay.setPayBankAccount(getElementValue(doc, "//body/InAcct"));
                    transBankPay.setPayName(getElementValue(doc, "//body/InName"));
                    transBankPay.setAccountCode(getElementValue(doc, "//body/InMemo"));
                    transBankPay.setPayNo(payNo);
//            transBankPay.setAccountName(getElementValue(doc, "//input/craccname"));
                    if (getElementValue(doc, "//body/InAmount") != null) {
                        transBankPay.setAmount(Double.parseDouble(getElementValue(doc, "//body/InAmount")) / 10000);
                    }
//            transBankPay.setRemark(getElementValue(doc, "//input/remark"));
                    transBankPay.setPayTime(buildDate(getElementValue(doc, "//body/InDate")));
                    try {
                        saveTransBankPay(transBankAccount, transBankPay);
                    } catch (Exception ex) {
                        addWord = ex.getMessage();
                        result = "02";
                    }
                }
            } else {
                addWord = "该帐号不存在";
                result = "02";
            }

        } else {
            addWord = "交款帐号和流水号必须填写";
            result = "02";
        }
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00001</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + payNo + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>" + result + "</Result>");
        stringBuffer.append("<AddWord>" + addWord + "</AddWord>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");

        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * 接收到账通知-->保存到账信息
     *
     * @param transBankAccount
     * @param transBankPay
     * @throws Exception
     */
    private void saveTransBankPay(TransBankAccount transBankAccount, TransBankPay transBankPay) throws Exception {
        transBankPayService.saveTransBankPay(transBankPay);
        //判读是否交足保证金
        TransResourceApply transResourceApply =
                transResourcesApplyClient.getTransResourceApply(transBankAccount.getApplyId());
        TransResource transResource =
                transResourceClient.getTransResource(transResourceApply.getResourceId());
        List<TransBankPay> transBankPayList =
                transBankPayService.getTransBankPaysByAccountId(transBankAccount.getAccountId());
        double amount = 0;
        for (TransBankPay bankPay : transBankPayList) {
            if (bankPay.getPayTime().before(transResource.getBzjEndTime())) {
                amount = add(amount, bankPay.getAmount());
            }
        }
        if (amount >= transResource.getFixedOffer()) {
            transResourceApply.setApplyStep(Constants.STEP_OVER);
            transResourcesApplyClient.saveTransResourceApply(transResourceApply);
        }
    }

    /**
     * 申请子账号银行返回信息接收处理
     *
     * @param doc
     * @return
     */
    // TODO 申请子账号银行返回信息接收处理 之后不再在银行接口中处理 放到申请子账号业务中去 代码暂时保留供之后参考
    private TransBankAccount bankApplyAccountReuslt(Document doc) {
        String accountCode = getElementValue(doc, "//body/AcctNo");
        //判断该银行账户是否正在使用
        TransBankAccount bankAccount = transBankAccountService.getTransBankAccountByAccountCode(accountCode);

        //拿到重复的保证金账号做一下对比，过来保证金交纳点的账号立马被释放，预防特殊情况：到报价阶段的地块终止还没释放子账号 liushaoshuai【liushaoshuai@gtmap.cn】2017/5/5 14:54
        try {
            if (bankAccount != null) {
                TransResourceApply transResourceApply = transResourcesApplyClient.getTransResourceApply(bankAccount.getApplyId());
                TransResource transResource = transResourceClient.getTransResource(transResourceApply.getResourceId());
                if (transResource.getBzjEndTime().before(new Date())) {
                    bankAccount.setClose(true);
                    transBankAccountService.saveTransBankAccount(bankAccount);
                    bankAccount = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TransBankAccount transBankAccount =
                transBankAccountService.getTransBankAccountByApplyNo(getElementValue(doc, "//head/SeqNo"));
        if (bankAccount == null) {
            transBankAccount.setAccountCode(getElementValue(doc, "//body/AcctNo"));
            transBankAccount.setReuslt(getElementValue(doc, "//body/Result"));
            transBankAccount.setReceiveDate(Calendar.getInstance().getTime());
            return transBankAccountService.saveTransBankAccount(transBankAccount);
        } else {
            log.error("----银行帐号正在使用！" + bankAccount);
            return transBankAccount;
        }
    }

    private BigDecimal getTotalSum(List<TransBankPay> transBankPayList) {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (TransBankPay transBankPay : transBankPayList) {
            totalSum = totalSum.add(BigDecimal.valueOf(transBankPay.getAmount()));
        }
        return totalSum;
    }

    public static double add(double d1, double d2) {        // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    private Date buildDate(String dateStr) {
        try {
            if (StringUtils.isNotBlank(dateStr)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                return df.parse(dateStr);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String formateDateString(String format, Date time) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(time);
    }

    private TransBank getBankByRegionCode(String regionCode, String bankCode, String moneyUnit) {
        String mu = moneyUnit;
        if (StringUtils.isBlank(moneyUnit)) {
            mu = "CNY";
        }
        List<TransBank> bankList = transBankService.getBankListByRegion(regionCode);
        for (TransBank bank : bankList) {
            if (bank.getBankCode().equals(bankCode) && bank.getMoneyUnit().equals(mu)) {
                return bank;
            }
        }
        return null;
    }

    private String stringLengthFormate(int length) {
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

    private String getElementValue(Document doc, String path) {
        Element element = (Element) doc.selectSingleNode(path);
        if (element != null) {
            return element.getTextTrim();
        } else {
            return null;
        }
    }

//    public static void main(String[] params) throws DocumentException {
//        String str="<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>\n  <head>\n    <TransCode>G00001</TransCode>\n    <TransDate>20150618</TransDate>\n    <TransTime>155607</TransTime>\n    <SeqNo>35582752</SeqNo>\n  </head>\n  <body>\n    <InstCode>32001757436052509237</InstCode>\n    <InDate>20150617170800</InDate>\n    <InAmount>100000.00</InAmount>\n    <InName>中国建设银行</InName>\n    <InAcct>9999990002002</InAcct>\n    <InMemo>32001757436052509237-000001</InMemo>\n    <InBankFLCode>3207511111</InBankFLCode>\n  </body>\n</root>\n\u0000\u0000\u0000\u0000\u0000\u0000";
//        try{
//            Document doc = DocumentHelper.parseText(str.trim());
//            Element element=(Element) doc.selectSingleNode("//head/TransCode");
//            System.out.println(element.getTextTrim());
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//    }
}
