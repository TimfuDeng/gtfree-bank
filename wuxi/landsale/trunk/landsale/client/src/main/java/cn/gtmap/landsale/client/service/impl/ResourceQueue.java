package cn.gtmap.landsale.client.service.impl;


import cn.gtmap.landsale.common.model.TransResourceOffer;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Created by Jibo on 2015/5/8.
 */
public class ResourceQueue {
    Logger logger = LoggerFactory.getLogger(ResourceQueue.class);
    private String resourceId;
    final Queue<DeferredResult<String>> realTimeQueue=new ConcurrentLinkedQueue<DeferredResult<String>>();

    public ResourceQueue(String resourceId){
        this.resourceId=resourceId;
    }

    public void addClient(final DeferredResult<String> clientReuslt){
        //当DeferredResult对客户端响应后将其从列表中移除
        clientReuslt.onCompletion(new Runnable() {
            @Override
            public void run() {
                realTimeQueue.remove(clientReuslt);
            }
        });
        clientReuslt.onTimeout(new Runnable() {
            @Override
            public void run() {
                Map<String,Object> resultMap= Maps.newHashMap();
                resultMap.put("time", Calendar.getInstance().getTime().getTime());
                resultMap.put("result", null);
                clientReuslt.setResult(JSON.toJSONString(resultMap));
            }
        });
        realTimeQueue.add(clientReuslt);
    }

    public void writeClient(TransResourceOffer transResourceOffer){
        List<TransResourceOffer> resourceOfferList= Lists.newArrayList();
        resourceOfferList.add(transResourceOffer);
        Map<String,Object> resultMap= Maps.newHashMap();
        resultMap.put("time", Calendar.getInstance().getTime().getTime());
        resultMap.put("result", resourceOfferList);
        for (DeferredResult<String> client : this.realTimeQueue) {
            client.setResult(JSON.toJSONString(resultMap));
        }
    }

}
