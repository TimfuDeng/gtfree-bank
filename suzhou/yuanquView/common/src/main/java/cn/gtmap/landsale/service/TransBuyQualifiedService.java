package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransBuyQualified;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.model.TransResourceVerify;

import java.util.List;

/**申购资格审核
 * Created by trr on 2015/10/13.
 */
public interface TransBuyQualifiedService {
    /**
     * 保存申购资格申请
     * @param transBuyQualified
     * @return
     */
    TransBuyQualified saveTransBuyQualified(TransBuyQualified transBuyQualified);

    /**
     * 保存成资格审核和银行
     * @param transBuyQualified
     * @param transResourceApply
     */
    void saveTransBuyQualifiedAndTransResourceApply(TransBuyQualified transBuyQualified,TransResourceApply transResourceApply);

    /**
     * 根据id获取申购资格信息
     * @param qualifiedId
     * @return
     */
    TransBuyQualified getTransBuyQualifiedById(String qualifiedId);

    /**
     *
     * @param applyId
     * @return
     */
    TransBuyQualified getTransBuyQualifiedByApplyId(String applyId);

    public List<TransBuyQualified> getListTransBuyQualifiedByApplyId(String applyId);
}
