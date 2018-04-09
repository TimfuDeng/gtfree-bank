package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.TransUserApplyInfo;

import java.util.List;

/**
 * 用户申请地块 扩展联系人信息
 * @author zsj
 * @version v1.0, 2017/11/28
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
     * 根据 地块申请Id 获取人员申请信息
     * @param applyId
     * @return
     */
    public TransUserApplyInfo getTransUserApplyInfoByApplyId(String applyId);

    /**
     * 保存人员申请信息
     * @param transUserApplyInfo
     * @return
     */
    public TransUserApplyInfo saveTransUserApplyInfo(TransUserApplyInfo transUserApplyInfo);

    /**
     * 删除人员申请信息
     * @param infoIds
     */
    public void deleteTransUserApplyInfo(String infoIds);

    /**
     * 根据人员Id删除其申请信息
     * @param userId
     */
    public void deleteTransUserApplyInfoByUser(String userId);

}


