package cn.gtmap.landsale.util;

import cn.gtmap.landsale.model.TransResourceOffer;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jiff on 14/12/21.
 */
public class OfferListUtil {

    public static String convetToJson(TransResourceOffer offer){
        List<HashMap<String,String>> transResourceOfferList= Lists.newArrayList();
        HashMap<String,String> mapValue=new  HashMap();
        mapValue.put("time",String.valueOf(offer.getOfferTime()));
        mapValue.put("value",offer.getOfferPrice().toString());
        transResourceOfferList.add(mapValue);
        return JSON.toJSONString(transResourceOfferList);
    }

    public static void main(String[] params){
        System.out.println("asdf达到大是大非");
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(new Long("1431676800000"));
        System.out.println(cal.getTime());
    }
}
