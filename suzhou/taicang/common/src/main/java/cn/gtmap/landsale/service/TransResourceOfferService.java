package cn.gtmap.landsale.service;


import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransResourceOffer;

import java.util.List;

/**
 * 出让地块报价服务
 * Created by jiff on 14/12/21.
 */
public interface TransResourceOfferService {

    /**
     * 根据offerId获得报价
     * @param offerId 报价对象Id
     * @return
     */
    TransResourceOffer getTransResourceOffer(String offerId);

    /**
     * 增加报价
     * @param offer 报价对象
     * @return
     */
    TransResourceOffer addTransResourceOffer(TransResourceOffer offer);

    /**
     * 获得一个资源所有的报价
     * @param resourceId 出让地块Id
     * @return
     */
    List<TransResourceOffer> getOfferListByResource(String resourceId);

    /**
     * 统计报名人数
     * @return
     */
    List<Object[]> getOfferNumList();
    
    /**
     * 获得最新的报价，time：-1表示所有的报价
     * @param resourceId
     * @param request
     * @return
     */
    Page<TransResourceOffer> getResourceOfferPage(String resourceId,Pageable request);

    /**
     * 获得一个人所有报价列表
     * @param resourceId
     * @param userId
     * @param request
     * @return
     */
    Page<TransResourceOffer> getResourceOfferPageByUserId(String resourceId,String userId,Pageable request);


    /**
     * 是否多人同意进行限时竞价
     * @param resourceId 出让地块Id
     * @return
     */
    boolean enterjjProcess(String resourceId);

    /**
     * 判断最高价(第一个出价的)
     * @param resourceId 出让地块Id
     * @return
     */
    TransResourceOffer getMaxOffer(String resourceId);

    /**
     * 获得总价和单价最高的价格，不包括多指标
     * @param resourceId
     * @return
     */
    TransResourceOffer getMaxOfferFormPrice(String resourceId);
    /**
     * 获取地块的报价次数
     * @param resourceId 出让地块Id
     * @return
     */
    Long getResourceOfferFrequency(String resourceId);
}
