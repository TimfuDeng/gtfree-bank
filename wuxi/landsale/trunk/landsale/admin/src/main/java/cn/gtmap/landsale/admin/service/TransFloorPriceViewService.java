package cn.gtmap.landsale.admin.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.TransFloorPriceView;

/**
 * 底价权限前台展示
 * @author cxm
 * @version v1.0, 2017/11/08
 */
public interface TransFloorPriceViewService {

    /**
     * 获取用户分页服务
     * @param viewName 用户名
     * @param request 分页请求
     * @param regionCodes 行政区代码
     * @param userType
     * @return
     */
    Page<TransFloorPriceView> findFloorUserPage(String viewName, Integer userType, String regionCodes, Pageable request);

    /**
     * 根据用户Id 获取用户信息
     * @param userId
     * @return
     */
    TransFloorPriceView getFloorViewById(String userId);

}
