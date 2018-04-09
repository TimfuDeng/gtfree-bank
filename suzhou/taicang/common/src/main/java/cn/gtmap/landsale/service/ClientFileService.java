package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransFile;

/**
 * 文件服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/19
 */
public interface ClientFileService {

    /**
     * 上传文件
     * @param bytes 文件字节流
     * @param fileName 文件名称
     * @param fileKey 文件key
     * @param fileType 文件类型
     * @return
     * @throws Exception
     */
    TransFile uploadFile(byte[] bytes, String fileName, String fileKey, String fileType) throws Exception;

    /**
     * 浏览文件，例如图片
     * @param fileId
     * @return
     */
    byte[] viewFile(String fileId) throws Exception;

}
