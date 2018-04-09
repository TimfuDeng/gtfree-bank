package cn.gtmap.landsale.core.web;


import cn.gtmap.landsale.common.model.MaterialCrgg;
import cn.gtmap.landsale.core.service.MaterialCrggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;


/**
 * 出让公告 材料 关系服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@RestController
@RequestMapping("/crgg/material")
public class CrggMaterialController {

    @Autowired
    MaterialCrggService materialCrggService;

    /**
     * 保存材料公告关联对象
     * @param materialCrgg
     */
    @RequestMapping("/saveMaterialCrgg")
    public MaterialCrgg saveMaterialCrgg(MaterialCrgg materialCrgg) {
        return materialCrggService.saveMaterialCrgg(materialCrgg);
    }

    /**
     * 根据公告id获取关联对象集合
     * @param crggId
     * @return
     */
    @RequestMapping("/getMaterialCrggByCrggId")
    public List<MaterialCrgg> getMaterialCrggByCrggId(@RequestParam("crggId") String crggId) {
        return materialCrggService.getMaterialCrggByCrggId(crggId);
    }

    /**
     * 根据材料id获取关联对象集合
     * @param materialId
     * @return
     */
    @RequestMapping("/getMaterialByMaterialId")
    public MaterialCrgg getMaterialByMaterialId(@RequestParam(value = "materialId") String materialId) {
        return materialCrggService.getMaterialByMaterialId(materialId);
    }

    /**
     * 根据主键获得关联对象
     * @param tmcId
     * @return
     */
    @RequestMapping("/getMaterialById")
    public MaterialCrgg getMaterialById(@RequestParam(value = "tmcId") String tmcId) {
        return materialCrggService.getMaterialById(tmcId);
    }

    /**
     * 删除同一公告id下所有的MaterialCrgg对象
     * @param crggId
     */
    @RequestMapping("/deleteMaterialCrggList")
    public void deleteMaterialCrggList(@RequestParam(value = "crggId") String crggId) {
        materialCrggService.deleteMaterialCrggList(crggId);
    }

    /**
     * 删除单个实体
     * @param materialCrgg
     */
    @RequestMapping("/deleteMaterialCrgg")
    public void deleteMaterialCrgg(@RequestBody MaterialCrgg materialCrgg) {
        materialCrggService.deleteMaterialCrgg(materialCrgg);
    }

    /**
     * 根据id批量删除
     * @param lawIds
     */
    @RequestMapping("/deleteMaterialByIds")
    public void deleteMaterialByIds(@RequestBody Collection<String> lawIds) {
        materialCrggService.deleteMaterialByIds(lawIds);
    }
}
