package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.util.ExUtils;
import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.egovplat.core.util.UUID;
import cn.gtmap.egovplat.core.web.BaseApiController;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.service.MimeTypeService;
import cn.gtmap.landsale.service.TransFileService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 文件管理
 * Created by jiff on 14-7-3.
 */
@Controller
public class FileController{
    @Autowired
    TransFileService transFileService;

    @Autowired
    MimeTypeService mimeTypeService;

    @Value("${file.maxUploadSize}")
    long maxUploadSize;

    @Value("${thumbnail.resolutions}")
    String thumbnailResolutions;

    @Autowired
    ServletContext servletContext;


    /**
     * 上传附件
     * @param file 文件对象
     * @param fileKey 文件的key
     * @param fileType 文件类型
     * @return
     * @throws Exception
     */
    @RequestMapping("/file/upload.f")
    public void uploadFile(@RequestParam MultipartFile file,HttpServletResponse response,@RequestParam(value = "fileKey", required = true) String fileKey,String fileType) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        if(StringUtils.isBlank(fileType))
            fileType = Constants.FileType.QT.getCode();

        if(fileType.equalsIgnoreCase(Constants.FileType.THUMBNAIL.getCode())&&file.getSize()>5242880) {
            response.getWriter().print(JSON.toJSONString(ExUtils.toMap(new AppException(5101))));
            return;
        }else if(file.getSize()>maxUploadSize){
            response.getWriter().print(JSON.toJSONString(ExUtils.toMap(new AppException(5101))));
            return;
        }


        //文件本身的类型，依据扩展名判断
        if(fileType.equalsIgnoreCase(Constants.FileType.THUMBNAIL.getCode())){
            if(!transFileService.allowedThumbnailType(FileUtils.getExt(file.getOriginalFilename()))){
                response.getWriter().print(JSON.toJSONString(ExUtils.toMap(new AppException(5102))));
                return;
            }
        }else {
            if(!transFileService.allowedFileType(FileUtils.getExt(file.getOriginalFilename()))) {
                response.getWriter().print(JSON.toJSONString(ExUtils.toMap(new AppException(5102))));
                return;
            }
        }

        TransFile transFile = null;
        //生成不同分辨率缩略图
        if(fileType.equalsIgnoreCase(Constants.FileType.THUMBNAIL.getCode())){
            generateThumbnails(file,fileKey,fileType);
        }
        transFile = new TransFile();
        transFile.setFileKey(fileKey);
        transFile.setFileName(new String(file.getOriginalFilename().getBytes()));
        transFile.setCreateAt(Calendar.getInstance().getTime());
        transFile.setFileSize(file.getSize());
        transFile.setFileType(fileType);
        transFile=transFileService.save(transFile,file.getInputStream());

        response.getWriter().write(JSON.toJSONString(transFile));
    }


    private void generateThumbnails(MultipartFile file,String fileKey,String fileType) throws Exception {
        String baseDir = servletContext.getRealPath("/");
        File tmpDir = new File(baseDir+"/tmp");
        if(!tmpDir.exists())
            tmpDir.mkdir();
        TransFile transFile = null;
        String[] resolutions = thumbnailResolutions.split(",");
        String fileExt = FileUtils.getExt(file.getOriginalFilename());
        for(int i = 0;i<resolutions.length;i++){
            transFile = new TransFile();
            transFile.setFileKey(fileKey);
            transFile.setFileName(new String(file.getOriginalFilename().getBytes()));
            transFile.setCreateAt(Calendar.getInstance().getTime());
            transFile.setFileType(fileType);
            transFile.setResolution(resolutions[i]);
            int width = Integer.valueOf(resolutions[i].split("_")[0]);
            int height = Integer.valueOf(resolutions[i].split("_")[1]);
            File tmpThumbnailFile = new File(tmpDir, UUID.hex32()+"."+fileExt);
            try {
                Thumbnails.of(file.getInputStream()).size(width, height).toFile(tmpThumbnailFile);
                transFile.setFileSize(tmpThumbnailFile.getTotalSpace());
                transFileService.save(transFile, new FileInputStream(tmpThumbnailFile));
            }finally {
                if(tmpThumbnailFile.exists())
                    tmpThumbnailFile.delete();
            }
        }
    }

    /**
     * 所有文件列表
     * @param fileKey 文件的key
     * @param model
     * @param fileType 文件类型
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping("/file/list.f")
    public @ResponseBody List<TransFile> list(String fileKey,String fileType,Model model) throws IOException {
        return fileType!=null?transFileService.getTransFileByKey(fileKey,fileType):transFileService.getTransFileByKey(fileKey);
    }

    /**
     * 除了缩略图之外所有的附件
     * @param fileKey
     * @param model
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping("/file/attachments.f")
    public @ResponseBody List<TransFile> attachments(String fileKey,Model model) throws IOException {
        return transFileService.getTransFileAttachments(fileKey);
    }

    @RequestMapping("/file/thumbnails.f")
    public @ResponseBody List<TransFile> thumbnails(String fileKey,String resolution,Model model) throws Exception {
        return transFileService.getTransFileThumbnails(fileKey, resolution);
    }

    /**
     * 在线浏览，比如浏览图片
     * @param fileId 文件Id
     * @return
     * @throws Exception
     */
    @RequestMapping("/file/view.f")
    public ResponseEntity<byte[]> viewImage(String fileId) throws Exception {
        TransFile transFile = transFileService.getTransFile(fileId);
        if (transFile != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeTypeService.getMimeType(transFile.getFileName())));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(transFileService.getFile(transFile)),
                    headers, HttpStatus.OK);
        }
        throw new FileNotFoundException();
    }

    /**
     * 删除附件
     * @param fileIds 文件Ids
     * @return
     */
    @RequestMapping("/file/remove.f")
    @ResponseBody
    public String removeFile(@RequestParam(value = "fileIds", required = true) String fileIds){
        List<String> fileIdList = Lists.newArrayList(fileIds.split(";"));
        for(String fileId:fileIdList){
            TransFile transFile = transFileService.getTransFile(fileId);
            if(transFile.getFileType().equals(Constants.FileType.THUMBNAIL.getCode())){
                transFileService.deleteThumbnailsByKey(Lists.newArrayList(transFile.getFileKey()));
            }else
                transFileService.deleteFile(transFile);
        }
        return "true";
    }


    /**
     * 根据文件Id下载文件
     * @param fileId
     * @return
     * @throws java.io.FileNotFoundException
     */
    @RequestMapping("/file/get")
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "fileId", required = true) String fileId) throws FileNotFoundException {
        TransFile transFile = transFileService.getTransFile(fileId);
        try {
            File targetFile = transFileService.getFile(transFile);
            if(targetFile==null)
                throw new FileNotFoundException();
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

    @RequestMapping(value = "/console/file/list")
    public String list(@PageDefault(value = 15) Pageable page,String title,Model model){
        Page<TransFile> transFilePage=null;

        transFilePage= transFileService.getTransFilePageByKey(Constants.FileKey,title,page);
        transFilePage=transFileService.dealExt(transFilePage);
        model.addAttribute("transFilePage",transFilePage);
        model.addAttribute("title",title);

        return "file-list";
    }

    @RequestMapping(value = "/console/file/edit")
    public String transFile( String fileId,Model model){
        TransFile transFile=null;
        if(StringUtils.isNotBlank(fileId)){
            transFile= transFileService.getTransFile(fileId);
        }else{
            transFile=new TransFile();
            transFile.setCreateAt(new Date());
        }
        model.addAttribute("transFile",transFile);
        return "file-edit";
    }


    /**
     * 资料保存
     * @param transFile
     * @param ra
     * @param model
     * @return
     */
    @RequestMapping(value = "/console/file/save")
    public String save (TransFile transFile,RedirectAttributes ra,Model model){
        try {
            TransFile file=new TransFile();
            file=transFileService.dealFile(file,transFile);
            transFileService.saveTransFile(file);
            ra.addFlashAttribute("_result",true);
            ra.addFlashAttribute("_msg","保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("_msg","保存失败");

        }
        return "redirect:/console/file/list";

    }





}


