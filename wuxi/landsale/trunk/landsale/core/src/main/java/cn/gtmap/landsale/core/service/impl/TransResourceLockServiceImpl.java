package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.TransResourceLock;
import cn.gtmap.landsale.core.service.TransResourceLockService;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by liushaoshuai on 2017/7/21.
 */
@Service
public class TransResourceLockServiceImpl extends HibernateRepo<TransResourceLock,String> implements TransResourceLockService {

    @Override
    @Transactional
    public TransResourceLock getResourceLock4Update(String resourceId) {
        StringBuilder sql = new StringBuilder();
        Map<String,Object> mapParam= Maps.newHashMap();
        sql.append("select t.* from trans_resource_lock t where 1=1");
        if (StringUtils.isNotBlank(resourceId)){
            sql.append(" and t.resource_id=:resourceId");
            mapParam.put("resourceId",resourceId);
        }
        sql.append(" for update ");
        List<TransResourceLock> transResourceOfferLockList =  sql(sql.toString(),mapParam).addEntity(TransResourceLock.class).list();
        if (transResourceOfferLockList.size()>0){
            return transResourceOfferLockList.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransResourceLock save(TransResourceLock transResourceLock) {
        return saveOrUpdate(transResourceLock);
    }
}
