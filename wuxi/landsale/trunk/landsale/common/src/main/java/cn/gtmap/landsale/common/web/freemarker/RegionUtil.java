package cn.gtmap.landsale.common.web.freemarker;

import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.register.TransRegionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 行政区工具类
 * @author zsj
 * @version v1.0, 2017/12/1
 */
@Component
public class RegionUtil {

    @Autowired
    TransRegionClient transRegionClient;


    public String getRegionNameByCode(String regionCode){
        TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(regionCode);
        if (transRegion != null) {
            return transRegion.getRegionName();
        }
        return null;
    }

}
