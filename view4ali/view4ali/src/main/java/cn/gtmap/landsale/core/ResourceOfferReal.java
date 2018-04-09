package cn.gtmap.landsale.core;

import cn.gtmap.landsale.model.TransResourceOffer;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by JIBO on 2016/9/23.
 */
public class ResourceOfferReal implements Serializable {
    static  int LIST_COUNT=20;
    long putTime;
    AtomicInteger offerCount=new AtomicInteger();
    List<TransResourceOffer> transResourceOfferList;

    public ResourceOfferReal(List<TransResourceOffer> offers, int count){
        putTime= Calendar.getInstance().getTimeInMillis();
        offerCount.set(count);
        transResourceOfferList=new CopyOnWriteArrayList<TransResourceOffer>();
        if (offers!=null && offers.size()>0){
            transResourceOfferList.addAll(offers);
        }
    }

    public void add(TransResourceOffer offer){
        offerCount.incrementAndGet();
        transResourceOfferList.add(0,offer);
        putTime=offer.getOfferTime();
        if (transResourceOfferList.size()>20){
            transResourceOfferList.remove(20);
        }
    }

    public int offerCount(){
        return offerCount.get();
    }

    public List<TransResourceOffer> getTransResourceOfferList() {
        return transResourceOfferList;
    }

    public long getPutTime() {
        return putTime;
    }

}
