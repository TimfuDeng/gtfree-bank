package cn.gtmap.landsale.admin.register;


import cn.gtmap.landsale.common.model.MaterialCrgg;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 出让公告 材料 关系服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@FeignClient(name = "core-server")
public interface TransCrggMaterialClient {

    /**
     * 根据公告id获取关联对象集合
     * @param crggId
     * @return
     */
    @RequestMapping(value = "/crgg/material/getMaterialCrggByCrggId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<MaterialCrgg> getMaterialCrggByCrggId(@RequestParam("crggId") String crggId);

    /**
     * 根据材料id获取关联对象集合
     * @param materialId
     * @return
     */
    @RequestMapping(value = "/crgg/material/getMaterialByMaterialId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    MaterialCrgg getMaterialByMaterialId(@RequestParam(value = "materialId") String materialId);

    /**
     * 根据主键获得关联对象
     * @param tmcId
     * @return
     */
    @RequestMapping(value = "/crgg/material/getMaterialById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    MaterialCrgg getMaterialById(@RequestParam(value = "tmcId") String tmcId);

}
