package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.SysExtend;
import cn.gtmap.landsale.service.SysExtendService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public class SysExtendServiceImpl extends HibernateRepo<SysExtend,String> implements SysExtendService {

    @Override
    @Transactional
    public void saveSysExtendList(List<SysExtend> sysExtend) {
       save(sysExtend);
    }

    @Override
    @Transactional
    public void saveSysExtend(SysExtend sysExtend) {
        save(sysExtend);
    }

    @Override
    @Transactional
    public SysExtend findSysExtend(String refId, String filedNo) {
        List<Criterion> criterions= Lists.newArrayList();
        if(StringUtils.isNotBlank(refId)&&StringUtils.isNotBlank(filedNo)){
            criterions.add(Restrictions.eq("refId",refId));
            criterions.add(Restrictions.eq("fieldNo",filedNo));
            return get(criteria(criterions));
        }
        return null;

    }
}
