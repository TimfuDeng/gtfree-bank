package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransNews;
import cn.gtmap.landsale.model.TransResourceSon;
import cn.gtmap.landsale.service.TransResourceSonService;
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
 * Created by trr on 2015/12/18.
 */
public class TransResourceSonServiceImpl extends HibernateRepo<TransResourceSon,String> implements TransResourceSonService{
    /**
     * 查询列表
     *
     * @param title   地块多用途标题
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransResourceSon> findTransResourceSon(String title, Pageable request) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("sonLandUse", title, MatchMode.ANYWHERE));
        }
        Page<TransResourceSon> list=find(criteria(criterionList).addOrder(Order.desc("sonId")),request);
        return list;

    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<TransResourceSon> getTransResourceSonList(String resourceId) {
//        List<Criterion> criterionList= Lists.newArrayList();
//        if(StringUtils.isNotBlank(resourceId)){
//            criterionList.add(Restrictions.eq("resourceId", resourceId));
//        }
//        List<TransResourceSon> list=list(criteria(criterionList).addOrder(Order.desc("zdCode")));
//        return list;
//    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceSon> getTransResourceSonList(String resourceId) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        List<TransResourceSon> list=list(criteria(criterionList).addOrder(Order.desc("zdCode")));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransResourceSon> getTransResourceSons(String zdCode, String resourceId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        if(StringUtils.isNotBlank(zdCode)){
            criterionList.add(Restrictions.like("zdCode", zdCode, MatchMode.ANYWHERE));
        }
        List<TransResourceSon> list=list(criteria(criterionList).addOrder(Order.desc("zdCode")));
        return list;
    }

    /**
     * 保存地块多用途
     *
     * @param transResourceSon
     * @return
     */
    @Override
    @Transactional
    public TransResourceSon saveTransResourceSon(TransResourceSon transResourceSon) {

        return saveOrUpdate(transResourceSon);
    }

    @Override
    @Transactional
    public void deleteTransResourceSon(Collection<String> resourceSonIds) {
        deleteByIds(resourceSonIds);

    }

    @Override
    @Transactional
    public void deleteTransResourceSonByResourceId(String resourceId) {
         hql("delete from TransResourceSon t where t.resourceId='"+resourceId+"'").executeUpdate();
    }

    @Override
    @Transactional
    public Collection<String> getTransResourceIdsByZdCode(String zdCode) {
        return sql("select distinct t.resource_id from trans_resource_son t where t.zd_code like'%"+zdCode+"%'").list();
    }

    @Override
    @Transactional(readOnly = true)
    public TransResourceSon getTransResourceSon(String resourceSonIds) {
        return get(resourceSonIds);
    }
}
