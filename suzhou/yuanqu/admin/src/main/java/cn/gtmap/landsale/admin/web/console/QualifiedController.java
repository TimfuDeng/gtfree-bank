package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.ResourceUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * 申购资格审核
 * Created by trr on 2015/10/13.
 */
@Controller
@RequestMapping(value = "/console/qualified")
public class QualifiedController  extends BaseController{
    @Autowired
    TransResourceService transResourceService;
    @Autowired
    TransResourceInfoService transResourceInfoService;
    @Autowired
    RegionService regionService;
    @Autowired
    TransUserService transUserService;

    @Autowired
    TransResourceApplyService transResourceApplyService;
    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;

    @Autowired
    TransBuyQualifiedService transBuyQualifiedService;
    @Autowired
    TransBankService transBankService;
    @Autowired
    TransBankAccountService transBankAccountService;
    @Autowired
    TransBankPayService transBankPayService;
    @Autowired
    TransUserUnionService transUserUnionService;
    @Autowired
    AttachmentCategoryService attachmentCategoryService;

    @Autowired
    ClientService clientService;

    @Autowired
    TransMaterialService transMaterialService;

    @RequestMapping(value = "list/land")
    public String releaseLandQualified(@PageDefault(value=5) Pageable page,String title,Model model){
        int resourceEditStatus=-1;
        int resourceStatus = 2;//默认查已发布的地块
        Set<String> regions = Sets.newHashSet();
        if(!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();
        Page<TransResource> transResourcePage=transResourceService.findTransResourcesByEditStatus(title, resourceStatus, null, regions, page);
        transResourcePage=transResourceService.countPassOrUnpass(transResourcePage);

        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("title", title);
        return "qualified/qualified-land-list";
    }




    @RequestMapping("list/resourceApply")
    public  String resourceApplyQualified(String resourceId,Model model) {
        //Page<TransResourceApply> transResourceApplyPage=
        //        transResourceApplyService.getTransResourceApplyPageByresourceId(resourceId,page);
        List<TransResourceApply> transResourceApplyList=transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
        if(transResourceApplyList.size()>0){
            for(TransResourceApply transResourceApply:transResourceApplyList){
                if(StringUtils.isNotBlank(transResourceApply.getInfoId())){
                    TransUserApplyInfo transUserApplyInfo= transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
                    transResourceApply.setTransUserApplyInfo(transUserApplyInfo);
                }

            }
        }
        model.addAttribute("transResourceApplyList",transResourceApplyList);
        model.addAttribute("resourceId",resourceId);
        return "qualified/qualified-list";
    }
    ///verify

    /**
     * 资格审查
     * @param applyId
     * @param model
     * @return
     */
    @RequestMapping("verify")
    public  String verifyApplyQualified(String applyId,String resourceId,Model model,RedirectAttributes ra) {
        TransBuyQualified transBuyQualified=null;
        //z申购申请id得到申购申请
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        //申购申请里面的申请人id得到申请人信息
        TransUserApplyInfo transUserApplyInfo= transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
        //申购人账号信息
        TransUser transUser=transUserService.getTransUser(transUserApplyInfo.getUserId());
        //根据币种得到对应支持银行
        List<TransBank>  bankList=transBankService.getBankList(transResourceApply.getMoneyUnit());
        //得到联合竞买者的信息
        List<TransUserUnion>  transUserUnionList= transUserUnionService.findTransUserUnion(applyId);
        transBuyQualified=new TransBuyQualified();
        transBuyQualified.setQualifiedTime(new Date());
        transBuyQualified.setQualifiedStatus(1);
        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transResourceApply",transResourceApply);
        model.addAttribute("transBuyQualified",transBuyQualified);
        model.addAttribute("transUser",transUser);
        model.addAttribute("bankList",bankList);
        model.addAttribute("resourceId",resourceId);
        model.addAttribute("applyId",applyId);
        model.addAttribute("transUserUnionList",transUserUnionList);

        return "qualified/qualified-verify";
    }


    @RequestMapping("verify/list")
    public  String listQualified(String applyId,Model model) {

        //z申购申请id得到申购申请
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        //申购人账号信息
        TransUser transUser=transUserService.getTransUser(transResourceApply.getUserId());
        List<TransBuyQualified> transBuyQualifiedList=transBuyQualifiedService.getListTransBuyQualifiedByApplyId(applyId);
        model.addAttribute("transResourceApply",transResourceApply);
        model.addAttribute("transBuyQualifiedList",transBuyQualifiedList);
        model.addAttribute("transUser",transUser);

        return "qualified/qualified-verify-list";
    }

    @RequestMapping("verify/view")
    public  String viewApplyQualified(String qualifiedId,String applyId,Model model) {

        //z申购申请id得到申购申请
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        //申购申请里面的申请人id得到申请人信息
        TransUserApplyInfo transUserApplyInfo= transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
        //申购人账号信息
        TransUser transUser=transUserService.getTransUser(transUserApplyInfo.getUserId());
        //根据币种得到对应支持银行
        List<TransBank>  bankList=transBankService.getBankList(transResourceApply.getMoneyUnit());
        TransBuyQualified transBuyQualified=transBuyQualifiedService.getTransBuyQualifiedById(qualifiedId);
        //得到联合竞买者的信息
        List<TransUserUnion>  transUserUnionList= transUserUnionService.findTransUserUnion(applyId);
        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transResourceApply",transResourceApply);
        model.addAttribute("transBuyQualified",transBuyQualified);
        model.addAttribute("transUser",transUser);
        model.addAttribute("transUserUnionList",transUserUnionList);
        model.addAttribute("bankList",bankList);
        model.addAttribute("resourceId",transResourceApply.getResourceId());
        return "qualified/qualified-view";
    }

    @ModelAttribute("transBuyQualified")
    public TransBuyQualified getRole(@RequestParam(value = "qualifiedId",required = false)String qualifiedId){
        return StringUtils.isBlank(qualifiedId) ? new TransBuyQualified():transBuyQualifiedService.getTransBuyQualifiedById(qualifiedId);
    }

    /**
     * 保存资格审查
     * @param
     * @param model
     * @return
     */
  @RequestMapping("verify/save")
    public  String saveVerifyApplyQualified(TransBuyQualified transBuyQualified,String applyId,String resourceId,String bankId,Model model,RedirectAttributes ra) {
     /* if (StringUtils.isBlank(transBuyQualified.getQualifiedAuditor())){
          ra.addFlashAttribute("_result",false);
          ra.addFlashAttribute("_msg", "请填写申请人！");
          transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
          return "redirect:/console/qualified/verify?applyId="+applyId+"&&resourceId="+resourceId;
      }*/
      if (StringUtils.isBlank(bankId)){
          ra.addFlashAttribute("_result",false);
          ra.addFlashAttribute("_msg", "请选择银行！");
          transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
          return "redirect:/console/qualified/verify?applyId="+applyId+"&&resourceId="+resourceId;
      }
      if (0!=transBuyQualified.getQualifiedStatus()&&1!=transBuyQualified.getQualifiedStatus()){
          ra.addFlashAttribute("_result",false);
          ra.addFlashAttribute("_msg", "请选择是否通过！");
         transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
          return "redirect:/console/qualified/verify?applyId="+applyId+"&&resourceId="+resourceId;
      }
      if (0==transBuyQualified.getQualifiedStatus()&&StringUtils.isBlank(transBuyQualified.getQualifiedReason())){
          ra.addFlashAttribute("_result",false);
          ra.addFlashAttribute("_msg", "请填写不通过的原因");
          transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
          return "redirect:/console/qualified/verify?applyId="+applyId+"&&resourceId="+resourceId;
      }

      //申购申请id得到申购申请
      TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
      transBuyQualified.setResourceId(resourceId);
      transBuyQualified.setApplyId(applyId);
      transBuyQualified.setInfoId(transResourceApply.getInfoId());
      transBuyQualified= transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
      //审核通过则设置申购里面的步骤为3
      if(1==transBuyQualified.getQualifiedStatus()){
          transResourceApply.setApplyStep(Constants.StepBaoZhengJin);
      }else if(0==transBuyQualified.getQualifiedStatus()){
          transResourceApply.setApplyStep(Constants.StepQualifiedFaile);
      }
      transResourceApply.setBankId(bankId);
      String bankCode=transBankService.getBank(bankId).getBankCode();
      transResourceApply.setBankCode(bankCode);

      transResourceApply.setQualifiedId(transBuyQualified.getQualifiedId());

      transResourceApplyService.saveTransResourceApply(transResourceApply);


     /* TransResourceApply  transResourceApplyOld=
              transResourceApplyService.getTransResourceApply(transResourceApply.getApplyId());
      if (StringUtils.isNotBlank(transResourceApplyOld.getBankCode()) && !transResourceApplyOld.getBankCode().equals(transResourceApply.getBankCode())){

          TransBankAccount transBankAccount=transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
          List<TransBankPay> transBankPayList=transBankPayService.getTransBankPaysByAccountId(transBankAccount.getAccountId());
          if (transBankPayList.size()>0){
              ra.addFlashAttribute("_msg", "该账户已收到保证金，不能重新选择银行！");
             // return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
          } else {
              transBankAccount.setAccountCode(null);
              transBankAccount.setApplyNo(transBankAccountService.getNextApplyNo());   //重新生成一个竞买号
              transBankAccountService.saveTransBankAccount(transBankAccount);
          }
      }*/
    //审核时否时自动退回
      if (StringUtils.isNotBlank(applyId)&&transBuyQualified.getQualifiedStatus()==0) {
          transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
          transResourceApply.setApplyStep(Constants.StepBaoMing);
          transResourceApplyService.saveTransResourceApply(transResourceApply);
      }
      //审核通过自动生成子行号，无法生成则回到资格审核状态，进行重新审核
      TransBankAccount transBankAccount=null;
      if(StringUtils.isNotBlank(applyId)&&transBuyQualified.getQualifiedStatus()==1){
          try {
              transBankAccount=bzj(transResourceApply.getResourceId(), transResourceApply.getUserId());
              if(StringUtils.isNotBlank(transBankAccount.getAccountCode())){
                  ra.addFlashAttribute("_result", true);
                  ra.addFlashAttribute("_msg", "保存成功!银行子账号申请成功！");
              }else{
                  transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
                  transResourceApply.setApplyStep(Constants.StepQualified);
                  transResourceApplyService.saveTransResourceApply(transResourceApply);
                  ra.addFlashAttribute("_result", false);
                  ra.addFlashAttribute("_msg", "保存失败！银行子行号申请失败！");
                  return "redirect:/console/qualified/verify?applyId="+applyId+"&&resourceId="+transResourceApply.getResourceId();
              }
          } catch (Exception e) {
              transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
              transResourceApply.setApplyStep(Constants.StepQualified);
              transResourceApplyService.saveTransResourceApply(transResourceApply);
              ra.addFlashAttribute("_result", false);
              ra.addFlashAttribute("_msg", "保存失败！银行网络出错!");
              return "redirect:/console/qualified/verify?applyId="+applyId+"&&resourceId="+transResourceApply.getResourceId();
          }
      }

      return "redirect:/console/qualified/verify/view?qualifiedId="+transBuyQualified.getQualifiedId()+"&&applyId="+applyId;
    }


    /**
     * 生成银行子账号
     * @param resourceId
     * @return
     * @throws Exception
     */
    public TransBankAccount bzj(String resourceId,String userId) throws Exception{

        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
        TransBankAccount transBankAccount=
                transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
        if (StringUtils.isBlank(transBankAccount.getAccountCode())){
                transBankAccount = clientService.applyBankAccount(transResourceApply.getApplyId());

        }
        return  transBankAccount;
    }

    private TransBank getBank(String bankId){
        return transBankService.getBank(bankId);
    }


    @RequestMapping(value="view")
    public String show(String resourceId,Model model){
        TransResourceInfo transResourceInfo = null;
        TransResource transResource = null;
        TransResourceVerify transResourceVerify = null;
        TransUser user = new TransUser();
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
            transResourceInfo=transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
        }else{
            transResource= ResourceUtil.buildNewResource();
            List<String[]> regionList = regionService.findAllRegions();
            transResource.setRegionCode(regionList.get(0)[0]);
        }
        if (transResourceInfo==null) {
            transResourceInfo = new TransResourceInfo();
        }
        if(transResourceVerify!=null) {
            user = transUserService.getTransUser(transResourceVerify.getAuditor());
        }
        model.addAttribute("transResourceInfo", transResourceInfo);
        model.addAttribute("regionAllList", regionService.findAllDeptRegions());
        model.addAttribute("transResource", transResource);
        model.addAttribute("auditor",user.getViewName());
        return "qualified/qualified-land-detail";
    }

    @RequestMapping("attachment")
    public String attachments(@RequestParam(value = "resourceId", required = true)String resourceId,@RequestParam(value = "userId", required = true)String userId,Model model){
        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
        TransUser transUser=transUserService.getTransUser(userId);
        TransUserApplyInfo transUserApplyInfo= transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
        List<TransMaterial> transMaterials = transMaterialService.getMaterialsByRegionCode(transResourceService.getTransResource(resourceId).getRegionCode());
        Map<String,String> map = Maps.newHashMap();
        for(TransMaterial transMaterial:transMaterials){
            map.put(transMaterial.getMaterialCode(),transMaterial.getMaterialName());
        }
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("transUserApplyInfo",transUserApplyInfo);
        model.addAttribute("transUser",transUser);
        model.addAttribute("resourceId",resourceId);
        model.addAttribute("attachmentTypeList", map);
        return "qualified/resource-apply-attachments";
    }

    @RequestMapping("/status/change.f")
    public @ResponseBody
    Object changeStatus(String applyId,int status,Model model) {
        TransResourceApply transResourceApply=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
            transResourceApply.setApplyStep(status);
           transResourceApplyService.saveTransResourceApply(transResourceApply);
            return success();
        }
        return fail("ID为空！");
    }
    @RequestMapping("/status/qualified-list-status.f")
    public String status(String applyId,Model model) {

        TransResourceApply transResourceApply=null;
        if (StringUtils.isNotBlank(applyId)) {
            transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        }
        model.addAttribute("resourceApply", transResourceApply);
        return "common/qualified-list-status";
    }

    @RequestMapping("/list/scale")
    public String getUserScale(String applyId,Model model){
        List<TransUserUnion> transUserUnions = null;
        double scale = 0;//所有被联合人的出资比例
        if(StringUtils.isNotBlank(applyId))
            transUserUnions = transUserUnionService.findTransUserUnion(applyId);
        for(TransUserUnion transUserUnion:transUserUnions){
            scale += transUserUnion.getAmountScale();
        }
        model.addAttribute("scale",scale);
        model.addAttribute("transUserUnions",transUserUnions);
        return "qualified/qualified-scale-list";
    }

    /**
     * 暂时废弃
     * @param applyId
     * @param resourceId
     * @param model
     * @param ra
     * @return
     */
    @RequestMapping("/backEdit")
    public String backEdit(String applyId,String resourceId,Model model,RedirectAttributes ra){
        TransBuyQualified transBuyQualified=null;
        //z申购申请id得到申购申请
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        //申购申请里面的申请人id得到申请人信息
        TransUserApplyInfo transUserApplyInfo= transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
        //申购人账号信息
        TransUser transUser=transUserService.getTransUser(transUserApplyInfo.getUserId());

        //得到联合竞买者的信息
        List<TransUserUnion>  transUserUnionList= transUserUnionService.findTransUserUnion(applyId);
        transBuyQualified=transBuyQualifiedService.getTransBuyQualifiedByApplyId(applyId);
        if(null==transBuyQualified){
            transBuyQualified=new TransBuyQualified();
            transBuyQualified.setQualifiedTime(new Date());
        }



        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transResourceApply",transResourceApply);
        model.addAttribute("transBuyQualified",transBuyQualified);
        model.addAttribute("transUser",transUser);
        model.addAttribute("resourceId",resourceId);
        model.addAttribute("applyId",applyId);
        model.addAttribute("transUserUnionList",transUserUnionList);
        return "qualified/qualified-force-cancle";
    }

    @RequestMapping("/backEdit/save")
    public  String saveBackEditQualified(TransBuyQualified transBuyQualified,String applyId,String resourceId,Model model,RedirectAttributes ra) {

        if (2==transBuyQualified.getQualifiedStatus()&&StringUtils.isBlank(transBuyQualified.getQualifiedReason())){
            ra.addFlashAttribute("_result",false);
            ra.addFlashAttribute("_msg", "请填写强制退回的原因");
            transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
            return "redirect:/console/qualified/backEdit?applyId="+applyId+"&&resourceId="+resourceId;
        }

        //申购申请id得到申购申请
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
        transBuyQualified.setResourceId(resourceId);
        transBuyQualified.setApplyId(applyId);
        transBuyQualified.setInfoId(transResourceApply.getInfoId());
        transBuyQualified= transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
        transResourceApply.setQualifiedId(transBuyQualified.getQualifiedId());
        transResourceApplyService.saveTransResourceApply(transResourceApply);

        //强制退回时否时自动退回
        if (StringUtils.isNotBlank(applyId)&&transBuyQualified.getQualifiedStatus()==2) {
            transResourceApply= transResourceApplyService.getTransResourceApply(applyId);
            transResourceApply.setApplyStep(Constants.StepBaoZhengJin);
            transResourceApplyService.saveTransResourceApply(transResourceApply);
        }

        ra.addFlashAttribute("_result", true);
        if(transBuyQualified.getQualifiedStatus()==2){
            ra.addFlashAttribute("_msg", "强制退回成功");
        }else{
            ra.addFlashAttribute("_msg", "取消强制退回成功");
        }

        return "redirect:/console/qualified/backEdit?applyId="+applyId+"&&resourceId="+resourceId;
    }
}
