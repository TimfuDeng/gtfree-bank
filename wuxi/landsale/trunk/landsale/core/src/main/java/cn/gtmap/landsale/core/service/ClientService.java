package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceOffer;

import java.util.List;

/**
 * Created by jiff on 15/5/6.
 */
public interface ClientService {

    /**
     * 接受客户端报价
     * @param userId
     * @param resourceId
     * @param offerPrice
     * @param offerType
     * @throws Exception
     * @return
     */
    ResponseMessage<TransResourceOffer> acceptResourceOffer(String userId, String resourceId, double offerPrice, int offerType)throws Exception;


    /**
     * 获得大于timevalue的最新报价，最多15条
     * @param resourceId
     * @param timeValue
     * @return
     */
    List<TransResourceOffer> getOfferList(String resourceId, long timeValue);

    /**
     * 获取地块报价
     * @param resourceId
     * @param request
     * @return
     */
    Page<TransResourceOffer> findTransResourceOffers(Pageable request, String resourceId);

    /**
     * 申请银行虚拟子帐号
     * @param applyId
     * @return
     */
//    TransBankAccount applyBankAccount(String applyId);

}
