package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransBank;

import java.util.List;

/**
 * User: JIFF
 * Date: 2014/12/31
 */
public interface TransBankService {
    /**
     * 获的所有的银行
     * @return
     */
    List<TransBank>  getBankList();

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
     */
    TransBank save(TransBank transBank);

    /**
     * 删除银行
     * @param bankId
     * @return
     */
    void del(String bankId);

}
