package cn.gtmap.landsale.web.freemarker;

import cn.gtmap.landsale.model.*;
import cn.gtmap.landsale.service.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by trr on 2016/7/12.
 */
public class ResultUtil {

    OneTargetService oneTargetService;
    TransCrggService transCrggService;
    TransUserService transUserService;

    public void setTransUserService(TransUserService transUserService) {
        this.transUserService = transUserService;
    }

    public void setTransCrggService(TransCrggService transCrggService) {
        this.transCrggService = transCrggService;
    }

    public void setOneTargetService(OneTargetService oneTargetService) {
        this.oneTargetService = oneTargetService;
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

    public TransCrgg getTransCrgg(String id){
        if(StringUtils.isNotBlank(id)){
           return transCrggService.getTransCrgg(id);
        }
        return null;
    }

    public TransUser getTransUser(String id){
        if(StringUtils.isNotBlank(id)){
            return transUserService.getTransUser(id);
        }
        return null;
    }

}
