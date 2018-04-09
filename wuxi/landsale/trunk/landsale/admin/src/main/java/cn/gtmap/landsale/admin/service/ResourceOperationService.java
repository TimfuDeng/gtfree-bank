package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.model.TransResource;

/**
 * 用来改变地块的状态
 * 在不同的状态间切换
 * @author Jibo on 2015/6/17.
 */
public interface ResourceOperationService {

    /**
     * 将状态改成正在公告
     * @param transResource
     */
    void toGG(TransResource transResource);

    /**
     * 将状态改成正在挂牌
     * @param transResource
     */
    void toGP(TransResource transResource);

    /**
     * 将状态改成摇号结束前一小时
     * @param transResource
     */
    void toGPOneHour(TransResource transResource);
    /**
     * 将状态改成正在限时
     * @param transResource
     */
    void toXS(TransResource transResource);

    /**
     * 准备结束，成交或者流拍
     * @param transResource
     */
    void toOver(TransResource transResource);
}
