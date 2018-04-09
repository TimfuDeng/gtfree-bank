package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneApply;
import cn.gtmap.landsale.common.model.ResponseMessage;

/**
 * 一次报价流程Service
 * @author zsj
 * @version v1.0, 2017/12/8
 */
public interface OneApplyService {

    /**
     * 保存一次报价流程
     * @param oneApply
     * @return
     */
    ResponseMessage<OneApply> saveOneApply(OneApply oneApply);

    /**
     * 根据报价设置信息Id 用户编号查找 报价流程
     * @param targetId
     * @param transUserId
     * @return
     */
    OneApply getOnApply(String targetId, String transUserId);

    /**
     * 获取报价流程列表
     * @param transUserId
     * @param page
     * @return
     */
    Page<OneApply> findOneApply(String transUserId, Pageable page);

}
