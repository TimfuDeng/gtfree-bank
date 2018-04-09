package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransUserApplyInfo;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransUserApplyInfoService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 交易人员申请信息服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/5/25
 */
public class TransUserApplyInfoServiceImpl extends HibernateRepo<TransUserApplyInfo, String> implements TransUserApplyInfoService {
    @Autowired
    TransResourceApplyService transResourceApplyService;
    /**
     * 根据Id获取人员申请信息
     *
     * @param infoId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransUserApplyInfo getTransUserApplyInfo(String infoId) {
        if(StringUtils.isNotBlank(infoId)){
            return get(infoId);
        }else {
            return  null;
        }

    }

    /**
     * 根据用户Id获取人员申请信息
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransUserApplyInfo> getTransUserApplyInfoByUser(String userId) {
        return list(criteria(Restrictions.eq("userId", userId)));
    }

    /**
     * 保存人员申请信息
     *
     * @param transUserApplyInfo
     * @return
     */
    @Override
    @Transactional
    public TransUserApplyInfo saveTransUserApplyInfo(TransUserApplyInfo transUserApplyInfo) {
        if(StringUtils.isEmpty(transUserApplyInfo.getInfoId()))
            transUserApplyInfo.setInfoId(null);
        return saveOrUpdate(transUserApplyInfo);
    }

    @Override
    @Transactional
    public TransUserApplyInfo saveTransUserApplyInfoAndTransResourceApply(TransUserApplyInfo transUserApplyInfo, TransResourceApply transResourceApply) {
        if(StringUtils.isEmpty(transUserApplyInfo.getInfoId()))
            transUserApplyInfo.setInfoId(null);
        transUserApplyInfo=saveOrUpdate(transUserApplyInfo);
        transResourceApply.setInfoId(transUserApplyInfo.getInfoId());
        transResourceApplyService.saveTransResourceApply(transResourceApply);
        return transUserApplyInfo;
    }

    /**
     * 删除人员申请信息
     *
     * @param infoIds
     */
    @Override
    @Transactional
    public void deleteTransUserApplyInfo(Collection<String> infoIds) {
        deleteByIds(infoIds);
    }

    /**
     * 根据人员Id删除其申请信息
     *
     * @param userId
     */
    @Override
    @Transactional
    public void deleteTransUserApplyInfoByUser(String userId) {

    }
}
