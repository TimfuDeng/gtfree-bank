package cn.gtmap.landsale.admin.web;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.landsale.admin.register.TransFileClient;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFile;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
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

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by M150237 on 2017-10-25.
 */
@Controller
@RequestMapping("/transfile")
public class TransFileController {
    @Autowired
    TransFileClient transFileClient;

    @Autowired
    ServletContext servletContext;

    @RequestMapping("/index")
    public String index(@PageDefault(value = 10) Pageable page, String title, Model model) {
        Page<TransFile> transFilePage = transFileClient.getTransFilePageByKey(Constants.FILE_KEY, title, page);
        model.addAttribute("transFilePage", transFilePage);
        model.addAttribute("title", title);
        return "file/file-list";
    }

    @RequestMapping("/edit")
    public String transFile(String fileId, Model model) {
        TransFile transFile = null;
        if (StringUtils.isNotBlank(fileId)) {
            transFile = transFileClient.getTransFile(fileId);
        } else {
            transFile = new TransFile();
            transFile.setCreateAt(new Date());
        }
        model.addAttribute("transFile", transFile);
        return "file/file-edit";
    }


    /**
     * 资料保存
     * @param transFile
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseMessage<TransFile> save(TransFile transFile) throws Exception {
            TransFile file = new TransFile();
            if(StringUtils.isBlank(transFile.getFileId())){//没上传文件
                transFile.setFileId(null);
                file=transFile;
                file.setFileKey("ziliao");
            }else{//上传了文件
                String fileId=transFile.getFileId();
                file= transFileClient.getTransFile(fileId);
                String fileName=file.getFileName();
                String  suffixFile="."+FileUtils.getExt(fileName);
                file.setFileName(transFile.getFileName()+suffixFile);
                file.setDescription(transFile.getDescription());
                file.setCreateAt(transFile.getCreateAt());
                file.setFileNo(transFile.getFileNo());
                file.setFileAuthor(SecUtil.getLoginUserViewName());
                file.setRegionCode(transFile.getRegionCode());
            }
        return transFileClient.saveTransFile(file);

    }


    /**
     * 删除附件
     *
     * @param fileIds 文件Ids
     * @return
     */
    @RequestMapping(value = "remove")
    @ResponseBody
    public ResponseMessage removeFile(@RequestParam(value = "fileIds", required = true) String fileIds) {
        List<String> fileIdList = Lists.newArrayList(fileIds.split(";"));
        for (String fileId : fileIdList) {
            TransFile transFile = transFileClient.getTransFile(fileId);
            if (transFile.getFileType().equals(Constants.FileType.THUMBNAIL.getCode())) {
                transFileClient.deleteThumbnailsByKey(Lists.newArrayList(transFile.getFileKey()));
            } else {
                transFileClient.deleteFile(transFile);
            }
        }
        return new ResponseMessage(true,"删除成功！");
    }


    /**
     * 根据文件Id下载文件
     *
     * @param fileId
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping("/get")
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "fileId", required = true) String fileId) throws FileNotFoundException {
        //获取文件对象
        TransFile transFile = transFileClient.getTransFile(fileId);
        try {
            //获取文件的存放地址
            File targetFile = transFileClient.getFile(transFile);
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
     * 除了缩略图之外所有的附件
     * @param fileKey
     * @return
     * @throws IOException
     */
    @RequestMapping("/attachments")
    @ResponseBody
    public List<TransFile> attachments(String fileKey) throws IOException {
        return transFileClient.getTransFileAttachments(fileKey);
    }

}
