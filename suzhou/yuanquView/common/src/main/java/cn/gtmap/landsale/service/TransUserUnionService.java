package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransUserUnion;

import java.util.List;

/**
 * Created by Jibo on 2015/5/25.
 */
public interface TransUserUnionService {
    /**
     *
     * @param unionId
     * @return
     */
    TransUserUnion getTransUserUnion(String unionId);

    /**
     *
     * @param applyId
     * @return
     */
    List<TransUserUnion> findTransUserUnion(String applyId);

    /**
     * 被联合竞买列表
     * @param userName
     * @param request
     * @return
     */
    Page<TransUserUnion> findTransUserUnionByUserName(String userName,Pageable request);


    Page<TransUserUnion> findTransUserUnionByUserNameIsAgree(String userName,Pageable request);
    /**
     *
     * @param transUserUnion
     * @return
     */
    TransUserUnion saveTransUserUnion(TransUserUnion transUserUnion);

    /**
     * 删除
     * @param unionId
     */
    void deleteTransUserUnion(String unionId);

    /**
     * 获取当前用户是被联合人的地块的联合竞买记录
     * @param userName 被联合人姓名，是姓名不是用户名
     * @param resourceId 地块Id
     * @return
     */
    TransUserUnion getResourceTransUserUnionByUserName(String userName,String resourceId);
}
