package cn.gtmap.landsale.service;

import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/23
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
    public boolean checkAttachmentNessesary(String applyId,String applyType,String ggId);

    /**
     * 获取出让地块附件分类，用于后台地块信息录入
     * @return
     */
    public Map getTransResourceAttachmentCategory();
}
