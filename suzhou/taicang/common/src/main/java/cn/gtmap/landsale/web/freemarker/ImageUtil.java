package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.service.TransFileService;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/15.
 */
public class ImageUtil {

    TransFileService transFileService;

    public void setTransFileService(TransFileService transFileService) {
        this.transFileService = transFileService;
    }


    public String url(String resourceId,String resolution) throws Exception {
        List<TransFile> transFileList=
                transFileService.getTransFileThumbnails(resourceId,resolution);
        if (transFileList.size()==0)
            return Constants.BLANK_IMAGE_PATH;
        else
            return Constants.IMAGE_BASE_PATH + transFileList.get(0).getFileId();
    }
}
