package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.web.ResourceQueryParam;
import cn.gtmap.landsale.core.register.TransRedisClient;
import cn.gtmap.landsale.core.service.*;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author liushaoshuai on 2017/7/6.
 */
@RestController
@RequestMapping(value = "/resource")
public class ResourceController {

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    ClientService clientService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransCrggService transCrggService;

    @Autowired
    TransRedisClient redisClient;

    @Autowired
    DealNoticeService dealNoticeService;

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelMap view(@RequestParam(value = "id", required = true) String id, String userId) throws Exception {
        ModelMap model = new ModelMap();
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

        // 最高报价 加上 加价幅度
//        if (maxOffer != null && transResource.getAddOffer() != null) {
//            model.addAttribute("newOffer", maxOffer.getOfferPrice() + transResource.getAddOffer());
//        }
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
        return model;
    }


    @RequestMapping("/index")
    public List<TransResource> index(@RequestBody Map map) throws Exception {
        ResourceQueryParam param = JSONObject.parseObject(map.get("ResourceQueryParam").toString(), ResourceQueryParam.class);
        int offset = JSONObject.parseObject(map.get("offset").toString(), int.class);
        int size = JSONObject.parseObject(map.get("size").toString(), int.class);
        Pageable page = new PageRequest(offset, size);
        //ModelMap model = new ModelMap();
        param.setDisplayStatus(-1);
        param.setGtResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_PRE_RELEASE);
        if (param.getResourceStatus() == Constants.RESOURCE_STATUS_CHENG_JIAO) {
            param.setResourceStatus(-1);
            param.setGtResourceStatus(Constants.RESOURCE_STATUS_GONG_GAO);
        }
        String regions = "";
        Page<TransResource> transResourcePage = transResourceService.findTransResources(param, regions, page);
        return transResourcePage.getItems();
    }

    /**
     * 获得出让地块
     * @param resourceId 地块Id
     * @return
     */
    @RequestMapping("/getTransResource")
    public TransResource getTransResource(@RequestParam("resourceId") String resourceId) {
        return transResourceService.getTransResource(resourceId);
    }

    /**
     * 查询资源、分页，Admin用
     * @param title
     * @param status
     * @param pageable
     * @param ggId
     * @param regionCodes
     * @return
     */
    @RequestMapping(value = "/findTransResourcesByEditStatus")
    Page<TransResource> findTransResourcesByEditStatus(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "status", required = false) int status, @RequestParam(value = "ggId", required = false) String ggId, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable) {
        return transResourceService.findTransResourcesByEditStatus(title, status, ggId, regionCodes, pageable);
    }

    /**
     * 保存出让地块对象
     * @param transResource 出让地块
     * @return ResponseMessage<TransResource>
     */
    @RequestMapping("/saveTransResource")
    public ResponseMessage<TransResource> saveTransResource(@RequestBody TransResource transResource) {
        return transResourceService.saveTransResource(transResource);
    }

    //以下是大屏展示的方法
    /**
     * 获取可以显示在大屏幕上的地块
     * @param title
     * @param displayStatus
     * @param regionCodes
     * @param request
     * @return
     */
    @RequestMapping(value = "/findDisplayResource")
    Page<TransResource> findDisplayResource(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "displayStatus", required = false) String displayStatus, @RequestParam(value = "regionCodes",required = false) String regionCodes, @RequestBody Pageable request) {
        return transResourceService.findDisplayResource(title, displayStatus, regionCodes, request);
    }

    /**
     * 更新地块在交易大屏幕上的显示状态
     * @param resourceId
     * @param displayStatus
     */
    @RequestMapping(value = "/updateTransResourceDisplayStatus")
    ResponseMessage<TransResource> updateTransResourceDisplayStatus(@RequestParam("resourceId") String resourceId, @RequestParam("displayStatus") int displayStatus) {
        return transResourceService.updateTransResourceDisplayStatus(resourceId, displayStatus);
    }

    /**
     * 根据显示状态查找地块
     * @param displayStatus
     * @return
     */
    @RequestMapping(value = "/findTransResourcesByDisplayStatus")
    List<TransResource> findTransResourcesByDisplayStatus(@RequestParam("displayStatus") int displayStatus) {
        return transResourceService.findTransResourcesByDisplayStatus(displayStatus);
    }

    /**
     * 统计未审核，报名中，已通过
     * @param transResourcePage
     * @return
     */
    @RequestMapping(value = "/countPassOrUnpass")
    Page<TransResource> countPassOrUnpass(@RequestBody Page<TransResource> transResourcePage) {
       return transResourceService.countPassOrUnpass(transResourcePage);
    }

    @RequestMapping(value = "/getResourceByCode")
    TransResource getResourceByCode(@RequestParam(value = "transResourceCode",required = true) String transResourceCode) {
        return transResourceService.getResourceByCode(transResourceCode);
    }


    /**
     * 获得出让地块
     * @param resourceCode 地块Code
     * @return
     */
    @RequestMapping("/getTransResourceByCode")
    public TransResource getTransResourceByCode(@RequestParam("resourceCode") String resourceCode) {
        return transResourceService.getTransResourceByCode(resourceCode);
    }

    /**
     * 获得出让地块集合
     * @param resourceCode 地块Code
     * @return
     */
    @RequestMapping("/getResourcesByCode")
    public List<TransResource> getResourcesByCode(@RequestParam("resourceCode") String resourceCode) {
        return transResourceService.getResourcesByCode(resourceCode);
    }

    /**
     * 根据公告Id获取公告地块
     * @param ggId
     * @return
     */
    @RequestMapping("/findTransResource")
    public List<TransResource> findTransResource(@RequestParam("ggId") String ggId) {
        return transResourceService.findTransResource(ggId);
    }

    /**
     * 查询资源，分页,client用 首页
     * @param queryParam
     * @param regionCodes
     * @return
     */
    @RequestMapping("/findTransResources")
    public Page<TransResource> findTransResources(@RequestBody ResourceQueryParam queryParam, @RequestParam(value = "regionCodes", required = false) String regionCodes) {
        return transResourceService.findTransResources(queryParam, regionCodes, queryParam.getPage());
    }

    /**
     * 查询资源，分页,client用 当前用户参与地块
     * @param queryParam
     * @param regionCodes
     * @return
     */
    @RequestMapping("/findTransResourcesByUser")
    public Page<TransResource> findTransResourcesByUser(@RequestBody ResourceQueryParam queryParam, @RequestParam(value = "userId") String userId, @RequestParam(value = "regionCodes", required = false) String regionCodes) {
        return transResourceService.findTransResourcesByUser(queryParam, userId, regionCodes, queryParam.getPage());
    }

    /**
     * 查询资源 client用 当前用户 可以报价的地块
     * @param userId
     * @return
     */
    @RequestMapping("/findResourcesForPriceByUser")
    public List<TransResource> findResourcesForPriceByUser(@RequestParam(value = "userId") String userId) {
        return transResourceService.findResourcesForPriceByUser(userId);
    }

    /**
     * 更新地块状态
     * @param resource
     * @param status
     * @return
     */
    @RequestMapping("/saveTransResourceStatus")
    public ResponseMessage<TransResource> saveTransResourceStatus(@RequestBody TransResource resource, @RequestParam(value = "status") int status){
        return transResourceService.saveTransResourceStatus(resource,status);
    }

    /**
     * 查询摇号资源、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    @RequestMapping("/findYhResourcesByEditStatus")
    public Page<TransResource> findYhResourcesByEditStatus(@RequestParam(value = "title",required = false) String title,@RequestParam(value = "status",required = false) int status,@RequestParam(value = "ggId",required = false) String ggId,@RequestParam(value = "regionCodes",required = false) String regionCodes,@RequestBody Pageable request){
        return transResourceService.findYhResourcesByEditStatus(title,status,ggId,regionCodes,request);
    }

    /**
     * 查询一次报价地块列表、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    @RequestMapping("/findYCBJResourcesByEditStatus")
    public Page<TransResource> findYCBJResourcesByEditStatus(@RequestParam(value = "title",required = false) String title,@RequestParam(value = "status",required = false) int status,@RequestParam(value = "ggId",required = false) String ggId,@RequestParam(value = "regionCodes",required = false) String regionCodes,@RequestBody Pageable request){
        return transResourceService.findYCBJResourcesByEditStatus(title,status,ggId,regionCodes,request);
    }

    /**
     * 获得正在交易的地块，成交和流拍的除外
     * @return
     */
    @RequestMapping("/getTransResourcesOnRelease")
    public List<TransResource> getTransResourcesOnRelease(){
        return transResourceService.getTransResourcesOnRelease();
    }

    /**
     * 查询已成交资源、分页，Admin用
     * @param title
     * @param status
     * @param request
     * @param ggId
     * @param regionCodes
     * @return
     */
    @RequestMapping("/findDealTransResourcesByEditStatus")
    public Page<TransResource> findDealTransResourcesByEditStatus(@RequestParam(value = "title",required = false) String title, @RequestParam(value = "status",required = false) int status, @RequestParam(value = "resourceStatus",required = false) int resourceStatus, @RequestParam(value = "ggId",required = false) String ggId, @RequestParam(value = "regionCodes",required = false) String regionCodes, @RequestBody Pageable request){
        return transResourceService.findDealTransResourcesByEditStatus(title, status, resourceStatus, ggId, regionCodes, request);
    }

    /**
     * 地块的中止、终止和成交公告加载
     * @param resourceId
     * @return
     */
    @RequestMapping("/resourceNotice")
    public List resourceNotice(@RequestParam(value = "resourceId") String resourceId){
        TransResource transResource = transResourceService.getTransResource(resourceId);
        if(transResource != null) {
            //成交状态
            if(Constants.RESOURCE_STATUS_CHENG_JIAO == transResource.getResourceStatus()){
                List<DealNotice> dealNoticeList = dealNoticeService.findDealNoticeByResourceCode(transResource.getResourceCode());
                return dealNoticeList;
            }

            if(Constants.RESOURCE_EDIT_STATUS_BREAK == transResource.getResourceEditStatus()){
                List<TransCrgg> transCrggs = transCrggService.findTransCrggByGGTypeAndResourceCode(Constants.AFFICHE_TYPE_ZZ,transResource.getResourceCode());
                return transCrggs;
            }

            if(Constants.RESOURCE_EDIT_STATUS_STOP == transResource.getResourceEditStatus()){
                List<TransCrgg> transCrggs = transCrggService.findTransCrggByGGTypeAndResourceCode(Constants.AFFICHE_TYPE_ZZ_2,transResource.getResourceCode());
                return transCrggs;
            }
        }
        return null;
    }
}
