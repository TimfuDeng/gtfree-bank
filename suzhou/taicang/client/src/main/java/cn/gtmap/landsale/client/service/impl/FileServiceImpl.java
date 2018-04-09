package cn.gtmap.landsale.client.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.service.ClientFileService;
import cn.gtmap.landsale.service.TransFileService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

/**
 * 文件服务实现
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/19
 */
public class FileServiceImpl implements ClientFileService {

    @Autowired
    TransFileService transFileService;

    private HttpClient httpClient;

    private String adminUrl;

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public void setAdminUrl(String adminUrl) {
        this.adminUrl = adminUrl;
    }


    /**
     * 上传文件
     * @param bytes 文件字节流
     * @param fileName 文件名称
     * @param fileKey 文件key
     * @param fileType 文件类型
     * @return
     * @throws Exception
     */
    @Override
    public TransFile uploadFile(byte[] bytes, String fileName, String fileKey, String fileType) throws Exception {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            HttpPost httpost = new HttpPost(adminUrl+"/file/upload.f");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charsets.CHARSET_UTF8);
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody("file", bytes, ContentType.DEFAULT_BINARY, fileName);
            builder.addTextBody("fileKey", fileKey, ContentType.TEXT_PLAIN);
            builder.addTextBody("fileType", fileType, ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = builder.build();
            httpost.setEntity(reqEntity);
            response = closeableHttpClient.execute(httpost);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode()==200) {
                return JSON.parseObject(EntityUtils.toString(entity, Charsets.CHARSET_UTF8), TransFile.class);
            }else{
                throw new AppException(5102);
            }
        }finally {
            if(response!=null)
                response.close();
        }
    }

    /**
     * 浏览文件，例如图片
     *
     * @param fileId
     * @return
     */
    @Override
    public byte[] viewFile(String fileId) throws Exception {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            StringBuilder requestUrl = new StringBuilder();
            requestUrl.append(adminUrl).append("/file/view.f?fileId=").append(fileId);
            HttpGet httpGet = new HttpGet(requestUrl.toString());
            response = closeableHttpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toByteArray(entity);
            }else if(response.getStatusLine().getStatusCode()==404){
                throw new FileNotFoundException();
            }else{
                throw new AppException(5103);
            }
        }finally {
            if(response!=null)
                response.close();
        }
    }


}
