package cn.gtmap.landsale.core;

import cn.gtmap.landsale.service.TransBankInterfaceService;
import cn.gtmap.landsale.util.BankXmlUtil;
import cn.gtmap.landsale.util.ThreadPool;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket server用来接受银行报文
 * Created by Jibo on 2015/5/9.
 */
public class BankSocketServer implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(BankSocketServer.class);

    int port;
    TransBankInterfaceService transBankInterfaceService;

    public void setTransBankInterfaceService(TransBankInterfaceService transBankInterfaceService) {
        this.transBankInterfaceService = transBankInterfaceService;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(port);
                    log.debug("---Socket启动,端口：" + port + "-----");
                    while (true) {
                        BankSocketReciver ssc = new BankSocketReciver(server.accept(),transBankInterfaceService);
                        ssc.start();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }




}
