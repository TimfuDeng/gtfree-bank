package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * 文件服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public interface TransFileService {

    /**
     * 根据fileId获取文件对象
     * @param fileId 文件Id
     * @return
     */
    public TransFile getTransFile(String fileId);

    /**
     * 根据外部key获取文件对象列表
     * @param fileKey 外部key
     * @return
     */
    public List<TransFile> getTransFileByKey(String fileKey);

    /**
     * 根据外部key获取文件page列表
     * @param fileKey 外部key
     * @param title 查询条件
     * @param request
     * @return
     */
    public Page<TransFile> getTransFilePageByKey(String fileKey,String title,Pageable request);


    /**
     * 根据外部key获取文件page列表
     * @param fileKey 外部key
     * @param title 查询条件
     * @param request 分页请求对象
     * @param regionCodes 行政区部门权限
     * @return
     */
    public Page<TransFile> getFilePageByKeyByRegionCode(String fileKey,String title,Pageable request,Collection<String> regionCodes);

    /**
     * 根据外部key和文件类型获取文件对象列表
     * @param fileKey 外部key
     * @param fileType 文件类型 THUMBNAILS等
     * @return
     */
    public List<TransFile> getTransFileByKey(String fileKey,String fileType);

    /**
     * 据外部key获取除缩略图之外所有文件对象列表
     * @param fileKey
     * @return
     */
    public List<TransFile> getTransFileAttachments(String fileKey);

    /**
     * 根据外部key和分辨率获取缩略图。如果指定的分辨率为null，则返回原始图。如果指定的分辨率不存在，则会自动生成
     * @param fileKey 外部key
     * @param resolutions 分辨率，如果未指定分辨率，则返回原始图
     * @return
     */
    public List<TransFile> getTransFileThumbnails(String fileKey,String resolutions) throws Exception;


    /**
     * 获取文件对象的输出流
     * @param file 文件对象
     * @return 输出流
     */
    public OutputStream getFileOutputStream(TransFile file) throws FileNotFoundException;

    /**
     * 获取文件
     * @param file 文件对象
     * @return
     * @throws FileNotFoundException
     */
    public File getFile(TransFile file) throws FileNotFoundException;

    /**
     * 获取文件对象的输出流
     * @param file 文件对象
     * @return 输出流
     */
    public InputStream getFileInputStream(TransFile file) throws FileNotFoundException;
    /**
     * 保存文件对象及输入流
     * @param file
     * @param inputStream 输入流
     */
    public TransFile save(TransFile file,InputStream inputStream) throws Exception;

    /**
     * 存储文件对象和文件本身
     * @param file 文件对象
     * @param fileUrl 物理文件url地址
     * @throws Exception
     */
    public TransFile save(TransFile file,String fileUrl) throws Exception;

    /**
     * 存储文件对象，但没有文件本身
     * @param file
     * @throws Exception
     */
    public void saveTransFile(TransFile file) throws Exception;

    /**
     * 删除文件对象
     * @param file
     */
    public void deleteFile(TransFile file);

    /**
     * 根据文件对象Id，批量删除文件对象
     * @param fileIds 文件对象Id
     */
    public void deleteFiles(Collection<String> fileIds);

    /**
     * 根据文件key，批量删除文件对象
     * @param fileKeys
     */
    public void deleteFilesByKey(Collection<String> fileKeys);

    /**
     * 根据文件key，批量删除缩略图
     * @param fileKeys
     */
    public void deleteThumbnailsByKey(Collection<String> fileKeys);

    /**
     * 是否是允许的文件类型。
     * @param fileType 例如pdf、zip等
     * @return
     */
    public boolean allowedFileType(String fileType);

    /**
     * 是否是允许的缩略图文件类型。
     * @param fileType 例如png，jpeg等
     * @return
     */
    public boolean allowedThumbnailType(String fileType);


    /**
     * 处理pagefile的后缀
     * @param transFilePage
     * @return  pageFile对象
     */
    public Page<TransFile> dealExt(Page<TransFile> transFilePage);

    /**
     * 处理文件对象，将页面上面的文件对象覆盖文件上传对象的文件名，描述，以及创建时间
     * @param file 文件上传上传对象
     * @param transFile 文件对象
     * @return 文件对象
     */
    public TransFile dealFile(TransFile file,TransFile transFile);
}
