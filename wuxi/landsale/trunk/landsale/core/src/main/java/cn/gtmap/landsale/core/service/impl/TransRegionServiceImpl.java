package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.Tree;
import cn.gtmap.landsale.core.service.TransRegionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 行政区ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Service
public class TransRegionServiceImpl extends HibernateRepo<TransRegion, String> implements TransRegionService {

    /**
     * 获取行政区分页服务
     * @param regionCode 行政区编码
     * @param regionLevel 行政区级别
     * @param request 分页请求
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransRegion> findTransRegionByCodeOrLeave(String regionCode, Integer regionLevel, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(regionCode)) {
            criterionList.add(Restrictions.eq("regionCode", regionCode));
        }
        if (regionLevel != null) {
            criterionList.add(Restrictions.eq("regionLevel", regionLevel));
        }
        return find(criteria(criterionList), request);
    }

    /**
     * 获取行政区列表服务
     * @param regionCode 行政区编码 查询行政区及其子集
     * @param regionLevel 行政区级别
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRegion> findTransRegionByCodeOrLeave(String regionCode, Integer regionLevel) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(regionCode)) {
            criterionList.add(Restrictions.like("regionCode", regionCode, MatchMode.START));
        }
        if (regionLevel != null) {
            criterionList.add(Restrictions.eq("regionLevel", regionLevel));
        }
        return list(criteria(criterionList).addOrder(Order.asc("regionCode")));
    }

    /**
     * 根据所属组织 获取组织所属行政区列表服务
     * @param organizeId 获取组织编号
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRegion> findTransRegionByOrganize(String organizeId) {
        StringBuilder sql = new StringBuilder();
        Map<String,Object> params = Maps.newHashMap();
        sql.append("SELECT TR.* FROM TRANS_ORGANIZE TOR ");
        sql.append(" LEFT JOIN TRANS_ORGANIZE_REGION TORE ON TOR.ORGANIZE_ID = TORE.ORGANIZE_ID ");
        sql.append(" LEFT JOIN TRANS_REGION TR ON TORE.REGION_CODE = TR.REGION_CODE ");
        sql.append(" WHERE TOR.ORGANIZE_ID =:organizeId ");
        params.put("organizeId", organizeId);
        SQLQuery sqlQuery = entitySql(sql.toString(), params);
        SQLQuery sqlQ = sql(sql.toString(), params);
        List<TransRegion> regionList = Lists.newArrayList();
        regionList.addAll(((List<TransRegion>) sqlQuery.list()).stream().filter(transRegion -> transRegion != null).collect(Collectors.toList()));
        return regionList;
    }

    /**
     * 根据所属角色 获取组织所属行政区列表服务
     * @param roleId 角色编号
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRegion> findTransRegionByRole(String roleId) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuffer hql = new StringBuffer();
        hql.append("select tre from TransRole tr, TransRoleOrganize tro, TransOrganize tor, TransOrganizeRegion tore, TransRegion tre ");
        hql.append(" where tr.roleId = tro.roleId and tro.organizeId = tor.organizeId and tor.organizeId = tore.organizeId and tore.regionCode = tre.regionCode ");
        hql.append(" and tr.roleId =:roleId ");
        params.put("roleId", roleId);
        Query query = hql(hql.toString(), params);
        return query.list();
    }

    /**
     * 根据行政区Code获取行政区对象
     * @param regionCode 行政区Code
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransRegion getTransRegionByRegionCode(String regionCode) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(regionCode)) {
            criterionList.add(Restrictions.eq("regionCode", regionCode));
        }
        return get(criteria(criterionList));
    }

    /**
     * 根据角色操作行政区权限 获取行政区
     * @param roleId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRegion> findTransRegionByRoleRegionOperation(String roleId) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuffer hql = new StringBuffer();
        hql.append("select tr from TransRoleRegionOperation tro, TransRegion tr ");
        hql.append(" where tro.regionCode = tr.regionCode ");
        hql.append(" and tro.roleId =:roleId ");
        params.put("roleId", roleId);
        Query query = hql(hql.toString(), params);
        return query.list();
    }

    /**
     * 保存行政区
     * @param transRegion 行政区对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage saveTransRegion(TransRegion transRegion) {
        // 根据行政区 查询数据库中是否存在相同行政区
        TransRegion transRegionTemp = getTransRegionByRegionCode(transRegion.getRegionCode());
        if (transRegionTemp != null) {
            return new ResponseMessage(false, "行政区代码:" + transRegionTemp.getRegionCode() + "已存在！");
        }
        save(transRegion);
        return new ResponseMessage(true);
    }

    /**
     * 删除行政区
     * @param transRegion 行政区
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransRegion(TransRegion transRegion) {
        // 查询该行政区下 是否存在子行政区
        List<TransRegion> regionList = findTransRegionByCodeOrLeave(transRegion.getRegionCode(), null);
        if (regionList != null && regionList.size() > 1) {
            delete(regionList);
        }
        delete(merge(transRegion));
        return new ResponseMessage(true);
    }

    /**
     * 修改行政区
     * @param transRegion 行政区对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage updateTransRegion(TransRegion transRegion) {
        merge(transRegion);
//        saveOrUpdate(transRegion);
        return new ResponseMessage(true);
    }

    /**
     * 获取行政区Tree结果
     * @param regionCode 传入查询其子集
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Tree> getRegionTree(String regionCode) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(regionCode)) {
            if (regionCode.split(",").length > 1) {
                criterionList.add(Restrictions.in("regionCode", regionCode.split(",")));
            } else {
                criterionList.add(Restrictions.like("regionCode", regionCode, MatchMode.START));
            }
        }
        List<TransRegion> transRegionList = list(criteria(criterionList).addOrder(Order.asc("regionCode")));
        List<Tree> regionTree = Lists.newArrayList();
        for (TransRegion transRegion : transRegionList) {
            Tree tree = new Tree();
            tree.setId(transRegion.getRegionCode());
            tree.setName(transRegion.getRegionName() + "(" + transRegion.getRegionCode() + ")");
            tree.setRegionLevel(transRegion.getRegionLevel());
            tree.setDrag(true);
            tree.setDrop(true);
            if (transRegion.getRegionLevel() == 2) {
                tree.setpId(transRegion.getRegionCode().substring(0, 2));
            } else if (transRegion.getRegionLevel() == 3) {
                tree.setpId(transRegion.getRegionCode().substring(0, 4));
            } else {
                tree.setOpen(true);
            }
            regionTree.add(tree);
        }
        return regionTree;
    }

}
