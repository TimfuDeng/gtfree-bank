package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.TransMaterial;

import java.util.List;

/**
 * 材料Service
 * @author zsj
 * @version v1.0, 2017/10/23
 */
public interface TransMaterialService {

    /**
     * 根据行政区代码获取所需上传的材料
     * @param regionCode
     * @return
     */
    public List<TransMaterial> getMaterialsByRegionCode(String regionCode);

    /**
     * 根据材料类型获取所需上传材料
     * @param materialType
     * @param regionCode
     * @return
     */
    public List<TransMaterial> getMaterialsBymaterialType(String regionCode, String materialType);

    /**
     * 根据Id获取材料
     * @param materialId
     * @return
     */
    public TransMaterial getMaterialsById(String materialId);

    /**
     * 保存材料
     * @param transMaterial
     * @return
     */
    public TransMaterial saveTransMaterial(TransMaterial transMaterial);
}
