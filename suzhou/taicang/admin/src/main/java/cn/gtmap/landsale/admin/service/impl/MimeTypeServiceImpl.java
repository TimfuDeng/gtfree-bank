package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.landsale.service.MimeTypeService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 根据文件名等获取MIME_TYPE等相关服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/1
 */
public class MimeTypeServiceImpl implements MimeTypeService {
    private static String DEFAULT_MIME_TYPE = "text/plain";

    private Map<String,String> mimeTypeMap = Maps.newHashMap();

    public void setMimeTypeMap(Map<String, String> mimeTypeMap) {
        this.mimeTypeMap = mimeTypeMap;
    }

    /**
     * @param fileName
     * @return
     */
    @Override
    public String getMimeType(String fileName) {
        String fileExt = FileUtils.getExt(fileName);
        if(StringUtils.isBlank(fileExt))
            return DEFAULT_MIME_TYPE;
        else {
            fileExt = StringUtils.lowerCase(fileExt);
            return mimeTypeMap.containsKey(fileExt)?mimeTypeMap.get(fileExt):DEFAULT_MIME_TYPE;
        }
    }
}
