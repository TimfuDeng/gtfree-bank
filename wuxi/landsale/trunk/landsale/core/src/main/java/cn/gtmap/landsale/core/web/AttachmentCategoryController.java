package cn.gtmap.landsale.core.web;

import cn.gtmap.landsale.core.service.AttachmentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
* 附件类型 服务
* @author zsj
* @version v1.0, 2017/11/1
*/
@RestController
@RequestMapping("/attachment")
public class AttachmentCategoryController {

    @Autowired
    AttachmentCategoryService attachmentCategoryService;

    /**
     * 地块报名时上传服务分类
     * @return
     */
    @RequestMapping(value = "/getTransResourceApplyAttachmentCategory", method = RequestMethod.POST)
    public Map getTransResourceApplyAttachmentCategory() {
        return attachmentCategoryService.getTransResourceApplyAttachmentCategory();
    }

    /**
     * 获取出让地块附件分类，用于后台地块信息录入
     * @return
     */
    @RequestMapping(value = "/getTransResourceAttachmentCategory", method = RequestMethod.POST)
    public Map getTransResourceAttachmentCategory() {
        return attachmentCategoryService.getTransResourceAttachmentCategory();
    }


    /**
     * 检查client端报名时上传资料是否完整
     * @param applyId(fileKey)
     * @param applyType 申请类型
     * @param ggId 公告id
     * @return
     */
    @RequestMapping(value = "/checkAttachmentNessesary", method = RequestMethod.POST)
    public boolean checkAttachmentNessesary(@RequestParam("/applyId") String applyId, @RequestParam("/applyType")  String applyType, @RequestParam("/ggId")  String ggId) {
        return attachmentCategoryService.checkAttachmentNessesary(applyId, applyType, ggId);
    }

}

