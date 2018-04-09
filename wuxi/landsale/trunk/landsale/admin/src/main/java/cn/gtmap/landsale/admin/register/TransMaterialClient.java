package cn.gtmap.landsale.admin.register;


import cn.gtmap.landsale.common.model.TransMaterial;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 材料服务
 * @author zsj
 * @version v1.0, 2017/10/18
 */
@FeignClient(name = "core-server")
public interface TransMaterialClient {

    /**
     * 根据行政区获取所需材料
     * @param regionCode
     * @return
     */
    @RequestMapping(value = "/material/getMaterialsByRegionCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransMaterial> getMaterialsByRegionCode(@RequestParam("regionCode") String regionCode);

    /**
     * 根据行政区 材料类型获取所需材料
     * @author zsj
     * @param materialType 文件类型
     * @param regionCode 行政区代码
     * @return 材料列表
     * @version v1.0, 2017/10/23
     */
    @RequestMapping(value = "/material/getMaterialsBymaterialType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransMaterial> getMaterialsBymaterialType(@RequestParam("regionCode") String  regionCode, @RequestParam("materialType") String materialType);

    /**
     * 根据Id获取材料
     * @param materialId
     * @return
     */
    @RequestMapping(value = "/material/getMaterialsById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransMaterial getMaterialsById(@RequestParam(value = "materialId") String materialId);

    /**
     * 保存材料
     * @param transMaterial 材料对象
     * @return TransMaterial
     */
    @RequestMapping(value = "/material/saveTransMaterial", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransMaterial saveTransMaterial(@RequestBody TransMaterial transMaterial);

}
