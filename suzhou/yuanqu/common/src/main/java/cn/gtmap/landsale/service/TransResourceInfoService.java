package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransResourceInfo;

/**
 * Created by Jibo on 2015/5/18.
 */
public interface TransResourceInfoService {

    TransResourceInfo getTransResourceInfo(String infoId);

    TransResourceInfo getTransResourceInfoByResourceId(String resourceId);

    TransResourceInfo saveTransResourceInfo(TransResourceInfo transResourceInfo);

}
