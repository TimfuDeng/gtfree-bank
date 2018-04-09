package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.security.SecUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 报名查询
 * @author cxm
 * @version v1.0, 2017/11/17
 */
@Controller
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    TransResourceClient transResourceClient;

    @RequestMapping("index")
    public String index(@PageDefault(value=10) Pageable page,String title ,Model model) {
        int resourceEditStatus=-1;
        String regions = null;
        if (!SecUtil.isAdmin()) {
            regions = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResourcePage= transResourceClient.findTransResourcesByEditStatus(title,resourceEditStatus,null,regions,page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("title", title);
        return "apply/apply-resource-list";
    }
}
