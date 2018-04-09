package cn.gtmap.landsale.core;

import java.util.List;

/**
 * 行政区列表
 * Created by Jibo on 2015/5/11.
 */
public class RegionAllList {
    List<String[]> regionList;

    public List<String[]> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String[]> regionList) {
        this.regionList = regionList;
    }


    public String getRegionName(String code){
        for(String[] values:regionList){
            if(values[0].equals(code))
                return values[1];
        }
        return "";
    }
}
