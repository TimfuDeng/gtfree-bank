package cn.gtmap.landsale.service;

import cn.gtmap.landsale.Constants;

import java.util.Collection;
import java.util.Set;

/**CA数字证书PCS密码运算服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/6
 */
public interface CaPcsService {
    /**
     * 获取可用的密钥ID
     * @return
     */
    public Collection<String> getAvailableKeyIds() throws Exception;

    /**
     * 获取密钥ID对应的数字证书
     * @param keyId 密钥Id
     * @return Base64编码的数字证书
     */
    public String getCertificateByKeyId(String keyId) throws Exception;

    /**
     *  私钥加密
     * @param keyId 密钥Id
     * @param password 私钥保护的口令
     * @param data BASE64编码的待加密数据（注意，私钥加密的明文最大长度不能超过私钥长度的2倍）
     * @return
     */
    public String encryptByPrivateKey(String keyId,String password,String data) throws Exception;

    /**
     * 私钥解密
     * @param keyId 密钥Id
     * @param password 私钥保护的口令
     * @param data BASE64编码待解密数据
     * @return
     */
    public String decryptByPrivateKey(String keyId,String password,String data) throws Exception;

    /**
     * 创建PKCS1签名
     * @param keyId 密钥Id
     * @param password 私钥保护的口令
     * @param data BASE64编码待签名数据
     * @param algo 数字摘要算法
     * @param dataType 原文数据类型
     * @return
     */
    public String signPKCS1(String keyId,String password,String data,String algo,Constants.CaOriginalDateType dataType) throws Exception;

    /**
     * 创建PKCS7签名
     * @param keyId 密钥Id
     * @param password 私钥保护的口令
     * @param data BASE64编码待签名数据
     * @return
     */
    public String signPKCS7(String keyId,String password,String data) throws Exception;
}
