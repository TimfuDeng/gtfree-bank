package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.core.service.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author jiff on 14-6-26.
 */
@RestController
@RequestMapping(value = "/view")
public class ViewController {
    private static final long TIMEOUT = 72 * 1000;  //1.2分钟超时时间，参考taobao

  /*  @Autowired
    ServiceUtils serviceUtils;*/

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    ClientService clientService;



/*    @Autowired
    TransResourceApplyService transResourceApplyService;*/

    @Autowired
    TransCrggService transCrggService;

  /*  @Autowired
    TransFileService transFileService;*/

   /* @Autowired
    TransResourceInfoService transResourceInfoService;*/

    @Autowired
    TransGpsOffsetService transGpsOffsetService;

    @Autowired
    TransResourceOfferService transResourceOfferService;



/*    @Autowired
    AttachmentCategoryService attachmentCategoryService;*/

/*    @Value("${ca.login.enabled}")
    Boolean caEnabled;*/

 /*   @Autowired
    CaSvsService caSvsService;*/

    @RequestMapping("/view")
    public String index(@RequestParam(value = "id", required = true) String id, Model model, String userId) throws Exception {
        TransResource transResource = transResourceService.getTransResource(id);
        model.addAttribute("resource", transResource);

       /* TransResourceInfo transResourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(id);
        if(transResourceInfo==null)
            transResourceInfo = new TransResourceInfo();
        model.addAttribute("resourceInfo",transResourceInfo);*/
        //获得报价列表（15条）
        List<TransResourceOffer> resourceOffers = clientService.getOfferList(id, -1);
        model.addAttribute("resourceOffers", resourceOffers);
        model.addAttribute("cDate", Calendar.getInstance().getTime().getTime());
        //获得当前最高报价
        TransResourceOffer maxOffer = (resourceOffers.size() > 0) ? maxOffer = resourceOffers.get(0) : null;
        model.addAttribute("maxOffer", maxOffer);
       /* if(null!=maxOffer)
        model.addAttribute("maxResourceApply",transResourceApplyService.getTransResourceApplyByUserId(maxOffer.getUserId(),id));*/
        //当前总价的最高报价,不包括多指标
        TransResourceOffer maxOfferPrice = transResourceOfferService.getMaxOfferFormPrice(id);
        model.addAttribute("maxOfferPrice", maxOfferPrice);
        //判断用户和当前地块的购买状态
       /* if (SecUtil.isLogin()){
            TransResourceApply transResourceApply=transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(),id);
            model.addAttribute("transResourceApply", transResourceApply);
        }*/
        /*//如果在限时竞价阶段，要判断当前限时竞价的截至时间
        if(transResource.getResourceStatus()== Constants.RESOURCE_STATUS_JING_JIA){
            if(maxOffer==null || maxOffer.getOfferType()==Constants.OFFER_TYPE_GUA_PAI || (maxOffer.getOfferType()==Constants.OFFER_XIANJIA && maxOffer.getOfferTime()<=transResource.getGpEndTime().getTime())){
                model.addAttribute("endTime", transResource.getGpEndTime().getTime()+1000*60*4);
            }else{
                model.addAttribute("endTime", maxOffer.getOfferTime()+1000*60*4);
            }
        }*/

        /**
         *@annotation
         *@author liushaoshuai【liushaoshuai@gtmap.cn】
         *@date 2017/5/8 14:53
         *@param
         *@return
         */
        //如果在限时竞价阶段，要判断当前限时竞价的截至时间
        if (transResource.getResourceStatus() == Constants.RESOURCE_STATUS_JING_JIA) {
            if (maxOffer == null || maxOffer.getOfferType() == Constants.OFFER_TYPE_GUA_PAI || (maxOffer.getOfferType() == Constants.OFFER_XIANJIA && maxOffer.getOfferTime() <= transResource.getGpEndTime().getTime())) {
                model.addAttribute("endTime", transResource.getGpEndTime().getTime() + 1000 * 60 * 4);
            } else {
                long offerTime = maxOffer.getOfferTime();
                long gpEndTime = transResource.getGpEndTime().getTime();
                if (offerTime < gpEndTime) {
                    model.addAttribute("endTime", transResource.getGpEndTime().getTime() + 1000 * 60 * 4);
                } else {
                    model.addAttribute("endTime", offerTime + 1000 * 60 * 4);
                }
            }
        }

        model.addAttribute("userId", userId);

        model.addAttribute("ggNum", StringUtils.isNotBlank(transResource.getGgId()) ? transCrggService.getTransCrgg(transResource.getGgId()).getGgNum() : "");

        List thumbnailsPathList = Lists.newArrayList();
       /* List<TransFile> thumbnailFileList = transFileService.getTransFileThumbnails(id, Constants.CLIENT_THUMBNAIL_RESOLUTION);
        if(thumbnailFileList!=null&&thumbnailFileList.size()>0){
            for(TransFile thumbnailFile:thumbnailFileList){
                thumbnailsPathList.add(Constants.IMAGE_BASE_PATH+thumbnailFile.getFileId());
            }
        }else{
            thumbnailsPathList.add(Constants.BLANK_IMAGE_PATH);
        }*/
        /*model.addAttribute("thumbnails",thumbnailsPathList);
        model.addAttribute("attachments", transFileService.getTransFileAttachments(id));
        Map attachmentCategory = attachmentCategoryService.getTransResourceAttachmentCategory();
        attachmentCategory.put(Constants.FileType.QT.getCode(), Constants.FileType.QT.toString());
        model.addAttribute("attachmentCategory", attachmentCategory);
        model.addAttribute("crggAttachments", transFileService.getTransFileAttachments(transResource.getGgId()));*/
        return "view";
    }


  /*  @RequestMapping("/view/offer/list.f")
    public String offerList(@PageDefault(value=10) Pageable page,@RequestParam(value = "resourceId",required = true)String resourceId,Model model) {
        Page<TransResourceOffer> transResourceOffers= clientService.findTransResourceOffers(page,resourceId);
        model.addAttribute("transResourceOffers", transResourceOffers);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("resource", transResourceService.getTransResource(resourceId));
        model.addAttribute("userId",SecUtil.getLoginUserId());
        return "common/view-offer-list";
    }
*/
   /* @RequestMapping("/offer")
    *//*@AuditServiceLog(category = Constants.LogCategory.CUSTOM_OFFER,producer = Constants.LogProducer.CLIENT,
            description = "用户报价")*//*
    public @ResponseBody
    String offer(String id,String offer,int type,CaSignerX caSignerX,String userId) throws Exception {
        //String userId= SecUtil.getLoginUserId();
        try{
            if(false){
               *//* boolean signatureValid = validateSignature(caSignerX);
                if(!signatureValid)
                    return "报价数据验证错误，请检查CA数字证书环境";
                String sxInput = new String(Base64.decodeBase64(caSignerX.getSxinput()), Charsets.CHARSET_UTF8);
                Map sxInputMap = JSON.parseObject(sxInput);
                id = String.valueOf(sxInputMap.get("id"));
                offer = String.valueOf(sxInputMap.get("offer"));
                type = NumberUtils.createInteger(String.valueOf(sxInputMap.get("type")));*//*
            }
            if (StringUtils.isNotBlank(offer)) {
                    //去掉同步锁
                    TransResourceOffer resourceOffer=clientService.acceptResourceOffer(userId, id, Double.parseDouble(offer),type);
                    if (resourceOffer!=null){

                        return "true";
                    }else{
                        return "接受报价错误！";
                    }

            }else{
                return "报价为空！";
            }
        }catch (Exception ex){
            return ex.getMessage();
        }
    }*/

 /*   @RequestMapping("/offerLimit.f")
    public @ResponseBody
    String offerLimit(String id) throws Exception {
        String userId= SecUtil.getLoginUserId();
        try{
            if (StringUtils.isNotBlank(id)) {
                TransResourceApply transResourceApply=
                        transResourceApplyService.getTransResourceApplyByUserId(userId,id);
                transResourceApply.setLimitTimeOffer(true);
                transResourceApplyService.saveTransResourceApply(transResourceApply);
            }
        }catch (Exception ex){
            return ex.getMessage();
        }
        return "true";
    }*/


    @RequestMapping("/getoffer")
    public @ResponseBody
    DeferredResult<String> deferredResult(String id, String time) {
        DeferredResult<String> clientResult = new DeferredResult<String>(TIMEOUT, "timeout");
        TransResource transResource = transResourceService.getTransResource(id);
        if (transResource.getResourceEditStatus() != Constants.RESOURCE_EDIT_STATUS_RELEASE ||
                transResource.getResourceStatus() == Constants.RESOURCE_STATUS_CHENG_JIAO ||
                transResource.getResourceStatus() == Constants.RESOURCE_STATUS_LIU_BIAO) {
            clientResult.setResult("refresh");
        } else {
            List<TransResourceOffer> offers = clientService.getOfferList(id, Long.parseLong(time));
            if (offers.size() > 0) {
                writeClient(clientResult, offers);
            } else {

                return clientResult;
            }
        }
        return clientResult;
    }

    private void writeClient(DeferredResult<String> client, List<TransResourceOffer> resourceOfferList) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("time", Calendar.getInstance().getTime().getTime());
        resultMap.put("result", resourceOfferList);
        client.setResult(JSON.toJSONString(resultMap));
    }

    /*  @RequestMapping("/view/crgg.f")
      public String viewCrgg(@RequestParam(value = "id", required = true)String id,Model model){
          TransResource transResource= transResourceService.getTransResource(id);
          String content = "";
          if(StringUtils.isNotBlank(transResource.getGgId()))
              content= transCrggService.getTransCrgg(transResource.getGgId()).getGgContent();

          model.addAttribute("content", HtmlEscape.unescapeHtml(content));
          return "crgg";
      }
  */
    @RequestMapping("/geometry")
    public Object viewResourceGeometry(@RequestParam(value = "id", required = true) String id, Model model) {
        TransResource transResource = transResourceService.getTransResource(id);
        return geometry2Features(transResource);
    }

    private List geometry2Features(TransResource transResource) {
        Map feature = Maps.newHashMap();
        Map properties = Maps.newHashMap();
        properties.put("popupContent", transResource.getResourceLocation());
        properties.put("title", transResource.getResourceCode());
        feature.put("geometry", correctGeoemtry(JSON.parse(transResource.getGeometry())));
        feature.put("properties", properties);
        feature.put("type", "Feature");
        return Lists.newArrayList(feature);
    }

    private Object correctGeoemtry(Object geometry) {
        if (geometry == null) {
            return null;
        }
        Map geometryMap = (Map) geometry;
        List<List> rings = (List) geometryMap.get("coordinates");
        for (List ring : rings) {
            List<List> points = ring;
            for (List coordinates : points) {
                Double xCoord = Double.valueOf(String.valueOf(coordinates.get(0)));
                Double yCoord = Double.valueOf(String.valueOf(coordinates.get(1)));
                Double[] newCoords = transGpsOffsetService.correctPoint(xCoord, yCoord);
                coordinates.set(0, newCoords[0]);
                coordinates.set(1, newCoords[1]);
            }
        }
        return geometry;
    }

 /*   private boolean validateSignature(CaSignerX caSignerX) throws Exception {
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
    }*/
}

