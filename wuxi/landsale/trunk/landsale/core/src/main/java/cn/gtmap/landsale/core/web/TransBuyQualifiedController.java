package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBuyQualified;
import cn.gtmap.landsale.core.service.TransBuyQualifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 资格审核
 * @author cxm
 * @version v1.0, 2017/11/13
 */
@RestController
@RequestMapping("/qualified")
public class TransBuyQualifiedController {
    @Autowired
    TransBuyQualifiedService transBuyQualifiedService;

    @RequestMapping("/getListTransBuyQualifiedByApplyId")
    public List<TransBuyQualified> getListTransBuyQualifiedByApplyId(@RequestParam(value = "applyId") String applyId) {
        return transBuyQualifiedService.getListTransBuyQualifiedByApplyId(applyId);
    }


    @RequestMapping("/saveTransBuyQualified")
    public ResponseMessage<TransBuyQualified> saveTransBuyQualified(@RequestBody TransBuyQualified transBuyQualified) {
        return transBuyQualifiedService.saveTransBuyQualified(transBuyQualified);
    }

    /**
     * 根据ApplyId获取 当前申购资格审核信息
     * @param applyId
     * @return
     */
    @RequestMapping("/getTransBuyQualifiedForCurrent")
    public TransBuyQualified getTransBuyQualifiedForCurrent(@RequestParam(value = "applyId") String applyId) {
        return transBuyQualifiedService.getTransBuyQualifiedForCurrent(applyId);
    }

    @RequestMapping("/getTransBuyQualifiedById")
    public TransBuyQualified getTransBuyQualifiedById(@RequestParam(value = "qualifiedId") String qualifiedId) {
        return transBuyQualifiedService.getTransBuyQualifiedById(qualifiedId);
    }

}
