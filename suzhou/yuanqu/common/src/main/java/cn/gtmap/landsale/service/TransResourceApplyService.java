package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransResourceApply;

import java.util.List;
import java.util.Set;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public interface TransResourceApplyService {

    TransResourceApply getTransResourceApply(String applyId);

    TransResourceApply getTransResourceApplyByUserId(String userId,String resourceId);

    Page<TransResourceApply> getTransResourceApplyPageByUserId(String userId,Pageable request);

    /**
     * 根据地块id得到所有申购人信息
     * @param resourceId
     * @param request
     * @return
     */
    Page<TransResourceApply> getTransResourceApplyPageByresourceId(String resourceId,Pageable request);
    Page<TransResourceApply> getTransResourceApplyByresourceId(String resourceId,Pageable request,String userName);

    TransResourceApply saveTransResourceApply(TransResourceApply transResourceApply);

    List<TransResourceApply> getTransResourceApplyByResourceId(String resourceId);

    /**
     * 获得参与限时竞买的人
     * @param resourceId
     * @return
     */
    List<TransResourceApply> getEnterLimitTransResourceApply(String resourceId);

    /**
     * 设置用户竞买进入
     * @param applyId
     */
    void enterLimitTransResourceApply(String applyId);

    /**
     * 竞买人是否已经确认过进入限时竞价阶段了
     * @param applyId
     * @return
     */
    boolean isConfirmedEnterLimitOffer(String applyId);

    /**
     * 获取到竞买地块已经使用过的号牌
     * @param resourceId
     * @return
     */
    List<Integer> getAllUsedApplyNumber(String resourceId);

    /**
     * 判断是否为有效号牌
     * @param resourceId
     * @param applyNumber
     * @return
     */
    boolean isValidApplyNumber(String resourceId,int applyNumber);
}
