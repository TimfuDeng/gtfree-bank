package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransUserUnion;
import cn.gtmap.landsale.service.TransUserUnionService;
import com.google.common.collect.Maps;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Jibo on 2015/5/25.
 */
public class TransUserUnionServiceImpl extends HibernateRepo<TransUserUnion, String> implements TransUserUnionService {

    @Override
    @Transactional(readOnly = true)
    public TransUserUnion getTransUserUnion(String unionId) {
        return get(unionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransUserUnion> findTransUserUnion(String applyId) {
        org.hibernate.Query query = hql("from TransUserUnion t where t.applyId=? ");
        query.setString(0,applyId);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransUserUnion> findTransUserUnionByUserName(String userName,Pageable request){
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("userName", userName);
        String hql = "from TransUserUnion t where t.userName=:userName  order by t.unionId desc";
        return findByHql(hql, mapParam, request);
    }

    @Override
    @Transactional
    public TransUserUnion saveTransUserUnion(TransUserUnion transUserUnion) {
        return saveOrUpdate(transUserUnion);
    }

    @Override
    @Transactional
    public void deleteTransUserUnion(String unionId){
        deleteById(unionId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransUserUnion getResourceTransUserUnionByUserName(String userName, String resourceId) {
        String sql = "select t.* from trans_user_union t left join trans_resource_apply t1 on t.apply_id=t1.apply_id where t.user_name=:userName and t1.resource_id=:resourceId";
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("userName", userName);
        mapParam.put("resourceId", resourceId);
        return (TransUserUnion)sql(sql, mapParam).addEntity(TransUserUnion.class).uniqueResult();
    }
}
