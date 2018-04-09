package cn.gtmap.landsale.admin.web.console;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageDefault;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.OnePriceLog;
import cn.gtmap.landsale.model.OneTarget;
import cn.gtmap.landsale.service.OnePriceLogService;
import cn.gtmap.landsale.service.OneTargetService;
import cn.gtmap.landsale.service.TransTargetService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.math.BigDecimal;
import java.util.List;

/**
 * 处理出让系统跨域请求
 * Created by trr on 2016/8/23.
 */
@Controller
@RequestMapping("trans/")
public class TransController {

    @Autowired
    OneTargetService oneTargetService;
    @Autowired
    OnePriceLogService onePriceLogService;

    /**
     * 成交之后加载一次报价结果
     * @param transTargetId
     * @param model
     * @return
     */
    @RequestMapping("onePriceLog.f")
    public String onePriceLogResult(String transTargetId,Model model,HttpServletResponse rep,@PageDefault(value=10) Pageable page){
        List<OnePriceLog> onePriceLogList=null;
        Page<OnePriceLog> onePriceLogListPage=null;
        long totalPrice=0L;
        Double avgPrice=null;
        OneTarget oneTarget = oneTargetService.getOneTargetByTransTarget(transTargetId);
        if(null!=oneTarget.getSuccessPrice()&&oneTarget.getSuccessPrice()>0){
            onePriceLogListPage=onePriceLogService.findOnePriceLogList2Page(transTargetId, page);
            onePriceLogList = onePriceLogService.findOnePriceLogList(transTargetId);
            if (onePriceLogList.size()>0){
                for(int i=0;i<onePriceLogList.size(); i++){
                    totalPrice = totalPrice + onePriceLogList.get(i).getPrice();
                }
                avgPrice=(double)totalPrice/(onePriceLogList.size());
            }
            //四舍五入，保留2位小数
            BigDecimal bigDecimal=new BigDecimal(avgPrice);
            avgPrice=bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        rep.setHeader("Access-Control-Allow-Origin","*");
        model.addAttribute("avgPrice",avgPrice);
        model.addAttribute("oneTarget",oneTarget);
        model.addAttribute("onePriceLogList",onePriceLogListPage);
        model.addAttribute("transTargetId",transTargetId);

        //可以跨域
        return "trans/oneprice-log";

    }

}
