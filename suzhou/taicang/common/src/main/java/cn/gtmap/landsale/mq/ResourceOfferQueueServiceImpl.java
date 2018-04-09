package cn.gtmap.landsale.mq;

import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceOffer;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Jibo on 2015/5/8.
 */
public class ResourceOfferQueueServiceImpl implements ResourceOfferQueueService {
    private Map<String,ResourceQueue> resourceQueueMap =new ConcurrentHashMap<String,ResourceQueue>();

    /**
     * 往队列中添加地块
     * @param resource
     * @param clientReuslt
     */
    public void addResource(TransResource resource,DeferredResult<String> clientReuslt){
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
    public void receiveNewOffer(TransResourceOffer resourceOffer){
        ResourceQueue resourceQueue=resourceQueueMap.get(resourceOffer.getResourceId());
        if(resourceQueue!=null){
            resourceQueue.writeClient(resourceOffer);
        }
    }

}
