package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.service.MaterialCenterService;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/7/28
 */
public class MaterialCenterServiceImpl implements MaterialCenterService {

    Map<String,String> materialMap;
    
    Map<String,String> materialMap_cr;

    public void setMaterialMap(Map<String, String> materialMap) {
        this.materialMap = materialMap;
    }

	public void setMaterialMap_cr(Map<String, String> materialMap_cr) {
		this.materialMap_cr = materialMap_cr;
	}

	/**
     * 获取到资料信息
     *
     * @return
     */
    @Override
    //@Cacheable(value="MaterialCenterCache",key="'getMaterials'")
    public Map<String, String> getMaterials() {
        return materialMap;
    }
    
	/**
     * 获取到出让相关资料信息
     *
     * @return
     */
    @Override
    //@Cacheable(value="CrMaterialCenterCache",key="'getMaterials_cr'")
    public Map<String, String> getMaterials_cr() {
        return materialMap_cr;
    }
}
