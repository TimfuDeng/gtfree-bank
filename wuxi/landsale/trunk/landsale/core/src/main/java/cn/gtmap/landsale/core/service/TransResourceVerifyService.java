package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceVerify;

import java.util.List;

/**
 * 成交审核
 * @author zsj
 * @version v1.0, 2017/12/23
 */
public interface TransResourceVerifyService {

    /**
     * 保存
     * @param transResourceVerify
     * @return
     */
    ResponseMessage<TransResourceVerify> saveTransVerify(TransResourceVerify transResourceVerify);

    /**
     * 用于一次报价及摇号，重新保存竞买结果时重置审核状态
     * @param transResourceVerify
     * @return
     */
    ResponseMessage<TransResourceVerify> updateTransVerify(TransResourceVerify transResourceVerify);
    /**
     * 根据 ResourceId 获取地块成交审核信息
     * @param resourceId
     * @return
     */
    List<TransResourceVerify> getTransVerifyListByResourceId(String resourceId);

    /**
     * 根据 ResourceId 获取地块 最新成交审核信息
     * @param resourceId
     * @return
     */
    TransResourceVerify getTransVerifyLastByResourceId(String resourceId);

    /**
     * 根据id获取地块成交审核信息
     * @param verifyId
     * @return
     */
    TransResourceVerify getTransVerifyById(String verifyId);
}
