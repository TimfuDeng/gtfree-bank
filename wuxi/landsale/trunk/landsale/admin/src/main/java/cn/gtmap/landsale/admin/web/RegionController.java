package cn.gtmap.landsale.admin.web;

import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.service.TransRegionService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 行政区管理
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Controller
@RequestMapping("/region")
public class RegionController {

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransRegionService transRegionService;

    @RequestMapping("/index")
    public String index() {
        return "region/index";
    }

    @RequestMapping("/getRegionTree")
    @ResponseBody
    public List<Tree> getRegionTree(String regionCode) {
        return transRegionClient.getRegionTree(regionCode);
    }

    @RequestMapping("/saveRegion")
    @ResponseBody
    public ResponseMessage saveRegion(TransRegion transRegion) {
        return transRegionClient.saveTransRegion(transRegion);
    }

    @RequestMapping("/updateRegion")
    @ResponseBody
    public ResponseMessage updateRegion(TransRegion transRegion) {
        return transRegionClient.updateTransRegion(transRegion);
    }

    @RequestMapping("/deleteAddRegion")
    @ResponseBody
    public ResponseMessage deleteAddRegion(TransRegion transRegion, String oldRegionCode) {
        return transRegionService.deleteAddRegion(transRegion, oldRegionCode);
    }


    @RequestMapping("/checkRegionCode")
    @ResponseBody
    public ResponseMessage checkRegionCode(String regionCode) {
        TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(regionCode);
        if (transRegion != null) {
            return new ResponseMessage(false, "数据库中已存在相同行政区代码");
        }
        return new ResponseMessage(true, null);
    }

    @RequestMapping("/deleteRegion")
    @ResponseBody
    public ResponseMessage deleteRegion(TransRegion transRegion) {
        return transRegionClient.deleteTransRegion(transRegion);
    }

}

