package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.landsale.service.RegionService;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/5
 */
public class RegionUtil {
    RegionService regionService;

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public String getRegionNameByCode(String code){
        return regionService.getRegionName(code);
    }

    public String getDefaultRegionName(){
        return regionService.getDefaultRegionName();
    }

    public String getDefaultRegionCode(){
        return regionService.getDefaultRegionCode();
    }

    public String getDefaultWebsiteICP(){
        return regionService.getDefaultWebsiteICP();
    }
}
