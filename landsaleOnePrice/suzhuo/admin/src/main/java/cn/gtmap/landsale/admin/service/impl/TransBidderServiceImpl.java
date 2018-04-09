package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransBidder;
import cn.gtmap.landsale.service.TransBidderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2016/7/19.
 */
public class TransBidderServiceImpl extends HibernateRepo<TransBidder,String> implements TransBidderService {
    @Override
    @Transactional(readOnly = true)
    public TransBidder getTransBidder(String id) {
        return get(id);
    }
}
