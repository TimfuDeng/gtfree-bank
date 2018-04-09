package cn.gtmap.landsale.mapper;

import cn.gtmap.landsale.model.TransCrgg;

/**
 * Created by JIBO on 2016/9/14.
 */
public interface TransCrggMapper {

    int insert(TransCrgg record);

    int update(TransCrgg record);

    TransCrgg selectByKey(String ggId);
}
