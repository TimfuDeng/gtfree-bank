package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.ResourceUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by u on 2015/9/28.
 */
@Controller
@RequestMapping(value="console/verify")
public class TransResourceVerifyController extends BaseController {
    @Autowired
    TransResourceService transResourceService;
    @Autowired
    TransResourceInfoService transResourceInfoService;
    @Autowired
    RegionService regionService;
    @Autowired
    TransVerifyService transVerifyService;
    @Autowired
    TransUserService transUserService;
    @Autowired
    TransResourceApplyService transResourceApplyService;
    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;
    @Autowired
    AttachmentCategoryService attachmentCategoryService;
    @Autowired
    TransFileService transFileService;
    @Autowired
    TransUserService userService;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    TransMaterialService transMaterialService;
    private static final int VERIFY_APPROVAL = 1;//审核通过


    @ModelAttribute("resource")
    public TransResource getResource(@RequestParam(value = "resourceId", required = false) String resourceId) {
        return StringUtils.isBlank(resourceId) ? new TransResource() : transResourceService.getTransResource(resourceId);
    }

    @ModelAttribute("resourceInfo")
    public TransResourceInfo getResourceInfo(@RequestParam(value = "resourceId", required = false) String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
            return new TransResourceInfo();
        } else {
            TransResourceInfo transResourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
            return transResourceInfo == null ? new TransResourceInfo() : transResourceInfo;
        }
    }

    @RequestMapping("list")
    public String list(@PageDefault(value = 5) Pageable page, String title, String status, String dealStatus, String ggId, Model model) {
        int resourceEditStatus = -1;
        int resourceStatus = 30;//默认查已成交的地块
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String verifyTime = date.format(new Date());

        if (StringUtils.isNotBlank(status))
            resourceEditStatus = Integer.parseInt(status);
        if (StringUtils.isNotBlank(dealStatus))
            resourceStatus = Integer.parseInt(dealStatus);
        Set<String> regions = Sets.newHashSet();
        if (!SecUtil.isAdmin())
            regions = SecUtil.getPermittedRegions();

        Page<TransResource> transResourcePage = transResourceService.findDealTransResourcesByEditStatus(title, resourceEditStatus, resourceStatus, ggId, regions, page);

        model.addAttribute("transResourcePage", transResourcePage);
        model.addAttribute("title", title);
        model.addAttribute("ggId", ggId);
        model.addAttribute("status", resourceEditStatus);
        model.addAttribute("resourceStatus", dealStatus);
        model.addAttribute("verifyTime", verifyTime);
        return "verify";
    }


    @RequestMapping(value = "save")
    public String save(TransResourceVerify transResourceVerify, Model model, String resourceId) {
        TransResource transResource = transResourceService.getTransResource(resourceId);
        if (transResourceVerify != null) {
            transVerifyService.saveTransResourceVerify(transResource, transResourceVerify);
        }
        model.addAttribute("transResource", transResource);
        return "redirect:/console/verify/list?status=9&dealStatus=30";
    }

    @RequestMapping(value = "view")
    public String show(String resourceId, Model model) {
        TransResourceInfo transResourceInfo = null;
        TransResource transResource = null;
        TransResourceVerify transResourceVerify = null;
        TransUser user = new TransUser();
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
            transResourceInfo = transResourceInfoService.getTransResourceInfoByResourceId(resourceId);
        } else {
            transResource = ResourceUtil.buildNewResource();
            List<String[]> regionList = regionService.findAllRegions();
            transResource.setRegionCode(regionList.get(0)[0]);
        }
        if (transResourceInfo == null) {
            transResourceInfo = new TransResourceInfo();
        }
        if (transResource.getResourceVerifyId() != null)
            transResourceVerify = transVerifyService.getTransVerifyById(transResource.getResourceVerifyId());
        if (transResourceVerify != null) {
            user = transUserService.getTransUser(transResourceVerify.getAuditor());
        }
        model.addAttribute("transResourceInfo", transResourceInfo);
        model.addAttribute("regionAllList", regionService.findAllRegions());
        model.addAttribute("transResource", transResource);
        model.addAttribute("transResourceVerify", transResourceVerify);
        model.addAttribute("auditor", user.getViewName());
        return "verify-detail";
    }

    @RequestMapping(value = "status/view.f")
    public String view(String resourceId, Model model) {
        TransResource transResource = null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
        }
        model.addAttribute("transResource", transResource);
        return "common/verify-status";
    }

    @RequestMapping(value = "statusBar/view.f")
    public String viewStatus(String resourceId, Model model) {
        TransResource transResource = null;
        if (StringUtils.isNotBlank(resourceId)) {
            transResource = transResourceService.getTransResource(resourceId);
        }
        model.addAttribute("transResource", transResource);
        return "verify-status";
    }

    @RequestMapping("attachment")
    public String attachments(@RequestParam(value = "resourceId", required = true) String resourceId, @RequestParam(value = "userId", required = true) String userId, Model model) {
        TransResourceApply transResourceApply =
                transResourceApplyService.getTransResourceApplyByUserId(userId, resourceId);
        TransUser transUser = transUserService.getTransUser(userId);
        TransUserApplyInfo transUserApplyInfo = transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
        List<TransMaterial> transMaterials = transMaterialService.getMaterialsByRegionCode(transResourceService.getTransResource(resourceId).getRegionCode());
        Map<String,String> map = Maps.newHashMap();
        for(TransMaterial transMaterial:transMaterials){
            map.put(transMaterial.getMaterialCode(),transMaterial.getMaterialName());
        }
        model.addAttribute("applyId", transResourceApply.getApplyId());
        model.addAttribute("transUserApplyInfo", transUserApplyInfo);
        model.addAttribute("transUser", transUser);
        model.addAttribute("resourceId", resourceId);
        model.addAttribute("attachmentTypeList", map);
        return "verify-apply-attachments";
    }


    @RequestMapping("list/resourceApply")
    public String resourceApplyQualified(String resourceId, Model model, @PageDefault(value = 4) Pageable page,String userName) {
        Page<TransResourceApply> transResourceApplyPage =
                transResourceApplyService.getTransResourceApplyByresourceId(resourceId, page,userName);
//        List<TransResourceApply> transResourceApplyList=transResourceApplyService.getTransResourceApplyByResourceId(resourceId);
        if (transResourceApplyPage.getItems().size() > 0) {
            for (TransResourceApply transResourceApply : transResourceApplyPage.getItems()) {
                if (StringUtils.isNotBlank(transResourceApply.getInfoId())) {
                    TransUserApplyInfo transUserApplyInfo = transUserApplyInfoService.getTransUserApplyInfo(transResourceApply.getInfoId());
                    transResourceApply.setTransUserApplyInfo(transUserApplyInfo);
                }
            }
        }
        model.addAttribute("transResourceApplyPage", transResourceApplyPage);
        model.addAttribute("resourceId", resourceId);
        return "verify-bm-attachment";
    }

    @RequestMapping("/file/zip")
    public void getZip(String userId, String fileKey, HttpServletResponse response) {
        List<TransFile> attachments = transFileService.getTransFileByKey(fileKey);
        String fileName = userService.getTransUser(userId).getViewName()+"附件材料.zip";
        File file = new File("c:/target.rar");
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fous = new FileOutputStream(file);
            /**打包的方法我们会用到ZipOutputStream这样一个输出流,
             * 所以这里我们把输出流转换一下*/
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            /**这个方法接受的就是一个所要打包文件的集合，
             * 还有一个ZipOutputStream*/
            zipFiles(attachments,zipOut);
            zipOut.close();
            fous.close();
            response.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
         downloadZip(file,response,fileName);
    }


    /**
     * 压缩文件
     * @param files
     * @param ouputStream
     */
    public void zipFiles(List<TransFile> files,ZipOutputStream ouputStream){
        try {
            for (TransFile file : files) {
                List<String> fielName = Lists.newArrayList(file.getFileName().split("\\."));
                ZipEntry entry = null;
                if (fielName.size()>0){
                    entry = new ZipEntry(fielName.get(0)+ Calendar.getInstance().getTime().getTime()+"."+fielName.get(1));
                }else {
                    entry = new ZipEntry(file.getFileName());
                }
                //ZipEntry entry = new ZipEntry(file.getFileName());
                File targetFile = transFileService.getFile(file);
                if (targetFile == null) {
                    throw new FileNotFoundException();
                }
                FileInputStream in = new FileInputStream(targetFile);
                BufferedInputStream bins = new BufferedInputStream(in,512);

                ouputStream.putNextEntry(entry);
                // 向压缩文件中输出数据
                int nNumber;
                byte[] buffer = new byte[512];
                while ((nNumber = bins.read(buffer)) != -1) {
                    ouputStream.write(buffer, 0, nNumber);
                }
                bins.close();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载压缩包
     * @param file
     * @param response
     * @return
     */
    public void downloadZip(File file,HttpServletResponse response,String fileName) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" +new String(fileName.getBytes("GBK"), "iso8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        return response;
    }

    @RequestMapping(value = "/excel.f")
    public String export(String applyId,HttpServletResponse response,Model model){
        try {
            TransResourceApply transResourceApply =  transResourceApplyService.getTransResourceApply(applyId);
            String infoId = transResourceApply.getInfoId();
            TransUser transUser = transUserService.getTransUser(transResourceApply.getUserId());
            TransUserApplyInfo userApplyInfo =  transUserApplyInfoService.getTransUserApplyInfo(infoId);
            String fileName = transUser.getViewName()+"联系信息.xls";
            model.addAttribute("userApplyInfo", userApplyInfo);
            model.addAttribute("transUser", transUser);
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("/views/verify-excel.ftl");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "iso8859-1"));
            response.setCharacterEncoding("utf-8");
            template.process(model, response.getWriter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("detail")
    public String verifyDetail(String resourceId,Model model){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String verifyTime = date.format(new Date());
        model.addAttribute("verifyTime",verifyTime);
        model.addAttribute("resourceId",resourceId);
        return "verify-resource";
    }


}

