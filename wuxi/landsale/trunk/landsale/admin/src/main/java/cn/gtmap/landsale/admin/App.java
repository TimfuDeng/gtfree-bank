package cn.gtmap.landsale.admin;


import cn.gtmap.landsale.admin.core.TransResourceContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * .cn.gtmap.landsale.admin.App
 * @author zsj
 * @version V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"cn.gtmap.landsale.admin.register", "cn.gtmap.landsale.common.register"})
@EntityScan({"cn.gtmap.landsale.common.model"})
@ComponentScan(basePackages = "cn.gtmap.landsale.*")
@EnableAspectJAutoProxy
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
public class App {

    @Autowired
    TransResourceContainer transResourceContainer;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }

}
