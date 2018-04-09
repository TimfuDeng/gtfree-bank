package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.*;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by Jibo on 2015/5/16.
 */
public class ResourceUtil {

    LandUseDictSerivce landUseDictSerivce;

    TransResourceService transResourceService;

    TransCrggService transCrggService;

    TransResourceApplyService transResourceApplyService;

    TransVerifyService transVerifyService;

    TransUserUnionService transUserUnionService;

    TransResourceOfferService transResourceOfferService;

    TransBankAccountService transBankAccountService;

    TransBuyQualifiedService transBuyQualifiedService;

    public void setLandUseDictSerivce(LandUseDictSerivce landUseDictSerivce) {
        this.landUseDictSerivce = landUseDictSerivce;
    }

    public void setTransBuyQualifiedService(TransBuyQualifiedService transBuyQualifiedService) {
        this.transBuyQualifiedService = transBuyQualifiedService;
    }

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public void setTransVerifyService(TransVerifyService transVerifyService) {
        this.transVerifyService = transVerifyService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransUserUnionService(TransUserUnionService transUserUnionService) {
        this.transUserUnionService = transUserUnionService;
    }

    public void setTransCrggService(TransCrggService transCrggService) {
        this.transCrggService = transCrggService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public TransResource getResource(String resourceId){
        return transResourceService.getTransResource(resourceId);
    }

    public TransResourceApply getResourceApply(String applyId){
        return transResourceApplyService.getTransResourceApply(applyId);
    }

    public TransCrgg getCrgg(String ggId){
        return StringUtils.isNotBlank(ggId)? transCrggService.getTransCrgg(ggId):null;
    }

    public TransResourceApply limitTimeOffer(String resourceId){
        //判断是否在挂牌前1个小时
        TransResource transResource= transResourceService.getTransResource(resourceId);
        if (Calendar.getInstance().getTime().before(transResource.getGpEndTime()) &&
                (transResource.getGpEndTime().getTime()-Calendar.getInstance().getTime().getTime())<1000*60*60  ){
            //判断当前用户是否报名和交保证金
            TransResourceApply transResourceApply=
                    transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(),resourceId);
            if (transResourceApply.getApplyStep()== Constants.StepOver){
                return transResourceApply;
            }
        }
        return null;
    }

    public int getApplyCountByStauts(){
        String userId=SecUtil.getLoginUserId();
        if (StringUtils.isNotBlank(userId)) {
            Set<String> regions = Sets.newHashSet();
            Page<TransResource> transResourcePage =
                    transResourceService.findTransResourcesByUser(userId, Constants.ResourceEditStatusRelease,regions,new PageRequest(0, 50));
            return transResourcePage.getItems().size();
        }else{
            return 0;
        }
    }

    public int getIsApplyedCountByStauts(){

        String userName=SecUtil.getLoginUserViewName();
        String userId=SecUtil.getLoginUserId();
        if (StringUtils.isNotBlank(userId)) {
            Page<TransUserUnion> transUserUnionPage=
                    transUserUnionService.findTransUserUnionByUserNameIsAgree(userName, new PageRequest(0, 50));
            return transUserUnionPage.getItems().size();
        }else{
            return 0;
        }
    }

    public TransResourceVerify getTransResourceVerify(String resourceId){
        TransResource transResource= transResourceService.getTransResource(resourceId);
        return transVerifyService.getTransVerifyById(transResource.getResourceVerifyId());
    }

    /**根据applyid获取所有被联合人的出资比例
     *
     * @param applyId
     * @return
     */
    public String getAllScale(String applyId){
        List<TransUserUnion> transUnionList = null;
        double scale = 0;
        if(StringUtils.isNotBlank(applyId)) {
            transUnionList = transUserUnionService.findTransUserUnion(applyId);
            for(TransUserUnion userUnion:transUnionList){
                scale = scale + userUnion.getAmountScale();
            }
        }
        return String.valueOf(scale);
    }

    public TransBankAccount getAccountByApplyId(String applyId){
        TransBankAccount account = null;
        if(StringUtils.isNotBlank(applyId)){
            account = transBankAccountService.getTransBankAccountByApplyId(applyId);
        }else{
            account = new TransBankAccount();
        }
        return account;
    }

    /**
     * 根据resourceId获取最高报价信息
     * @param resourceId
     * @return
     */
    public TransResourceOffer getMaxOfferByresourceId(String resourceId){
        TransResourceOffer transResourceOffer = null;
        if(resourceId!=null){
            transResourceOffer= transResourceOfferService.getMaxOffer(resourceId);
            return transResourceOffer;
        }else{
            return transResourceOffer;
        }
    }

    public TransResourceVerify getResourceVerifyById(String resourceVerifyId){
        TransResourceVerify transResourceVerify = null;
        if(StringUtils.isNotBlank(resourceVerifyId)){
            transResourceVerify = transVerifyService.getTransVerifyById(resourceVerifyId);
        }
        return transResourceVerify;
    }
    public TransBuyQualified getTransBuyQualifiedById(String qualifiedById){
        TransBuyQualified transBuyQualified=null;
        if(StringUtils.isNotBlank(qualifiedById)){
            transBuyQualified=transBuyQualifiedService.getTransBuyQualifiedById(qualifiedById);
        }
        return transBuyQualified;
    }

    public List<LandUseDict> getLandUseDictList(){
       return landUseDictSerivce.getLandUseDictList();
    }

    public LandUseDict getLandUseDict(String code){
        if (StringUtils.isNotBlank(code)){
            return  landUseDictSerivce.getLandUseDict(code);
        }
        return null;
    }

}
