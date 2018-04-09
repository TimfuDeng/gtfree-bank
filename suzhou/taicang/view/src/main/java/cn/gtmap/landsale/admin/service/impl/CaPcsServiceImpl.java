package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.ArrayUtils;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.service.CaPcsService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/6
 */
public class CaPcsServiceImpl implements CaPcsService {

    private static final String SL_PATH="sl.svr";
    private static final String SG_PATH="sg.svr";
    private static final String SPE_PATH="spe.svr";
    private static final String SPD_PATH="spd.svr";
    private static final String SMP1_PATH="smp1.svr";
    private static final String SMP7_PATH="smp7.svr";

    private HttpClient httpClient;
    private String baseUrl;

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 获取可用的密钥ID
     *
     * @return
     */
    @Override
    public Collection<String> getAvailableKeyIds() throws Exception {
        String keyIds = post(baseUrl+"/"+SL_PATH,null);
        return StringUtils.isNotBlank(keyIds)?ArrayUtils.asList(StringUtils.split(keyIds,"\n")):null;
    }

    /**
     * 获取密钥ID对应的数字证书
     *
     * @param keyId 密钥Id
     * @return Base64编码的数字证书
     */
    @Override
    public String getCertificateByKeyId(String keyId) throws Exception {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("id", keyId));
        return post(baseUrl + "/" + SG_PATH, params);
    }

    /**
     * 私钥加密
     *
     * @param keyId    密钥Id
     * @param password 私钥保护的口令
     * @param data     BASE64编码的待加密数据（注意，私钥加密的明文最大长度不能超过私钥长度的2倍）
     * @return
     */
    @Override
    public String encryptByPrivateKey(String keyId, String password, String data) throws Exception {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("id", keyId));
        params.add(new BasicNameValuePair("passwd", password));
        params.add(new BasicNameValuePair("data", data));
        return post(baseUrl + "/" + SPE_PATH, params);
    }

    /**
     * 私钥解密
     *
     * @param keyId    密钥Id
     * @param password 私钥保护的口令
     * @param data     BASE64编码待解密数据
     * @return
     */
    @Override
    public String decryptByPrivateKey(String keyId, String password, String data) throws Exception {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("id", keyId));
        params.add(new BasicNameValuePair("passwd", password));
        params.add(new BasicNameValuePair("data", data));
        return post(baseUrl + "/" + SPD_PATH, params);
    }

    /**
     * 创建PKCS1签名
     *
     * @param keyId    密钥Id
     * @param password 私钥保护的口令
     * @param data     BASE64编码待签名数据
     * @param algo     数字摘要算法
     * @param dataType 原文数据类型
     * @return
     */
    @Override
    public String signPKCS1(String keyId, String password, String data, String algo, Constants.CaOriginalDateType dataType) throws Exception {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("id", keyId));
        params.add(new BasicNameValuePair("passwd", password));
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("algo", algo));
        params.add(new BasicNameValuePair("datt", String.valueOf(dataType.ordinal())));
        return post(baseUrl + "/" + SMP1_PATH, params);
    }

    /**
     * 创建PKCS7签名
     *
     * @param keyId    密钥Id
     * @param password 私钥保护的口令
     * @param data     BASE64编码待签名数据
     * @return
     */
    @Override
    public String signPKCS7(String keyId, String password, String data) throws Exception {
        List params = Lists.newArrayList();
        params.add(new BasicNameValuePair("id", keyId));
        params.add(new BasicNameValuePair("passwd", password));
        params.add(new BasicNameValuePair("data", data));
        return post(baseUrl + "/" + SMP7_PATH, params);
    }

    private String post(String url,List params) throws Exception{
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            HttpPost httPost = new HttpPost(url);
            if(params!=null&&!params.isEmpty())
                httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = closeableHttpClient.execute(httPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode==200) {
                return EntityUtils.toString(response.getEntity(), Charsets.CHARSET_UTF8);
            }else if(statusCode==400){
                throw new AppException(4102);
            }else if(statusCode==403){
                throw new AppException(4103);
            }else if(statusCode==404){
                throw new AppException(4104);
            }else if(StringUtils.startsWith(String.valueOf(statusCode),"5")){
                throw new AppException(4101);
            }
        }finally {
            if(response!=null)
                response.close();
        }
        return null;
    }
}
