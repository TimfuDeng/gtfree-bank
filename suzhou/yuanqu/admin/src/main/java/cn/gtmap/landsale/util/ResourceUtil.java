package cn.gtmap.landsale.util;

import cn.gtmap.landsale.Constants;
import cn.gtmap.landsale.model.TransResource;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jiff on 15/5/5.
 */
public class ResourceUtil {


    static int beginHour=9;
    static int endHour=17;

    public  static TransResource buildNewResource(){
        Date cDate=Calendar.getInstance().getTime();
        Date beginDate=   getDate(cDate,beginHour,0);
        Date endDate=  getDate(addDate(cDate, 9),endHour,0);
        TransResource transResource=new TransResource();
        transResource.setBmBeginTime(beginDate);
        transResource.setBmEndTime(addDate(endDate, -2));
        transResource.setGpBeginTime(beginDate);
        transResource.setGpEndTime(endDate);
        transResource.setXsBeginTime(endDate);
        transResource.setYxEndTime(addDate(endDate,2));
        transResource.setBzjBeginTime(beginDate);
        transResource.setBzjEndTime(addDate(endDate, -2));
        transResource.setResourceStatus(Constants.ResourceStatusGongGao);
        transResource.setResourceEditStatus(Constants.ResourceEditStatusInput);
        transResource.setMinOffer(false);
        return transResource;
    }

    public static Date getDate(Date date,int hour,int min){
        try {
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String dateStr = df1.format(date) + " " + hour + ":" + min;
            return df2.parse(dateStr);
        }catch (Exception ex){
            return Calendar.getInstance().getTime();
        }
    }

    public static Date addDate(Date date,int days){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DAY_OF_MONTH,days);
        return gc.getTime();
    }

    public static Date addDateMin(Date date,int min){
        GregorianCalendar gc=new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.MINUTE,min);
        return gc.getTime();
    }
    public static String double2Str(Double doubleObj){
        if(null==doubleObj){
            return "";
        }
        return doubleObj+"";
    }
    public static Object obj2Str(Object obj){
        if(null==obj){
           return "";
        }
        return obj;
    }
}
