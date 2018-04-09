package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransResourceApply;

import java.util.List;

/**
 * Created by jibo1_000 on 2015/5/9.
 */
public interface TransResourceApplyService {

    TransResourceApply getTransResourceApply(String applyId);

    TransResourceApply getTransResourceApplyByUserId(String userId,String resourceId);

    Page<TransResourceApply> getTransResourceApplyPageByUserId(String userId,Pageable request);

    TransResourceApply saveTransResourceApply(TransResourceApply transResourceApply);

    List<TransResourceApply> getTransResourceApplyByResourceId(String resourceId);

    /**
     * 获得参与限时竞买的人
     * @param resourceId
     * @return
     */
    List<TransResourceApply> getEnterLimitTransResourceApply(String resourceId);
    
    /**
     * 统计提交资格审核人数
     * @param trialType
     * @return
     */
    List<TransResourceApply> getCommitTransResourceApply(String resourceId);
    
    List<TransResourceApply> getCommitToTransResourceApply(String resourceId);
    
    List<TransResourceApply> getPassedTransResourceApply(String resourceId);
    
    List<TransResourceApply> getBailPaidTransResourceApply(String resourceId);

    void delete(String applyId);
}
