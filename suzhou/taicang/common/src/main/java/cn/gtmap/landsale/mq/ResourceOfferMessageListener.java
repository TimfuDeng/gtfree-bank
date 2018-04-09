package cn.gtmap.landsale.mq;

import cn.gtmap.landsale.model.TransResourceOffer;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/21
 */
public class ResourceOfferMessageListener {

    private ResourceOfferQueueService resourceOfferQueueService;

    public void setResourceOfferQueueService(ResourceOfferQueueService resourceOfferQueueService) {
        this.resourceOfferQueueService = resourceOfferQueueService;
    }

    public void receiveOffer(TransResourceOffer transResourceOffer){
        if(transResourceOffer!=null){
            resourceOfferQueueService.receiveNewOffer(transResourceOffer);
        }
    }
}
