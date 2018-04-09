package cn.gtmap.landsale.client.web;


import cn.gtmap.landsale.client.register.*;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 地块报名
 * @author zsj
 * @version v1.0, 2017/11/27
 */
@Controller
@RequestMapping("/resourceApply")
public class ResourceApplyController {

    Logger logger = LoggerFactory.getLogger(ResourceApplyController.class);


    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransJmrClient jmrClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransUserUnionClient transUserUnionClient;

    @Autowired
    TransBankAccountClient transBankAccountClient;

    @Autowired
    TransUserApplyInfoClient transUserApplyInfoClient;

    @Autowired
    TransBankPayClient transBankPayClient;

    @Autowired
    TransBankClient transBankClient;

    @Autowired
    TransBankInterfaceClient transBankInterfaceClient;

    @Autowired
    AttachmentCategoryClient attachmentCategoryClient;

    @Autowired
    TransBuyQualifiedClient transBuyQualifiedClient;

    @Autowired
    CaSvsClient caSvsClient;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    /**
     * 报名申请 是否同意的页面
     *
     * @param resourceId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/apply")
    public String apply(String resourceId, Model model) throws Exception {
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        TransCrgg transCrgg = transCrggClient.getTransCrgg(transResource.getGgId());
        TransRegion transRegion = transRegionClient.getTransRegionByRegionCode(transResource.getRegionCode());

        String userId = SecUtil.getLoginUserId();
        TransUser transUser = transUserClient.getTransUserById(userId);
        model.addAttribute("transResource", transResource);
        model.addAttribute("transCrgg", transCrgg);
        model.addAttribute("transRegion", transRegion);
        if (transUser == null && caEnabled) {
            model.addAttribute("caEnabled", true);
        } else {
            model.addAttribute("caEnabled", false);
        }

        //判断是否当前用户是否参加了当前地块的联合竞买，如果是，则不能再次报名竞买
        List<TransUserUnion> transUserUnion = transUserUnionClient.getResourceTransUserUnionByUserName(SecUtil.getLoginUserViewName(), resourceId);
        if (transUserUnion != null && transUserUnion.size() > 0) {
            model.addAttribute("applyEnabled", false);
            model.addAttribute("msg", "当前用户已参加本地块的联合竞买，不能再次报名！");
        } else {
            model.addAttribute("applyEnabled", true);
        }
        return "/apply/apply";
    }

    /**
     * 报名通过 同意 跳转到选择银行信息
     *
     * @param req
     * @param resourceId
     * @param caSignerX
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/apply-over")
//    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_APPLY,producer = Constants.LogProducer.CLIENT,
//            description = "用户报名")
    public String applyOver(HttpServletRequest req, String resourceId, CaSignerX caSignerX, Model model) throws Exception {
        String userId = SecUtil.getLoginUserId();
        TransUser transUser = transUserClient.getTransUserById(userId);
        //如果登录用户在管理系统中不存在，则需要新建用户信息
        if (transUser == null) {
            if (caSvsClient.validateCertificate(caSignerX.getSxcertificate()).getFlag()) {
                transUser = new TransUser();
                transUser.setUserName(caSignerX.getCertFriendlyName());
                transUser.setViewName(caSignerX.getCertFriendlyName());
                transUser.setCaThumbprint(caSignerX.getCertThumbprint());
                transUser.setCaCertificate(caSignerX.getSxcertificate());
                transUser.setCaName(caSignerX.getCertFriendlyName());
                transUser.setType(Constants.UserType.CLIENT);
                transUser.setPassword(caSignerX.getCertFriendlyName());
                transUser = jmrClient.addJmr(transUser).getEmpty();
                userId = transUser.getUserId();
                SecUtil.setLoginUserToSession(req, transUser);
                SecUtil.setLoginUserToLocal(transUser, req);
            } else {
                throw new Exception("数字证书错误！");
            }
        }
        //再次判断是否当前用户是否参加了当前地块的联合竞买，如果是，则不能再次报名竞买
        List<TransUserUnion> transUserUnion = transUserUnionClient.getResourceTransUserUnionByUserName(transUser.getViewName(), resourceId);
        if (transUserUnion != null && transUserUnion.size() > 0) {
            return "redirect:/resourceApply/apply?resourceId=" + resourceId;
        }
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        if (transResourceApply == null) {
            transResourceApply = new TransResourceApply();
            transResourceApply.setUserId(userId);
            transResourceApply.setResourceId(resourceId);
            transResourceApply.setApplyDate(Calendar.getInstance().getTime());
        }
        if (transResourceApply.getApplyStep() < Constants.STEP_BAO_MING) {
            transResourceApply.setApplyStep(Constants.STEP_BAO_MING);
        }
        transResourceApplyClient.saveTransResourceApply(transResourceApply);
        return "redirect:/resourceApply/apply-bank?resourceId=" + resourceId;
    }

    /**
     * 选择竞买方式和银行
     *
     * @param resourceId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/apply-bank")
    public String bank(String resourceId, Model model) throws Exception {
        String userId = SecUtil.getLoginUserId();
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("transResource", transResource);
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        model.addAttribute("transResourceApply", transResourceApply);
        //银行
        List<TransBank> bankList = transBankClient.getBankListByRegion(transResource.getRegionCode());
        model.addAttribute("bankList", bankList);
        //竞买人
        List<TransUserApplyInfo> transUserApplyInfoList = transUserApplyInfoClient.getTransUserApplyInfoByUser(userId);
        if (transUserApplyInfoList != null && transUserApplyInfoList.size() > 0) {
            model.addAttribute("transUserApplyInfo", transUserApplyInfoList.get(0));
        } else {
            TransUserApplyInfo transUserApplyInfo = new TransUserApplyInfo();
            transUserApplyInfo.setUserId(userId);
            model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        }
        // 通过地块申请信息 查找审核表 是否存在审核未通过信息
        TransBuyQualified transBuyQualified = transBuyQualifiedClient.getTransBuyQualifiedForCurrent(transResourceApply.getApplyId());
        if (transBuyQualified != null) {
            model.addAttribute("transBuyQualified", transBuyQualified);
        }
        model.addAttribute("attachmentTypeList", attachmentCategoryClient.getTransResourceApplyAttachmentCategory());
        return "/apply/apply-bank";
    }

    @ModelAttribute("TransResourceApply")
    public TransResourceApply getTransResourceApply(@RequestParam(value = "resourceId", required = false) String resourceId) {
        String userId= SecUtil.getLoginUserId();
        return StringUtils.isBlank(resourceId) ? new TransResourceApply() :
                transResourceApplyClient.getTransResourceApplyByUserId(userId,resourceId);
    }

    /**
     * 选择银行信息
     * @param transResourceApply
     * @param transUserApplyInfo
     * @param ra
     * @return
     * @throws Exception
     */
    @RequestMapping("/apply-bank-over")
    public String applyBank(@ModelAttribute("TransResourceApply") TransResourceApply transResourceApply, TransUserApplyInfo transUserApplyInfo, RedirectAttributes ra) throws Exception {
        TransResource resource=transResourceClient.getTransResource(transResourceApply.getResourceId());
        // 申请时间在保证金缴纳时间之前
        if (transResourceApply.getApplyDate().before(resource.getBzjBeginTime())){
            return applyBankBeforeBzjTime(transResourceApply,transUserApplyInfo,ra);
        }else {
            return applyBankOver(transResourceApply,transUserApplyInfo,ra);
        }
    }

    /**
     * 到了保证金缴纳开始时间，则给账号(有前审的审核之后给账号，没前审的直接给账号)
     * @param transResourceApply
     * @param transUserApplyInfo
     * @param ra
     * @return
     * @throws Exception
     */
    private String applyBankOver(@ModelAttribute("TransResourceApply") TransResourceApply transResourceApply, TransUserApplyInfo transUserApplyInfo, RedirectAttributes ra) throws Exception {
        // 原为bankCode 新改为bankId
        if (StringUtils.isBlank(transResourceApply.getBankId())) {
            ra.addFlashAttribute("_msg", "请选择保证金交纳银行！");
            transResourceApplyClient.saveTransResourceApply(transResourceApply);
            return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
        }
        //判断是否联合竞买
        if (transResourceApply.getApplyType() == Constants.APPLY_TYPE_MULTI) {
            List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(transResourceApply.getApplyId());
            if (transUserUnionList.size() == 0) {
                ra.addFlashAttribute("_msg", "联合竞买人列表为空！");
                transResourceApplyClient.saveTransResourceApply(transResourceApply);
                return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
            } else {
                double scale = 0;
                //判断出资比例和是否同意
                for (TransUserUnion transUserUnion : transUserUnionList) {
                    scale = scale + transUserUnion.getAmountScale();
                    if (!transUserUnion.isAgree()) {
                        ra.addFlashAttribute("_msg", "被联合竞买人尚未同意，请被联合竞买人登录系统，同意本次联合竞买申请！");
                        transResourceApplyClient.saveTransResourceApply(transResourceApply);
                        return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
                    }
                }
                if (scale >= 100) {
                    ra.addFlashAttribute("_msg", "被联合竞买人出资比例总和必须小于100，余下的即为本次竞买申请人的出资比例！");
                    transResourceApplyClient.saveTransResourceApply(transResourceApply);
                    return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
                }
            }
        } else {
            List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(transResourceApply.getApplyId());
            for (TransUserUnion transUserUnion : transUserUnionList) {
                transUserUnionClient.deleteTransUserUnion(transUserUnion.getUnionId());
            }
        }
        //判断是否更换了银行
        TransResourceApply transResourceApplyOld = transResourceApplyClient.getTransResourceApply(transResourceApply.getApplyId());
        // 原为bankCode 新改为bankId
        if (StringUtils.isNotBlank(transResourceApplyOld.getBankId()) && !transResourceApplyOld.getBankId().equals(transResourceApply.getBankId())) {
            TransBankAccount transBankAccount = transBankAccountClient.createOrGetTransBankAccount(transResourceApply.getApplyId());
            List<TransBankPay> transBankPayList = transBankPayClient.getTransBankPaysByAccountId(transBankAccount.getAccountId());
            if (transBankPayList.size() > 0) {
                ra.addFlashAttribute("_msg", "该账户已收到保证金，不能重新选择银行！");
                return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
            } else {
                // 更换银行的时候清空现有的子账号
                transBankAccount.setAccountCode(null);
                transBankAccount.setApplyNo(transBankAccountClient.getNextApplyNo());   //重新生成一个竞买号
                transBankAccountClient.saveTransBankAccount(transBankAccount);
            }
        }
        TransResource transResource = transResourceClient.getTransResource(transResourceApply.getResourceId());
        transUserApplyInfo.setApplyId(transResourceApply.getApplyId());
        transUserApplyInfo = transUserApplyInfoClient.saveTransUserApplyInfo(transUserApplyInfo);
        transResourceApply.setInfoId(transUserApplyInfo.getInfoId());
        // 如果竞买的地块是资格前审，则资格审核状态修改为提交审核中
        if(Constants.Whether.YES.equals(transResource.getBeforeBzjAudit())){
            // 只有报名状态 1 才改为 待审核 2
            if ((transResourceApply.getApplyStep() == Constants.STEP_BAO_MING)) {
                transResourceApply.setApplyStep(Constants.STEP_QUALIFIED);
            }
            // 先根据地块申请表 查询是否已存在审核信息
            TransBuyQualified transBuyQualified = transBuyQualifiedClient.getTransBuyQualifiedForCurrent(transResourceApply.getApplyId());
            if (transBuyQualified != null) {
                // 判断当前审核信息是否为 未审核 或 已通过 跳过信息添加
                if (Constants.Qualified_Status.NONE.equals(transBuyQualified.getQualifiedStatus())
                        || Constants.Qualified_Status.PASS.equals(transBuyQualified.getQualifiedStatus())) {
                    transResourceApplyClient.saveTransResourceApply(transResourceApply);
                    return "redirect:/resourceApply/apply-bzj?resourceId=" + transResourceApply.getResourceId();
                } else {
                    // 审核未通过 修改当前状态
                    transBuyQualified.setCurrentStatus(Constants.Whether.NO);
                    transBuyQualifiedClient.saveTransBuyQualified(transBuyQualified);
                }

            }
            // 不存在信息 或审核未通过 新建审核信息
            transBuyQualified = new TransBuyQualified();
            transBuyQualified.setQualifiedStatus(Constants.Qualified_Status.NONE);
            transBuyQualified.setInfoId(transUserApplyInfo.getInfoId());
            transBuyQualified.setResourceId(transResource.getResourceId());
            transBuyQualified.setApplyId(transResourceApply.getApplyId());
            transBuyQualified.setCurrentStatus(Constants.Whether.YES);
            transBuyQualifiedClient.saveTransBuyQualified(transBuyQualified).getEmpty();
        // 不需要前审
        } else {
            // 只有报名状态 1 才改为 待审核 2 防止缴足保证金后 再次点击申请 重新修改状态
            if ((transResourceApply.getApplyStep() == Constants.STEP_BAO_MING)) {
                transResourceApply.setApplyStep(Constants.STEP_BAO_ZHENG_JIN);
            }
        }
        transResourceApply = transResourceApplyClient.saveTransResourceApply(transResourceApply).getEmpty();
        return "redirect:/resourceApply/apply-bzj?resourceId=" + transResourceApply.getResourceId();
    }


    /**
     * 保证金开始时间之前申请子账号，提示"保证金缴纳时间未开始！"
     * @param transResourceApply
     * @param transUserApplyInfo
     * @param ra
     * @return
     * @throws Exception
     */
    private String applyBankBeforeBzjTime(@ModelAttribute("TransResourceApply") TransResourceApply transResourceApply, TransUserApplyInfo transUserApplyInfo, RedirectAttributes ra) throws Exception {
        // 原为bankCode 新改为bankId
        if (StringUtils.isBlank(transResourceApply.getBankId())) {
            ra.addFlashAttribute("_msg", "请选择保证金交纳银行！");
            transResourceApplyClient.saveTransResourceApply(transResourceApply);
            return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
        }
        //判断是否联合竞买
        if (transResourceApply.getApplyType() == Constants.APPLY_TYPE_MULTI) {
            List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(transResourceApply.getApplyId());
            if (transUserUnionList.size() == 0) {
                ra.addFlashAttribute("_msg", "联合竞买人列表为空！");
                transResourceApplyClient.saveTransResourceApply(transResourceApply);
                return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
            } else {
                double scale = 0;
                //判断出资比例和是否同意
                for (TransUserUnion transUserUnion : transUserUnionList) {
                    scale = scale + transUserUnion.getAmountScale();
                    if (!transUserUnion.isAgree()) {
                        ra.addFlashAttribute("_msg", "被联合竞买人尚未同意，请被联合竞买人登录系统，同意本次联合竞买申请！");
                        transResourceApplyClient.saveTransResourceApply(transResourceApply);
                        return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
                    }
                }
                if (scale >= 100) {
                    ra.addFlashAttribute("_msg", "被联合竞买人出资比例总和必须小于100，余下的即为本次竞买申请人的出资比例！");
                    transResourceApplyClient.saveTransResourceApply(transResourceApply);
                    return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
                }
            }
        } else {
            List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(transResourceApply.getApplyId());
            for (TransUserUnion transUserUnion : transUserUnionList) {
                transUserUnionClient.deleteTransUserUnion(transUserUnion.getUnionId());
            }
        }
        //判断是否更换了银行
        TransResourceApply transResourceApplyOld = transResourceApplyClient.getTransResourceApply(transResourceApply.getApplyId());
        // 原为bankCode 新改为bankId
        if (StringUtils.isNotBlank(transResourceApplyOld.getBankId()) && !transResourceApplyOld.getBankId().equals(transResourceApply.getBankId())) {
            TransBankAccount transBankAccount = transBankAccountClient.createOrGetTransBankAccount(transResourceApply.getApplyId());
            List<TransBankPay> transBankPayList = transBankPayClient.getTransBankPaysByAccountId(transBankAccount.getAccountId());
            if (transBankPayList.size() > 0) {
                ra.addFlashAttribute("_msg", "该账户已收到保证金，不能重新选择银行！");
                return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
            } else {
                // 更换银行的时候清空现有的子账号
                transBankAccount.setAccountCode(null);
                transBankAccount.setApplyNo(transBankAccountClient.getNextApplyNo());   //重新生成一个竞买号
                transBankAccountClient.saveTransBankAccount(transBankAccount);
            }
        }
        TransResource transResource = transResourceClient.getTransResource(transResourceApply.getResourceId());
        transUserApplyInfo.setApplyId(transResourceApply.getApplyId());
        transUserApplyInfo = transUserApplyInfoClient.saveTransUserApplyInfo(transUserApplyInfo);
        transResourceApply.setInfoId(transUserApplyInfo.getInfoId());
        transResourceApply = transResourceApplyClient.saveTransResourceApply(transResourceApply).getEmpty();
        ra.addFlashAttribute("_msg", "保证金缴纳时间未开始，请等待！");
        return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
    }


    /**
     * 申请保证金子账号
     *
     * @param resourceId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/apply-bzj")
    public String bzj(@RequestParam(value = "resourceId") String resourceId, Model model) throws Exception {
        String userId = SecUtil.getLoginUserId();
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("transResource", transResource);
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        //地块有前审
        if (Constants.Whether.YES.equals(transResource.getBeforeBzjAudit())) {
            //查询前审表是否有该用户报名
            TransBuyQualified transBuyQualified = transBuyQualifiedClient.getTransBuyQualifiedForCurrent(transResourceApply.getApplyId());
            if (transBuyQualified != null) {
                //审核状态 0:未审核   1:通过   2:被强制退回
                if (transBuyQualified.getQualifiedStatus() == 0) {
                    model.addAttribute("transResource", transResource);
                    return "/apply/apply-trial-ing";
                } else if (transBuyQualified.getQualifiedStatus() == 2) {
                    //审核未通过被强制退回，apply-bank页面带有未通过原因
                    model.addAttribute("_msg", transBuyQualified.getQualifiedReason());
                    return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
                }
            } else {
                // 前审 但是前审表没有数据 基本不可能发生 如果发生就是系统问题
                model.addAttribute("_msg", "审核信息出错联系管理员");
                return "redirect:/resourceApply/apply-bank?resourceId=" + transResourceApply.getResourceId();
            }
        }
        TransBankAccount transBankAccount = transBankAccountClient.createOrGetTransBankAccount(transResourceApply.getApplyId());
        // 子账号为空 申请子账号
        if (StringUtils.isBlank(transBankAccount.getAccountCode())) {
            try {
                ResponseMessage responseMessage = transBankInterfaceClient.sendBankApplyAccount(transResourceApply);
                if ("00".equals(responseMessage.getCode())) {
                    Document doc = DocumentHelper.parseText(responseMessage.getMessage());
                    transBankAccount = bankApplyAccountReuslt(doc);
                } else {
                    model.addAttribute("_msg", "保证金帐号无法生成请重新刷新页面或联系管理员！");
                }
            } catch (Exception ex) {
                model.addAttribute("_msg", "保证金帐号无法生成请重新刷新页面或联系管理员！");
            }
        }
        //找到开户银行的信息
        model.addAttribute("bank", transBankClient.getBankById(transResourceApply.getBankId()));
        model.addAttribute("transBankAccount", transBankAccount);
        model.addAttribute("transResourceApply", transResourceApply);
        return "/apply/apply-bzj";
    }

    private TransBankAccount bankApplyAccountReuslt(Document doc) {
        String accountCode = getElementValue(doc, "//body/AcctNo");
        //判断该银行账户是否正在使用
        TransBankAccount bankAccount = transBankAccountClient.getTransBankAccountByAccountCode(accountCode);
        //拿到重复的保证金账号做一下对比，过期保证金交纳点的账号立马被释放，预防特殊情况：到报价阶段的地块终止还没释放子账号 liushaoshuai【liushaoshuai@gtmap.cn】2017/5/5 14:54
        try {
            if (bankAccount != null) {
                TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(bankAccount.getApplyId());
                TransResource transResource = transResourceClient.getTransResource(transResourceApply.getResourceId());
                if (transResource.getBzjEndTime().before(new Date())) {
                    bankAccount.setClose(true);
                    transBankAccountClient.saveTransBankAccount(bankAccount);
                    bankAccount = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TransBankAccount transBankAccount = transBankAccountClient.getTransBankAccountByApplyNo(getElementValue(doc, "//head/SeqNo"));
        if (bankAccount == null) {
            transBankAccount.setAccountCode(getElementValue(doc, "//body/AcctNo"));
            transBankAccount.setReuslt(getElementValue(doc, "//body/Result"));
            transBankAccount.setReceiveDate(Calendar.getInstance().getTime());
            return transBankAccountClient.saveTransBankAccount(transBankAccount);
        } else {
            logger.error("----银行帐号正在使用！" + bankAccount);
            return transBankAccount;
        }
    }

    private String getElementValue(Document doc, String path) {
        Element element = (Element) doc.selectSingleNode(path);
        if (element != null) {
            return element.getTextTrim();
        } else {
            return null;
        }
    }

}
