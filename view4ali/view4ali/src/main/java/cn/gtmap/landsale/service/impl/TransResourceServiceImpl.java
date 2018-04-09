package cn.gtmap.landsale.service.impl;

import cn.gtmap.landsale.mapper.TransResourceMapper;
import cn.gtmap.landsale.model.QueryCondition;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.TransResourceService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import sun.util.resources.CalendarData_th;

import java.util.Calendar;
import java.util.List;

/**
 * Created by JIBO on 2016/9/15.
 */
public class TransResourceServiceImpl implements TransResourceService {

    CacheManager cacheManager;

    TransResourceMapper transResourceMapper;

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setTransResourceMapper(TransResourceMapper transResourceMapper) {
        this.transResourceMapper = transResourceMapper;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value="ResourceCache", key = "#transResource.resourceId")
    })
    public int insertOrUpdate(TransResource transResource) {
        clearCacheByRegion(transResource.getRegionCode());
        if (StringUtils.isNotBlank(transResource.getResourceId())){
            TransResource resource=transResourceMapper.selectByKey(transResource.getResourceId());
            if (resource==null){
                return transResourceMapper.insert(transResource);
            }else{
                return transResourceMapper.update(transResource);
            }
        }
        return 0;
    }


    @Transactional
    @Caching(evict={
            @CacheEvict(value="ResourceCache", key = "#transResource.resourceId")
    })
    public int updateStatusToFinish(TransResource transResource,String overStatus,String transUser) {
        clearCacheByRegion(transResource.getRegionCode());
        return transResourceMapper.updateStatusToFinish(transResource.getResourceId(),overStatus,transUser);
    }

    @Transactional(readOnly = true)
    @Cacheable(value="ResourceCache",key="#resourceId")
    public TransResource selectByKey(String resourceId) {
        return transResourceMapper.selectByKey(resourceId);
    }

    @Transactional(readOnly = true)
    public List<TransResource> selectByPage(QueryCondition condition,int limit) {
        int offset=condition.getPage()>0 ? condition.getPage()*limit:0;
        if (inCache(condition,offset)){
            Element obj= cacheManager.getCache("ResourceCache").get("region"+condition.getRegion());
            if (obj!=null)
                return (List<TransResource>)obj.getObjectValue();
            else{
                List<TransResource> result=transResourceMapper.selectByPage(condition, Calendar.getInstance().getTime(), limit,offset);
                cacheManager.getCache("ResourceCache").put(new Element("region"+condition.getRegion(), result));
            }
        }
        return transResourceMapper.selectByPage(condition, Calendar.getInstance().getTime(), limit,offset);
    }

    @Transactional(readOnly = true)
    public int selectCount(QueryCondition condition){
        if (inCache(condition,0)){
            Element obj= cacheManager.getCache("ResourceCache").get("count"+condition.getRegion());
            if (obj!=null)
                return Integer.parseInt(obj.getObjectValue().toString());
            else{
                int result=transResourceMapper.selectCount(condition, Calendar.getInstance().getTime());
                cacheManager.getCache("ResourceCache").put(new Element("count"+condition.getRegion(), result));
            }
        }
        return transResourceMapper.selectCount(condition, Calendar.getInstance().getTime());
    }

    private void clearCacheByRegion(String regionCode){
        if (regionCode.length()>4){
            cacheManager.getCache("ResourceCache").remove("region"+regionCode);
            cacheManager.getCache("ResourceCache").remove("region"+regionCode.substring(0,4));
            cacheManager.getCache("ResourceCache").remove("region32");
            cacheManager.getCache("ResourceCache").remove("count"+regionCode);
            cacheManager.getCache("ResourceCache").remove("count"+regionCode.substring(0,4));
            cacheManager.getCache("ResourceCache").remove("count32");
        }
    }


    private boolean inCache(QueryCondition condition,int offset){
        if (offset>0)
            return false;
        if (StringUtils.isNotBlank(condition.getName()))
            return false;
        if (condition.getStatus()>0)
            return false;
        if (condition.getUse()>0)
            return false;
        if (StringUtils.isNotBlank(condition.getName()) && !condition.getName().equals("0"))
            return false;
        return true;
    }

}
