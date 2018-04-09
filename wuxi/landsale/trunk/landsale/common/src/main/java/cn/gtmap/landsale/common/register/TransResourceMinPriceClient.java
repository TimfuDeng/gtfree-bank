package cn.gtmap.landsale.common.register;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceMinPrice;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 地块底价 服務
 * @author zsj
 * @version v1.0, 2017/10/26
 */
@FeignClient(name = "core-server")
public interface TransResourceMinPriceClient {

    /**
     * 通过低价Id 获取 TransResourceMinPrice
     * @param priceId
     * @return TransResourceMinPrice
     */
    @RequestMapping(value = "/minPrice/getTransResourceMinPrice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransResourceMinPrice getTransResourceMinPrice(@RequestParam("priceId") String priceId);

    /**
     * 获取 地块底价
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/minPrice/getMinPriceByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Double getMinPriceByResourceId(@RequestParam("resourceId") String resourceId);

    /**
     * 获取 地块底价 通过 resourceId
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/minPrice/getMinPriceListByResourceId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TransResourceMinPrice> getMinPriceListByResourceId(@RequestParam("resourceId") String resourceId);

    /**
     * 保存底价
     * @param transResourceMinPrice
     * @return
     */
    @RequestMapping(value = "/minPrice/saveTransResourceInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransResourceMinPrice> saveTransResourceInfo(@RequestBody TransResourceMinPrice transResourceMinPrice);

}
