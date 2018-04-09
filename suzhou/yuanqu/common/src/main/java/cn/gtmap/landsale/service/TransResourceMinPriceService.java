package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransResourceMinPrice;

import java.util.List;

/**
 * 底价录入
 * Created by jibo1_000 on 2015/6/2.
 */
public interface TransResourceMinPriceService {

    TransResourceMinPrice getTransResourceMinPrice(String priceId);

    Double getMinPriceByResourceId(String resourceId);

    List<TransResourceMinPrice> getMinPriceListByResourceId(String resourceId);

    TransResourceMinPrice saveTransResourceInfo(TransResourceMinPrice resourceMinPrice);
}
