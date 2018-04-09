package cn.gtmap.landsale.service;

import cn.gtmap.landsale.model.TransCrgg;

/**
 * Created by JIBO on 2016/9/14.
 */
public interface TransCrggService {

    int insertOrUpdate(TransCrgg record);

    TransCrgg selectByKey(String ggId);
}
