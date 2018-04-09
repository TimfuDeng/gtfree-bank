package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransOrganizeRegion;
import cn.gtmap.landsale.core.service.TransOrganizeRegionService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织 行政区 关系ServiceImpl
 *
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Service
public class TransOrganizeRegionServiceImpl extends HibernateRepo<TransOrganizeRegion, String> implements TransOrganizeRegionService {

    /**
     * 获取组织行政区关系 列表服务
     *
     * @param organizeId 组织行政区关系Id
     * @param regionCode 行政区Code
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransOrganizeRegion> findTransOrganizeRegionByOrganizeOrRegion(String organizeId, String regionCode) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(organizeId)) {
            criterionList.add(Restrictions.eq("organizeId", organizeId));
        }
        if (StringUtils.isNotBlank(regionCode)) {
            criterionList.add(Restrictions.eq("regionCode", regionCode));
        }
        return list(criteria(criterionList));
    }

    /**
     * 根据组织行政区关系Id 获取组织行政区关系对象
     *
     * @param organizeRegionId 组织行政区关系Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransOrganizeRegion getTransOrganizeRegionById(String organizeRegionId) {
        return get(organizeRegionId);
    }

    /**
     * 保存组织行政区关系
     *
     * @param transOrganizeRegion 组织行政区关系对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransOrganizeRegion saveTransOrganizeRegion(TransOrganizeRegion transOrganizeRegion) {
        return save(transOrganizeRegion);
    }

    /**
     * 删除组织行政区关系
     *
     * @param transOrganizeRegion 组织行政区关系行
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransOrganizeRegion(TransOrganizeRegion transOrganizeRegion) {
        delete(transOrganizeRegion);
        return new ResponseMessage(true);
    }

    /**
     * 删除组织行政区关系
     *
     * @param transOrganizeRegionList 组织行政区关系
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransOrganizeRegion(List<TransOrganizeRegion> transOrganizeRegionList) {
        delete(transOrganizeRegionList);
        return new ResponseMessage(true);
    }

    /**
     * 修改组织行政区关系
     *
     * @param transOrganizeRegion 组织行政区关系
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransOrganizeRegion updateTransOrganizeRegion(TransOrganizeRegion transOrganizeRegion) {
        return merge(transOrganizeRegion);
    }
}
