package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.model.LandUseDict;
import cn.gtmap.landsale.common.model.ResponseMessage;
import cn.gtmap.landsale.common.model.TransResourceSon;
import cn.gtmap.landsale.core.service.LandUseDictSerivce;
import cn.gtmap.landsale.core.service.TransResourceSonService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地块多用途服务
 * @author zsj
 * @version v1.0, 2017/10/31
 */
@Service
public class TransResourceSonServiceImpl extends HibernateRepo<TransResourceSon, String> implements TransResourceSonService {
    @Autowired
    LandUseDictSerivce landUseDictSerivce;

    /**
     * 查询列表
     * @param resourceId 地块Id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResourceSon> getTransResourceSonList(String resourceId) {
        List<Criterion> criterionList= Lists.newArrayList();
        if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        List<TransResourceSon> list=list(criteria(criterionList).addOrder(Order.desc("zdCode")));
        return list;
    }

    /**
     * 根据地块多用途Id 获取TransResourceSon
     * @param resourceSonId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TransResourceSon getTransResourceSon(String resourceSonId) {
        return get(resourceSonId);
    }

    /**
     * 保存地块多用途
     * @param transResourceSon
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceSon> saveTransResourceSon(TransResourceSon transResourceSon) {
        LandUseDict landUseDict = landUseDictSerivce.getLandUseDict(transResourceSon.getTdytCode());
        if (landUseDict != null) {
            transResourceSon.setTdytName(landUseDict.getName());
        }
        transResourceSon = save(transResourceSon);
        return new ResponseMessage<>(true, transResourceSon);
    }

    /**
     * 修改地块多用途
     * @param transResourceSon
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<TransResourceSon> updateTransResourceSon(TransResourceSon transResourceSon) {
        transResourceSon = merge(transResourceSon);
        return new ResponseMessage<>(true, transResourceSon);
    }

    /**
     * 根据地块多用途Id 删除
     * @param resourceSonId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransResourceSon(String resourceSonId) {
        deleteById(resourceSonId);
        return new ResponseMessage<>(true);
    }

    /**
     * 根据地块Id 删除
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteTransResourceSonByResourceId(String resourceId) {
        List<TransResourceSon> transResourceSonList = getTransResourceSonList(resourceId);
        if (transResourceSonList != null && transResourceSonList.size() > 0) {
            delete(transResourceSonList);
        }
        return new ResponseMessage<>(true);
    }

    /**
     * 根据宗地号和地块id查询
     * @param zdCode
     * @param resourceId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransResourceSon> getTransResourceSons(String zdCode, String resourceId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if(StringUtils.isNotBlank(resourceId)){
            criterionList.add(Restrictions.eq("resourceId", resourceId));
        }
        if(StringUtils.isNotBlank(zdCode)){
            criterionList.add(Restrictions.like("zdCode", zdCode, MatchMode.ANYWHERE));
        }
        List<TransResourceSon> list=list(criteria(criterionList).addOrder(Order.desc("zdCode")));
        return list;
    }
}
