package cn.gtmap.landsale.admin.register;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFile;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

/**
 * Created by M150237 on 2017-10-25.
 */
@FeignClient(name = "core-server")
public interface TransFileClient {

    /**
     * 根据文件类型获取
     * @param fileKey 文件类型
     * @param title 标题
     * @param request
     * @return
     */
    @RequestMapping(value = "/transfile/getTransFilePageByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransFile> getTransFilePageByKey(@RequestParam(value = "fileKey", required = false) String fileKey, @RequestParam(value = "title", required = false) String title, @RequestBody Pageable request);

    /**
     * 根据文件id获取
     * @param fileId
     * @return
     */
    @RequestMapping(value = "/transfile/getTransFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransFile getTransFile(@RequestParam(value = "fileId", required = false) String fileId);

    /**
     *  保存文件
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/transfile/saveTransFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransFile> saveTransFile(@RequestBody TransFile file) throws Exception;

    /**
     *  根据类型删除缩略图
     * @param fileKeys 文件类型
     */
    @RequestMapping(value = "/transfile/deleteThumbnailsByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteThumbnailsByKey(@RequestParam(value = "fileKeys", required = false) Collection<String> fileKeys);

    /**
     * 删除文件
     * @param file
     */
    @RequestMapping(value = "/transfile/deleteFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteFile(@RequestBody TransFile file);

    /**
     *  获取文件
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/transfile/getFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    File getFile(@RequestBody TransFile file) throws FileNotFoundException;

    /**
     * 据外部key获取除缩略图之外所有文件对象列表
     * @param fileKey
     * @return
     */
    @RequestMapping(value = "/transfile/getTransFileAttachments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TransFile> getTransFileAttachments(@RequestParam(value = "fileKeys") String fileKey);









//    @RequestMapping(value = "/transfile/getTransFileByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    List<TransFile> getTransFileByKey(@RequestParam(value = "fileKey", required = false) String fileKey);
//
//    @RequestMapping(value = "/transfile/getTransFileByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    List<TransFile> getTransFileByKey(@RequestParam(value = "fileKey", required = false) String fileKey, @RequestParam(value = "fileType", required = false) String fileType);
//
//
//    @RequestMapping(value = "/transfile/getTransFileAttachments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    List<TransFile> getTransFileAttachments(@RequestParam(value = "fileKey", required = false) String fileKey);
//
//    @RequestMapping(value = "/transfile/getTransFileThumbnails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    List<TransFile> getTransFileThumbnails(@RequestParam(value = "fileKey", required = false) String fileKey, @RequestParam(value = "resolutions", required = false) String resolutions) throws Exception;
//
////    @RequestMapping(value = "/transfile/getFileOutputStream", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
////    OutputStream getFileOutputStream(@RequestBody TransFile file) throws FileNotFoundException;
//
//
//    @RequestMapping(value = "/transfile/getFileInputStream", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    InputStream getFileInputStream(@RequestBody TransFile file) throws FileNotFoundException;
//
//    @RequestMapping(value = "/transfile/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    TransFile save(@RequestBody TransFile file) throws Exception;
//
//
//
//    @RequestMapping(value = "/transfile/deleteFiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    void deleteFiles(@RequestParam(value = "fileIds", required = false) Collection<String> fileIds);
//
//
//    @RequestMapping(value = "/transfile/deleteFilesByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    void deleteFilesByKey(@RequestParam(value = "fileKeys", required = false) Collection<String> fileKeys);
//
//
//    @RequestMapping(value = "/transfile/allowedFileType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    boolean allowedFileType(@RequestParam(value = "fileType", required = false) String fileType);
//
//    @RequestMapping(value = "/transfile/allowedThumbnailType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    boolean allowedThumbnailType(@RequestParam(value = "fileType", required = false) String fileType);
//
//    @RequestMapping(value = "/transfile/dealExt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    Page<TransFile> dealExt(@RequestParam(value = "transFilePage", required = false) Page<TransFile> transFilePage);


}
