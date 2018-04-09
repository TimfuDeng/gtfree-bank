package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.mq.ResourceOfferQueueService;
import cn.gtmap.landsale.security.DesUtil;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.NumberUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import org.springframework.web.context.request.async.DeferredResult;
import org.unbescape.html.HtmlEscape;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
public class ViewController {
    private static final long _TimeOut=72*1000;  //1.2分钟超时时间，参考taobao
    private static Logger log = LoggerFactory.getLogger(ViewController.class);
    @Autowired
    ServiceUtils serviceUtils;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    ClientService clientService;

    @Autowired
    ResourceOfferQueueService resourceOfferQueueService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransFileService transFileService;

    @Autowired
    TransResourceInfoService transResourceInfoService;

    @Autowired
    TransGpsOffsetService transGpsOffsetService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    AttachmentCategoryService attachmentCategoryService;

    @Autowired
    TransBuyQualifiedService transBuyQualifiedService;

    @Autowired
    TransResourceSonService transResourceSonService;
    @Autowired
    OnePriceLogService onePriceLogService;
    @Autowired
    OneTargetService oneTargetService;

    @Autowired
    LandUseDictSerivce landUseDictSerivce;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;


    @Autowired
    CaSvsService caSvsService;

    private Set<Integer> baseNumber = Sets.newHashSet(1, 2, 3, 4, 5,6,7,8,9,10);

    private int applyNumberCount = 10;

    @RequestMapping("/view")
    public String index(@RequestParam(value = "id",required = true)String id,Model model) throws Exception{
        TransResource transResource= transResourceService.getTransResource(id);
        model.addAttribute("resource", transResource);
        List<TransResourceSon> resourceSonList = transResourceSonService.getTransResourceSonList(id);
        Set<String> landUseList=new HashSet<String>();
        /*for(TransResourceSon transResourceSon:resourceSonList){
            landUseList.add(transResourceSon.getSonLandUse().getCode());
        }
        model.addAttribute("landUseList",Lists.newArrayList(landUseList));*/

        for(TransResourceSon transResourceSon:resourceSonList){
            landUseList.add(transResourceSon.getSonLandUseMuli());
        }
        model.addAttribute("landUseList",Lists.newArrayList(landUseList));
        LandUseDict landUseDict = landUseDictSerivce.getLandUseDict(transResource.getLandUseMuli());
        model.addAttribute("landUseDict",landUseDict);
        TransResourceInfo transResourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(id);
        if(transResourceInfo==null)
            transResourceInfo = new TransResourceInfo();
        model.addAttribute("resourceInfo",transResourceInfo);
        //获得报价列表（15条）,单不包括一次报价数据
        List<TransResourceOffer> resourceOffers= clientService.getOfferList(id,-1);
        model.addAttribute("resourceOffers", resourceOffers);
        model.addAttribute("cDate", Calendar.getInstance().getTime().getTime());
        //获得当前最高报价
        TransResourceOffer maxOffer= (resourceOffers.size()>0) ? maxOffer=resourceOffers.get(0):null;
        model.addAttribute("maxOffer", maxOffer);
        model.addAttribute("maxoffercount",resourceOffers.size()>0?resourceOffers.get(0).getOfferCount():null);
        //当前总价的最高报价,不包括多指标
        TransResourceOffer maxOfferPrice= transResourceOfferService.getMaxOfferFormPrice(id);
        model.addAttribute("maxOfferPrice", maxOfferPrice);
        //判断用户和当前地块的购买状态
        if (SecUtil.isLogin()){
            TransResourceApply transResourceApply=transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(),id);
            model.addAttribute("transResourceApply", transResourceApply);
            TransBuyQualified transBuyQualified=null;
            if(null!=transResourceApply&&null!=transResourceApply.getQualifiedId())
                transBuyQualified= transBuyQualifiedService.getTransBuyQualifiedById(transResourceApply.getQualifiedId());
            model.addAttribute("transBuyQualified",transBuyQualified);
        }
        //如果在限时竞价阶段，要判断当前限时竞价的截至时间
        if(transResource.getResourceStatus()== Constants.ResourceStatusJingJia){
            if(maxOffer==null || maxOffer.getOfferType()==Constants.OfferTypeGuaPai || (maxOffer.getOfferType()==Constants.OfferXianjia && maxOffer.getOfferTime()<=transResource.getGpEndTime().getTime())){
                  /* model.addAttribute("endTime", transResource.getGpEndTime().getTime() + 1000 * 60 * 4);*/
                model.addAttribute("endTime", transResource.getXsBeginTime().getTime()+1000*60*4);
            }else{
                long offerTime = maxOffer.getOfferTime();
                //long gpEndTime = transResource.getGpEndTime().getTime();
                long xsBeginTime = transResource.getXsBeginTime().getTime();
                if (offerTime < xsBeginTime) {
                   /* model.addAttribute("endTime", transResource.getGpEndTime().getTime() + 1000 * 60 * 4);*/
                    model.addAttribute("endTime", transResource.getXsBeginTime().getTime() + 1000 * 60 * 4);
                } else {
                    model.addAttribute("endTime", offerTime + 1000 * 60 * 4);
                }
            }
        }
        model.addAttribute("userId", SecUtil.getLoginUserId());
        TransCrgg crgg = transCrggService.getTransCrgg(transResource.getGgId());
        model.addAttribute("ggNum", StringUtils.isNotBlank(transResource.getGgId()) ? crgg.getGgNum() : "");
        model.addAttribute("ggBeginTime",crgg.getGgBeginTime());
        model.addAttribute("ggEndTime",crgg.getGgEndTime());
        model.addAttribute("winStandard", StringUtils.isNotBlank(transResource.getGgId()) ? crgg.getWinStandard() : "03");

        List thumbnailsPathList = Lists.newArrayList();
        List<TransFile> thumbnailFileList = transFileService.getTransFileThumbnails(id, Constants.CLIENT_THUMBNAIL_RESOLUTION);
        if(thumbnailFileList!=null&&thumbnailFileList.size()>0){
            for(TransFile thumbnailFile:thumbnailFileList){
                thumbnailsPathList.add(Constants.IMAGE_BASE_PATH+thumbnailFile.getFileId());
            }
        }else{
            thumbnailsPathList.add(Constants.IMAGE_PATH);
        }

        model.addAttribute("thumbnails",thumbnailsPathList);
        model.addAttribute("attachments", transFileService.getTransFileAttachments(id));
        Map attachmentCategory = attachmentCategoryService.getTransResourceAttachmentCategory();
        attachmentCategory.put(Constants.FileType.QT.getCode(), Constants.FileType.QT.toString());
        model.addAttribute("attachmentCategory", attachmentCategory);
        model.addAttribute("crggAttachments", transFileService.getTransFileAttachments(transResource.getGgId()));
        model.addAttribute("applyNumberCount",applyNumberCount);
        model.addAttribute("baseNumber",baseNumber);
        model.addAttribute("usedApplyNumbers",transResourceApplyService.getAllUsedApplyNumber(id));
        List<TransResourceSon> transResourceSonList=transResourceSonService.getTransResourceSonList(transResource.getResourceId());
        String resourceCodeSecret="";
        for(TransResourceSon transResourceSon:transResourceSonList){
            resourceCodeSecret=resourceCodeSecret+DesUtil.encodeData(transResourceSon.getZdCode(), Constants.key)+",";
        }
        if(resourceCodeSecret.contains(",")){
            resourceCodeSecret=resourceCodeSecret.substring(0,resourceCodeSecret.length()-1);
        }
        model.addAttribute("resourceCodeSecret", resourceCodeSecret);
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
        model.addAttribute("oneTarget",oneTarget);
        TransResourceOffer successOffer = null;
        if(null!=transResource.getOfferId()){
            successOffer = transResourceOfferService.getTransResourceOffer(transResource.getOfferId());
        }
        model.addAttribute("successOffer", successOffer);

        return "view";
    }


    @RequestMapping("/view/offer/list.f")
    public String offerList(@PageDefault(value=10) Pageable page,@RequestParam(value = "resourceId",required = true)String resourceId,Model model) {
        Page<TransResourceOffer> transResourceOffers= clientService.findTransResourceOffers(page,resourceId);
        model.addAttribute("transResourceOffers", transResourceOffers);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("resource", transResourceService.getTransResource(resourceId));
        model.addAttribute("userId",SecUtil.getLoginUserId());
        return "common/view-offer-list";
    }

    @RequestMapping("/view/oneprice/offer/list.f")
    public String onePriceOfferList(@PageDefault(value=10) Pageable page,@RequestParam(value = "resourceId",required = true)String resourceId,Model model) {
        List<OnePriceLog> onePriceLogList=null;
        long totalPrice=0L;
        Double avgPrice=null;
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(resourceId);
        if(null!=oneTarget && null!=oneTarget.getSuccessPrice() && oneTarget.getSuccessPrice()>0){
            onePriceLogList=onePriceLogService.findOnePriceLogList(resourceId);
            if (onePriceLogList.size()>0){
                for(int i=0;i<onePriceLogList.size(); i++){
                    totalPrice = totalPrice + onePriceLogList.get(i).getPrice();
                }
                avgPrice=(double)totalPrice/(onePriceLogList.size());
            }
            //四舍五入，保留2位小数
            BigDecimal bigDecimal=new BigDecimal(avgPrice);
            avgPrice=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        model.addAttribute("avgPrice",avgPrice);
        model.addAttribute("oneTarget",oneTarget);
        model.addAttribute("onePriceLogList",onePriceLogList);
        return "common/view-oneprice-list";
    }

    @RequestMapping("view/offer/getMaxOfferCount")
    @ResponseBody
    public Integer getMaxOfferCount(@PageDefault(value=10) Pageable page,String resourceId){
        Page<TransResourceOffer> transResourceOffers= clientService.findTransResourceOffers(page,resourceId);
        return transResourceOffers.getItems().get(0).getOfferCount();
    }

    /**
     * 选择号牌
     * @param id
     * @param applyNumber
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectNumber.f")
    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_OFFER,producer = Constants.LogProducer.CLIENT,
            description = "用户领取号牌")
    public @ResponseBody String selectNumber(String id,Integer applyNumber) throws Exception{
        String userId= SecUtil.getLoginUserId();
        try {
            if (applyNumber != null) {
                TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApplyByUserId(userId, id);
                TransResource transResource = transResourceService.getTransResource(transResourceApply.getResourceId());
                if (null != transResource.getYxEndTime() && (new Date().after(transResource.getYxEndTime()))) {
                    return "该竞买人超过了有效报价时间！";
                }
                //地块中止;号牌已经被领取；超过有效时间;
                if (transResource.getResourceEditStatus() != 2) {
                    return "OfferErrorChange";
                }

                if (transResourceApply.getApplyNumber() > 0){
                    return "该竞买人已经选择当前地块的号牌！";
                }
                if(!transResourceApplyService.isValidApplyNumber(id, applyNumber)){
                    return "该号牌已经被领取，重新请选择号牌!";
                }

                synchronized (this) {
                    transResourceApply.setApplyNumber(applyNumber);
                    transResourceApply=transResourceApplyService.saveTransResourceApply(transResourceApply);
                    if(transResourceApply.getApplyNumber()>0){
                        return "true";
                    }else {
                        return "领取号牌错误!";
                    }

                }

            }else{
                return "号牌不能为空！";
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ex.getMessage();
        }

    }

    @RequestMapping("/offer.f")
    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_OFFER,producer = Constants.LogProducer.CLIENT,
            description = "用户报价")
    public @ResponseBody String offer(String id,String offer,int type,Integer applyNumber,CaSignerX caSignerX) throws Exception{
        String userId= SecUtil.getLoginUserId();
            try {
                if (caEnabled) {
                    boolean signatureValid = validateSignature(caSignerX);
                    if (!signatureValid)
                        return "报价数据验证错误，请检查CA数字证书环境";
                    String sxInput = new String(Base64.decodeBase64(caSignerX.getSxinput()), Charsets.CHARSET_UTF8);
                    Map sxInputMap = JSON.parseObject(sxInput);
                    id = String.valueOf(sxInputMap.get("id"));
                    offer = String.valueOf(sxInputMap.get("offer"));
                    type = NumberUtils.createInteger(String.valueOf(sxInputMap.get("type")));
                    if (!String.valueOf(sxInputMap.get("applyNumber")).equalsIgnoreCase("null"))
                        applyNumber = NumberUtils.createInteger(String.valueOf(sxInputMap.get("applyNumber")));
                }
                //地块中止;未选择号牌；号牌已经被领取；超过有效时间；一块地块只有一个报价
                TransResourceApply transResourceApply = transResourceApplyService.getTransResourceApplyByUserId(userId, id);
                TransResource transResource = transResourceService.getTransResource(transResourceApply.getResourceId());
                if (null != transResource.getYxEndTime() && (new Date().after(transResource.getYxEndTime()))) {
                    return "该竞买人超过了有效报价时间！";
                }
                if (transResource.getResourceEditStatus() != 2) {
                    return "OfferErrorChange";
                }
                //将地块和报价字段设置唯一值
                if(null!=transResourceOfferService.getOnlyPriceOffer(id,Double.parseDouble(offer))){
                    return "报价重复，请重新报价！";
                }
              /*  if (applyNumber != null) {
                    if (transResourceApply.getApplyNumber() > 0)
                        return "该竞买人已经选择当前地块的号牌！";
                    if (transResourceApplyService.isValidApplyNumber(id, applyNumber)) {
                        transResourceApply.setApplyNumber(applyNumber);
                        transResourceApplyService.saveTransResourceApply(transResourceApply);
                    } else {
                        return "该号牌已经被领取，重新请选择号牌!";
                    }
                }*/
                TransResourceOffer  maxOffer = transResourceOfferService.getMaxOffer(id);
                if(null!=maxOffer && ((Double.parseDouble(offer)-maxOffer.getOfferPrice())/transResource.getAddOffer())>5){
                    return "报价增价幅度不能超过5倍";
                }

                if (StringUtils.isNotBlank(offer)) {
                    synchronized (this) {
                        TransResourceOffer resourceOffer = clientService.acceptResourceOffer(userId, id, Double.parseDouble(offer), type);
                        if (resourceOffer != null) {
                            resourceOfferQueueService.receiveNewOffer(resourceOffer);
                            return "true";
                        } else {
                            return "接受报价错误！";
                        }
                    }
                } else {
                    return "报价为空！";
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                return ex.getMessage();
            }

    }

    @RequestMapping("/offerLimit.f")
    public @ResponseBody String offerLimit(String id) throws Exception{
        String userId= SecUtil.getLoginUserId();
        try{
            if (StringUtils.isNotBlank(id)) {
                TransResourceApply transResourceApply= transResourceApplyService.getTransResourceApplyByUserId(userId,id);
                TransResource transResource = transResourceService.getTransResource(transResourceApply.getResourceId());
                long gpEndTime = transResource.getGpEndTime().getTime();
                long curtTime = new Date().getTime();
                if(curtTime>gpEndTime) {
                    return "当前时间超出限时竞价询问期限，进入失败";
                }else{
                    transResourceApplyService.enterLimitTransResourceApply(transResourceApply.getApplyId());
                }
            }
        }catch (Exception ex){
            return ex.getMessage();
        }
        return "true";
    }


    /* public  String Longdate2Str(long date){
         String dateStr="";
         if(date>0){
             SimpleDateFormat dateFormatyyyyMMddHHmmss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             dateStr=dateFormatyyyyMMddHHmmss.format(date);
         }
         return dateStr;
     }*/
    @RequestMapping("/getoffer.f")
    public @ResponseBody  DeferredResult<String> deferredResult(String id,String time) {
        DeferredResult<String> clientResult = new DeferredResult<String>(_TimeOut,"timeout");
        TransResource transResource= transResourceService.getTransResource(id);
        if (transResource.getResourceEditStatus()!=Constants.ResourceEditStatusRelease ||
                transResource.getResourceStatus()==Constants.ResourceStatusChengJiao ||
                transResource.getResourceStatus()==Constants.ResourceStatusLiuBiao    ){
            clientResult.setResult("refresh");
        }else {
            List<TransResourceOffer> offers = clientService.getOfferList(id, Long.parseLong(time));
            if (offers.size() > 0) {
                writeClient(clientResult, offers);
                return null;
            } else {
                resourceOfferQueueService.addResource(transResource, clientResult);
                return clientResult;
            }
        }
        return clientResult;
    }

    private void writeClient(DeferredResult<String> client,List<TransResourceOffer> resourceOfferList){
        Map<String,Object> resultMap= Maps.newHashMap();
        resultMap.put("time", Calendar.getInstance().getTime().getTime());
        resultMap.put("result", resourceOfferList);
        client.setResult(JSON.toJSONString(resultMap));
    }

    @RequestMapping("/view/crgg.f")
    public String viewCrgg(@RequestParam(value = "id", required = true)String id,Model model){
        TransResource transResource= transResourceService.getTransResource(id);
        String content = "";
        if(StringUtils.isNotBlank(transResource.getGgId()))
            content= transCrggService.getTransCrgg(transResource.getGgId()).getGgContent();

        model.addAttribute("content", HtmlEscape.unescapeHtml(content));
        return "crgg";
    }

    @RequestMapping("/view/geometry.f")
    @ResponseBody
    public Object viewResourceGeometry(@RequestParam(value = "id", required = true)String id,Model model){
        TransResource transResource= transResourceService.getTransResource(id);
        return geometry2Features(transResource);
    }


    @RequestMapping("/view/selectApplyNumber.f")
    public String selectApplyNumber(@RequestParam(value = "id", required = true)String id,Model model){
        TransResourceApply transResourceApply=transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(), id);
        model.addAttribute("applyNumberCount",applyNumberCount);
        model.addAttribute("baseNumber",baseNumber);
        model.addAttribute("usedApplyNumbers",transResourceApplyService.getAllUsedApplyNumber(id));
        model.addAttribute("transResourceApply",transResourceApply);
        return "common/apply-number";
    }

    @RequestMapping("/view/applyNumber.f")
    public String applyNumber(@RequestParam(value = "id", required = true)String id,Model model){
        TransResourceApply transResourceApply=transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(), id);
        model.addAttribute("transResourceApply",transResourceApply);
        return "common/view-applynumber";
    }

    private List geometry2Features(TransResource transResource){
        Map feature = Maps.newHashMap();
        Map properties = Maps.newHashMap();
        properties.put("popupContent",transResource.getResourceLocation());
        properties.put("title",transResource.getResourceCode());
        feature.put("geometry",correctGeoemtry(JSON.parse(transResource.getGeometry())));
        feature.put("properties",properties);
        feature.put("type","Feature");
        return Lists.newArrayList(feature);
    }

    private Object correctGeoemtry(Object geometry){
        if(geometry==null)
            return null;
        Map geometryMap = (Map) geometry;
        List<List> rings = (List)geometryMap.get("coordinates");
        for(List ring:rings){
            List<List> points = ring;
            for(List coordinates:points){
                Double xCoord = Double.valueOf(String.valueOf(coordinates.get(0)));
                Double yCoord = Double.valueOf(String.valueOf(coordinates.get(1)));
                Double[] newCoords = transGpsOffsetService.correctPoint(xCoord, yCoord);
                coordinates.set(0,newCoords[0]);
                coordinates.set(1,newCoords[1]);
            }
        }
        return geometry;
    }

    private boolean validateSignature(CaSignerX caSignerX) throws Exception {
        boolean signatureValid = false;
        if(caSignerX.getSxaction().equalsIgnoreCase("PKCS1")){
            String algo = null;
            if(StringUtils.isNotBlank(caSignerX.getSxdigest())&&"SHA1".equals(caSignerX.getSxdigest()))
                algo = Constants.CaSignatureAlgo.RSA_SHA1.toString();
            else
                algo = Constants.CaSignatureAlgo.RSA_MD5.toString();
            signatureValid = caSvsService.validatePKCS1Signature(caSignerX.getSxcertificate(),caSignerX.getPkcs1(),caSignerX.getSxinput(),
                    algo, Constants.CaOriginalDateType.ORIGINAL);
        }else{
            signatureValid = caSvsService.validatePKCS7Signature(caSignerX.getPkcs7());
        }

        return signatureValid;
    }


}

