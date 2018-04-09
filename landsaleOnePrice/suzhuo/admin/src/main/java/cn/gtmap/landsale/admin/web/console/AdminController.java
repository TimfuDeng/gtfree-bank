package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.DateUtils;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
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
import java.util.Date;
import java.util.List;


/**
 * Created by trr on 2016/8/4.
 */
@Controller
@RequestMapping("oneprice")
public class AdminController extends BaseController{
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    TransTargetService transTargetService;
    @Autowired
    OneTargetService oneTargetService;
    @Autowired
    SysFieldChangeLogService sysFieldChangeLogService;
    @Autowired
    OnePriceLogService onePriceLogService;
    @Autowired
    TransResultLogService transResultLogService;
    @Autowired
    TransLicenseService transLicenseService;
    @Autowired
    TransBidderService transBidderService;

    @Autowired
    OneParamService oneParamService;
    @Autowired
    SysUserService sysUserService;

    @Value("${one.param.id}")
    String oneParamId;


    @RequestMapping("resource/list")
    public String resourceList(@PageDefault(value=10) Pageable page,String title,String noticeId, Model model) {
        Page<TransTarget> targetList= transTargetService.findTransTarget(title,null, page);
        model.addAttribute("targetList",targetList);
        return "admin/resource-list";
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
        TransTarget transTarget = transTargetService.getTransTarget(id);
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
        model.addAttribute("transTarget",transTarget);
        model.addAttribute("transTargetId",transTarget.getId());
        model.addAttribute("transTargetName",transTarget.getName());
        model.addAttribute("transTargetNo",transTarget.getNo());
        model.addAttribute("oneTarget",oneTarget);
        return "admin/resource-edit";
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
        TransTarget transTarget = transTargetService.getTransTarget(oneTarget.getTransTargetId());
        SysFieldChangeLog sysFieldChangeLog = sysFieldChangeLogService.getSysFieldChangeLogByRefId(oneTarget.getTransTargetId());
        Date stopDate=null;
        //出让系统中止，对一次报价系统时间进行设置
        if(null!=sysFieldChangeLog){
           stopDate=sysFieldChangeLog.getChangeDate();
        }
        OneParam oneParam = oneParamService.getOneParam(oneParamId);
        if (null!=target){//更新
                target.setPriceMin(oneTarget.getPriceMin());
                target.setPriceMax(oneTarget.getPriceMax());
                target.setPriceGuid(oneTarget.getPriceGuid());
                oneTarget = getOneTargetValue(target,stopDate,oneParam);
        }else {//保存
                oneTarget = getOneTargetValue(oneTarget,stopDate,oneParam);
                oneTarget.setIsStop(0);
        }
        oneTarget = oneTargetService.saveOneTarget(oneTarget);
        model.addAttribute("transTargetId",oneTarget.getTransTargetId());
        model.addAttribute("transTargetName",transTarget.getName());
        model.addAttribute("oneTarget",oneTarget);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/oneprice/resource/edit?id="+transTarget.getId();
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
        Date stopDate=null;
        OneParam oneParam = oneParamService.getOneParam(oneParamId);

        if (StringUtils.isNotBlank(transTargetId)) {
            //判断原来的系统是否已经中止
            TransTarget transTarget = transTargetService.getTransTarget(transTargetId);
            if (null==transTarget.getIsSuspend()||transTarget.getIsSuspend()==0){
                return fail("请在公开出让系统里面中止该地块！");
            }
            oneTarget = oneTargetService.getOneTargetByTransTarget(transTargetId);
            if(null==oneTarget){
               return fail("请设置报价信息！");
            }
            SysFieldChangeLog sysFieldChangeLog=sysFieldChangeLogService.getSysFieldChangeLogByRefId(oneTarget.getTransTargetId());
            if(null!=sysFieldChangeLog){
                stopDate = sysFieldChangeLog.getChangeDate();
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
        TransTarget target = null;
        if (StringUtils.isNotBlank(transTargetId)) {
            oneTarget = oneTargetService.getOneTargetByTransTarget(transTargetId);
            target=transTargetService.getTransTarget(transTargetId);
        }
        model.addAttribute("target", target);
        model.addAttribute("oneTarget", oneTarget);
        return "common/resource-status";
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
        //四舍五入，保留2位小数
        BigDecimal bigDecimal=new BigDecimal(avgPrice);
        avgPrice=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("avgPrice",avgPrice);
        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("oneTarget",oneTarget);
        model.addAttribute("nowDate",nowDate);
        return "admin/resourceoffer-list";
    }

    @RequestMapping("resource/success/edit")
    public String resourceSuccessEdit(String id,Model model){
        TransTarget transTarget = transTargetService.getTransTarget(id);
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
        //报价列表
        Date nowDate=new Date();
        List<OnePriceLog> onePriceLogList =  onePriceLogService.findOnePriceLogList(id);
        List<TransResultLog> transOnePriceLogList = transResultLogService.findTransOnePriceLogList(id);


        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("nowDate",nowDate);
        model.addAttribute("transOnePriceLogList",transOnePriceLogList);


        model.addAttribute("transTarget",transTarget);
        model.addAttribute("transTargetId",transTarget.getId());
        model.addAttribute("transTargetName",transTarget.getName());
        model.addAttribute("oneTarget",oneTarget);
        return "admin/resource-success";
    }


    @RequestMapping("resource/success/save")
    public String resourceSuccessSave(OneTarget oneTarget,Model mode,RedirectAttributes ra){
        try {
            oneTarget = oneTargetService.saveOneTarget(oneTarget);
            OnePriceLog onePriceLog=onePriceLogService.findOnePriceLogListByTransTargetIdPrice(oneTarget.getTransTargetId(),oneTarget.getSuccessPrice());

          /*  //成交数据同步到出让系统

            TransTarget transTarget = transTargetService.getTransTarget(oneTarget.getTransTargetId());
            //得到成交人竞买资格
            TransLicense transLicense =transLicenseService.getTransLicenseByTargetIdSysUserId(oneTarget.getTransTargetId(),oneTarget.getTransUserId());
            transTarget.setStatus(5);
            transTarget.setSuccLicenseId(transLicense.getId());
            transTarget.setTransPrice(Double.parseDouble(Long.toString(oneTarget.getSuccessPrice())));
            transTarget.setSuccTransPrice("地价:" + oneTarget.getSuccessPrice() / 10000 + "万元");
            transTarget.setEndTransTime(onePriceLog.getPriceDate());

            transLicense.setTransPrice(Double.parseDouble(Long.toString(oneTarget.getSuccessPrice())));
            transLicense.setTransDate(onePriceLog.getPriceDate());
            SysUser sysUser = sysUserService.getSysUser(onePriceLog.getTransUserId());
            TransBidder bidder = transBidderService.getTransBidder(sysUser.getId());
            transLicense.setBidderId(sysUser.getRefId());
            transLicense.setStatus(4);
            transTargetService.saveTransTarget(transTarget);
            transLicenseService.saveTransLicense(transLicense);
            ra.addFlashAttribute("_result", true);
            ra.addFlashAttribute("_msg", "保存成功！");*/

            if(null!=onePriceLog){//一次报价系统报价
                //成交数据同步到出让系统
                TransTarget transTarget = transTargetService.getTransTarget(oneTarget.getTransTargetId());
                //得到成交人竞买资格
                TransLicense transLicense =transLicenseService.getTransLicenseByTargetIdSysUserId(oneTarget.getTransTargetId(),oneTarget.getTransUserId());
                transTarget.setStatus(5);
                transTarget.setSuccLicenseId(transLicense.getId());
                transTarget.setTransPrice(Double.parseDouble(Long.toString(oneTarget.getSuccessPrice())));
                transTarget.setSuccTransPrice("地价:" + oneTarget.getSuccessPrice() / 10000 + "万元");
                transTarget.setEndTransTime(onePriceLog.getPriceDate());

                transLicense.setTransPrice(Double.parseDouble(Long.toString(oneTarget.getSuccessPrice())));
                transLicense.setTransDate(onePriceLog.getPriceDate());
                SysUser sysUser = sysUserService.getSysUser(onePriceLog.getTransUserId());
                TransBidder bidder = transBidderService.getTransBidder(sysUser.getId());
                transLicense.setBidderId(sysUser.getRefId());
                transLicense.setStatus(4);
                transTargetService.saveTransTarget(transTarget);
                transLicenseService.saveTransLicense(transLicense);
                ra.addFlashAttribute("_result", true);
                ra.addFlashAttribute("_msg", "保存成功！");
            }else{//公开出让系统
                //成交数据同步到出让系统
                TransTarget transTarget = transTargetService.getTransTarget(oneTarget.getTransTargetId());
                //得到成交人竞买资格
                TransLicense transLicense =transLicenseService.getTransLicenseByTargetIdSysUserId(oneTarget.getTransTargetId(),oneTarget.getTransUserId());
                transTarget.setStatus(5);
                transTarget.setSuccLicenseId(transLicense.getId());
                transTarget.setTransPrice(Double.parseDouble(Long.toString(oneTarget.getSuccessPrice())));
                transTarget.setSuccTransPrice("地价:" + oneTarget.getSuccessPrice() / 10000 + "万元");
                transTarget.setEndTransTime(new Date());

                transLicense.setTransPrice(Double.parseDouble(Long.toString(oneTarget.getSuccessPrice())));
                transLicense.setTransDate(new Date());
                SysUser sysUser = sysUserService.getSysUser(oneTarget.getTransUserId());
                TransBidder bidder = transBidderService.getTransBidder(sysUser.getId());
                transLicense.setBidderId(sysUser.getRefId());
                transLicense.setStatus(4);
                transTargetService.saveTransTarget(transTarget);
                transLicenseService.saveTransLicense(transLicense);
                ra.addFlashAttribute("_result", true);
                ra.addFlashAttribute("_msg", "保存成功！");

            }

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
        return "admin/between-list";
    }
    @RequestMapping("between/edit")
    public String betweenEdit(String id,Model model,RedirectAttributes ra) throws Exception{
        OneParam oneParam = oneParamService.getOneParam(id);
        model.addAttribute("oneParam",oneParam);
        return "admin/between-edit";
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
            //四舍五入，保留2位小数
            BigDecimal bigDecimal=new BigDecimal(avgPrice);
            avgPrice=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("avgPrice",avgPrice);
            model.addAttribute("onePriceLogList",onePriceLogList);
            model.addAttribute("oneTarget",oneTarget);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""+new String(oneTarget.getTransName().getBytes("gb2312"),"ISO8859-1")+".xls\"");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/admin/resourceoffer-excel.ftl");
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
//            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
