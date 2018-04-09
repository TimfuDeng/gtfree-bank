package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransUser;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransUserService;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * 保证金查询管理
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/15
 */
@Controller
@RequestMapping(value = "console/bail")
public class BailController {

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransUserService transUserService;
    /**
     * 地块保证金缴纳情况查询列表
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/resource/list")
    public String resourceList(@PageDefault(value=10) Pageable page,String caThumbprint,String caName,Model model) {
        String userId = "-99";
        if(StringUtils.isNotBlank(caThumbprint)){
            TransUser transUser = transUserService.getTransUserByCAThumbprint(caThumbprint);
           if(transUser!=null)
               userId=transUser.getUserId();
        }
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResourcePage= transResourceService.findTransResourcesByUser(userId, Constants.ResourceEditStatusRelease,regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("caThumbprint", caThumbprint);
        model.addAttribute("caName", caName);
        return "bail-resource-list";
    }

    @RequestMapping("/resource/bail")
    @AuditServiceLog(category = Constants.LogCategory.DATA_VIEW,producer = Constants.LogProducer.ADMIN,
            description = "查看保证金")
    public String resourceBail(String resourceId,String caThumbprint,Model model) {
        if(StringUtils.isNotBlank(caThumbprint)){
            TransUser transUser = transUserService.getTransUserByCAThumbprint(caThumbprint);
            if(transUser!=null)
                model.addAttribute("resourceApply", transResourceApplyService.getTransResourceApplyByUserId(transUser.getUserId(),resourceId));
        }
        model.addAttribute("resourceId", resourceId);
        return "resource-bail";
    }
}
