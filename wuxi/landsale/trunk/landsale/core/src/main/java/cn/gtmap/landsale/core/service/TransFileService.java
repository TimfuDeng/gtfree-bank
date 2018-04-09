package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @author M150237 on 2017-10-24.
 */
public interface TransFileService {
    /**
     * 根据fileId获取文件对象
     *
     * @param fileId 文件Id
     * @return
     */
    public TransFile getTransFile(String fileId);

    /**
     * 根据外部key获取文件对象列表
     *
     * @param fileKey 外部key
     * @return
     */
    public List<TransFile> getTransFileByKey(String fileKey);

    /**
     * 根据外部key和文件类型获取文件对象列表
     *
     * @param fileKey  外部key
     * @param fileType 文件类型 THUMBNAILS等
     * @return
     */
    public List<TransFile> getTransFileByKey(String fileKey, String fileType);

    /**
     * 据外部key获取除缩略图之外所有文件对象列表
     * @param fileKey
     * @return
     */
    public List<TransFile> getTransFileAttachments(String fileKey);


    /**
     * 据外部key及行政区代码获取除缩略图之外所有文件对象列表
     * @param fileKey
     * @param regionCodes
     * @param pageable
     * @return
     */
    public Page<TransFile> getTransFileByFileKeysAndRegionCode(String fileKey,String regionCodes,Pageable pageable);

    /**
     * 根据外部key获取文件page列表
     *
     * @param fileKey 外部key
     * @param title   查询条件
     * @param request
     * @return
     */
    public Page<TransFile> getTransFilePageByKey(String fileKey, String title, Pageable request);


    /**
     * 获取文件
     *
     * @param file 文件对象
     * @return
     * @throws FileNotFoundException
     */
    public File getFile(TransFile file) throws FileNotFoundException;

    /**
     * 保存文件对象及输入流
     * @param inputStream
     * @param file
     * @return
     * @throws Exception
     */
    public TransFile saveStream(TransFile file, InputStream inputStream) throws Exception;


    /**
     * 存储文件对象，但没有文件本身
     *
     * @param file
     * @return
     * @throws Exception
     */
    ResponseMessage<TransFile> saveTransFile(TransFile file) throws Exception;

    /**
     * 删除文件对象
     *
     * @param file
     */
    public void deleteFile(TransFile file);



    /**
     * 根据文件key，批量删除缩略图
     *
     * @param fileKeys
     */
    public void deleteThumbnailsByKey(Collection<String> fileKeys);

    /**
     * 是否是允许的文件类型。
     *
     * @param fileType 例如pdf、zip等
     * @return
     */
    public boolean allowedFileType(String fileType);

    /**
     * 是否是允许的缩略图文件类型。
     *
     * @param fileType 例如png，jpeg等
     * @return
     */
    public boolean allowedThumbnailType(String fileType);

    /**
     * 处理pagefile的后缀
     *
     * @param transFilePage
     * @return pageFile对象
     */
    public Page<TransFile> dealExt(Page<TransFile> transFilePage);

    /**
     * 根据文件key，批量删除文件对象
     * @param fileKeys
     */
    public void deleteFilesByKey(Collection<String> fileKeys);


}
