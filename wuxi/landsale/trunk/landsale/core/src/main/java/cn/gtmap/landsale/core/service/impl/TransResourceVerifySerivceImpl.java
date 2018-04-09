package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceVerify;
import cn.gtmap.landsale.core.service.TransResourceService;
import cn.gtmap.landsale.core.service.TransResourceVerifyService;
import com.google.common.collect.Lists;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 成交审核
 * @author zsj
 * @version v1.0, 2017/12/23
 */
@Service
public class TransResourceVerifySerivceImpl extends HibernateRepo<TransResourceVerify, String> implements TransResourceVerifyService {

    @Autowired
    TransResourceService transResourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceVerify> saveTransVerify(TransResourceVerify transResourceVerify) {
        // 查找 之前最后一次的审核信息 改为历史信息
        TransResourceVerify lastTransVerify = getTransVerifyLastByResourceId(transResourceVerify.getResourceId());
        if (lastTransVerify != null) {
            lastTransVerify.setCurrentStatus(Constants.CURRENT_STATUS.HISTORY);
            merge(lastTransVerify);
        }
        return new ResponseMessage<>(true, save(transResourceVerify));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceVerify> updateTransVerify(TransResourceVerify transResourceVerify) {
        if (transResourceVerify != null) {
            return new ResponseMessage<>(true,saveOrUpdate(transResourceVerify));
        }
        return new ResponseMessage<>(false,"待更新对象不存在");
    }

    /**
     * 根据 ResourceId 获取地块成交审核信息
     * @param resourceId
     * @return
     */
    @Override
    public List<TransResourceVerify> getTransVerifyListByResourceId(String resourceId) {
        return list(criteria(Restrictions.eq("resourceId", resourceId)).addOrder(Order.asc("currentStatus")).addOrder(Order.desc("verifyTime")));
    }

    /**
     * 根据 ResourceId 获取地块 最新成交审核信息
     * @param resourceId
     * @return
     */
    @Override
    public TransResourceVerify getTransVerifyLastByResourceId(String resourceId) {
        List<Criterion> list = Lists.newArrayList();
        list.add(Restrictions.eq("resourceId", resourceId));
        list.add(Restrictions.eq("currentStatus", Constants.CURRENT_STATUS.CURRENT));
        return get(criteria(list));
    }

    /**
     * 根据id获取地块成交审核信息
     * @param verifyId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransResourceVerify getTransVerifyById(String verifyId) {
        return get(verifyId);
    }
}
