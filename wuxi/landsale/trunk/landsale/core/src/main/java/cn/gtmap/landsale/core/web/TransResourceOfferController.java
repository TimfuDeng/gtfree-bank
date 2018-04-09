package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.core.register.TransRedisClient;
import cn.gtmap.landsale.core.service.ClientService;
import cn.gtmap.landsale.core.service.TransResourceOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 报价 服务
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@RestController
@RequestMapping(value = "/offer")
public class TransResourceOfferController {

    private static Logger log = LoggerFactory.getLogger(TransResourceOfferController.class);

    @Autowired
    ClientService clientService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransRedisClient redisClient;


    /**
     * 获得最新的报价
     * @param resourceId
     * @param request
     * @return
     */
    @RequestMapping(value = "/getResourceOfferPage")
    Page<TransResourceOffer> getResourceOfferPage(@RequestParam("resourceId") String resourceId, @RequestBody Pageable request) {
        return transResourceOfferService.getResourceOfferPage(resourceId, request);
    }

    /**
     * 获取地块的报价次数
     * @param resourceId 出让地块Id
     * @return
     */
    @RequestMapping(value = "/getResourceOfferFrequency")
    Long getResourceOfferFrequency(@RequestParam("resourceId") String resourceId) {
        return transResourceOfferService.getResourceOfferFrequency(resourceId);
    }

    /**
     * 根据offerId获得报价
     * @param offerId 报价对象Id
     * @return
     */
    @RequestMapping(value = "/getTransResourceOffer")
    TransResourceOffer getTransResourceOffer(@RequestParam("offerId") String offerId) {
        return transResourceOfferService.getTransResourceOffer(offerId);
    }

    /**
     * 判断最高价(第一个出价的)
     * @param resourceId 出让地块Id
     * @return
     */
    @RequestMapping(value = "/getMaxOffer")
    TransResourceOffer getMaxOffer(@RequestParam("resourceId") String resourceId) {
        return transResourceOfferService.getMaxOffer(resourceId);
    }

    /**
     * 获得总价和单价最高的价格，不包括多指标
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/getMaxOfferFormPrice")
    TransResourceOffer getMaxOfferFormPrice(@RequestParam("resourceId") String resourceId) {
        return transResourceOfferService.getMaxOfferFormPrice(resourceId);
    }

    /**
     * 报价
     * @param userId
     * @param id
     * @param offer
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/acceptResourceOffer")
    ResponseMessage<TransResourceOffer> acceptResourceOffer(String userId, String id, String offer, int type) throws Exception {
        ResponseMessage<TransResourceOffer> resourceOffer = clientService.acceptResourceOffer(userId, id, Double.parseDouble(offer), type);
        if (null != resourceOffer.getEmpty()) {
            redisClient.sendOffer(resourceOffer.getEmpty());
        }
        return resourceOffer;

    }

    /**
     * 获得大于timevalue的最新报价，最多15条
     * @param resourceId
     * @param timeValue
     * @return
     */
    @RequestMapping(value = "/getOfferList")
    List<TransResourceOffer> getOfferList(@RequestParam("resourceId") String resourceId, @RequestParam("timeValue") long timeValue) {
        return clientService.getOfferList(resourceId, timeValue);
    }


    /**
     * 获得一个资源所有的报价
     * @param resourceId 出让地块Id
     * @return
     */
    @RequestMapping(value = "/getOfferListByResource")
    List<TransResourceOffer> getOfferListByResource(@RequestParam("resourceId") String resourceId) {
        return transResourceOfferService.getOfferListByResource(resourceId);
    }

    /**
     * 增加报价
     * @param offer 报价对象
     * @return
     */
    @RequestMapping(value = "/addTransResourceOffer")
    ResponseMessage<TransResourceOffer> addTransResourceOffer(@RequestBody TransResourceOffer offer){
        return transResourceOfferService.addTransResourceOffer(offer);
    }

    /**
     * 获取地块的报价大于等于offerPrice的列表
     * @param offerPrice
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/findTransResourceOfferByOfferPrice")
    List<TransResourceOffer> findTransResourceOfferByOfferPrice(@RequestParam("offerPrice") double offerPrice, @RequestParam("resourceId") String resourceId) {
        return transResourceOfferService.findTransResourceOfferByOfferPrice(offerPrice,resourceId);
    }

    /**
     * 获得一个人所有报价列表
     * @param resourceId
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping("/getResourceOfferPageByUserId")
    Page<TransResourceOffer> getResourceOfferPageByUserId(@RequestParam("resourceId") String resourceId, @RequestParam("userId") String userId, @RequestBody Pageable request) {
        return transResourceOfferService.getResourceOfferPageByUserId(resourceId, userId, request);
    }

}
