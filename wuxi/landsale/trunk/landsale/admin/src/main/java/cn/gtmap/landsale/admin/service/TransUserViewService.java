package cn.gtmap.landsale.admin.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.TransUserView;

/**
 * 用户服务
 * @author zsj
 * @version v1.0, 2017/9/19
 */
public interface TransUserViewService {

    /**
     * 获取用户分页服务
     * @param viewName 用户名
     * @param request 分页请求
     * @param regionCodes 行政区代码
     * @param userType 用户类型
     * @return
     */
    Page<TransUserView> findTransUserPage(String viewName, Integer userType, String regionCodes, Pageable request);


    /**
     * 根据用户Id 获取用户信息
     * @param userId
     * @return
     */
    TransUserView getTransUserViewById(String userId);
}
