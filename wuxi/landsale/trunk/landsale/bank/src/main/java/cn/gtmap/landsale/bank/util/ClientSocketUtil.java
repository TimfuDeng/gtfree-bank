package cn.gtmap.landsale.bank.util;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * @author jibo1_000 on 2015/5/13.
 */
public class ClientSocketUtil {
    protected static Socket server;
    //  private String url="";
//  private int port=9766;
    private boolean isConnected = true;

    public ClientSocketUtil(int port) {//链接自己，本机上测试的时候用
        try {
            server = new Socket(InetAddress.getLocalHost(), port);
        } catch (Exception e) {
            isConnected = false;
        }
    }

    public ClientSocketUtil(String url, int port) {
        try {
            server = new Socket(url, port);
        } catch (Exception e) {
            isConnected = false;
        }
    }

    /**
     * 向服务端程序发送数据
     * @param msg
     */
    public void send(String msg) {
        DataOutputStream out=null;
        try {
            out = new DataOutputStream((server.getOutputStream()));
            out.write(msg.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从服务端程序接收数据,返回一个BufferedReader
     *
     * @return
     */
    public String recieve() {
        byte [] head = new byte[6];
        byte [] body=null;
        try {
            BufferedInputStream bufIn = new BufferedInputStream(server.getInputStream());
            bufIn.read(head);
            int len1 = Integer.parseInt(new String(head));
            body = new byte[len1];
            bufIn.read(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(body, Charset.forName("UTF-8"));
    }

    public void close() {
        try {
            if (server != null || server.isConnected()) {
                server.close();
            }
        } catch (IOException e) {
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

}
