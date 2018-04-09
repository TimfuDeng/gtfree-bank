package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneParam;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OneParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "oneParam")
public class OneParamController {

    @Autowired
    OneParamService oneParamService;

    @RequestMapping(value = "/saveOrUpdateOneParam")
    ResponseMessage<OneParam> saveOrUpdateOneParam(@RequestBody OneParam oneParam){
        return oneParamService.saveOrUpdateOneParam(oneParam);
    }

    @RequestMapping(value = "/getOneParam")
    OneParam getOneParam(@RequestParam(value = "id") String id){
        return oneParamService.getOneParam(id);
    }

    @RequestMapping(value = "/findOneParam")
    Page<OneParam> findOneParam(@RequestBody Pageable request){
        return oneParamService.findOneParam(request);
    }

    @RequestMapping(value = "/getOneParamByRegionCode")
    OneParam getOneParamByRegionCode(@RequestParam(value = "regionCode") String regionCode){
        return oneParamService.getOneParamByRegionCode(regionCode);
    }
}
