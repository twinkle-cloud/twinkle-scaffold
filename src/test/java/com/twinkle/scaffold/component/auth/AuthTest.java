package com.twinkle.scaffold.component.auth;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.twinkle.scaffold.AbstractTest;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月2日 下午10:21:56 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class AuthTest extends AbstractTest{

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void passwordEncoderTest(){
        System.out.println(passwordEncoder.encode("secret"));
        System.out.println(passwordEncoder.encode("12345"));
    }
}
