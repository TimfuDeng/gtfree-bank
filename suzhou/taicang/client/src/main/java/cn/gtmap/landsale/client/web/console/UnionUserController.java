package cn.gtmap.landsale.client.web.console;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransUserUnion;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransUserUnionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 联合竞买
 * Created by Jibo on 2015/5/25.
 */
@Controller
public class UnionUserController {


    @Autowired
    TransUserUnionService transUserUnionService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceApplyService transResourceApplyService;

    @RequestMapping("/union-list.f")
    public String UnionUserList(String applyId,Model model){
        List<TransUserUnion> transUserUnionList=
                transUserUnionService.findTransUserUnion(applyId);
        model.addAttribute("transUserUnionList",transUserUnionList);
        double scale=0;
        for(TransUserUnion transUserUnion:transUserUnionList){
            scale=scale + transUserUnion.getAmountScale();
        }
        model.addAttribute("scale",scale);
        return  "/console/union-list";
    }

    @RequestMapping("/union-edit.f")
    public String edit(String unionId,Model model){
        TransUserUnion transUserUnion=new TransUserUnion();
        if (StringUtils.isNotBlank(unionId))
            transUserUnion=transUserUnionService.getTransUserUnion(unionId);
        model.addAttribute("transUserUnion",transUserUnion);
        return  "/console/union-edit";
    }

    @RequestMapping("/union-save.f")
    public @ResponseBody String edit(String applyId,TransUserUnion transUserUnion,Model model){
        if(transUserUnion.getUserName().equalsIgnoreCase(SecUtil.getLoginUserViewName()))
            throw new AppException(3102);
        List<TransUserUnion> transUserUnionList=
                transUserUnionService.findTransUserUnion(applyId);
        double scale=0;
        for(TransUserUnion oldTransUserUnion:transUserUnionList){
            scale=scale + oldTransUserUnion.getAmountScale();
        }
        if(transUserUnion.getAmountScale()<0)
            throw new AppException(3103);
        else if(transUserUnion.getAmountScale()+scale>=100)
            throw new AppException(3104);

        transUserUnion=transUserUnionService.saveTransUserUnion(transUserUnion);
        model.addAttribute("transUserUnion",transUserUnion);
        return  "ok";
    }

    @RequestMapping("/union-del.f")
    public @ResponseBody String del(String unionId){
        transUserUnionService.deleteTransUserUnion(unionId);
        return  "ok";
    }

    @RequestMapping("/union-agree.f")
    public @ResponseBody String agree(String unionId){
        TransUserUnion transUserUnion=transUserUnionService.getTransUserUnion(unionId);
        //在一个资源中只能被联合一次
        TransResourceApply transResourceApply=
                transResourceApplyService.getTransResourceApply(transUserUnion.getApplyId());

        List<TransResourceApply> transResourceApplyList=
                transResourceApplyService.getTransResourceApplyByResourceId(transResourceApply.getResourceId());
        for(TransResourceApply resourceApply:transResourceApplyList){
            if (!resourceApply.getApplyId().equals(transResourceApply.getApplyId())){
                List<TransUserUnion> transUserUnionList=
                        transUserUnionService.findTransUserUnion(resourceApply.getApplyId());
                for(TransUserUnion userUnion:transUserUnionList){
                    if (userUnion.isAgree() && userUnion.getUserName().equals(transUserUnion.getUserName())){
                        return "在一个地块中只能参加联合竞买1次！";
                    }
                }
            }
        }

        //判断当前用户是否已经对这块地块报名了。如果已经报名，则不能再同意联合报名
        TransResourceApply oldTransResourceApply = transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(), transResourceApply.getResourceId());
        if(oldTransResourceApply!=null)
            return "您已经报名参与本地块的竞买，不能再参与联合竞买！";

        transUserUnion.setAgree(true);
        transUserUnionService.saveTransUserUnion(transUserUnion);
        return  "";
    }

}
