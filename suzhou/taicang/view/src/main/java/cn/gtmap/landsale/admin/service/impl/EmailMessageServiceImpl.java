package cn.gtmap.landsale.admin.service.impl;

import cn.gtmap.egovplat.core.ex.AppException;
import cn.gtmap.landsale.admin.service.EmailMessageService;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Collection;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/7/10
 */
public class EmailMessageServiceImpl implements EmailMessageService {
    private JavaMailSender javaMailSender;
    private String sender;

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * 邮件发送
     *
     * @param addressee 收件人
     * @param subject   主题
     * @param content   内容
     */
    @Override
    public void send(String addressee, String subject, String content) {
        if(StringUtils.isBlank(addressee))
            throw new AppException(7101);
        send(Sets.newHashSet(addressee),subject,content);
    }

    /**
     * 邮件发送
     *
     * @param addressee 多个收件人
     * @param subject   主题
     * @param content   内容
     */
    @Override
    public void send(Collection<String> addressee, String subject, String content) {
        if(addressee.isEmpty())
            throw new AppException(7101);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(addressee.toArray(new String[addressee.size()]));
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setFrom(sender);
        javaMailSender.send(mailMessage);

    }
}
