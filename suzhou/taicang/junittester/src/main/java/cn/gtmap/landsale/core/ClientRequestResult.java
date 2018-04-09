package cn.gtmap.landsale.core;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Date;

/**
 * Created by jiff on 15/5/6.
 */
public class ClientRequestResult {

    Date clientTime;

    DeferredResult<String> clientReuslt;  //客户端对象

    String resourceId;


    public ClientRequestResult(String resourceId,DeferredResult<String> clientReuslt){
        this.resourceId=resourceId;
        this.clientReuslt=clientReuslt;
    }

    public Date getClientTime() {
        return clientTime;
    }

    public void setClientTime(Date clientTime) {
        this.clientTime = clientTime;
    }

    public DeferredResult<String> getClientReuslt() {
        return clientReuslt;
    }

    public void setClientReuslt(DeferredResult<String> clientReuslt) {
        this.clientReuslt = clientReuslt;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
