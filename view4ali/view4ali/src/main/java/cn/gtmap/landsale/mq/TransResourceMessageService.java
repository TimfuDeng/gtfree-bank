package cn.gtmap.landsale.mq;

import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.TransResourceService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JIBO on 2016/9/19.
 */
public class TransResourceMessageService implements InitializingBean {

    static String QUEUE_NAME = "landjs-queue-resource";

    MessageConsumer messageConsumer;

    TransResourceService transResourceService;

    public void setMessageConsumer(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
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
//                                TransResource transResource = JSON.parseObject(popMsg.getMessageBody(), TransResource.class);
//                                if (StringUtils.isNotBlank(transResource.getOverStatus())){
//                                    TransResource resource=
//                                            transResourceService.selectByKey(transResource.getResourceId());
//                                    transResourceService.updateStatusToFinish(resource,transResource.getOverStatus(),transResource.getTransUser());
//                                }else {
//                                    transResourceService.insertOrUpdate(transResource);
//                                }
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
