package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceApply;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public interface TransResourceApplyService {

    /**
     * 根据applyid获取
     * @param applyId
     * @return
     */
    TransResourceApply getTransResourceApply(String applyId);

    /**
     * 根据用户id获取
     * @param userId
     * @param resourceId
     * @return
     */
    TransResourceApply getTransResourceApplyByUserId(String userId, String resourceId);

    /**
     * 报名查询
     * @param resourceId
     * @param applyStep
     * @return
     */
    List<TransResourceApply> getTransResourceApplyStep(String resourceId, int applyStep);

    /**
     * 根据用户id获取报名列表
     * @param userId
     * @param request
     * @return
     */
    Page<TransResourceApply> getTransResourceApplyPageByUserId(String userId, Pageable request);

    /**
     * 保存
     * @param transResourceApply
     * @return
     */
    ResponseMessage<TransResourceApply> saveTransResourceApply(TransResourceApply transResourceApply);

    /**
     * 根据地块id获取
     * @param resourceId
     * @return
     */
    List<TransResourceApply> getTransResourceApplyByResourceId(String resourceId);

    /**
     * 获得参与限时竞买的人
     * @param resourceId
     * @return
     */
    List<TransResourceApply> getEnterLimitTransResourceApply(String resourceId);

    /**
     * 删除
     * @param applyId
     * @return
     */
    ResponseMessage deleteApply(String applyId);
}
