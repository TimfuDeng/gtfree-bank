package cn.gtmap.landsale.core.service;

import java.util.Map;

/**
 * 附件类型服务
 * @author zsj
 * @version v1.0, 2017/11/1
 */
public interface AttachmentCategoryService {

    /**
     * 地块报名时上传服务分类
     * @return
     */
    public Map getTransResourceApplyAttachmentCategory();

    /**
     * 检查client端报名时上传资料是否完整
     * @param applyId(fileKey)
     * @param applyType 申请类型
     * @param ggId 公告id
     * @return
     */
    public boolean checkAttachmentNessesary(String applyId, String applyType, String ggId);

    /**
     * 获取出让地块附件分类，用于后台地块信息录入
     * @return
     */
    public Map getTransResourceAttachmentCategory();
}
