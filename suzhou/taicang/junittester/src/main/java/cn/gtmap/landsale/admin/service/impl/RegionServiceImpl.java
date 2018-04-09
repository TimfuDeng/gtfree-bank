package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.core.RegionAllList;
import cn.gtmap.landsale.service.RegionService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/5
 */
public class RegionServiceImpl implements RegionService {

    private RegionAllList regionAllList;

    private String defaultRegionCode;

    private String defaultRegionName;

    private String websiteICP;

    public void setRegionAllList(RegionAllList regionAllList) {
        this.regionAllList = regionAllList;
    }

    public void setDefaultRegionCode(String defaultRegionCode) {
        this.defaultRegionCode = defaultRegionCode;
    }

    public void setDefaultRegionName(String defaultRegionName) {
        this.defaultRegionName = defaultRegionName;
    }

    public void setWebsiteICP(String websiteICP) {
        this.websiteICP = websiteICP;
    }

    /**
     * 获取所有行政区
     *
     * @return
     */
    @Override
    //@Cacheable(value="RegionCache",key="'findAllRegions'")
    public List<String[]> findAllRegions() {
        return regionAllList.getRegionList();
    }

    /**
     * 根据行政区代码获取行政区配置信息
     *
     * @param regionCodes
     * @return
     */
    @Override
    //@Cacheable(value="RegionCache",key="'findRegionsByRegionCode'+#regionCodes")
    public List<String[]> findRegionsByRegionCode(Collection<String> regionCodes) {
        if(regionCodes==null||regionCodes.isEmpty())
            return null;
        else{
            List<String[]> result = Lists.newArrayList();
            for(String regionCode:regionCodes){
                String regionName = getRegionName(regionCode);
                if(StringUtils.isNotBlank(regionName))
                    result.add(new String[]{regionCode,getRegionName(regionCode)});
            }
            return result;
        }
    }

    /**
     * 获取行政区代码名称
     *
     * @param code 行政区代码
     * @return
     */
    @Override
    //@Cacheable(value="RegionCache",key="'getRegionName_Code'+#code")
    public String getRegionName(String code) {
        return regionAllList.getRegionName(code);
    }

    /**
     * 获取application.properties中的配置的默认行政区名称
     *
     * @return
     */
    @Override
    //@Cacheable(value="RegionCache",key="'getDefaultRegionName'")
    public String getDefaultRegionName() {
        return defaultRegionName;
    }

    /**
     * 获取application.properties中的配置的默认行政区代码
     *
     * @return
     */
    @Override
    //@Cacheable(value="RegionCache",key="'getDefaultRegionCode'")
    public String getDefaultRegionCode() {
        return defaultRegionCode;
    }

    @Override
    //@Cacheable(value="RegionCache",key="'getDefaultWebsiteICP'")
    public String getDefaultWebsiteICP() {
        return websiteICP;
    }


}
