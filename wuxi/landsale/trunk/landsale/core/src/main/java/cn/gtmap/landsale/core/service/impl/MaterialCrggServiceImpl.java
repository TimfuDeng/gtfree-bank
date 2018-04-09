package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.MaterialCrgg;
import cn.gtmap.landsale.core.service.MaterialCrggService;
import com.google.common.collect.Maps;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 出让公告 材料 关系ServiceImpl
 * @author zsj
 * @version v1.0, 2017/10/23
 */
@Service
public class MaterialCrggServiceImpl extends HibernateRepo<MaterialCrgg,String> implements MaterialCrggService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MaterialCrgg saveMaterialCrgg(MaterialCrgg materialCrgg) {
        return saveOrUpdate(materialCrgg);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialCrgg getMaterialById(String tmcId) {
        return get(tmcId);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialCrgg getMaterialByMaterialId(String materialId) {
        Map<String,Object> map = Maps.newHashMap();
        String hql = "from MaterialCrgg where materialId =:materialId";
        map.put("materialId",materialId);
        Query query = hql(hql, map);
        return (MaterialCrgg)query.list().get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCrgg> getMaterialCrggByCrggId(String crggId) {
        Map<String,Object> map = Maps.newHashMap();
        String hql = "from MaterialCrgg where crggId =:crggId";
        map.put("crggId",crggId);
        Query query = hql(hql,map);
        return query.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaterialCrggList(String crggId) {
        List<MaterialCrgg> materialCrggList= getMaterialCrggByCrggId(crggId);
        if(materialCrggList.size()>0){
            delete(materialCrggList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaterialCrgg(MaterialCrgg materialCrgg) {
        delete(materialCrgg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaterialByIds(Collection<String> lawIds) {
        deleteByIds(lawIds);
    }
}
