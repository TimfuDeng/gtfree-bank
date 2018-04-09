package cn.gtmap.landsale.core.service.impl;

import cn.gtmap.egovplat.core.support.hibernate.HibernateRepo;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.core.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class YHResultServiceImpl extends HibernateRepo<YHResult, String> implements YHResultService {

    @Autowired
    TransResourceService transResourceService;

    @Autowired
    YHAgreeService yhAgreeService;

    @Autowired
    TransResourceOfferService transResourceOfferService;

    @Autowired
    TransResourceVerifyService transResourceVerifyService;

    /**
     * 保存摇号信息，修改地块信息
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage<YHResult> saveOrUpdateYHResult(String resourceId, String successPrice, String offerUserId, String userId) {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTimeInMillis(now.getTime());
        //保存摇号结果
        YHAgree yhAgree = yhAgreeService.getYHAgreeByResourceIdAndUserId(resourceId, offerUserId);

        //根据agreeId查询结果，如果存在，仅更新
        YHResult yhResult = getYHResultByResourceId(resourceId);
        if (yhResult == null) {
            yhResult = new YHResult();
        }
        yhResult.setAgreeId(yhAgree.getAgreeId());
        yhResult.setResultTime(cal.getTime());
        yhResult.setResultPrice(Double.valueOf(successPrice));
        yhResult.setResultUser(userId);

        TransResource transResource = transResourceService.getTransResource(yhAgree.getResourceId());
        String offerId = transResource.getOfferId();
        cal.setTimeInMillis(now.getTime());
        //添加一条出价记录
        //判断offer存在
        TransResourceOffer transResourceOffer = null;
        if(StringUtils.isNotBlank(offerId)){
            transResourceOffer = transResourceOfferService.getTransResourceOffer(transResource.getOfferId());
        }
        if(transResourceOffer != null) {
            //如果存在仅修改
            //类别同一次报价，区分普通报价
            transResourceOffer.setOfferType(-1);
            transResourceOffer.setOfferPrice(yhResult.getResultPrice());
            transResourceOffer.setOfferTime(cal.getTimeInMillis());
            transResourceOffer.setResourceId(transResource.getResourceId());
            transResourceOffer.setUserId(yhAgree.getUserId());
            transResourceOfferService.saveOrUpdateTransResourceOffer(transResourceOffer);
        }else {
            //不存在则新建
            transResourceOffer = new TransResourceOffer();
            //类别同一次报价，区分普通报价
            transResourceOffer.setOfferType(-1);
            transResourceOffer.setOfferPrice(yhResult.getResultPrice());
            transResourceOffer.setOfferTime(cal.getTimeInMillis());
            transResourceOffer.setResourceId(transResource.getResourceId());
            transResourceOffer.setUserId(yhAgree.getUserId());
            transResourceOfferService.addTransResourceOffer(transResourceOffer);
        }
        //设置地块offerId
        transResource.setOfferId(transResourceOffer.getOfferId());
        transResource.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_OVER);
        transResource.setSuccessOfferChoose(Constants.SUCCESS_OFFER_CHOOSE.YH); // 设置成交方式
        transResource.setOverTime(cal.getTime());   //设置成交时间
        ResponseMessage<TransResource> responseMessage = transResourceService.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_CHENG_JIAO);

        //重置审核信息
        TransResourceVerify transResourceVerify = transResourceVerifyService.getTransVerifyLastByResourceId(transResource.getResourceId());
        if(transResourceVerify != null) {
            transResourceVerify.setCurrentStatus(Constants.CURRENT_STATUS.HISTORY);
            transResourceVerifyService.updateTransVerify(transResourceVerify);
        }
        ResponseMessage<YHResult> result = new ResponseMessage<>(true,saveOrUpdate(yhResult));
        return result;
    }

    /**
     * 发布摇号信息，同时修改地块状态
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage postYHResult(String yhResultId) {
        YHResult yhResult = this.getYHResult(yhResultId);
        YHAgree yhAgree = yhAgreeService.getYHAgree(yhResult.getAgreeId());
        TransResource transResource = transResourceService.getTransResource(yhAgree.getResourceId());
        String offerId = transResource.getOfferId();
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTimeInMillis(now.getTime());
        //添加一条出价记录
        //判断offer存在
        TransResourceOffer transResourceOffer = null;
        if(StringUtils.isNotBlank(offerId)){
            transResourceOffer = transResourceOfferService.getTransResourceOffer(transResource.getOfferId());
        }
        if(transResourceOffer != null) {
            //如果存在仅修改
            //类别同一次报价，区分普通报价
            transResourceOffer.setOfferType(-1);
            transResourceOffer.setOfferPrice(yhResult.getResultPrice());
            transResourceOffer.setOfferTime(cal.getTimeInMillis());
            transResourceOffer.setResourceId(transResource.getResourceId());
            transResourceOffer.setUserId(yhAgree.getUserId());
            transResourceOfferService.saveOrUpdateTransResourceOffer(transResourceOffer);
        }else {
            //不存在则新建
            transResourceOffer = new TransResourceOffer();
            //类别同一次报价，区分普通报价
            transResourceOffer.setOfferType(-1);
            transResourceOffer.setOfferPrice(yhResult.getResultPrice());
            transResourceOffer.setOfferTime(cal.getTimeInMillis());
            transResourceOffer.setResourceId(transResource.getResourceId());
            transResourceOffer.setUserId(yhAgree.getUserId());
            transResourceOfferService.addTransResourceOffer(transResourceOffer);
        }
        //设置地块offerId
        transResource.setOfferId(transResourceOffer.getOfferId());
        transResource.setResourceEditStatus(Constants.RESOURCE_EDIT_STATUS_OVER);
        transResource.setSuccessOfferChoose(Constants.SUCCESS_OFFER_CHOOSE.YH); // 设置成交方式
        transResource.setOverTime(cal.getTime());   //设置成交时间
        ResponseMessage<TransResource> responseMessage = transResourceService.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_CHENG_JIAO);

        //设置摇号发布
        yhResult.setYhPostTime(cal.getTime());
        yhResult.setYhPostStatus(Constants.YH_POST_STATUS_YES);
        return new ResponseMessage<YHResult>(true,saveOrUpdate(yhResult));
    }

    @Override
    @Transactional(readOnly = true)
    public YHResult getYHResult(String id) {
        return get(id);
    }

    @Override
    @Transactional(readOnly = true)
    public YHResult getYHResultByYHAgreeId(String agreeId) {
        List<Criterion> criterionList = Lists.newArrayList();
        if (StringUtils.isNotBlank(agreeId)) {
            criterionList.add(Restrictions.eq("agreeId", agreeId));
        }
        return get(criteria(criterionList));
    }

    @Override
    @Transactional(readOnly = true)
    public YHResult getYHResultByResourceId(String resourceId) {
        Map<String, Object> args = Maps.newHashMap();
        StringBuilder hql = new StringBuilder();
        hql.append("select yhr from YHResult yhr, YHAgree yha where yhr.agreeId = yha.agreeId ");
        hql.append(" and yha.resourceId =:resourceId ");
        args.put("resourceId", resourceId);
        return get(hql(hql.toString(), args));
    }
}
