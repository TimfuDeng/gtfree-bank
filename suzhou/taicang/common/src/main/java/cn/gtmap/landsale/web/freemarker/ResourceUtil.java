package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.model.TransResourceApply;
import cn.gtmap.landsale.security.SecUtil;
import cn.gtmap.landsale.service.TransCrggService;
import cn.gtmap.landsale.service.TransResourceApplyService;
import cn.gtmap.landsale.service.TransResourceService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by Jibo on 2015/5/16.
 */
public class ResourceUtil {

    TransResourceService transResourceService;

    TransCrggService transCrggService;

    TransResourceApplyService transResourceApplyService;


    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }


    public void setTransCrggService(TransCrggService transCrggService) {
        this.transCrggService = transCrggService;
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
        try {
            String userId=SecUtil.getLoginUserId();
            if (StringUtils.isNotBlank(userId)) {
                Set<String> regions = Sets.newHashSet();
                Page<TransResource> transResourcePage =
                        transResourceService.findTransResourcesByUser(userId, Constants.ResourceEditStatusRelease,regions,new PageRequest(0, 50));
                return transResourcePage.getItems().size();
            }else{
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
