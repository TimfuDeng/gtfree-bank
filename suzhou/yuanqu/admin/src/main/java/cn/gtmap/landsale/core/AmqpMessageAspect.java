/*
package cn.gtmap.landsale.core;

import cn.gtmap.landsale.model.TransResourceOffer;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

*/
/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/21
 *//*

@Aspect
public class AmqpMessageAspect {

    @Autowired
    AmqpTemplate amqpTemplate;

    @AfterReturning(pointcut="execution(* cn.gtmap.landsale.admin.service.impl.ClientServiceImpl.acceptResourceOffer(..))",returning="transResourceOffer")
    public void addResourceOffer(JoinPoint jp,TransResourceOffer transResourceOffer){
        if(transResourceOffer!=null) {
            amqpTemplate.convertAndSend("resourceOffer.add", transResourceOffer);
        }
    }
}
*/
