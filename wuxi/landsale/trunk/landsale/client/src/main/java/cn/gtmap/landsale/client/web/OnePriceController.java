package cn.gtmap.landsale.client.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.client.register.*;
import cn.gtmap.landsale.client.util.HTMLSpirit;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.log.AuditServiceLog;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 一次报价
 * @author zsj
 * @version v1.0, 2017/12/8
 */
@Controller
@RequestMapping("/oneprice")
public class OnePriceController extends BaseController {
    private Logger log= LoggerFactory.getLogger(OnePriceController.class);

    @Autowired
    OneTargetClient oneTargetClient;

    @Autowired
    OneApplyClient oneApplyClient;

    @Autowired
    CaSvsClient caSvsClient;

//    @Autowired
//    FreeMarkerConfigurer freeMarkerConfigurer;

//    @Autowired
//    ServletContext servletContext;

    @Autowired
    ServiceUtilClient serviceUtils;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    OnePriceLogClient onePriceLogClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransResourceClient transResourceClient;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @RequestMapping("/index")
    public String index(String title, @PageDefault(value=8) Pageable page, Model model) {
        model.addAttribute("nowDate", new Date());
        model.addAttribute("title", title);
        return "oneprice/index";
    }

    @RequestMapping("/content")
    public String content(String title, @PageDefault(value=8) Pageable page, Model model) {
        Page<OneTarget> oneTargetList = oneTargetClient.findOneTargetPageByIsStop(title, page, 1);
        for (OneTarget oneTarget : oneTargetList){
            oneTarget.setPriceMin(oneTarget.getPriceMin() * 10000);
            oneTarget.setPriceMax(oneTarget.getPriceMax() * 10000);
        }
        model.addAttribute("oneTargetList", oneTargetList);
        model.addAttribute("nowDate", new Date());
        model.addAttribute("title", title);
        return "oneprice/oneTarget-content";
    }


    @RequestMapping("/view")
    public String view(String id, Model model) {
        OneTarget oneTarget = oneTargetClient.getOneTarget(id);
        oneTarget.setPriceMin(oneTarget.getPriceMin() * 10000);
        oneTarget.setPriceMax(oneTarget.getPriceMax() * 10000);
        String userId= SecUtil.getLoginUserId();
        //先判断用户是否对该地块拥有报价权利
        TransResourceApply resourceApply = transResourceApplyClient.getTransResourceApplyByUserId(userId, oneTarget.getTransResourceId());
        List<OnePriceLog> onePriceLogList = onePriceLogClient.findOnePriceLogList(oneTarget.getTransResourceId());
        OnePriceLog myOnePriceLog = onePriceLogClient.findOnePriceLogListByTransResourceIdTransUserId(oneTarget.getTransResourceId(), SecUtil.getLoginUserId());
        Pageable request = new PageRequest(0, 15);
        Page<TransResourceOffer> transResourceOfferPage = transResourceOfferClient.getResourceOfferPageByUserId(id, userId, request);
        List<TransResourceOffer> transResourceOfferList = transResourceOfferPage.getItems();
        OneApply oneApply = oneApplyClient.getOnApply(oneTarget.getId(), SecUtil.getLoginUserId());
        model.addAttribute("oneApply",oneApply);
        model.addAttribute("myOnePriceLog",myOnePriceLog);
        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("resourceApply",resourceApply);
        model.addAttribute("oneTarget",oneTarget);
        model.addAttribute("nowDate",new Date());
        model.addAttribute("transUserId",SecUtil.getLoginUserId());
        model.addAttribute("caEnabled",caEnabled);
        model.addAttribute("transResourceOfferList",transResourceOfferList);
        return "oneprice/view";
    }

    /**
     * 跳转到规则页面
     * @param targetId
     * @param transUserId
     * @param model
     * @return
     */
    @RequestMapping("/view-agree")
    public String agree(String targetId,String transUserId,Model model){

        model.addAttribute("targetId",targetId);
        model.addAttribute("transUserId",transUserId);
        model.addAttribute("nowDate",new Date());
        return "/oneprice/view-agree";
    }


    /**
     * 同意，产生申请记录
     * @param targetId
     * @param transUserId
     * @param model
     * @return
     */
    @RequestMapping("/view-agree/edit")
    @ResponseBody
    public String agreeEdit(String targetId,String transUserId,Model model){
        try {
            OneApply oneApply = oneApplyClient.getOnApply(targetId, transUserId);
            Long nowDate=Calendar.getInstance().getTime().getTime();
            OneTarget oneTarget = oneTargetClient.getOneTarget(targetId);
            //在询问时间范围内，申请
            if (nowDate<=oneTarget.getQueryBeginDate().getTime() || nowDate>oneTarget.getQueryEndDate().getTime()){
                return "请在询问时间范围内询问！";
            }
            if (null==oneApply){
                oneApply=new OneApply();
                oneApply.setTransUserId(SecUtil.getLoginUserId());
                oneApply.setCreateDate(new Date());
                oneApply.setTargetId(targetId);
                oneApply.setStep(1);
                oneApplyClient.saveOneApply(oneApply);
            }
            if(null!=oneApply){
                return "true";
            }else {
                return "申请错误！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return  e.getMessage();
        }

    }




    @RequestMapping("/view-oneprice")
    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_OFFER,producer = Constants.LogProducer.CLIENT,
            description = "用户报价")
    public @ResponseBody
    String offer(String id, String applyId, String offer, CaSignerX caSignerX) throws Exception {
        String transUserId = SecUtil.getLoginUserId();
        Long nowDate=Calendar.getInstance().getTime().getTime();
        OneTarget oneTarget = oneTargetClient.getOneTarget(id);
        oneTarget.setPriceMin(oneTarget.getPriceMin()*10000);
        oneTarget.setPriceMax(oneTarget.getPriceMax()*10000);
        try {
            if (false) {
                boolean signatureValid = validateSignature(caSignerX);
                if (!signatureValid) {
                    return "报价数据验证错误，请检查CA数字证书环境";
                }
                String sxInput = new String(Base64.decodeBase64(caSignerX.getSxinput()), Charsets.CHARSET_UTF8);
                Map sxInputMap = JSON.parseObject(sxInput);
                id = String.valueOf(sxInputMap.get("id"));
                offer = String.valueOf(sxInputMap.get("offer"));
                applyId = String.valueOf(sxInputMap.get("applyId"));
                Long longOffer = Long.parseLong(offer);
                TransUser user = transUserClient.getTransUserById(transUserId);
                String key=user.getCaThumbprint().replaceAll("[/\\D/g]", "");
                if(key.length()>3){
                    key=key.substring(key.length()-3)+"1";
                }else {
                    key=key+"1";
                }
                Long longKey=Long.parseLong(key);
                longOffer=longOffer/longKey;
                offer=String.valueOf(longOffer);
            }
            if (StringUtils.isNotBlank(offer)) {
                synchronized (this) {
                    //报价区间
                    //不能重复报价
                    //不能超过时间
                    //同一个ca对同一块地块只能报价一次
                    Long longOffer = Long.parseLong(offer);
                    if(oneTarget.getIsStop()==0){
                        return "地块已经被撤回，请返回首页！";
                    }
                    if(longOffer<=oneTarget.getPriceMin()||oneTarget.getPriceMax()<longOffer){
                        return "报价不在报价区间,请重新报价！";
                    }
                    if(null != onePriceLogClient.findOnePriceLogListByTransResourceIdPrice(oneTarget.getTransResourceId(), longOffer)){
                        return "您的报价已被报了，请重新报价！";
                    }
                    if(nowDate<=oneTarget.getPriceBeginDate().getTime() || nowDate>oneTarget.getPriceEndDate().getTime()){
                        return "请在报价时间范围内报价！";
                    }
                    if(null != onePriceLogClient.getOnePriceLog(applyId)){
                        return "您已经进行过一次报价，请刷新页面！";
                    }
                    OnePriceLog onePriceLog =new OnePriceLog();
                    onePriceLog.setPriceDate(new Date());
                    onePriceLog.setCreateDate(new Date());
                    onePriceLog.setPrice(longOffer);
                    onePriceLog.setTransUserId(transUserId);
                    onePriceLog.setApplyId(applyId);
                    onePriceLog.setPriceUnit(SecUtil.getLoginUserViewName());
                    onePriceLog.setTransResourceId(oneTarget.getTransResourceId());
                    ResponseMessage<OnePriceLog> temp = onePriceLogClient.saveOnePriceLog(onePriceLog);
                    if (temp != null) {
                        return "true";
                    } else {
                        return "接受报价错误！";
                    }
                }
            } else {
                return "报价为空！";
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ex.getMessage();
        }
    }

    private boolean validateSignature(CaSignerX caSignerX) throws Exception {
        boolean signatureValid = false;
        if("PKCS1".equalsIgnoreCase(caSignerX.getSxaction())){
            String algo = null;
            if(StringUtils.isNotBlank(caSignerX.getSxdigest())&&"SHA1".equals(caSignerX.getSxdigest())) {
                algo = Constants.CaSignatureAlgo.RSA_SHA1.toString();
            } else {
                algo = Constants.CaSignatureAlgo.RSA_MD5.toString();
            }
            signatureValid = caSvsClient.validatePKCS1Signature(caSignerX.getSxcertificate(),caSignerX.getPkcs1(),caSignerX.getSxinput(),
                    algo, Constants.CaOriginalDateType.ORIGINAL).getFlag();
        }else{
            signatureValid = caSvsClient.validatePKCS7Signature(caSignerX.getPkcs7()).getFlag();
        }
        return signatureValid;
    }

    /**
     * 我的交易列表
     * @return
     * @throws Exception
     */
    @RequestMapping("my/view-resource-list")
    public String resourceList(Model model,@PageDefault(value=5) Pageable page){
        //我申请过的
        Page<OneApply> oneApplyList = oneApplyClient.findOneApply(SecUtil.getLoginUserId(), page);
        model.addAttribute("oneApplyList",oneApplyList);
        return "oneprice/resource-list";
    }

    @RequestMapping("my/view-offer-list")
    public String offerList(String id,Model model){
        OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(id);
        //报价列表
        Date nowDate=new Date();
        List<OnePriceLog> onePriceLogList =  onePriceLogClient.findOnePriceLogList(id);

        String userId = SecUtil.getLoginUserId();
        Pageable request = new PageRequest(0, 15);
        Page<TransResourceOffer> transResourceOfferPage = transResourceOfferClient.getResourceOfferPageByUserId(id, userId, request);
        List<TransResourceOffer> transResourceOfferList = transResourceOfferPage.getItems();

        model.addAttribute("onePriceLogList",onePriceLogList);
        model.addAttribute("nowDate",nowDate);
        model.addAttribute("transResourceOfferList",transResourceOfferList);
        model.addAttribute("oneTarget",oneTarget);

        return "my/offer-list-oneprice";
    }

   /* @RequestMapping("my/cjtzs.f")
    public void getFile(String resourceId, HttpServletResponse response) throws Exception {
        Map params = Maps.newHashMap();
        StringBuilder fileName = new StringBuilder();

        fileName.append("成交通知书.doc");
        msWordTpl2Response(getTplPath("cjtzs.ftl"), fileName.toString(), params, response);
    }

    *//**
     * 将msword的模板写入到response中，实现word文件导出功能
//     * @param ftlName 模板文件名称，带相对路径，例如/views/my/offerfail.ftl
//     * @param fileName 导出word文件名称
//     * @param dataModel freemarker模板数据模型
//     * @param response
     * @throws Exception
     *//*
    private void msWordTpl2Response(String ftlName,String fileName,Object dataModel,HttpServletResponse response) throws Exception {
        OutputStream outputStream = null;
        Writer bufferedWriter = null;
        try {
            response.reset();
            response.setContentType("application/msword; charset="+ Charsets.UTF8);
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(Charsets.CHARSET_GBK), Charsets.CHARSET_ISO88591));
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName, Charsets.UTF8);
            outputStream = response.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charsets.CHARSET_UTF8));
            template.process(dataModel, bufferedWriter);
            outputStream.flush();
        } finally {
            if(bufferedWriter!=null)
                bufferedWriter.close();
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }*/

//    private String getTplPath(String tplName){
//        String basePath = servletContext.getRealPath("/")+"/WEB-INF";
//        return "/views/material/default/"+tplName;
//    }


    @RequestMapping("/view-result")
    public String result(@RequestParam(value = "id",required = true)String id,Model model) throws Exception{
        return "oneprice/view-result";
    }

    /**
     * @作者 王建明
     * @创建日期 2017/2/23 0023
     * @创建时间 上午 11:21
     * @描述 —— 一次报价列表
     */
    @RequestMapping("/offer/list")
    public String offerList2(@RequestParam(value = "resourceId",required = true)String resourceId, Model model) throws Exception{
        if(StringUtils.isNotBlank(resourceId)){
            try {
                resourceId = HTMLSpirit.delHTMLTag(URLDecoder.decode(resourceId, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<OnePriceLog> onePriceLogList = onePriceLogClient.findOnePriceLogList(resourceId);
        model.addAttribute("onePriceLogList", onePriceLogList);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("userId",SecUtil.getLoginUserId());
        return "oneprice/view-offer-list";
    }
}
