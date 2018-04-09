package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.OnePriceLog;
import cn.gtmap.landsale.common.model.ResponseMessage;

import java.util.List;

/**
 *@author trr on 2016/8/13.
 */
public interface OnePriceLogService {

    /**
     * 根据地块id获取一次报价列表
     * @param transResourceId
     * @return
     */
    List<OnePriceLog> findOnePriceLogList(String transResourceId);

    /**
     * 以报价排序查询一次报价列表
     * @param transResourceId
     * @return
     */
    List<OnePriceLog> findOnePriceLogListOrderByBj(String transResourceId);

    /**
     * 根据地块id和用户id获取
     * @param transResourceId
     * @param transUserId
     * @return
     */
    OnePriceLog findOnePriceLogListByTransResourceIdTransUserId(String transResourceId, String transUserId);

    /**
     * 根据地块id和报价获取
     * @param transResourceId
     * @param price
     * @return
     */
    OnePriceLog findOnePriceLogListByTransResourceIdPrice(String transResourceId, Long price);

    /**
     * 保存
     * @param onePriceLog
     * @return
     */
    ResponseMessage<OnePriceLog> saveOnePriceLog(OnePriceLog onePriceLog);

    /**
     * 获取
     * @param logId
     * @return
     */
    OnePriceLog getOnePriceLogByLogId(String logId);

    /**
     * 根据applyId获取一次报价记录
     * @param applyId
     * @return
     */
    OnePriceLog getOnePriceLog(String applyId);



}
