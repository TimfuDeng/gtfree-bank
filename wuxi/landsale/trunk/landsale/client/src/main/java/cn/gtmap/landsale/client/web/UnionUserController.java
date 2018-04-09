package cn.gtmap.landsale.client.web;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.client.register.TransResourceApplyClient;
import cn.gtmap.landsale.client.register.TransUserUnionClient;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceApply;
import cn.gtmap.landsale.common.model.TransUser;
import cn.gtmap.landsale.common.model.TransUserUnion;
import cn.gtmap.landsale.common.security.SecUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 联合竞买服务
 * @author cxm
 * @version v1.0, 2017/12/11
 */
@Controller
@RequestMapping("/union")
public class UnionUserController {
    @Autowired
    TransUserUnionClient transUserUnionClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @RequestMapping("/union-list")
    public String unionUserList(String applyId, Model model){
        model.addAttribute("applyId",applyId);
        List<TransUserUnion> transUserUnionList=
                transUserUnionClient.findTransUserUnion(applyId);
        model.addAttribute("transUserUnionList",transUserUnionList);
        double scale=0;
        for(TransUserUnion transUserUnion:transUserUnionList){
            scale=scale + transUserUnion.getAmountScale();
        }
        model.addAttribute("scale",scale);
        return  "/console/union-list";
    }

    @RequestMapping("/union-edit")
    public String edit(String unionId,Model model){
        TransUserUnion transUserUnion=new TransUserUnion();
        if (StringUtils.isNotBlank(unionId)) {
            transUserUnion = transUserUnionClient.getTransUserUnion(unionId);
        }
        model.addAttribute("transUserUnion",transUserUnion);
        return  "/console/union-edit";
    }
    @RequestMapping("/union-save")
    public @ResponseBody ResponseMessage edit(String applyId, TransUserUnion transUserUnion, Model model){
        if(transUserUnion.getUserName().equalsIgnoreCase(SecUtil.getLoginUserViewName())) {
            return new ResponseMessage(false, "被联合人与竞买申请人不能为同一人！");
        }
        List<TransUserUnion> transUserUnionList=
                transUserUnionClient.findTransUserUnion(applyId);
        double scale=0;
        for(TransUserUnion oldTransUserUnion:transUserUnionList){
            scale=scale + oldTransUserUnion.getAmountScale();
        }
        if(transUserUnion.getAmountScale()<0) {
            return new ResponseMessage(false, "出资比例不能为负数！");
        } else if(transUserUnion.getAmountScale()+scale>=100) {
            return new ResponseMessage(false, "所有被联合人的出资比例达到或者超过100%！");
        }

        transUserUnionClient.saveTransUserUnion(transUserUnion);
        model.addAttribute("transUserUnion",transUserUnion);
        return  new ResponseMessage(true);
    }

    @RequestMapping("/union-del.f")
    public @ResponseBody String del(String unionId){
        transUserUnionClient.deleteTransUserUnion(unionId);
        return  "ok";
    }

    @RequestMapping("/union-agree")
    @ResponseBody
    public ResponseMessage<TransUserUnion> agree(String unionId){
        TransUserUnion transUserUnion=transUserUnionClient.getTransUserUnion(unionId);
        //在一个资源中只能被联合一次
        TransResourceApply transResourceApply=
                transResourceApplyClient.getTransResourceApply(transUserUnion.getApplyId());
        List<TransResourceApply> transResourceApplyList=
                transResourceApplyClient.getTransResourceApplyByResourceId(transResourceApply.getResourceId());
        for(TransResourceApply resourceApply:transResourceApplyList){
            if (!resourceApply.getApplyId().equals(transResourceApply.getApplyId())){
                List<TransUserUnion> transUserUnionList=
                        transUserUnionClient.findTransUserUnion(resourceApply.getApplyId());
                for(TransUserUnion userUnion:transUserUnionList){
                    if (userUnion.isAgree() && userUnion.getUserName().equals(transUserUnion.getUserName())){
//                        return "在一个地块中只能参加联合竞买1次！";
                        return new ResponseMessage(false,"在一个地块中只能参加联合竞买1次！");
                    }
                }
            }
        }

        //判断当前用户是否已经对这块地块报名了。如果已经报名，则不能再同意联合报名
        TransResourceApply oldTransResourceApply = transResourceApplyClient.getTransResourceApplyByUserId(SecUtil.getLoginUserId(), transResourceApply.getResourceId());
        if(oldTransResourceApply!=null)
//            return "您已经报名参与本地块的竞买，不能再参与联合竞买！";
        {
            return new ResponseMessage(false, "您已经报名参与本地块的竞买，不能再参与联合竞买！");
        }

        transUserUnion.setAgree(true);
        transUserUnionClient.saveTransUserUnion(transUserUnion);
//        return  "";
        return new ResponseMessage(true);
    }
}
