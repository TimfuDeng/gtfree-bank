package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransDept;
import cn.gtmap.landsale.service.TransDeptService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2015/10/9.
 */
public class TransDeptServiceImpl extends HibernateRepo<TransDept,String> implements TransDeptService {

    @Override
    @Transactional(readOnly = true)
    public Page<TransDept> findTransDept(String title, Pageable request) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("deptName",title, MatchMode.ANYWHERE));
        }
        Page<TransDept> transDepts=find(criteria(criterionList).addOrder(Order.desc("deptNo")),request);
        return transDepts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransDept> findAllTransDept() {
        List<TransDept> allTransDept= list();
        return allTransDept;
    }

    @Override
    @Transactional
    public TransDept saveTransDept(TransDept transDept) {

        return saveOrUpdate(transDept);
    }

    @Override
    @Transactional
    public void deleteTransDept(Collection<String> deptIds) {
            deleteByIds(deptIds);
    }

    @Override
    @Transactional
    public TransDept getTransDept(String deptId) {
        TransDept transDept=null;
        transDept=get(deptId);
        if(null!=transDept){
            if(1==transDept.getDeptType()){
                transDept.setDeptTypeName(Constants.GTJ);
            }else if(2==transDept.getDeptType()){
                transDept.setDeptTypeName(Constants.TDCBZX);
            }else if(3==transDept.getDeptType()){
                transDept.setDeptTypeName(Constants.JRJG);
            }else if(4==transDept.getDeptType()){
                transDept.setDeptTypeName(Constants.JGBM);
            }else if(5==transDept.getDeptType()){
                transDept.setDeptTypeName(Constants.QTJG);
            }

        }
        return transDept;
    }
}
