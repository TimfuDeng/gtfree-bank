package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.register.TransRegionClient;
import cn.gtmap.landsale.admin.service.TransRegionService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;
import cn.gtmap.landsale.common.model.TransRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政区ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/7
 */
@Service
public class TransRegionServiceImpl extends HibernateRepo<TransRole, String> implements TransRegionService {


    @Autowired
    TransRegionClient transRegionClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteAddRegion(TransRegion transRegion, String oldRegionCode) {
        // 删除原行政区
        TransRegion transRegionDel = transRegionClient.getTransRegionByRegionCode(oldRegionCode);
        ResponseMessage delResponse = transRegionClient.deleteTransRegion(transRegionDel);
        if (delResponse.getFlag()) {
            return transRegionClient.updateTransRegion(transRegion);
        } else {
            return delResponse;
        }
    }
}
