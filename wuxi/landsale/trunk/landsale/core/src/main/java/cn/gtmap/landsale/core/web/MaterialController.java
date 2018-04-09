package cn.gtmap.landsale.core.web;


import cn.gtmap.landsale.common.model.TransMaterial;
import cn.gtmap.landsale.core.service.TransMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 材料服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    TransMaterialService transMaterialService;

    /**
     * 根据行政区获取所需材料
     * @param regionCode
     * @return
     */
    @RequestMapping("/getMaterialsByRegionCode")
    public List<TransMaterial> getMaterialsByRegionCode(@RequestParam("regionCode") String regionCode) {
        return transMaterialService.getMaterialsByRegionCode(regionCode);
    }

    /**
     * 根据行政区 材料类型获取所需材料
     * @author zsj
     * @version v1.0, 2017/10/23
     */
    @RequestMapping("/getMaterialsBymaterialType")
    public List<TransMaterial> getMaterialsBymaterialType(@RequestParam("regionCode") String  regionCode, @RequestParam("materialType") String materialType) {
        return transMaterialService.getMaterialsBymaterialType(regionCode, materialType);
    }

    /**
     * 根据Id获取材料
     * @param materialId
     * @return
     */
    @RequestMapping("/getMaterialsById")
    public TransMaterial getMaterialsById(@RequestParam(value = "materialId") String materialId) {
        return transMaterialService.getMaterialsById(materialId);
    }

    /**
     * 保存材料
     * @param transMaterial 材料对象
     * @return TransMaterial
     */
    @RequestMapping("/saveTransMaterial")
    public TransMaterial saveTransMaterial(TransMaterial transMaterial) {
        return transMaterialService.saveTransMaterial(transMaterial);
    }

}
