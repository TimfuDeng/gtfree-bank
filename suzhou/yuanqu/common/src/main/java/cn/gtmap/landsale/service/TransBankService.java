package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransBank;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

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

    List<TransBank> getBankListByRegions(Set<String> regions);


    /**
     * 根据币种得到银行
     * @param moneyUnit 币种
     * @return
     */
    List<TransBank> getBankList(String moneyUnit);

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
