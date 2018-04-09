package cn.gtmap.landsale.core.web;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceVerify;
import cn.gtmap.landsale.core.service.TransResourceVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 成交审核服务
 * @author zsj
 * @version v1.0, 2017/12/23
 */
@RestController
@RequestMapping("/verify")
public class TransResourceVerifyController {

    @Autowired
    TransResourceVerifyService transResourceVerifyService;

    /**
     * 根据Id获取 成家审核
     * @param verifyId 成家审核Id
     * @return 成家审核对象
     */
    @RequestMapping("/getTransVerifyById")
    public TransResourceVerify getTransVerifyById(@RequestParam("verifyId") String verifyId) {
        return transResourceVerifyService.getTransVerifyById(verifyId);
    }

    /**
     * 保存 成家审核对象
     * @param transResourceVerify 成家审核对象
     * @return  新的成家审核对象
     */
    @RequestMapping("/saveTransVerify")
    public ResponseMessage<TransResourceVerify> saveTransVerify(@RequestBody TransResourceVerify transResourceVerify) {
        return transResourceVerifyService.saveTransVerify(transResourceVerify);
    }

    /**
     * 用于一次报价及摇号，重新保存竞买结果时重置审核状态
     * @param transResourceVerify
     * @return
     */
    @RequestMapping("/updateTransVerify")
    ResponseMessage<TransResourceVerify> updateTransVerify(@RequestBody TransResourceVerify transResourceVerify) {
        return transResourceVerifyService.updateTransVerify(transResourceVerify);
    }


    /**
     * 根据 ResourceId 获取地块成交审核信息
     * @param resourceId
     * @return
     */
    @RequestMapping("/getTransVerifyListByResourceId")
    public List<TransResourceVerify> getTransVerifyListByResourceId(@RequestParam("resourceId") String resourceId) {
        return transResourceVerifyService.getTransVerifyListByResourceId(resourceId);
    }

    /**
     * 根据 ResourceId 获取地块 最新成交审核信息
     * @param resourceId
     * @return
     */
    @RequestMapping("/getTransVerifyLastByResourceId")
    public TransResourceVerify getTransVerifyLastByResourceId(@RequestParam("resourceId") String resourceId) {
        return transResourceVerifyService.getTransVerifyLastByResourceId(resourceId);
    }

}
