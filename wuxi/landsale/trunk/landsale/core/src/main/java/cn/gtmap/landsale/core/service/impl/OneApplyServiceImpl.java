package cn.gtmap.landsale.core.service.impl;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.OneApply;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.core.service.OneApplyService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 一次报价流程Service
 * @author zsj
 * @version v1.0, 2017/12/8
 */
@Service
public class OneApplyServiceImpl extends HibernateRepo<OneApply,String> implements OneApplyService {

    /**
     * 保存一次报价流程
     * @param oneApply
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<OneApply> saveOneApply(OneApply oneApply) {
        return new ResponseMessage(true, saveOrUpdate(oneApply));
    }

    /**
     * 根据报价设置信息Id 用户编号查找 报价流程
     * @param targetId
     * @param transUserId
     * @return
     */
    @Override
    @Transactional
    public OneApply getOnApply(String targetId, String transUserId) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(targetId)&& StringUtils.isNotBlank(transUserId)){
            list.add(Restrictions.eq("targetId", targetId));
            list.add(Restrictions.eq("transUserId", transUserId));
            return get(criteria(list));
        }
        return null;
    }

    /**
     * 获取报价流程列表
     * @param transUserId
     * @param page
     * @return
     */
    @Override
    @Transactional
    public Page<OneApply> findOneApply(String transUserId, Pageable page) {
        List<Criterion> list= Lists.newArrayList();
        if (StringUtils.isNotBlank(transUserId)){
            list.add(Restrictions.eq("transUserId", transUserId));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),page);
    }

}
