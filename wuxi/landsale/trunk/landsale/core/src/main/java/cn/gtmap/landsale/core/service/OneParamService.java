package cn.gtmap.landsale.core.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.common.model.OneParam;
import cn.gtmap.landsale.common.model.ResponseMessage;

/**
 * Created by trr on 2016/8/11.
 */
public interface OneParamService {
    /**
     * 保存一次报价参数
     * @param oneParam
     * @return
     */
    ResponseMessage<OneParam> saveOrUpdateOneParam(OneParam oneParam);

    /**
     * 获取
     * @param id
     * @return
     */
    OneParam getOneParam(String id);

    /**
     * 列表获取
     * @param request
     * @return
     */
    Page<OneParam> findOneParam(Pageable request);

    /**
     * 根据行政区代码获取
     * @param regionCode
     * @return
     */
    OneParam getOneParamByRegionCode(String regionCode);
}
