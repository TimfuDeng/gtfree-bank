package cn.gtmap.landsale.view.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.service.ClientFileService;
import cn.gtmap.landsale.service.TransFileService;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;

/**
 * 文件服务实现
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/19
 */
public class FileServiceImpl implements ClientFileService {
    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

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
     *
     * @param bytes    文件字节流
     * @param fileName 文件名称
     * @param fileKey  文件key
     * @param fileType 文件类型
     * @return
     * @throws Exception
     */
    @Override
    public TransFile uploadFile(byte[] bytes, String fileName, String fileKey, String fileType) throws Exception {
        return null;
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
            logger.info("requestUrl",requestUrl.toString());
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
