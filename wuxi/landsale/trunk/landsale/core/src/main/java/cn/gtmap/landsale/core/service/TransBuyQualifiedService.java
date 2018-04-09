package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.TransBuyQualified;
import cn.gtmap.landsale.common.model.ResponseMessage;

import java.util.List;

/**
 * 用户服务
 * @author cxm
 * @version 1.0, 2017/11/13
 */
public interface TransBuyQualifiedService {

    /**
     * 查找审核信息
     * @param applyId
     * @return
     */
    public List<TransBuyQualified> getListTransBuyQualifiedByApplyId(String applyId);

    /**
     * 保存申购资格申请
     * @param transBuyQualified
     * @return
     */
    ResponseMessage<TransBuyQualified> saveTransBuyQualified(TransBuyQualified transBuyQualified);

    /**
     * 根据ApplyId获取 当前申购资格审核信息
     * @param applyId
     * @return
     */
    TransBuyQualified getTransBuyQualifiedForCurrent(String applyId);

    /**
     * 获取
     * @param qualifiedId
     * @return
     */
    TransBuyQualified getTransBuyQualifiedById(String qualifiedId);

    /**
     * 删除
     * @param transBuyQualified
     */
    void deleteTransBuyQualified(TransBuyQualified transBuyQualified);
}
