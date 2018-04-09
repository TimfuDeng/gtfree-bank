package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransTargetMultiTrade;

import java.util.List;

/**
 * Created by trr on 2016/7/19.
 */
public interface TransTargetMultiTradeService {
    List<TransTargetMultiTrade> getTransTargetMultiTradeList(String targetId);
}
