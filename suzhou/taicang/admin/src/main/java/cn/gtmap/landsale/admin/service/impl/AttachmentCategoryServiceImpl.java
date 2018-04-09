package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.service.AttachmentCategoryService;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

/**
 * 附件分类
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/23
 */
public class AttachmentCategoryServiceImpl implements AttachmentCategoryService {
    /**
     * 竞买人申请地块时上传材料分类
     */
    Map applyAttachmentCategory;

    /**
     * 出让地块附件材料分类
     */
    Map resourceAttachmentCategory;

    public void setApplyAttachmentCategory(Map applyAttachmentCategory) {
        this.applyAttachmentCategory = applyAttachmentCategory;
    }

    public void setResourceAttachmentCategory(Map resourceAttachmentCategory) {
        this.resourceAttachmentCategory = resourceAttachmentCategory;
    }

    /**
     * 地块报名时上传服务分类
     *
     * @return
     */
    @Override
    public Map getTransResourceApplyAttachmentCategory() {
        return applyAttachmentCategory;
    }

    /**
     * 获取出让地块附件分类，用于后台地块信息录入
     *
     * @return
     */
    @Override
    public Map getTransResourceAttachmentCategory() {
        return resourceAttachmentCategory;
    }
}
