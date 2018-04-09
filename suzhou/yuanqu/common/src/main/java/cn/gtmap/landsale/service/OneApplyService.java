package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.OneApply;

/**
 * Created by trr on 2016/8/14.
 */
public interface OneApplyService {
    OneApply saveOneApply(OneApply oneApply);

    OneApply getOnApply(String targetId, String transUserId);

    Page<OneApply> findOneApply(String transUserId, Pageable page);

}
