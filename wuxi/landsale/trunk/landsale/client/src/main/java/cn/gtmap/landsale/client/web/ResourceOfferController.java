package cn.gtmap.landsale.client.web;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.client.register.*;
import cn.gtmap.landsale.client.service.ResourceOfferQueueService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报价服务
 * @author zsj
 * @version v1.0, 2017/12/7
 */
@Controller
@RequestMapping("/resourceOffer")
public class ResourceOfferController {

    private static final long TIMEOUT =72*1000;  //1.2分钟超时时间，参考taobao

    private static Logger log = LoggerFactory.getLogger(ResourceOfferController.class);

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransResourceSonClient transResourceSonClient;

    @Autowired
    TransOfferClient offerClient;

    @Autowired
    ResourceOfferQueueService resourceOfferQueueService;

    @Autowired
    TransResourceInfoClient transResourceInfoClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    OneParamClient oneParamClient;

    @Autowired
    OneTargetClient oneTargetClient;

    @Autowired
    YHClient yhClient;

    @Value("${ca.login.enabled}")
    Boolean caEnabled;

    /**
     * 弹出报价页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/index")
    public String index(String resourceId, Model model) throws Exception {
        model.addAttribute("resourceId", resourceId);
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("resourceCode", transResource.getResourceCode());
        return "offer/index";
    }

    /**
     * 通过当前登录用户 过去该用户所有 可以报价的地块
     * @return
     * @throws Exception
     */
    @RequestMapping("/getResource")
    @ResponseBody
    public List<TransResource> index() throws Exception {
        String userId = SecUtil.getLoginUserId();
        // 通过当前登录用户 过去该用户所有 可以报价的地块
        List<TransResource> transResourceList = transResourceClient.findResourcesForPriceByUser(userId);
        return transResourceList;
    }

    @RequestMapping("/getOfferHistory")
    public String getOfferHistory(String resourceId, @PageDefault(value = 5) Pageable page, Model model) {
        Page<TransResourceOffer> transResourceOfferList = transResourceOfferClient.getResourceOfferPage(resourceId, page);
        List<TransResourceSon> transResourceSonList = transResourceSonClient.getTransResourceSonList(resourceId);
        TransResource transResource = transResourceClient.getTransResource(resourceId);
        double maxRjl = 0;
        double minRjl = 0;
        // 最大容积率
        if (transResourceSonList != null && transResourceSonList.size() > 0) {
            for (TransResourceSon transResourceSon : transResourceSonList) {
                if (transResourceSon.getMaxRjl() != null && transResourceSon.getMaxRjl() > maxRjl) {
                    maxRjl = transResourceSon.getMaxRjl();
                }
                if (transResourceSon.getMinRjl() != null && transResourceSon.getMinRjl() > minRjl) {
                    minRjl = transResourceSon.getMinRjl();
                }
            }
        }
        // 最大容积率 为0 选择最小容积率
        if (0 == maxRjl) {
            maxRjl = minRjl;
        }
        // 最大容积率 < 最小容积率 选择最小容积率
        if (maxRjl < minRjl) {
            maxRjl = minRjl;
        }
        model.addAttribute("transResourceOfferList", getOfferHistoryPrice(transResourceOfferList, transResource, maxRjl));
        model.addAttribute("transResource", transResource);
        model.addAttribute("userId", SecUtil.getLoginUserId());
        model.addAttribute("maxRjl", maxRjl);
        return "offer/view-offer-history";
    }

    private Page<TransResourceOffer> getOfferHistoryPrice(Page<TransResourceOffer> transResourceOfferList, TransResource transResource, double maxRjl) {
        if (transResourceOfferList != null && transResourceOfferList.getItemSize() > 0 && transResource != null) {
            for (TransResourceOffer transResourceOffer : transResourceOfferList.getItems()) {
                // 判断 地块的 报价单位 offerUnit 所有情况都算溢价率 且算法相同
                // 溢价率 当前价-起始价/起始价
                transResourceOffer.setPremiumRate(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).subtract(BigDecimal.valueOf(transResource.getBeginOffer())).divide(BigDecimal.valueOf(transResource.getBeginOffer()), 10, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_DOWN));
                // 万元情况 算溢价率 平均地价 楼面价（容积率>0）
                if (Constants.OFFER_UNIT.WY.equals(transResource.getOfferUnit())) {
                    // 平均地价 当前价*10000/面积 不要小数
                    transResourceOffer.setAvgPrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(new BigDecimal(10000)).divide(BigDecimal.valueOf(transResource.getCrArea()), 10, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN));
                    // 楼面价 当前价*10000/（面积*容积率） 不要小数
                    if (0 < maxRjl) {
                        transResourceOffer.setGatePrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(new BigDecimal(10000)).divide(BigDecimal.valueOf(transResource.getCrArea()).multiply(BigDecimal.valueOf(maxRjl)), 10, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN));
                    } else {
                        transResourceOffer.setGatePrice(BigDecimal.ZERO);
                    }
                // 元/平方米(地面价)情况 算溢价率 楼面价（容积率>0） 总价
                } else if (Constants.OFFER_UNIT.Y_M2_DMJ.equals(transResource.getOfferUnit())) {
                    // 总价 当前价*面积/10000
                    transResourceOffer.setTotalPrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(BigDecimal.valueOf(transResource.getCrArea())).divide(new BigDecimal(10000)));
                    // 楼面价 当前价/容积率 不要小数
                    if (0 < maxRjl) {
                        transResourceOffer.setGatePrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).divide(BigDecimal.valueOf(maxRjl), 10, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN));
                    } else {
                        transResourceOffer.setGatePrice(BigDecimal.ZERO);
                    }
                // 元/平方米(楼面价)情况 算溢价率 平均价 总价
                } else if (Constants.OFFER_UNIT.Y_M2_LMJ.equals(transResource.getOfferUnit())) {
                    // 平均地价 当前价*容积率 不要小数
                    transResourceOffer.setAvgPrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(BigDecimal.valueOf(maxRjl)).setScale(0, BigDecimal.ROUND_DOWN));
                    // 总价 当前价*建筑面积/10000
                    transResourceOffer.setTotalPrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(BigDecimal.valueOf(transResource.getBuildingArea())).divide(new BigDecimal(10000)));
                // 万元/亩情况 算溢价率 平均价 总价 楼面价
                } else if (Constants.OFFER_UNIT.WY_MU.equals(transResource.getOfferUnit())) {
                    // 平均地价 当前价*15/10000*10000 = 当前价*15 不要小数
                    transResourceOffer.setAvgPrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(new BigDecimal(15)).setScale(0, BigDecimal.ROUND_DOWN));
                    // 总价 当前价*（面积/10000*15）
                    transResourceOffer.setTotalPrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(BigDecimal.valueOf(transResource.getCrArea()).multiply(new BigDecimal(15).divide(new BigDecimal(10000), 10, BigDecimal.ROUND_HALF_DOWN))).setScale(4, BigDecimal.ROUND_DOWN));
                    // 楼面价 当前价*15*10000/10000/容积率 = 当前价*15/容积率 不要小数
                    if (0 < maxRjl) {
                        transResourceOffer.setGatePrice(BigDecimal.valueOf(transResourceOffer.getOfferPrice()).multiply(new BigDecimal(15)).divide(BigDecimal.valueOf(maxRjl), 10, BigDecimal.ROUND_HALF_DOWN).setScale(0, BigDecimal.ROUND_DOWN));
                    } else {
                        transResourceOffer.setGatePrice(BigDecimal.ZERO);
                    }
                }
            }
        }
        return transResourceOfferList;
    }

    @RequestMapping("/view")
    public String view(String resourceId, ModelMap model) throws Exception {

        TransResource transResource = transResourceClient.getTransResource(resourceId);
        model.addAttribute("resource", transResource);

        TransResourceInfo transResourceInfo = transResourceInfoClient.getTransResourceInfoByResourceId(resourceId);
        if(transResourceInfo==null) {
            transResourceInfo = new TransResourceInfo();
        }
        model.addAttribute("resourceInfo",transResourceInfo);
        //获得报价列表（15条）
        List<TransResourceOffer> resourceOffers = transResourceOfferClient.getOfferList(resourceId, -1);
        model.addAttribute("resourceOffers", resourceOffers);
        model.addAttribute("cDate", Calendar.getInstance().getTime().getTime());
        //获得当前最高报价+
        TransResourceOffer maxOffer = (resourceOffers.size() > 0) ? resourceOffers.get(0) : null;
        model.addAttribute("maxOffer", maxOffer);
        if (null!=maxOffer) {
            model.addAttribute("maxResourceApply", transResourceApplyClient.getTransResourceApplyByUserId(maxOffer.getUserId(), resourceId));
        }
        //当前总价的最高报价,不包括多指标
        TransResourceOffer maxOfferPrice = transResourceOfferClient.getMaxOfferFormPrice(resourceId);
        model.addAttribute("maxOfferPrice", maxOfferPrice);
        // 当前用户
        String userId = SecUtil.getLoginUserId();
        model.addAttribute("userId", userId);
        //判断用户和当前地块的购买状态
        if (SecUtil.isLogin()){
            TransResourceApply transResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(SecUtil.getLoginUserId(), resourceId);
            model.addAttribute("transResourceApply", transResourceApply);
        }
        /**
         *@annotation
         *@author liushaoshuai【liushaoshuai@gtmap.cn】
         *@date 2017/5/8 14:53
         *@param
         *@return
         */
        //如果在限时竞价阶段，要判断当前限时竞价的截至时间
        Date cDate= Calendar.getInstance().getTime();
        if (cDate.after(transResource.getXsBeginTime())) {
            // 判断是否 达到最高限价
            if (Constants.RESOURCE_STATUS_MAX_OFFER == transResource.getResourceStatus()) {
                // 判断 最高限价后的 竞价方式
                if (Constants.MaxOfferChoose.YCBJ.getCode() == transResource.getMaxOfferChoose().getCode()) {
                    OneTarget oneTarget = oneTargetClient.getOneTargetByTransResource(transResource.getResourceId());
                    model.addAttribute("oneTarget", oneTarget);
                } else if (Constants.MaxOfferChoose.YH.getCode() == transResource.getMaxOfferChoose().getCode()) {
                    YHAgree yhAgree = yhClient.getYHAgreeByResourceIdAndUserId(resourceId, userId);
                    model.addAttribute("yhAgree", yhAgree);
                } else {
                    // 其他方式
                }
            } else {
                // 最高报价为空 或 最高报价为挂牌期报价 或 （最该报价为-挂牌期-最高限价）
                if (maxOffer == null || maxOffer.getOfferType() == Constants.OFFER_TYPE_GUA_PAI || (maxOffer.getOfferType() == Constants.OFFER_XIANJIA && maxOffer.getOfferTime() <= transResource.getGpEndTime().getTime())) {
                    model.addAttribute("endTime", transResource.getXsBeginTime().getTime() + 1000 * 60 * 4);
                } else {
                    long offerTime = maxOffer.getOfferTime();
                    long xsBeginTime = transResource.getXsBeginTime().getTime();
                    // 四分钟时中断竞买 再次启动时 从四分钟开始
                    if (offerTime < xsBeginTime) {
                        model.addAttribute("endTime", transResource.getXsBeginTime().getTime() + 1000 * 60 * 4);
                    } else {
                        model.addAttribute("endTime", offerTime + 1000 * 60 * 4);
                    }
                }
            }
        }
        model.addAttribute("ggNum", StringUtils.isNotBlank(transResource.getGgId()) ? transCrggClient.getTransCrgg(transResource.getGgId()).getGgNum() : "");
        return "offer/view";
    }

    @RequestMapping("/joinYh")
    @ResponseBody
    public ResponseMessage joinYh(YHAgree yhAgree) {
        yhAgree.setUserId(SecUtil.getLoginUserId());
        yhAgree.setAgreeTime(new Date());
        return yhClient.saveOrUpdateYHAgree(yhAgree);
    }


    @RequestMapping("/offer")
   /* @AuditServiceLog(category = Constants.LogCategory.CUSTOM_OFFER,producer = Constants.LogProducer.CLIENT,
            description = "用户报价")*/
    @ResponseBody
    public ResponseMessage<TransResourceOffer> offer(String resourceId, String offer, int type, CaSignerX caSignerX) throws Exception{
        String userId = SecUtil.getLoginUserId();
//        TransResourceOffer resourceOffer = new TransResourceOffer();
        try{
            if(caEnabled){
              /*  boolean signatureValid = validateSignature(caSignerX);
                if(!signatureValid)
                    return "报价数据验证错误，请检查CA数字证书环境";
                String sxInput = new String(Base64.decodeBase64(caSignerX.getSxinput()), Charsets.CHARSET_UTF8);
                Map sxInputMap = JSON.parseObject(sxInput);
                id = String.valueOf(sxInputMap.get("id"));
                offer = String.valueOf(sxInputMap.get("offer"));
                type = NumberUtils.createInteger(String.valueOf(sxInputMap.get("type")));*/
            }

            if (StringUtils.isNotBlank(offer)) {
                ResponseMessage<TransResourceOffer> responseMessage = transResourceOfferClient.acceptResourceOffer(userId, resourceId, offer, type);
                return responseMessage;
            }else{
//                resourceOffer.setOfferMessage();
                return new ResponseMessage<>(false, "报价为空！");
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            return new ResponseMessage<>(false, ex.getMessage());
        }
    }


    @RequestMapping("/getoffer")
    public @ResponseBody
    DeferredResult<String> deferredResult(String id,String time) {

        DeferredResult<String> clientResult = new DeferredResult<String>(TIMEOUT,"timeout");
        TransResource transResource= transResourceClient.getTransResource(id);
        if (transResource.getResourceEditStatus()!=Constants.RESOURCE_EDIT_STATUS_RELEASE ||
                transResource.getResourceStatus()==Constants.RESOURCE_STATUS_CHENG_JIAO ||
                transResource.getResourceStatus()==Constants.RESOURCE_STATUS_LIU_BIAO){
            clientResult.setResult("refresh");
        }else {
            List<TransResourceOffer> offers = transResourceOfferClient.getOfferList(id, Long.parseLong(time));
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

}
