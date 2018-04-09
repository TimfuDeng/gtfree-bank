package cn.gtmap.landsale.common.register;

import cn.gtmap.landsale.common.model.LandUseDict;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author M150237 on 2017-11-23.
 */
@FeignClient(name = "core-server")
public interface LandUseDictClient {

    /**
     * 获取字典表数据对象
     * @return
     */
    @RequestMapping(value = "/floorPrice/getLandUseDict", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    LandUseDict getLandUseDict(@RequestParam(value = "code") String code);

    /**
     * 获取土地用途字典表数据
     * @return
     */
    @RequestMapping(value = "/floorPrice/getLandUseDictList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<LandUseDict> getLandUseDictList();


}
