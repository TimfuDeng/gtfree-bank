package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransResourceInfo;
import cn.gtmap.landsale.service.TransResourceInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jibo on 2015/5/18.
 */
public class TransResourceInfoServiceImpl extends HibernateRepo<TransResourceInfo, String> implements TransResourceInfoService {

    @Override
    @Transactional(readOnly = true)
    public TransResourceInfo getTransResourceInfo(String infoId) {
        return get(infoId);
    }

    @Override
    @Transactional(readOnly = true)
    public TransResourceInfo getTransResourceInfoByResourceId(String resourceId) {
        return getByNaturalId("resourceId",resourceId);
    }

    @Override
    @Transactional
    public TransResourceInfo saveTransResourceInfo(TransResourceInfo transResourceInfo) {
        return saveOrUpdate(transResourceInfo);
    }
}
