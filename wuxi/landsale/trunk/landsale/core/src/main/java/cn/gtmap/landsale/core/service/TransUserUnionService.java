package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUserUnion;

import java.util.List;

/**
 * 联合竞买服务
 * @author zsj
 * @version v1.0, 2017/11/27
 */
public interface TransUserUnionService {

    /**
     * 根据Id获取 联合竞买
     * @param unionId 联合竞买Id
     * @return 联合竞买对象
     */
    TransUserUnion getTransUserUnion(String unionId);

    /**
     * 获取 联合竞买列表
     * @param applyId 地块申请Id
     * @return 出让公告对象
     */
    List<TransUserUnion> findTransUserUnion(String applyId);

    /**
     * 被联合竞买列表
     * @param userName
     * @param request
     * @return
     */
    Page<TransUserUnion> findTransUserUnionByUserName(String userName, Pageable request);

    /**
     * 保存联合竞买 信息
     * @param transUserUnion
     * @return
     */
    ResponseMessage<TransUserUnion> saveTransUserUnion(TransUserUnion transUserUnion);

    /**
     * 删除
     * @param unionId
     */
    ResponseMessage deleteTransUserUnion(String unionId);

    /**
     * 获取当前用户是被联合人的地块的联合竞买记录
     * @param userName 被联合人姓名，是姓名不是用户名
     * @param resourceId 地块Id
     * @return
     */
    List<TransUserUnion> getResourceTransUserUnionByUserName(String userName, String resourceId);
}
