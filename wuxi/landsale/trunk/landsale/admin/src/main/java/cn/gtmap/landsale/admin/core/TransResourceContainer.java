package cn.gtmap.landsale.admin.core;

import cn.gtmap.landsale.admin.register.TransCrggClient;
import cn.gtmap.landsale.admin.register.TransResourceClient;
import cn.gtmap.landsale.admin.service.ResourceOperationService;
import cn.gtmap.landsale.common.Constants;
import cn.gtmap.landsale.common.model.TransCrgg;
import cn.gtmap.landsale.common.model.TransResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 地块状态监视器
 *
 * @author zsj
 * @version v1.0, 2017/12/4
 */
@Component
public class TransResourceContainer implements ApplicationRunner {

    private static Logger log = LoggerFactory.getLogger(TransResourceContainer.class);

    List<TransResourceStatusObject> resourceStatusObjectList = new CopyOnWriteArrayList<TransResourceStatusObject>();

    @Autowired
    ResourceOperationService resourceOperationService;

    @Autowired
    TransResourceClient transResourceClient;

    @Autowired
    TransCrggClient transCrggClient;

    /**
     * 查询所有发布地块 加入监视队列
     *
     * @param applicationArguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<TransResource> releaseResourceList = transResourceClient.getTransResourcesOnRelease();
        for (TransResource transResource : releaseResourceList) {
            addResource(transResource);
        }
        timer();//启动定时器
    }


    public void removeResource(String resourceId) {
        for (Iterator<TransResourceStatusObject> iter = resourceStatusObjectList.iterator(); iter.hasNext(); ) {
            TransResourceStatusObject resourceStatusObject = iter.next();
            if (resourceStatusObject.getTransResource().getResourceId().equals(resourceId)) {
                resourceStatusObjectList.remove(resourceStatusObject);
                break;
            }
        }
    }

    /**
     * 管理端修改地块状态为发布后 后 根据地块时间 修改resourceStatus
     * @param transResource
     */
    public void checkResource(TransResource transResource) {
        // 地块状态为发布后 重新加入队列
        if (transResource.getResourceEditStatus() == Constants.RESOURCE_EDIT_STATUS_RELEASE) {
            removeResource(transResource.getResourceId());
            addResource(transResource);
        } else {
            removeResource(transResource.getResourceId());
        }
    }

    public void addResourceStatus(TransResource transResource, Constants.ResourceOperateStep operateStep, Date enterTime) {
        log.info("resource add:" + operateStep + "_" + transResource.getResourceId());
        TransResourceStatusObject resourceStatusObject = new TransResourceStatusObject(transResource, operateStep, enterTime);
        resourceStatusObjectList.add(resourceStatusObject);
    }

    /**
     * 根据当前时间 判断地块下一步状态 和进入下一状态的时间 加入监视队列
     *
     * @param transResource
     */
    public void addResource(TransResource transResource) {
        Date cDate = Calendar.getInstance().getTime();
        // 根据公告编号 查找公告
        if (StringUtils.isNotBlank(transResource.getGgId())) {
            TransCrgg transCrgg = transCrggClient.getTransCrgg(transResource.getGgId());
            // 如果当前时间 小于 公告开始时间 设置地块状态为编辑中 0
            if (transCrgg != null && cDate.before(transCrgg.getGgBeginTime())) {
                if (transResource.getResourceStatus() != 0) {
                    transResourceClient.saveTransResourceStatus(transResource, 0);
                }
                // 下一步是公告
                addResourceStatus(transResource, Constants.ResourceOperateStep.GG, transCrgg.getGgBeginTime());
                return;
            }
        }
        // 如果当前时间在 公告时间内 设置当前地块状态为公告
        if (cDate.before(transResource.getGpBeginTime())) {
            if (transResource.getResourceStatus() != Constants.RESOURCE_STATUS_GONG_GAO) {
                transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_GONG_GAO);
            }
            // 下一步是挂牌
            addResourceStatus(transResource, Constants.ResourceOperateStep.GP, transResource.getGpBeginTime());
            // 如果当前时间在 挂牌时间内(包括限时开始时间之前) 设置当前地块状态为挂牌
        } else if (cDate.before(transResource.getXsBeginTime())) {
            // 判断 当前是否已达到最高限价 不是最高限价 设为挂牌
            if (Constants.RESOURCE_STATUS_MAX_OFFER != transResource.getResourceStatus()) {
                transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_GUA_PAI);
            } else {
                transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_MAX_OFFER);
            }
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(transResource.getGpEndTime());
            gc.add(Calendar.HOUR, -1);
            // 如果当前时间 > 挂牌前1小时 下一步是 限时竞价
            if (cDate.after(gc.getTime())) {
                addResourceStatus(transResource, Constants.ResourceOperateStep.XS, transResource.getXsBeginTime());
                // 如果当前时间 < 挂牌前1小时 下一步是 挂牌前1小时
            } else {
                addResourceStatus(transResource, Constants.ResourceOperateStep.GPONEHOUR, gc.getTime());
            }
            // 当前时间在 限时竞价期 修改地块状态为 限时竞价
        } else {
            // 判断 当前是否已达到最高限价 未达到限价继续监视地块
            if (Constants.RESOURCE_STATUS_MAX_OFFER != transResource.getResourceStatus()) {
                if (transResource.getResourceStatus() != Constants.RESOURCE_STATUS_JING_JIA) {
                    transResourceClient.saveTransResourceStatus(transResource, Constants.RESOURCE_STATUS_JING_JIA);
                }
                addResourceStatus(transResource, Constants.ResourceOperateStep.XS, transResource.getXsBeginTime());
            }
        }
    }

    /**
     * 定时启动
     */
    private void timer() throws InterruptedException {
        ThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        for (Iterator<TransResourceStatusObject> iter = resourceStatusObjectList.iterator(); iter.hasNext(); ) {
                            TransResourceStatusObject resourceStatusObject = iter.next();
                            if (resourceStatusObject.enterStep()) {
                                log.info("resource change:" + resourceStatusObject.operateStep + "_" + resourceStatusObject.getTransResource().getResourceId());
                                operateResource(resourceStatusObject);
                                resourceStatusObjectList.remove(resourceStatusObject);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        //1秒扫描1次
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void operateResource(TransResourceStatusObject resourceStatusObject) {
        if (resourceStatusObject.operateStep == Constants.ResourceOperateStep.GG) {
            resourceOperationService.toGG(resourceStatusObject.getTransResource());
        } else if (resourceStatusObject.operateStep == Constants.ResourceOperateStep.GP) {
            resourceOperationService.toGP(resourceStatusObject.getTransResource());
        } else if (resourceStatusObject.operateStep == Constants.ResourceOperateStep.GPONEHOUR) {
            resourceOperationService.toGPOneHour(resourceStatusObject.getTransResource());
        } else if (resourceStatusObject.operateStep == Constants.ResourceOperateStep.XS) {
            resourceOperationService.toXS(resourceStatusObject.getTransResource());
        } else if (resourceStatusObject.operateStep == Constants.ResourceOperateStep.OVER) {
            resourceOperationService.toOver(resourceStatusObject.getTransResource());
        }
    }
}
