package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransRoleOrganizeService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRoleOrganize;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织 行政区 关系ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Service
public class TransRoleOrganizeServiceImpl extends HibernateRepo<TransRoleOrganize, String> implements TransRoleOrganizeService {



    /**
     * 获取角色组织关系 列表服务
     * @param roleId 角色Id
     * @param organizeId 组织Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRoleOrganize> findTransRoleOrganizeByRoleIdOrOrganizeId(String roleId, String organizeId) {
        List<Criterion> criteriaList = Lists.newArrayList();
        if (StringUtils.isNotBlank(roleId)) {
            criteriaList.add(Restrictions.eq("roleId", roleId));
        }
        if (StringUtils.isNotBlank(organizeId)) {
            criteriaList.add(Restrictions.eq("organizeId", organizeId));
        }
        return list(criteria(criteriaList));
    }

    /**
     * 根据角色组织关系Id 获取角色组织关系对象
     * @param roleOrganizeId 角色组织关系Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransRoleOrganize getTransRoleOrganizeById(String roleOrganizeId) {
        return get(roleOrganizeId);
    }

    /**
     * 保存角色组织关系
     * @param transRoleOrganize 角色组织关系对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransRoleOrganize saveTransRoleOrganize(TransRoleOrganize transRoleOrganize) {
        return save(transRoleOrganize);
    }

    /**
     * 删除角色组织关系
     * @param transRoleOrganize 角色组织关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransRoleOrganize(TransRoleOrganize transRoleOrganize) {
        delete(transRoleOrganize);
        return new ResponseMessage(true);
    }

    /**
     * 删除角色组织关系
     * @param transRoleOrganizeList 角色组织关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransRoleOrganize(List<TransRoleOrganize> transRoleOrganizeList) {
        delete(transRoleOrganizeList);
        return new ResponseMessage(true);
    }

    /**
     * 修改角色组织关系
     * @param transRoleOrganize 角色组织关系
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransRoleOrganize updateTransRoleOrganize(TransRoleOrganize transRoleOrganize) {
        return merge(transRoleOrganize);
    }
}
