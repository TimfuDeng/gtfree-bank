package cn.gtmap.landsale.client.config;

import cn.gtmap.landsale.client.service.ResourceOfferQueueService;
import cn.gtmap.landsale.common.model.TransResourceOffer;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

@Configuration
public class MyMessageListener implements MessageListener {
    @Autowired
    ResourceOfferQueueService resourceOfferQueueService;

    @Override
    public void onMessage(final Message message, final byte[] pattern) {

        System.out.println("Message received: " + message.getBody().toString());
        TransResourceOffer resourceOffer = JSONObject.parseObject(message.getBody(),TransResourceOffer.class);
        resourceOfferQueueService.receiveNewOffer(resourceOffer);
    }

}
