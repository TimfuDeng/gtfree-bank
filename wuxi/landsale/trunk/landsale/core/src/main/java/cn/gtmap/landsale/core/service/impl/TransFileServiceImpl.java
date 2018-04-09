package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFile;
import cn.gtmap.landsale.core.service.FileStoreService;
import cn.gtmap.landsale.core.service.TransFileService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 文件服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/28
 */
@Service
public class TransFileServiceImpl  extends HibernateRepo<TransFile, String> implements TransFileService {

    @Autowired
    FileStoreService fileStoreService;

    @Autowired
    ServletContext servletContext;

    private Set<String> allowedFileType= Sets.newHashSet("gif", "bmp", "jpg", "jpeg", "png", "pdf", "zip", "rar", "doc", "docx", "txt");

    private Set<String> allowedThumbnailType = Sets.newHashSet("gif", "bmp", "jpg", "jpeg", "png");


    /**
     * 根据fileId获取文件对象
     *
     * @param fileId 文件Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
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
    public List<TransFile> getTransFileByKey(String fileKey) {
        return StringUtils.isNotBlank(fileKey)?list(criteria(Restrictions.eq("fileKey", fileKey)).addOrder(Order.asc("fileType"))):null;
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
    public List<TransFile> getTransFileByKey(String fileKey,String fileType) {
        return StringUtils.isNotBlank(fileKey)?list(criteria(Restrictions.eq("fileKey", fileKey)).add(Restrictions.eq("fileType", fileType))):null;
    }

    /**
     * 根据外部key获取文件page列表
     *
     * @param fileKey 外部key
     * @param title   查询条件
     * @param request
     * @return
     */
    @Override
    public Page<TransFile> getTransFilePageByKey(String fileKey, String title, Pageable request) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("fileName", title, MatchMode.ANYWHERE));
        }
        criterionList.add(Restrictions.eq("fileKey", fileKey));
        Page<TransFile> list=find(criteria(criterionList).addOrder(Order.desc("createAt")),request);
        return list;
    }

    /**
     * 据外部key获取除缩略图之外所有文件对象列表
     *
     * @param fileKey
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransFile> getTransFileAttachments(String fileKey) {
        return StringUtils.isNotBlank(fileKey)?list(criteria(Restrictions.eq("fileKey",fileKey)).add(Restrictions.ne("fileType", Constants.FileType.THUMBNAIL.getCode())).addOrder(Order.asc("fileType"))):null;
    }

    /**
     * 据外部key及行政区代码获取除缩略图之外所有文件对象列表
     * @param fileKey
     * @param regionCodes
     * @param pageable
     * @return
     */
    @Override
    public Page<TransFile> getTransFileByFileKeysAndRegionCode(String fileKey, String regionCodes, Pageable pageable) {
        List<Criterion> criterionList= Lists.newArrayList();
        if (regionCodes != null && !regionCodes.isEmpty()) {
            if (regionCodes.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCodes.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCodes, MatchMode.START));
            }
        }
        criterionList.add(Restrictions.eq("fileKey", fileKey));
        criterionList.add(Restrictions.isNotNull("storePath"));
        Page<TransFile> list=find(criteria(criterionList).addOrder(Order.desc("createAt")),pageable);
        return list;
    }

    /**
     * 获取文件
     *
     * @param file 文件对象
     * @return
     * @throws java.io.FileNotFoundException
     */
    @Override
    @Transactional
    public File getFile(TransFile file) throws FileNotFoundException {
        if(StringUtils.isNotBlank(file.getStorePath())){
            return fileStoreService.getFile(file.getStorePath());
        }
        return null;
    }



    @Override
    public Page<TransFile> dealExt(Page<TransFile> transFilePage) {
        String fileName=null;
        if (transFilePage.getSize()>1){
            for (TransFile transFile:transFilePage.getItems()){
                fileName=transFile.getFileName();
                if(fileName!=null){
                    int poinPosit=fileName.lastIndexOf(46);
                    if(poinPosit>-1){
                        transFile.setFileName(fileName.substring(0,poinPosit));
                    }

                }

            }
        }

        return transFilePage;
    }

    /**
     * 保存文件对象及输入流
     *
     * @param file
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransFile saveStream(TransFile file, InputStream inputStream) throws Exception {
        if(StringUtils.isBlank(file.getStorePath())) {
            file.setStorePath(fileStoreService.getStorePath());
        }
        fileStoreService.save(file, inputStream);
        return saveOrUpdate(file);
    }


    /**
     * 存储文件对象，但没有文件本身
     *
     * @param file
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransFile> saveTransFile(TransFile file) throws Exception {
        saveOrUpdate(file);
        return new ResponseMessage(true, file);
    }

    /**
     * 删除文件对象
     *
     * @param file
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(TransFile file) {
        deleteById(file.getFileId());
        if(StringUtils.isNotBlank(file.getStorePath())){
            fileStoreService.delete(file);
        }

    }


    /**
     * 根据文件key，批量删除缩略图
     *
     * @param fileKeys
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
     * 根据文件key，批量删除文件对象
     *
     * @param fileKeys
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFilesByKey(Collection<String> fileKeys) {
        Iterator<String> iterator = fileKeys.iterator();
        while (iterator.hasNext()){
            String fileKey = iterator.next();
            List<TransFile> transFileList = getTransFileByKey(fileKey);
            delete(transFileList);
        }
    }
}

