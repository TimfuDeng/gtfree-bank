package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.SysCakey;
import cn.gtmap.landsale.service.SysCakeyService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2016/8/10.
 */
public class SysCakeyServiceImpl extends HibernateRepo<SysCakey,String> implements SysCakeyService {
    @Override
    @Transactional
    public SysCakey getSysCakey(String cakey) {
        return StringUtils.isNotBlank(cakey)?get(criteria(Restrictions.eq("key", cakey))):null;
    }

    @Override
    @Transactional
    public SysCakey getSysCakeyByUserId(String userId) {
        return StringUtils.isNotBlank(userId)?get(criteria(Restrictions.eq("userId", userId))):null;
    }
}
