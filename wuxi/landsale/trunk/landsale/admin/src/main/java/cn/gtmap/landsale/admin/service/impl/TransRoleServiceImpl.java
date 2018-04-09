package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.register.TransUserClient;
import cn.gtmap.landsale.admin.service.TransRoleMenuService;
import cn.gtmap.landsale.admin.service.TransRoleOrganizeService;
import cn.gtmap.landsale.admin.service.TransRoleRegionOperationService;
import cn.gtmap.landsale.admin.service.TransRoleService;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Service
public class TransRoleServiceImpl extends HibernateRepo<TransRole, String> implements TransRoleService {

    @Autowired
    TransRoleMenuService transRoleMenuService;

    @Autowired
    TransRoleOrganizeService transRoleOrganizeService;

    @Autowired
    TransUserClient transUserClient;

    @Autowired
    TransRoleRegionOperationService transRoleRegionOperationService;

    /**
     * 获取角色列表 分页
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransRole> getTransRolePage(String roleName, Pageable request) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT * FROM (");
        sql.append("SELECT TR.* FROM TRANS_ROLE TR ");
        sql.append(" LEFT JOIN TRANS_ROLE_ORGANIZE TRO ON TR.ROLE_ID = TRO.ROLE_ID ");
        sql.append(" LEFT JOIN TRANS_ORGANIZE TOR ON TRO.ORGANIZE_ID = TOR.ORGANIZE_ID ");
        sql.append(" LEFT JOIN TRANS_ORGANIZE_REGION TORE ON TOR.ORGANIZE_ID = TORE.ORGANIZE_ID ");
        sql.append(" LEFT JOIN TRANS_REGION TRE ON TORE.REGION_CODE = TRE.REGION_CODE WHERE 1=1 ");
        if (StringUtils.isNotBlank(roleName)) {
            sql.append(" AND TR.ROLE_NAME LIKE '%" + roleName +"%'");
        }
        if (!SecUtil.isAdmin()) {
            sql.append(" AND TRE.REGION_CODE IN (" + SecUtil.getLoginUserRegionCodes() + ")");
        }
        sql.append(" ) ORDER BY CREATE_TIME DESC ");
        return findBySql(sql.toString(), request);
    }

    /**
     * 获取角色
     * @return
     */
    @Override
    public List<TransRole> getTransRole() {
        return list();
    }

    /**
     * 通过角色获取行政区
     * @param regionCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRole> getTransRoleByRegion(String regionCode) {
        Map<String, Object> params = Maps.newHashMap();
        StringBuffer hql = new StringBuffer();
        hql.append("select distinct tr from TransRole tr, TransRoleOrganize tro, TransOrganize tor, TransOrganizeRegion tore, TransRegion tre ");
        hql.append(" where tr.roleId = tro.roleId and tro.organizeId = tor.organizeId and tor.organizeId = tore.organizeId and tore.regionCode = tre.regionCode ");
        hql.append(" and tre.regionCode in:regionCode ");
        params.put("regionCode", regionCode.split(","));
        Query query = hql(hql.toString(), params);
        return query.list();
    }

    /**
     * 获取角色
     * @return
     */

    @Override
    @Transactional(readOnly = true)
    public TransRole getTransRoleById(String roleId) {
        return get(roleId);
    }

    /**
     * 通过用户 获取角色
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransRole getTransRoleByUserId(String userId) {
        Map<String, Object> params = Maps.newHashMap();
        String hql = "select tr from TransUser tu, TransUserRole tur, TransRole tr where tu.userId = tur.userId and tur.roleId = tr.roleId and tu.userId =:userId";
        params.put("userId", userId);
        Query query = hql(hql, params);
        return (TransRole) query.uniqueResult();
    }

    /**
     * 添加角色
     * @param transRole
     * @param organizeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransRole addTransRole(TransRole transRole, String organizeId) {
        transRole = save(transRole);
        // 添加角色组织关系
        TransRoleOrganize transRoleOrganize = new TransRoleOrganize();
        transRoleOrganize.setOrganizeId(organizeId);
        transRoleOrganize.setRoleId(transRole.getRoleId());
        transRoleOrganizeService.saveTransRoleOrganize(transRoleOrganize);
        return transRole;
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteTransRole(String roleId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        // 查询角色下是否存在用户
        List<TransUser> transUserList = transUserClient.getTransUserListByRole(roleId);
        if (transUserList != null && transUserList.size() > 0) {
            resultMap.put("result", false);
            resultMap.put("msg", "该角色下存在用户,无法删除角色！");
            return resultMap;
        }
        // TODO 删除roleLandUse
        // 删除roleOrganize
        List<TransRoleOrganize> transRoleOrganizeList = transRoleOrganizeService.findTransRoleOrganizeByRoleIdOrOrganizeId(roleId, null);
        if (transRoleOrganizeList != null && transRoleOrganizeList.size() > 0) {
            transRoleOrganizeService.deleteTransRoleOrganize(transRoleOrganizeList);
        }
        // 删除角色菜单表
        List<TransRoleMenu> transRoleMenuList = transRoleMenuService.findTransRoleMenuByRoleId(roleId);
        if (transRoleMenuList != null && transRoleMenuList.size() > 0) {
            transRoleMenuService.deleteTransRoleMenu(transRoleMenuList);
        }
        // 删除角色表
        deleteById(roleId);
        resultMap.put("result", true);
        resultMap.put("msg", "删除成功！");
        return resultMap;
    }
    /**
     * 修改角色
     * @param transRole
     * @param organizeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransRole updateTransRole(TransRole transRole, String organizeId) {
        // 根据角色 查找原有角色组织关系
        List<TransRoleOrganize> transRoleOrganizeList = transRoleOrganizeService.findTransRoleOrganizeByRoleIdOrOrganizeId(transRole.getRoleId(), null);
        if (transRoleOrganizeList != null && transRoleOrganizeList.size() > 0) {
            transRoleOrganizeService.deleteTransRoleOrganize(transRoleOrganizeList);
        }
        // 修改角色
        transRole = saveOrUpdate(transRole);
        // 添加新 角色组织关系
        TransRoleOrganize transRoleOrganize = new TransRoleOrganize();
        transRoleOrganize.setRoleId(transRole.getRoleId());
        transRoleOrganize.setOrganizeId(organizeId);
        transRoleOrganizeService.saveTransRoleOrganize(transRoleOrganize);
        return transRole;
    }

    /**
     * 角色授权
     * @param menuIds
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> grantRole(String menuIds, String roleId, String regionCodes) {
        Map<String, Object> resultMap = Maps.newHashMap();
        // 根据角色 查询已有的菜单
        List<TransRoleMenu> transRoleMenuExists = transRoleMenuService.findTransRoleMenuByRoleId(roleId);
        // 删除已存在 菜单
        if (transRoleMenuExists != null && transRoleMenuExists.size() > 0) {
            transRoleMenuService.deleteTransRoleMenu(transRoleMenuExists);
        }
        if (StringUtils.isNotBlank(menuIds)) {
            // 保存新角色菜单关系
            List<TransRoleMenu> transRoleMenuList = Lists.newArrayList();
            for (String menuId : menuIds.split(",")) {
                TransRoleMenu transRoleMenu = new TransRoleMenu();
                transRoleMenu.setMenuId(menuId);
                transRoleMenu.setRoleId(roleId);
                transRoleMenuList.add(transRoleMenu);
            }
            transRoleMenuService.saveTransRoleMenu(transRoleMenuList);
        }
        // 根据角色 查询原有的角色操作行政区
        List<TransRoleRegionOperation> transRoleRegionOperationList = transRoleRegionOperationService.findTransRoleRegionOperationByRoleId(roleId);
        if (transRoleRegionOperationList != null && transRoleRegionOperationList.size() > 0) {
            transRoleRegionOperationService.deleteTransRoleRegionOperation(transRoleRegionOperationList);
        }
        // 插入新的 角色操作行政区关系 2008
        if (StringUtils.isNotBlank(regionCodes)) {
            List<TransRoleRegionOperation> addReloRegionOperationList = Lists.newArrayList();
            for (String regionCode : regionCodes.split(",")) {
                TransRoleRegionOperation transRoleRegionOperation = new TransRoleRegionOperation();
                transRoleRegionOperation.setRoleId(roleId);
                transRoleRegionOperation.setRegionCode(regionCode);
                addReloRegionOperationList.add(transRoleRegionOperation);
            }
            transRoleRegionOperationService.saveTransRoleRegionOperation(addReloRegionOperationList);
        }
        resultMap.put("result", true);
        resultMap.put("msg", "保存成功！");
        return resultMap;
    }
}
