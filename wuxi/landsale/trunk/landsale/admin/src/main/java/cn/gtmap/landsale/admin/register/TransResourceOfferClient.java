package cn.gtmap.landsale.admin.register;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 地块报价 服務
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@FeignClient(name = "core-server")
public interface TransResourceOfferClient {

    /**
     * 获得最新的报价，time：-1表示所有的报价
     * @param resourceId
     * @param request
     * @return
     */
    @RequestMapping(value = "/offer/getResourceOfferPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransResourceOffer> getResourceOfferPage(@RequestParam("resourceId") String resourceId, @PageDefault(value = 10) Pageable request);

    /**
     * 获取地块的报价次数
     * @param resourceId 出让地块Id
     * @return
     */
    @RequestMapping(value = "/offer/getResourceOfferFrequency", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Long getResourceOfferFrequency(@RequestParam("resourceId") String resourceId);

    /**
     * 根据offerId获得报价
     * @param offerId 报价对象Id
     * @return
     */
    @RequestMapping(value = "/offer/getTransResourceOffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceOffer getTransResourceOffer(@RequestParam("offerId") String offerId);

    /**
     * 判断最高价(第一个出价的)
     * @param resourceId 出让地块Id
     * @return
     */
    @RequestMapping(value = "/offer/getMaxOffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceOffer getMaxOffer(@RequestParam("resourceId") String resourceId);

    /**
     * 获得总价和单价最高的价格，不包括多指标
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/offer/getMaxOfferFormPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceOffer getMaxOfferFormPrice(@RequestParam("resourceId") String resourceId);

    /**
     * 获得一个资源所有的报价
     * @param resourceId 出让地块Id
     * @return
     */
    @RequestMapping(value = "/offer/getOfferListByResource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceOffer> getOfferListByResource(@RequestParam("resourceId") String resourceId);

    /**
     * 增加报价
     * @param offer 报价对象
     * @return
     */
    @RequestMapping(value = "/offer/addTransResourceOffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResourceOffer> addTransResourceOffer(@RequestBody TransResourceOffer offer);

    /**
     * 获取地块的报价大于等于offerPrice的列表
     * @param offerPrice
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/offer/findTransResourceOfferByOfferPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceOffer> findTransResourceOfferByOfferPrice(@RequestParam("offerPrice") double offerPrice, @RequestParam("resourceId") String resourceId);

    }
