package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.QueryCondition;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransResource;

import java.util.List;

/**
 * Created by JIBO on 2016/9/15.
 */
public interface TransResourceService {

    int insertOrUpdate(TransResource transResource);

    TransResource selectByKey(String resourceId);

    List<TransResource> selectByPage(QueryCondition condition,int limit);

    int selectCount(QueryCondition condition);

    int updateStatusToFinish(TransResource transResource,String overStatus,String transUser);
}
