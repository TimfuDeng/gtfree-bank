package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.OneParam;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OneParamService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by trr on 2016/8/11.
 */
@Service
public class OneParamServiceImpl extends HibernateRepo<OneParam,String> implements OneParamService {

    @Override
    @Transactional
    public OneParam getOneParam(String id) {
        return get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<OneParam> saveOrUpdateOneParam(OneParam oneParam) {
        return new ResponseMessage(true,saveOrUpdate(oneParam));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OneParam> findOneParam(Pageable request) {
        return find(request);
    }

    @Override
    @Transactional(readOnly = true)
    public OneParam getOneParamByRegionCode(String regionCode) {
        return StringUtils.isNotBlank(regionCode) ? get(criteria(Restrictions.eq("regionCode", regionCode))): null;
    }

}
