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
     * 获取出让地块附件分类，用于后台地块信息录入
     * @return
     */
    public Map getTransResourceAttachmentCategory();
}
