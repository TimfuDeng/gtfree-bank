package cn.gtmap.landsale.bank.service.impl;

import cn.gtmap.landsale.bank.register.TransResourceClient;
import cn.gtmap.landsale.bank.register.TransResourcesApplyClient;
import cn.gtmap.landsale.bank.service.*;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransBankAccount;
import cn.gtmap.landsale.common.model.TransBankPay;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceApply;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 接受银行返回信息测试 接口
 *
 * @author zsj
 * @version v1.0, 2017/8/29
 */
@Service
public class TransBankRevicerServiceImpl implements TransBankReciverService {
    private static Logger log = LoggerFactory.getLogger(TransBankRevicerServiceImpl.class);

    @Autowired
    TransResourceClient transResourceService;

    @Autowired
    TransBankAccountService transBankAccountService;

    @Autowired
    TransBankService transBankService;

    @Autowired
    TransBankPayService transBankPayService;

    @Autowired
    TransBankRefundService transBankRefundService;

    @Autowired
    TransResourcesApplyClient transResourceApplyService;


    /**
     * G00001
     * 模拟银行发送到账通知，手动发送到账通知用
     *
     * @param transBankPay
     * @return
     */
    @Override
    public String sendBankPayTest(TransBankPay transBankPay) {
        // TODO 测试用 使用时删掉
        if (StringUtils.isNotBlank(transBankPay.getPayId())) {
            transBankPay = transBankPayService.getTransBankPay(transBankPay.getPayId());
        }
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00001</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + transBankPay.getPayNo() + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InDate>" + formateDateString("yyyyMMddHHmmss", transBankPay.getPayTime()) + "</InDate>");
        stringBuffer.append("<InstCode>" + transBankPay.getAccountCode() + "</InstCode>");
        stringBuffer.append("<InAmount>" + (transBankPay.getAmount() * 10000) + "</InAmount>");
        stringBuffer.append("<InName>" + transBankPay.getPayName() + "</InName>");
        stringBuffer.append("<InAcct>" + transBankPay.getPayBankAccount() + "</InAcct>");
        stringBuffer.append("<InMemo>" + transBankPay.getAccountCode() + "</InMemo>");
        stringBuffer.append("<InBankFLCode>" + transBankPay.getPayNo() + "</InBankFLCode>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00012
     * 模拟银行发送退款到账通知，手动发送退款到账通知用
     *
     * @param transBankAccount
     * @return
     */
    @Override
    public String sendBankRefundTest(TransBankAccount transBankAccount) {
        // 获取保证金信息
        List<TransBankPay> transBankPayList = transBankPayService.getTransBankPaysByAccountCodeAndAccountId(transBankAccount.getAccountCode(), transBankAccount.getAccountId());
        if (transBankPayList != null && transBankPayList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
            stringBuffer.append("<root>");
            stringBuffer.append("<head>");
            stringBuffer.append("<TransCode>G00012</TransCode>");
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
     * socket server test接受到的信息 测试用 只模拟返回XML 不处理业务
     *
     * @param receiveXml
     * @return
     */
    @Override
    public String socketServerReceiveTest(String receiveXml) {
        if (StringUtils.isNotBlank(receiveXml)) {
            //处理接受的数据 TODO 业务暂未处理
            try {
                Document doc = DocumentHelper.parseText(receiveXml);
                String code = getElementValue(doc, "//head/TransCode");
                if ("G00001".equals(code)) {
                    // 到账通知
                    return receivePay(doc);
                } else if ("G00003".equals(code)) {
                    // 处理开户通知 申请子账号
                    return bankApplyAccount(doc);
                } else if ("G00004".equals(code)) {
                    // 注销子账号
                    return bankCancalAccount(doc);
                } else if ("G00005".equals(code)) {
                    // 保证金到账明细
                    return bankPayDetail(doc);
                } else if ("G00009".equals(code)) {
                    // 链路测试
                    return sendTestXml(doc);
                } else if ("G00010".equals(code)) {
                    // 退款申请
                    return bankRefundApply(doc);
                } else if ("G00011".equals(code)) {
                    // 退款明细查询
                    return receiveBankRefundDetail(doc);
                } else if ("G00012".equals(code)) {
                    // 退款到账通知
                    return bankRefundPay(doc);
                } else if ("G00013".equals(code)) {
                    // 子账号剩余情况查询
                    return bankAccountSurplus(doc);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex.getCause());
            }
        }
        return null;
    }

    /**
     * G00001
     * 接受银行到账通知 XML返回 测试用只模拟业务逻辑 暂不保存信息
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

    private void saveTransBankPay(TransBankAccount transBankAccount, TransBankPay transBankPay) throws Exception {
        transBankPayService.saveTransBankPay(transBankPay);
        //判读是否交足保证金
        TransResourceApply transResourceApply =
                transResourceApplyService.getTransResourceApply(transBankAccount.getApplyId());
        TransResource transResource =
                transResourceService.getTransResource(transResourceApply.getResourceId());
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
            transResourceApplyService.saveTransResourceApply(transResourceApply);
        }
    }

    public static double add(double d1, double d2) {        // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    /**
     * G00003
     * 银行开户 获取保证金账号 XML返回
     *
     * @param doc
     * @return
     */
    private String bankApplyAccount(Document doc) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00003</TransCode>");
        stringBuffer.append("<TransDate>" + getElementValue(doc, "//head/TransDate") + "</TransDate>");
        stringBuffer.append("<TransTime>" + getElementValue(doc, "//head/TransTime") + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AcctNo>" + "6200" + getElementValue(doc, "//body/InstSeq") + "</AcctNo>");
        stringBuffer.append("<AddWord>ok</AddWord>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");

        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00004
     * 接收注销子账号 XML返回
     *
     * @param doc
     * @return
     */
    private String bankCancalAccount(Document doc) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00004</TransCode>");
        stringBuffer.append("<TransDate>" + getElementValue(doc, "//head/TransDate") + "</TransDate>");
        stringBuffer.append("<TransTime>" + getElementValue(doc, "//head/TransTime") + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AddWord>ok</AddWord>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");

        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00005
     * 接收保证金到账明细 XML返回
     *
     * @param doc
     * @return
     */
    private String bankPayDetail(Document doc) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00005</TransCode>");
        stringBuffer.append("<TransDate>" + getElementValue(doc, "//head/TransDate") + "</TransDate>");
        stringBuffer.append("<TransTime>" + getElementValue(doc, "//head/TransTime") + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<InMemo>30000706105225765</InMemo>");
        stringBuffer.append("<Count>1</Count>");
        stringBuffer.append("<listEntity>");
        stringBuffer.append("<Entity rowNum='0'>");
        stringBuffer.append("<InDate>20170825093733</InDate>");
        stringBuffer.append("<InAmount>5000000</InAmount>");
        stringBuffer.append("<InName>黄百栋</InName>");
        stringBuffer.append("<InAcct>6227002920660193525</InAcct>");
        stringBuffer.append("<InBankFLCode>1706093733695</InBankFLCode>");
        stringBuffer.append("</Entity>");
        stringBuffer.append("</listEntity>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");

        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00009
     * 接受银行链路监测 返回XML
     *
     * @return
     */
    private String sendTestXml(Document doc) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00009</TransCode>");
        stringBuffer.append("<TransDate>" + getElementValue(doc, "//head/TransCode") + "</TransDate>");
        stringBuffer.append("<TransTime>" + getElementValue(doc, "//head/TransTime") + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }


    /**
     * G00010
     * 模拟银行接收退款申请 返回XML
     *
     * @param doc
     * @return
     */
    private String bankRefundApply(Document doc) {
        //到账信息
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00010</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AddWord>成功处理</AddWord>");
        stringBuffer.append("<batchNo>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</batchNo>");
        stringBuffer.append("<listEntity>");
        for (int i = 1, len = Integer.parseInt(getElementValue(doc, "//body/TotalNum")); i <= len; i++) {
            stringBuffer.append("<Entity rowNum='" + (i - 1) + "'>");
            stringBuffer.append("<InAcct>" + getElementValue(doc, "//Entity[" + i + "]/InAcct") + "</InAcct>");
            stringBuffer.append("<InName>" + getElementValue(doc, "//Entity[" + i + "]/InName") + "</InName>");
            stringBuffer.append("<Result>00</Result>");
            stringBuffer.append("<AddWord>成功处理</AddWord>");
            stringBuffer.append("</Entity>");
        }
        stringBuffer.append("</listEntity>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00011
     * 模拟银行接收退款明细查询 返回XML
     *
     * @param doc
     * @return
     */
    @Transactional(readOnly = true)
    private String receiveBankRefundDetail(Document doc) {
//        List<TransBankRefund> transBankRefundList = transBankRefundService.getTransBankPaysByAccountAndBatch(getElementValue(doc, "//body/InMemo"), getElementValue(doc, "//body/batchNo"));
        List<TransBankPay> transBankPayList = transBankPayService.getTransBankPaysByAccountCode(getElementValue(doc, "//body/InMemo"));
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00011</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//body/InBankFLCode") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AddWord>成功处理</AddWord>");
        stringBuffer.append("<listEntity>");
        for (int i = 0, len = transBankPayList.size(); i < len; i++) {
            stringBuffer.append("<Entity rowNum='" + i + "'>");
            stringBuffer.append("<InAcct>" + transBankPayList.get(i).getPayBankAccount() + "</InAcct>");
            stringBuffer.append("<InName>" + transBankPayList.get(i).getPayName() + "</InName>");
            stringBuffer.append("<InAcctName>" + transBankPayList.get(i).getPayBank() + "</InAcctName>");
            stringBuffer.append("<InAmount>" + transBankPayList.get(i).getAmount() + "</InAmount>");
            stringBuffer.append("<InBankFLCode>" + transBankPayList.get(i).getPayNo() + "</InBankFLCode>");
            stringBuffer.append("<Result>00</Result>");
            stringBuffer.append("<AddWord>成功处理</AddWord>");
            stringBuffer.append("</Entity>");
        }
        stringBuffer.append("</listEntity>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00012
     * 模拟银行接收退款到账通知 返回XML
     *
     * @param doc
     * @return
     */
    private String bankRefundPay(Document doc) {
        //到账信息
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00012</TransCode>");
        stringBuffer.append("<TransDate>" + formateDateString("yyyyMMdd", Calendar.getInstance().getTime()) + "</TransDate>");
        stringBuffer.append("<TransTime>" + formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AddWord>成功处理</AddWord>");
        stringBuffer.append("<listEntity>");
        for (int i = 1, len = Integer.parseInt(getElementValue(doc, "//body/TotalNum")); i <= len; i++) {
            stringBuffer.append("<Entity rowNum='" + (i - 1) + "'>");
            stringBuffer.append("<InAcct>" + getElementValue(doc, "//Entity[" + i + "]/InAcct") + "</InAcct>");
            stringBuffer.append("<InName>" + getElementValue(doc, "//Entity[" + i + "]/InName") + "</InName>");
            stringBuffer.append("<Result>00</Result>");
            stringBuffer.append("<AddWord>成功处理</AddWord>");
            stringBuffer.append("</Entity>");
        }
        stringBuffer.append("</listEntity>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }

    /**
     * G00013
     * 模拟银行接收子账号剩余情况查询 XML返回
     *
     * @param doc
     * @return
     */
    private String bankAccountSurplus(Document doc) {
        StringBuffer stringBuffer = new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00013</TransCode>");
        stringBuffer.append("<TransDate>" + getElementValue(doc, "//head/TransDate") + "</TransDate>");
        stringBuffer.append("<TransTime>" + getElementValue(doc, "//head/TransTime") + "</TransTime>");
        stringBuffer.append("<SeqNo>" + getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AddWord>ok</AddWord>");
        stringBuffer.append("<InSurplus>00</InSurplus>");
        stringBuffer.append("<TotalNum>30</TotalNum>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length) + stringBuffer.toString();
    }


    private BigDecimal getTotalSum(List<TransBankPay> transBankPayList) {
        BigDecimal totalSum = BigDecimal.ZERO;
        for (TransBankPay transBankPay : transBankPayList) {
            totalSum = totalSum.add(BigDecimal.valueOf(transBankPay.getAmount()));
        }
        return totalSum;
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

    private String getElementValue(Document doc, String path) {
        Element element = (Element) doc.selectSingleNode(path);
        if (element != null) {
            return element.getTextTrim();
        } else {
            return null;
        }
    }

    private String formateDateString(String format, Date time) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(time);
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
}
