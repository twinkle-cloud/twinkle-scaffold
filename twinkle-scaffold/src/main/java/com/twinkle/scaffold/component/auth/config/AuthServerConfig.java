package com.twinkle.scaffold.component.auth.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.twinkle.scaffold.component.auth.CustomTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private ClientDetailsService customClientDetailsService;
	@Autowired
    private UserDetailsService customUserDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(customClientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    /*
	     * 设置 reuseRefreshTokens(false)
	     * 重复使用：这种模式下的refresh token的过期仍然是以初次生成的时间为准
	     * 非重复使用： 即当access token 更新的时候，同时更新refresh token，这样就把refresh token的过期时间一直往后延续。
	     * 该模式的用意就是通过更新refresh token实现永远不需要再次登陆的目的。除非前后两次对项目的操作时间间隔超出了refresh token的有效时间段。
	     * */ 
	    endpoints.authenticationManager(authenticationManager)
		        .userDetailsService(customUserDetailsService)
				.tokenStore(tokenStore).reuseRefreshTokens(false);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
            // 开启/oauth/token_key验证端口无权限访问
            .tokenKeyAccess("permitAll()")
            // 开启/oauth/check_token验证端口认证权限访问
            .checkTokenAccess("isAuthenticated()");
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder(6);
    }
	
    @Bean
    public TokenStore tokenStore(DataSource dataSource) {
        return new CustomTokenStore(dataSource);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(Collections.singletonList(provider));
    }
}