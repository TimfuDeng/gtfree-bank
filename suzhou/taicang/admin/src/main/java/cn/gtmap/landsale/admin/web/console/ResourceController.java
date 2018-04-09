package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.core.BankAllList;
import cn.gtmap.landsale.core.RegionAllList;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.RMBUtils;
import cn.gtmap.landsale.util.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 出让地块管理
 * User: JIFF
 * Date: 2014/12/31
 */
@Controller
@RequestMapping(value = "console/resource")
public class ResourceController extends BaseController {

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceInfoService transResourceInfoService;

    @Autowired
    TransBankService transBankService;

    @Autowired
    BankAllList bankAllList;

    @Autowired
    TransFileService transFileService;

    @Autowired
    RegionService regionService;

    @Autowired
    TransResourceOfferService transResourceOfferService;
    @Autowired
    TransUserService transUserService;
    @Autowired
    TransResourceApplyService transResourceApplyService;
    @Autowired
    TransUserUnionService transUserUnionService;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    AttachmentCategoryService attachmentCategoryService;

    @Autowired
    CaSvsService caSvsService;

    //将挂牌时间，报名时间，保证金交纳记录在static变量里，下次新增时提取
    public static Date bmBeginTime = null;
    public static Date bmEndTime = null;
    public static Date gpBeginTime = null;
    public static Date gpEndTime = null;
    public static Date bzjBeginTime = null;
    public static Date bzjEndTime = null;

    @ModelAttribute("resource")
    public TransResource getResource(@RequestParam(value = "resourceId", required = false) String resourceId) {
        return StringUtils.isBlank(resourceId) ? new TransResource() : transResourceService.getTransResource(resourceId);
    }

    @ModelAttribute("resourceInfo")
    public TransResourceInfo getResourceInfo(@RequestParam(value = "resourceId", required = false) String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
          return  new TransResourceInfo();
        }else{
            TransResourceInfo transResourceInfo=transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
            return transResourceInfo==null ? new TransResourceInfo():transResourceInfo;
        }
    }

    @RequestMapping("list")
    public String list(@PageDefault(value=5) Pageable page,String queryType,String title,String status ,String ggId,Model model) {
        int resourceEditStatus=-1;
        if (StringUtils.isNotBlank(status))
            resourceEditStatus=Integer.parseInt(status);
        Set<String> regions = Sets.newHashSet();

        boolean zgqsAuthorize = false;// 资格前审权限【王建明 2016-05-06 16:52】
        if(!SecUtil.isAdmin()){
            regions = SecUtil.getPermittedRegions();


            TransUser transUser = transUserService.getTransUser(SecUtil.getLoginUserId());
            String privileges = transUser.getPrivilege();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(privileges)) {
                if(privileges.indexOf("zgqs_temp_aaa") > -1){
                    zgqsAuthorize = true;
                }
            }
        } else {
            zgqsAuthorize = true;
        }
        Page<TransResource> transResourcePage= transResourceService.findTransResourcesByEditStatusAndType(queryType,title,resourceEditStatus,ggId,regions,page);
        
        //报名（报名且报价）人数加1，实时统计，enrolledOfferNum不再写入数据库【邵可2016-06-22 8:55】
        HashMap<String,String> offerMap = new HashMap<String,String>();
        List<Object[]> cntList = transResourceOfferService.getOfferNumList();
        for(int i = 0; i < cntList.size(); i++){
        	offerMap.put(cntList.get(i)[0].toString(), cntList.get(i)[1].toString());
        }
        model.addAttribute("offerMap", offerMap);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("title", title);
        model.addAttribute("ggId", ggId);
        model.addAttribute("zgqsAuthorize", zgqsAuthorize);
        model.addAttribute("status", resourceEditStatus);
        return "resource-list";
    }


    /**
     * 已发布的地块列表
     * @param page
     * @param title
     * @param model
     * @return
     */
    @RequestMapping("/release/list")
    public String releaseList(@PageDefault(value=5) Pageable page,String title,String ggId,Model model) {
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResourcePage= transResourceService.findTransResourcesByEditStatus(title, Constants.ResourceEditStatusRelease, ggId, regions, page);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("title", title);
        return "resource-release-list";
    }


    @RequestMapping("/release/query")
    @AuditServiceLog(category = Constants.LogCategory.DATA_VIEW,producer = Constants.LogProducer.ADMIN,
            description = "查看地块报名情况")
    public String releaseQuery(@RequestParam(value = "resourceId", required = true)String resourceId,String caThumbprint1,
                               String caThumbprint2, String caThumbprint3,String caName1,String caName2,String caName3,Model model,
                               RedirectAttributes ra) throws Exception {
        if(!isValidCaUsers(caThumbprint1,caThumbprint2,caThumbprint3)) {
            ra.addFlashAttribute("_result", false);
            ra.addFlashAttribute("_msg", "CA数字证书无效，请提供正确有效的CA数字证书！");
            ra.addFlashAttribute("caThumbprint1", caThumbprint1);
            ra.addFlashAttribute("caThumbprint2", caThumbprint2);
            ra.addFlashAttribute("caThumbprint3", caThumbprint3);
            ra.addFlashAttribute("caName1", caName1);
            ra.addFlashAttribute("caName2", caName2);
            ra.addFlashAttribute("caName3", caName3);
            return "redirect:/console/resource/release/list";
        }
        List<TransResourceApply> transResourceApplyList = transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
        model.addAttribute("transResourceApplyList", transResourceApplyList);
        return "resource-release-query";
    }


    private boolean isValidCaUsers(String caThumbprint1,String caThumbprint2,String caThumbprint3) throws Exception {
        if(StringUtils.isNotBlank(caThumbprint1)&&StringUtils.isNotBlank(caThumbprint2)&&StringUtils.isNotBlank(caThumbprint3)){
            TransUser transUser1 = transUserService.getTransUserByCAThumbprint(caThumbprint1);
            TransUser transUser2 = transUserService.getTransUserByCAThumbprint(caThumbprint2);
            TransUser transUser3 = transUserService.getTransUserByCAThumbprint(caThumbprint3);
            if(transUser1!=null&&transUser2!=null&&transUser3!=null&&(!transUser1.getUserId().equals(transUser2.getUserId()))
                    &&(!transUser1.getUserId().equals(transUser3.getUserId()))&&(!transUser2.getUserId().equals(transUser3.getUserId()))){
                if(caSvsService.validateCertificate(transUser1.getCaCertificate())&&caSvsService.validateCertificate(transUser2.getCaCertificate())&&caSvsService.validateCertificate(transUser2.getCaCertificate()))
                    return true;
            }
        }
        return false;
    }



    @RequestMapping("edit")
    public String edit(String resourceId,String ggId,Model model) {
        TransResourceInfo transResourceInfo=null;
        TransResource transResource=null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
            transResourceInfo=transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
        }else{
            transResource= ResourceUtil.buildNewResource();
            List<String[]> regionList = regionService.findAllRegions();
            transResource.setRegionCode(regionList.get(0)[0]);
            transResource.setGgId(ggId);
        }
        if (transResourceInfo==null)
            transResourceInfo=new TransResourceInfo();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(ggId))
            transResource.setGgId(ggId);
        model.addAttribute("transResourceInfo", transResourceInfo);
        model.addAttribute("regionAllList", regionService.findAllRegions());
        model.addAttribute("transResource", transResource);
        model.addAttribute("openBankList", transBankService.getBankListByRegion(transResource.getRegionCode()));
        model.addAttribute("fileKey", cn.gtmap.egovplat.core.util.UUID.hex32());
        Map attachmentCategory = attachmentCategoryService.getTransResourceAttachmentCategory();
        attachmentCategory.put(Constants.FileType.QT.getCode(),Constants.FileType.QT.toString());
        model.addAttribute("attachmentCategory",attachmentCategory);

        return "resource-edit";
    }

    @RequestMapping("view")
    public String readonly(String resourceId,String ggId,Model model) {
        edit(resourceId, ggId, model);
        return "resource-view";
    }

    @RequestMapping("/edit/banks.f")
    public String banks(String resourceId,String regionCode,Model model) {
        TransResource transResource=null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
        }else{
            transResource= ResourceUtil.buildNewResource();
            List<String[]> regionList = regionService.findAllRegions();
            transResource.setRegionCode(regionList.get(0)[0]);
        }
        model.addAttribute("transResource", transResource);
        model.addAttribute("openBankList", transBankService.getBankListByRegion(regionCode));
        return "common/resource-bank";
    }

    @RequestMapping("/status/change.f")
    public @ResponseBody Object changeStatus(String resourceId,int status,Model model) {
        TransResource transResource=null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
            if(status > 0&&StringUtils.isBlank(transResource.getBanks()))
                throw new AppException(2101,transResource.getResourceCode());
            transResource.setResourceEditStatus(status);
            transResourceService.saveTransResource(transResource);
            return success();
        }
        return fail("地块ID为空！");
    }

    @RequestMapping("/status/view.f")
    public String status(String resourceId,Model model) {
        TransResource transResource=null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
        }
        model.addAttribute("transResource", transResource);
        return "common/resource-status";
    }

    @RequestMapping("/edit/save")
    @Transactional
    public String save(@ModelAttribute("resource") TransResource transResource,
                       @ModelAttribute("resourceInfo") TransResourceInfo transResourceInfo,
                       RedirectAttributes ra,String minOffer,Model model) {
        if(StringUtils.isBlank(minOffer))
            transResource.setMinOffer(false);
        if (StringUtils.isBlank(transResource.getResourceId())) {
            transResource.setResourceId(null);
        }
        if (transResource.getShowTime()==null)
            transResource.setShowTime(Calendar.getInstance().getTime());
        
        bmBeginTime = transResource.getBmBeginTime();
        bmEndTime = transResource.getBmEndTime();
        gpBeginTime = transResource.getGpBeginTime();
        gpEndTime = transResource.getGpEndTime();
        bzjBeginTime = transResource.getBzjBeginTime();
        bzjEndTime = transResource.getBzjEndTime();
        
        TransResource resource= transResourceService.saveTransResource(transResource);
        transResourceInfo.setResourceId(resource.getResourceId());
        if(transResourceInfo.getInfoId().equals("")) transResourceInfo.setInfoId(null);
        transResourceInfoService.saveTransResourceInfo(transResourceInfo);
        ra.addFlashAttribute("_result", true);
        ra.addFlashAttribute("_msg", "保存成功！");
        return "redirect:/console/resource/edit?resourceId=" + transResource.getResourceId();
    }

    /**
     * 成交确认书
     * @param resourceId
     * @param response
     * @throws Exception
     */
    @RequestMapping("cjqrs.f")
    public void cjqrs(String resourceId,HttpServletResponse response,HttpServletRequest request) throws Exception {
        OutputStream outputStream = null;
        Writer bufferedWriter = null;
        try {
            TransResource resource= transResourceService.getTransResource(resourceId);
            TransResourceOffer offerPrice=transResourceOfferService.getTransResourceOffer(resource.getOfferId());
            //TransResourceOffer offerPrice=transResourceOfferService.getMaxOfferFormPrice(resourceId);
            TransUser user=transUserService.getTransUser(offerPrice.getUserId());
            TransCrgg transCrgg = transCrggService.getTransCrgg(resource.getGgId());
            TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApplyByUserId(offerPrice.getUserId(), resourceId);
            List<TransUserUnion> transUserUnionList= transUserUnionService.findTransUserUnion(transResourceApply.getApplyId());

            Map params = Maps.newHashMap();
            params.put("regionName", regionService.getDefaultRegionName());
            Calendar gpBeginCalendar = Calendar.getInstance();
            gpBeginCalendar.setTime(resource.getGpBeginTime());
            Calendar gpEndCalendar = Calendar.getInstance();
            gpEndCalendar.setTime(resource.getGpEndTime());
            Calendar resourceOverCalendar = Calendar.getInstance();
            resourceOverCalendar.setTime(resource.getOverTime());
            params.put("resource", resource);
            params.put("offer", offerPrice);
            params.put("resourceCode", resource.getResourceCode());
            params.put("offerPrice", offerPrice.getOfferPrice() * 10000);
            params.put("offerPriceLocal", RMBUtils.toBigAmt(offerPrice.getOfferPrice() * 10000));
            params.put("offerAreaLocal", RMBUtils.toBigAmt(resource.getCrArea()));
            params.put("ggNum",transCrgg.getGgNum());
            params.put("newComName", StringUtils.isBlank(transResourceApply.getCreateNewComName()) ? "/" : transResourceApply.getCreateNewComName());
            params.put("user", user);

            int transUserUnionSize = transUserUnionList!=null?transUserUnionList.size():0;
            double userAmountScale = 100;
            DecimalFormat decimalFormat = new DecimalFormat("###.#");
            for(int i=0;i<4;i++){
                String unionUser = "/";
                String unionUserAmountScale = "/";

                if(i<transUserUnionSize) {
                    unionUser =transUserUnionList.get(i).getUserName();
                    unionUserAmountScale = decimalFormat.format(transUserUnionList.get(i).getAmountScale());
                    userAmountScale -= transUserUnionList.get(i).getAmountScale();
                }
                params.put("unionUser"+(i+1), unionUser);
                params.put("unionUserAmountScale"+(i+1), unionUserAmountScale);
            }
            if(transUserUnionSize>0){
                params.put("unionUser"+0, user.getViewName());
                params.put("unionUserAmountScale"+0, decimalFormat.format(userAmountScale));
            }else{
                params.put("unionUser"+0, "/");
                params.put("unionUserAmountScale"+0, "/");
            }

            StringBuilder fileName = new StringBuilder();
            fileName.append(resource.getResourceCode());
            fileName.append("成交确认书.doc");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/resource-cjqrs.ftl",Charsets.UTF8);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.toString().getBytes(Charsets.CHARSET_GBK), Charsets.CHARSET_ISO88591));
            response.setContentType("application/msword; charset="+Charsets.UTF8);
            outputStream = response.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,Charsets.CHARSET_UTF8));
            template.process(params,bufferedWriter);
            outputStream.flush();
        } finally {
            if(bufferedWriter!=null)
                bufferedWriter.close();
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }



}
