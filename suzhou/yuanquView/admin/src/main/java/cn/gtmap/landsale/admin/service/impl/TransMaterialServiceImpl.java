package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransMaterial;
import cn.gtmap.landsale.service.TransMaterialService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by u on 2016/2/29.
 */
public class TransMaterialServiceImpl extends HibernateRepo<TransMaterial, String> implements TransMaterialService {
    @Override
    @Transactional(readOnly = true)
    public List<TransMaterial> getMaterialsByRegionCode(String regionCode) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(regionCode)){
            criterionList.add(Restrictions.eq("regionCode",regionCode));
        }
        return list(criteria(criterionList));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransMaterial> getMaterialsBymaterialType(String regionCode,String materialType) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(materialType)){
            criterionList.add(Restrictions.eq("materialType",materialType));
        }
        if(StringUtils.isNotBlank(regionCode)){
            criterionList.add(Restrictions.eq("regionCode",regionCode));
        }
        return list(criteria(criterionList));
    }

    @Override
    @Transactional(readOnly = true)
    public TransMaterial getMaterialsById(String materialId) {

        return get(materialId);
    }

    @Override
    @Transactional
    public TransMaterial save(TransMaterial transMaterial) {
        return saveOrUpdate(transMaterial);
    }
}
