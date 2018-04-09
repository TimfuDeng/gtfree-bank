package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransRoleRegionOperationService;
import cn.gtmap.landsale.common.model.TransRoleRegionOperation;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色操作菜单ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/30
 */
@Service
public class TransRoleRegionOperationServiceImpl extends HibernateRepo<TransRoleRegionOperation, String> implements TransRoleRegionOperationService {

    /**
     * 获取角色操作菜单列表
     * @param roleId
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransRoleRegionOperation> findTransRoleRegionOperationByRoleId(String roleId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(roleId)) {
            criterionList.add(Restrictions.eq("roleId", roleId));
        }
        return list(criteria(criterionList));
    }

    /**
     * 保存角色操作菜单
     * @param transRoleRegionOperationList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TransRoleRegionOperation> saveTransRoleRegionOperation(List<TransRoleRegionOperation> transRoleRegionOperationList) {
        return save(transRoleRegionOperationList);
    }

    /**
     * 删除角色操作菜单
     * @param transRoleRegionOperationList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransRoleRegionOperation(List<TransRoleRegionOperation> transRoleRegionOperationList) {
        delete(transRoleRegionOperationList);
    }
}
