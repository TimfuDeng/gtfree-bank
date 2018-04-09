package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.support.http.HttpClientFactoryBean;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.CaSvsService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CA数字证书SVS签名验证服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
@Service
public class CaSvsServiceImpl implements CaSvsService {

    @Resource(name = "httpClientFactoryBean")
    private HttpClient httpClient;

    @Autowired
    private HttpClientFactoryBean httpClientFactoryBean;

    @Value("${ca.svs.url}")
    private String baseUrl;

    private static final String VC_PATH="vc.svr";
    private static final String VP1_PATH="vp1.svr";
    private static final String VP7_PATH="vp7.svr";
    private static final String DEFAULT_GREEN_PASS="0";

    /**
     * 验证证书是否有效
     *
     * @param certificate 所需要验证的证书（B64编码）
     * @return
     */
    @Override
    public ResponseMessage validateCertificate(String certificate) {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("cert", certificate));
        try {
            return new ResponseMessage(validate(baseUrl+"/"+VC_PATH,params));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(false, e.getMessage());
        }
    }

    /**
     * 验证PKCS1签名是否正确
     *
     * @param certificate 签名证书（B64编码）
     * @param signature   签名数据(B64编码)
     * @param algo        签名算法
     * @param data        原文数据(B64编码)
     * @param dataType    原文数据类型
     * @return
     */
    @Override
    public ResponseMessage validatePKCS1Signature(String certificate, String signature, String data, String algo, Constants.CaOriginalDateType dataType) {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("cert", certificate));
        params.add(new BasicNameValuePair("signature", signature));
        params.add(new BasicNameValuePair("algo", algo));
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("datt", String.valueOf(dataType.ordinal())));
        try {
            return new ResponseMessage(validate(baseUrl+"/"+VP1_PATH,params));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(false, e.getMessage());
        }
    }



    /**
     * 验证PKCS7签名是否正确
     *
     * @param p7data PKCS7格式数据(B64编码)。注意：该pkcs7数据中应包含签名证书(链)、签名数据以及原文数据
     * @return
     */
    @Override
    public ResponseMessage validatePKCS7Signature(String p7data) {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("p7data", p7data));
        try {
            return new ResponseMessage(validate(baseUrl+"/"+VP7_PATH,params));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(false, e.getMessage());
        }
    }

    /**
     * HTTP验证情况服务
     * @param url 地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    private boolean validate(String url, List params) throws Exception{
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient) httpClient;
            HttpPost httPost = new HttpPost(url);
            params.add(new BasicNameValuePair("greenpass", DEFAULT_GREEN_PASS));
            httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = closeableHttpClient.execute(httPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String prefix4 = "4";
            String prefix5 = "5";
            if (statusCode==200) {
                return true;
            }else if(StringUtils.startsWith(String.valueOf(statusCode),prefix4)){
                return false;
            }else if(StringUtils.startsWith(String.valueOf(statusCode),prefix5)){
                throw new AppException(4101);
            }
        }finally {
            if(response!=null) {
                response.close();
            }
        }
        return false;
    }
}
