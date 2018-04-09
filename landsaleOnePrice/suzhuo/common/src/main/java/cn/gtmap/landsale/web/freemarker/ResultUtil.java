package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by trr on 2016/7/12.
 */
public class ResultUtil {

    TransNoticeService transNoticeService;

    TransNoticeTargetRelService transNoticeTargetRelService;

    TransTargetService transTargetService;

    TransTargetGoodsRelService transTargetGoodsRelService;

    TransGoodsService transGoodsService;

    SysExtendService sysExtendService;

    TransBidderService transBidderService;

    TransLicenseService transLicenseService;

    TransOfferLogService transOfferLogService;

    TransTargetMultiTradeService transTargetMultiTradeService;

    OneTargetService oneTargetService;

    public void setOneTargetService(OneTargetService oneTargetService) {
        this.oneTargetService = oneTargetService;
    }

    public void setSysExtendService(SysExtendService sysExtendService) {
        this.sysExtendService = sysExtendService;
    }

    public void setTransTargetGoodsRelService(TransTargetGoodsRelService transTargetGoodsRelService) {
        this.transTargetGoodsRelService = transTargetGoodsRelService;
    }

    public void setTransGoodsService(TransGoodsService transGoodsService) {
        this.transGoodsService = transGoodsService;
    }

    public void setTransNoticeTargetRelService(TransNoticeTargetRelService transNoticeTargetRelService) {
        this.transNoticeTargetRelService = transNoticeTargetRelService;
    }

    public void setTransNoticeService(TransNoticeService transNoticeService) {
        this.transNoticeService = transNoticeService;
    }

    public void setTransTargetMultiTradeService(TransTargetMultiTradeService transTargetMultiTradeService) {
        this.transTargetMultiTradeService = transTargetMultiTradeService;
    }

    public void setTransOfferLogService(TransOfferLogService transOfferLogService) {
        this.transOfferLogService = transOfferLogService;
    }

    public void setTransTargetService(TransTargetService transTargetService) {
        this.transTargetService = transTargetService;
    }

    public void setTransBidderService(TransBidderService transBidderService) {
        this.transBidderService = transBidderService;
    }

    public void setTransLicenseService(TransLicenseService transLicenseService) {
        this.transLicenseService = transLicenseService;
    }

    public TransTarget getTransTarget(String id){
        TransTarget transTarget=null;
        if(StringUtils.isNotBlank(id)){
            transTarget=transTargetService.getTransTarget(id);
        }
        return transTarget;

    }

    public TransBidder getTransBidder(String id){
        TransBidder transBidder=null;
        if(StringUtils.isNotBlank(id)){
            transBidder=transBidderService.getTransBidder(id);
        }
        return transBidder;
    }

    public Integer getOfferLogCount(String targetId){
        List<TransLicense> transLicenseList= transLicenseService.findTransLicenseListByNo(null, targetId);
        List<String> licenseIds= Lists.newArrayList();
        if(null!=transLicenseList&&transLicenseList.size()>0){
            for(int i=0;i<transLicenseList.size();i++){
                licenseIds.add(transLicenseList.get(i).getId());
            }
        }
        List<TransOfferLog> transOfferLogList=transOfferLogService.getOfferLogListByLicenseId(licenseIds);
        return transOfferLogList.size();
    }

    public TransTargetMultiTrade getTransTargetMultiTrade(String targetId){
        List<TransTargetMultiTrade> transTargetMultiTradeList=transTargetMultiTradeService.getTransTargetMultiTradeList(targetId);
        if(transTargetMultiTradeList.size()>0){
            return transTargetMultiTradeList.get(0);
        }else {
            return null;
        }
    }

    public TransLicense getTransLicense(String targetId){
        TransLicense transLicense=null;
        if(StringUtils.isNotBlank(targetId)){
            List<TransLicense> transLicenseList= transLicenseService.findTransLicenseListById(targetId);
            transLicense=transLicenseList.get(0);
        }
        return transLicense;
    }

    /**
     * 通过标的Id得到公告
     * @param targetId
     * @return
     */
    public TransNotice getTransNotice(String targetId){
        TransNoticeTargetRel transNoticeTargetRel=null;
        TransNotice transNotice=null;
        if (StringUtils.isNotBlank(targetId))
         transNoticeTargetRel = transNoticeTargetRelService.findTransNoticeTargetRelBytargetId(targetId);
        if (null!=transNoticeTargetRel)
         transNotice = transNoticeService.getTransNotice(transNoticeTargetRel.getNoticeId());
        return transNotice;

    }

    /**
     * 通过标的id得到地块
     * @param targetId
     * @return
     */
    public TransGoods getTransGoods(String targetId){
        List<TransTargetGoodsRel> transTargetGoodsRelList= transTargetGoodsRelService.findTransTargetGoodsRelTargetId(targetId);
        TransGoods goods = new TransGoods();
        goods.setArea2Extend(0.00);
        for (TransTargetGoodsRel transTargetGoodsRel:transTargetGoodsRelList){
            TransGoods transGoods=transGoodsService.getTransGoods(transTargetGoodsRel.getGoodsId());
            if (null!=transGoods){
                goods.setLandUse2Extend(transGoods.getGoodsUse() + ";");
                goods.setAddress2Extend(transGoods.getAddress() + ";");
                SysExtend sysExtend= sysExtendService.findSysExtend(transGoods.getId(), "build_area");
                if (null!=sysExtend&&null!=sysExtend.getFieldValue()){
                    goods.setArea2Extend(goods.getArea2Extend()+Double.parseDouble(sysExtend.getFieldValue()));
                }
            }



        }
        return goods;
    }

    public OneTarget getOneTargetByTransTargetId(String id){
        if (StringUtils.isNotBlank(id))
            return oneTargetService.getOneTargetByTransTarget(id);
        return null;
    }

    public OneTarget getOneTargetById(String id){
        if(StringUtils.isNotBlank(id)){
           return oneTargetService.getOneTarget(id);
        }
        return null;
    }

    public boolean isCurrentUser(String userId){
        return StringUtils.isBlank(SecUtil.getLoginUserId())?false: SecUtil.getLoginUserId().equals(userId);
    }

}
