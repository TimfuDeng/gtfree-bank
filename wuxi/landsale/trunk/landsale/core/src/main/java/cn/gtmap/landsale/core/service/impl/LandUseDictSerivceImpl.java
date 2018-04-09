package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.LandUseDict;
import cn.gtmap.landsale.core.service.LandUseDictSerivce;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Created by liushaoshuai on 2017/8/24.
 */
@Service
public class LandUseDictSerivceImpl extends HibernateRepo<LandUseDict,String>
        implements LandUseDictSerivce {

    /**
     * @param code
     * @return
     * @annotation 获取字典表数据对象
     * @author liushaoshuai【liushaoshuai@gtmap.cn】
     * @date 2017/8/24 14:26
     */
    @Override
    @Transactional(readOnly = true)
    public LandUseDict getLandUseDict(String code) {
        return get(criteria(Restrictions.eq("code", code)));
    }

    /**
     * @return
     * @annotation 获取土地用途字典表数据
     * @author liushaoshuai【liushaoshuai@gtmap.cn】
     * @date 2017/8/24 14:25
     */
    @Override
    @Transactional(readOnly = true)
    public List<LandUseDict> getLandUseDictList() {
        Criteria criteria = criteria();
        criteria.addOrder(Order.asc("grade")).addOrder(Order.asc("code"));
        return list(criteria);
    }
}