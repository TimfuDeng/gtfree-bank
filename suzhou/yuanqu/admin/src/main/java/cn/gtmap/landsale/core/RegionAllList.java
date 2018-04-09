package cn.gtmap.landsale.core;

import java.util.List;

/**
 * 行政区列表
 * Created by Jibo on 2015/5/11.
 */
public class RegionAllList {
    //行政区
    List<String[]> regionList;
    //行政区部门
    List<String[]> regionDeptList;

    public List<String[]> getRegionDeptList() {
        return regionDeptList;
    }

    public void setRegionDeptList(List<String[]> regionDeptList) {
        this.regionDeptList = regionDeptList;
    }

    public List<String[]> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String[]> regionList) {
        this.regionList = regionList;
    }

    public String getRegionDeptName(String code){
        for(String[] values:regionDeptList){
            if(values[0].equals(code))
                return values[1];
        }
        return "";
    }

    public String getRegionName(String code){
        for(String[] values:regionList){
            if(values[0].equals(code))
                return values[1];
        }
        return "";
    }
}
