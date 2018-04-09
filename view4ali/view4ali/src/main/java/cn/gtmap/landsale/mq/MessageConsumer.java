package cn.gtmap.landsale.mq;


import com.aliyun.openservices.ons.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

/**
 * 阿里云的消息中心，接收并处理消息
 * Created by JIBO on 2016/9/19.
 */
public class MessageConsumer implements InitializingBean,DisposableBean {

    Properties properties;
    private static Logger Log = LoggerFactory.getLogger(MessageConsumer.class);



    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void destroy() throws Exception {

    }

    public void afterPropertiesSet() throws Exception {
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("LANDJS", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                Log.info("Receive: " + message);
                if ("crgg".equalsIgnoreCase(message.getTag())){

                }else if ("offer".equalsIgnoreCase(message.getTag())){

                }


                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }


}
