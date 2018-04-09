package cn.gtmap.landsale.admin.service;

import cn.gtmap.landsale.model.TransFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public interface FileStoreService {
    /**
     * 根据storePath删除文件
     * @param file
     * @return
     */
    public boolean delete(TransFile file);

    /**
     * 根据storePath存储文件
     * @param file
     * @param inputStream
     */
    public void save(TransFile file,InputStream inputStream) throws Exception;

    /**
     * 存储文件对象
     * @param file 文件对象
     * @param fileUrl 物理文件url地址
     * @throws Exception
     */
    public void save(TransFile file,String fileUrl) throws Exception;

    /**
     * 获取文件的storePath
     * @return
     */
    public String getStorePath();

    /**
     * 根据storePath，获取文件对象
     * @param storePath
     * @return
     */
    public File getFile(String storePath);
}
