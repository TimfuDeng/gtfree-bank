package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.YHAgree;
import cn.gtmap.landsale.common.model.YHResult;
import cn.gtmap.landsale.core.service.YHAgreeService;
import cn.gtmap.landsale.core.service.YHResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lq on 2017/12/27.
 */
@RestController
@RequestMapping(value = "/yh")
public class YHController {

    @Autowired
    YHAgreeService yhAgreeService;

    @Autowired
    YHResultService yhResultService;

    @RequestMapping("/getYHAgree")
    public YHAgree getYHAgree(@RequestParam(value = "agreeId") String agreeId) {
        return yhAgreeService.getYHAgree(agreeId);
    }

    @RequestMapping("/getYHAgreeByResourceId")
    public List<YHAgree> getYHAgreeByResourceId(@RequestParam(value = "resourceId") String resourceId) {
        return yhAgreeService.getYHAgreeByResourceId(resourceId);
    }

    @RequestMapping("/getYHAgreeByResourceIdAndUserId")
    public YHAgree getYHAgreeByResourceIdAndUserId(@RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "userId") String userId){
        return yhAgreeService.getYHAgreeByResourceIdAndUserId(resourceId,userId);
    }

    @RequestMapping("/saveOrUpdateYHAgree")
    ResponseMessage<YHAgree> saveOrUpdateYHAgree(@RequestBody YHAgree yhAgree) {
        return yhAgreeService.saveOrUpdateYHAgree(yhAgree);
    }

    @RequestMapping("/getYHResult")
    YHResult getYHResult(@RequestParam(value = "resultId") String resultId) {
        return yhResultService.getYHResult(resultId);
    }

    @RequestMapping("/getYHResultByYHAgreeId")
    YHResult getYHResultByYHAgreeId(@RequestParam(value = "agreeId") String agreeId){
        return yhResultService.getYHResultByYHAgreeId(agreeId);
    }

    @RequestMapping("/saveOrUpdateYHResult")
    ResponseMessage<YHResult> saveOrUpdateYHResult(@RequestParam(value = "resourceId") String resourceId,@RequestParam(value = "successPrice") String successPrice,@RequestParam(value = "offerUserId") String offerUserId,@RequestParam(value = "userId") String userId){
        return yhResultService.saveOrUpdateYHResult(resourceId,successPrice,offerUserId,userId);
    }

    @RequestMapping("/postYHResult")
    ResponseMessage<YHResult> postYHResult(@RequestParam(value = "yhResultId") String yhResultId){
        return yhResultService.postYHResult(yhResultId);
    }

    @RequestMapping("/getYHResultByResourceId")
    YHResult getYHResultByResourceId(@RequestParam(value = "resourceId") String resourceId) {
        return yhResultService.getYHResultByResourceId(resourceId);
    }
}
