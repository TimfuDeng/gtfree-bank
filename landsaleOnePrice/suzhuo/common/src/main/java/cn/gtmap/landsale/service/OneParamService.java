package cn.gtmap.landsale.service;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.landsale.model.OneParam;

import java.util.Collection;
import java.util.List;

/**
 * Created by trr on 2016/8/11.
 */
public interface OneParamService {
    OneParam saveOrUpdateOneParam(OneParam oneParam);

    OneParam getOneParam(String id);

    Page<OneParam> findOneParam(Pageable request);


}
