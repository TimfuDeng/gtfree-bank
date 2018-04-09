package cn.gtmap.landsale.client.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.client.register.TransFileClient;
import cn.gtmap.landsale.client.register.TransRegionClient;
import cn.gtmap.landsale.common.model.TransFile;
import cn.gtmap.landsale.common.model.TransRegion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

/**
 * 客户端资料下载
 * @author cxm
 * @version v1.0, 2017/12/08
 */
@Controller
@RequestMapping("/material")
public class MaterialCenterController {

    @Autowired
    TransFileClient transFileClient;

    @Autowired
    TransRegionClient transRegionClient;

    @RequestMapping("/index")
    public String index(Model model) {
        return "/material/material-index";
    }

    @RequestMapping("/getFileList")
    public String getFileList(String regionCodes, Model model, @PageDefault(value = 10) Pageable pageable) {
        Page<TransFile> fileList = transFileClient.getTransFileByFileKeysAndRegionCode("ziliao",regionCodes,pageable);
        model.addAttribute("materials",fileList);
        return "/material/fileList";
    }

    @RequestMapping("/help")
    public String help() {
        return "/help";
    }

    @RequestMapping("/helpContent")
    public String helpContent(String regionCodes,Model model) {
        String regionName = "";
        if(StringUtils.isNotBlank(regionCodes)) {
           TransRegion region =  transRegionClient.getTransRegionByRegionCode(regionCodes);
           if(region!=null) {
               regionName = region.getRegionName();
           }
        } else {
            regionCodes = "321100";
            TransRegion region =  transRegionClient.getTransRegionByRegionCode(regionCodes);
            if(region!=null) {
                regionName = region.getRegionName();
            }
        }
        model.addAttribute("regionCode",regionCodes);
        model.addAttribute("regionName",regionName);
        return "/help-content";
    }
}
