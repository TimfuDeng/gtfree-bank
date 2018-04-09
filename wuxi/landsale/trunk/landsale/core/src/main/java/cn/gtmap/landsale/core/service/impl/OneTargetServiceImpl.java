package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.Pageable;
import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.core.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 一次报价表标的
 * Created by trr on 2016/8/12.
 */
@Service
public class OneTargetServiceImpl extends HibernateRepo<OneTarget,String> implements OneTargetService {

    @Autowired
    OnePriceLogService onePriceLogService;

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransResourceVerifyService transResourceVerifyService;

    @Override
    @Transactional(readOnly = true)
    public OneTarget getOneTargetByTransResource(String transResourceId) {
        return get(criteria(Restrictions.eq("transResourceId",transResourceId)));
    }

    @Override
    @Transactional(readOnly = true)
    public OneTarget getOneTarget(String id) {
        return get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //@Caching(evict={
    //@CacheEvict(value="OneTargetQueryCache",allEntries=true),
    //@CacheEvict(value = "OnePriceLogCache",allEntries = true)
    //})
    public ResponseMessage<OneTarget> saveOneTarget(OneTarget oneTarget){
        return new ResponseMessage(true, saveOrUpdate(oneTarget));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    //统一事务,修改地块状态,插入出价记录
    public ResponseMessage<OneTarget> saveOneTargetAndUpdateResource(OneTarget oneTarget,String offerType,String logId,String offerId) {
        TransResource transResource = transResourceService.getTransResource(oneTarget.getTransResourceId());
        //存在一次报价时
        if("1".equals(offerType)){
            OnePriceLog onePriceLog = onePriceLogService.getOnePriceLogByLogId(logId);
            //成交数据同步到出让系统

            //1.将竞得的一次报价数据转化成出让系统里面的报价数据，对于无法转化的数据（如：号牌）作-1处理
            // 2.设置地块成交
            //3.在交易系统里面消除转化成出让系统报价数据的影响,在出让系统里面设置
            TransResourceOffer transResourceOffer = null;
            if(StringUtils.isNotBlank(transResource.getOfferId())) {
                transResourceOffer = transResourceOfferService.getTransResourceOffer(transResource.getOfferId());
            }
            if(transResourceOffer != null) {
                transResourceOffer.setOfferType(-1);
//                transResourceOffer.setOfferPrice((double)oneTarget.getSuccessPrice() / 10000.0);
                transResourceOffer.setOfferTime(onePriceLog.getPriceDate().getTime());
                transResourceOffer.setResourceId(onePriceLog.getTransResourceId());
                transResourceOffer.setUserId(onePriceLog.getTransUserId());
            }else {
                transResourceOffer = new TransResourceOffer();
                transResourceOffer.setOfferType(-1);
                transResourceOffer.setOfferPrice((double)oneTarget.getSuccessPrice() / 10000.0);
                transResourceOffer.setOfferTime(onePriceLog.getPriceDate().getTime());
                transResourceOffer.setResourceId(onePriceLog.getTransResourceId());
                transResourceOffer.setUserId(onePriceLog.getTransUserId());
            }
            ResponseMessage<TransResourceOffer> transResourceOffer1 = transResourceOfferService.addTransResourceOffer(transResourceOffer);

            transResourceOffer = transResourceOffer1.getEmpty();

            transResource.setOfferId(transResourceOffer.getOfferId());
            transResource.setOverTime(onePriceLog.getPriceDate());
            transResource.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_OVER);
            transResource.setSuccessOfferChoose(Constants.SUCCESS_OFFER_CHOOSE.YCBJ); // 设置成交方式
            transResourceService.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_CHENG_JIAO);
        }else if("2".equals(offerType)){
            //不存在一次报价时，报价信息取正常报价信息
            TransResourceOffer transResourceOffer = transResourceOfferService.getTransResourceOffer(offerId);
            transResourceOffer.setOfferType(-1);
            transResourceOffer.setOfferTime(System.currentTimeMillis());
            //成交数据同步到出让系统
//            TransResource transResource = transResourceService.getTransResource(oneTarget.getTransResourceId());

            transResource.setOfferId(transResourceOffer.getOfferId());
            transResource.setOverTime(new Date());
            transResource.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_OVER);
            transResource.setSuccessOfferChoose(Constants.SUCCESS_OFFER_CHOOSE.YCBJ); // 设置成交方式
            transResourceService.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_CHENG_JIAO);
        }

        //重置审核信息
        TransResourceVerify transResourceVerify = transResourceVerifyService.getTransVerifyLastByResourceId(transResource.getResourceId());
        if(transResourceVerify != null) {
            transResourceVerify.setCurrentStatus(Constants.CURRENT_STATUS.HISTORY);
            transResourceVerifyService.updateTransVerify(transResourceVerify);
        }
        return new ResponseMessage(true, saveOrUpdate(oneTarget));
    }

    @Override
    @Transactional
    //@Cacheable(value = "OneTargetQueryCache",key = "'findOneTargetPage'+#title+#page.getOffset()+#page.getIndex()")
    public Page<OneTarget> findOneTargetPage(String title, Pageable page) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("transName",title));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),page);
    }

    @Override
    @Transactional
    //@Cacheable(value = "OneTargetQueryCache",key = "'findOneTargetPageByIsStop'+#title+#page.getOffset()+#page.getIndex()+#isStop")
    public Page<OneTarget> findOneTargetPageByIsStop(String title, Pageable page, Integer isStop) {
        List<Criterion> list= Lists.newArrayList();
        if(StringUtils.isNotBlank(title)){
            list.add(Restrictions.like("transName",title, MatchMode.ANYWHERE));
        }
        if (null!=isStop){
            list.add(Restrictions.like("isStop",isStop));
        }
        return find(criteria(list).addOrder(Order.desc("createDate")),page);
    }

    @Override
    @Transactional
    public Page<OneTarget> findMyOneApplyList(String title, String transUserId, Pageable page, Integer isStop) {
        StringBuilder sql = new StringBuilder();
        Map<String,Object> mapParam= Maps.newHashMap();
        sql.append("select * from  ONE_TARGET T1  left join TRANS_RESOURCE_APPLY  T2 on T1.TRANS_TARGET_ID=T2.RESOURCE_ID  where 1=1 ");
        if (StringUtils.isNoneBlank(transUserId)){
            sql.append("  and  T2.USER_ID=:transUserId");
            mapParam.put("transUserId",transUserId);
        }
        if (null!=isStop){
            sql.append("  and  T1.IS_STOP=:isStop");
            mapParam.put("isStop", isStop);
        }
        if (StringUtils.isNotBlank(title)){
            sql.append("  and  T1.TRANS_NAME=:title");
            mapParam.put("title", title);
        }
        return findBySql(sql.toString(),mapParam,page);
    }
}
