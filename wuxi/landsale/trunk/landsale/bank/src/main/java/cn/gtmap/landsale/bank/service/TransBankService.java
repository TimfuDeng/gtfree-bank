package cn.gtmap.landsale.bank.service;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransBank;

import java.util.List;

/**
 * 银行Service
 * @author zsj
 * @version v1.0, 2017/9/28
 */
public interface TransBankService {

    /**
     * 获取银行分页 列表
     * @param bankName
     * @param regionCodes
     * @param pageable
     * @return
     */
    Page<TransBank> findBankPage(String bankName, String regionCodes, Pageable pageable);

    /**
     * 获的所有的银行
     * @return
     */
    List<TransBank>  getBankList();

    /**
     * 根据bankId获取银行
     * @param bankId
     * @return
     */
    TransBank  getBank(String bankId);
    /**
     * 获得银行的信息
     * @param regionCode
     * @return
     */
    List<TransBank> getBankListByRegion(String regionCode);

    /**
     * 保存银行的相关信息
     * @param transBank
     * @return
     */
    ResponseMessage<TransBank> saveTransBank(TransBank transBank);

    /**
     * 删除银行
     * @param bankId
     * @return
     */
    ResponseMessage<TransBank> deleteBank(String bankId);

    /**
     * 根据银行代码和行政区代码获取银行
     * @param bankCode
     * @param regionCode
     * @return
     */
    TransBank getBankByCodeAndRegion(String bankCode, String regionCode);

    /**
     * 根据code获取银行
     * @param code
     * @return
     */
    TransBank findByCode(String code);
}
