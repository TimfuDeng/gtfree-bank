package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.landsale.core.service.MimeTypeService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by M150237 on 2017-10-25.
 */
@Service
public class MimeTypeServiceImpl implements MimeTypeService {
    private static String DEFAULT_MIME_TYPE = "text/plain";

    private Map<String, String> mimeTypeMap = Maps.newHashMap();

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
        if (StringUtils.isBlank(fileExt)) {
            return DEFAULT_MIME_TYPE;
        } else {
            fileExt = StringUtils.lowerCase(fileExt);
            return mimeTypeMap.containsKey(fileExt) ? mimeTypeMap.get(fileExt) : DEFAULT_MIME_TYPE;
        }
    }
}
