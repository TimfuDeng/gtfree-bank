package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.service.CaSvsService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * CA数字证书SVS签名验证服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/22
 */
public class CaSvsServiceImpl implements CaSvsService {
    private HttpClient httpClient;
    private String baseUrl;
    private static final String VC_PATH="vc.svr";
    private static final String VP1_PATH="vp1.svr";
    private static final String VP7_PATH="vp7.svr";
    private static final String DEFAULT_GREEN_PASS="0";


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 验证证书是否有效
     *
     * @param certificate 所需要验证的证书（B64编码）
     * @return
     */
    @Override
    public boolean validateCertificate(String certificate) throws Exception {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("cert", certificate));
        return validate(baseUrl+"/"+VC_PATH,params);
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
    public boolean validatePKCS1Signature(String certificate,String signature,String data,String algo,Constants.CaOriginalDateType dataType) throws Exception{
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("cert", certificate));
        params.add(new BasicNameValuePair("signature", signature));
        params.add(new BasicNameValuePair("algo", algo));
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("datt", String.valueOf(dataType.ordinal())));
        return validate(baseUrl+"/"+VP1_PATH,params);
    }



    /**
     * 验证PKCS7签名是否正确
     *
     * @param p7data PKCS7格式数据(B64编码)。注意：该pkcs7数据中应包含签名证书(链)、签名数据以及原文数据
     * @return
     */
    @Override
    public boolean validatePKCS7Signature(String p7data) throws Exception{
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("p7data", p7data));
        return validate(baseUrl+"/"+VP7_PATH,params);
    }

    /**
     * HTTP验证情况服务
     * @param url 地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    private boolean validate(String url,List params) throws Exception{
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            HttpPost httPost = new HttpPost(url);
            params.add(new BasicNameValuePair("greenpass", DEFAULT_GREEN_PASS));
            httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = closeableHttpClient.execute(httPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode==200) {
                return true;
            }else if(StringUtils.startsWith(String.valueOf(statusCode),"4")){
                return false;
            }else if(StringUtils.startsWith(String.valueOf(statusCode),"5")){
                throw new AppException(4101);
            }
        }finally {
            if(response!=null)
                response.close();
        }
        return false;
    }
}
