package cn.gtmap.landsale.model;

import java.io.Serializable;

/**
 * 数字证书Signer对象信息
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
public class CaSignerX implements Serializable{
    private String sxaction; //数据签名打包方式
    private String sxdigest; //数据摘要算法类型
    private String sxoptexpress; //是否启用快捷模式
    private String sxoptcertfilter; //是否启用证书过滤
    private String sxinput; //表单域待签名源数据
    private String sxcertificate; //数据签名使用证书
    private String pkcs1; //PKCS1签名结果
    private String pkcs7; //PKCS7签名结果
    private String certFriendlyName; //证书用户
    private String certThumbprint; //证书指纹
    private String serialNumber;//唯一ca识别指纹


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSxaction() {
        return sxaction;
    }

    public void setSxaction(String sxaction) {
        this.sxaction = sxaction;
    }

    public String getSxdigest() {
        return sxdigest;
    }

    public void setSxdigest(String sxdigest) {
        this.sxdigest = sxdigest;
    }

    public String getSxoptexpress() {
        return sxoptexpress;
    }

    public void setSxoptexpress(String sxoptexpress) {
        this.sxoptexpress = sxoptexpress;
    }

    public String getSxoptcertfilter() {
        return sxoptcertfilter;
    }

    public void setSxoptcertfilter(String sxoptcertfilter) {
        this.sxoptcertfilter = sxoptcertfilter;
    }

    public String getSxinput() {
        return sxinput;
    }

    public void setSxinput(String sxinput) {
        this.sxinput = sxinput;
    }

    public String getSxcertificate() {
        return sxcertificate;
    }

    public void setSxcertificate(String sxcertificate) {
        this.sxcertificate = sxcertificate;
    }

    public String getPkcs1() {
        return pkcs1;
    }

    public void setPkcs1(String pkcs1) {
        this.pkcs1 = pkcs1;
    }

    public String getPkcs7() {
        return pkcs7;
    }

    public void setPkcs7(String pkcs7) {
        this.pkcs7 = pkcs7;
    }

    public String getCertFriendlyName() {
        return certFriendlyName;
    }

    public void setCertFriendlyName(String certFriendlyName) {
        this.certFriendlyName = certFriendlyName;
    }

    public String getCertThumbprint() {
        return certThumbprint;
    }

    public void setCertThumbprint(String certThumbprint) {
        this.certThumbprint = certThumbprint;
    }
}
