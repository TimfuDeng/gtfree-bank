package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransMaterial;

import java.util.List;

/**
 * Created by u on 2016/2/29.
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
    public List<TransMaterial> getMaterialsBymaterialType(String regionCode,String materialType);

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
    public TransMaterial save(TransMaterial transMaterial);
}
