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

    public void setMaterialMap(Map<String, String> materialMap) {
        this.materialMap = materialMap;
    }

    /**
     * 获取到资料信息
     *
     * @return
     */
    @Override
    @Cacheable(value="MaterialCenterCache",key="'getMaterials'")
    public Map<String, String> getMaterials() {
        return materialMap;
    }
}
