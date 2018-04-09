package cn.gtmap.landsale.core.service;


import cn.gtmap.landsale.common.model.TransGpsOffset;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地图纠偏服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/6
 */
public interface TransGpsOffsetService {

    /**
     * 根据经度和维度获取TransGpsOffset对象
     * @param lng 经度（2位小数）
     * @param lat 维度（2位小数）
     * @return
     */
    TransGpsOffset getTransGpsOffset(Double lng, Double lat);

    /**
     * 获取一个点的网格
     * @param lng 经度
     * @param lat 经度
     * @return
     */
    List<TransGpsOffset> getGrid(Double lng, Double lat);

    /**
     * 纠偏一个坐标点
     * @param lng
     * @param lat
     * @return
     */
    Double[] correctPoint(Double lng, Double lat);
}
