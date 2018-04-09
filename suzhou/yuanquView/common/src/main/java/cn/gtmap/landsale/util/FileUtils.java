package cn.gtmap.landsale.util;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;

/**
 * 读取文件
 * Created by lss on 2016-10-26.
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    //读取文件内容长度
    public static int redLen=4098;

    /**
     * 读取远程共享目录的文件
     * @param url
     */
    public static byte[] remoteReadFile(String url){
        InputStream in=null;
        ByteArrayOutputStream out=null;

        try {
            SmbFile file=new SmbFile(url);
            file.connect();
            in = new BufferedInputStream(new SmbFileInputStream(file));
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[redLen];
            int length = 0;
            while((length = in.read(buffer,0,buffer.length)) != -1){
                out.write(buffer,0,length);
            }
            out.flush();
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } finally {
            try {
                if(null != in){
                    in.close();
                }
                if (null != out){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage(),e);
            } finally {
                in = null;
                out = null;
            }

        }
        return null;


    }
}
