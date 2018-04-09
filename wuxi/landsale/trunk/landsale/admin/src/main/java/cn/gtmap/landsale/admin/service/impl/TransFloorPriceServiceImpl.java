package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.admin.register.LandUseDictClient;
import cn.gtmap.landsale.admin.service.TransFloorPriceService;
import cn.gtmap.landsale.common.model.LandUseDict;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransFloorPrice;
import cn.gtmap.landsale.common.security.SecUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by M150237 on 2017-09-01.
 */
@Service
public class TransFloorPriceServiceImpl extends HibernateRepo<TransFloorPrice,String>  implements TransFloorPriceService,ApplicationContextAware  {

    ApplicationContext applicationContext;

    @Autowired
    LandUseDictClient landUseDictClient;
    /**
     * 根据userId，找出不同行政区可查看的不同用途的地块
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransFloorPrice> getXzqYtList(String userId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(userId!=null && userId!="") {
            criterionList.add(Restrictions.eq("userId", userId));
        }
        return list(criteria(criterionList));

    }

    /**
     * 底价授权
     *
     * @param userId
     * @param regionCodes
     * @param tdytDictCodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransFloorPrice> saveGrant(@RequestParam(value = "userId",required = true) String userId, @RequestParam(value = "regionCodes",required = true)String regionCodes, @RequestParam(value = "tdytDictCodes",required = true) String tdytDictCodes) {
        List tdytDictNames=new ArrayList<>();
        for (String code:tdytDictCodes.split(",")){
            LandUseDict landUseDict=landUseDictClient.getLandUseDict(code);
            tdytDictNames.add(landUseDict.getName());
        }
        String names=listToString(tdytDictNames.toArray(),",");
        TransFloorPrice transFloorPrice=new TransFloorPrice();
        transFloorPrice.setRegionCode(regionCodes);
        transFloorPrice.setTdytDictCode(tdytDictCodes);
        transFloorPrice.setUserId(userId);
        transFloorPrice.setTdytDictName(names);
        saveOrUpdate(transFloorPrice);
        return new ResponseMessage(true,transFloorPrice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransFloorPrice> editGrant(TransFloorPrice transFloorPrice, @RequestParam(value = "regionCodes",required = true)String regionCodes, @RequestParam(value = "tdytDictCodes",required = true) String tdytDictCodes) {
        List tdytDictNames=new ArrayList<>();
        for (String code:tdytDictCodes.split(",")){
            LandUseDict landUseDict=landUseDictClient.getLandUseDict(code);
            tdytDictNames.add(landUseDict.getName());
        }
        String names=listToString(tdytDictNames.toArray(),",");
        transFloorPrice.setRegionCode(regionCodes);
        transFloorPrice.setTdytDictCode(tdytDictCodes);
        transFloorPrice.setTdytDictName(names);
        merge(transFloorPrice);
        return new ResponseMessage(true,transFloorPrice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransFloorPrice> deleteGrant(String floorPriceIds) {
        if(StringUtils.isNotBlank(floorPriceIds)){
            for (String id:floorPriceIds.split(",")){
                deleteById(id);
            }
            return new ResponseMessage(true,"删除成功！");
        }else {
            return new ResponseMessage(false,"该用户还未授权，无法删除授权！");
        }

    }


    private   String listToString(Object[] list,String separater){
        return StringUtils.join(list,separater);
    }


    /**
     * 获取角色的底价权限
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransFloorPrice getTransFloorPrice(String userId) {
        return StringUtils.isNotBlank(userId) ? get(criteria(Restrictions.eq("userId", userId))) : null;
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext= applicationContext;
    }
}
