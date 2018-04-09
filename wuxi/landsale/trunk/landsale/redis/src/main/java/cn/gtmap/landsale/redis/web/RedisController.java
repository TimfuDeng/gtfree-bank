package cn.gtmap.landsale.redis.web;


import cn.gtmap.landsale.common.model.TransResourceOffer;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Redis Controller
 * @author zsj
 * @version v1.0, 2017/7/12
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    private static Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    final AtomicLong counter = new AtomicLong(0);

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public void test(String id, String message) throws Exception {
        stringRedisTemplate.convertAndSend(id, "Message : " + id + ";" + message + ";" + counter.incrementAndGet() +
                ", " + Thread.currentThread().getName());
    }

    @RequestMapping(value = "/sendNotice", method = RequestMethod.POST)
    @ResponseBody
    public void sendNotice(String id, Object object) throws Exception {
        stringRedisTemplate.convertAndSend(id, object);
    }

    @RequestMapping(value = "/sendOffer", method = RequestMethod.POST)
    @ResponseBody
    public void sendOffer(@RequestBody TransResourceOffer offer) throws Exception {
        stringRedisTemplate.convertAndSend("offer", JSON.toJSONString(offer));
    }

}

