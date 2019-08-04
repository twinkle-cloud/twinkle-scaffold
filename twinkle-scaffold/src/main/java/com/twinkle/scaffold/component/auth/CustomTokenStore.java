package com.twinkle.scaffold.component.auth;

import javax.sql.DataSource;

import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 上午10:31:43 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class CustomTokenStore extends JdbcTokenStore{

    /**
     * @param dataSource
     */
    public CustomTokenStore(DataSource dataSource) {
        super(dataSource);
    }

}
