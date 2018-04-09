package cn.gtmap.landsale.common.web.freemarker;

import cn.gtmap.landsale.common.model.TransResourceMinPrice;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import cn.gtmap.landsale.common.register.TransResourceMinPriceClient;
import cn.gtmap.landsale.common.register.TransResourceOfferClient;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * freemarker价格工具类
 * @author zsj
 * @version v1.0, 2017/11/14
 */
@Component
public class PriceUtil {

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    TransResourceMinPriceClient transResourceMinPriceClient;


    public TransResourceOffer getTransResourceOffer(String offerId){
        if(StringUtils.isBlank(offerId)){
            return new TransResourceOffer();
        }

        return transResourceOfferClient.getTransResourceOffer(offerId);
    }

    public TransResourceOffer getSuccessOffer(String offerId){
        return transResourceOfferClient.getTransResourceOffer(offerId);
    }

    public TransResourceOffer getMaxOffer(String resourceId){
        return transResourceOfferClient.getMaxOffer(resourceId);
    }


    public Double getMinPrice(String resourceId){
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceClient.getMinPriceListByResourceId(resourceId);
        for(TransResourceMinPrice transResourceMinPrice: transResourceMinPriceList){
            if (transResourceMinPrice.getUserId().equals(SecUtil.getLoginUserId())){
                return transResourceMinPrice.getPrice();
            }
        }
        return 0.0;
    }

    public Double getMinPriceByResourceId(String resourceId){
        return transResourceMinPriceClient.getMinPriceByResourceId(resourceId);
    }

    public int getMinPriceCount(String resourceId){
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceClient.getMinPriceListByResourceId(resourceId);
        return transResourceMinPriceList.size();
    }
}
