package cn.gtmap.landsale.client.service.impl;


import cn.gtmap.landsale.client.service.ResourceOfferQueueService;
import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jibo on 2015/5/8.
 */
@Service
public class ResourceOfferQueueServiceImpl implements ResourceOfferQueueService {
    private Map<String,ResourceQueue> resourceQueueMap =new ConcurrentHashMap<String,ResourceQueue>();

    /**
     * 往队列中添加地块
     * @param resource
     * @param clientReuslt
     */
    @Override
    public void addResource(TransResource resource, DeferredResult<String> clientReuslt){
        ResourceQueue resourceQueue=null;
        if (resourceQueueMap.get(resource.getResourceId())==null){
            resourceQueue=new ResourceQueue(resource.getResourceId());
            resourceQueueMap.put(resource.getResourceId(),resourceQueue);
        }else{
            resourceQueue=resourceQueueMap.get(resource.getResourceId());
        }
        resourceQueue.addClient(clientReuslt);
    }

    /**
     * 接收到新报价
     * @param resourceOffer
     */
    @Override
    public void receiveNewOffer(TransResourceOffer resourceOffer){
        ResourceQueue resourceQueue=resourceQueueMap.get(resourceOffer.getResourceId());
        if(resourceQueue!=null){
            resourceQueue.writeClient(resourceOffer);
        }
    }

}
