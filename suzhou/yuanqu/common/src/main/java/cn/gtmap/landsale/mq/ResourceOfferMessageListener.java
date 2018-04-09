package cn.gtmap.landsale.mq;

import cn.gtmap.landsale.model.TransResourceOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/21
 */
public class ResourceOfferMessageListener {
    private static Logger log = LoggerFactory.getLogger(ResourceOfferMessageListener.class);
    private ResourceOfferQueueService resourceOfferQueueService;

    public void setResourceOfferQueueService(ResourceOfferQueueService resourceOfferQueueService) {
        this.resourceOfferQueueService = resourceOfferQueueService;
    }

    public void receiveOffer(TransResourceOffer transResourceOffer){
        if(transResourceOffer!=null){
            log.info("接受到报价_"+transResourceOffer.getResourceId()+"   :"+transResourceOffer.getOfferPrice());
            resourceOfferQueueService.receiveNewOffer(transResourceOffer);
        }
    }
}
