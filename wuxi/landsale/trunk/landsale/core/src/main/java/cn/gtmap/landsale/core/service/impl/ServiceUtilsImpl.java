package cn.gtmap.landsale.core.service.impl;


import cn.gtmap.landsale.core.service.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Jibo on 2015/4/24.
 */
@Service
public class ServiceUtilsImpl implements ServiceUtils {
    @Override
    public String getServerTime() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }
}
