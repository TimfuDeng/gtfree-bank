package cn.gtmap.landsale.core.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.core.service.CaSvsService;
import cn.gtmap.landsale.core.service.TransCrggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * CA数字证书SVS签名验证服务
 * @author zsj
 * @version v1.0, 2017/11/27
 */
@RestController
@RequestMapping("/caSvs")
public class CaSvsController {

    @Autowired
    CaSvsService caSvsService;

    /**
     * 验证证书是否有效
     * @param certificate 所需要验证的证书（B64编码）
     * @return
     */
    @RequestMapping("/validateCertificate")
    public ResponseMessage validateCertificate(@RequestParam("certificate") String certificate) {
        return caSvsService.validateCertificate(certificate);
    }

    /**
     * 验证PKCS1签名是否正确
     * @param certificate 签名证书（B64编码）
     * @param signature 签名数据(B64编码)
     * @param data 原文数据((B64编码)
     * @param algo 签名算法
     * @param dataType 原文数据类型
     * @return
     */
    @RequestMapping("/validatePKCS1Signature")
    public ResponseMessage validatePKCS1Signature(@RequestParam("certificate") String certificate, @RequestParam("signature") String signature, @RequestParam("data") String data, @RequestParam("algo") String algo, @RequestBody Constants.CaOriginalDateType dataType) {
        return caSvsService.validatePKCS1Signature(certificate, signature, data, algo, dataType);
    }

    /**
     * 验证PKCS7签名是否正确
     * @param p7data PKCS7格式数据(B64编码)。注意：该pkcs7数据中应包含签名证书(链)、签名数据以及原文数据
     * @return
     */
    @RequestMapping("/validatePKCS7Signature")
    public ResponseMessage validatePKCS7Signature(@RequestParam(value = "p7data") String p7data) {
        return caSvsService.validatePKCS7Signature(p7data);
    }

}
