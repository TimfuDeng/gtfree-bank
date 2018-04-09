package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.egovplat.core.web.BaseController;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransNewsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.net.httpserver.Authenticator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Set;

/**
 * 新闻管理
 * Created by www on 2015/9/23.
 */
/*
@Controller
@RequestMapping(value = "console/news")
public class NewsController extends BaseController {

    @Autowired
    TransNewsService transNewsService;

    @ModelAttribute("transNews")
    public TransNews getRole(@RequestParam(value = "newsId",required = false)String newsId){
        return StringUtils.isBlank(newsId)? new TransNews():transNewsService.getTransNews(newsId);
    }


    @RequestMapping(value = "list")
    public String list(@PageDefault(value = 10) Pageable page,String title,String newsId,String status,Model model){
        Page<TransNews> transNewsPage=null;
        TransNews transNews=null;
        if (StringUtils.isNotBlank(status))
        {
            transNews= transNewsService.getTransNews(newsId);
            transNews.setNewsStauts(Integer.parseInt(status));
            transNewsService.saveTransNews(transNews);
        }
        transNewsPage= transNewsService.findTransNews(title,page);
        model.addAttribute("transNewsPage",transNewsPage);
        model.addAttribute("title",title);

        return "news/news-list";
    }


    @RequestMapping("/status/change.f")
    public @ResponseBody Object changeStatus(String newsId,int status,Model model) {
        TransResource transResource=null;
        if (StringUtils.isNotBlank(newsId)) {
            TransNews transNews=null;
                transNews= transNewsService.getTransNews(newsId);
                transNews.setNewsStauts(status);
                transNewsService.saveTransNews(transNews);
            return success();
        }
        return fail("新闻ID为空！");
    }


    @RequestMapping("/status/view.f")
    public String status(String newsId,Model model) {
        TransNews transNews=null;
        if (StringUtils.isNotBlank(newsId)) {
            transNews = transNewsService.getTransNews(newsId);
        }
        model.addAttribute("news", transNews);
        return "common/news-status";
    }

    @RequestMapping(value = "save")
    public String save (@ModelAttribute("transNews")TransNews transNews,RedirectAttributes ra,Model model){
        if(StringUtils.isBlank(transNews.getNewsId())){
            transNews.setNewsId(null);
        }
        transNewsService.saveTransNews(transNews);
        ra.addFlashAttribute("_result",true);
        ra.addFlashAttribute("_msg","保存成功");
        return "redirect:/console/news/edit?newsId="+transNews.getNewsId();

    }

    @RequestMapping(value = "edit")
    public String transNews( String newsId,Model model){
        TransNews transNews=null;
        if(StringUtils.isNotBlank(newsId)){
            transNews= transNewsService.getTransNews(newsId);
        }else{
            transNews=new TransNews();
            transNews.setNewsReportTime(new Date());
        }

        model.addAttribute("transNews",transNews);
        return "news/news-edit";
    }

    @RequestMapping(value = "view")
    public String viewTransNews( String newsId,Model model){
        TransNews transNews=null;
        transNews= transNewsService.getTransNews(newsId);
        model.addAttribute("transNews",transNews);
        return "news/news";
    }


    @RequestMapping(value = "delete.f")
    @ResponseBody
    public String deleteTreansNews(@RequestParam(value = "newsIds",required = false) String newsIds){
        transNewsService.deleteTransNews(Lists.newArrayList(newsIds.split(";")));
        return "true";
    }

}
*/
