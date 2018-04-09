package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.*;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.admin.register.*;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "oneprice")
public class OnePriceController extends BaseController {

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    OneTargetClient oneTargetClient;

    @Autowired
    OnePriceLogClient onePriceLogClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    OneParamClient oneParamClient;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("resource/list")
    public String resourceList(@PageDefault(value=10) Pageable page, String title, Model model) {
        Page<TransResource> transResourceList = transResourceClient.findYCBJResourcesByEditStatus(title, -1, null, null, page);
        model.addAttribute("transResourceList", transResourceList);
        if(title != null && title != "") {
            model.addAttribute("title", title);
        }
        return "oneprice/resource-list";
    }


    @RequestMapping("/toStopDate")
    public String toStopDate(String resourceId, Model model) {
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(resourceId);
        model.addAttribute("transResource", transResource);
        model.addAttribute("oneTarget", oneTarget);
        return "oneprice/resource-stop-date";
    }

    /**
     * 编辑页面
     * @param resourceId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("resource/edit")
    public String resourceEdit(String resourceId,Model model) throws Exception{
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(resourceId);
        model.addAttribute("transResource",transResource);
        model.addAttribute("transResourceId",transResource.getResourceId());
        model.addAttribute("transTargetName",transResource.getResourceCode());
        model.addAttribute("transTargetNo",transResource.getResourceCode());
        model.addAttribute("oneTarget",oneTarget);
        return "oneprice/resource-edit";
    }

    /**
     * 保存标的，将原来的标的复制一份到一次报价标的，用于发布
     * @param oneTarget
     * @param model
     * @param ra
     * @return
     * @throws Exception
     */
    @RequestMapping("resource/save")
    @ResponseBody
    public Map resourceSave(OneTarget oneTarget,Model model,RedirectAttributes ra) throws Exception{
        OneTarget target =  oneTargetClient.getOneTargetByTransResource(oneTarget.getTransResourceId());
        TransResource transResource = transResourceClient.getTransResource(oneTarget.getTransResourceId());
        Pageable request = new PageRequest(0,15);
//        SecUtil.getre
        if (null!=target){//更新
            target.setPriceMin(oneTarget.getPriceMin());
            target.setPriceMax(oneTarget.getPriceMax());
            target.setPriceGuid(oneTarget.getPriceGuid());
            oneTarget=target;
        }else {//保存
            oneTarget.setIsStop(0);
        }
        ResponseMessage<OneTarget> newOneTarget = oneTargetClient.saveOneTarget(oneTarget);
        model.addAttribute("transResourceId",oneTarget.getTransResourceId());
        model.addAttribute("transTargetName",transResource.getResourceCode());
        model.addAttribute("oneTarget",newOneTarget.getEmpty());
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        Map map = new HashMap();
        map.put("flag",true);
        map.put("id",transResource.getResourceId());
        return map;
//        return "/oneprice/resource/edit?id="+transResource.getResourceId();
    }

    private OneTarget getOneTargetValue(OneTarget oneTarget,Date stopDate,OneParam oneParam){
        if(null!=stopDate){
            oneTarget.setStopDate(stopDate);//发布时间
            oneTarget.setWaitBeginDate(stopDate);//等待开始时间
            oneTarget.setWaitEndDate(DateUtils.addMinutes(oneTarget.getWaitBeginDate(), oneParam.getWaitTime()));//等待结束时间
            oneTarget.setQueryBeginDate(oneTarget.getWaitEndDate());//询问开始时间
            oneTarget.setQueryEndDate(DateUtils.addMinutes(oneTarget.getQueryBeginDate(), oneParam.getQueryTime()));//询问结束时间
            oneTarget.setPriceBeginDate(oneTarget.getQueryEndDate());//报价开始时间
            oneTarget.setPriceEndDate(DateUtils.addMinutes(oneTarget.getQueryEndDate(),oneParam.getPriceTime()));//报价结束时间

        }
        oneTarget.setCreateDate(new Date());
        oneTarget.setCreateUserId(SecUtil.getLoginUserId());
        return  oneTarget;
    }

    /**
     * 发布地块
     * @param transResourceId
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "/resource/status/change",method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage changeStatus(String transResourceId, int status, Date stopDate, Model model) {
        OneTarget oneTarget=null;
        Pageable request = new PageRequest(0,15);
        OneParam oneParam = oneParamClient.findOneParam(request).getFirst();

        if (StringUtils.isNotBlank(transResourceId)) {
            //判断 地块是否已最高限价 TODO
            TransResource transResource = transResourceClient.getTransResource(transResourceId);
//            if (Constants.RESOURCE_STATUS_MAX_OFFER != transResource.getResourceStatus()){
//                return new ResponseMessage(false, "该地块未达到最该限价！");
//            }
            if (Constants.Whether.YES.equals(status)) {
                if (stopDate == null) {
                    return new ResponseMessage(false, "发布时间必须选择！");
                }
                // 判断 发布时间 < 当前时间
                if (stopDate.before(new Date())) {
                    return new ResponseMessage(false, "发布时间不能小于当前时间！");
                }
                // 判断 发布时间 < 限时开始时间
                if (stopDate.before(transResource.getXsBeginTime())) {
                    return new ResponseMessage(false, "发布时间不能小于限时竞价开始时间！");
                }
            }
            oneTarget = oneTargetClient.getOneTargetByTransResource(transResourceId);
            if(null == oneTarget){
                return new ResponseMessage(false,"请设置报价信息！");
            }
            oneTarget = getOneTargetValue(oneTarget, stopDate, oneParam);
            oneTarget.setIsStop(status);
            oneTargetClient.saveOneTarget(oneTarget);
            return new ResponseMessage(true);
        }
        return new ResponseMessage(false, "请设置报价信息！");
    }

    @RequestMapping("/resource/status/view")
    public String status(String transResourceId,Model model) {
        OneTarget oneTarget=null;
        TransResource transResource = null;
        if (StringUtils.isNotBlank(transResourceId)) {
            oneTarget = oneTargetClient.getOneTargetByTransResource(transResourceId);
            transResource=transResourceClient.getTransResource(transResourceId);
        }
        model.addAttribute("resource", transResource);
        model.addAttribute("oneTarget", oneTarget);
        return "oneprice/resource-status";
    }

    /**
     * 竞买人一次报价的列表，页面按照报价时间进行排序
     * @param resourceId
     * @param model
     * @return
     */
    @RequestMapping("resource/info")
    public String resourceInfo(String resourceId,Model model){
        Date nowDate=new Date();
        OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(resourceId);
        List<OnePriceLog> onePriceLogList =  onePriceLogClient.findOnePriceLogList(resourceId);
        long totalPrice=0L;
        double avgPrice=0.00;
        if (onePriceLogList.size()>0){
            for(int i=0;i<onePriceLogList.size(); i++){
                totalPrice = totalPrice + onePriceLogList.get(i).getPrice();
            }
            avgPrice=(double)totalPrice/(onePriceLogList.size());
        }
        //四舍五入，保留2位小数
        BigDecimal bigDecimal=new BigDecimal(avgPrice);
        avgPrice=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("avgPrice",avgPrice);
        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("oneTarget",oneTarget);
        model.addAttribute("nowDate",nowDate);
        return "oneprice/resourceoffer-list";
    }

    /**
     * 竞买人一次报价保存到execl中，按照差值进行排序
     * @param id
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("offerlist-excel")
    public String offerList(String id, HttpServletResponse response, Model model) {

        try {
            OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(id);
            List<OnePriceLog> onePriceLogList = onePriceLogClient.findOnePriceLogListOrderByBj(id);
            long totalPrice=0L;
            double avgPrice=0.00;
            if (onePriceLogList.size()>0){
                for(int i=0;i<onePriceLogList.size(); i++){
                    totalPrice = totalPrice + onePriceLogList.get(i).getPrice();
                }
                avgPrice=(double)totalPrice/(onePriceLogList.size());
            }

//            OnePriceLog temp=null;
//            for (int i=0;i<onePriceLogList.size()-1;i++){
//                for (int j=0;j<onePriceLogList.size()-i-1;j++){
//                    if ((onePriceLogList.get(j).getPrice()-avgPrice)<(onePriceLogList.get(j+1).getPrice()-avgPrice)){
//                        temp=onePriceLogList.get(j);
//                        onePriceLogList.set(j,onePriceLogList.get(j+1));
//                        onePriceLogList.set(j+1,temp);
//                    }
//                }
//            }

            //四舍五入，保留2位小数
            BigDecimal bigDecimal=new BigDecimal(avgPrice);
            avgPrice=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("avgPrice",avgPrice);
            model.addAttribute("onePriceLogList",onePriceLogList);
            model.addAttribute("oneTarget",oneTarget);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+new String(oneTarget.getTransName().getBytes("gb2312"),"ISO8859-1")+".xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/oneprice/resourceoffer-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置成交信息
     * @param resourceId
     * @param model
     * @return
     */
    @RequestMapping("resource/success/edit")
    public String resourceSuccessEdit(String resourceId, Model model){
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(resourceId);
        //报价列表
        Date nowDate=new Date();
        List<OnePriceLog> onePriceLogList =  onePriceLogClient.findOnePriceLogList(resourceId);
        List<TransResourceOffer> transResourceOfferList = transResourceOfferClient.getOfferListByResource(resourceId);
        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("nowDate",nowDate);
        model.addAttribute("transResourceOfferList",transResourceOfferList);
        model.addAttribute("transResource",transResource);
        model.addAttribute("transResourceId",transResource.getResourceId());
        model.addAttribute("transTargetName",transResource.getResourceCode());
        model.addAttribute("oneTarget",oneTarget);
        return "oneprice/resource-success";
    }

    @RequestMapping("resource/success/save")
    @ResponseBody
    public ResponseMessage resourceSuccessSave(OneTarget oneTarget,String offerType,String logId,String offerId){
        if(StringUtils.isNotBlank(offerType)){
            if(StringUtils.isBlank(logId) && StringUtils.isBlank(offerId)){
                return new ResponseMessage(false,"保存失败!");
            }
        }else {
            return new ResponseMessage(false,"保存失败!");
        }
        ResponseMessage<OneTarget> oneTarget1= oneTargetClient.saveOneTargetAndUpdateResource(oneTarget,offerType,logId,offerId);
        return oneTarget1;
    }

    @RequestMapping("between/list")
    public String betweenList(@PageDefault(value=10) Pageable page, Model model) throws Exception{
        if (!SecUtil.isAdmin()) {
            String regionCodes = SecUtil.getLoginUserRegionCodes();
            model.addAttribute("regionCodes", regionCodes);
        }
        Page<OneParam> oneParamList= oneParamClient.findOneParam(page);
        List<String> regionNames = new ArrayList<>();
        List<OneParam> temp = oneParamList.getItems();
        List<TransRegion> regionList = new ArrayList<>();
        for(OneParam oneParam : temp){
            String regionCode = oneParam.getRegionCode();
            if(!StringUtil.isBlank(regionCode)) {
                TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(regionCode);
                regionList.add(transRegion);
            }
        }
        model.addAttribute("regionList",regionList);
        model.addAttribute("oneParamList",oneParamList);
        return "oneprice/between-list";
    }
    @RequestMapping("between/edit")
    public String betweenEdit(String id,Model model) throws Exception{
        OneParam oneParam;
        if(StringUtil.isBlank(id)){
            oneParam = new OneParam();
            oneParam.setPriceTime(Constants.ONE_PARAM_DEFAULT_TIME.PRICE_TIME);
            oneParam.setWaitTime(Constants.ONE_PARAM_DEFAULT_TIME.WAIT_TIME);
            oneParam.setQueryTime(Constants.ONE_PARAM_DEFAULT_TIME.QUERY_TIME);
//            oneParam = oneParamClient.saveOrUpdateOneParam(oneParam).getEmpty();
        } else {
            oneParam = oneParamClient.getOneParam(id);
        }
        String regionCode = oneParam.getRegionCode();
        if(!StringUtil.isBlank(regionCode)) {
            TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(regionCode);
            model.addAttribute("transRegion",transRegion);
        }
        model.addAttribute("oneParam",oneParam);
        return "oneprice/between-edit";
    }

    @RequestMapping("between/save")
    @ResponseBody
    public ResponseMessage betweenSave(OneParam oneParam) throws Exception{
        //如返回空id,oneParam为新建，将id置为null保证新建而非Update
        if(StringUtil.isBlank(oneParam.getId())) {
            oneParam.setId(null);
        }
        //当前行政区已存在参数,修改原参数
        OneParam temp= oneParamClient.getOneParamByRegionCode(oneParam.getRegionCode());
        if(temp != null) {
           oneParam.setId(temp.getId());
        }
        ResponseMessage<OneParam> param = oneParamClient.saveOrUpdateOneParam(oneParam);
        return param;
    }

    @RequestMapping("getOneParamByRegionCode")
    @ResponseBody
    public OneParam getOneParamByRegionCode(String regionCode) {
        if(!StringUtil.isBlank(regionCode)) {
            return  oneParamClient.getOneParamByRegionCode(regionCode);
        }else {
            return  null;
        }
    }
}
