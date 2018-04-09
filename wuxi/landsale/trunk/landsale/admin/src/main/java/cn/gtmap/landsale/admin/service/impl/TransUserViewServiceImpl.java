package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransUserViewService;
import cn.gtmap.landsale.common.model.TransCaRegister;
import cn.gtmap.landsale.common.model.TransUserView;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户服务
 * @author zsj
 * @version v1.0, 2017/9/18
 */
@Service
public class TransUserViewServiceImpl extends HibernateRepo<TransUserView, String> implements TransUserViewService {

    /**
     * 获取用户分页服务
     *
     * @param viewName
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransUserView> findTransUserPage(String viewName, Integer userType, String regionCodes, Pageable request) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT * FROM ( ");
        sql.append(" SELECT ");
        sql.append(" TU.USER_ID, TO_CHAR(TU.CA_CERTIFICATE) CA_CERTIFICATE, TU.CA_NAME, TU.CA_NOT_AFTER_TIME, TU.CA_NOT_BEFORE_TIME, ");
        sql.append(" TU.CA_THUMBPRINT, TU.CREATE_AT, TU.DESCRIPTION, TU.GENDER, TU.MOBILE, TU.PASSWORD, TU.PRIVILEGE, ");
        sql.append(" TU.REGION_CODE, TU.STATUS, TU.TYPE, TU.USER_CODE, TU.USER_NAME, TU.VIEW_NAME, TU.CERTIFICATE_ID, ");
        sql.append(" TR.ROLE_ID, TR.ROLE_NAME FROM TRANS_USER TU ");
        sql.append(" LEFT JOIN TRANS_USER_ROLE TUR ON TU.USER_ID = TUR.USER_ID ");
        sql.append(" LEFT JOIN TRANS_ROLE TR ON TUR.ROLE_ID = TR.ROLE_ID ");
        sql.append(" LEFT JOIN TRANS_ROLE_ORGANIZE TRO ON TR.ROLE_ID = TRO.ROLE_ID ");
        sql.append(" LEFT JOIN TRANS_ORGANIZE TOR ON TRO.ORGANIZE_ID = TOR.ORGANIZE_ID ");
        sql.append(" LEFT JOIN TRANS_ORGANIZE_REGION TORE ON TOR.ORGANIZE_ID = TORE.ORGANIZE_ID ");
        sql.append(" LEFT JOIN TRANS_REGION TRE ON TORE.REGION_CODE = TRE.REGION_CODE WHERE 1=1 ");
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
        sql.append(") ORDER BY CREATE_AT DESC");
        return findBySql(sql.toString(), request);
//        return findBySql(sql.toString(), params, request);
    }



    /**
     * 根据用户Id 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public TransUserView getTransUserViewById(String userId) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT TU.*, TR.ROLE_ID, TR.ROLE_NAME FROM TRANS_USER TU ");
        sql.append(" LEFT JOIN TRANS_USER_ROLE TUR ON TU.USER_ID = TUR.USER_ID ");
        sql.append(" LEFT JOIN TRANS_ROLE TR ON TUR.ROLE_ID = TR.ROLE_ID WHERE 1=1 ");
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" AND TU.USER_ID =:userId");
            params.put("userId", userId);
        }
        sql.append(" ORDER BY TU.CREATE_AT DESC");
        List<TransUserView> transUserViewList = sql(sql.toString(), params).addEntity(TransUserView.class).list();
        if (transUserViewList != null && transUserViewList.size() == 1) {
            return transUserViewList.get(0);
        }
        return null;
    }

}
