package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.landsale.service.RegionService;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/5
 */
public class RegionUtil {

    public String getRegionNameByCode(String code){
        return "太仓市";
    }

    public String getDefaultRegionName(){
        return "太仓市";
    }

    public String getDefaultRegionCode(){
        return "320585";
    }

    public String getDefaultWebsiteICP(){
        return "苏ICP备10217070号-3";
    }
}
