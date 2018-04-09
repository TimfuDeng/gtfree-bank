package cn.gtmap.landsale.core.service;

import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.YHAgree;

import java.util.List;

/**
 * 同意参加摇号记录
 * @author lq
 * @version v1.0, 2017/12/27
 */
public interface YHAgreeService {

    /**
     * 保存或更新参与摇号信息
     * @param yhAgree
     * @return
     */
    ResponseMessage<YHAgree> saveOrUpdateYHAgree(YHAgree yhAgree);

    /**
     * 获取参与摇号信息
     * @param id
     * @return
     */
    YHAgree getYHAgree(String id);

    /**
     * 根据地块id获取参与摇号信息列表
     * @param resourceId
     * @return
     */
    List<YHAgree> getYHAgreeByResourceId(String resourceId);

    /**
     * 根据地块id获取参与摇号信息列表
     * @param resourceId
     * @return
     */
    List<YHAgree> getAllYHAgreeByResourceId(String resourceId);

    /**
     * 根据地块id和用户id获取参与摇号信息
     * @param resourceId
     * @param userId
     * @return
     */
    YHAgree getYHAgreeByResourceIdAndUserId(String resourceId,String userId);
}
