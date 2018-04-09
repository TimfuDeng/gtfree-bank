package cn.gtmap.landsale.admin.service;

import java.util.Collection;

/**
 * 电子邮件服务
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/10
 */
public interface EmailMessageService {

    /**
     * 邮件发送
     * @param addressee 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void send(String addressee,String subject,String content);

    /**
     * 邮件发送
     * @param addressee 多个收件人
     * @param subject 主题
     * @param content 内容
     */
    public void send(Collection<String> addressee,String subject,String content);
}
