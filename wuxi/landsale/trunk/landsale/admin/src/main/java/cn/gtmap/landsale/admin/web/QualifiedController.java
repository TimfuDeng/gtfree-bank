package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.admin.register.*;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.register.TransUserUnionClient;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Maps;
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

/**
* 申购资格审核
* Created by trr on 2015/10/13.
*/
@Controller
@RequestMapping(value = "/qualified")
public class QualifiedController extends BaseController {
    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    TransUserApplyInfoClient transUserApplyInfoClient;

    @Autowired
    TransUserUnionClient transUserUnionClient;

    @Autowired
    TransBuyQualifiedClient transBuyQualifiedClient;

    @Autowired
    TransMaterialClient transMaterialClient;


    @RequestMapping("/index")
    public String releaseLandQualified(@PageDefault(value = 5) Pageable page, String title, Model model) {
        int resourceEditStatus = -1;
        int resourceStatus = 2;//默认查已发布的地块
        String regions = null;
        if (!SecUtil.isAdmin()) {
            regions = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResourcePage = transResourceClient.findTransResourcesByEditStatus(title, resourceStatus, null, regions, page);
        transResourcePage = transResourceClient.countPassOrUnpass(transResourcePage);

        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("title", title);
        return "qualified/qualified-land-list";
    }


    @RequestMapping("/resourceApply")
    public String resourceApplyQualified(String resourceId, Model model) {
        List<TransResourceApply> transResourceApplyList = transResourceApplyClient.getTransResourceApplyByResourceId(resourceId);
        if (transResourceApplyList.size() > 0) {
            for (TransResourceApply transResourceApply : transResourceApplyList) {
                if (StringUtils.isNotBlank(transResourceApply.getInfoId())) {
                    TransUserApplyInfo transUserApplyInfo = transUserApplyInfoClient.getTransUserApplyInfo(transResourceApply.getInfoId());
                    transResourceApply.setTransUserApplyInfo(transUserApplyInfo);
                }
            }
        }
        model.addAttribute("transResourceApplyList", transResourceApplyList);
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("transResource", transResource);
        return "qualified/qualified-list";
    }


    /**
     * 资格审查
     *
     * @param applyId
     * @param model
     * @return
     */
    @RequestMapping("verify")
    public String verifyApplyQualified(String applyId, String resourceId, Model model, RedirectAttributes ra) {
        //z申购申请id得到申购申请
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(applyId);
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        //申购申请里面的申请人id得到申请人信息
        TransUserApplyInfo transUserApplyInfo = transUserApplyInfoClient.getTransUserApplyInfo(transResourceApply.getInfoId());
        //申购人账号信息
        TransUser transUser = transUserClient.getTransUserById(transUserApplyInfo.getUserId());
        //根据币种得到对应支持银行
//        List<TransBank>  bankList=transBankService.getBankListByMoneyUnitRegion(transResourceApply.getMoneyUnit(), transResource.getRegionCode());
        //得到联合竞买者的信息
        List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(applyId);
        TransBuyQualified transBuyQualified = transBuyQualifiedClient.getTransBuyQualifiedForCurrent(applyId);
        transBuyQualified.setQualifiedTime(new Date());
        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transResourceApply", transResourceApply);
        model.addAttribute("transBuyQualified", transBuyQualified);
        model.addAttribute("transUser", transUser);
//        model.addAttribute("bankList",bankList);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("applyId", applyId);
        model.addAttribute("transUserUnionList", transUserUnionList);

        return "qualified/qualified-verify";
    }


    @RequestMapping("listQualified")
    public String listQualified(String applyId, Model model) {
        //z申购申请id得到申购申请
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(applyId);
        //申购人账号信息
        TransUser transUser = transUserClient.getTransUserById(transResourceApply.getUserId());
        List<TransBuyQualified> transBuyQualifiedList = transBuyQualifiedClient.getListTransBuyQualifiedByApplyId(applyId);
        model.addAttribute("transResourceApply", transResourceApply);
        model.addAttribute("transBuyQualifiedList", transBuyQualifiedList);
        model.addAttribute("transUser", transUser);

        return "qualified/qualified-verify-list";
    }

    /**
     * 审核记录的详情
     *
     * @param qualifiedId
     * @param applyId
     * @param model
     * @return
     */
    @RequestMapping("verify/view")
    public String viewApplyQualified(String qualifiedId, String applyId, Model model) {

        //z申购申请id得到申购申请
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(applyId);
        //申购申请里面的申请人id得到申请人信息
        TransUserApplyInfo transUserApplyInfo = transUserApplyInfoClient.getTransUserApplyInfo(transResourceApply.getInfoId());
        //申购人账号信息
        TransUser transUser = transUserClient.getTransUserById(transUserApplyInfo.getUserId());
        //根据币种得到对应支持银行
//        List<TransBank>  bankList=transBankService.getBankList(transResourceApply.getMoneyUnit());
        TransBuyQualified transBuyQualified = transBuyQualifiedClient.getTransBuyQualifiedById(qualifiedId);
        //得到联合竞买者的信息
        List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(applyId);
        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transResourceApply", transResourceApply);
        model.addAttribute("transBuyQualified", transBuyQualified);
        model.addAttribute("transUser", transUser);
        model.addAttribute("transUserUnionList", transUserUnionList);
//        model.addAttribute("bankList",bankList);
        model.addAttribute("resourceId", transResourceApply.getResourceId());
        return "qualified/qualified-view";
    }

    @ModelAttribute("TransBuyQualified")
    public TransBuyQualified getTransBuyQualified(@RequestParam(value = "qualifiedId", required = false) String qualifiedId) {
        String userId= SecUtil.getLoginUserId();
        return StringUtils.isBlank(qualifiedId) ? new TransBuyQualified() :
                transBuyQualifiedClient.getTransBuyQualifiedById(qualifiedId);
    }

    /**
     * 保存资格审查
     * @param
     * @return
     */
    @RequestMapping("verify/save")
    @ResponseBody
    public ResponseMessage<TransBuyQualified> saveVerifyApplyQualified(@ModelAttribute("TransBuyQualified") TransBuyQualified transBuyQualified) {
        //审核状态是1 通过 同时地块申请状态改为 缴纳保证金 3
        if (1 == transBuyQualified.getQualifiedStatus()) {
            TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(transBuyQualified.getApplyId());
            transResourceApply.setApplyStep(Constants.STEP_BAO_ZHENG_JIN);
            transResourceApplyClient.saveTransResourceApply(transResourceApply);

            return transBuyQualifiedClient.saveTransBuyQualified(transBuyQualified);
        }
        //审核未通过并且审核意见不为空===》保存，同时 状态为审核失败
        if (transBuyQualified.getQualifiedStatus() == 2 && StringUtils.isNotBlank(transBuyQualified.getQualifiedReason())) {
            TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(transBuyQualified.getApplyId());
            transResourceApply.setApplyStep(Constants.STEP_QUALIFIED_FAILE);
            transResourceApplyClient.saveTransResourceApply(transResourceApply);

            return transBuyQualifiedClient.saveTransBuyQualified(transBuyQualified);
        }
        return new ResponseMessage(true,"操作成功！");
    }

    /**
     * 查看附件材料
     *
     * @param resourceId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("attachment")
    public String attachments(@RequestParam(value = "resourceId", required = true) String resourceId, @RequestParam(value = "userId", required = true) String userId, Model model) {
        TransResourceApply transResourceApply =
                transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        TransUser transUser = transUserClient.getTransUserById(userId);
        TransUserApplyInfo transUserApplyInfo = transUserApplyInfoClient.getTransUserApplyInfo(transResourceApply.getInfoId());
        List<TransMaterial> transMaterials = transMaterialClient.getMaterialsByRegionCode(transResourceClient.getTransResource(resourceId).getRegionCode());
        Map<String, String> map = Maps.newHashMap();
        for (TransMaterial transMaterial : transMaterials) {
            map.put(transMaterial.getMaterialCode(), transMaterial.getMaterialName());
        }
        model.addAttribute("transResourceApply", transResourceApply);
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transUser", transUser);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("attachmentTypeList", map);
        return "qualified/resource-apply-attachments";
    }

    @RequestMapping("/list/scale")
    public String getUserScale(String applyId,Model model){
        List<TransUserUnion> transUserUnions = null;
        double scale = 0;//所有被联合人的出资比例
        if(StringUtils.isNotBlank(applyId)) {
            transUserUnions = transUserUnionClient.findTransUserUnion(applyId);
        }
        for(TransUserUnion transUserUnion:transUserUnions){
            scale += transUserUnion.getAmountScale();
        }
        model.addAttribute("scale",scale);
        model.addAttribute("transUserUnions",transUserUnions);
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApply(applyId);
        model.addAttribute("resourceId", transResourceApply.getResourceId());
        return "qualified/qualified-scale-list";
    }

    /**
     * 删除报名 及相关信息
     * @param applyId
     * @return
     */
    @RequestMapping("/deleteApply")
    @ResponseBody
    public ResponseMessage deleteApply(String applyId) {
        return transResourceApplyClient.deleteApply(applyId);
    }


}

