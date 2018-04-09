package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.LngLatUnionId;
import cn.gtmap.landsale.common.model.TransGpsOffset;
import cn.gtmap.landsale.core.service.TransGpsOffsetService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/6/6
 */
@Service
public class TransGpsOffsetServiceImpl extends HibernateRepo<TransGpsOffset, LngLatUnionId> implements TransGpsOffsetService {

    private double latOffset;
    private double lngOffset;

    public void setLatOffset(double latOffset) {
        this.latOffset = latOffset;
    }

    public void setLngOffset(double lngOffset) {
        this.lngOffset = lngOffset;
    }

    /**
     * 根据经度和维度获取TransGpsOffset对象
     *
     * @param lng 经度（2位小数）
     * @param lat 维度（2位小数）
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransGpsOffset getTransGpsOffset(Double lng, Double lat) {
        LngLatUnionId id = new LngLatUnionId(lng,lat);
        return get(id);
    }

    /**
     * 获取一个点的网格
     *
     * @param lng 经度
     * @param lat 经度
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransGpsOffset> getGrid(Double lng, Double lat) {
        Double minLng = Math.floor(lng);
        Double maxLng = Math.ceil(lng);
        Double minLat = Math.floor(lat);
        Double maxLat = Math.ceil(lat);
        List<TransGpsOffset> grid = Lists.newArrayList();
        grid.add(getTransGpsOffset(minLng,minLat));
        grid.add(getTransGpsOffset(minLng,maxLat));
        grid.add(getTransGpsOffset(maxLng,minLat));
        grid.add(getTransGpsOffset(maxLng,maxLat));
        return grid;
    }

    /**
     * 纠偏一个坐标点
     *
     * @param lng
     * @param lat
     */
    @Override
    @Transactional(readOnly = true)
    public Double[] correctPoint(Double lng, Double lat) {
        Double[] result = new Double[2];
//        List<TransGpsOffset> grid = getGrid(lng,lat);
//        Double lngOffset = 0.0;
//        Double latOffset = 0.0;
//        for(TransGpsOffset transGpsOffset:grid){
//            lngOffset += transGpsOffset.getOffsetlng();
//            latOffset += transGpsOffset.getOffsetlat();
//        }
        //lng = lng + lngOffset/4;
        lat = lat + latOffset;
        lng = lng + lngOffset;
        result[0] = lng;
        result[1] = lat;
        return result;
    }
}
