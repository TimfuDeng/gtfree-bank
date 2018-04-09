package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransBankAccount;
import cn.gtmap.landsale.model.TransCaUser;
import cn.gtmap.landsale.model.TransResourceOffer;

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
     * @throws Exception
     */
    TransResourceOffer acceptResourceOffer(String userId,String resourceId,double offerPrice,int offerType)throws Exception;


    /**
     * 获得大于timevalue的最新报价，最多15条
     * @param resourceId
     * @param timeValue
     * @return
     */
    List<TransResourceOffer> getOfferList(String resourceId,long timeValue);

    /**
     * 获取地块报价
     * @param resourceId
     * @return
     */
    Page<TransResourceOffer> findTransResourceOffers(Pageable request,String resourceId);

    /**
     * 申请银行虚拟子帐号
     * @param applyId
     * @return
     */
    TransBankAccount applyBankAccount(String applyId);

    /**
     * @作者 王建明
     * @创建日期 2015-10-27
     * @创建时间 15:45
     * @描述 —— 将此方法封装在这里的原因是动态切换数据源声明必须要在紧邻的service操作前声明
     */
    TransCaUser getTransCaUserByCAThumbprint(String thumbprint);
}
