package cn.gtmap.landsale.client.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.client.register.*;
import cn.gtmap.landsale.client.util.RMBUtils;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/**
 * @author cxm
 * @version v1.0, 2017/12/11
 */
@Controller
@RequestMapping("/my")
public class MyResourceController {

    @Autowired
    TransUserUnionClient transUserUnionClient;

    @Autowired
    TransUserApplyInfoClient transUserApplyInfoClient;

    @Autowired
    TransMaterialClient transMaterialClient;

   @Autowired
    TransResourceApplyClient transResourceApplyClient;

   @Autowired
    TransResourceClient transResourceClient;

   @Autowired
    TransBankAccountClient transBankAccountClient;

   @Autowired
    TransBankPayClient transBankPayClient;

   @Autowired
    TransBankClient transBankClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransBuyQualifiedClient transBuyQualifiedClient;

    @Autowired
    TransOrganizeClient transOrganizeClient;

    @Autowired
    TransResourceInfoClient transResourceInfoClient;

    @Autowired
    TransFileClient transFileClient;

    @Autowired
    AttachmentCategoryClient attachmentCategoryClient;

    @Autowired
    OneTargetClient oneTargetClient;

    @Autowired
    YHClient yhClient;

    @Autowired
    org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    ServletContext servletContext;

    @Autowired
    TransResourceSonClient transResourceSonClient;



    /**
     * 我的交易
     *
     * @param regionCode
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/menu")
    public String myMenu(String regionCode, Model model) throws Exception {
        model.addAttribute("regionCode", regionCode);
        return "my/menu";
    }

    /**
     * 参与地块
     *
     * @param regionCode
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/resource/index")
    public String myIndex(String regionCode, Model model) throws Exception {
        model.addAttribute("regionCode", regionCode);
        return "my/resource";
    }

    @RequestMapping("/resource/content")
    public String myContent(ResourceQueryParam param, @PageDefault(value = 12) Pageable page, String regionCode, ModelMap model) throws Exception {
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_PRE_RELEASE);
        if (param.getResourceStatus() == Constants.RESOURCE_STATUS_CHENG_JIAO) {
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.RESOURCE_STATUS_GONG_GAO);
        }
        param.setPage(page);
        // 当前登录用户
        String userId = SecUtil.getLoginUserId();
        Page<TransResource> transResourcePage = transResourceClient.findTransResourcesByUser(param, userId, regionCode);
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("param", param);
        model.addAttribute("regionCode", regionCode);
        return "my/resource-content";
    }

    @RequestMapping("/resource/view")
    public String view(String resourceId, ModelMap model) throws Exception {

        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("resource", transResource);
        TransOrganize transOrganize = transOrganizeClient.getTransOrganizeById(transResource.getOrganizeId());
        model.addAttribute("transOrganize", transOrganize);
        TransResourceInfo transResourceInfo = transResourceInfoClient.getTransResourceInfoByResourceId(resourceId);
        if (transResourceInfo == null) {
            transResourceInfo = new TransResourceInfo();
        }
        model.addAttribute("resourceInfo", transResourceInfo);
        //获得报价列表（15条）
        Page<TransResourceOffer> resourceOffers = transResourceOfferClient.getResourceOfferPage(resourceId, new PageRequest(0, 10));
        model.addAttribute("resourceOffers", resourceOffers);
        model.addAttribute("cDate", Calendar.getInstance().getTime().getTime());
        //获得当前最高报价+
        TransResourceOffer maxOffer = (resourceOffers.getItems().size() > 0) ? resourceOffers.getItems().get(0) : null;
        model.addAttribute("maxOffer", maxOffer);
        //当前总价的最高报价,不包括多指标
//        TransResourceOffer maxOfferPrice = transResourceOfferClient.getMaxOfferFormPrice(resourceId);
//        model.addAttribute("maxOfferPrice", maxOfferPrice);
        // 当前用户
        model.addAttribute("userId", SecUtil.getLoginUserId());
        // 当前用户报名信息
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(SecUtil.getLoginUserId(), resourceId);
        model.addAttribute("transResourceApply", transResourceApply);
        if (Constants.Whether.YES.equals(transResource.getBeforeBzjAudit())) {
            // 报名审核信息
            TransBuyQualified transBuyQualified = transBuyQualifiedClient.getTransBuyQualifiedForCurrent(transResourceApply.getApplyId());
            model.addAttribute("transBuyQualified", transBuyQualified);
        }
        TransResourceOffer successOffer = null;
        if(null != transResource.getOfferId()){
            successOffer = transResourceOfferClient.getTransResourceOffer(transResource.getOfferId());
        }
        // 判断成交
        if (Constants.RESOURCE_STATUS_CHENG_JIAO == transResource.getResourceStatus()) {
            // 判断是否最高限价
            if ((Constants.Whether.YES.equals(transResource.getMaxOfferExist()))
                    && (transResource.getMaxOffer().compareTo(maxOffer.getOfferPrice()) <= 0)) {
                // 判断最高限价后的成交方式
                if (Constants.MaxOfferChoose.YCBJ.getCode() == transResource.getMaxOfferChoose().getCode()) {
                    OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(resourceId);
                    model.addAttribute("oneTarget", oneTarget);
                } else if (Constants.MaxOfferChoose.YH.getCode() == transResource.getMaxOfferChoose().getCode()) {
                    YHResult yhResult = yhClient.getYHResultByResourceId(resourceId);
                    model.addAttribute("yhResult", yhResult);
                } else {
                    // 暂无
                }
            }
        }
        model.addAttribute("successOffer", successOffer);
        model.addAttribute("ggNum", StringUtils.isNotBlank(transResource.getGgId()) ? transCrggClient.getTransCrgg(transResource.getGgId()).getGgNum() : "");
        model.addAttribute("attachments", transFileClient.getTransFileAttachments(resourceId));
        Map attachmentCategory = attachmentCategoryClient.getTransResourceAttachmentCategory();
        attachmentCategory.put(Constants.FileType.QT.getCode(), Constants.FileType.QT.toString());
        model.addAttribute("attachmentCategory", attachmentCategory);
        model.addAttribute("crggAttachments", transFileClient.getTransFileAttachments(transResource.getGgId()));
        // 地块多用途信息
        List<TransResourceSon> transResourceSonList = transResourceSonClient.getTransResourceSonList(resourceId);
        model.addAttribute("transResourceSonList", transResourceSonList);
        return "my/resource-view";
    }

    /**
     * 地块的中止、终止和成交公告加载
     * @param resourceId
     * @return
     */
    @RequestMapping("/resourceNotice")
    public String resourceNotice(String resourceId,Model model){
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        List noticeList = transResourceClient.resourceNotice(transResource.getResourceId());
        if(noticeList == null) {
            noticeList = new ArrayList();
        }
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("transResource",transResource);
        return "common/resource-noticeList";
    }

    /**
     * 我参与的联合竞买
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/union-list")
    public String unionList(@PageDefault(value=5) Pageable page, Model model){
        String userId = SecUtil.getLoginUserId();
        String viewName = transUserClient.getTransUserById(userId).getViewName();
        Page<TransUserUnion> transUserUnionPage = transUserUnionClient.findTransUserUnionByUserName(viewName, page);
        model.addAttribute("transUserUnionPage", transUserUnionPage);
        return "/my/union-list";
    }

    @RequestMapping("/resource-list")
    public String index(ResourceQueryParam param, @PageDefault(value=5) Pageable page, Model model) throws Exception{
        String userId=SecUtil.getLoginUserId();
        Page<TransResourceApply>  transResourceApplyPage=
                transResourceApplyClient.getTransResourceApplyPageByUserId(userId, page);
        for (TransResourceApply resourceApply:transResourceApplyPage.getItems()){
            //判断是否足额缴纳保证金
            TransBankAccount transBankAccount =transBankAccountClient.getTransBankAccountByApplyId(resourceApply.getApplyId());
            TransResource transResource=
                    transResourceClient.getTransResource(resourceApply.getResourceId());
            if(null!=transBankAccount&&null!=transResource){
                List<TransBankPay> transBankPayList=
                        transBankPayClient.getTransBankPaysByAccountId(transBankAccount.getAccountId());
                double amount=0;
                for(TransBankPay bankPay: transBankPayList){
                    if(bankPay.getPayTime().before(transResource.getBzjEndTime())) {
                        amount += bankPay.getAmount();
                    }
                }
                Double resourceFixedOffer = 0.0;
                if ("USD".equals(resourceApply.getMoneyUnit())){
                    if(null!=transResource.getFixedOfferUsd()) {
                        resourceFixedOffer = transResource.getFixedOfferUsd();
                    }
                }else if ("HKD".equals(resourceApply.getMoneyUnit())){
                    if(null!=transResource.getFixedOfferHkd()) {
                        resourceFixedOffer = transResource.getFixedOfferHkd();
                    }
                }else{
                    resourceFixedOffer=transResource.getFixedOffer();
                }
                if (amount>=0) {
                    resourceApply.setFixedOfferEnough(true);
                }
            }
        }
        model.addAttribute("transResourceApplyPage", transResourceApplyPage);
        return "/my/resource-list";
    }

    /**
     * 查询特定地块的缴款情况
     * @param resourceId
     * @param model
     * @return
     */
    @RequestMapping("/pay-list")
    public String payList(String resourceId,Model model){
        String userId=SecUtil.getLoginUserId();
        TransResource transResource=
                transResourceClient.getTransResource(resourceId);
        TransResourceApply transResourceApply=
                transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        if (transResourceApply!=null) {
            TransBankAccount transBankAccount =
                    transBankAccountClient.getTransBankAccountByApplyId(transResourceApply.getApplyId());
            model.addAttribute("transBankAccount",transBankAccount);
            //找到开户银行的信息
            model.addAttribute("bank", getBank(transResource.getRegionCode(), transResourceApply.getBankId(),
                    transResourceApply.getMoneyUnit()));
            if (transBankAccount != null) {
                String accountCode= transBankAccount.getAccountCode();
                if(StringUtils.isNotBlank(accountCode)){
                    List<TransBankPay> transBankPayList=
                            transBankPayClient.getTransBankPaysByAccountId(transBankAccount.getAccountId());
                    model.addAttribute("transBankPayList",transBankPayList);
                }
            }
        }
        model.addAttribute("transResource",transResource);
        model.addAttribute("transResourceApply",transResourceApply);
        return "/my/pay-list";
    }

    /**
     * 已报名已缴纳保证金用户的报价列表
     * @param resourceId
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/offer-list")
    public String offerList(String resourceId,@PageDefault(value=15) Pageable page,Model model){
        String userId=SecUtil.getLoginUserId();
        TransResource transResource=
                transResourceClient.getTransResource(resourceId);
        Page<TransResourceOffer> transResourceOfferPage=
                transResourceOfferClient.getResourceOfferPageByUserId(resourceId, userId, page);
        model.addAttribute("transResource",transResource);
        model.addAttribute("transResourceOfferPage",transResourceOfferPage);
        return "/my/offer-list";
    }


    /**
     * 查看附件材料
     *
     * @param resourceId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping("/attachment")
    public String attachments(@RequestParam(value = "resourceId", required = true) String resourceId, @RequestParam(value = "userId", required = true) String userId, Model model) {
        TransResourceApply transResourceApply =
                transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        TransUser transUser = transUserClient.getTransUserById(userId);
        if (transResourceApply.getInfoId()!=null && transResourceApply.getInfoId()!=""){
            TransUserApplyInfo transUserApplyInfo = transUserApplyInfoClient.getTransUserApplyInfo(transResourceApply.getInfoId());
            model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        }
        List<TransMaterial> transMaterials = transMaterialClient.getMaterialsByRegionCode(transResourceClient.getTransResource(resourceId).getRegionCode());
        Map<String, String> map = Maps.newHashMap();
        for (TransMaterial transMaterial : transMaterials) {
            map.put(transMaterial.getMaterialCode(), transMaterial.getMaterialName());
        }
        model.addAttribute("transResourceApply", transResourceApply);
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("transUser", transUser);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("attachmentTypeList", map);
        return "/my/attachment-list";
    }



    /**
     * 下载竞买申请书
     * @param resourceId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/jmsqs.f")
    public void getJmsqs(String resourceId, HttpServletResponse response) throws Exception {
        TransResource resource = transResourceClient.getTransResource(resourceId);
        TransCrgg transCrgg = transCrggClient.getTransCrgg(resource.getGgId());
        Map params = Maps.newHashMap();
        TransRegion region=transRegionClient.getTransRegionByRegionCode(resource.getRegionCode());
        params.put("regionName",region.getRegionName());
        params.put("resource", resource);
        params.put("resourceCode", resource.getResourceCode());
        params.put("ggNum", transCrgg.getGgNum());
        params.put("userName", SecUtil.getLoginUserViewName());
        params.put("currentDate", Calendar.getInstance().getTime());
        StringBuilder fileName = new StringBuilder();
        fileName.append(resource.getResourceCode());
        fileName.append("竞买申请书.doc");
        msWordTpl2Response(getTplPath(resourceId,"jmsqs.ftl"), fileName.toString(), params, response);
    }


    @RequestMapping("offersuccess")
    public String offersuccess(String resourceId,Model model){
        TransResource resource= transResourceClient.getTransResource(resourceId);
        TransResourceOffer offer=transResourceOfferClient.getTransResourceOffer(resource.getOfferId());
        TransResourceOffer offerPrice=transResourceOfferClient.getMaxOfferFormPrice(resourceId);
        TransUser user=transUserClient.getTransUserById(offer.getUserId());
        TransCrgg transCrgg = transCrggClient.getTransCrgg(resource.getGgId());
        String userId=SecUtil.getLoginUserId();
        TransResourceApply transResourceApply= transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        TransRegion region=transRegionClient.getTransRegionByRegionCode(resource.getRegionCode());
        model.addAttribute("regionName",region.getRegionName());
        model.addAttribute("resource",resource);
        model.addAttribute("offer",offer);
        model.addAttribute("offerPrice",offerPrice);
        model.addAttribute("user",user);
        model.addAttribute("crgg",transCrgg);
        model.addAttribute("resourceApply",transResourceApply);
        return "/my/offersuccess";
    }

    @RequestMapping("offersuccess/download.f")
    public void getFile(String resourceId, HttpServletResponse response) throws Exception {
        TransResource resource = transResourceClient.getTransResource(resourceId);
        TransResourceOffer offer = transResourceOfferClient.getTransResourceOffer(resource.getOfferId());
        TransResourceOffer offerPrice = transResourceOfferClient.getMaxOfferFormPrice(resourceId);
        TransUser user = transUserClient.getTransUserById(offer.getUserId());
        TransCrgg transCrgg = transCrggClient.getTransCrgg(resource.getGgId());
        //行政区是地块所在的行政区（竞买人可能跨区域购买）
        Map params = Maps.newHashMap();
        TransRegion region=transRegionClient.getTransRegionByRegionCode(resource.getRegionCode());
        params.put("regionName",region.getRegionName());
        Calendar gpBeginCalendar = Calendar.getInstance();
        gpBeginCalendar.setTime(resource.getGpBeginTime());
        Calendar gpEndCalendar = Calendar.getInstance();
        gpEndCalendar.setTime(resource.getGpEndTime());
        Calendar resourceOverCalendar = Calendar.getInstance();
        resourceOverCalendar.setTime(resource.getOverTime());

        String userId=SecUtil.getLoginUserId();
        TransResourceApply transResourceApply= transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);

        params.put("resource", resource);
        params.put("resourceCode", resource.getResourceCode());
        params.put("offerPrice", offerPrice.getOfferPrice() * 10000);
        params.put("offerPriceLocal", RMBUtils.toBigAmt(offerPrice.getOfferPrice() * 10000));
        params.put("ggNum", transCrgg.getGgNum());
        params.put("user", user);
        params.put("offer", offer);
        params.put("resourceApply",transResourceApply);
        StringBuilder fileName = new StringBuilder();
        fileName.append(resource.getResourceCode());
        fileName.append("成交通知书.doc");
        msWordTpl2Response(getTplPath(resourceId,"cjtzs.ftl"), fileName.toString(), params, response);
    }

    /**
     * @作者 王建明
     * @创建日期 2015/7/1
     * @创建时间 16:13
     * @描述 —— 成交失败后的退款说明书下载
     */
    @RequestMapping("offerfail.f")
    public void offerFail(String resourceId,HttpServletResponse response,Model model) throws Exception {
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        String userId = SecUtil.getLoginUserId();
        TransUser transUser = transUserClient.getTransUserById(userId);
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
        TransBankAccount transBankAccount = transBankAccountClient.createOrGetTransBankAccount(transResourceApply.getApplyId());
        List<TransBankPay> transBankPays = transBankPayClient.getTransBankPaysByAccountCodeAndAccountId(transBankAccount.getAccountCode(), transBankAccount.getAccountId());

        TransBankPay transBankPayTemp = new TransBankPay();
        transBankPayTemp.setPayTime(new Date());
        transBankPayTemp.setAmount(0);
        transBankPayTemp.setPayBankAccount("支付账户");
        transBankPayTemp.setPayName("支付单位");
        if (transBankPays != null) {
            for (TransBankPay transBankPay : transBankPays) {
                transBankPayTemp.setAmount(transBankPayTemp.getAmount() + transBankPay.getAmount());
                // 以下信息以最后一笔支付信息为准，无所谓，反正用户可以自己修改【王建明 2015/7/1 17:23】
                transBankPayTemp.setPayBankAccount(transBankPay.getPayBankAccount());
                transBankPayTemp.setPayName(transBankPay.getPayName());
                transBankPayTemp.setPayTime(transBankPay.getPayTime());
            }
        }

        Map params = Maps.newHashMap();
        TransRegion region=transRegionClient.getTransRegionByRegionCode(transResource.getRegionCode());
        params.put("regionName",region.getRegionName());
        params.put("transUser", transUser);
        params.put("transResource", transResource);
        params.put("transBankPay", transBankPayTemp);
        params.put("payTime", transBankPayTemp.getPayTime());
        params.put("currentDate", Calendar.getInstance().getTime());

        StringBuilder fileName = new StringBuilder();
        fileName.append(transResource.getResourceCode());
        fileName.append("保证金退款说明书.doc");
        msWordTpl2Response(getTplPath(resourceId,"offerfail.ftl"),fileName.toString(),params,response);
    }

    private TransBank getBank(String regionCode,String bankId,String moneyUnit){
        List<TransBank> bankList= transBankClient.getBankListByRegion(regionCode);
        for(TransBank transBank:bankList){
            if(bankId.equals(transBank.getBankId())){
                if(StringUtils.isBlank(moneyUnit) && "CNY".equals(transBank.getMoneyUnit())) {
                    return transBank;
                }else if(StringUtils.isNotBlank(moneyUnit) && transBank.getMoneyUnit().equals(moneyUnit)  ){
                    return transBank;
                }
            }
        }
        return null;
    }

    /**
     * 将msword的模板写入到response中，实现word文件导出功能
     * @param ftlName 模板文件名称，带相对路径，例如/views/my/offerfail.ftl
     * @param fileName 导出word文件名称
     * @param dataModel freemarker模板数据模型
     * @param response
     * @throws Exception
     */
    private void msWordTpl2Response(String ftlName,String fileName,Object dataModel,HttpServletResponse response) throws Exception {
        OutputStream outputStream = null;
        Writer bufferedWriter = null;
        try {
            response.reset();
            response.setContentType("application/msword; charset="+ Charsets.UTF8);
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(Charsets.CHARSET_GBK), Charsets.CHARSET_ISO88591));
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName,Charsets.UTF8);
            outputStream = response.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,Charsets.CHARSET_UTF8));
            template.process(dataModel, bufferedWriter);
            outputStream.flush();
        } finally {
            if(bufferedWriter!=null) {
                bufferedWriter.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }

    private String getTplPath(String resourceId,String tplName){
        //获取当前登录用户的行政区
        TransResource resource = transResourceClient.getTransResource(resourceId);
        TransRegion region=transRegionClient.getTransRegionByRegionCode(resource.getRegionCode());
        String regionTag = region.getRegionCode();
        String tplPath = "/material/"+regionTag+"/"+tplName;
        String basePath = servletContext.getRealPath("/")+"templates";
        if(new File(basePath+tplPath).exists()) {
            return tplPath;
        } else {
            return "/material/default/" + tplName;
        }
    }

}
