package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.landsale.service.ServiceUtils;

import java.util.Calendar;

/**
 * Created by Jibo on 2015/4/24.
 */
public class ServiceUtilsImpl implements ServiceUtils {
    @Override
    public String getServerTime() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }
}
