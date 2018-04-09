package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransFloorPriceViewService;
import cn.gtmap.landsale.common.model.TransFloorPriceView;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by M150237 on 2017-11-28.
 */
@Service
public class TransFloorPriceViewServiceImpl extends HibernateRepo<TransFloorPriceView, String> implements TransFloorPriceViewService {
    /**
     * 获取用户分页服务
     * @param viewName    用户名
     * @param userType
     * @param regionCodes 行政区代码
     * @param request     分页请求   @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransFloorPriceView> findFloorUserPage(String viewName, Integer userType, String regionCodes, Pageable request) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" TU.USER_ID, TU.STATUS, TU.VIEW_NAME, TU.CREATE_AT, TFP.TDYT_DICT_NAME, TFP.TDYT_DICT_CODE, TFP.FLOOR_PRICE_ID, ");
        sql.append(" TRE.REGION_NAME, TRE.REGION_CODE  FROM TRANS_USER TU ");
        sql.append(" LEFT JOIN TRANS_FLOOR_PRICE TFP ON TU.USER_ID = TFP.USER_ID ");
        sql.append(" LEFT JOIN TRANS_USER_ROLE TUR ON TU.USER_ID = TUR.USER_ID ");
        sql.append(" LEFT JOIN TRANS_ROLE TR ON TUR.ROLE_ID = TR.ROLE_ID ");
        sql.append(" LEFT JOIN TRANS_REGION TRE ON TFP.REGION_CODE = TRE.REGION_CODE WHERE 1=1 ");
        if (StringUtils.isNotBlank(viewName)) {
            sql.append(" AND TU.VIEW_NAME like '%" + viewName + "%'");
        }
        if (userType != null) {
            sql.append(" AND TU.TYPE ='" + userType+ "'");
        }
        if (StringUtils.isNotBlank(regionCodes)) {
            sql.append(" AND TU.REGION_CODE IN (" + regionCodes + ")");
        }
        if (!SecUtil.isAdmin()) {
            sql.append(" AND TRE.REGION_CODE IN (" + SecUtil.getLoginUserRegionCodes() + ")");
        }
        sql.append(" ORDER BY TU.CREATE_AT DESC");
        return findBySql(sql.toString(), request);
    }

    /**
     * 根据用户Id 获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransFloorPriceView getFloorViewById(String userId) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TU.USER_ID, TU.STATUS, TU.VIEW_NAME, TU.CREATE_AT, TFP.TDYT_DICT_NAME, TFP.TDYT_DICT_CODE, TFP.FLOOR_PRICE_ID, TRE.REGION_NAME, TRE.REGION_CODE  FROM TRANS_USER TU ");
        sql.append(" LEFT JOIN TRANS_FLOOR_PRICE TFP ON TU.USER_ID = TFP.USER_ID ");
        sql.append(" LEFT JOIN TRANS_USER_ROLE TUR ON TU.USER_ID = TUR.USER_ID ");
        sql.append(" LEFT JOIN TRANS_REGION TRE ON TFP.REGION_CODE = TRE.REGION_CODE ");
        sql.append(" LEFT JOIN TRANS_ROLE TR ON TUR.ROLE_ID = TR.ROLE_ID WHERE 1=1 ");
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" AND TU.USER_ID ='" + userId+ "'");
        }
        sql.append(" ORDER BY TU.CREATE_AT DESC");
        List<TransFloorPriceView> transUserViewList = sql(sql.toString(), params).addEntity(TransFloorPriceView.class).list();
        if (transUserViewList != null && transUserViewList.size() == 1) {
            return transUserViewList.get(0);
        }
        return null;
    }
}
