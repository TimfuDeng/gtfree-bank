package cn.gtmap.landsale.client.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCrgg;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CA数字证书SVS签名验证服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@FeignClient(name = "core-server")
public interface CaSvsClient {

    /**
     * 验证证书是否有效
     * @param certificate 所需要验证的证书（B64编码）
     * @return
     */
    @RequestMapping(value = "/caSvs/validateCertificate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage validateCertificate(@RequestParam(value = "certificate") String certificate);

    /**
     * 验证PKCS1签名是否正确
     * @param certificate 签名证书（B64编码）
     * @param signature 签名数据(B64编码)
     * @param data 原文数据((B64编码)
     * @param algo 签名算法
     * @param dataType 原文数据类型
     * @return
     */
    @RequestMapping(value = "/caSvs/validatePKCS1Signature", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage validatePKCS1Signature(@RequestParam("certificate") String certificate, @RequestParam("signature") String signature, @RequestParam("data") String data, @RequestParam("algo") String algo, @RequestBody Constants.CaOriginalDateType dataType);

    /**
     * 验证PKCS7签名是否正确
     * @param p7data PKCS7格式数据(B64编码)。注意：该pkcs7数据中应包含签名证书(链)、签名数据以及原文数据
     * @return
     */
    @RequestMapping(value = "/caSvs/validatePKCS7Signature", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage validatePKCS7Signature(@RequestParam(value = "p7data") String p7data);

}
