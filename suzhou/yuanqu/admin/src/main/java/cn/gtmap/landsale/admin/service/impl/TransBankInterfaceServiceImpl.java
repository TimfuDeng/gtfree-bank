package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 中智的接口
 * Created by Jibo on 2015/5/21.
 */
public class TransBankInterfaceServiceImpl implements TransBankInterfaceService {
    private static Logger log = LoggerFactory.getLogger(TransBankInterfaceServiceImpl.class);

    TransResourceService transResourceService;

    TransBankAccountService transBankAccountService;

    TransBankService transBankService;

    TransBankPayService transBankPayService;

    TransResourceApplyService transResourceApplyService;


    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransBankPayService(TransBankPayService transBankPayService) {
        this.transBankPayService = transBankPayService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setTransBankService(TransBankService transBankService) {
        this.transBankService = transBankService;
    }

    /**
     *
     * @param transResourceApply
     * @return
     */
    @Override
    public String sendBankApplyAccount(TransResourceApply transResourceApply) {
        //资源
        TransResource transResource= transResourceService.getTransResource(transResourceApply.getResourceId());
        //申请银行
        //TransBank transBank=getBankByRegionCode(transResource.getRegionCode(), transResourceApply.getBankCode(), transResourceApply.getMoneyUnit());
        TransBank transBank= transBankService.getBank(transResourceApply.getBankId());
        TransBankAccount transBankAccount=
                transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
        StringBuffer stringBuffer=new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00003</TransCode>");
        stringBuffer.append("<TransDate>"+formateDateString("yyyyMMdd", Calendar.getInstance().getTime())+"</TransDate>");
        stringBuffer.append("<TransTime>"+formateDateString("HHmmss", Calendar.getInstance().getTime())+"</TransTime>");
        stringBuffer.append("<SeqNo>"+transBankAccount.getApplyNo() + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InstCode>"+transBank.getAccountCode()+"</InstCode>");
        stringBuffer.append("<InstSeq>"+transBankAccount.getApplyNo()+"</InstSeq>");
        stringBuffer.append("<MatuDay>"+formateDateString("yyyyMMddHHmmss", transResource.getGpEndTime())+"</MatuDay>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");

        return stringLengthFormate(stringBuffer.toString().getBytes().length)+stringBuffer.toString();
    }

    @Override
    public String sendBankPayTest(TransBankPay transBankPay) {
        StringBuffer stringBuffer=new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00001</TransCode>");
        stringBuffer.append("<TransDate>"+formateDateString("yyyyMMdd", Calendar.getInstance().getTime())+"</TransDate>");
        stringBuffer.append("<TransTime>"+formateDateString("HHmmss", Calendar.getInstance().getTime())+"</TransTime>");
        stringBuffer.append("<SeqNo>"+transBankPay.getPayNo() + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<InDate>"+formateDateString("yyyyMMddHHmmss", transBankPay.getPayTime()) +"</InDate>");
        stringBuffer.append("<InstCode>"+transBankPay.getAccountCode()+"</InstCode>");
        stringBuffer.append("<InAmount>"+(transBankPay.getAmount()*10000)+"</InAmount>");
        stringBuffer.append("<InName>"+transBankPay.getPayName()+"</InName>");
        stringBuffer.append("<InAcct>"+transBankPay.getPayBankAccount()+"</InAcct>");
        stringBuffer.append("<InMemo>"+transBankPay.getAccountCode()+"</InMemo>");
        stringBuffer.append("<InBankFLCode>"+transBankPay.getPayNo()+"</InBankFLCode>");
        stringBuffer.append("<InstSeq>"+transBankPay.getApplyNo()+"</InstSeq>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length)+stringBuffer.toString();
    }

    @Override
    public String sendTestXml(){
        StringBuffer stringBuffer=new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00009</TransCode>");
        stringBuffer.append("<TransDate>"+formateDateString("yyyyMMdd", Calendar.getInstance().getTime())+"</TransDate>");
        stringBuffer.append("<TransTime>"+formateDateString("HHmmss", Calendar.getInstance().getTime())+"</TransTime>");
        stringBuffer.append("<SeqNo>"+formateDateString("HHmmss", Calendar.getInstance().getTime()) + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length)+stringBuffer.toString();
    }

    @Override
    public Object receiveBankBack(String info) {
        if (StringUtils.isNotBlank(info)) {
            //处理接受的数据
            String receiveXml= info;
            try {
                Document doc = DocumentHelper.parseText(receiveXml);
                String code=getElementValue(doc,"//head/TransCode");
                if ("G00003".equals(code)){
                    //收到银行的开户结果
                    return bankApplyAccountReuslt(doc);
                }else if ("G00005".equals(code)){
                    //保证金到账结果

                }
            }catch (Exception ex){
                log.error(ex.getMessage(), ex.getCause());
            }
        }
        return null;
    }

    @AuditServiceLog(category = Constants.LogCategory.DATA_RECEIVE,producer = Constants.LogProducer.ADMIN,
            description = "接收银行数据")
    public String socketServerReceive(String receiveXml) {
        if (StringUtils.isNotBlank(receiveXml)) {
            //处理接受的数据
            try {
                Document doc = DocumentHelper.parseText(receiveXml);
                String code=getElementValue(doc,"//head/TransCode");
                if ("G00003".equals(code)){
                    //处理开户通知，这里是测试用的
                    return bankApplyAccount(doc);
                }else if ("G00001".equals(code)){
                    //到账通知
                    return receivePay(doc);
                }else if ("G00009".equals(code)){
                    return sendTestXml(doc);
                }
            }catch (Exception ex){
                log.error(ex.getMessage(), ex.getCause());
            }
        }
        return null;
    }

    private String sendTestXml(Document doc){
        StringBuffer stringBuffer=new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00009</TransCode>");
        stringBuffer.append("<TransDate>"+getElementValue(doc, "//head/TransCode")+"</TransDate>");
        stringBuffer.append("<TransTime>"+getElementValue(doc, "//head/TransTime")+"</TransTime>");
        stringBuffer.append("<SeqNo>"+getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length)+stringBuffer.toString();
    }

    private String bankApplyAccount(Document doc){
        StringBuffer stringBuffer=new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00003</TransCode>");
        stringBuffer.append("<TransDate>"+getElementValue(doc, "//head/TransDate")+"</TransDate>");
        stringBuffer.append("<TransTime>"+getElementValue(doc, "//head/TransTime")+"</TransTime>");
        stringBuffer.append("<SeqNo>"+getElementValue(doc, "//head/SeqNo") + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>00</Result>");
        stringBuffer.append("<AcctNo>"+"6200"+getElementValue(doc, "//body/InstSeq") +"</AcctNo>");
        stringBuffer.append("<AddWord>ok</AddWord>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");
        return stringLengthFormate(stringBuffer.toString().getBytes().length)+stringBuffer.toString();
    }

    private String receivePay(Document doc) {
        //到账信息
        String payNo = getElementValue(doc, "//body/InBankFLCode");
        String accountCode = getElementValue(doc, "//body/InMemo");
        String applyNo = getElementValue(doc, "//body/InstSeq");
        String result="00";
        String addWord="成功处理";
        if (StringUtils.isNotBlank(payNo) && StringUtils.isNotBlank(accountCode)) {
            TransBankAccount transBankAccount = null;
            List<TransBankPay> transBankPayList = null;
            if(StringUtils.isNotBlank(applyNo)){
                //传入竞买号时，外币账号
                transBankAccount= transBankAccountService.getTransBankAccountByAccountCodeAndApplyNo(accountCode,applyNo);
                transBankPayList = transBankPayService.getTransBankPaysByAccountCodeAndApplyNo(accountCode, applyNo, payNo);
            }else{
                transBankAccount = transBankAccountService.getTransBankAccountByAccountCode(accountCode);
                transBankPayList = transBankPayService.getTransBankPaysByAccountCode(accountCode, payNo);
            }

            TransBankPay transBankPay = new TransBankPay();
            transBankPay.setPayBankAccount(getElementValue(doc, "//body/InAcct"));
            transBankPay.setPayName(getElementValue(doc, "//body/InName"));
            transBankPay.setAccountCode(getElementValue(doc, "//body/InMemo"));
            transBankPay.setPayNo(payNo);
            transBankPay.setApplyNo(applyNo);
            if (getElementValue(doc, "//body/InAmount") != null)
                transBankPay.setAmount(Double.parseDouble(getElementValue(doc, "//body/InAmount"))/10000);
            transBankPay.setPayTime(buildDate(getElementValue(doc, "//body/InDate")));


            if (transBankAccount!=null) {
                if (transBankPayList!=null&&transBankPayList.size() > 0) {
                    result = "01";
                    addWord="到账重复";
                }else {
                    transBankPay.setAccountId(transBankAccount.getAccountId());
                    try {
                        saveTransBankPay(transBankAccount, transBankPay);
                    } catch (Exception ex) {
                        addWord = ex.getMessage();
                        result = "02";
                        log.error(ex.getMessage(),ex.getCause());
                    }
                }
            }else{
                transBankPayService.saveTransBankPay(transBankPay);
                addWord="该帐号不存在";
                result="02";
            }

        }else{
            addWord="交款帐号和银行流水号必须填写";
            result="02";
        }
        StringBuffer stringBuffer=new StringBuffer("<?xml version='1.0' encoding='GBK'?>");
        stringBuffer.append("<root>");
        stringBuffer.append("<head>");
        stringBuffer.append("<TransCode>G00001</TransCode>");
        stringBuffer.append("<TransDate>"+formateDateString("yyyyMMdd", Calendar.getInstance().getTime())+"</TransDate>");
        stringBuffer.append("<TransTime>"+formateDateString("HHmmss", Calendar.getInstance().getTime())+"</TransTime>");
        stringBuffer.append("<SeqNo>"+payNo + "</SeqNo>");
        stringBuffer.append("</head>");
        stringBuffer.append("<body>");
        stringBuffer.append("<Result>"+result +"</Result>");
        stringBuffer.append("<AddWord>"+addWord+"</AddWord>");
        stringBuffer.append("</body>");
        stringBuffer.append("</root>");

        return stringLengthFormate(stringBuffer.toString().getBytes().length)+stringBuffer.toString();
    }

    private void saveTransBankPay(TransBankAccount transBankAccount,TransBankPay transBankPay) throws Exception{
        transBankPayService.saveTransBankPay(transBankPay);
        //判读是否交足保证金
        TransResourceApply transResourceApply=
                transResourceApplyService.getTransResourceApply(transBankAccount.getApplyId());
        TransResource transResource=
                transResourceService.getTransResource(transResourceApply.getResourceId());
        List<TransBankPay> transBankPayList=
                transBankPayService.getTransBankPaysByAccountId(transBankAccount.getAccountId());
        double amount=0;
        for(TransBankPay bankPay: transBankPayList){
            if(bankPay.getPayTime().before(transResource.getBzjEndTime())) {
                amount=add(amount,bankPay.getAmount());

            }
        }
        System.out.println("amount="+amount);
        Double resourceFixedOffer = 0.0;
        if ("USD".equals(transResourceApply.getMoneyUnit())){
            if(null!=transResource.getFixedOfferUsd())
            resourceFixedOffer=transResource.getFixedOfferUsd();
        }else if ("HKD".equals(transResourceApply.getMoneyUnit())){
            if(null!=transResource.getFixedOfferHkd())
            resourceFixedOffer=transResource.getFixedOfferHkd();
        }else{
            resourceFixedOffer=transResource.getFixedOffer();
        }
        if (amount>=resourceFixedOffer) {
            transResourceApply.setApplyStep(Constants.StepOver);
            //所有缴纳保证金的人都进入限时竞价
            transResourceApply.setLimitTimeOffer(true);
            transResourceApply.setLimitTimeOfferConfirmDate(Calendar.getInstance().getTime());
            transResourceApplyService.saveTransResourceApply(transResourceApply);
        }
    }

    public static double add(double d1, double d2)
    {        // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    private Date buildDate(String dateStr){
        try {
            if (StringUtils.isNotBlank(dateStr)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                return df.parse(dateStr);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    private TransBankAccount bankApplyAccountReuslt(Document doc){
        String accountCode=getElementValue(doc, "//body/AcctNo");
        String seqNo = getElementValue(doc, "//head/SeqNo");
        //判断该银行账户是否正在使用
        TransBankAccount bankAccount= transBankAccountService.getTransBankAccountByAccountCodeAndApplyNo(accountCode,seqNo);

        //拿到重复的保证金账号做一下对比，过来保证金交纳点的账号立马被释放，预防特殊情况：到报价阶段的地块终止还没释放子账号 liushaoshuai【liushaoshuai@gtmap.cn】2017/5/5 14:54
        try {
            if (bankAccount != null) {
                TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApply(bankAccount.getApplyId());
                TransResource transResource = transResourceService.getTransResource(transResourceApply.getResourceId());
                if (transResource.getBzjEndTime().before(new Date())) {
                    bankAccount.setClose(true);
                    transBankAccountService.saveTransBankAccount(bankAccount);
                    bankAccount = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TransBankAccount transBankAccount=
                transBankAccountService.getTransBankAccountByApplyNo(getElementValue(doc, "//head/SeqNo") );
        if (bankAccount==null){
            transBankAccount.setAccountCode(accountCode);
            transBankAccount.setReuslt(getElementValue(doc, "//body/Result"));
            transBankAccount.setReceiveDate(Calendar.getInstance().getTime());
            return transBankAccountService.saveTransBankAccount(transBankAccount);
        }else{
            log.error("----银行帐号正在使用！" + bankAccount);
            return transBankAccount;
        }
    }

    private String formateDateString(String format,Date time){
        SimpleDateFormat df=new SimpleDateFormat(format);
        return df.format(time);
    }

    private TransBank getBankByRegionCode(String regionCode,String bankCode,String moneyUnit){
        String mu= moneyUnit;
        if (StringUtils.isBlank(moneyUnit))   mu="CNY";
        List<TransBank> bankList=transBankService.getBankListByRegion(regionCode);
        for(TransBank bank:bankList){
            if (bank.getBankCode().equals(bankCode) && bank.getMoneyUnit().equals(mu)){
                return bank;
            }
        }
        return null;
    }

    private String stringLengthFormate(int length){
        String result=    String.valueOf(length);
        if(String.valueOf(length).length()<6){
            for(int i=result.length();i<6;i++){
                result="0"+ result;
            }
        }else{
            return String.valueOf(length).substring(0, 5);
        }
        return result;
    }

    private String getElementValue(Document doc,String path){
        Element element=(Element) doc.selectSingleNode(path);
        if (element!=null){
            return element.getTextTrim();
        }else{
            return null;
        }
    }

    public static void main(String[] params) throws DocumentException {
        String str="<?xml version=\"1.0\" encoding=\"GBK\"?>\n<root>\n  <head>\n    <TransCode>G00001</TransCode>\n    <TransDate>20150618</TransDate>\n    <TransTime>155607</TransTime>\n    <SeqNo>35582752</SeqNo>\n  </head>\n  <body>\n    <InstCode>32001757436052509237</InstCode>\n    <InDate>20150617170800</InDate>\n    <InAmount>100000.00</InAmount>\n    <InName>中国建设银行</InName>\n    <InAcct>9999990002002</InAcct>\n    <InMemo>32001757436052509237-000001</InMemo>\n    <InBankFLCode>3207511111</InBankFLCode>\n  </body>\n</root>\n\u0000\u0000\u0000\u0000\u0000\u0000";
        try{
            Document doc = DocumentHelper.parseText(str.trim());
            Element element=(Element) doc.selectSingleNode("//head/TransCode");
            System.out.println(element.getTextTrim());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
