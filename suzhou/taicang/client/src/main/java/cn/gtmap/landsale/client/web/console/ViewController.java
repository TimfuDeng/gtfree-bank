package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.client.util.HTMLSpirit;
import cn.gtmap.landsale.log.AuditServiceLog;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.mq.ResourceOfferQueueService;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.NumberUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.unbescape.html.HtmlEscape;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
public class ViewController {
    private static final long _TimeOut=72*1000;  //1.2分钟超时时间，参考taobao

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
    OneTargetService oneTargetService;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    @Autowired
    CaSvsService caSvsService;

    @RequestMapping("/view")
    public String index(@RequestParam(value = "id",required = true)String id,Model model) throws Exception{
        TransResource transResource= transResourceService.getTransResource(id);
        model.addAttribute("resource", transResource);

        TransResourceInfo transResourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(id);
        if(transResourceInfo==null)
            transResourceInfo = new TransResourceInfo();
        model.addAttribute("resourceInfo",transResourceInfo);
        //获得报价列表（15条）
        List<TransResourceOffer> resourceOffers= clientService.getOfferList(id,-1);
        model.addAttribute("resourceOffers", resourceOffers);
        model.addAttribute("cDate", Calendar.getInstance().getTime().getTime());
        //获得当前最高报价
        TransResourceOffer maxOffer= (resourceOffers.size()>0) ? maxOffer=resourceOffers.get(0):null;
        model.addAttribute("maxOffer", maxOffer);

        if(StringUtils.isNotBlank(transResource.getOfferId())){
            TransResourceOffer successOffer= transResourceOfferService.getTransResourceOffer(transResource.getOfferId());
            model.addAttribute("successOffer", successOffer);
        }

        if(transResource.getResourceStatus() == 30){
            TransResourceApply transResourceApplyMaxPrice = transResourceApplyService.getTransResourceApplyByUserId(maxOffer.getUserId(),id);
            model.addAttribute("transResourceApplyMaxPrice", transResourceApplyMaxPrice);
        }

        //当前总价的最高报价,不包括多指标
        TransResourceOffer maxOfferPrice= transResourceOfferService.getMaxOfferFormPrice(id);
        model.addAttribute("maxOfferPrice", maxOfferPrice);
        //判断用户和当前地块的购买状态
        if (SecUtil.isLogin()){
            TransResourceApply transResourceApply=transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(),id);
            model.addAttribute("transResourceApply", transResourceApply);
        }
        //如果在限时竞价阶段，要判断当前限时竞价的截至时间
        if(transResource.getResourceStatus()== Constants.ResourceStatusJingJia){
            //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int countDownSec = 240;
            if(transResource.getCountDownSec() != null && transResource.getCountDownSec() != 240){
                countDownSec = transResource.getCountDownSec();
            }

            if (maxOffer == null || maxOffer.getOfferType() == Constants.OfferTypeGuaPai || (maxOffer.getOfferType() == Constants.OfferXianjia && maxOffer.getOfferTime() <= transResource.getGpEndTime().getTime())) {
                model.addAttribute("endTime", transResource.getGpEndTime().getTime() + 1000 * countDownSec);
                //System.out.println("view_index彻底截止a===>" + df.format(new Date(transResource.getGpEndTime().getTime() + 1000 * countDownSec)));
            } else {
                long offerTime = maxOffer.getOfferTime();
                long gpEndTime = transResource.getGpEndTime().getTime();
                if (offerTime < gpEndTime) {
                    model.addAttribute("endTime", transResource.getGpEndTime().getTime() + 1000 * countDownSec);
                    //System.out.println("view_index彻底截止b===>" + df.format(transResource.getGpEndTime().getTime() + 1000 * countDownSec));
                } else {
                    model.addAttribute("endTime", offerTime + 1000 * countDownSec);
                    //System.out.println("view_index彻底截止c===>" + df.format(offerTime + countDownSec));
                }

                //model.addAttribute("endTime", maxOffer.getOfferTime() + 1000 * countDownSec);
            }
        }
        model.addAttribute("userId", SecUtil.getLoginUserId());

        String ggNum = "";
        TransCrgg transCrgg;
        if(StringUtils.isNotBlank(transResource.getGgId())){
            transCrgg = transCrggService.getTransCrgg(transResource.getGgId());
            ggNum = transCrgg.getGgNum();
        } else {
            transCrgg = new TransCrgg();
        }
        model.addAttribute("transCrgg", transCrgg);

        model.addAttribute("ggNum", ggNum);

        List thumbnailsPathList = Lists.newArrayList();
        List<TransFile> thumbnailFileList = transFileService.getTransFileThumbnails(id, Constants.CLIENT_THUMBNAIL_RESOLUTION);
        if(thumbnailFileList!=null&&thumbnailFileList.size()>0){
            for(TransFile thumbnailFile:thumbnailFileList){
                thumbnailsPathList.add(Constants.IMAGE_BASE_PATH+thumbnailFile.getFileId());
            }
        }else{
            thumbnailsPathList.add(Constants.BLANK_IMAGE_PATH);
        }
        model.addAttribute("thumbnails",thumbnailsPathList);
        model.addAttribute("attachments", transFileService.getTransFileAttachments(id));
        Map attachmentCategory = attachmentCategoryService.getTransResourceAttachmentCategory();
        attachmentCategory.put(Constants.FileType.QT.getCode(), Constants.FileType.QT.toString());
        model.addAttribute("attachmentCategory", attachmentCategory);
        model.addAttribute("crggAttachments", transFileService.getTransFileAttachments(transResource.getGgId()));

        Pageable page = new PageRequest();// 默认取前20条数据【王建明 2015-09-07 9:26】
        Page<TransResourceOffer> transResourceOfferPage= transResourceOfferService.getResourceOfferPageByUserId(id, SecUtil.getLoginUserId(), page);
        model.addAttribute("myResourceOfferCount", transResourceOfferPage.getTotalCount());// 获取我的竞价次数，用于页面判断4分钟限时竞价环节是否可以报价【王建明 2015-09-07 11:07】

        // 获取该地块的中止价格并带至页面【王建明 2017/2/22 0022 上午 11:23】
        try {
            OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(id);
            if (oneTarget != null)
                model.addAttribute("stopPrice", oneTarget.getPriceMin());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "view";
    }


    @RequestMapping("/view/offer/list.f")
    public String offerList(@PageDefault(value=10) Pageable page,@RequestParam(value = "resourceId",required = true)String resourceId,Model model) {
        if(StringUtils.isNotBlank(resourceId)){
            try {
                resourceId = HTMLSpirit.delHTMLTag(URLDecoder.decode(resourceId,"UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Page<TransResourceOffer> transResourceOffers= clientService.findTransResourceOffers(page,resourceId);
        model.addAttribute("transResourceOffers", transResourceOffers);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("resource", transResourceService.getTransResource(resourceId));
        model.addAttribute("userId",SecUtil.getLoginUserId());
        return "common/view-offer-list";
    }

    @RequestMapping("/offer.f")
    @AuditServiceLog(category = Constants.LogCategory.CUSTOM_OFFER,producer = Constants.LogProducer.CLIENT,
            description = "用户报价")
    public @ResponseBody String offer(String id,String offer,int type,CaSignerX caSignerX) throws Exception{
        String userId= SecUtil.getLoginUserId();
        try{
            if(caEnabled){
                boolean signatureValid = validateSignature(caSignerX);
                if(!signatureValid)
                    return "报价数据验证错误，请检查CA数字证书环境";
                String sxInput = new String(Base64.decodeBase64(caSignerX.getSxinput()), Charsets.CHARSET_UTF8);
                Map sxInputMap = JSON.parseObject(sxInput);
                id = String.valueOf(sxInputMap.get("id"));
                offer = String.valueOf(sxInputMap.get("offer"));
                type = NumberUtils.createInteger(String.valueOf(sxInputMap.get("type")));
            }
            if (StringUtils.isNotBlank(offer)) {
                synchronized(this) {
                    TransResourceOffer resourceOffer = clientService.acceptResourceOffer(userId, id, Double.parseDouble(offer), type);
                    if (resourceOffer != null) {
//                    resourceQueueContainer.acceptNewOffer(resourceOffer);
                        return "true";
                    } else {
                        return "接受报价错误！";
                    }
                }
            }else{
                return "报价为空！";
            }
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    @RequestMapping("/offerLimit.f")
    public @ResponseBody String offerLimit(String id) throws Exception{
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
    }


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

    @RequestMapping("/view/resource_edit_status.f")
    @ResponseBody
    public Object viewResource(@RequestParam(value = "id", required = true)String id,Model model){
        TransResource transResource= transResourceService.getTransResource(id);
        return transResource.getResourceEditStatus();
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

