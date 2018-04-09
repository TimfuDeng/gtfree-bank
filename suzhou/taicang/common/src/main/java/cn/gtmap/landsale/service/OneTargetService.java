package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.OneTarget;

/**
 * Created by trr on 2016/8/12.
 */
public interface OneTargetService {
    OneTarget getOneTarget(String id);

    OneTarget getOneTargetByTransTarget(String transTargetId);

    OneTarget saveOneTarget(OneTarget oneTarget);

    Page<OneTarget> findOneTargetPage(String title,Pageable page);

    Page<OneTarget> findOneTargetPageByIsStop(String title,Pageable page,Integer isStop);
}
