package cn.gtmap.landsale.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * .cn.gtmap.landsale.center.App
 * @author zsj
 * @version V1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class App {

    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }
}
