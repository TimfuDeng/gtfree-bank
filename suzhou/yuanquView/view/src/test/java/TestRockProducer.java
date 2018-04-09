/*
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

*/
/**
 * Created by liushaoshuai on 2017/4/14.
 *//*

public class TestRockProducer {
    public static void main(String[] args) {
         DefaultMQProducer mqProducer=null;
        try {
            mqProducer = new DefaultMQProducer("ProducerGroupName");
            mqProducer.setNamesrvAddr("192.168.8.188:9876");
            mqProducer.setInstanceName("Producer");
            mqProducer.start();
            for (int i=0;i<3;i++){
                Message message = new Message();
                message.setTopic("landjs_topic_test");
                message.setTags("landjs_tag_crgg_test");
                message.setKeys("landjs_key__test_00"+i);
                message.setBody(("hello rocketmq" + i).getBytes());
                SendResult sendResult = mqProducer.send(message);
                System.out.println("landjs_key_001==="+sendResult);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (mqProducer!=null)
                mqProducer.shutdown();
        }

    }
}
*/
