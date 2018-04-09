package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceVerify;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.service.TransVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2015/10/9.
 */
public class TransVerifySerivceImpl extends HibernateRepo<TransResourceVerify, String> implements TransVerifyService {

    @Autowired
    TransResourceService transResourceService;
    @Override
    @Transactional
    public TransResourceVerify saveTransVerify(TransResourceVerify transResourceVerify) {
        return saveOrUpdate(transResourceVerify);
    }

    @Override
    @Transactional
    public void saveTransResourceVerify(TransResource transResource, TransResourceVerify transResourceVerify) {
        transResourceVerify= saveOrUpdate(transResourceVerify);
        transResource.setResourceVerifyId(transResourceVerify.getVerifyId());
        transResourceService.saveTransResource(transResource);

    }

    @Override
    @Transactional(readOnly = true)
    public TransResourceVerify getTransVerifyById(String verifyId) {
        return get(verifyId);
    }
}
