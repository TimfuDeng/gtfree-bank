package cn.gtmap.landsale.client.register;

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
     * 根据外部key获取文件page列表
     *
     * @param fileKey 外部key
     * @param title   查询条件
     * @param request
     * @return
     */
    @RequestMapping(value = "/transfile/getTransFilePageByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<TransFile> getTransFilePageByKey(@RequestParam(value = "fileKey", required = false) String fileKey, @RequestParam(value = "title", required = false) String title, @RequestBody Pageable request);

    /**
     * 根据fileId获取文件对象
     *
     * @param fileId 文件Id
     * @return
     */
    @RequestMapping(value = "/transfile/getTransFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    TransFile getTransFile(@RequestParam(value = "fileId", required = false) String fileId);

    /**
     * 存储文件对象，但没有文件本身
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/transfile/saveTransFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseMessage<TransFile> saveTransFile(@RequestBody TransFile file) throws Exception;

    /**
     * 根据文件key，批量删除缩略图
     *
     * @param fileKeys
     */
    @RequestMapping(value = "/transfile/deleteThumbnailsByKey", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteThumbnailsByKey(@RequestParam(value = "fileKeys", required = false) Collection<String> fileKeys);

    /**
     * 删除文件对象
     *
     * @param file
     */
    @RequestMapping(value = "/transfile/deleteFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteFile(@RequestBody TransFile file);

    /**
     * 根据storePath删除文件
     *
     * @param file
     * @return
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

    /**
     * 据外部key及行政区代码获取除缩略图之外所有文件对象列表
     * @param fileKey
     * @param regionCodes
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/transfile/getTransFileByFileKeysAndRegionCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Page<TransFile> getTransFileByFileKeysAndRegionCode(@RequestParam(value = "fileKeys") String fileKey, @RequestParam(value = "regionCodes",required = false) String regionCodes,@RequestBody Pageable pageable);








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
