package cn.gtmap.landsale.mq;

import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.ResourceOfferService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JIBO on 2016/9/19.
 */
public class OfferMessageService implements InitializingBean {

    static String QUEUE_NAME = "landjs-queue-offer";

    MessageConsumer messageConsumer;

    ResourceOfferService resourceOfferService;

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void setResourceOfferService(ResourceOfferService resourceOfferService) {
        this.resourceOfferService = resourceOfferService;
    }

    public void afterPropertiesSet() throws Exception {
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
//        fixedThreadPool.execute(new Runnable() {
//            public void run() {
//                while (true) {
//                    try {
//                        CloudQueue queue = messageConsumer.getClient().getQueueRef(QUEUE_NAME);
//                        Message popMsg = queue.popMessage();
//                        if (popMsg==null){
//                            Thread.sleep(3000);
//                        }else {
//                            System.out.println("PopMessage Body: " + popMsg.getMessageBody());
//                            try {
//                                TransResourceOffer resourceOffer = JSON.parseObject(popMsg.getMessageBody(), TransResourceOffer.class);
//                                resourceOfferService.insert(resourceOffer);
//                            }catch (Exception ex){
//                                ex.printStackTrace();
//                            }
//                            queue.deleteMessage(popMsg.getReceiptHandle());
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

    }
}
