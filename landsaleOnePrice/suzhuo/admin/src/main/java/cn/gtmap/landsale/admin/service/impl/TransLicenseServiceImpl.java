package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransLicense;
import cn.gtmap.landsale.service.TransLicenseService;
import com.google.common.collect.Lists;
import javassist.convert.Transformer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/12.
 */
public class TransLicenseServiceImpl extends HibernateRepo<TransLicense,String> implements TransLicenseService{
    @Override
    @Transactional
    public List<TransLicense> findTransLicenseListByNo(String no,String targetId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(no)){
            list.add(Restrictions.eq("name", no));
        }
        if(StringUtils.isNotBlank(targetId)){
            list.add(Restrictions.eq("targetId", targetId));
        }
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional
    public List<TransLicense> findTransLicenseListById(String targetId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(targetId)){
            list.add(Restrictions.eq("targetId", targetId));
        }
        list.add(Restrictions.eq("status", 4));
        return list(criteria(list).addOrder(Order.desc("createDate")));
    }

    @Override
    @Transactional
    public Page<TransLicense> findTransLicenseList(String title, Pageable request) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("name", title));
        }
        list.add(Restrictions.eq("status", 4));
        return find(criteria(list).addOrder(Order.desc("createDate")), request);
    }

    @Override
    @Transactional
    public List<TransLicense> findTransLicenseListInTargetId(Collection<String> targetIds) {
        List<Criterion> list= Lists.newArrayList();
        if(null!=targetIds&&targetIds.size()>0){
            list.add(Restrictions.in("targetId", targetIds));
        }
        list.add(Restrictions.eq("status", 4));
        return list(criteria(list).addOrder(Order.desc("createDate")));

    }

    @Override
    @Transactional
    public TransLicense saveTransLicense(TransLicense transLicense) {
        return saveOrUpdate(transLicense);
    }

    @Override
    @Transactional
    public TransLicense getTransLicenseByTargetIdSysUserId(String transTargetId, String sysUserId) {
        StringBuffer sb=new StringBuffer();
        sb.append("select t.* from TRANS_LICENSE t ")
                .append(" left join trans_target t1 on t.target_id=t1.id ")
                .append(" left join sys_user t2 on t.bidder_id=t2.ref_id where 1=1 ");
        if (StringUtils.isNotBlank(sysUserId)){
            sb.append(" and t2.id='"+sysUserId+"'");
        }else {
            return null;
        }
        if(StringUtils.isNotBlank(transTargetId)){
            sb.append(" and t1.id='"+transTargetId+"'");
        }else {
            return null;
        }
        Query sql=sql(sb.toString()).addEntity(TransLicense.class);
        List<TransLicense> transLicenseList = sql .list();
        if (null!=transLicenseList && transLicenseList.size()>0){
            return  transLicenseList.get(0);
        }

        return null;
    }
}
