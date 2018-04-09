package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.MaterialCrgg;
import cn.gtmap.landsale.model.TransMaterial;
import cn.gtmap.landsale.service.MaterialCrggService;
import com.google.common.collect.Maps;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by u on 2016/2/29.
 */
public class MaterialCrggServiceImpl extends HibernateRepo<MaterialCrgg,String> implements MaterialCrggService {
    @Override
    @Transactional
    public void saveMaterialCrgg(MaterialCrgg materialCrgg) {
        saveOrUpdate(materialCrgg);
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
    @Transactional
    public void deleteMaterialCrggList(String crggId) {
        List<MaterialCrgg> materialCrggList= getMaterialCrggByCrggId(crggId);
        if(materialCrggList.size()>0){
            delete(materialCrggList);
        }
    }

    @Override
    @Transactional
    public void deleteMaterialCrgg(MaterialCrgg materialCrgg) {
        delete(materialCrgg);
    }

    @Override
    @Transactional
    public void deleteMaterialByIds(Collection<String> lawIds) {
        deleteByIds(lawIds);
    }
}
