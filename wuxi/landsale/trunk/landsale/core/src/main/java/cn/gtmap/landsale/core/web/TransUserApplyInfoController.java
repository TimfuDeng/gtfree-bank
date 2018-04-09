package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.common.model.TransUserApplyInfo;
import cn.gtmap.landsale.core.service.TransUserApplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户申请地块 扩展联系人信息服务
 * @author zsj
 * @version v1.0, 2017/11/28
 */
@RestController
@RequestMapping("/userApplyInfo")
public class TransUserApplyInfoController {
    @Autowired
    TransUserApplyInfoService transUserApplyInfoService;

    /**
     * 根据Id获取人员申请信息
     * @param infoId
     * @return
     */
    @RequestMapping("/getTransUserApplyInfo")
    public TransUserApplyInfo getTransUserApplyInfo(@RequestParam(value = "infoId") String infoId) {
        return transUserApplyInfoService.getTransUserApplyInfo(infoId);
    }

    /**
     * 根据用户Id获取人员申请信息
     * @param userId
     * @return
     */
    @RequestMapping("/getTransUserApplyInfoByUser")
    public List<TransUserApplyInfo> getTransUserApplyInfoByUser(@RequestParam(value = "userId") String userId) {
        return transUserApplyInfoService.getTransUserApplyInfoByUser(userId);
    }

    /**
     * 保存人员申请信息
     * @param transUserApplyInfo
     * @return
     */
    @RequestMapping("/saveTransUserApplyInfo")
    public TransUserApplyInfo saveTransUserApplyInfo(@RequestBody TransUserApplyInfo transUserApplyInfo) {
        return transUserApplyInfoService.saveTransUserApplyInfo(transUserApplyInfo);
    }

    /**
     * 删除人员申请信息
     * @param infoIds
     */
    @RequestMapping("/deleteTransUserApplyInfo")
    public void deleteTransUserApplyInfo(@RequestParam(value = "infoIds") String infoIds) {
        transUserApplyInfoService.deleteTransUserApplyInfo(infoIds);
    }

    /**
     * 根据人员Id删除其申请信息
     * @param userId
     */
    @RequestMapping("/deleteTransUserApplyInfoByUser")
    public void deleteTransUserApplyInfoByUser(@RequestParam(value = "userId") String userId) {
        transUserApplyInfoService.deleteTransUserApplyInfoByUser(userId);
    }

}
