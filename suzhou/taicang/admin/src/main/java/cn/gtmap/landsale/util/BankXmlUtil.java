package cn.gtmap.landsale.util;

import cn.gtmap.landsale.model.TransBank;
import cn.gtmap.landsale.model.TransBankPay;
import org.apache.commons.codec.digest.DigestUtils;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
@Deprecated
public class BankXmlUtil {


//    public static StringBuffer buildBankApplyAccountXml(String applyNo,String moneyType,TransBank transBank){
//        String strMd5=DigestUtils.md5Hex("10001" +applyNo + transBank.getAccountCode()+ transBank.getAccountName()+transBank.getBankCode());
//        StringBuffer stringBuffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//        stringBuffer.append("<root>");
//        stringBuffer.append("<input>");
//        stringBuffer.append("<trxcode>10001</trxcode>");
//        stringBuffer.append("<busino>"+applyNo+"</busino>");
//        stringBuffer.append("<orgno>"+transBank.getAccountCode()+"</orgno>");
//        stringBuffer.append("<currency>"+moneyType+"</currency>");
//        stringBuffer.append("<username>"+transBank.getAccountName()+"</username>");
//        stringBuffer.append("<remark></remark>");
//        stringBuffer.append("<bankid>"+transBank.getBankCode()+"</bankid>");
//        stringBuffer.append("<rsa>"+strMd5+"</rsa>");
//        stringBuffer.append("</input>");
//        stringBuffer.append("</root>");
//        return stringBuffer;
//    }
//
//    public static StringBuffer buildBankApplyAccountBackXml(String applyCode,String accountCode){
//        StringBuffer stringBuffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//        stringBuffer.append("<root>");
//        stringBuffer.append("<input>");
//        stringBuffer.append("<trxcode>10001</trxcode>");
//        stringBuffer.append("</input>");
//        stringBuffer.append("<output>");
//        stringBuffer.append("<status>00</status>");
//        stringBuffer.append("<retcode>000</retcode>");
//        stringBuffer.append("<retmsg></retmsg>");
//        stringBuffer.append("<accno>"+accountCode+"</accno>");
//        stringBuffer.append("<commseq>"+applyCode+"</commseq>");
//        stringBuffer.append("</output>");
//        stringBuffer.append("</root>");
//        return stringBuffer;
//    }
//
//    public static StringBuffer buildBankPayBackXml(String trxcode,String retcode,
//                                                   String applyCode,String result){
//        StringBuffer stringBuffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//        stringBuffer.append("<root>");
//        stringBuffer.append("<input>");
//        stringBuffer.append("<trxcode>"+trxcode+"</trxcode>");
//        stringBuffer.append("</input>");
//        stringBuffer.append("<output>");
//        stringBuffer.append("<status>00</status>");
//        stringBuffer.append("<retcode>"+retcode+"</retcode>");
//        stringBuffer.append("<retmsg>"+result+"</retmsg>");
//        stringBuffer.append("<commseq>"+applyCode+"</commseq>");
//        stringBuffer.append("</output>");
//        stringBuffer.append("</root>");
//        return stringBuffer;
//    }
//
//    public static StringBuffer buildBankPayXml(TransBankPay transBankPay){
//        StringBuffer stringBuffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
//        stringBuffer.append("<root>");
//        stringBuffer.append("<input>");
//        stringBuffer.append("<trxcode>10002</trxcode>");
//        stringBuffer.append("<busino>"+transBankPay.getApplyNo()+"</busino>");
//        stringBuffer.append("<bankid>"+transBankPay.getBankCode()+"</bankid>");
//        stringBuffer.append("<draccbank>"+transBankPay.getPayBank()+"</draccbank>");
//        stringBuffer.append("<draccsubbank>"+transBankPay.getPayBankAddress()+"</draccsubbank>");
//        stringBuffer.append("<draccno>"+transBankPay.getPayBankAccount()+"</draccno>");
//        stringBuffer.append("<draccname>"+transBankPay.getPayName()+"</draccname>");
//        stringBuffer.append("<craccno>"+transBankPay.getAccountCode()+"</craccno>");
//        stringBuffer.append("<craccname>"+transBankPay.getAccountName()+"</craccname>");
//        stringBuffer.append("<amount>"+transBankPay.getAmount()+"</amount>");
//        stringBuffer.append("<currency>"+transBankPay.getMoneyUnit()+"</currency>");
//        stringBuffer.append("<rate>"+transBankPay.getRate()+"</rate>");
//        stringBuffer.append("<transtime>"+getDateTimeStr(transBankPay.getPayTime())+"</transtime>");
//        stringBuffer.append("<busiid>"+transBankPay.getApplyNo()+"</busiid>");
//        stringBuffer.append("<remark>"+transBankPay.getRemark()+"</remark>");
//        stringBuffer.append("<rsa></rsa>");
//        stringBuffer.append("</input>");
//        stringBuffer.append("</root>");
//        return stringBuffer;
//    }
//
//    private static String getDateTimeStr(Date cDate){
//        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
//        return df.format(cDate);
//    }

}
