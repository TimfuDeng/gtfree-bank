package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.TransUserApplyInfo;
import cn.gtmap.landsale.core.service.TransUserApplyInfoService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户申请地块 扩展联系人信息
 * @author zsj
 * @version v1.0, 2017/11/28
 */
@Service
public class TransUserApplyInfoServiceImpl extends HibernateRepo<TransUserApplyInfo, String> implements TransUserApplyInfoService {
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
     * 根据 地块申请Id 用户Id获取人员申请信息
     * @param applyId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransUserApplyInfo getTransUserApplyInfoByApplyId(String applyId) {
        return get(criteria(Restrictions.eq("applyId", applyId)));
    }

    /**
     * 保存人员申请信息
     *
     * @param transUserApplyInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransUserApplyInfo saveTransUserApplyInfo(TransUserApplyInfo transUserApplyInfo) {
        if(StringUtils.isEmpty(transUserApplyInfo.getInfoId())) {
            transUserApplyInfo.setInfoId(null);
        }
        return saveOrUpdate(transUserApplyInfo);
    }

    /**
     * 删除人员申请信息
     *
     * @param infoIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransUserApplyInfo(String infoIds) {
        deleteByIds(infoIds.split(","));
    }

    /**
     * 根据人员Id删除其申请信息
     *
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTransUserApplyInfoByUser(String userId) {
        List<TransUserApplyInfo> transUserApplyInfoList = getTransUserApplyInfoByUser(userId);
        delete(transUserApplyInfoList);
    }
}
