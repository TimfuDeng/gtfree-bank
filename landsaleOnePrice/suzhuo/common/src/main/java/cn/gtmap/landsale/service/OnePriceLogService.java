package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.OnePriceLog;
import cn.gtmap.landsale.model.TransResultLog;

import java.util.List;

/**
 * Created by trr on 2016/8/13.
 */
public interface OnePriceLogService {
    List<OnePriceLog> findOnePriceLogList(String transTargetId);

    Page<OnePriceLog> findOnePriceLogList2Page(String transTargetId,Pageable request);

    OnePriceLog findOnePriceLogListByTransTargetIdTransUserId(String transTargetId,String price);

    OnePriceLog findOnePriceLogListByTransTargetIdPrice(String transTargetId,Long price);

    OnePriceLog saveOnePriceLog(OnePriceLog onePriceLog);

    OnePriceLog getOnePriceLog(String applyId);



}
