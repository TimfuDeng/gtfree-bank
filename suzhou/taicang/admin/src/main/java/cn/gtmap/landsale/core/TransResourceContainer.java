package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.admin.service.ResourceOperationService;
import cn.gtmap.landsale.model.TransCrgg;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.TransCrggService;
import cn.gtmap.landsale.service.TransResourceService;
import cn.gtmap.landsale.util.ThreadPool;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jibo on 2015/6/17.
 */
public class TransResourceContainer implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(TransResourceContainer.class);

    ResourceOperationService resourceOperationService;

    List<TransResourceStatusObject> resourceStatusObjectList= new CopyOnWriteArrayList<TransResourceStatusObject>();

    TransResourceService transResourceService;

    TransCrggService transCrggService;

    public void setResourceOperationService(ResourceOperationService resourceOperationService) {
        this.resourceOperationService = resourceOperationService;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransCrggService(TransCrggService transCrggService) {
        this.transCrggService = transCrggService;
    }

    public void afterPropertiesSet() throws Exception{
        List<TransResource> releaseResourceList= transResourceService.getTransResourcesOnRelease();
        for(TransResource transResource:releaseResourceList){
            addResource(transResource);
        }
        timer();//启动定时器
    }

    public void checkResource(TransResource transResource){
        if (transResource.getResourceEditStatus()== Constants.ResourceEditStatusRelease){
            removeResource(transResource.getResourceId());
            addResource(transResource);
        }else{
            removeResource(transResource.getResourceId());
        }
    }

    public void addResourceStatus(TransResource transResource,Constants.ResourceOperateStep OperateStep, Date enterTime){
        log.info("resource add:" + OperateStep + "_" + transResource.getResourceId());
        TransResourceStatusObject resourceStatusObject=new TransResourceStatusObject(transResource,OperateStep, enterTime);
        resourceStatusObjectList.add(resourceStatusObject);
    }

    public void addResource(TransResource transResource){
        Date cDate= Calendar.getInstance().getTime();
        if (StringUtils.isNotBlank(transResource.getGgId())){
            TransCrgg transCrgg= transCrggService.getTransCrgg(transResource.getGgId());
            if (transCrgg!=null && cDate.before(transCrgg.getGgBeginTime())){
                if (transResource.getResourceStatus()!= 0)
                    transResourceService.saveTransResourceStatus(transResource, 0);
                addResourceStatus(transResource,Constants.ResourceOperateStep.GG,transCrgg.getGgBeginTime());
                return;
            }
        }
        if (cDate.before(transResource.getGpBeginTime())){
            if (transResource.getResourceStatus()!= Constants.ResourceStatusGongGao)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGongGao);
            addResourceStatus(transResource,Constants.ResourceOperateStep.GP,transResource.getGpBeginTime());
        }else if(cDate.before(transResource.getGpEndTime())){
            if (transResource.getResourceStatus()!= Constants.ResourceStatusGuaPai)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusGuaPai);
            GregorianCalendar gc=new GregorianCalendar();
            gc.setTime(transResource.getGpEndTime());
            gc.add(Calendar.HOUR,-1);
            if(cDate.after(gc.getTime())){
                addResourceStatus(transResource, Constants.ResourceOperateStep.XS,transResource.getGpEndTime());
            }else{
                addResourceStatus(transResource, Constants.ResourceOperateStep.GPONEHOUR,gc.getTime());
            }
        }else{
            if (transResource.getResourceStatus()!= Constants.ResourceStatusJingJia)
                transResourceService.saveTransResourceStatus(transResource, Constants.ResourceStatusJingJia);
            addResourceStatus(transResource,Constants.ResourceOperateStep.XS,transResource.getGpEndTime());
        }

    }

    public void removeResource(String resourceId){
        for (Iterator<TransResourceStatusObject> iter = resourceStatusObjectList.iterator(); iter.hasNext();) {
            TransResourceStatusObject resourceStatusObject=iter.next();
            if(resourceStatusObject.getTransResource().getResourceId().equals(resourceId)){
                resourceStatusObjectList.remove(resourceStatusObject);
                break;
            }
        }
    }

    /**
     * 定时启动
     */
    private void timer() throws InterruptedException {
        ThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true) {
                    try {
                        for (Iterator<TransResourceStatusObject> iter = resourceStatusObjectList.iterator(); iter.hasNext(); ) {
                            TransResourceStatusObject resourceStatusObject = iter.next();
                            if (resourceStatusObject.enterStep()) {
                                log.info("resource change:" + resourceStatusObject.OperateStep + "_" + resourceStatusObject.getTransResource().getResourceId());
                                operateResource(resourceStatusObject);
                                resourceStatusObjectList.remove(resourceStatusObject);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);   //1秒扫描1次
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void operateResource(TransResourceStatusObject resourceStatusObject){
        if(resourceStatusObject.OperateStep== Constants.ResourceOperateStep.GG){
            resourceOperationService.toGG(resourceStatusObject.getTransResource());
        }else if (resourceStatusObject.OperateStep== Constants.ResourceOperateStep.GP){
            resourceOperationService.toGP(resourceStatusObject.getTransResource());
        }else if(resourceStatusObject.OperateStep== Constants.ResourceOperateStep.GPONEHOUR){
            resourceOperationService.toGPOneHour(resourceStatusObject.getTransResource());
        }else if(resourceStatusObject.OperateStep== Constants.ResourceOperateStep.XS){
            resourceOperationService.toXS(resourceStatusObject.getTransResource());
        }else if(resourceStatusObject.OperateStep== Constants.ResourceOperateStep.OVER){
            resourceOperationService.toOver(resourceStatusObject.getTransResource());
        }
    }
}
