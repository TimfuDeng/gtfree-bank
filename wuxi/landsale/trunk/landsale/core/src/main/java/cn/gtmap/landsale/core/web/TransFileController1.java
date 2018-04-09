//package cn.gtmap.landsale.core.web;
//
//import cn.gtmap.egovplat.core.util.FileUtils;
//import cn.gtmap.landsale.common.Constants;
//import cn.gtmap.landsale.common.model.TransFile;
//import cn.gtmap.landsale.core.service.TransFileService;
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.ServletContext;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by M150237 on 2017-10-25.
// */
//@Controller
//@RequestMapping("/transfile")
//public class TransFileController {
//    @Autowired
//    TransFileService transFileService;
//
////    @Value("${file.maxUploadSize}")
////    long maxUploadSize;
//
////    @Value("${thumbnail.resolutions}")
////    String thumbnailResolutions;
//
////    @Autowired
////    MimeTypeService mimeTypeService;
//
//    @Autowired
//    ServletContext servletContext;
//
//
//
//
//
//
//
//
//    /**
//     * 所有文件列表
//     * @param fileKey 文件的key
//     * @param model
//     * @param fileType 文件类型
//     * @return
//     * @throws java.io.IOException
//     */
////    @RequestMapping("/list")
////    public String list(String fileKey, String fileType, Model model) {
////        List<TransFile> transFileList = fileType!=null?transFileService.getTransFileByKey(fileKey,fileType):transFileService.getTransFileByKey(fileKey);
////        model.addAttribute("transFileList", transFileList);
////        return "file/file-list";
////    }
//
//    /**
//     * 除了缩略图之外所有的附件
//     *
//     * @param fileKey
//     * @param model
//     * @return
//     * @throws java.io.IOException
//     */
//    @RequestMapping("/attachments.f")
//    public
//    @ResponseBody
//    List<TransFile> attachments(String fileKey, Model model) throws IOException {
//        return transFileService.getTransFileAttachments(fileKey);
//    }
//
//    @RequestMapping("/thumbnails.f")
//    public
//    @ResponseBody
//    List<TransFile> thumbnails(String fileKey, String resolution, Model model) throws Exception {
//        return transFileService.getTransFileThumbnails(fileKey, resolution);
//    }
//
//    /**
//     * 在线浏览，比如浏览图片
//     *
//     * @param fileId 文件Id
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/view.f")
//    public ResponseEntity<byte[]> viewImage(String fileId) throws Exception {
//        TransFile transFile = transFileService.getTransFile(fileId);
//        if (transFile != null) {
//            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.parseMediaType(mimeTypeService.getMimeType(transFile.getFileName())));
//            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(transFileService.getFile(transFile)),
//                    headers, HttpStatus.OK);
//        }
//        throw new FileNotFoundException();
//    }
//
//    /**
//     * 删除附件
//     *
//     * @param fileIds 文件Ids
//     * @return
//     */
//    @RequestMapping("/remove.f")
//    @ResponseBody
//    public String removeFile(@RequestParam(value = "fileIds", required = true) String fileIds) {
//        List<String> fileIdList = Lists.newArrayList(fileIds.split(";"));
//        for (String fileId : fileIdList) {
//            TransFile transFile = transFileService.getTransFile(fileId);
//            if (transFile.getFileType().equals(Constants.FileType.THUMBNAIL.getCode())) {
//                transFileService.deleteThumbnailsByKey(Lists.newArrayList(transFile.getFileKey()));
//            } else
//                transFileService.deleteFile(transFile);
//        }
//        return "true";
//    }
//
//
//    /**
//     * 根据文件Id下载文件
//     *
//     * @param fileId
//     * @return
//     * @throws java.io.FileNotFoundException
//     */
//    @RequestMapping("/get")
//    public ResponseEntity<byte[]> getFile(@RequestParam(value = "fileId", required = true) String fileId) throws FileNotFoundException {
//        TransFile transFile = transFileService.getTransFile(fileId);
//        try {
//            File targetFile = transFileService.getFile(transFile);
//            if (targetFile == null)
//                throw new FileNotFoundException();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", new String(transFile.getFileName().getBytes("GBK"), "iso8859-1"));
//            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile),
//                    headers, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        throw new FileNotFoundException();
//    }
//
//
//    /**
//     * 资料保存
//     *
//     * @param transFile
//     * @param ra
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "save")
//    public String save(TransFile transFile, RedirectAttributes ra, Model model) {
//        try {
//            TransFile file = new TransFile();
//            file = transFileService.dealFile(file, transFile);
//            transFileService.saveTransFile(file);
//            ra.addFlashAttribute("_result", true);
//            ra.addFlashAttribute("_msg", "保存成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            ra.addFlashAttribute("_msg", "保存失败");
//
//        }
//        return "redirect:list";
//
//    }
//
//}
