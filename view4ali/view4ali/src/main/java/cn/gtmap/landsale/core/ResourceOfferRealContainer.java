package cn.gtmap.landsale.core;

import org.springframework.beans.factory.InitializingBean;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JIBO on 2016/9/23.
 */
public class ResourceOfferRealContainer implements InitializingBean {

    Map<String,ResourceOfferReal> resourceOfferRealMap=new ConcurrentHashMap<String,ResourceOfferReal>();

    public ResourceOfferReal getResourceOfferReal(String resourceId){
        return resourceOfferRealMap.get(resourceId);
    }

    public void putResourceOfferReal(String resourceId,ResourceOfferReal resourceOfferReal){
        resourceOfferRealMap.put(resourceId,resourceOfferReal);
    }

    public void afterPropertiesSet() throws Exception {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        fixedThreadPool.execute(new Runnable() {
            public void run() {
                while (true) {
                    try{
                        Thread.sleep(1000 * 60 *30);   //每隔30分钟清理一次
                        long cTime= Calendar.getInstance().getTimeInMillis();
                        Iterator iter = resourceOfferRealMap.keySet().iterator();
                        while (iter.hasNext()) {
                            Object key =  (String) iter.next();
                            ResourceOfferReal val = resourceOfferRealMap.get(key);
                            if ((cTime-val.getPutTime())>1000*60*60){
                                resourceOfferRealMap.remove(key);
                            }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            }
        });

    }
}
