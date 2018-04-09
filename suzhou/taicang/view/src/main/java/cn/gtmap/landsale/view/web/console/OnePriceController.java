package cn.gtmap.landsale.view.web.console;


import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.OnePriceLog;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.HTMLSpirit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.net.URLDecoder;
import java.util.List;


/**
 * Created by trr on 2016/8/2.
 */
@Controller
@RequestMapping("oneprice")
public class OnePriceController extends BaseController{
    private Logger log= LoggerFactory.getLogger(OnePriceController.class);
    @Autowired
    OnePriceLogService onePriceLogService;

    /**
     * @作者 王建明
     * @创建日期 2017/2/23 0023
     * @创建时间 上午 11:21
     * @描述 —— 一次报价列表
     */
    @RequestMapping("/offer/list.f")
    public String offer_list(@RequestParam(value = "resourceId",required = true)String resourceId,Model model) throws Exception{
        if(StringUtils.isNotBlank(resourceId)){
            try {
                resourceId = HTMLSpirit.delHTMLTag(URLDecoder.decode(resourceId, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<OnePriceLog> onePriceLogList = onePriceLogService.findOnePriceLogList(resourceId);
        model.addAttribute("onePriceLogList", onePriceLogList);
        model.addAttribute("resourceId", resourceId);
        return "oneprice/view-offer-list";
    }
}
