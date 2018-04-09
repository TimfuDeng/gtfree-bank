package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransInteractCommunion;
import cn.gtmap.landsale.service.TransInteractCommunionService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by www on 2015/9/29.
 */
public class TransInteractCommunionServiceImpl extends HibernateRepo<TransInteractCommunion,String> implements TransInteractCommunionService {

    /**
     *
     * @param communionId  TransInteractCommunion的id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransInteractCommunion getTransInteractCommunionById(String communionId) {
        return get(communionId);
    }

    @Override
    @Transactional
    public String getNextPublicNo() {
        SQLQuery query= sql("select trans_public_no.nextval from dual");
        Object result= query.uniqueResult();
        String publicNo = "000000"+ result.toString();

        return publicNo;
    }

    /**
     *
     * @param communionIds 多个id集合
     */
    @Override
    @Transactional
    public void deleteTransInteractCommunion(Collection<String> communionIds) {
        deleteByIds(communionIds);
    }

    /**
     *
     * @param transInteractCommunion 互相交流对象
     * @return
     */
    @Override
    @Transactional
    public TransInteractCommunion saveTransInteractCommunion(TransInteractCommunion transInteractCommunion) {
        return saveOrUpdate(transInteractCommunion);
    }

    /**
     *
     * @param title 来信标题
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransInteractCommunion> findTransInteractCommunionPage(String title, Pageable request) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            criterionList.add(Restrictions.like("publicTitle",title, MatchMode.ANYWHERE));
        }
        Page<TransInteractCommunion> list=find(criteria(criterionList).addOrder(Order.desc("publicTime")),request);

        return list;
    }

    /**
     * 获取已审核的交流对象
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransInteractCommunion> findApprovedComm(Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        criterionList.add(Restrictions.ne("replyStatus", Constants.COMM_UNVERFY));
        Page<TransInteractCommunion> list = find(criteria(criterionList).addOrder(Order.desc("publicTime")),request);
        return list;
    }
}
