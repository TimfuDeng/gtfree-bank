package cn.gtmap.landsale.admin.service;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBankConfig;

import java.util.List;

/**
 * 银行配置Service
 * @author zsj
 * @version v1.0, 2017/9/26
 */
public interface TransBankConfigService {

    /**
     * 获取银行配置 分页
     * @param bankName
     * @param request
     * @return
     */
    Page<TransBankConfig> getTransBankConfigPage(String bankName, Pageable request);

    /**
     * 获取 银行配置列表
     * @return
     */
    List<TransBankConfig> getTransBankConfigList();

    /**
     * 通过银行配置ID 获取银行配置信息
     * @param configId
     * @return
     */
    TransBankConfig getTransBankConfigById(String configId);

    /**
     * 添加 银行配置信息
     * @param transBankConfig
     * @return
     */
    ResponseMessage<TransBankConfig> addTransBankConfig(TransBankConfig transBankConfig);

    /**
     * 修改 银行配置信息
     * @param transBankConfig
     * @return
     */
    ResponseMessage<TransBankConfig> updateTransBankConfig(TransBankConfig transBankConfig);

    /**
     * 删除 银行配置信息
     * @param configId
     * @return
     */
    ResponseMessage deleteTransBankConfig(String configId);


}
