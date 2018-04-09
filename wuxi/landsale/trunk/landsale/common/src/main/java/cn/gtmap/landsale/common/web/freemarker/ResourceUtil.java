package cn.gtmap.landsale.common.web.freemarker;

import cn.gtmap.landsale.common.model.*;
import cn.gtmap.landsale.common.register.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * FreeMarker 使用工具类
 * @author zsj
 * @version 1.0
 */
@Component
public class ResourceUtil {

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransCrggClient transCrggClient;

    @Autowired
    TransResourceApplyClient transResourceApplyClient;

    @Autowired
    LandUseDictClient landUseDictClient;

    @Autowired
    TransBuyQualifiedClient transBuyQualifiedClient;

    @Autowired
    TransResourceVerifyClient transResourceVerifyClient;

    @Autowired
    TransResourceOfferClient transResourceOfferClient;

    @Autowired
    YHClient yhClient;

//    TransResourceApplyService transResourceApplyService;
//
//    PartnerService partnerService;

    public TransResource getResource(String resourceId){
        return transResourceClient.getTransResource(resourceId);
    }

//    public TransResourceApply getResourceApply(String applyId){
//        return transResourceApplyClient.getTransResourceApply(applyId);
//    }

    public TransCrgg getCrgg(String ggId){
        return StringUtils.isNotBlank(ggId)? transCrggClient.getTransCrgg(ggId):null;
    }

    public int getTransResourceApply(String resourceId,int applyStep){
        if(StringUtils.isNotBlank(resourceId)){
            if(null!= transResourceApplyClient.getTransResourceApplyStep(resourceId, applyStep)) {
                return transResourceApplyClient.getTransResourceApplyStep(resourceId, applyStep).size();
            }

        }
        return 0;
    }


    public List<Tree> getLandUseDictList (){
        Object jsonNodes=null;
        List<LandUseDict> landUseDictList = landUseDictClient.getLandUseDictList();
        return landUseDictList2Node(landUseDictList);

    }

    public List<Tree> landUseDictList2Node(List<LandUseDict> landUseDictList){
        List<Tree> treeList = new ArrayList();
        for(LandUseDict landUseDict : landUseDictList){
            Tree tree = new Tree();
            tree.setId(landUseDict.getCode());
            tree.setpId(landUseDict.getParent());
            tree.setName(landUseDict.getName());
            treeList.add(tree);
        }
//        for(int i=0;i<landUseDictList.size();i++){
//            LandUseDict landUseDict = landUseDictList.get(i);
//            Map node = new TreeMap();
//            node.put("id",landUseDict.getCode());
//            node.put("pId",landUseDict.getParent());
//
//            node.put("name",landUseDict.getName());
//           /* if(region[0].length()==4){
//                node.put("open",false);
//            }*/
//            nodes.add(node);
//        }
//        return nodes;
            return treeList;
    }
    public TransResourceApply getTransResourceApplyByUserId(String userId, String resourceId) {
        return transResourceApplyClient.getTransResourceApplyByUserId(userId, resourceId);
    }

    public TransResourceApply getResourceApply(String applyId){
        return transResourceApplyClient.getTransResourceApply(applyId);
    }

    public TransResourceVerify getTransResourceVerify(String resourceId) {
        if (StringUtils.isNotBlank(resourceId)) {
            return transResourceVerifyClient.getTransVerifyLastByResourceId(resourceId);
        }
        return null;
    }

    public TransBuyQualified getTransBuyQualifiedById(String applyId){
        TransBuyQualified transBuyQualified=null;
        if(StringUtils.isNotBlank(applyId)){
            transBuyQualified=transBuyQualifiedClient.getTransBuyQualifiedForCurrent(applyId);
        }
        return transBuyQualified;
    }




//    public TransResourceApply limitTimeOffer(String resourceId){
//        //判断是否在挂牌前1个小时
//        TransResource transResource= transResourceService.getTransResource(resourceId);
//        if (Calendar.getInstance().getTime().before(transResource.getGpEndTime()) &&
//                (transResource.getGpEndTime().getTime()-Calendar.getInstance().getTime().getTime())<1000*60*60  ){
//            //判断当前用户是否报名和交保证金
//            TransResourceApply transResourceApply=
//                    transResourceApplyService.getTransResourceApplyByUserId(SecUtil.getLoginUserId(),resourceId);
//            if (transResourceApply.getApplyStep()== Constants.STEP_OVER){
//                return transResourceApply;
//            }
//        }
//        return null;
//    }
//
//    public int getApplyCountByStauts(){
//        String userId=SecUtil.getLoginUserId();
//        if (StringUtils.isNotBlank(userId)) {
//            Set<String> regions = Sets.newHashSet();
//            Page<TransResource> transResourcePage =
//                    transResourceService.findTransResourcesByUser(userId, Constants.RESOURCE_EDIT_STATUS_RELEASE,regions,new PageRequest(0, 50));
//            return transResourcePage.getItems().size();
//        }else{
//            return 0;
//        }
//    }
//
//    public List<Partner> getPartnerListByApplyId(String applyId){
//        List<Partner> partners = Lists.newArrayList();
//        if(StringUtils.isNotBlank(applyId)){
//            partners = partnerService.getPartnerList(applyId);
//        }
//        return partners;
//    }

    public boolean isYhResultExist(String resourceId) {
        if(yhClient.getYHResultByResourceId(resourceId) != null) {
            return true;
        }else {
            return false;
        }
    }

    public String getOfferUserId(String resourceOfferId) {
        if(StringUtils.isBlank(resourceOfferId)){
            return null;
        }
        TransResourceOffer transResourceOffer = transResourceOfferClient.getTransResourceOffer(resourceOfferId);
        return transResourceOffer.getUserId();
    }

}
