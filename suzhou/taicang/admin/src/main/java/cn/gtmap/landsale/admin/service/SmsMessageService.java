package cn.gtmap.landsale.admin.service;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Jibo on 2015/6/12.
 */
public interface SmsMessageService {

    /**
     * 发送短信
     * @param body
     * @param mobiles
     */
    public void send(String body, String mobiles) throws Exception;
}
