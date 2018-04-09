package cn.gtmap.landsale.service.impl;

import cn.gtmap.landsale.core.ResourceOfferReal;
import cn.gtmap.landsale.core.ResourceOfferRealContainer;
import cn.gtmap.landsale.mapper.ResourceOfferMapper;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.ResourceOfferService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by JIBO on 2016/9/14.
 */
public class ResourceOfferServiceImpl implements ResourceOfferService {

    ResourceOfferMapper resourceOfferMapper;

    ResourceOfferRealContainer resourceOfferRealContainer;

    public void setResourceOfferMapper(ResourceOfferMapper resourceOfferMapper) {
        this.resourceOfferMapper = resourceOfferMapper;
    }

    public void setResourceOfferRealContainer(ResourceOfferRealContainer resourceOfferRealContainer) {
        this.resourceOfferRealContainer = resourceOfferRealContainer;
    }

    public int insert(TransResourceOffer resourceOffer) {
        ResourceOfferReal resourceOfferReal =resourceOfferRealContainer.getResourceOfferReal(resourceOffer.getResourceId());
        if (resourceOfferReal!=null){
            resourceOfferReal.add(resourceOffer);
        }
        return resourceOfferMapper.insert(resourceOffer);
    }

    @Transactional(readOnly = true)
    public TransResourceOffer selectMaxOffer(String resourceId) {
        List<TransResourceOffer> resourceOfferList=resourceOfferMapper.selectOfferListByPage(resourceId,1,0);
        return resourceOfferList.get(0);
    }

    @Transactional(readOnly = true)
    public List<TransResourceOffer> selectOfferListByPage(String resourceId,int limit, int offset) {
        return resourceOfferMapper.selectOfferListByPage(resourceId,limit,offset);
    }

    @Transactional(readOnly = true)
    public int selectCount(String resourceId) {
        return resourceOfferMapper.selectCount(resourceId);
    }
}
