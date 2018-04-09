package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransUserApplyInfo;

import java.util.Collection;
import java.util.List;

/**
 * 交易人员申请信息服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/25
 */
public interface TransUserApplyInfoService {

    /**
     * 根据Id获取人员申请信息
     * @param infoId
     * @return
     */
    public TransUserApplyInfo getTransUserApplyInfo(String infoId);

    /**
     * 根据用户Id获取人员申请信息
     * @param userId
     * @return
     */
    public List<TransUserApplyInfo> getTransUserApplyInfoByUser(String userId);





    /**
     * 保存人员申请信息
     * @param transUserApplyInfo
     * @return
     */
    public TransUserApplyInfo saveTransUserApplyInfo(TransUserApplyInfo transUserApplyInfo);

    /**
     * 保存申请人员信息，并在申购表中保存申请人员Id
     * @param transUserApplyInfo
     * @param transResourceApply
     * @return
     */
    public TransUserApplyInfo saveTransUserApplyInfoAndTransResourceApply(TransUserApplyInfo transUserApplyInfo,TransResourceApply transResourceApply);

    /**
     * 删除人员申请信息
     * @param infoIds
     */
    public void deleteTransUserApplyInfo(Collection<String> infoIds);

    /**
     * 根据人员Id删除其申请信息
     * @param userId
     */
    public void deleteTransUserApplyInfoByUser(String userId);
}
