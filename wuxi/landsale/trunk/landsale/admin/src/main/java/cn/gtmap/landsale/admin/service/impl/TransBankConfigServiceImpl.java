package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.service.TransBankConfigService;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBankConfig;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 银行配置ServiceImpl
 * @author zsj
 * @version v1.0, 2017/9/26
 */
@Service
public class TransBankConfigServiceImpl extends HibernateRepo<TransBankConfig, String> implements TransBankConfigService {


    /**
     * 获取银行配置 分页
     * @param bankName
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransBankConfig> getTransBankConfigPage(String bankName, Pageable request) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(bankName)) {
            criterionList.add(Restrictions.like("bankName", bankName, MatchMode.ANYWHERE));
        }
        return find(criteria(criterionList), request);
    }

    /**
     * 获取 银行配置列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransBankConfig> getTransBankConfigList() {
        return list();
    }

    /**
     * 通过银行配置ID 获取银行配置信息
     * @param configId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransBankConfig getTransBankConfigById(String configId) {
        return get(configId);
    }

    /**
     * 添加 银行配置信息
     * @param transBankConfig
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransBankConfig> addTransBankConfig(TransBankConfig transBankConfig) {
        transBankConfig.setConfigId(null);
        transBankConfig.setCreateTime(new Date());
        transBankConfig = save(transBankConfig);
        return new ResponseMessage(true, transBankConfig);
    }

    /**
     * 修改 银行配置信息
     * @param transBankConfig
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransBankConfig> updateTransBankConfig(TransBankConfig transBankConfig) {
        transBankConfig.setCreateTime(new Date());
        transBankConfig = merge(transBankConfig);
        return new ResponseMessage(true, transBankConfig);
    }

    /**
     * 删除 银行配置信息
     * @param configId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransBankConfig(String configId) {
        deleteById(configId);
        return new ResponseMessage(true);
    }
}
