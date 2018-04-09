//package cn.gtmap.landsale.core.config;
//
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.util.concurrent.atomic.AtomicLong;
//
//public class MyPublisher {
//
//    private final RedisTemplate template;
//    private final ChannelTopic topic;
//    private final AtomicLong counter = new AtomicLong(0);
//
//    public MyPublisher(final StringRedisTemplate template,
//                               final ChannelTopic topic) {
//        this.template = template;
//        this.topic = topic;
//    }
//
//    @Scheduled(fixedDelay = 100)
//    public void publish() {
//        template.convertAndSend("plushMsg", "Message : plushMsg," + counter.incrementAndGet() +
//                ", " + Thread.currentThread().getName());
//    }
//
//    @Scheduled(fixedDelay = 200)
//    public void publish1() {
//        template.convertAndSend("plushMsg1", "Message : plushMsg1," + counter.incrementAndGet() +
//                ", " + Thread.currentThread().getName());
//    }
//}