package cn.gtmap.landsale.service;

import java.io.File;

/**
 * request或者response的contentType服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/1
 */
public interface MimeTypeService {

    /**
     *
     * @param fileName
     * @return
     */
    public String getMimeType(String fileName);
}