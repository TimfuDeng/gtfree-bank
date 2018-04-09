package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransResultLog;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by trr on 2016/8/14.
 */
public interface TransResultLogService {

    /**
     * 从原来系统里面获取报价记录
     * @param transTargetId
     * @return
     */
    List<TransResultLog> findTransOnePriceLogList(String transTargetId);
}
