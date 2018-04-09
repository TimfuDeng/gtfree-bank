package cn.gtmap.landsale.core;

import cn.gtmap.landsale.service.TransBankInterfaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

/**
 * Created by jibo1_000 on 2015/5/13.
 */
public class BankSocketReciver extends  Thread{
    private static Logger log = LoggerFactory.getLogger(BankSocketReciver.class);
    private Socket socket;

    TransBankInterfaceService transBankInterfaceService;

    public BankSocketReciver(Socket client,TransBankInterfaceService transBankInterfaceService){
        this.socket=client;
        this.transBankInterfaceService=transBankInterfaceService;
    }

    public String recieve(DataInputStream in) {
        byte [] head = new byte[6];
        byte [] body=null;
        try {
            in.read(head);
            int len1 = Integer.parseInt(new String(head));
            body = new byte[len1];
            in.read(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(body, Charset.forName("GBK"));
    }

    public void run(){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String receiveXml=recieve(in);
            String result=transBankInterfaceService.socketServerReceive(receiveXml.trim());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(result.getBytes());//服务器响应给客户端
            socket.close();
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
////            PrintWriter out = new PrintWriter(client.getOutputStream());
//            OutputStreamWriter  outSW = new OutputStreamWriter(client.getOutputStream(), "UTF-8");
//            BufferedWriter bw = new BufferedWriter(outSW);
//            String data = in.readLine();
//            String result=transBankInterfaceService.socketServerReceive(data);
////            out.println(result);//向客户端发送
//            bw.write(result);
//            ///////////////////////////////////////////////////////
//            bw.flush();
//            client.close();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {

        }
    }
}
