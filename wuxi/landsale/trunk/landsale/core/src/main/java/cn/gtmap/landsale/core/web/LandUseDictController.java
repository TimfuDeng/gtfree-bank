package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.LandUseDict;
import cn.gtmap.landsale.core.service.LandUseDictSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author M150237 on 2017-11-23.
 */
@RestController
@RequestMapping("/floorPrice")
public class LandUseDictController {
    @Autowired
    LandUseDictSerivce landUseDictSerivce;

    @RequestMapping("/getLandUseDict")
    public LandUseDict getLandUseDict(@RequestParam(value = "code") String code) {
        return landUseDictSerivce.getLandUseDict(code);
    }


    @RequestMapping("/getLandUseDictList")
    public List<LandUseDict> getLandUseDictList() {
        return landUseDictSerivce.getLandUseDictList();
    }

}
