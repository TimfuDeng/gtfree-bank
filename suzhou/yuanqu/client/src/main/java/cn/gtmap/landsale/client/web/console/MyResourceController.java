package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.freemarker.FreeMarkerConfigurer;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.RMBUtils;
import cn.gtmap.landsale.web.ResourceQueryParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jibo1_000 on 2015/5/15.
 */
@Controller
@RequestMapping(value = "my")
public class MyResourceController {



    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransBankAccountService transBankAccountService;

    @Autowired
    TransBankPayService transBankPayService;

    @Autowired
    TransBankService transBankService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransUserUnionService transUserUnionService;

    @Autowired
    TransUserService transUserService;

    @Autowired
    TransResourceInfoService transResourceInfoService;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    RegionService regionService;

    @Autowired
    ServletContext servletContext;

    @RequestMapping("resource-list")
    public String index(ResourceQueryParam param,@PageDefault(value=5) Pageable page,Model model) throws Exception{
        String userId=SecUtil.getLoginUserId();
        String userName = SecUtil.getLoginUserViewName();
        Page<TransResourceApply>  transResourceApplyPage=
                transResourceApplyService.getTransResourceApplyPageByUserId(userId, page);

        for(TransResourceApply transResourceApply:transResourceApplyPage.getItems()){
            //判读是否交足保证金
            TransBankAccount transBankAccount =transBankAccountService.getTransBankAccountByApplyId(transResourceApply.getApplyId());
            TransResource transResource=
                    transResourceService.getTransResource(transResourceApply.getResourceId());
            if(null!=transBankAccount&&null!=transResource){
                List<TransBankPay> transBankPayList=
                        transBankPayService.getTransBankPaysByAccountId(transBankAccount.getAccountId());
                double amount=0;
                for(TransBankPay bankPay: transBankPayList){
                    if(bankPay.getPayTime().before(transResource.getBzjEndTime())) {
                        amount += bankPay.getAmount();
                    }
                }
                Double resourceFixedOffer = 0.0;
                if ("USD".equals(transResourceApply.getMoneyUnit())){
                    if(null!=transResource.getFixedOfferUsd())
                        resourceFixedOffer=transResource.getFixedOfferUsd();
                }else if ("HKD".equals(transResourceApply.getMoneyUnit())){
                    if(null!=transResource.getFixedOfferHkd())
                        resourceFixedOffer=transResource.getFixedOfferHkd();
                }else{
                    resourceFixedOffer=transResource.getFixedOffer();
                }
                if (amount>=resourceFixedOffer) {
                    transResourceApply.setFixedOfferEnough(true);
                }
            }

        }
        model.addAttribute("transResourceApplyPage",transResourceApplyPage);
        return "/my/resource-list";
    }

    //查询特定地块的交款情况
    @RequestMapping("pay-list")
    public String payList(String resourceId,Model model){
        String userId=SecUtil.getLoginUserId();
        TransResource transResource=
                transResourceService.getTransResource(resourceId);
        TransResourceApply transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId, resourceId);
        if (transResourceApply!=null) {
            TransBankAccount transBankAccount =
                    transBankAccountService.getTransBankAccountByApplyId(transResourceApply.getApplyId());
            if(transBankAccount!=null){
                model.addAttribute("transBankAccount",transBankAccount);
            }else{
                model.addAttribute("transBankAccount",new TransBankAccount());
            }
            //找到开户银行的信息
           /* model.addAttribute("bank", getBank(transResource.getRegionCode(), transResourceApply.getBankCode(),
                    transResourceApply.getMoneyUnit()));*/
            String bankId = transResourceApply.getBankId();
            if(bankId!=null){
                model.addAttribute("bank", transBankService.getBank(bankId));
            }else{
                model.addAttribute("bank", null);
            }

            if(transBankAccount!=null){
                String accountCode= transBankAccount.getAccountCode();
                if(StringUtils.isNotBlank(accountCode)){
                    List<TransBankPay> transBankPayList=
                            transBankPayService.getTransBankPaysByAccountId(transBankAccount.getAccountId());
                    model.addAttribute("transBankPayList",transBankPayList);
                }
            }else{
                model.addAttribute("transBankPayList", Lists.newArrayList());
            }

        }
        model.addAttribute("transResource",transResource);
        model.addAttribute("transResourceApply",transResourceApply);
        return "/my/pay-list";
    }

    //查询特定地块的交款情况
    @RequestMapping("offer-list")
    public String offerList(String resourceId,@PageDefault(value=15) Pageable page,Model model){
        String userId=SecUtil.getLoginUserId();
        TransResource transResource=
                transResourceService.getTransResource(resourceId);
        Page<TransResourceOffer> transResourceOfferPage=
                transResourceOfferService.getResourceOfferPageByUserId(resourceId, userId, page);
        model.addAttribute("transResource",transResource);
        model.addAttribute("transResourceOfferPage",transResourceOfferPage);
        return "/my/offer-list";
    }

    @RequestMapping("union-list")
    public String unionList(@PageDefault(value=5) Pageable page,Model model){
        String userName=SecUtil.getLoginUserViewName();
        Page<TransUserUnion> transUserUnionPage=
                transUserUnionService.findTransUserUnionByUserName(userName, page);
        model.addAttribute("transUserUnionPage",transUserUnionPage);
        return "/my/union-list";
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

    @RequestMapping("offersuccess")
    public String offersuccess(String resourceId,Model model){
        TransResource resource= transResourceService.getTransResource(resourceId);
        TransResourceOffer offer=transResourceOfferService.getTransResourceOffer(resource.getOfferId());
        TransResourceOffer offerPrice=transResourceOfferService.getMaxOfferFormPrice(resourceId);
        TransUser user=transUserService.getTransUser(offer.getUserId());
        TransCrgg transCrgg = transCrggService.getTransCrgg(resource.getGgId());
        TransResourceInfo resourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
        model.addAttribute("resource",resource);
        model.addAttribute("offer",offer);
        model.addAttribute("offerPrice",offerPrice);
        model.addAttribute("user",user);
        model.addAttribute("crgg",transCrgg);
        model.addAttribute("resourceInfo",resourceInfo);
        if("320503001".equalsIgnoreCase(resource.getRegionCode())){
            model.addAttribute("regionCode","苏州工业园区土地储备中心");
            model.addAttribute("phone","0512-66680507");
            model.addAttribute("address","苏州工业园区现代大道999号现代大厦5楼");
        }else{
            model.addAttribute("regionCode","苏州工业园区国土环保局");
            model.addAttribute("phone","0512-66680868");
            model.addAttribute("address","苏州工业园区现代大道999号现代大厦8楼");
        }
        return "/my/offersuccess";
    }

    @RequestMapping("offersuccess/download.f")
    public void getFile(String resourceId, HttpServletResponse response) throws Exception {
        TransResource resource = transResourceService.getTransResource(resourceId);
        TransResourceOffer offer = transResourceOfferService.getTransResourceOffer(resource.getOfferId());
        //TransResourceOffer offerPrice = transResourceOfferService.getMaxOfferFormPrice(resourceId);
        TransResourceOffer offerPrice = transResourceOfferService.getTransResourceOffer(resource.getOfferId());
        TransUser user = transUserService.getTransUser(offer.getUserId());
        TransCrgg transCrgg = transCrggService.getTransCrgg(resource.getGgId());
        TransResourceInfo resourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
        Map params = Maps.newHashMap();
        params.put("regionName", regionService.getDefaultRegionName());
        Calendar gpBeginCalendar = Calendar.getInstance();
        gpBeginCalendar.setTime(resource.getGpBeginTime());
        Calendar gpEndCalendar = Calendar.getInstance();
        gpEndCalendar.setTime(resource.getGpEndTime());
        Calendar resourceOverCalendar = Calendar.getInstance();
        resourceOverCalendar.setTime(resource.getOverTime());

        params.put("resource", resource);
        params.put("resourceCode", resource.getResourceCode());
        params.put("offerPrice", offerPrice.getOfferPrice() * 10000);
        params.put("offerPriceLocal", RMBUtils.toBigAmt(offerPrice.getOfferPrice() * 10000));
        params.put("ggNum", transCrgg.getGgNum());
        params.put("user", user);
        params.put("offer", offer);
        if("320503001".equalsIgnoreCase(resource.getRegionCode())){
            params.put("regionCode","苏州工业园区土地储备中心");
            params.put("phone","0512-66680507");
            params.put("address","苏州工业园区现代大道999号现代大厦5楼");
        }else{
            params.put("regionCode","苏州工业园区国土环保局");
            params.put("phone","0512-66680868");
            params.put("address","苏州工业园区现代大道999号现代大厦8楼");
        }

        StringBuilder fileName = new StringBuilder();
        if(resource.getRegionCode().equals("320503001")){
            fileName.append(resource.getResourceCode());
            fileName.append("成交通知书.doc");
            msWordTpl2Response(getTplPath("cjtzs-g.ftl"), fileName.toString(), params, response);
        }else{
            fileName.append(resource.getResourceCode());
            fileName.append("成交通知书.doc");
            if (null!=resourceInfo&&null!=resourceInfo.getIndustryType()){
                msWordTpl2Response(getTplPath("cjtzs-industrytype.ftl"), fileName.toString(), params, response);
            }else {
                msWordTpl2Response(getTplPath("cjtzs.ftl"), fileName.toString(), params, response);
            }
        }
    }

	/**
	 * @作者 王建明
	 * @创建日期 2015/7/1
	 * @创建时间 16:13
	 * @描述 —— 成交失败后的退款说明书下载
	 */
	@RequestMapping("offerfail.f")
	public void offerFail(String resourceId,HttpServletResponse response,Model model) throws Exception {
		TransResource transResource = transResourceService.getTransResource(resourceId);
		String userId = SecUtil.getLoginUserId();
		TransUser transUser = transUserService.getTransUser(userId);
		TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApplyByUserId(userId, resourceId);
        TransBank transBank = transBankService.getBank(transResourceApply.getBankId());
		TransBankAccount transBankAccount = transBankAccountService.createOrGetTransBankAccount(transResourceApply.getApplyId());
		List<TransBankPay> transBankPays = transBankPayService.getTransBankPaysByAccountCodeAndAccountId(transBankAccount.getAccountCode(), transBankAccount.getAccountId());
        TransBank bank=transBankService.getBank(transResourceApply.getBankId());
        String moneyUnit="万元";
        Double fixedOffer=transResource.getFixedOffer();
        if("USD".equalsIgnoreCase(bank.getMoneyUnit())){
            moneyUnit="万美元";
            fixedOffer=transResource.getFixedOfferUsd();
        }else if("HKD".equalsIgnoreCase(bank.getMoneyUnit())){
            moneyUnit="万港元";
            fixedOffer=transResource.getFixedOfferHkd();
        }
		TransBankPay transBankPayTemp = new TransBankPay();
		transBankPayTemp.setPayTime(new Date());
		transBankPayTemp.setAmount(0);
		transBankPayTemp.setPayBankAccount("支付账户");
		transBankPayTemp.setPayName("支付单位");
		if (transBankPays != null)
			for (TransBankPay transBankPay : transBankPays) {
				transBankPayTemp.setAmount(transBankPayTemp.getAmount() + transBankPay.getAmount());
				// 以下信息以最后一笔支付信息为准，无所谓，反正用户可以自己修改【王建明 2015/7/1 17:23】
				transBankPayTemp.setPayBankAccount(transBankPay.getPayBankAccount());
				transBankPayTemp.setPayName(transBankPay.getPayName());
				transBankPayTemp.setPayTime(transBankPay.getPayTime());
			}

        Map params = Maps.newHashMap();
        TransCrgg crgg = transCrggService.getTransCrgg(transResource.getGgId());
        params.put("transCrgg",crgg);
        params.put("regionName", regionService.getDefaultRegionName());
        params.put("transBank", transBank);
        params.put("transUser", transUser);
        params.put("transResource", transResource);
        params.put("transBankPay", transBankPayTemp);
        params.put("moneyUnit",moneyUnit);
        params.put("fixedOffer",fixedOffer);
        params.put("offerPriceLocal", RMBUtils.toBigAmt(fixedOffer * 10000));
        params.put("payTime", transBankPayTemp.getPayTime());
        params.put("currentDate", Calendar.getInstance().getTime());
        if(crgg.getRegionCode().equals("320503001")){
            params.put("dept","国土环保局");
            StringBuilder fileName = new StringBuilder();
            fileName.append(transResource.getResourceCode());
            fileName.append("保证金退款申请书.doc");
            msWordTpl2Response(getTplPath("offerfail-g.ftl"),fileName.toString(),params,response);
        }else{
            params.put("dept","土地储备中心");
            StringBuilder fileName = new StringBuilder();
            fileName.append(transResource.getResourceCode());
            fileName.append("保证金退款申请书.doc");
            msWordTpl2Response(getTplPath("offerfail.ftl"),fileName.toString(),params,response);
        }




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
            response.setContentType("application/msword; charset="+Charsets.UTF8);
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(Charsets.CHARSET_GBK), Charsets.CHARSET_ISO88591));
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName,Charsets.UTF8);
            outputStream = response.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,Charsets.CHARSET_UTF8));
            template.process(dataModel, bufferedWriter);
            outputStream.flush();
        } finally {
            if(bufferedWriter!=null)
                bufferedWriter.close();
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }


    @RequestMapping("attachment-list")
    public String attchementList(String resourceId,Model model){
        String userId= SecUtil.getLoginUserId();
        TransResourceApply  transResourceApply=
                transResourceApplyService.getTransResourceApplyByUserId(userId,resourceId);
        model.addAttribute("applyId", transResourceApply.getApplyId());
        return "/my/attachment-list";
    }

    /**
     * 下载竞买申请书
     * @param resourceId
     * @param response
     * @throws Exception
     */
    @RequestMapping("jmsqs.f")
    public void getJmsqs(String resourceId, HttpServletResponse response) throws Exception {
        TransResource resource = transResourceService.getTransResource(resourceId);
        TransCrgg transCrgg = transCrggService.getTransCrgg(resource.getGgId());

        Map params = Maps.newHashMap();
        params.put("regionName", regionService.getDefaultRegionName());
        params.put("resource", resource);
        params.put("resourceCode", resource.getResourceCode());
        params.put("ggNum", transCrgg.getGgNum());
        params.put("userName", SecUtil.getLoginUserViewName());
        params.put("currentDate", Calendar.getInstance().getTime());
        params.put("offerPriceLocal", RMBUtils.toBigAmt(resource.getFixedOffer() * 10000));
        StringBuilder fileName = new StringBuilder();
        if(resource.getRegionCode().equals("320503001")){
            fileName.append(resource.getResourceCode());
            fileName.append("竞买申请书.doc");
            msWordTpl2Response(getTplPath("jmsqs-g.ftl"), fileName.toString(), params, response);
        }else{
            fileName.append(resource.getResourceCode());
            fileName.append("竞买承诺书.doc");
            msWordTpl2Response(getTplPath("jmsqs.ftl"), fileName.toString(), params, response);

        }

    }


    private String getTplPath(String tplName){
        String regionTag = regionService.getDefaultRegionCode();
        String tplPath = "/views/material/"+regionTag+"/"+tplName;
        String basePath = servletContext.getRealPath("/")+"/WEB-INF";
        if(new File(basePath+tplPath).exists())
            return tplPath;
        else
            return "/views/material/default/"+tplName;
    }

}
