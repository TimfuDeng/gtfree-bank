package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by trr on 2016/8/4.
 */
@Controller
@RequestMapping("oneprice")
public class OnePriceController extends BaseController{
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;


    @Autowired
    OneParamService oneParamService;
    @Autowired
   TransResourceService transResourceService;
    @Autowired
    OneTargetService oneTargetService;
    @Autowired
    OnePriceLogService onePriceLogService;
    @Autowired
    TransResourceOfferService transResourceOfferService;

    @RequestMapping("resource/list")
    public String resourceList(@PageDefault(value=10) Pageable page,String title, Model model) {
        Page<TransResource> transResourceList = transResourceService.findTransResourcesByEditStatus(title, -1, null, null, page);
        model.addAttribute("transResourceList", transResourceList);
        return "oneprice/resource-list";
    }

    /**
     * 编辑页面
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("resource/edit")
    public String resourceEdit(String id,Model model) throws Exception{
        TransResource transResource = transResourceService.getTransResource(id);
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
        model.addAttribute("transResource",transResource);
        model.addAttribute("transTargetId",transResource.getResourceId());
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
    public String resourceSave(OneTarget oneTarget,Model model,RedirectAttributes ra) throws Exception{
        OneTarget target =  oneTargetService.getOneTargetByTransTarget(oneTarget.getTransTargetId());
        TransResource transResource = transResourceService.getTransResource(oneTarget.getTransTargetId());
        Pageable request = new PageRequest(0,15);
        OneParam oneParam = oneParamService.findOneParam(request).getFirst();
        if (null!=target){//更新
                target.setPriceMin(oneTarget.getPriceMin());
                target.setPriceMax(oneTarget.getPriceMax());
                target.setPriceGuid(oneTarget.getPriceGuid());
        }else {//保存
                oneTarget.setIsStop(0);
        }
        oneTarget = oneTargetService.saveOneTarget(oneTarget);
        model.addAttribute("transTargetId",oneTarget.getTransTargetId());
        model.addAttribute("transTargetName",transResource.getResourceCode());
        model.addAttribute("oneTarget",oneTarget);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/oneprice/resource/edit?id="+transResource.getResourceId();

    }

    private OneTarget getOneTargetValue(OneTarget oneTarget,Date stopDate,OneParam oneParam){
        if(null!=stopDate){
            oneTarget.setStopDate(stopDate);//中止时间
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
     * @param transTargetId
     * @param status
     * @param model
     * @return
     */
    @RequestMapping("/resource/status/change.f")
    public @ResponseBody
    Object changeStatus(String transTargetId,int status,Model model) {
        OneTarget oneTarget=null;
        Date stopDate=new Date();

        Pageable request = new PageRequest(0,15);
        OneParam oneParam = oneParamService.findOneParam(request).getFirst();

        if (StringUtils.isNotBlank(transTargetId)) {
            //判断原来的系统是否已经中止
            TransResource transResource = transResourceService.getTransResource(transTargetId);
            if (Constants.ResourceEditStatusBreak!=transResource.getResourceEditStatus()){
                return fail("请在公开出让系统里面中止该地块！");
            }
            oneTarget = oneTargetService.getOneTargetByTransTarget(transTargetId);
            if(null==oneTarget){
               return fail("请设置报价信息！");
            }
            oneTarget = getOneTargetValue(oneTarget,stopDate,oneParam);
            oneTarget.setIsStop(status);
            oneTargetService.saveOneTarget(oneTarget);
            return success();
        }
        return fail("请设置报价信息！");
    }

    @RequestMapping("/resource/status/view.f")
    public String status(String transTargetId,Model model) {
        OneTarget oneTarget=null;
        TransResource transResource = null;
        if (StringUtils.isNotBlank(transTargetId)) {
            oneTarget = oneTargetService.getOneTargetByTransTarget(transTargetId);
            transResource=transResourceService.getTransResource(transTargetId);
        }
        model.addAttribute("resource", transResource);
        model.addAttribute("oneTarget", oneTarget);
        return "oneprice/resource-status";
    }

    @RequestMapping("resource/info")
    public String resourceInfo(String id,Model model){
        Date nowDate=new Date();
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
        List<OnePriceLog> onePriceLogList =  onePriceLogService.findOnePriceLogList(id);
        long totalPrice=0L;
        double avgPrice=0.00;
        if (onePriceLogList.size()>0){
            for(int i=0;i<onePriceLogList.size(); i++){
                totalPrice = totalPrice + onePriceLogList.get(i).getPrice();
            }
            avgPrice=(double)totalPrice/(onePriceLogList.size());
        }
        //四舍五入，保留1位小数
        BigDecimal bigDecimal=new BigDecimal(avgPrice);
        avgPrice=bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("avgPrice",avgPrice);
        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("oneTarget",oneTarget);
        model.addAttribute("nowDate",nowDate);
        return "oneprice/resourceoffer-list";
    }

    @RequestMapping("resource/success/edit")
    public String resourceSuccessEdit(String id,Model model){
        TransResource transResource = transResourceService.getTransResource(id);
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
        //报价列表
        Date nowDate=new Date();
        List<OnePriceLog> onePriceLogList =  onePriceLogService.findOnePriceLogList(id);
        List<TransResourceOffer> transResourceOfferList = transResourceOfferService.getOfferListByResource(id);
        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("nowDate",nowDate);
        model.addAttribute("transResourceOfferList",transResourceOfferList);
        model.addAttribute("transResource",transResource);
        model.addAttribute("transTargetId",transResource.getResourceId());
        model.addAttribute("transTargetName",transResource.getResourceCode());
        model.addAttribute("oneTarget",oneTarget);
        return "oneprice/resource-success";
    }


    @RequestMapping("resource/success/save")
    public String resourceSuccessSave(OneTarget oneTarget,Model mode,RedirectAttributes ra){
        try {
            oneTarget = oneTargetService.saveOneTarget(oneTarget);
            OnePriceLog onePriceLog=onePriceLogService.findOnePriceLogListByTransTargetIdPrice(oneTarget.getTransTargetId(),oneTarget.getSuccessPrice());

            //成交数据同步到出让系统

            TransResource transResource = transResourceService.getTransResource(oneTarget.getTransTargetId());
            //1.将竞得的一次报价数据转化成出让系统里面的报价数据，对于无法转化的数据（如：号牌）作-1处理
            // 2.设置地块成交
            //3.在交易系统里面消除转化成出让系统报价数据的影响,在出让系统里面设置
            TransResourceOffer transResourceOffer = new TransResourceOffer();
            transResourceOffer.setOfferType(-1);
            transResourceOffer.setOfferPrice((double)onePriceLog.getPrice());
            transResourceOffer.setOfferTime(onePriceLog.getPriceDate().getTime());
            transResourceOffer.setResourceId(onePriceLog.getTransTargetId());
            transResourceOffer.setUserId(onePriceLog.getTransUserId());
            transResourceOffer = transResourceOfferService.addTransResourceOffer(transResourceOffer);

            //得到成交人竞买资格
            transResource.setOfferId(transResourceOffer.getOfferId());
            transResource.setOverTime(onePriceLog.getPriceDate());
            transResource.setResourceEditStatus(Constants.ResourceEditStatusOver);
            transResource=transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusChengJiao);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            ra.addFlashAttribute("_result", false);
            ra.addFlashAttribute("_msg", "保存失败！");

        }
        return "redirect:/oneprice/resource/success/edit?id="+oneTarget.getTransTargetId();
    }


    @RequestMapping("between/list")
    public String betweenList(@PageDefault(value=10) Pageable page, Model model) throws Exception{
        Page<OneParam> oneParamList= oneParamService.findOneParam(page);
        model.addAttribute("oneParamList",oneParamList);
        return "oneprice/between-list";
    }
    @RequestMapping("between/edit")
    public String betweenEdit(String id,Model model,RedirectAttributes ra) throws Exception{
        OneParam oneParam;
        if(StringUtil.isBlank(id)){
            oneParam = new OneParam();
            oneParam.setPriceTime(4);
            oneParam.setWaitTime(4);
            oneParam.setQueryTime(4);
            oneParamService.saveOrUpdateOneParam(oneParam);
        } else {
            oneParam = oneParamService.getOneParam(id);
        }
        model.addAttribute("oneParam",oneParam);
        return "oneprice/between-edit";
    }

    @RequestMapping("between/save")
    public String betweenSave(OneParam oneParam,Model model,RedirectAttributes ra) throws Exception{
        OneParam param = oneParamService.saveOrUpdateOneParam(oneParam);
        model.addAttribute("oneParam",param);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/oneprice/between/edit?id="+param.getId();
    }
    @RequestMapping("offerlist-excel.f")
    public String OfferList(String id,HttpServletResponse response,Model model) {

        try {
            OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
            List<OnePriceLog> onePriceLogList =  onePriceLogService.findOnePriceLogList(id);
            long totalPrice=0L;
            double avgPrice=0.00;
            if (onePriceLogList.size()>0){
                for(int i=0;i<onePriceLogList.size(); i++){
                    totalPrice = totalPrice + onePriceLogList.get(i).getPrice();
                }
                avgPrice=(double)totalPrice/(onePriceLogList.size());
            }
            //四舍五入，保留1位小数
            BigDecimal bigDecimal=new BigDecimal(avgPrice);
            avgPrice=bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("avgPrice",avgPrice);
            model.addAttribute("onePriceLogList",onePriceLogList);
            model.addAttribute("oneTarget",oneTarget);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+new String(oneTarget.getTransName().getBytes("gb2312"),"ISO8859-1")+".xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/oneprice/resourceoffer-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
