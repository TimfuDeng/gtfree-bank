package cn.gtmap.landsale.admin.service;


import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransRegion;

/**
 * 行政区Service
 * @author zsj
 * @version v1.0, 2017/9/7
 */
public interface TransRegionService {

    /**
     * 删除原行政区 添加新行政区
     * @param oldRegionCode
     * @param transRegion
     * @return
     */
    ResponseMessage deleteAddRegion(TransRegion transRegion, String oldRegionCode);


}
