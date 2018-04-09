package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransFile;
import cn.gtmap.landsale.service.MaterialCenterService;
import cn.gtmap.landsale.service.RegionService;
import cn.gtmap.landsale.service.TransFileService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/7/28
 */
@Controller
@RequestMapping(value = "/material")
public class MaterialCenterController {


    @Autowired
    MaterialCenterService materialCenterService;

    @Autowired
    ServletContext servletContext;

    @Autowired
    RegionService regionService;

    @Autowired
    TransFileService transFileService;

    @RequestMapping("")
    public String materialList(@PageDefault(value = 10) Pageable page,String title,String regionCode,Model model){
//        Map<String,String> materials = materialCenterService.getMaterials();
//        model.addAttribute("materials",materials);
        Page<TransFile> transFilePage=null;
        Set<String> regions = Sets.newHashSet();
        regions.add(regionCode);
        transFilePage= transFileService.getFilePageByKeyByRegionCode(Constants.FileKey, title, page, regions);
        transFilePage=transFileService.dealExt(transFilePage);
        model.addAttribute("transFilePage",transFilePage);
        model.addAttribute("title",title);
        model.addAttribute("regionCode",regionCode);
        return "material";
    }

    /**
     * 根据文件Id下载文件
     * @return
     */
    @RequestMapping("get")
    public ResponseEntity<byte[]> getFile(@RequestParam(value = "fileId", required = true) String fileId,HttpServletResponse response) throws Exception {
        String regionTag = regionService.getDefaultRegionCode();
        String basePath = servletContext.getRealPath("/")+"/WEB-INF/views/material/";
        String path = basePath+regionTag;
        File file = FileUtils.getFile(new File(path),fileId);
        if(!file.exists())
            file = FileUtils.getFile(new File(basePath+"default"), fileId);
        if (file.exists()) {
            byte[] fileBytes = FileUtils.readFileToByteArray(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("GBK"), "iso8859-1"));
            return new ResponseEntity<byte[]>(fileBytes,
                    headers, HttpStatus.OK);
        }
        throw new FileNotFoundException();
    }

    /**
     * 根据文件Id下载文件
     * @param fileId
     * @return
     * @throws FileNotFoundException
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
}
