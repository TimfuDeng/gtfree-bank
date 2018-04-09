package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.LandUseDict;
import cn.gtmap.landsale.service.LandUseDictSerivce;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liushaoshuai on 2017/8/24.
 */
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
        List<Criterion> list= Lists.newArrayList();
        if (StringUtils.isNotBlank(code)){
            list.add(Restrictions.eq("code", code));
            return get(criteria(list));
        }
        return null;

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
        return list();
    }
}
