package cn.gtmap.landsale.client;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RestController;

//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * .cn.gtmap.landsale.center.App
 * @author zsj
 * @version V1.0
 */
@SpringBootApplication
@EntityScan({"cn.gtmap.landsale.common.model"})
@EnableDiscoveryClient
@EnableHystrix
@ComponentScan(basePackages = "cn.gtmap.landsale.*")
@EnableFeignClients(basePackages = {"cn.gtmap.landsale.client.register", "cn.gtmap.landsale.common.register"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
@RestController
public class App {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }

}
