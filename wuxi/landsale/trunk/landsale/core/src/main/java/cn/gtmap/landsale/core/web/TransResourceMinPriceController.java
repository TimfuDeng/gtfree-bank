package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceMinPrice;
import cn.gtmap.landsale.core.service.TransResourceMinPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 底价 服务
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@RestController
@RequestMapping(value = "/minPrice")
public class TransResourceMinPriceController {

    private static Logger log = LoggerFactory.getLogger(TransResourceMinPriceController.class);


    @Autowired
    TransResourceMinPriceService transResourceMinPriceService;

    /**
     * 通过低价Id 获取 TransResourceMinPrice
     * @param priceId
     * @return TransResourceMinPrice
     */
    @RequestMapping(value = "/getTransResourceMinPrice")
    TransResourceMinPrice getTransResourceMinPrice(@RequestParam("priceId") String priceId) {
        return transResourceMinPriceService.getTransResourceMinPrice(priceId);
    }

    /**
     * 获取 地块底价
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/getMinPriceByResourceId")
    Double getMinPriceByResourceId(@RequestParam("resourceId") String resourceId) {
        return transResourceMinPriceService.getMinPriceByResourceId(resourceId);
    }

    /**
     * 获取 地块底价 通过 resourceId
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/getMinPriceListByResourceId")
    List<TransResourceMinPrice> getMinPriceListByResourceId(@RequestParam("resourceId") String resourceId) {
        return transResourceMinPriceService.getMinPriceListByResourceId(resourceId);
    }

    /**
     * 保存底价
     * @param transResourceMinPrice
     * @return
     */
    @RequestMapping(value = "/saveTransResourceInfo")
    ResponseMessage<TransResourceMinPrice> saveTransResourceInfo(@RequestBody TransResourceMinPrice transResourceMinPrice){
        return  transResourceMinPriceService.saveTransResourceInfo(transResourceMinPrice);
    }

}
