package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.admin.service.CrggService;
import cn.gtmap.landsale.core.RegionAllList;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransCrggService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 江苏土地市场网公告导入管理
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/5/6
 */
@Controller
@RequestMapping(value = "console/landMarket")
public class LandMarketController {
    @Autowired
    CrggService crggService;

    @Autowired
    RegionAllList regionAllList;


    @Value("${region.code}")
    String xzqdm;

    @RequestMapping("crgg-list")
    public String crggJsList(@PageDefault(value=5) Pageable page,String title,Model model) throws Exception {
        StringBuilder whereClause = new StringBuilder();
       /* if(SecUtil.isAdmin())
           whereClause.append("XZQ_DM = '").append(xzqdm).append("' ");
        else{
            buildRegionCode(whereClause, Sets.newHashSet(xzqdm));
        }*/
        whereClause.append("XZQ_DM = '").append(xzqdm).append("' ");
        whereClause.append(" order by AFFICHE_DATE desc");
        //.append(" and (AFFICHE_TYPE = 0 or AFFICHE_TYPE = 1) ")
        Page<TransCrgg> transCrggList= crggService.findTransCrgg(whereClause.toString(), page);
        model.addAttribute("transCrggList", transCrggList);
        model.addAttribute("title", title);
        return "landMarket-crgg-list";
    }

    @RequestMapping("crgg-import.f")
    @ResponseBody
    public Object landMarketCrggImport(@RequestParam(value = "gyggIds", required = true)String gyggIds) throws Exception {
        if(!SecUtil.isAdmin()){
            if(SecUtil.getPermittedRegions().toArray()[0].toString().equalsIgnoreCase("-999"))
                return "-999";
        }

        crggService.getTransCrgg(Lists.newArrayList(gyggIds.split(";")));
        return gyggIds;
    }

    @RequestMapping("crgg-report.f")
    @ResponseBody
    public String landMarketCrggReport(@RequestParam(value = "ggIds", required = false) String ggIds) throws Exception {
        crggService.reportTransCrgg(Lists.newArrayList(ggIds.split(";")));
        return "true";
    }

    @RequestMapping("dealnotice-report.f")
    @ResponseBody
    public String landMarketDealNoticeReport(@RequestParam(value = "noticeIds", required = false) String noticeIds) throws Exception {
        crggService.reportDealNotice(Lists.newArrayList(noticeIds.split(";")));
        return "true";
    }


    private void buildRegionCode(StringBuilder whereClause,Collection<String> regionCodes){
        whereClause.append("XZQ_DM in (");
        Iterator regionIterator = regionCodes.iterator();
        while (regionIterator.hasNext()){
            whereClause.append("'"+regionIterator.next()+"'");
            if(regionIterator.hasNext())
                whereClause.append(",");
        }
        whereClause.append(")");
    }
}
