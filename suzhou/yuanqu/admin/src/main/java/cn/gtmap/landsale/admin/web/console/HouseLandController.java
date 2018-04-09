package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.ExUtils;
import cn.gtmap.landsale.admin.service.landService;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceInfo;
import cn.gtmap.landsale.service.TransResourceInfoService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.util.DateUtil;
import cn.gtmap.landsale.util.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import freemarker.template.utility.StringUtil;
import org.dom4j.Element;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by trr on 2015/11/16.
 * 房地一张图
 */
@Controller
public class HouseLandController {

    @Autowired
    landService landService;

    @Autowired
    private TransResourceService transResourceService;
    @Autowired
    private TransResourceInfoService transResourceInfoService;

    @RequestMapping("console/houseLand/importHouseLand.f")
    @ResponseBody
    public String importHouseLand(String resourceCode) throws Exception{
        //发送请求得到地块的部分信息，然后返回到页面
        String result= landService.getLand(resourceCode);

        return result;
    }
    @RequestMapping("/report/crgg.f")
    @ResponseBody
    public void  reportCrgg(
            @RequestParam(value = "resourceCode",required = true)String resourceCode,HttpServletResponse res){
        res.setContentType("text/html;charset=UTF-8");
        try {
            List crggList = landService.crggMapList(resourceCode);
            PrintWriter print=res.getWriter();
            print.write(JSON.toJSONString(crggList));
            print.flush();
            print.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return;
    }

    @RequestMapping("/report/crggOffer.f")
    @ResponseBody
    public void  reportCrggOffer(
            @RequestParam(value = "resourceCode",required = true)String resourceCode,HttpServletResponse res){
        res.setContentType("text/html;charset=UTF-8");
        try {
            List crggList = landService.crggOfferMapList(resourceCode);
            PrintWriter print=res.getWriter();
            print.write(JSON.toJSONString(crggList));
            print.flush();
            print.close();
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            if(null!=res){

            }
        }
        return;
    }

}
