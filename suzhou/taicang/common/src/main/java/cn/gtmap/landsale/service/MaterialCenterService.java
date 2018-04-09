package cn.gtmap.landsale.service;

import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/7/28
 */
public interface MaterialCenterService {
    /**
     * 获取到资料信息
     * @return
     */
    public Map<String,String> getMaterials();
    
	/**
     * 获取到出让相关资料信息
     *
     * @return
     */
    public Map<String, String> getMaterials_cr();
}
