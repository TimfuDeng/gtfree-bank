package cn.gtmap.landsale.bank.core;

import cn.gtmap.landsale.bank.service.TransBankReciverService;
import cn.gtmap.landsale.bank.util.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.ServerSocket;

/**
 * socket server用来接受银行报文
 * @author Jibo on 2015/5/9.
 */
@Configuration
public class BankSocketServer implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(BankSocketServer.class);

    @Value("${banksocket.port}")
    int port;

    @Autowired
    TransBankReciverService transBankReciverService;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(port);
                    log.debug("---Socket启动,端口：" + port + "-----");
                    while (true) {
                        BankSocketReciver ssc = new BankSocketReciver(server.accept(), transBankReciverService);
                        ssc.start();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }




}
