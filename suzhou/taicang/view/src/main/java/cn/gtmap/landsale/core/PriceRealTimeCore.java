package cn.gtmap.landsale.core;

import cn.gtmap.egovplat.core.data.Page;
import cn.gtmap.egovplat.core.data.PageRequest;
import cn.gtmap.landsale.model.TransResourceOffer;
import cn.gtmap.landsale.service.TransResourceOfferService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jiff on 14/12/13.
 */

@Deprecated
public class PriceRealTimeCore {

    //所有的客户端长连接请求
    List<ClientRequestResult> clientRequestResultList= Lists.newArrayList();

    TransResourceOfferService transResourceOfferService;

    public void setTransResourceOfferService(TransResourceOfferService transResourceOfferService) {
        this.transResourceOfferService = transResourceOfferService;

    }



//    public boolean writeNewPrice(Page<TransResourceOffer> transResourceOfferPage,ClientRequestResult clientRequestResult){
//
//    }
}
