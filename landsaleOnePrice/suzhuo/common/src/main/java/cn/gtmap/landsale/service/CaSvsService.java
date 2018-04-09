package cn.gtmap.landsale.service;

import cn.gtmap.landsale.Constants;

import java.io.UnsupportedEncodingException;

/**
 * CA数字证书SVS签名验证服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
public interface CaSvsService {
    /**
     * 验证证书是否有效
     * @param certificate 所需要验证的证书（B64编码）
     * @return
     */
    public boolean validateCertificate(String certificate) throws Exception;

    /**
     * 验证PKCS1签名是否正确
     * @param certificate 签名证书（B64编码）
     * @param signature 签名数据(B64编码)
     * @param data 原文数据((B64编码)
     * @param algo 签名算法
     * @param dataType 原文数据类型
     * @return
     */
    public boolean validatePKCS1Signature(String certificate,String signature,String data,String algo,Constants.CaOriginalDateType dataType) throws Exception;

    /**
     * 验证PKCS7签名是否正确
     * @param p7data PKCS7格式数据(B64编码)。注意：该pkcs7数据中应包含签名证书(链)、签名数据以及原文数据
     * @return
     */
    public boolean validatePKCS7Signature(String p7data) throws Exception;
}
