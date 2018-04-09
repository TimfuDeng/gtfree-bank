package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.Charsets;
import cn.gtmap.landsale.admin.core.TransResourceContainer;
import cn.gtmap.landsale.admin.register.*;
import cn.gtmap.landsale.admin.util.RMBUtils;
import cn.gtmap.landsale.admin.util.ResourceUtil;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.util.*;

/**
 * 地块 服务
 * @author zsj
 * @version v1.0, 2017/10/19
 */
@Controller
@RequestMapping("/resource")
public class TransResourceController {

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceInfoClient transResourceInfoClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;


    @Autowired
    TransResourceSonClient transResourceSonClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransBankClient transBankClient;

    @Autowired
    TransOrganizeClient transOrganizeClient;

    @Autowired
    TransRegionClient transRegionClient;

    @Autowired
    AttachmentCategoryClient attachmentCategoryClient;

    @Autowired
    LandUseDictClient landUseDictClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransResourceContainer transResourceTimer;

    @Autowired
    YHClient yhClient;

    @Autowired
    TransUserUnionClient transUserUnionClient;

    @Autowired
    org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    ServletContext servletContext;


    /**
     * 列表页方法
     * @param title
     * @param status
     * @param ggId
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(String title, String status, String ggId, @PageDefault(value = 5) Pageable pageable, Model model) {
        int resourceEditStatus = -1;
        if (StringUtils.isNotBlank(status)) {
            resourceEditStatus = Integer.parseInt(status);
        }
        String regionCodes = null;
        if (!SecUtil.isAdmin()) {
            regionCodes = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResourceList = transResourceClient.findTransResourcesByEditStatus(title, resourceEditStatus, ggId, regionCodes, pageable);
        model.addAttribute("transResourceList", transResourceList);
        model.addAttribute("title", title);
        model.addAttribute("ggId", ggId);
        model.addAttribute("status", resourceEditStatus);
        List values = new ArrayList<Map>();
        for (TransResource transResource : transResourceList.getItems()) {
            Map valueMap = new HashMap();
            // 万元情况 算 平均地价 万元/亩
            if (Constants.OFFER_UNIT.WY.equals(transResource.getOfferUnit())) {
                // 平均地价(元/平方米) 最高限价*10000/面积 不要小数
                valueMap.put("price1",BigDecimal.valueOf(transResource.getBeginOffer()).multiply(new BigDecimal(10000)).divide(BigDecimal.valueOf(transResource.getCrArea()), 10, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN));
                valueMap.put("unit1","元/平方米");
                //万元/亩 起始价/出让面积*10000/15
                valueMap.put("price2",BigDecimal.valueOf(transResource.getBeginOffer()).divide(BigDecimal.valueOf(transResource.getCrArea()), 10, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(10000)).divide(new BigDecimal(15), 10, BigDecimal.ROUND_HALF_DOWN).setScale(4, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","万元/亩");
                // 元/平方米(地面价)情况 算总价 万元/亩
            } else if (Constants.OFFER_UNIT.Y_M2_DMJ.equals(transResource.getOfferUnit())) {
                //总价 /万元
                valueMap.put("price1",BigDecimal.valueOf(transResource.getBeginOffer()).multiply(BigDecimal.valueOf(transResource.getCrArea())).divide(new BigDecimal(10000)));
                valueMap.put("unit1","万元");
                //万元/亩
                valueMap.put("price2",BigDecimal.valueOf(transResource.getBeginOffer()).divide(new BigDecimal(10000)).multiply(new BigDecimal(10000)).divide(new BigDecimal(15),10,BigDecimal.ROUND_HALF_DOWN).setScale(4, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","万元/亩");
                // 元/平方米(楼面价)情况 算总价 万元/亩
            } else if (Constants.OFFER_UNIT.Y_M2_LMJ.equals(transResource.getOfferUnit())) {
                //总价 楼面价*建筑面积/10000  /万元
                valueMap.put("price1",BigDecimal.valueOf(transResource.getBeginOffer()).multiply(BigDecimal.valueOf(transResource.getBuildingArea())).divide(new BigDecimal(10000)));
                valueMap.put("unit1","万元");
                //万元/亩 楼面价*建筑面积（总价）/出让面积/10000/15
                valueMap.put("price2",BigDecimal.valueOf(transResource.getBeginOffer()).multiply(BigDecimal.valueOf(transResource.getBuildingArea())).divide(new BigDecimal(10000)).divide(BigDecimal.valueOf(transResource.getCrArea()),10,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(10000)).divide(new BigDecimal(15),10,BigDecimal.ROUND_HALF_DOWN).setScale(4, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","万元/亩");
                // 万元/亩情况 算总价 平均价（万元/平方米）
            } else if (Constants.OFFER_UNIT.WY_MU.equals(transResource.getOfferUnit())) {
                //总价 平均价*面积
                valueMap.put("price1",BigDecimal.valueOf(transResource.getBeginOffer()).multiply(new BigDecimal(15)).divide(new BigDecimal(10000)).multiply(BigDecimal.valueOf(transResource.getCrArea())));
                valueMap.put("unit1","万元");
                //平均价 最高限价*15/10000*10000
                valueMap.put("price2",BigDecimal.valueOf(transResource.getBeginOffer()).multiply(new BigDecimal(15)).setScale(0, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","元/平方米");
            }
            values.add(valueMap);
        }
        model.addAttribute("values", values);
        return "resource/resource-list";
    }

    @RequestMapping("/getBankList")
    public String getBankList(String resourceId, String organizeId, Model model) {
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByOrganize(organizeId);
        if (transRegionList != null && transRegionList.size() > 0) {
            StringBuilder regionCodes = new StringBuilder();
            for (TransRegion transRegion : transRegionList) {
                if (StringUtils.isEmpty(regionCodes)) {
                    regionCodes.append(transRegion.getRegionCode());
                } else {
                    regionCodes.append("," + transRegion.getRegionCode());
                }
            }
            List<TransBank> bankList = transBankClient.getBankListByRegion(regionCodes.toString());
            model.addAttribute("bankList", bankList);
        }
        if (StringUtils.isNotEmpty(resourceId)) {
            TransResource transResource = transResourceClient.getTransResource(resourceId);
            model.addAttribute("transResource", transResource);
        }
        return "resource/resource-bank";
    }

    /**
     * 添加页面初始化
     * @param ggId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public String add(String ggId, Model model, HttpServletRequest request) {
        // 行政部门
        List<TransOrganize> transOrganizeList;
        if (SecUtil.isAdmin()) {
            transOrganizeList = transOrganizeClient.findTransOrganizeList(null, null);
        } else {
            transOrganizeList = SecUtil.getLoginOrganizeToSession(request);
        }
        model.addAttribute("transOrganizeList", transOrganizeList);
        // 银行
//        List<TransBank> bankList = transBankClient.getBankListByRegion(SecUtil.getLoginUserRegionCodes());
//        model.addAttribute("bankList", bankList);
        // 初始化地块的一些时间
        TransResource transResource = ResourceUtil.buildNewResource();
        transResource.setGgId(ggId);
        TransCrgg transCrgg = transCrggClient.getTransCrgg(ggId);
        transResource.setGpBeginTime(transCrgg.getGgBeginTime());
        transResource.setBmBeginTime(transCrgg.getGgBeginTime());
        transResource.setBmEndTime(transCrgg.getGgEndTime());
        transResource.setBzjBeginTime(transCrgg.getGgBeginTime());
        model.addAttribute("transResource", transResource);
        return "resource/resource-add";
    }

    /**
     * 添加地块
     * @param transResource
     * @param transResourceInfo
     * @return
     */
    @RequestMapping("/addResource")
    @ResponseBody
    public ResponseMessage<TransResource> addResource(TransResource transResource, TransResourceInfo transResourceInfo) {
        //所有地块编号都唯一
        List<TransResource> tsResource=transResourceClient.getResourcesByCode(transResource.getResourceCode());
        if (tsResource != null && tsResource.size() > 0){
            return new ResponseMessage(false,"地块编号不能重复，请重新填写！！");
        }
        transResource.setResourceId(null);
        if (transResource.getShowTime() == null) {
            transResource.setShowTime(Calendar.getInstance().getTime());
        }
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByOrganize(transResource.getOrganizeId());
        if (transRegionList != null && transRegionList.size() > 0) {
            transResource.setRegionCode(transRegionList.get(0).getRegionCode());
        } else {
            return new ResponseMessage<>(false, "选择的行政区部门未绑定所属行政区,请检查！");
        }
        transResource.setTransResourceInfo(transResourceInfo);
        ResponseMessage<TransResource> responseMessage = transResourceClient.saveTransResource(transResource);
        if (responseMessage.getFlag()) {
            try {
                //检查资源线程
                transResourceTimer.checkResource(transResource);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return responseMessage;

    }

    /**
     * 修改页面初始化
     * @param resourceId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/edit")
    public String edit(String resourceId, Model model, HttpServletRequest request) {
        // 行政部门
        List<TransOrganize> transOrganizeList;
        if (SecUtil.isAdmin()) {
            transOrganizeList = transOrganizeClient.findTransOrganizeList(null, null);
        } else {
            transOrganizeList = SecUtil.getLoginOrganizeToSession(request);
        }
        model.addAttribute("transOrganizeList", transOrganizeList);
        // 地块
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("transResource", transResource);
        // 地块扩展信息
        TransResourceInfo transResourceInfo = transResourceInfoClient.getTransResourceInfoByResourceId(resourceId);
        model.addAttribute("transResourceInfo", transResourceInfo);
        // 地块多用途信息
        List<TransResourceSon> transResourceSonList = transResourceSonClient.getTransResourceSonList(resourceId);
        model.addAttribute("transResourceSonList", transResourceSonList);
        // 附件类型
        Map attachmentCategory = attachmentCategoryClient.getTransResourceAttachmentCategory();
        model.addAttribute("attachmentCategory",attachmentCategory);
        // 银行
        List<TransBank> bankList = transBankClient.getBankListByRegion(transResource.getRegionCode());
        model.addAttribute("bankList", bankList);
        return "resource/resource-add";
    }

    /**
     * 修改地块
     * @param transResource
     * @param transResourceInfo
     * @return
     */
    @RequestMapping("/editResource")
    @ResponseBody
    public ResponseMessage<TransResource> editResource(TransResource transResource, TransResourceInfo transResourceInfo) {
        if (transResource.getShowTime() == null) {
            transResource.setShowTime(Calendar.getInstance().getTime());
        }
        List<TransRegion> transRegionList = transRegionClient.findTransRegionByOrganize(transResource.getOrganizeId());
        if (transRegionList != null && transRegionList.size() > 0) {
            transResource.setRegionCode(transRegionList.get(0).getRegionCode());
        } else {
            return new ResponseMessage<>(false, "选择的行政区部门未绑定所属行政区,请检查！");
        }
        transResource.setTransResourceInfo(transResourceInfo);
        ResponseMessage<TransResource> responseMessage = transResourceClient.saveTransResource(transResource);
        if (responseMessage.getFlag()) {
            try {
                //检查资源线程
                transResourceTimer.checkResource(transResource);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return responseMessage;
    }

    /**
     * 地块多用途添加 初始化
     * @param resourceId
     * @param model
     * @return
     */
    @RequestMapping("/redourcesSon")
    public String redourcesSon(String resourceId, Model model) {
        model.addAttribute("resourceId", resourceId);
        return "resource/resource-son";
    }

    /**
     * 添加地块多用途信息
     * @param transResourceSon
     * @return
     */
    @RequestMapping("/addResourceSon")
    @ResponseBody
    public ResponseMessage addResourceSon(TransResourceSon transResourceSon) {
        return transResourceSonClient.saveTransResourceSon(transResourceSon);
    }

    /**
     * 删除地块多用途信息
     * @param resourceSonId
     * @return
     */
    @RequestMapping("/deleteResourceSon")
    @ResponseBody
    public ResponseMessage deleteResourceSon(String resourceSonId) {
        return transResourceSonClient.deleteTransResourceSon(resourceSonId);
    }

    /**
     * 更新地块状态
     * @param resourceId
     * @param status
     * @return
     */
    @RequestMapping("/status/change")
    @ResponseBody
    public ResponseMessage changeStatus(String resourceId, int status) {
        if (StringUtils.isNotBlank(resourceId)) {
            TransResource transResource = transResourceClient.getTransResource(resourceId);
            //如果此时地块状态已经不是发布状态，那么需要刷新状态
            if(transResource.getResourceStatus() == Constants.RESOURCE_STATUS_CHENG_JIAO || transResource.getResourceStatus() == Constants.RESOURCE_STATUS_LIU_BIAO){
                return new ResponseMessage(false, "地块状态已发生改变，此次更改无效");
            }
            transResource.setResourceEditStatus(status);
            ResponseMessage<TransResource> responseMessage = transResourceClient.saveTransResource(transResource);
            if (responseMessage.getFlag()) {
                try {
                    //检查资源线程
                    transResourceTimer.checkResource(transResource);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return responseMessage;
        }
        return new ResponseMessage(false, "地块编号为空,请检查！");
    }

    /**
     * 刷新地块状态
     * @param resourceId
     * @param model
     * @return
     */
    @RequestMapping("/status/view")
    public String status(String resourceId, Model model) {
        TransResource transResource=null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceClient.getTransResource(resourceId);
        }
        model.addAttribute("resource", transResource);
        return "resource/resource-status";
    }

    /**
     * 查看 页面初始化
     * @param resourceId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public String view(String resourceId, Model model, HttpServletRequest request) {
        // 行政部门
        List<TransOrganize> transOrganizeList;
        if (SecUtil.isAdmin()) {
            transOrganizeList = transOrganizeClient.findTransOrganizeList(null, null);
        } else {
            transOrganizeList = SecUtil.getLoginOrganizeToSession(request);
        }
        model.addAttribute("transOrganizeList", transOrganizeList);
        // 地块
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("transResource", transResource);
        // 地块扩展信息
        TransResourceInfo transResourceInfo = transResourceInfoClient.getTransResourceInfoByResourceId(resourceId);
        model.addAttribute("transResourceInfo", transResourceInfo);
        // 地块多用途信息
        List<TransResourceSon> transResourceSonList = transResourceSonClient.getTransResourceSonList(resourceId);
        model.addAttribute("transResourceSonList", transResourceSonList);
        // 附件类型
        Map attachmentCategory = attachmentCategoryClient.getTransResourceAttachmentCategory();
        model.addAttribute("attachmentCategory",attachmentCategory);
        // 银行
        List<TransBank> bankList = transBankClient.getBankListByRegion(transResource.getRegionCode());
        model.addAttribute("bankList", bankList);
        return "resource/resource-view";
    }

    /**
     * 土地用途
     * @return
     */
    @RequestMapping("/getLandUseDictTree")
    @ResponseBody
    public List<Tree> getLandUseDictTree(String tdytDictCode) {
        List<LandUseDict> landUseDictList = landUseDictClient.getLandUseDictList();
        return landUseDictList2Node(landUseDictList, tdytDictCode);
    }

    private List<Tree> landUseDictList2Node(List<LandUseDict> landUseDictList, String tdytDictCode) {
        List<Tree> treeList = new ArrayList();
        if (org.apache.commons.lang.StringUtils.isEmpty(tdytDictCode)) {
            for (LandUseDict landUseDict : landUseDictList) {
                Tree tree = new Tree();
                tree.setId(landUseDict.getCode());
                tree.setpId(landUseDict.getParent());
                tree.setName(landUseDict.getName());
                treeList.add(tree);
            }
        } else {
            String[] tdytDictCodeArr = tdytDictCode.split(",");
            for (LandUseDict landUseDict : landUseDictList) {
                Tree tree = new Tree();
                tree.setId(landUseDict.getCode());
                tree.setpId(landUseDict.getParent());
                tree.setName(landUseDict.getName());
                for (String s : tdytDictCodeArr) {
                    if (s.equals(landUseDict.getCode())) {
                        tree.setChecked(true);
                    }
                }
                treeList.add(tree);
            }
        }
        return treeList;
    }


    /**展示进入摇号的地块
     *@annotation
     *@author lq
     *@date 2017/12/26
     *@param
     *@return
     */
    @RequestMapping("yh/list")
    public String yhResourceList(@PageDefault(value=5) Pageable page,String title,String status ,String ggId,Model model) {
        int resourceEditStatus=Constants.RESOURCE_EDIT_STATUS_RELEASE;
        if (org.apache.commons.lang.StringUtils.isNotBlank(status)) {
            resourceEditStatus = Integer.parseInt(status);
        }
        String regions = "";
        if(!SecUtil.isAdmin()) {
            regions = SecUtil.getLoginUserRegionCodes();
        }
        Page<TransResource> transResourcePage= transResourceClient.findYhResourcesByEditStatus(title, resourceEditStatus, ggId, regions, page);
        model.addAttribute("title", title);
        model.addAttribute("ggId", ggId);
        model.addAttribute("status", resourceEditStatus);
        List values = new ArrayList<Map>();
        for (TransResource transResource : transResourcePage.getItems()) {
            Map valueMap = new HashMap();
            // 万元情况 算 平均地价 万元/亩
            if (Constants.OFFER_UNIT.WY.equals(transResource.getOfferUnit())) {
                // 平均地价(元/平方米) 最高限价*10000/面积 不要小数
                valueMap.put("price1",BigDecimal.valueOf(transResource.getMaxOffer()).multiply(new BigDecimal(10000)).divide(BigDecimal.valueOf(transResource.getCrArea()), 10, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN));
                valueMap.put("unit1","元/平方米");
                //万元/亩 最高限价/出让面积*10000/15
                valueMap.put("price2",BigDecimal.valueOf(transResource.getMaxOffer()).divide(BigDecimal.valueOf(transResource.getCrArea()), 10, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(10000)).divide(new BigDecimal(15), 10, BigDecimal.ROUND_HALF_DOWN).setScale(4, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","万元/亩");
                // 元/平方米(地面价)情况 算总价 万元/亩
            } else if (Constants.OFFER_UNIT.Y_M2_DMJ.equals(transResource.getOfferUnit())) {
                //总价 /万元
                valueMap.put("price1",BigDecimal.valueOf(transResource.getMaxOffer()).multiply(BigDecimal.valueOf(transResource.getCrArea())).divide(new BigDecimal(10000)));
                valueMap.put("unit1","万元");
                //万元/亩
                valueMap.put("price2",BigDecimal.valueOf(transResource.getMaxOffer()).divide(new BigDecimal(10000)).multiply(new BigDecimal(10000)).divide(new BigDecimal(15),10,BigDecimal.ROUND_HALF_DOWN).setScale(4, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","万元/亩");
                // 元/平方米(楼面价)情况 算总价 万元/亩
            } else if (Constants.OFFER_UNIT.Y_M2_LMJ.equals(transResource.getOfferUnit())) {
                //总价 楼面价*建筑面积/10000  /万元
                valueMap.put("price1",BigDecimal.valueOf(transResource.getMaxOffer()).multiply(BigDecimal.valueOf(transResource.getBuildingArea())).divide(new BigDecimal(10000)));
                valueMap.put("unit1","万元");
                //万元/亩 楼面价*建筑面积（总价）/出让面积/10000/15
                valueMap.put("price2",BigDecimal.valueOf(transResource.getMaxOffer()).multiply(BigDecimal.valueOf(transResource.getBuildingArea())).divide(new BigDecimal(10000)).divide(BigDecimal.valueOf(transResource.getCrArea()),10,BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(10000)).divide(new BigDecimal(15),10,BigDecimal.ROUND_HALF_DOWN).setScale(4, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","万元/亩");
                // 万元/亩情况 算总价 平均价（万元/平方米）
            } else if (Constants.OFFER_UNIT.WY_MU.equals(transResource.getOfferUnit())) {
                //总价 平均价*面积
                valueMap.put("price1",BigDecimal.valueOf(transResource.getMaxOffer()).multiply(new BigDecimal(15)).divide(new BigDecimal(10000)).multiply(BigDecimal.valueOf(transResource.getCrArea())));
                valueMap.put("unit1","万元");
                //平均价 最高限价*15/10000*10000
                valueMap.put("price2",BigDecimal.valueOf(transResource.getMaxOffer()).multiply(new BigDecimal(15)).setScale(0, BigDecimal.ROUND_DOWN));
                valueMap.put("unit2","元/平方米");
            }
            values.add(valueMap);
        }
        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("values", values);

        return "resource/resource-yh-list";
    }

    /**
     *@annotation 设置摇号地块成交
     *@author lq
     *@date 2017/12/26
     *@param
     *@return
     */
    @RequestMapping("yh/resource")
    public String yhResourceDetail(String resourceId,Model model) {
        TransResource resource = transResourceClient.getTransResource(resourceId);
        List<YHAgree> agreeList = yhClient.getYHAgreeByResourceId(resource.getResourceId());
        YHResult yhResult = yhClient.getYHResultByResourceId(resourceId);
        if(yhResult != null) {
            model.addAttribute("yhResult",yhResult);
            model.addAttribute("selectedAgree",yhClient.getYHAgree(yhResult.getAgreeId()));
        }
        model.addAttribute("resource",resource);
        model.addAttribute("agreeList",agreeList);
        return "resource/resource-yh";
    }


    /**
     *@annotation 设置摇号竞得人
     *@author lq
     *@date 2017/12/26
     *@param
     *@return
     */
    @RequestMapping("yh/success/save")
    @ResponseBody
    public ResponseMessage yhResourceSuccess(String resourceId,String successPrice,String offerUserId) {
        if (!org.apache.commons.lang.StringUtils.isNotBlank(offerUserId)){
            return new ResponseMessage<Object>(false,"必须在报名列表中选中一个报名人!");
        }
        String userId = SecUtil.getLoginUserId();
        ResponseMessage<YHResult> responseMessage = yhClient.saveOrUpdateYHResult(resourceId,successPrice,offerUserId,userId);
        return responseMessage;
    }

    /**
     *@annotation 发布摇号结果
     *@author lq
     *@date 2018/01/04
     *@param
     *@return
     */
    @RequestMapping("yh/success/post")
    @ResponseBody
    public ResponseMessage postYHResult(String resourceId) {
        YHResult yhResult = yhClient.getYHResultByResourceId(resourceId);
        if(StringUtils.isBlank(resourceId) || yhResult == null){
            return new ResponseMessage(false,"请先保存摇号信息！");
        }
        ResponseMessage<YHResult> responseMessage = yhClient.postYHResult(yhResult.getResultId());
        return responseMessage;
    }


    /**
     * 下载成交确认书
     * @param resourceId
     * @param response
     * @throws Exception
     */
    @RequestMapping("/cjqrs")
    public void getJmsqs(String resourceId, HttpServletResponse response) throws Exception {
        TransResource resource = transResourceClient.getTransResource(resourceId);
        TransResourceOffer offer=transResourceOfferClient.getTransResourceOffer(resource.getOfferId());
        TransCrgg transCrgg = transCrggClient.getTransCrgg(resource.getGgId());
        TransResourceOffer offerPrice=transResourceOfferClient.getMaxOffer(resourceId);
        TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(offer.getUserId(), resourceId);
        Map params = Maps.newHashMap();
        TransRegion region=transRegionClient.getTransRegionByRegionCode(resource.getRegionCode());
        TransUser user=transUserClient.getTransUserById(offer.getUserId());
        List<TransUserUnion> transUserUnionList = transUserUnionClient.findTransUserUnion(transResourceApply.getApplyId());
        double userAmountScale = getUserAmountScale(transUserUnionList);
        params.put("regionName",region.getRegionName());
        params.put("offer", offer);
        params.put("resource", resource);
        params.put("resourceCode", resource.getResourceCode());
        params.put("offerPrice", offerPrice.getOfferPrice() * 10000);
        params.put("offerPriceLocal", RMBUtils.toBigAmt(offerPrice.getOfferPrice() * 10000));
        params.put("offerAreaLocal", RMBUtils.toBigAmt(resource.getCrArea()));
        params.put("newComName", StringUtils.isBlank(transResourceApply.getCreateNewComName()) ? "/" : transResourceApply.getCreateNewComName());
        params.put("ggNum", transCrgg.getGgNum());
        params.put("user", user);
        params.put("userName", SecUtil.getLoginUserViewName());
        params.put("currentDate", Calendar.getInstance().getTime());
        params.put("transUserUnionList", transUserUnionList);
        params.put("userAmountScale", userAmountScale);
        StringBuilder fileName = new StringBuilder();
        fileName.append(resource.getResourceCode());
        fileName.append("成交确认书.doc");
        msWordTpl2Response(getTplPath(resourceId,"resource-cjqrs.ftl"), fileName.toString(), params, response);
    }

    /**
     *
     * @return
     */
    private double getUserAmountScale(List<TransUserUnion> transUserUnionList) {
        double userAmountScale = 100;
        DecimalFormat decimalFormat = new DecimalFormat("###.#");
        for (TransUserUnion transUserUnion : transUserUnionList) {
            userAmountScale -= transUserUnion.getAmountScale();
        }
        return userAmountScale;
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
//        TransResource resource = transResourceClient.getTransResource(resourceId);
//        TransRegion region=transRegionClient.getTransRegionByRegionCode(resource.getRegionCode());
//        String regionTag = region.getRegionCode();
//        String tplPath = "/material/"+regionTag+"/"+tplName;
        String tplPath = "/material/"+tplName;
        String basePath = servletContext.getRealPath("/")+"templates";
        if(new File(basePath+tplPath).exists()) {
            return tplPath;
        } else {
            return "/material/default/" + tplName;
        }
    }


}

