package cn.gtmap.landsale.core;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import cn.gtmap.landsale.service.*;
import cn.gtmap.landsale.util.ThreadPool;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 交易地块的守护进程，用来检查正在交易地块和线程
 *
 * Created by jiff on 15/5/5.
 */
@Deprecated
public class TransResourceTimer implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(TransResourceTimer.class);

    //线程池
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    //所有待交易资源
    Map<String,TransResourceTask> resourceTaskHashMap = Maps.newHashMap();

    TransResourceService transResourceService;

    TransResourceOfferService transResourceOfferService;

    TransResourceApplyService transResourceApplyService;

    TransBankAccountService transBankAccountService;

    TransCrggService transCrggService;

    TransResourceMinPriceService transResourceMinPriceService;

    final static int IntervalTime=4*1000;   //循环执行时间

    public void setTransResourceMinPriceService(TransResourceMinPriceService transResourceMinPriceService) {
        this.transResourceMinPriceService = transResourceMinPriceService;
    }

    public void setTransResourceApplyService(TransResourceApplyService transResourceApplyService) {
        this.transResourceApplyService = transResourceApplyService;
    }

    public void setTransBankAccountService(TransBankAccountService transBankAccountService) {
        this.transBankAccountService = transBankAccountService;
    }

    public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public void setTransResourceService(TransResourceService transResourceService) {
        this.transResourceService = transResourceService;
    }

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;
    }

    public void setTransCrggService(TransCrggService transCrggService) {
        this.transCrggService = transCrggService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(IntervalTime);
                    List<TransResource> releaseResourceList= transResourceService.getTransResourcesOnRelease();
                    for(TransResource transResource:releaseResourceList){
                        addResource(transResource);
                    }
                } catch (Exception ex) {
                }
            }
        });
    }

    /**
     * 添加资源
     * @param transResource
     */
    public void addResource(TransResource transResource){
        if (resourceTaskHashMap.get(transResource.getResourceId())==null) {
            log.debug("+++++++资源线程启动:{}",transResource.getResourceId());
            TransResourceTask resourceTask = new TransResourceTask(transResource,transResourceService,
                    transResourceOfferService,transResourceApplyService,transBankAccountService,transCrggService,
                    transResourceMinPriceService);
            threadPoolTaskExecutor.execute(resourceTask);
            resourceTaskHashMap.put(transResource.getResourceId(), resourceTask);
        }
    }

    /**
     * 删除资源
     * @param transResource
     */
    public void removeResource(TransResource transResource){
        if (resourceTaskHashMap.get(transResource.getResourceId())!=null) {
            log.debug("------资源线程关闭:{}",transResource.getResourceId());
            TransResourceTask resourceTask =resourceTaskHashMap.get(transResource.getResourceId());
            Thread resourceThread= getThread(transResource.getResourceId());
            if (resourceThread!=null) {
                resourceTask.stop = true;
                resourceThread.interrupt();
            }
            resourceTaskHashMap.remove(transResource.getResourceId());
        }

    }

    public void checkResource(TransResource transResource){
        if (resourceTaskHashMap.get(transResource.getResourceId())!=null) {
            if (transResource.getResourceEditStatus()== Constants.ResourceEditStatusRelease){
                removeResource(transResource);
                addResource(transResource);
            }else{
                removeResource(transResource);
            }
        }else{
            if (transResource.getResourceEditStatus()== Constants.ResourceEditStatusRelease){
                addResource(transResource);
            }
        }
    }

    public Thread getThread(String resourceId){
        ThreadGroup group = threadPoolTaskExecutor.getThreadGroup();
        Thread[] threads = new Thread[group.activeCount()];
        group.enumerate(threads);
        for (int i=0; i < threads.length; ++i) {
           if (threads[i].getName().equals(resourceId)){
               return  threads[i];
           }
        }
        return null;
    }

}
