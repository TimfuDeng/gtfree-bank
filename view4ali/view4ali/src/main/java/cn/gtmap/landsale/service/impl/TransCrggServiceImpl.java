package cn.gtmap.landsale.service.impl;

import cn.gtmap.landsale.mapper.TransCrggMapper;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.service.TransCrggService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by JIBO on 2016/9/14.
 */
public class TransCrggServiceImpl implements TransCrggService {

    TransCrggMapper crggMapper;


    public void setCrggMapper(TransCrggMapper crggMapper) {
        this.crggMapper = crggMapper;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="CrggCache", key = "#record.ggId")
    })
    public int insertOrUpdate(TransCrgg record) {
        if (StringUtils.isNotBlank(record.getGgId())){
            TransCrgg transCrgg=crggMapper.selectByKey(record.getGgId());
            if (transCrgg==null){
                return crggMapper.insert(record);
            }else{
                return crggMapper.update(record);
            }
        }
        return 0;
    }

    @Transactional(readOnly = true)
    @Cacheable(value="CrggCache",key="#ggId")
    public TransCrgg selectByKey(String ggId) {
        return crggMapper.selectByKey(ggId);
    }
}
