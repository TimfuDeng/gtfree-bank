package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.FileStoreService;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.service.TransFileService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * 文件服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
public class TransFileServiceImpl  extends HibernateRepo<TransFile, String> implements TransFileService {

    private FileStoreService fileStoreService;

    @Autowired
    ServletContext servletContext;

    private Set<String> allowedFileType= Sets.newHashSet();

    private Set<String> allowedThumbnailType = Sets.newHashSet("gif", "bmp", "jpg", "jpeg", "png");

    public void setFileStoreService(FileStoreService fileStoreService) {
        this.fileStoreService = fileStoreService;
    }

    public void setAllowedFileType(Set<String> allowedFileType) {
        this.allowedFileType = allowedFileType;
    }

    /**
     * 根据fileId获取文件对象
     *
     * @param fileId 文件Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getTransFile'+#fileId")
    public TransFile getTransFile(String fileId) {
        return get(fileId);
    }

    /**
     * 根据外部key获取文件对象列表
     *
     * @param fileKey 外部key
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getTransFileByKey'+#fileKey")
    public List<TransFile> getTransFileByKey(String fileKey) {
        return StringUtils.isNotBlank(fileKey)?list(criteria(Restrictions.eq("fileKey", fileKey)).addOrder(Order.asc("fileType"))):null;
    }

    /**
     *
     * @param fileKey 外部key
     * @param title 查询条件
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransFile> getTransFilePageByKey(String fileKey,String title,Pageable request) {
        //createAt
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("fileName", title, MatchMode.ANYWHERE));
        }
        criterionList.add(Restrictions.eq("fileKey", fileKey));
        Page<TransFile> list=find(criteria(criterionList).addOrder(Order.desc("createAt")),request);
        return list;


    }

    /**
     * 根据外部key获取文件page列表
     *
     * @param fileKey     外部key
     * @param title       查询条件
     * @param request     分页请求对象
     * @param regionCodes 行政区部门权限
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransFile> getFilePageByKeyByRegionCode(String fileKey, String title, Pageable request, Collection<String> regionCodes) {
        //createAt
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("fileName", title, MatchMode.ANYWHERE));
        }
        if (null!=regionCodes&&!regionCodes.isEmpty()){
            criterionList.add(Restrictions.in("regionCode",regionCodes));
        }
        criterionList.add(Restrictions.eq("fileKey", fileKey));
        Page<TransFile> list=find(criteria(criterionList).addOrder(Order.desc("fileNo")),request);
        return list;
    }

    /**
     * 根据外部key和文件类型获取文件对象列表
     *
     * @param fileKey  外部key
     * @param fileType 文件类型 ATTACHMENT，THUMBNAILS
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getTransFileByKey'+#fileKey+'fileType'+#fileType")
    public List<TransFile> getTransFileByKey(String fileKey,String fileType) {
        return StringUtils.isNotBlank(fileKey)?list(criteria(Restrictions.eq("fileKey", fileKey)).add(Restrictions.eq("fileType", fileType))):null;
    }

    /**
     * 据外部key获取除缩略图之外所有文件对象列表
     *
     * @param fileKey
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getTransFileAttachments'+#fileKey")
    public List<TransFile> getTransFileAttachments(String fileKey) {
        return StringUtils.isNotBlank(fileKey)?list(criteria(Restrictions.eq("fileKey",fileKey)).add(Restrictions.ne("fileType", Constants.FileType.THUMBNAIL.getCode())).addOrder(Order.asc("fileType"))):null;
    }

    /**
     * 根据外部key和分辨率获取缩略图。如果指定的分辨率为null，则返回原始图。如果指定的分辨率不存在，则会自动生成
     *
     * @param fileKey     外部key
     * @param resolutions 分辨率，如果未指定分辨率，则返回原始图
     * @return
     */
    @Override
    @Transactional
    public List<TransFile> getTransFileThumbnails(String fileKey, String resolutions) throws Exception {
        List previews = getTransFileThumbnailsByResolution(fileKey, resolutions);
        if(StringUtils.isBlank(resolutions)) //返回原始图
            return previews;
        else{
            if(previews.isEmpty()){
                List<TransFile> thumbnailList = getTransFileThumbnailsByResolution(fileKey, null);
                if(thumbnailList.isEmpty())
                    return previews;
                String baseDir = servletContext.getRealPath("/");
                File tmpDir = new File(baseDir+"/tmp");
                if(!tmpDir.exists())
                    tmpDir.mkdir();
                String fileExt = FileUtils.getExt(thumbnailList.get(0).getFileName());

                TransFile transFile = new TransFile();
                transFile.setFileKey(fileKey);
                transFile.setFileName(thumbnailList.get(0).getFileName());
                transFile.setCreateAt(Calendar.getInstance().getTime());

                transFile.setFileType(Constants.FileType.THUMBNAIL.getCode());
                transFile.setResolution(resolutions);
                int width = Integer.valueOf(resolutions.split("_")[0]);
                int height = Integer.valueOf(resolutions.split("_")[1]);
                File tmpThumbnailFile = new File(tmpDir, cn.gtmap.egovplat.core.util.UUID.hex32()+"."+fileExt);
                try {
                    Thumbnails.of(getFile(thumbnailList.get(0))).size(width, height).toFile(tmpThumbnailFile);
                    transFile.setFileSize(tmpThumbnailFile.getTotalSpace());
                    previews.add(save(transFile, new FileInputStream(tmpThumbnailFile)));
                }finally {
                    if(tmpThumbnailFile.exists())
                        tmpThumbnailFile.delete();
                }

            }
            return previews;
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getTransFileThumbnails'+#fileKey+'resolutions'+#resolutions")
    private List<TransFile> getTransFileThumbnailsByResolution(String fileKey, String resolution){
        List<org.hibernate.criterion.Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("fileKey", fileKey));
        criterionList.add(Restrictions.eq("fileType", Constants.FileType.THUMBNAIL.getCode()));
        if(StringUtils.isBlank(resolution)){
            criterionList.add(Restrictions.isNull("resolution"));
        }else{
            criterionList.add(Restrictions.eq("resolution", resolution));
        }
        return list(criteria(criterionList));
    }

    /**
     * 获取文件对象的输出流
     *
     * @param file 文件对象
     * @return 输出流
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getFileOutputStream'+#file")
    public OutputStream getFileOutputStream(TransFile file) throws FileNotFoundException {
        if(StringUtils.isNotBlank(file.getStorePath())){
            return new FileOutputStream(fileStoreService.getFile(file.getStorePath()));
        }
        return null;
    }

    /**
     * 获取文件
     *
     * @param file 文件对象
     * @return
     * @throws FileNotFoundException
     */
    @Override
    @Cacheable(value="FileCache",key="'getFile'+#file")
    public File getFile(TransFile file) throws FileNotFoundException {
        if(StringUtils.isNotBlank(file.getStorePath())){
            return fileStoreService.getFile(file.getStorePath());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="FileCache",key="'getFileInputStream'+#file")
    public InputStream getFileInputStream(TransFile file) throws FileNotFoundException{
        if(StringUtils.isNotBlank(file.getStorePath())){
            return new FileInputStream(fileStoreService.getFile(file.getStorePath()));
        }
        return null;
    }
    /**
     * 保存文件对象及输入流
     *
     * @param file
     * @param inputStream 输入流
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public TransFile save(TransFile file, InputStream inputStream) throws Exception {
        if(StringUtils.isBlank(file.getStorePath()))
            file.setStorePath(fileStoreService.getStorePath());
        fileStoreService.save(file,inputStream);
        return saveOrUpdate(file);
    }

    /**
     * 存储文件对象
     *
     * @param file    文件对象
     * @param fileUrl 物理文件url地址
     * @throws Exception
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public TransFile save(TransFile file, String fileUrl) throws Exception {
        return save(file,new URL(fileUrl).openStream());
    }

    /**
     * 存储文件对象，但没有文件本身
     *
     * @param file
     * @throws Exception
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public void saveTransFile(TransFile file) throws Exception {
        saveOrUpdate(file);
    }

    /**
     * 删除文件对象
     *
     * @param file
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public void deleteFile(TransFile file) {
        deleteById(file.getFileId());
        if(file.getStorePath()!=null){
            fileStoreService.delete(file);
        }
    }

    /**
     * 根据文件对象Id，批量删除文件对象
     *
     * @param fileIds 文件对象Id
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public void deleteFiles(Collection<String> fileIds) {
        for(String fileId:fileIds){
            TransFile file = getTransFile(fileId);
            if(file!=null)
                delete(file);
        }
    }

    /**
     * 根据文件key，批量删除文件对象
     *
     * @param fileKeys
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public void deleteFilesByKey(Collection<String> fileKeys) {
        Iterator<String> iterator = fileKeys.iterator();
        while (iterator.hasNext()){
            String fileKey = iterator.next();
            List<TransFile> transFileList = getTransFileByKey(fileKey);
            delete(transFileList);
        }
    }

    /**
     * 根据文件key，批量删除缩略图
     *
     * @param fileKeys
     */
    @Override
    @Transactional
    @Caching(evict={
            @CacheEvict(value="FileCache",allEntries=true)
    })
    public void deleteThumbnailsByKey(Collection<String> fileKeys) {
        Iterator<String> iterator = fileKeys.iterator();
        while (iterator.hasNext()){
            String fileKey = iterator.next();
            List<TransFile> transFileList = getTransFileByKey(fileKey, Constants.FileType.THUMBNAIL.getCode());
            delete(transFileList);
        }
    }

    /**
     * 是否是允许的文件类型。
     *
     * @param fileType 例如pdf、zip等
     * @return
     */
    @Override
    public boolean allowedFileType(String fileType) {
        return StringUtils.isBlank(fileType)?false:allowedFileType.contains(StringUtils.lowerCase(fileType));
    }

    /**
     * 是否是允许的缩略图文件类型。
     *
     * @param fileType 例如png，jpeg等
     * @return
     */
    @Override
    public boolean allowedThumbnailType(String fileType) {
        return StringUtils.isBlank(fileType)?false:allowedThumbnailType.contains(StringUtils.lowerCase(fileType));
    }

    /**
     * 处理文件显示后缀
     * @param transFilePage
     * @return
     */
    @Override
    public Page<TransFile> dealExt(Page<TransFile> transFilePage) {

        String fileName=null;
        for (TransFile transFile:transFilePage.getItems()){
            fileName=transFile.getFileName();
            if(fileName!=null){
                int poinPosit=fileName.lastIndexOf(46);
                if(poinPosit>-1){
                    transFile.setFileName(fileName.substring(0,poinPosit));
                }

            }

        }

        return transFilePage;
    }

    /**
     *
     * @param file 文件上传上传对象
     * @param transFile 文件对象
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransFile dealFile(TransFile file, TransFile transFile) {
        if(StringUtils.isBlank(transFile.getFileId())){//没上传文件
            transFile.setFileId(null);
            file=transFile;
            file.setFileKey("ziliao");
        }else{//上传了文件
            String fileId=transFile.getFileId();
            file= getTransFile(fileId);
            String fileName=file.getFileName();
            String  suffixFile="."+FileUtils.getExt(fileName);
            // String suffixFile= file.getFileName().substring(fileName.length()-4);
            file.setFileName(transFile.getFileName()+suffixFile);
            file.setDescription(transFile.getDescription());
            file.setCreateAt(transFile.getCreateAt());
            file.setFileNo(transFile.getFileNo());
        }
        return file;
    }
}
