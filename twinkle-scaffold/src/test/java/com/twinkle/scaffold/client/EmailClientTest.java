package com.twinkle.scaffold.client;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.twinkle.scaffold.AbstractTest;
import com.twinkle.scaffold.client.email.EmailClient;
import com.twinkle.scaffold.common.data.email.SimpleEmail;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月4日 上午11:35:28 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class EmailClientTest extends AbstractTest{

    @Autowired
    private EmailClient emailClient;
    
    @Test
    public void sendSimpleMailTest(){
        SimpleEmail simpleEmail = new SimpleEmail();
        simpleEmail.setFrom("yk163001@126.com");
        simpleEmail.setTo(Arrays.asList("1129013140@qq.com"));
        simpleEmail.setBcc(Arrays.asList("1129013140@qq.com"));
        simpleEmail.setSubject("主题");
        simpleEmail.setText("<h1>大标题-h1</h1>",Boolean.FALSE);
        emailClient.sendSimpleEMail(simpleEmail);
    }
}
