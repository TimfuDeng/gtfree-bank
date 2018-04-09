package cn.gtmap.landsale.client.service;


import cn.gtmap.landsale.common.model.TransResource;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/21
 */
public interface ResourceOfferQueueService {

    /**
     * 往队列中添加地块
     * @param resource
     * @param clientReuslt
     */
    public void addResource(TransResource resource, DeferredResult<String> clientReuslt);

    /**
     * 接收到新报价
     * @param resourceOffer
     */
    public void receiveNewOffer(TransResourceOffer resourceOffer);
}
