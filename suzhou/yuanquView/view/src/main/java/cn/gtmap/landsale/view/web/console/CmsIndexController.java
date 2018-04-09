package cn.gtmap.landsale.view.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.util.FileUtils;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;

import com.google.common.collect.Sets;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by trr on 2015/10/14.
 */
@Controller
public class CmsIndexController {
    @Autowired
    TransCrggService transCrggService;
    @Autowired
    TransNewsService transNewsService;
    @Autowired
    TransResourceService transResourceService;
    @Autowired
    TransFileService transFileService;
    @Autowired
    TransInteractCommunionService transInteractCommunionService;
    @Autowired
    RegionService regionService;
    @Autowired
    SuspendNoticeService suspendNoticeService;
    @Autowired
    DealNoticeService dealNoticeService;
    @Autowired
    TransLawService transLawService;
    @Autowired
    TransResourceSonService transResourceSonService;


    private static final int CRGG_DEPLOYED = 1;
    private static final int CRGG_UNDEPLOYED = 0;
    /**
     * 出让公告列表
     * @param page
     * @param title
     * @param model
     * @return
     */
    @RequestMapping("/crgg/list.f")
    public String listCrggs(@PageDefault(value=10) Pageable page,String title,Model model) {
        Set<String> regions = Sets.newHashSet();

        List<CrggInfo> transCrggList = transCrggService.findCrggInfo(title,regions,page, CRGG_DEPLOYED);

        model.addAttribute("transCrggList", transCrggList);
        model.addAttribute("title", title);
        return "crgg/crgg-list";
    }

    @RequestMapping("/crgg/listAll.f")
    public String listAllCrggs(@PageDefault(value=10) Pageable page,String title,Model model) {
        Set<String> regions = Sets.newHashSet();
        Page<TransCrgg> transCrggList = transCrggService.findTransCrggByStatus(title, regions, page,CRGG_DEPLOYED);
        model.addAttribute("transCrggList", transCrggList);
        model.addAttribute("title", title);
        return "crgg/crgg-list-page";
    }

    /**
     * 出让公告内容
     * @param crggId
     * @param model
     * @return
     */
    @RequestMapping("/crgg/content.f")
     public String showCrgg(String crggId,Model model){
        TransCrgg crgg = transCrggService.getTransCrgg(crggId);
        List region = regionService.findAllRegions();
        if(region.size()>0){
            model.addAttribute("region",region.get(0));
        }
        model.addAttribute("crggAttachments", transFileService.getTransFileAttachments(crggId));
        model.addAttribute("crgg",crgg);
        return "crgg/crgg-content";
    }

    /**
     * 新闻公告列表
     * @param page
     * @param model
     * @return
     */
    @RequestMapping("/news/list.f")
    public String listNews(@PageDefault(value=10) Pageable page,Model model){
        Page<TransNews> transNewsList = transNewsService.findTransNewsDeployed(page);
        model.addAttribute("transNewsList",transNewsList);
        return "news/news-list";
    }

    @RequestMapping("/news/listAll.f")
    public String listAllNews(@PageDefault(value=10) Pageable page,Model model){
        Page<TransNews> transNewsList = transNewsService.findTransNewsDeployed(page);
        model.addAttribute("transNewsList",transNewsList);
        return "news/news-list-page";
    }

    /**
     * 新闻内容
     * @param newsId
     * @param model
     * @return
     */
    @RequestMapping("/news/content.f")
    public String showNews(String newsId,Model model){
        TransNews news = transNewsService.getTransNews(newsId);
        model.addAttribute("news",news);
        return "news/news-content";
    }

    /**
     * 文件资源列表
     * @param page
     * @param title
     * @param model
     * @return
     */
    @RequestMapping("/fileDownload/list.f")
    public String listFiles(@PageDefault(value=15) Pageable page,String title,Model model){
        Page<TransFile> transFilePage=null;

        transFilePage= transFileService.getTransFilePageByKey(Constants.FileKey,title,page);
        transFilePage=transFileService.dealExt(transFilePage);
        model.addAttribute("transFilePage",transFilePage);
        model.addAttribute("title",title);

        return "file/file-list";
    }

    /**
     * 文件下载
     * @param fileId
     * @return
     * @throws FileNotFoundException
     */
    @RequestMapping("/file/download")
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


    /**
     * 交流意见保存
     * @param transInteractCommunion
     */
    @RequestMapping("/letter/save")
    @ResponseBody
    public String saveLetter(TransInteractCommunion transInteractCommunion){
        if(StringUtils.isBlank(transInteractCommunion.getCommunionId())){
            transInteractCommunion.setCommunionId(null);

        }
        String pubNo=transInteractCommunionService.getNextPublicNo();
        transInteractCommunion.setPublicNo(pubNo);
        transInteractCommunion.setPublicTime(new Date());
        transInteractCommunionService.saveTransInteractCommunion(transInteractCommunion);
        return "true";
    }

    /**
     * 列出交流意见
     * @param page
     * @param title
     * @param model
     * @return
     */
    @RequestMapping("/letter/list.f")
    public String listLetters(@PageDefault(value=10) Pageable page,String title,Model model){
        Page<TransInteractCommunion> letterList = transInteractCommunionService.findApprovedComm(page);
        model.addAttribute("letterList",letterList);
        return "letter/letter-list";
    }

    /**
     * 交流意见内容
     * @param letterId
     * @param model
     * @return
     */
    @RequestMapping("/letter/content.f")
    public String showLetter(String letterId,Model model){
        TransInteractCommunion letter = transInteractCommunionService.getTransInteractCommunionById(letterId);
        model.addAttribute("letter",letter);
        return "letter/letter-content";
    }

    @RequestMapping("/suspendNotice/list.f")
    public String listsuspendNotice(@PageDefault(value = 10)Pageable page,Model model){
        int deployStatus = 1;//初始化状态为已发布
        Page<SuspendNotice> suspendNotices = suspendNoticeService.findAllSuspendNoticesByStatus(page, deployStatus);
        model.addAttribute("suspendNotices",suspendNotices);
        return "suspendNotice/suspend-notice-list";
    }

    @RequestMapping("/suspendNotice/listAll.f")
    public String listAllSuspendNotice(@PageDefault(value = 10)Pageable page,Model model){
        int deployStatus = 1;//初始化状态为已发布
        Page<SuspendNotice> suspendNotices = suspendNoticeService.findAllSuspendNoticesByStatus(page, deployStatus);
        model.addAttribute("suspendNotices",suspendNotices);
        return "suspendNotice/suspend-notice-list-page";
    }

    @RequestMapping("/suspendNotice/content.f")
    public String showSuspendNotice(String noticeId,Model model){
        SuspendNotice suspendNotice = suspendNoticeService.getNotice(noticeId);
        model.addAttribute("suspendNotice",suspendNotice);
        return "suspendNotice/suspend-notice-content";
    }

    @RequestMapping("/dealNotice/list.f")
    public String listdealNotice(@PageDefault(value = 10)Pageable page,Model model){
        int deployStatus = 1;//初始化状态为已发布
        Page<DealNotice> dealNotices = dealNoticeService.findAllDealNoticesByStatus(page,deployStatus);
        model.addAttribute("dealNotices",dealNotices);
        return "dealNotice/deal-notice-list";
    }

    @RequestMapping("/dealNotice/listAll.f")
    public String listAllDealNotice(@PageDefault(value = 10)Pageable page,Model model){
        int deployStatus = 1;//初始化状态为已发布
        Page<DealNotice> dealNotices = dealNoticeService.findAllDealNoticesByStatus(page, deployStatus);
        model.addAttribute("dealNotices",dealNotices);
        return "dealNotice/deal-notice-list-page";
    }

    @RequestMapping("/dealNotice/content.f")
    public String showDealNotice(String noticeId,Model model){
        DealNotice dealNotice = dealNoticeService.getNotice(noticeId);
        model.addAttribute("dealNotice",dealNotice);
        return "dealNotice/deal-notice-content";
    }

    @RequestMapping("online/resource/list.f")
    public String onlineList(String resourceCode,String resourceLocation, String resourcePurpose,String beginTime,String endTime,Model model,@PageDefault(value = 10)Pageable page,String zdCode){
        Page<TransResource> transResourceList = transResourceService.findDealResourceByCondiction2ResourceIds2(resourceCode, resourceLocation, resourcePurpose, beginTime, endTime, page, zdCode);
        List<TransResource> list = transResourceList.getItems();
        for(TransResource transResource:list){
            List lists = transResourceSonService.getTransResourceSonList(transResource.getResourceId());
            transResource.setTransResourceSon(lists);
        }
        model.addAttribute("transResourceList", transResourceList);
        return "onlineQuery/resource-list";
    }

    @RequestMapping("/transLaw/listAll.f")
    public String listAllTransLaw(@PageDefault(value = 10)Pageable page,Model model){
        int lawStatus = 1;//初始化状态为已发布
        Page<TransLaw> transLaws = transLawService.findTransNewsDeployed(page, lawStatus);
        model.addAttribute("transLaws",transLaws);
        return "transLaw/law-list-page";
    }

    @RequestMapping("/transLaw/content.f")
    public String showTransLaw(String lawId,Model model){
        TransLaw transLaw = transLawService.getById(lawId);
        model.addAttribute("transLaw",transLaw);
        return "transLaw/law-content";
    }

    @RequestMapping("/help/content.f")
    public String showHelp(){
        return "help_4_index";
    }

    @RequestMapping("/help/jmsm.f")
    public String showHelpjmsm(){
        return "help_jmsm";
    }

    @RequestMapping("/help/cjwt.f")
    public String showHelpcjwt(){
        return "help_cjwt";
    }

    @RequestMapping("/onlineQuery/dealResult.f")
    public String showDealResult(String resourceCode,String resourceLocation, String resourcePurpose,String beginTime,String endTime,Model model,@PageDefault(value = 10)Pageable page,String zdCode){
        Page<TransResource> transResourceList = transResourceService.findDealResourceByCondiction2ResourceIds2(resourceCode, resourceLocation, resourcePurpose, beginTime, endTime, page, zdCode);
        model.addAttribute("transResourceList", transResourceList);
        return "onlineQuery/deal-result";
    }
}
