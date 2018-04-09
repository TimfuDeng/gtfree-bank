package cn.gtmap.landsale.core.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.ExUtils;
import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.egovplat.core.util.UUID;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFile;
import cn.gtmap.landsale.core.service.TransFileService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * @author M150237 on 2017-10-25.
 */
@RestController
@RequestMapping(value = "/transfile")
public class TransFileController {

    @Autowired
    TransFileService transFileService;

    @Autowired
    ServletContext servletContext;

    @Value("${file.maxUploadSize}")
    long maxUploadSize;

    /**
     * 上传附件
     *
     * @param file     文件对象
     * @param fileKey  文件的key
     * @param fileType 文件类型
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseMessage<TransFile> uploadFile(@RequestParam MultipartFile file, HttpServletResponse response, @RequestParam(value = "fileKey", required = false) String fileKey, String fileType) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if (StringUtils.isBlank(fileType)) {
            fileType = Constants.FileType.QT.getCode();
        }

        if (fileType.equalsIgnoreCase(Constants.FileType.THUMBNAIL.getCode()) && file.getSize() > 5242880) {
            return new ResponseMessage(false, JSON.toJSONString(ExUtils.toMap(new AppException(5101))));
        } else if (file.getSize() > maxUploadSize) {
            return new ResponseMessage(false, JSON.toJSONString(ExUtils.toMap(new AppException(5101))));
        }
        //文件本身的类型，依据扩展名判断
        if (fileType.equalsIgnoreCase(Constants.FileType.THUMBNAIL.getCode())) {
            if (!transFileService.allowedThumbnailType(FileUtils.getExt(file.getOriginalFilename()))) {
                return new ResponseMessage(false, JSON.toJSONString(ExUtils.toMap(new AppException(5102))));
            }
        } else {
            if (!transFileService.allowedFileType(FileUtils.getExt(file.getOriginalFilename()))) {
                return new ResponseMessage(false, JSON.toJSONString(ExUtils.toMap(new AppException(5102))));
            }
        }
        TransFile transFile = null;
        //生成不同分辨率缩略图
        if (fileType.equalsIgnoreCase(Constants.FileType.THUMBNAIL.getCode())) {
            generateThumbnails(file, fileKey, fileType);
        }
        transFile = new TransFile();
        transFile.setFileKey(fileKey);
        transFile.setFileName(new String(file.getOriginalFilename().getBytes()));
        transFile.setCreateAt(Calendar.getInstance().getTime());
        transFile.setFileSize(file.getSize());
        transFile.setFileType(fileType);
        transFile = transFileService.saveStream(transFile, file.getInputStream());

        return new ResponseMessage(true, transFile);
    }

    private void generateThumbnails(MultipartFile file, String fileKey, String fileType) throws Exception {
        String baseDir = servletContext.getRealPath("/");
        File tmpDir = new File(baseDir + "/tmp");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        TransFile transFile = null;
        String thumbnailResolutions = "280_185,402_320";
        String[] resolutions = thumbnailResolutions.split(",");
        String fileExt = FileUtils.getExt(file.getOriginalFilename());
        for (int i = 0; i < resolutions.length; i++) {
            transFile = new TransFile();
            transFile.setFileKey(fileKey);
            transFile.setFileName(new String(file.getOriginalFilename().getBytes()));
            transFile.setCreateAt(Calendar.getInstance().getTime());
            transFile.setFileType(fileType);
            transFile.setResolution(resolutions[i]);
            int width = Integer.valueOf(resolutions[i].split("_")[0]);
            int height = Integer.valueOf(resolutions[i].split("_")[1]);
            File tmpThumbnailFile = new File(tmpDir, UUID.hex32() + "." + fileExt);
            try {
                Thumbnails.of(file.getInputStream()).size(width, height).toFile(tmpThumbnailFile);
                transFile.setFileSize(tmpThumbnailFile.getTotalSpace());
            } finally {
                if (tmpThumbnailFile.exists()) {
                    tmpThumbnailFile.delete();
                }
            }
        }
    }

    @RequestMapping("/getTransFilePageByKey")
    public Page<TransFile> getTransFilePageByKey(@RequestParam(value = "fileKey", required = false) String fileKey, @RequestParam(value = "title", required = false) String title, @RequestBody Pageable request) {
        Page<TransFile> transFilePage = transFileService.getTransFilePageByKey(fileKey, title, request);
        if (transFilePage.getItems().size() > 1) {
            transFilePage = transFileService.dealExt(transFilePage);
        }
        return transFilePage;
    }

    @RequestMapping("/getTransFile")
    public TransFile getTransFile(@RequestParam(value = "fileId", required = false) String fileId) {
        return transFileService.getTransFile(fileId);
    }


    @RequestMapping("/saveTransFile")
    public ResponseMessage<TransFile> saveTransFile(@RequestBody TransFile file) throws Exception {
        return transFileService.saveTransFile(file);
    }

    @RequestMapping("/deleteThumbnailsByKey")
    public void deleteThumbnailsByKey(@RequestParam(value = "fileKeys", required = false) Collection<String> fileKeys) {
        transFileService.deleteThumbnailsByKey(fileKeys);
    }

    @RequestMapping("/deleteFile")
    public void deleteFile(@RequestBody TransFile file) {
        transFileService.deleteFile(file);
    }

    @RequestMapping("/getFile")
    public File getFile(@RequestBody TransFile file) throws FileNotFoundException {
        return transFileService.getFile(file);
    }

    /**
     * 据外部key获取除缩略图之外所有文件对象列表
     *
     * @param fileKey
     * @return
     */
    @CrossOrigin
    @RequestMapping("/getTransFileAttachments")
    public List<TransFile> getTransFileAttachments(@RequestParam(value = "fileKeys") String fileKey) {
        return transFileService.getTransFileAttachments(fileKey);
    }

    /**
     * 据外部key及行政区代码获取除缩略图之外所有文件对象列表
     *
     * @param fileKey
     * @param regionCodes
     * @param pageable
     * @return
     */
    @RequestMapping("/getTransFileByFileKeysAndRegionCode")
    public Page<TransFile> getTransFileByFileKeysAndRegionCode(@RequestParam(value = "fileKeys") String fileKey, @RequestParam(value = "regionCodes", required = false) String regionCodes, @RequestBody Pageable pageable) {
        return transFileService.getTransFileByFileKeysAndRegionCode(fileKey, regionCodes, pageable);
    }

    /**
     * 根据文件Id下载文件
     *
     * @param fileId
     * @return
     * @throws FileNotFoundException
     */
    @CrossOrigin
    @RequestMapping(value = "/get")
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "fileId", required = true) String fileId) throws FileNotFoundException {
        //获取文件对象
        TransFile transFile = transFileService.getTransFile(fileId);
        try {
            //获取文件的存放地址
            File targetFile = transFileService.getFile(transFile);
            if (targetFile == null) {
                throw new FileNotFoundException();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", new String(transFile.getFileName().getBytes("GBK"), "iso8859-1"));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile),
                    headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new FileNotFoundException();
    }

    /**
     * 删除附件
     *
     * @param fileIds 文件Ids
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/remove")
    public ResponseMessage removeFile(@RequestParam(value = "fileIds", required = true) String fileIds) {
        List<String> fileIdList = Lists.newArrayList(fileIds.split(";"));
        for (String fileId : fileIdList) {
            TransFile transFile = transFileService.getTransFile(fileId);
            if (transFile.getFileType().equals(Constants.FileType.THUMBNAIL.getCode())) {
                transFileService.deleteThumbnailsByKey(Lists.newArrayList(transFile.getFileKey()));
            } else {
                transFileService.deleteFile(transFile);
            }
        }
        return new ResponseMessage(true, "删除成功！");
    }

}
