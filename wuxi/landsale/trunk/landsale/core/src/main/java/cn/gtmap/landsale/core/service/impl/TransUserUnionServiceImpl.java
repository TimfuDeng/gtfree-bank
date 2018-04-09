package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransUserUnion;
import cn.gtmap.landsale.core.service.TransUserUnionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 联合竞买服务
 * @author zsj
 * @version v1.0, 2017/11/27
 */
@Service
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
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(userName)) {
            criterionList.add(Restrictions.eq("userName", userName));
        }
        return find(criteria(criterionList).addOrder(Order.desc("unionId")), request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransUserUnion> saveTransUserUnion(TransUserUnion transUserUnion) {
        transUserUnion = saveOrUpdate(transUserUnion);
        return new ResponseMessage<TransUserUnion>(true, transUserUnion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransUserUnion(String unionId){
        deleteById(unionId);
        return new ResponseMessage(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransUserUnion> getResourceTransUserUnionByUserName(String userName, String resourceId) {
        String sql = "select t.* from trans_user_union t left join trans_resource_apply t1 on t.apply_id=t1.apply_id where t.user_name=:userName and t1.resource_id=:resourceId and t.agree = 1";
        Map<String,Object> mapParam= Maps.newHashMap();
        mapParam.put("userName", userName);
        mapParam.put("resourceId", resourceId);
        return entitySql(sql, mapParam).list();
    }
}
