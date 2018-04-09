package cn.gtmap.landsale.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    @Autowired
    StringRedisTemplate template;

    @Autowired
    MyMessageListener myMessageListener;

//    @Scheduled(fixedDelay = 100)
//    public void publish() {
//        final AtomicLong counter = new AtomicLong(0);
//        template.convertAndSend("plushMsg", "Message : plushMsg," + counter.incrementAndGet() +
//                ", " + Thread.currentThread().getName());
//    }
//
//    @Scheduled(fixedDelay = 200)
//    public void publish1() {
//        final AtomicLong counter = new AtomicLong(0);
//        template.convertAndSend("plushMsg1", "Message : plushMsg1," + counter.incrementAndGet() +
//                ", " + Thread.currentThread().getName());
//    }

//    @Bean
//    MyPublisher redisPublisher(RedisConnectionFactory connectionFactory) {
//        return new MyPublisher(template(connectionFactory), topic("plushMsg"));
//    }

//    @Bean
//    MyPublisher redisPublisher1(RedisConnectionFactory connectionFactory) {
//        return new MyPublisher(template(connectionFactory), topic("plushMsg1"));
//    }

//    @Bean
    ChannelTopic topic(String key) {
        return new ChannelTopic(key);
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(myMessageListener);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), topic("offer"));
        return container;
    }
//
//    @Bean
//    RedisMessageListenerContainer redisContainer1(RedisConnectionFactory factory) {
//        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(factory);
//        container.addMessageListener(messageListener(), topic("plushMsg1"));
//        return container;
//    }

}
