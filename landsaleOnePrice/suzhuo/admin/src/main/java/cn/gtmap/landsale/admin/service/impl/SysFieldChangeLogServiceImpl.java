package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.SysFieldChangeLog;
import cn.gtmap.landsale.service.SysFieldChangeLogService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by trr on 2016/8/12.
 */
public class SysFieldChangeLogServiceImpl extends HibernateRepo<SysFieldChangeLog,String> implements SysFieldChangeLogService {

    @Override
    @Transactional(readOnly = true)
    public SysFieldChangeLog getSysFieldChangeLogByRefId(String refId) {
        List<Criterion> list= Lists.newArrayList();
        if (StringUtils.isNotBlank(refId)){
            list.add(Restrictions.eq("refId",refId));
            list.add(Restrictions.eq("fieldName","is_suspend"));
            list.add(Restrictions.eq("refTableName","trans_target"));
            list.add(Restrictions.eq("newValue","1"));
            List<SysFieldChangeLog> sysFieldChangeLogs =list(criteria(list).addOrder(Order.desc("changeDate")));
            if (null!=sysFieldChangeLogs&&sysFieldChangeLogs.size()>0){
                return sysFieldChangeLogs.get(0);
            }
        }
        return null;
    }
}
