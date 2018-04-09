package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.TransNotice;
import cn.gtmap.landsale.model.TransTarget;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/7/7.
 */
public interface TransTargetService {
    Page<TransTarget> findTransTarget(String title, Collection<String> targetIds, Pageable request);

    Page<TransTarget> findTransTarget2Export(String title, Pageable request);

    public TransTarget saveTransTarget(TransTarget transTarget);

    List<TransTarget> findTransTargetListByNo(String name);

    public TransTarget getTransTarget(String id);

    public List<TransTarget> findTransTargetByTargetIds(Collection<String> targetIds);

}
