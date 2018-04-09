package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.admin.service.SmsMessageService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Jibo on 2015/6/12.
 */
public class SmsMessageServiceImpl implements SmsMessageService {

    private HttpClient httpClient;
    private String spUrl;

    public void setSpUrl(String spUrl) {
        this.spUrl = spUrl;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    @Override
    @Async
    public void send(String body, String mobiles) throws Exception{
        /*CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            HttpPost httPost = new HttpPost(spUrl);

            List params = Lists.newArrayList();
            params.add(new BasicNameValuePair("text", body.trim()));
            params.add(new BasicNameValuePair("sendAt", DateFormatUtils.ISO_DATETIME_FORMAT.format(Calendar.getInstance().getTime())));
            params.add(new BasicNameValuePair("mobile", mobiles));
            httPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = closeableHttpClient.execute(httPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode==200) {
               String httpResult = EntityUtils.toString(response.getEntity(), Charsets.CHARSET_UTF8);
                if(StringUtils.isNotBlank(httpResult)){
                    JSONObject json = JSON.parseObject(httpResult);
                    if (!json.getBooleanValue("ret")) {
                        throw new AppException(json.getString("msg"));
                    }
                }else {
                    throw new AppException(6101);
                }
            }else{
                throw new AppException(6101);
            }
        }finally {
            if(response!=null)
                response.close();
        }*/
    }
}
