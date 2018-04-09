package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransResourceSonService;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by trr on 2016/1/29.
 */
@Controller
public class CountController extends BaseController {
    @Autowired
    TransResourceService transResourceService;
    @Autowired
    TransResourceSonService transResourceSonService;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;


    /**
     * 参照地块查询
     * @param resourceCode
     * @param resourceLocation
     * @param resourcePurpose
     * @param beginTime
     * @param endTime
     * @param model
     * @param page
     * @param zdCode
     * @return
     */
    @RequestMapping("/console/count/list")
    public String countResearch(String resourceCode,String resourceLocation, String resourcePurpose,String beginTime,String endTime,Model model,@PageDefault(value = 10)Pageable page,String zdCode){
        Page<TransResource> transResourceList=null;
        if(StringUtils.isBlank(zdCode)){
            transResourceList = transResourceService.findDealResourceByCondiction(resourceCode,resourceLocation,resourcePurpose,beginTime,endTime,page);
            List<TransResource> list = transResourceList.getItems();
            for(TransResource transResource:list){
                List lists = transResourceSonService.getTransResourceSonList(transResource.getResourceId());
                transResource.setTransResourceSon(lists);
            }
        }else {

            transResourceList=transResourceService.findDealResourceByCondiction2ResourceIds(resourceCode,resourceLocation,resourcePurpose,beginTime,endTime,page,zdCode);
            List<TransResource> list = transResourceList.getItems();
            for(TransResource transResource:list){
                List lists = transResourceSonService.getTransResourceSonList(transResource.getResourceId());
                transResource.setTransResourceSon(lists);
            }
        }


        model.addAttribute("transResourceList", transResourceList);
        if (beginTime!=null) model.addAttribute("beginTime", beginTime);
        if (endTime!=null) model.addAttribute("endTime", endTime);
        if (resourceCode!=null) model.addAttribute("resourceCode", resourceCode);
        if (resourceLocation!=null) model.addAttribute("resourceLocation", resourceLocation);
        if (resourcePurpose!=null) model.addAttribute("resourcePurpose", resourcePurpose);
        if (zdCode!=null) model.addAttribute("zdCode", zdCode);
        return "count/count-list";
    }



    @RequestMapping("/console/count/excel.f")
    public String importCountResarch(String resourceCode,String resourceLocation, String resourcePurpose,String beginTime,String endTime,Model model,HttpServletResponse response,@PageDefault(value = 10)Pageable page,String zdCode){
//        Page<TransResource> transResourceList=null;
        //客户要求一次性导出所有的记录到excel，这里不适用分页后的数据
        List<TransResource> transResourceList = null;
        if(StringUtils.isBlank(zdCode)){
            transResourceList = transResourceService.findDealResourceByCondiction2List(resourceCode,resourceLocation,resourcePurpose,beginTime,endTime,page);
//            List<TransResource> list = transResourceList.getItems();
            for(TransResource transResource:transResourceList){
                List lists = transResourceSonService.getTransResourceSonList(transResource.getResourceId());
                transResource.setTransResourceSon(lists);
            }
        }else {

            transResourceList=transResourceService.findDealResourceByCondiction2ResourceIds2List(resourceCode,resourceLocation,resourcePurpose,beginTime,endTime,page,zdCode);
//            List<TransResource> list = transResourceList.getItems();
            for(TransResource transResource:transResourceList){
                List lists = transResourceSonService.getTransResourceSonList(transResource.getResourceId());
                transResource.setTransResourceSon(lists);
            }
        }


        model.addAttribute("transResourceList", transResourceList);
        if (beginTime!=null) model.addAttribute("beginTime", beginTime);
        if (endTime!=null) model.addAttribute("endTime", endTime);
        if (resourceCode!=null) model.addAttribute("resourceCode", resourceCode);
        if (resourceLocation!=null) model.addAttribute("resourceLocation", resourceLocation);
        if (resourcePurpose!=null) model.addAttribute("resourcePurpose", resourcePurpose);
        if (zdCode!=null) model.addAttribute("zdCode", zdCode);
        //return "count/count-list";


        try {

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+new Date()+".xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/count/count-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
