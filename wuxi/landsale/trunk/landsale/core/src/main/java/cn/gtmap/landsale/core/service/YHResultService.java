package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.YHResult;

/**
 * 摇号结果
 * @author lq
 * @version v1.0, 2017/12/27
 */
public interface YHResultService {

    /**
     * 仅保存摇号信息，发布时再修改地块信息
     * @param resourceId
     * @param successPrice
     * @param offerUserId
     * @param userId
     * @return
     */
    ResponseMessage<YHResult> saveOrUpdateYHResult(String resourceId, String successPrice, String offerUserId, String userId);

    /**
     * 发布摇号信息，同时修改地块状态
     * @param yhResultId
     * @return
     */
    ResponseMessage postYHResult(String yhResultId);

    /**
     * 获取摇号结果
     * @param id
     * @return
     */
    YHResult getYHResult(String id);

    /**
     * 根据参与摇号信息获取摇号结果
     * @param agreeId
     * @return
     */
    YHResult getYHResultByYHAgreeId(String agreeId);

    /**
     * 通过resourceId获取摇号结果
     * @param resourceId
     * @return
     */
    YHResult getYHResultByResourceId(String resourceId);
}
