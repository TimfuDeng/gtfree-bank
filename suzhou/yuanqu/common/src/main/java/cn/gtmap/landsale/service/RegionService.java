package cn.gtmap.landsale.service;

import java.util.Collection;
import java.util.List;

/**
 * 行政区服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/5
 */
public interface RegionService {
    /**
     * 获取所有行政区
     * @return
     */
    public List<String[]> findAllRegions();

    /**
     * 获取行政区部门信息
     * @return
     */
    public List<String[]> findAllDeptRegions();

    /**
     * 根据行政区代码获取行政区配置信息
     * @param regionCodes
     * @return
     */
    public List<String[]> findRegionsByRegionCode(Collection<String> regionCodes);

    /**
     * 获取行政区代码名称
     * @param code 行政区代码
     * @return
     */
    public String getRegionName(String code);

    /**
     * 获取application.properties中的配置的默认行政区名称
     * @return
     */
    public String getDefaultRegionName();

    /**
     * 获取application.properties中的配置的默认行政区代码
     * @return
     */
    public String getDefaultRegionCode();

    /**
     * 获取当前行政区网站的ICP备案号
     * @return
     */
    public String getDefaultWebsiteICP();

}
