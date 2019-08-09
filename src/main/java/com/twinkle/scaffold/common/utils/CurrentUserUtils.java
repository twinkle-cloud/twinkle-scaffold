package com.twinkle.scaffold.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.error.GeneralException;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午12:04:03 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Slf4j
public class CurrentUserUtils {

    /**
     * 获取当前用户
     * 仅适用于受限资源访问，可以使用
     * */
    public static String getCurrentUserName(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            return userDetails.getUsername();
        } catch (Exception e) {
            throw new GeneralException(ResultCode.NO_CURRENT_USER,"当前用户不存在");
        }
    }
    
    /**
     * 获取当前用户的所使用的accessToken
     * 仅适用于受限资源访问，可以使用
     * */
    public static String getCurrentAccessToken(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails)authentication.getDetails();
            return authenticationDetails.getTokenValue();
        } catch (Exception e) {
            throw new GeneralException(ResultCode.NO_CURRENT_USER,"当前用户不存在");
        }
    }
}
