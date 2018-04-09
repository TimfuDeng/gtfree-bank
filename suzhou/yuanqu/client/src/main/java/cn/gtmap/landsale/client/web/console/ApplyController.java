package cn.gtmap.landsale.client.web.console;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/10.
 */
@Controller
public class ApplyController {

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransBankService transBankService;

    @Autowired
    ClientService clientService;

    @Autowired
    TransBankAccountService transBankAccountService;

    @Autowired
    TransUserService transUserService;

    @Autowired
    CaSvsService caSvsService;

    @Autowired
    TransUserUnionService transUserUnionService;

    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;

    @Autowired
    TransBankPayService transBankPayService;

    @Autowired
    MaterialCrggService materialCrggService;

    @Autowired
    TransMaterialService transMaterialService;


    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    AttachmentCategoryService attachmentCategoryService;

    @Autowired
    TransBuyQualifiedService transBuyQualifiedService;

    @RequestMapping("/console/apply")
      public String apply(String id,Model model,RedirectAttributes ra) throws Exception{
        TransResource transResource= transResourceService.getTransResource(id);
        long currentTime = Calendar.getInstance().getTime().getTime();
        long bmEndTime = transResource.getBmEndTime().getTime();
        if(currentTime > bmEndTime){
            return "redirect:/view?id="+id;
        }
        TransCrgg transCrgg= transCrggService.getTransCrgg(transResource.getGgId());

        String userId= SecUtil.getLoginUserId();
        TransUser transUser = transUserService.getTransUser(userId);
        model.addAttribute("transResource", transResource);
        model.addAttribute("transCrgg", transCrgg);
        if(transUser==null&&caEnabled)
            model.addAttribute("caEnabled",true);
        else
            model.addAttribute("caEnabled",false);

        //判断是否当前用户是否参加了当前地块的联合竞买，如果是，则不能再次报名竞买
        TransUserUnion transUserUnion = transUserUnionService.getResourceTransUserUnionByUserName(SecUtil.getLoginUserViewName(), id);
        if(transUserUnion!=null) {
            model.addAttribute("applyEnabled",false);
            model.addAttribute("msg","当前用户已参加本地块的联合竞买，不能再次报名！");
        }else{
            model.addAttribute("applyEnabled",true);
        }

        return  "/console/apply";
    }

    @RequestMapping("/console/apply-fail")
    public String applyFail(String id,Model model) throws Exception{
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(id);

        TransBuyQualified transBuyQualified=  transBuyQualifiedService.getTransBuyQualifiedById(transResourceApply.getQualifiedId());
        TransResource resource= transResourceService.getTransResource(transResourceApply.getResourceId());
        model.addAttribute("resource",resource);
        model.addAttribute("transBuyQualified",transBuyQualified);

        return  "/console/apply-fail";
    }

    @RequestMapping("/console/force-cancle")
    public String forceCancle(String id,Model model) throws Exception{
        TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApply(id);

        TransBuyQualified transBuyQualified=  transBuyQualifiedService.getTransBuyQualifiedByApplyId(transResourceApply.getApplyId());
        TransResource resource= transResourceService.getTransResource(transResourceApply.getResourceId());
        model.addAttribute("resource",resource);
        model.addAttribute("transBuyQualified",transBuyQualified);

        return  "/console/force-cancle";
    }



    @RequestMapping("/console/apply-over")
    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_APPLY,producer = Constants.LogProducer.CLIENT,
            description = "用户报名")
    public String applyOver(HttpServletRequest req,String id,CaSignerX caSignerX,Model model) throws Exception{
        String userId= SecUtil.getLoginUserId();
        TransUser transUser = transUserService.getTransUser(userId);
        //如果登录用户在管理系统中不存在，则需要新建用户信息
        if(transUser==null){
            if(caSvsService.validateCertificate(caSignerX.getSxcertificate())) {
                transUser = new TransUser();
                transUser.setUserName(caSignerX.getCertFriendlyName());
                transUser.setViewName(caSignerX.getCertFriendlyName());
                transUser.setCaThumbprint(caSignerX.getCertThumbprint());
                transUser.setCaCertificate(caSignerX.getSxcertificate());
                transUser.setCaName(caSignerX.getCertFriendlyName());
                transUser.setType(Constants.UserType.CLIENT);
                transUser.setPassword(caSignerX.getCertFriendlyName());
                transUser = transUserService.saveTransUser(transUser);
                userId = transUser.getUserId();
                SecUtil.setLoginUserIdToSession(req, transUser);
                SecUtil.setLoginUserIdToLocal(userId,transUser.getViewName());
            }else {
                throw new Exception("数字证书错误！");
            }

        }
        //再次判断是否当前用户是否参加了当前地块的联合竞买，如果是，则不能再次报名竞买
        TransUserUnion transUserUnion = transUserUnionService.getResourceTransUserUnionByUserName(transUser.getViewName(), id);
        if(transUserUnion!=null) {
            return "redirect:/console/apply?id="+id;
        }

        TransResourceApply transResourceApply=transResourceApplyService.getTransResourceApplyByUserId(userId,id);
        if (transResourceApply==null) {
            transResourceApply=new TransResourceApply();
            transResourceApply.setUserId(userId);
            transResourceApply.setResourceId(id);
            transResourceApply.setApplyDate(Calendar.getInstance().getTime());
        }
        if (transResourceApply.getApplyStep()<Constants.StepBaoMing)
            transResourceApply.setApplyStep(Constants.StepBaoMing);
        transResourceApplyService.saveTransResourceApply(transResourceApply);
        return "redirect:/console/apply-bank?id="+id;
    }

    /**
     * 选择竞买方式和银行
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/console/apply-bank")
    public String bank(String id,Model model) throws Exception{
        String userId= SecUtil.getLoginUserId();
        TransResource transResource= transResourceService.getTransResource(id);
        model.addAttribute("transResource", transResource);
        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,id);
        model.addAttribute("transResourceApply", transResourceApply);
        //银行
        List<TransBank> bankList= transBankService.getBankListByRegion(transResource.getRegionCode());
        model.addAttribute("bankList", bankList);
        //竞买人
        List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoService.getTransUserApplyInfoByUser(userId);
        if(transUserApplyInfoList!=null&&transUserApplyInfoList.size()>0)
            model.addAttribute("transUserApplyInfo",transUserApplyInfoList.get(0));
        else{
            TransUserApplyInfo transUserApplyInfo = new TransUserApplyInfo();
            transUserApplyInfo.setUserId(userId);
            model.addAttribute("transUserApplyInfo",transUserApplyInfo);
        }
        List<MaterialCrgg> materialCrggs = materialCrggService.getMaterialCrggByCrggId(transResource.getGgId());
        List<TransMaterial> necessaryCategories = Lists.newArrayList();
        List materialPersonal = Lists.newArrayList();
        List materialGroup = Lists.newArrayList();
        for(MaterialCrgg materialCrgg:materialCrggs){
            necessaryCategories.add(transMaterialService.getMaterialsById(materialCrgg.getMaterialId()));
        }
        for(TransMaterial transMaterial:necessaryCategories){
            if(transMaterial.getMaterialType().equals("PERSONAL")){
                materialPersonal.add(transMaterial);
            }else{
                materialGroup.add(transMaterial);
            }
        }
        model.addAttribute("attachmentTypeList", attachmentCategoryService.getTransResourceApplyAttachmentCategory());
        model.addAttribute("materialPersonal",materialPersonal);
        model.addAttribute("materialGroup",materialGroup);
       /* if(transResource.getResourceEditStatus()!=2){
            model.addAttribute("msg","地块已被中止，请返回首页！");
            return "redirect:/console/apply-bank?id="+transResource.getResourceId();
        }*/
        return  "/console/apply-bank";
    }

    @ModelAttribute("TransResourceApply")
    public TransResourceApply getTransResourceApply(@RequestParam(value = "id", required = false) String resourceId) {
        String userId= SecUtil.getLoginUserId();
        return StringUtils.isBlank(resourceId) ? new TransResourceApply() :
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
    }

    @RequestMapping("/console/apply-bank-over")
    public String applyBankOver(@ModelAttribute("TransResourceApply") TransResourceApply transResourceApply,TransUserApplyInfo transUserApplyInfo,RedirectAttributes ra) throws Exception{
        /*if (StringUtils.isBlank(transResourceApply.getBankCode())){
            ra.addFlashAttribute("_msg", "请选择保证金交纳银行！");
            transResourceApplyService.saveTransResourceApply(transResourceApply);
            return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
        }*/
        if (null!=transResourceApply&&null!=transResourceApply.getApplyDate()){
            transResourceApply.setApplyDate(Calendar.getInstance().getTime());
        }
        if (StringUtils.isBlank(transResourceApply.getMoneyUnit())){
            ra.addFlashAttribute("_msg", "请选择币种！");
            transResourceApplyService.saveTransResourceApply(transResourceApply);
            return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
        }
        //判断当前用户已参加本地块的联合竞买
        TransUserUnion userUnion = transUserUnionService.getResourceTransUserUnionByUserName(SecUtil.getLoginUserViewName(), transResourceApply.getResourceId());
        if(userUnion!=null) {
            ra.addFlashAttribute("_msg", "当前用户已参加本地块的联合竞买，不能再次报名！");
            return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
        }
        //判断是否联合竞买
        if(transResourceApply.getApplyType()==Constants.ApplyTypeMulti){
            List<TransUserUnion> transUserUnionList= transUserUnionService.findTransUserUnion(transResourceApply.getApplyId());
            if (transUserUnionList.size()==0){
                ra.addFlashAttribute("_msg", "联合竞买人列表为空！");
                transResourceApplyService.saveTransResourceApply(transResourceApply);
                return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
            }else{
                double scale=0;
                //判断出资比例和是否同意
                for(TransUserUnion transUserUnion: transUserUnionList){
                    scale=scale+transUserUnion.getAmountScale();
                    if (!transUserUnion.isAgree()){
                        ra.addFlashAttribute("_msg", "被联合竞买人尚未同意，请被联合竞买人登录系统，同意本次联合竞买申请！");
                        transResourceApplyService.saveTransResourceApply(transResourceApply);
                        return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
                    }
                }
                if (scale>=100){
                    ra.addFlashAttribute("_msg", "被联合竞买人出资比例总和必须小于100，余下的即为本次竞买申请人的出资比例！");
                    transResourceApplyService.saveTransResourceApply(transResourceApply);
                    return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
                }


            }
        }else{
            List<TransUserUnion> transUserUnionList= transUserUnionService.findTransUserUnion(transResourceApply.getApplyId());
            for(TransUserUnion transUserUnion: transUserUnionList){
                transUserUnionService.deleteTransUserUnion(transUserUnion.getUnionId());
            }
        }
        if (transResourceApply.getApplyStep()==Constants.StepBaoMing)
            transResourceApply.setApplyStep(Constants.StepQualified);
        //判断是否更换了银行
       /* TransResourceApply  transResourceApplyOld=
                transResourceApplyService.getTransResourceApply(transResourceApply.getApplyId());
        if (StringUtils.isNotBlank(transResourceApplyOld.getBankCode()) && !transResourceApplyOld.getBankCode().equals(transResourceApply.getBankCode())){

            TransBankAccount transBankAccount=transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
            List<TransBankPay> transBankPayList=transBankPayService.getTransBankPaysByAccountId(transBankAccount.getAccountId());
            if (transBankPayList.size()>0){
                ra.addFlashAttribute("_msg", "该账户已收到保证金，不能重新选择银行！");
                return "redirect:/console/apply-bank?id="+transResourceApply.getResourceId();
            } else {
                transBankAccount.setAccountCode(null);
                transBankAccount.setApplyNo(transBankAccountService.getNextApplyNo());   //重新生成一个竞买号
                transBankAccountService.saveTransBankAccount(transBankAccount);
            }
        }*/
        /*transUserApplyInfo= transUserApplyInfoService.saveTransUserApplyInfo(transUserApplyInfo);
        transResourceApply.setInfoId(transUserApplyInfo.getInfoId());
        transResourceApplyService.saveTransResourceApply(transResourceApply);*/
        transUserApplyInfoService.saveTransUserApplyInfoAndTransResourceApply(transUserApplyInfo,transResourceApply);

        return "redirect:/view?id="+transResourceApply.getResourceId();

    }

    /**
     *  缴纳保证金
     * @param id
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/console/apply-bzj")
    public String bzj(String id,Model model) throws Exception{
        String userId= SecUtil.getLoginUserId();
        TransResource transResource= transResourceService.getTransResource(id);
        model.addAttribute("transResource", transResource);
        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,id);
        model.addAttribute("transResourceApply", transResourceApply);

        TransBankAccount transBankAccount=
                transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
        if (StringUtils.isBlank(transBankAccount.getAccountCode())){
            try {
                transBankAccount = clientService.applyBankAccount(transResourceApply.getApplyId());
            }catch (Exception ex){
                model.addAttribute("_msg", "保证金帐号无法生成请重新刷新页面或联系管理员！");
            }
        }
        //找到开户银行的信息
        /*model.addAttribute("bank", getBank(transResource.getRegionCode(), transResourceApply.getBankCode(),
                transResourceApply.getMoneyUnit()));*/
        model.addAttribute("bank", getBank(transResourceApply.getBankId()));

        model.addAttribute("transBankAccount",transBankAccount);
        return  "/console/apply-bzj";
    }

    private TransBank getBank(String bankId){
        return transBankService.getBank(bankId);
    }

    private TransBank getBank(String regionCode,String bankCode,String moneyUnit){
        List<TransBank> bankList= transBankService.getBankListByRegion(regionCode);
        for(TransBank transBank:bankList){
            if(bankCode.equals(transBank.getBankCode())){
                if(transBank.getMoneyUnit().equals("CNY") && StringUtils.isBlank(moneyUnit)) {
                    return transBank;
                }else if(StringUtils.isNotBlank(moneyUnit) && transBank.getMoneyUnit().equals(moneyUnit)  ){
                    return transBank;
                }
            }
        }
        return null;
    }

    @RequestMapping("/console/apply-account.f")
    public @ResponseBody
    TransBankAccount applyAccount(String applyId){
        TransResourceApply transResourceApply=
                transResourceApplyService.getTransResourceApply(applyId);
        TransBankAccount transBankAccount=
                transBankAccountService.createOrGetTransBankAccount(applyId);
        return StringUtils.isBlank(transBankAccount.getAccountCode()) ? clientService.applyBankAccount(applyId):transBankAccount;
    }

    @RequestMapping("/console/apply/fileCheck")
    @ResponseBody
    public boolean checkFile(String applyId,String applyType){
        String resourceId = transResourceApplyService.getTransResourceApply(applyId).getResourceId();
        String ggId = transResourceService.getTransResource(resourceId).getGgId();
        return attachmentCategoryService.checkAttachmentNessesary(applyId, applyType, ggId);
    }

    @RequestMapping("/console/apply/resourceCheck")
    @ResponseBody
    public boolean checkResource(String id){
        TransResource transResource = transResourceService.getTransResource(id);
        if(transResource.getResourceEditStatus()!=2){
            return false;
        }
        return true;
    }
}
