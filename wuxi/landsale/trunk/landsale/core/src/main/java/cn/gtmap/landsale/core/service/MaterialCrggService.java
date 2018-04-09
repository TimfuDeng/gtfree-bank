package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.MaterialCrgg;

import java.util.Collection;
import java.util.List;

/**
 * 出让公告 材料 关系Service
 * @author zsj
 * @version v1.0, 2017/10/23
 */
public interface MaterialCrggService {

    /**
     * 保存材料公告关联对象
     * @param materialCrgg
     * @return
     */
    public MaterialCrgg saveMaterialCrgg(MaterialCrgg materialCrgg);

    /**
     * 根据主键获得关联对象
     * @param tmcId
     * @return
     */
    public MaterialCrgg getMaterialById(String tmcId);

    /**
     * 根据材料id获取关联对象集合
     * @param materialId
     * @return
     */
    public MaterialCrgg getMaterialByMaterialId(String materialId);

    /**
     * 根据公告id获取关联对象集合
     * @param crggId
     * @return
     */
    public List<MaterialCrgg> getMaterialCrggByCrggId(String crggId);

    /**
     * 删除同一公告id下所有的MaterialCrgg对象
     * @param crggId
     */
    public void deleteMaterialCrggList(String crggId);

    /**
     * 删除单个实体
     * @param materialCrgg
     */
    public void deleteMaterialCrgg(MaterialCrgg materialCrgg);

    /**
     * 根据id批量删除
     * @param lawIds
     */
    public void deleteMaterialByIds(Collection<String> lawIds);
}
