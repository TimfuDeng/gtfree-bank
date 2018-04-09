package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransLaw;
import cn.gtmap.landsale.service.TransLawService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by trr on 2016/1/27.
 */
public class TransLawServiceImpl extends HibernateRepo<TransLaw, String> implements TransLawService {
    /**
     * 新增法律条例
     *
     * @param transLaw
     * @return
     */
    @Override
    @Transactional
    public TransLaw save(TransLaw transLaw) {
        transLaw.setUpdateTime(new Date());
        return saveOrUpdate(transLaw);
    }

    /**
     * 删除法律条例
     *
     * @param lawIds
     */
    @Override
    @Transactional
    public void delete(Collection<String> lawIds) {
        deleteByIds(lawIds);
    }

    /**
     * 根据Id查询法律条例
     *
     * @param lawId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransLaw getById(String lawId) {
        return get(lawId);
    }

    /**
     * 分页获取法律条例对象
     *
     * @param title
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransLaw> findByPage(String title, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("title",title, MatchMode.ANYWHERE));
        }
        Page<TransLaw> transLawPage = find(criteria(criterionList).addOrder(Order.desc("updateTime")),request);
        return transLawPage;
    }

    /**
     * 查询已经发布的法律条例
     *
     * @param request
     * @param lawStatus
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransLaw> findTransNewsDeployed(Pageable request, int lawStatus) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.eq("lawStauts",lawStatus));
        Page<TransLaw> transLawPage = find(criteria(criterionList).addOrder(Order.desc("updateTime")),request);
        return transLawPage;
    }
}
