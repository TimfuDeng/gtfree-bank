package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.util.UUID;
import cn.gtmap.landsale.common.model.TransFile;
import cn.gtmap.landsale.core.service.FileStoreService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 文件存取服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
@Service
public class FileStoreServiceImpl implements FileStoreService {

    private static final int TRANSFER_SIZE = 16 * 1024;
    @Value("${fileStore.directory}")
    private String baseDir;
    public static final String STORE_PROTOCOL = "store://";
    public final SimpleDateFormat PATH_SDF = new SimpleDateFormat("yyyy/MM/dd/HH/");


    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * 根据storePath删除文件
     *
     * @param file
     * @return
     */
    @Override
    public boolean delete(TransFile file) {
        String filePath = toFilePath(file.getStorePath());
        File targetFile = new File(filePath);
        if(targetFile.exists()) {
            return targetFile.delete();
        }
        return false;
    }

    /**
     * 根据storePath存储文件
     *
     * @param file
     * @param inputStream
     */
    @Override
    public void save(TransFile file, InputStream inputStream) throws Exception {
        ReadableByteChannel in = null;
        FileChannel out = null;
        try {
            File targetFile = new File(toFilePath(file.getStorePath()));
            if(targetFile.exists()) {
                targetFile.delete();
            }
            File targetDir =targetFile.getParentFile();
            if(!targetDir.exists()) {
                targetDir.mkdirs();
            }
            in = Channels.newChannel(inputStream);
            out = new FileOutputStream(targetFile).getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(TRANSFER_SIZE);
            while (in.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
            if(file.getFileSize()==0) {
                file.setFileSize(out.size());
            }
        } catch (Exception e) {
            throw new Exception("Failed to save file:" + file.getFileName());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 存储文件对象
     *
     * @param file    文件对象
     * @param fileUrl 物理文件url地址
     * @throws Exception
     */
    @Override
    public void save(TransFile file, String fileUrl) throws Exception {
        ReadableByteChannel in = null;
        FileChannel out = null;
        try {
            URL url = new URL(fileUrl);
            File targetFile = new File(toFilePath(file.getStorePath()));
            if(targetFile.exists()) {
                targetFile.delete();
            }
            File targetDir =targetFile.getParentFile();
            if(!targetDir.exists()) {
                targetDir.mkdirs();
            }
            in = Channels.newChannel(url.openStream());
            out = new FileOutputStream(targetFile).getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(TRANSFER_SIZE);
            while (in.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }

            if(file.getFileSize()==0) {
                file.setFileSize(out.size());
            }
        } catch (Exception e) {
            throw new Exception("Failed to save file:" + file.getFileName());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 获取文件的storePath
     *
     * @return
     */
    @Override
    public String getStorePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(STORE_PROTOCOL).append(PATH_SDF.format(Calendar.getInstance().getTime())).append(UUID.hex32());
        return stringBuilder.toString();
    }

    /**
     * 根据storePath，获取文件对象
     *
     * @param storePath
     * @return
     */
    @Override
    public File getFile(String storePath) {
        return new File(toFilePath(storePath));
    }

    private String toFilePath(String storePath){
        if(StringUtils.isNotBlank(storePath)){
            String relativePath = null;
            if(storePath.indexOf(STORE_PROTOCOL)>-1){
                relativePath = "/"+storePath.substring(STORE_PROTOCOL.length());
            }else {
                relativePath = storePath;
            }
            return baseDir+relativePath;
        }
        return null;
    }


}

