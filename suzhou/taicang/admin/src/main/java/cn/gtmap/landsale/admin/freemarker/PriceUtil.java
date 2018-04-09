package cn.gtmap.landsale.admin.freemarker;

import cn.gtmap.landsale.model.TransResourceMinPrice;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransResourceMinPriceService;
import cn.gtmap.landsale.service.TransResourceOfferService;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/6/2.
 */
public class PriceUtil {

    TransResourceOfferService transResourceOfferService;

    TransResourceMinPriceService transResourceMinPriceService;

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public void setTransResourceMinPriceService(TransResourceMinPriceService transResourceMinPriceService) {
        this.transResourceMinPriceService = transResourceMinPriceService;
    }

    public TransResourceOffer getTransResourceOffer(String offerId){
        return transResourceOfferService.getTransResourceOffer(offerId);
    }

    public TransResourceOffer getMaxOffer(String resourceId){
        return transResourceOfferService.getMaxOffer(resourceId);
    }

    public TransResourceOffer getMaxPriceOffer(String resourceId){
        return transResourceOfferService.getMaxOfferFormPrice(resourceId);
    }
    public Double getMinPrice(String resourceId){
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceService.getMinPriceListByResourceId(resourceId);
        for(TransResourceMinPrice transResourceMinPrice: transResourceMinPriceList){
            if (transResourceMinPrice.getUserId().equals(SecUtil.getLoginUserId())){
                return transResourceMinPrice.getPrice();
            }
        }
        return 0.0;
    }

    public int getMinPriceCount(String resourceId){
        List<TransResourceMinPrice> transResourceMinPriceList=
                transResourceMinPriceService.getMinPriceListByResourceId(resourceId);
        return transResourceMinPriceList.size();
    }
}
