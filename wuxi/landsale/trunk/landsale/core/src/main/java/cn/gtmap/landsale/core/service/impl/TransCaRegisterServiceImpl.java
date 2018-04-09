package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransCaRegister;
import cn.gtmap.landsale.core.service.TransCaRegisterService;
import com.google.common.collect.Maps;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.Criterion;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import java.util.*;

/**
 * @author cxm
 * @version v1.0, 2017/9/29
 */
@Service
public class TransCaRegisterServiceImpl extends HibernateRepo<TransCaRegister, String> implements TransCaRegisterService {

    /**
     * 获取CA用户分页服务
     * @param contactUser 联系人
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransCaRegister> findRegisterUser(String contactUser, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(contactUser)) {
            criterionList.add(Restrictions.like("contactUser", contactUser, MatchMode.ANYWHERE));
        }
        return find(criteria(criterionList).addOrder(Order.desc("registerTime")), request);
    }

    /**
     * 保存(或更新)
     * @param transCaRegister
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransCaRegister> saveTransCaRegister(TransCaRegister transCaRegister) {
        saveOrUpdate(transCaRegister);
        return new ResponseMessage(true, transCaRegister);
    }

    /**
     * 根据注册id获取CA用户
     * @param registerId 注册id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransCaRegister> getTransCaRegisterByRegisterId(String registerId) {
        Map<String, Object> params = Maps.newHashMap();
        String hql = "select tcr from TransCaRegister tcr where tcr.registerId =:registerId";
        params.put("registerId", registerId);
        Query query = hql(hql, params);
        return query.list();
    }

    /**
     * 根据用户ID获取CA用户
     * @param userId 用户ID
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransCaRegister> getTransCaRegisterByUserId(String userId) {
        Map<String, Object> params = Maps.newHashMap();
        String hql = "select tcr from TransCaRegister tcr where tcr.userId =:userId";
        params.put("userId", userId);
        Query query = hql(hql, params);
        return query.list();
    }

    /**
     * 根据用户ID和行政区代码获取CA用户
     * @param userId 用户ID
     * @param regionCode  行政区代码
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransCaRegister> getCaRegisterByUserIdAndCode(String userId, String regionCode) {
        Map<String, Object> params = Maps.newHashMap();
        String hql = "select tcr from TransCaRegister tcr where tcr.userId =:userId and tcr.regionCode=:regionCode";
        params.put("userId", userId);
        params.put("regionCode",regionCode);
        Query query = hql(hql, params);
        return query.list();
    }

}
