package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceMinPrice;

import java.util.List;

/**
 * 底价Service
 * @author zsj
 * @version v1.0, 2017/10/26
 */
public interface TransResourceMinPriceService {

    /**
     * 通过低价Id 获取 TransResourceMinPrice
     * @param priceId
     * @return TransResourceMinPrice
     */
    TransResourceMinPrice getTransResourceMinPrice(String priceId);

    /**
     * 获取 地块底价
     * @param resourceId
     * @return
     */
    Double getMinPriceByResourceId(String resourceId);

    /**
     * 获取 地块底价 通过 resourceId
     * @param resourceId
     * @return
     */
    List<TransResourceMinPrice> getMinPriceListByResourceId(String resourceId);

    /**
     * 保存底价
     * @param resourceMinPrice
     * @return
     */
    ResponseMessage<TransResourceMinPrice> saveTransResourceInfo(TransResourceMinPrice resourceMinPrice);
}
