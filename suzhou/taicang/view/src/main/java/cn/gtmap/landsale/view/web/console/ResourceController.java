package cn.gtmap.landsale.view.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.mq.ResourceOfferQueueService;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.unbescape.html.HtmlEscape;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by jiff on 14-6-26.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    private static final long _TimeOut=72*1000;  //1.2分钟超时时间，参考taobao

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    ClientService clientService;

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
    ResourceOfferQueueService resourceOfferQueueService;

    @Autowired
    OneTargetService oneTargetService;

    @RequestMapping("index")
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
            if(maxOffer==null || maxOffer.getOfferType()==Constants.OfferTypeGuaPai || (maxOffer.getOfferType()==Constants.OfferXianjia && maxOffer.getOfferTime()<=transResource.getGpEndTime().getTime())){
                model.addAttribute("endTime", transResource.getGpEndTime().getTime()+1000*60*4);
            }else{
                model.addAttribute("endTime", maxOffer.getOfferTime()+1000*60*4);
            }
        }
        model.addAttribute("userId", SecUtil.getLoginUserId());

        model.addAttribute("ggNum", StringUtils.isNotBlank(transResource.getGgId()) ? transCrggService.getTransCrgg(transResource.getGgId()).getGgNum() : "");

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


    @RequestMapping("offer/list.f")
    public String offerList(@PageDefault(value=10) Pageable page,@RequestParam(value = "resourceId",required = true)String resourceId,Model model) {
        Page<TransResourceOffer> transResourceOffers= clientService.findTransResourceOffers(page, resourceId);
        model.addAttribute("transResourceOffers", transResourceOffers);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("userId",SecUtil.getLoginUserId());
        model.addAttribute("resource", transResourceService.getTransResource(resourceId));
        return "common/view-offer-list";
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

    @RequestMapping("/getoffer_ajax.f")
    public @ResponseBody  Map<String,Object> getoffer_ajax(String id,String time) {
            Map<String,Object> resultMap= Maps.newHashMap();
        TransResource transResource= transResourceService.getTransResource(id);
        if (transResource.getResourceEditStatus()!=Constants.ResourceEditStatusRelease ||
                transResource.getResourceStatus()==Constants.ResourceStatusChengJiao ||
                transResource.getResourceStatus()==Constants.ResourceStatusLiuBiao    ){
            resultMap.put("result", "refresh");
        }else {
            long timeLongValue = Long.parseLong(time);
            List<TransResourceOffer> offers = clientService.getOfferList(id, timeLongValue);
            if(offers != null && !offers.isEmpty()){
                timeLongValue = offers.get(0).getOfferTime();
            }
            resultMap.put("time", timeLongValue);
            resultMap.put("result", offers);
        }
        return resultMap;
    }

    private void writeClient(DeferredResult<String> client,List<TransResourceOffer> resourceOfferList){
        Map<String,Object> resultMap= Maps.newHashMap();
        resultMap.put("time", Calendar.getInstance().getTime().getTime());
        resultMap.put("result", resourceOfferList);
        client.setResult(JSON.toJSONString(resultMap));
    }


    @RequestMapping("crgg.f")
    public String viewCrgg(@RequestParam(value = "id", required = true)String id,Model model){
        TransResource transResource= transResourceService.getTransResource(id);
        String content = "";
        if(StringUtils.isNotBlank(transResource.getGgId()))
            content= transCrggService.getTransCrgg(transResource.getGgId()).getGgContent();

        model.addAttribute("content", HtmlEscape.unescapeHtml(content));
        return "crgg";
    }

    @RequestMapping("geometry.f")
    @ResponseBody
    public Object viewResourceGeometry(@RequestParam(value = "id", required = true)String id,Model model){
        TransResource transResource= transResourceService.getTransResource(id);
        return geometry2Features(transResource);
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
}

