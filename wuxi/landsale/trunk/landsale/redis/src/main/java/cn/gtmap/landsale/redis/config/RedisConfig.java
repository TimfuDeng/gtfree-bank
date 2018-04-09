//package cn.gtmap.landsale.redis.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.util.concurrent.atomic.AtomicLong;
//
///**
//* @author zsj
//* @version v1.0, 2017/7/5
//*/
//@Configuration
//public class RedisConfig {
//
//    @Autowired
//    StringRedisTemplate template;
//
//    @Autowired
//    RedisConnectionFactory factory;
//
//    final AtomicLong counter = new AtomicLong(0);
//
//    @Scheduled(fixedDelay = 100)
//    public void publish() {
//        template.convertAndSend("resourceId", "Message : plushMsg," + counter.incrementAndGet() +
//                ", " + Thread.currentThread().getName());
//    }
//
//}
