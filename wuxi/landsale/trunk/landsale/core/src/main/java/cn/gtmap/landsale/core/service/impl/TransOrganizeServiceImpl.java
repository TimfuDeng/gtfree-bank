package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganize;
import cn.gtmap.landsale.common.model.TransOrganizeRegion;
import cn.gtmap.landsale.core.service.TransOrganizeRegionService;
import cn.gtmap.landsale.core.service.TransOrganizeService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 组织ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Service
public class TransOrganizeServiceImpl extends HibernateRepo<TransOrganize, String> implements TransOrganizeService {

    @Autowired
    TransOrganizeRegionService transOrganizeRegionService;

    /**
     * 获取组织 分页服务
     * @param organizeName 组织名称
     * @param regionCodes 所属行政区
     * @param request 分页请求
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransOrganize> findTransOrganizePage(String organizeName, String regionCodes, Pageable request) {
        StringBuilder sql = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        // 此处有坑 select前不要加 空格！！！！
        sql.append("select distinct tor.* from trans_organize tor ");
        sql.append(" left join trans_organize_region tore on tor.organize_id = tore.organize_id ");
        sql.append(" left join trans_region tr on tore.region_code = tr.region_code where 1=1 ");
        if (StringUtils.isNotBlank(organizeName)) {
            sql.append(" and tor.organize_name like '%" + organizeName +"%'");
        }
        if (StringUtils.isNotBlank(regionCodes)) {
            sql.append(" and tr.region_code in (" + regionCodes + ")");
        }
        sql.append(" order by tor.create_time desc ");
        return findBySql(sql.toString(), request);
    }

    /**
     * 获取组织 列表服务
     * @param organizeName 组织名称
     * @param regionCode 组织代码
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransOrganize> findTransOrganizeList(String organizeName, String regionCode) {
        StringBuilder sql = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sql.append("SELECT DISTINCT * FROM( ");
        sql.append("SELECT TOR.* FROM TRANS_ORGANIZE TOR ");
        sql.append(" LEFT JOIN TRANS_ORGANIZE_REGION TORE ON TOR.ORGANIZE_ID = TORE.ORGANIZE_ID ");
        sql.append(" LEFT JOIN TRANS_REGION TR ON TORE.REGION_CODE = TR.REGION_CODE WHERE 1=1 ");
        if (StringUtils.isNotBlank(organizeName)) {
            sql.append(" AND TOR.ORGANIZE_NAME LIKE :organizeName ");
            params.put("organizeName", "%" + organizeName + "%");
        }
        if (StringUtils.isNotBlank(regionCode)) {
            sql.append(" AND TR.REGION_CODE LIKE :regionCode ");
            params.put("regionCode", regionCode + "%");
        }
        sql.append(" ORDER BY TR.REGION_CODE) ");
        SQLQuery sqlQuery = entitySql(sql.toString(), params);
        return sqlQuery.list();
    }

    /**
     * 通过角色 获取组织 列表服务
     * @param roleId 角色编号
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransOrganize> findTransOrganizeByRole(String roleId) {
        Map<String, Object> params = Maps.newHashMap();
        String hql = "select tor from TransRole tr, TransRoleOrganize tro, TransOrganize tor where tr.roleId = tro.roleId and tro.organizeId = tor.organizeId and tr.roleId =:roleId";
        params.put("roleId", roleId);
        Query query = hql(hql, params);
        return query.list();
    }

    /**
     * 根据组织Id 获取组织对象
     * @param organizeId 组织Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransOrganize getTransOrganizeById(String organizeId) {
        return get(organizeId);
    }

    /**
     * 保存组织
     * @param transOrganize 组织对象
     * @param regionCodes 所属行政区
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransOrganize> saveTransOrganize(TransOrganize transOrganize, String regionCodes) {
        if (StringUtils.isEmpty(regionCodes)) {
            return new ResponseMessage(false, "所属行政区必须选择！");
        }
        transOrganize.setOrganizeId(null);
        transOrganize.setCreateTime(new Date());
        transOrganize = save(transOrganize);
        for (String regionCode : regionCodes.split(",")) {
            TransOrganizeRegion transOrganizeRegion = new TransOrganizeRegion();
            transOrganizeRegion.setOrganizeId(transOrganize.getOrganizeId());
            transOrganizeRegion.setRegionCode(regionCode);
            transOrganizeRegionService.saveTransOrganizeRegion(transOrganizeRegion);
        }
        return new ResponseMessage(true, "操作成功！", transOrganize);
    }

    /**
     * 删除组织
     * @param transOrganize 组织
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransOrganize(TransOrganize transOrganize) {
        delete(get(transOrganize.getOrganizeId()));
        // 查找 组织行政区 原有关系 删除
        List<TransOrganizeRegion> transOrganizeRegionList = transOrganizeRegionService.findTransOrganizeRegionByOrganizeOrRegion(transOrganize.getOrganizeId(), null);
        if (transOrganizeRegionList != null && transOrganizeRegionList.size() > 0) {
            transOrganizeRegionService.deleteTransOrganizeRegion(transOrganizeRegionList);
        }
        return new ResponseMessage(true, "操作成功！");
    }

    /**
     * 修改组织
     * @param transOrganize 组织对象
     * @param regionCodes 所属行政区
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransOrganize> updateTransOrganize(TransOrganize transOrganize, String regionCodes) {
        if (StringUtils.isEmpty(regionCodes)) {
            return new ResponseMessage(false, "所属行政区必须选择！");
        }
        transOrganize.setCreateTime(new Date());
        transOrganize = merge(transOrganize);
        // 查找原有关系 删除
        List<TransOrganizeRegion> transOrganizeRegionList = transOrganizeRegionService.findTransOrganizeRegionByOrganizeOrRegion(transOrganize.getOrganizeId(), null);
        if (transOrganizeRegionList != null && transOrganizeRegionList.size() > 0) {
            transOrganizeRegionService.deleteTransOrganizeRegion(transOrganizeRegionList);
        }
        // 插入 新关系
        for (String regionCode : regionCodes.split(",")) {
            TransOrganizeRegion transOrganizeRegion = new TransOrganizeRegion();
            transOrganizeRegion.setOrganizeId(transOrganize.getOrganizeId());
            transOrganizeRegion.setRegionCode(regionCode);
            transOrganizeRegionService.saveTransOrganizeRegion(transOrganizeRegion);
        }
        return new ResponseMessage(true, "操作成功！", transOrganize);
    }
}
