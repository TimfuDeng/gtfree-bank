package cn.gtmap.landsale.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by trr on 2015/11/11.
 */
public class DateUtil {

    public static String Date2Str(Date date){
        String dateStr="";
        if(null!=date){
            SimpleDateFormat dateFormatyyyyMMddHHmmss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr=dateFormatyyyyMMddHHmmss.format(date);
        }
        return dateStr;
    }
    public static String Longdate2Str(long date){
        String dateStr="";
        if(date>0){
            SimpleDateFormat dateFormatyyyyMMddHHmmss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr=dateFormatyyyyMMddHHmmss.format(date);
        }
        return dateStr;
    }

}
