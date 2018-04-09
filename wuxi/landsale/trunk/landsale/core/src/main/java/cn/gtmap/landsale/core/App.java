package cn.gtmap.landsale.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * .cn.gtmap.landsale.center.App
 * @author zsj
 * @version V1.0
 */
@SpringBootApplication
@EntityScan({"cn.gtmap.landsale.common.model"})
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"cn.gtmap.landsale.core.register"})
@ComponentScan(basePackages="cn.gtmap.landsale.core.*")
@ImportResource(locations={"classpath:dict/app-dict.xml"})
//@EnableScheduling
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(App.class, args);
    }

}
