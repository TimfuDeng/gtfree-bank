package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransBankPay;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.TransBankAccountService;
import cn.gtmap.landsale.service.TransBankPayService;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.util.BankXmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理银行的发送信息
 * Created by Jibo on 2015/5/14.
 */
@Deprecated
public class BankXmlReceiverHandle {
    private static Logger log = LoggerFactory.getLogger(BankXmlReceiverHandle.class);

    TransBankPayService transBankPayService;

    TransBankAccountService transBankAccountService;

    TransResourceApplyService transResourceApplyService;

    TransResourceService transResourceService;

    public void setTransBankPayService(TransBankPayService transBankPayService) {
        this.transBankPayService = transBankPayService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

//    /**
//     * 接受到银行的请求
//     * @param receiveXml
//     */
//    public String receiveXml(String receiveXml){
//        try{
//            log.debug("----socket receive:"+receiveXml );
//            Document doc= DocumentHelper.parseText(receiveXml);
//            Element codeElement=(Element) doc.selectSingleNode("//trxcode");
//            if (codeElement!=null && "10001".equals(codeElement.getTextTrim())){
//                //生成子帐号（这里是返回测试信息，测试用的）
//                String applyNo=getElementValue(doc,"//busino");
//                StringBuffer returnXml= BankXmlUtil.buildBankApplyAccountBackXml(applyNo, "6200" + applyNo);
//                log.debug("----socket send:"+returnXml.toString() );
//                return returnXml.toString();
//            }else if(codeElement!=null && "10002".equals(codeElement.getTextTrim())){
//                String payNo = getElementValue(doc, "//input/busino");
//                try{
//                    TransBankPay transBankPay= receivePay(doc);
//                    return BankXmlUtil.buildBankPayBackXml("10002","0000",transBankPay.getPayNo(),"").toString();
//                }catch (Exception ex){
//                    return BankXmlUtil.buildBankPayBackXml("10002","0001",payNo,ex.getMessage()).toString();
//                }
//            }
//
//        }catch (Exception ex){
//            log.error(ex.getMessage(), ex.getCause());
//        }
//        return null;
//    }
//
//    private TransBankPay receivePay(Document doc) throws Exception{
//        //到账信息
//        String payNo = getElementValue(doc, "//input/busino");
//        String accountCode = getElementValue(doc, "//input/craccno");
//        //根据流水号去找，看accountCode是否对
//        String busiid = getElementValue(doc, "//input/busiid");
//        TransBankAccount transBankAccount =
//                transBankAccountService.getTransBankAccountByApplyNo(busiid);
//        if (transBankAccount.getAccountCode().equals(accountCode)) {
//            if (StringUtils.isNotBlank(payNo) && StringUtils.isNotBlank(accountCode)) {
//                TransBankPay transBankPay = createOrGetTransBankPay(accountCode, payNo);
//                transBankPay.setBankCode(getElementValue(doc, "//input/bankid"));
//                transBankPay.setPayBank(getElementValue(doc, "//input/draccbank"));
//                transBankPay.setPayBankAddress(getElementValue(doc, "//input/draccsubbank"));
//                transBankPay.setPayBankAccount(getElementValue(doc, "//input/draccno"));
//                transBankPay.setPayName(getElementValue(doc, "//input/draccname"));
//                transBankPay.setAccountCode(getElementValue(doc, "//input/craccno"));
//                transBankPay.setPayNo(getElementValue(doc, "//input/busino"));
//                transBankPay.setAccountName(getElementValue(doc, "//input/craccname"));
//                transBankPay.setAmount(Double.parseDouble(getElementValue(doc, "//input/amount")));
//                if (getElementValue(doc, "//input/amount") != null)
//                    transBankPay.setAmount(Double.parseDouble(getElementValue(doc, "//input/amount")));
//                transBankPay.setRemark(getElementValue(doc, "//input/remark"));
//                transBankPay.setPayTime(buildDate(getElementValue(doc, "//input/transtime")));
//                transBankPay.setApplyNo(getElementValue(doc, "//input/busiid"));
//                if (getElementValue(doc, "//input/currency")!=null) {
//                    transBankPay.setMoneyUnit(getElementValue(doc, "//input/currency"));
//                }else{
//                    transBankPay.setMoneyUnit("CNY");
//                }
//                saveTransBankPay(transBankPay);
//                return transBankPay;
//            }else{
//                throw  new Exception("交款帐号和流水号必须填写");
//            }
//        } else {
//            throw  new Exception("交款帐号和竞买流水号不匹配");
//        }
//    }
//
//    private Date buildDate(String dateStr){
//        try {
//            if (StringUtils.isNotBlank(dateStr)) {
//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//                return df.parse(dateStr);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }
//    private TransBankPay createOrGetTransBankPay(String accountCode,String payNo){
//        List<TransBankPay> transBankPayList=  transBankPayService.getTransBankPaysByAccountCode(accountCode);
//        TransBankPay bankPay=new TransBankPay();
//        if (transBankPayList.size()>0){
//            for(TransBankPay transBankPay:transBankPayList){
//                if (transBankPay.equals(transBankPay.getPayNo())) {
//                    bankPay = transBankPay;
//                    break;
//                }
//            }
//        }
//        return bankPay;
//    }
//
//    private void saveTransBankPay(TransBankPay transBankPay){
//        transBankPayService.saveTransBankPay(transBankPay);
//        //判读是否交足保证金
//        List<TransBankPay> transBankPayList=
//                transBankPayService.getTransBankPaysByAccountCode(transBankPay.getAccountCode());
//        double amount=0;
//        for(TransBankPay bankPay: transBankPayList){
//            amount+=bankPay.getAmount();
//        }
//        TransBankAccount transBankAccount=
//                transBankAccountService.getTransBankAccountByAccountCode(transBankPay.getAccountCode());
//        TransResourceApply transResourceApply=
//                transResourceApplyService.getTransResourceApply(transBankAccount.getApplyId());
//        TransResource transResource=
//                transResourceService.getTransResource(transResourceApply.getResourceId());
//        if (amount>=transResource.getFixedOffer()) {
//            transResourceApply.setApplyStep(Constants.StepOver);
//            transResourceApplyService.saveTransResourceApply(transResourceApply);
//        }
//    }
//
//    private String getElementValue(Document doc,String path){
//        Element element=(Element) doc.selectSingleNode(path);
//        if (element!=null){
//            return element.getTextTrim();
//        }else{
//            return null;
//        }
//    }
}
