package com.twinkle.scaffold.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月28日 下午10:36:34 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Configurable
public class WebMVCConfig implements WebMvcConfigurer{

    /**
     * 配置API接口的跨域访问
     * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }
    
}
