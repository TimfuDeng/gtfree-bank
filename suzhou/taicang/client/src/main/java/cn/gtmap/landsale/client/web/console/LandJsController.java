package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.util.HtmlUtils;
import cn.gtmap.landsale.client.util.HTMLSpirit;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * Created by Jibo on 2015/6/24.
 */
@Controller
public class LandJsController {

    @RequestMapping("/view/ggdk_view.aspx")
    public String enterLandJs(HttpServletRequest request,Model model) throws Exception{
        String queryString = request.getQueryString();
        if(StringUtils.isNotBlank(queryString)){
            queryString = HTMLSpirit.delHTMLTag(URLDecoder.decode(queryString, "UTF-8"));
        }
        model.addAttribute("requestStr", queryString);
        return  "landjs";
    }
}
