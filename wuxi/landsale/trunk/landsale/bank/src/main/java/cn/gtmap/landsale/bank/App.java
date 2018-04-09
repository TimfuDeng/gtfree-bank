package cn.gtmap.landsale.bank;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * .cn.gtmap.landsale.bank.App
 * @author zsj
 * @version V1.0
 */
@SpringBootApplication
@EntityScan({"cn.gtmap.landsale.common.model"})
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(basePackages = {"cn.gtmap.landsale.bank.register"})
public class App {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(App.class, args);
    }

}
