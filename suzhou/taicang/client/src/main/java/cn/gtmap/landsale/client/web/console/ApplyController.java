package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
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

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    AttachmentCategoryService attachmentCategoryService;

    @RequestMapping("/console/apply")
    public String apply(String id,Model model) throws Exception{
        TransResource transResource= transResourceService.getTransResource(id);
        TransCrgg transCrgg= transCrggService.getTransCrgg(transResource.getGgId());

        //判断当前地块是否已结束报名，如果已结束，则不能再报名
        Date bmEndTime = transResource.getBmEndTime();
        Date cDate= Calendar.getInstance().getTime();
        if(bmEndTime != null && cDate.after(bmEndTime)){
            model.addAttribute("applyEnabled",false);
            model.addAttribute("msg","报名已结束！");
            return "redirect:/view?id="+id;
        }
        
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

    @RequestMapping("/console/apply-over")
    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_APPLY,producer = Constants.LogProducer.CLIENT,
            description = "用户报名")
    public String applyOver(HttpServletRequest req,String id,CaSignerX caSignerX,Model model) throws Exception{
        String userId= SecUtil.getLoginUserId();
        TransUser transUser = transUserService.getTransUser(userId);
        //如果登录用户在管理系统中不存在，则需要新建用户信息
        if (transUser == null) {
            if (caSvsService.validateCertificate(caSignerX.getSxcertificate())) {
                transUser = transUserService.getTransUserByKeyInfo(caSignerX.getCertFriendlyName(), caSignerX.getCertThumbprint());
                if (transUser == null) {
                    transUser = new TransUser();
                    transUser.setType(Constants.UserType.CLIENT);
                    transUser.setUserName(caSignerX.getCertFriendlyName());
                    transUser.setViewName(caSignerX.getCertFriendlyName());
                    transUser.setPassword("1");
                    transUser.setCaName(caSignerX.getCertFriendlyName());
                }
                transUser.setCaThumbprint(caSignerX.getCertThumbprint());
                transUser.setCaCertificate(caSignerX.getSxcertificate());
                transUser = transUserService.saveTransUser(transUser);
                userId = transUser.getUserId();
                SecUtil.setLoginUserIdToSession(req, transUser);
                SecUtil.setLoginUserIdToLocal(userId, transUser.getViewName());
            } else {
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

        model.addAttribute("attachmentTypeList", attachmentCategoryService.getTransResourceApplyAttachmentCategory());
        return  "/console/apply-bank";
    }

    @ModelAttribute("TransResourceApply")
    public TransResourceApply getTransResourceApply(@RequestParam(value = "id", required = false) String resourceId) {
        String userId= SecUtil.getLoginUserId();
        return StringUtils.isBlank(resourceId) ? new TransResourceApply() :
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
    }

    @RequestMapping("/console/apply-bank-over")
    public String applyBankOver(@ModelAttribute("TransResourceApply") TransResourceApply transResourceApply,TransUserApplyInfo transUserApplyInfo,RedirectAttributes ra,HttpServletRequest request) throws Exception{
        /*if(caEnabled != null && !caEnabled){
            Enumeration e = request.getHeaders("Referer");
            String a = "";
            if (e.hasMoreElements()) {
                a = (String) e.nextElement();
            }
            if (StringUtils.isNotBlank(a)) {
                if (!a.contains("jsyd.szgtj.gov.cn:8084")) {
                    throw new Exception("referer not valid!!!");
                }
            }
        }*/

        if (StringUtils.isBlank(transResourceApply.getBankCode())){
            ra.addFlashAttribute("_msg", "请选择保证金交纳银行！");
            transResourceApplyService.saveTransResourceApply(transResourceApply);
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
            transResourceApply.setApplyStep(Constants.StepBaoZhengJin);
        //判断是否更换了银行
        TransResourceApply  transResourceApplyOld=
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
        }

        // 如果竞买的地块是资格前审，则资格审核状态修改为提交审核中【王建明 2015-10-28 9:40】
        TransResource transResource = transResourceService.getTransResource(transResourceApply.getResourceId());
        if(transResource.getQualificationType().equals(Constants.QualificationType.PRE_TRIAL)){
            transResourceApply.setTrialType(Constants.TrialType.COMMIT_TO_TRIAL);
        }
        transResourceApplyService.saveTransResourceApply(transResourceApply);

        //更新已申请人数
        List<TransResourceApply> transResourceApplyCommitList = transResourceApplyService.getCommitTransResourceApply(transResourceApply.getResourceId());
        transResource.setEnrolledNum(transResourceApplyCommitList.size());
        
        //如果竞买的地块是资格前审，更新待审核人数
        if(transResource.getQualificationType().equals(Constants.QualificationType.PRE_TRIAL)){
        	List<TransResourceApply> transResourceApplyCommitToList = transResourceApplyService.getCommitToTransResourceApply(transResourceApply.getResourceId());
        	transResource.setPendingTrialNum(transResourceApplyCommitToList.size());
        }
        transResourceService.saveTransResource(transResource);
        transUserApplyInfoService.saveTransUserApplyInfo(transUserApplyInfo);
        return "redirect:/console/apply-bzj?id="+transResourceApply.getResourceId();

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
        TransResource transResource= transResourceService.getTransResource(id);
        model.addAttribute("transResource", transResource);

        String userId= SecUtil.getLoginUserId();
        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId, id);
        model.addAttribute("transResourceApply", transResourceApply);
        
        //判断保证金缴纳是否结束，如果已结束，则不能再缴纳
        Date bzjEndTime = transResource.getBzjEndTime();
        Date cDate= Calendar.getInstance().getTime();
        if(bzjEndTime != null && cDate.after(bzjEndTime)){
            return "redirect:/view?id="+id;
        }
        
        // 已提交资格审核的，跳转到资格审核中的提示页面【王建明 2015-10-28 9:44】
        if(transResourceApply.getTrialType().equals(Constants.TrialType.COMMIT_TO_TRIAL)){
            return  "/console/apply-trial-ing";
        }

        // 资格审核未通过的，跳转到资格审核未通过提示页面【邵可 2015-12-17 16:07】
        if(transResourceApply.getTrialType().equals(Constants.TrialType.FAILED_TRIAL)){
            return  "/console/apply-trial-failed";
        }
        
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
        model.addAttribute("bank", getBank(transResource.getRegionCode(), transResourceApply.getBankCode(),
                transResourceApply.getMoneyUnit()));
        model.addAttribute("transBankAccount",transBankAccount);
        return  "/console/apply-bzj";
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
}
