package cn.gtmap.landsale.admin.service;

import cn.gtmap.egovplat.core.bean.Attrable;

import java.util.Map;

/**
 * 通知定时器服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/9
 */
public interface NotifyTimerService {
    /**
     * 短信通知的方法。
     * @return 返回的Map中key为手机号，value为短信内容
     */
    public Map<String,String> smsNotify();

    /**
     * 邮件通知方法
     * @return 返回Attrable对象，key分别为addressee，subject和content等
     */
    public Attrable emailNotify();
}
