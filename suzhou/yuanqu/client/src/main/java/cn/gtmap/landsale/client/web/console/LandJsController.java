package cn.gtmap.landsale.client.web.console;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jibo on 2015/6/24.
 */
@Controller
public class LandJsController {

    @RequestMapping("/view/ggdk_view.aspx")
    public String enterLandJs(HttpServletRequest request,Model model) throws Exception{
        model.addAttribute("requestStr",request.getQueryString());
        return  "landjs";
    }
}
