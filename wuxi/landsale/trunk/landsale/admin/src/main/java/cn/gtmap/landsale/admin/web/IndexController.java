package cn.gtmap.landsale.admin.web;

import cn.gtmap.landsale.admin.service.TransMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理端首页
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Controller
@RequestMapping({"/index", "/"})
public class IndexController {

    @Autowired
    TransMenuService transMenuService;

    @RequestMapping({"/index", "/"})
    public String index() {
        return "layout";
    }

}

