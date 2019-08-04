package com.twinkle.scaffold.client.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.common.data.email.SimpleAttachment;
import com.twinkle.scaffold.common.data.email.SimpleEmail;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO ADD DESC <br/>
 * Date: 2019年8月4日 上午11:29:45 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Slf4j
public class EmailClient {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送简单邮件
     * @param simpleEmail 简单邮件
     * */
    public void sendSimpleEMail(SimpleEmail simpleEmail) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(simpleEmail.getFrom());
            helper.setTo(String.join(",",simpleEmail.getTo()));
            helper.setBcc(String.join(",",simpleEmail.getBcc()));
            helper.setSubject(simpleEmail.getSubject());
            if(Boolean.TRUE.equals(simpleEmail.getHtml())){
                helper.setText(simpleEmail.getText(), true);
            }else{
                helper.setText(simpleEmail.getText());
            }
            if(CollectionUtils.isNotEmpty(simpleEmail.getAttachments())){
                for (SimpleAttachment simpleAttachment : simpleEmail.getAttachments()) {
                    helper.addAttachment(simpleAttachment.getName(),simpleAttachment.getInputStreamSource(),simpleAttachment.getContentType());
                }
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(),e);
        }
    }
}
