package cn.gtmap.landsale.view.web.console;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.service.ClientFileService;
import cn.gtmap.landsale.service.MimeTypeService;
import cn.gtmap.landsale.service.TransFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 文件
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/19
 */
@Controller
@RequestMapping(value = "/file")
public class FileController{

    @Autowired
    TransFileService transFileService;
    @Autowired
    ClientFileService fileService;
    @Autowired
    MimeTypeService mimeTypeService;

    /**
     * 文件列表
     * @param fileKey 文件的key
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("list.f")
    public @ResponseBody
    List<TransFile> list(String fileKey, Model model) throws IOException {
        return transFileService.getTransFileByKey(fileKey);
    }

    @RequestMapping("view.f")
    public ResponseEntity<byte[]> viewFile(String fileId,HttpServletRequest request) throws Exception {
        TransFile transFile = transFileService.getTransFile(fileId);
        if(transFile!=null){
            byte[] file = fileService.viewFile(fileId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeTypeService.getMimeType(transFile.getFileName())));
            headers.setCacheControl(Constants.CLIENT_THUMBNAIL_CACHE_CONTROL);
            headers.setLastModified(transFile.getCreateAt().getTime());
            long ifModifiedSince = -1;
            ifModifiedSince = request.getDateHeader("If-Modified-Since");
            boolean notModified = (ifModifiedSince >= (transFile.getCreateAt().getTime() / 1000 * 1000));
            if(notModified){
                return new ResponseEntity<byte[]>(null,
                        headers, HttpStatus.NOT_MODIFIED);
            }else{
                return new ResponseEntity<byte[]>(file,
                        headers, HttpStatus.OK);
            }
        }
        throw new FileNotFoundException();
    }

    /**
     * 根据文件Id下载文件
     * @param fileId
     * @return
     */
    @RequestMapping("get")
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "fileId", required = true) String fileId,HttpServletResponse response) throws Exception {
        TransFile transFile = transFileService.getTransFile(fileId);
        if (transFile != null) {
            byte[] file = fileService.viewFile(fileId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", new String(transFile.getFileName().getBytes("GBK"), "iso8859-1"));
            return new ResponseEntity<byte[]>(file,
                    headers, HttpStatus.OK);
        }
        throw new FileNotFoundException();
    }

    /**
     * 除了缩略图之外所有的附件
     * @param fileKey
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("attachments.f")
    public @ResponseBody List<TransFile> attachments(String fileKey,Model model) throws IOException {
        return transFileService.getTransFileAttachments(fileKey);
    }

}
