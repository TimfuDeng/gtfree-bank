package cn.gtmap.landsale.client.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.client.register.TransCrggClient;
import cn.gtmap.landsale.client.register.TransFileClient;
import cn.gtmap.landsale.client.register.TransResourceClient;
import cn.gtmap.landsale.common.model.TransCrgg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.unbescape.html.HtmlEscape;

import java.util.Date;

/**
 * 通知公告
 * @author lq
 * @version v1.0, 2017/12/18
 */
@Controller
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransFileClient transFileClient;

    @RequestMapping(value = "/index")
    public String index() {
        return "notice/notice-index";
    }

    @RequestMapping(value = "/getCrggList")
    public String getCrggList(@PageDefault(value = 12) Pageable pageable, String ggNum, String afficheType, Date ggBeginTime, Date ggEndTime, Model model,String regionCodes) {
        String startTime = "";
        String endTime = "";
        if(ggBeginTime != null) {
            startTime = String.valueOf(ggBeginTime.getTime());
        }
        if(ggEndTime != null) {
            endTime = String.valueOf(ggEndTime.getTime());
        }
        Page<TransCrgg> crggList = transCrggClient.searchTransCrgg(ggNum,afficheType,startTime,endTime,regionCodes,pageable);
        model.addAttribute("transCrggList", crggList);
        return "notice/notice-list";
    }

    @RequestMapping("/view/crgg")
    public String viewCrgg(@RequestParam(value = "id", required = true)String id, Model model){
        String content = "";
        if(StringUtils.isNotBlank(id)) {
            content = transCrggClient.getTransCrgg(id).getGgContent();
        }

        model.addAttribute("content", HtmlEscape.unescapeHtml(content));
        model.addAttribute("crggAttachments", transFileClient.getTransFileAttachments(id));
        return "notice/crgg";
    }

}
