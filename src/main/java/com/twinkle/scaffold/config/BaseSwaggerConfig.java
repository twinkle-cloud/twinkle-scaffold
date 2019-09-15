package com.twinkle.scaffold.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 基础模块 配置
 */
@Configuration
@EnableSwagger2
public class BaseSwaggerConfig extends AbstractSwaggerConfig {

    // 以下为基础模块配置
    @Bean
    public Docket cfigSwaggerApi() {
        String moduleName = "cfig";
        String moduleDesc = "配置API";
        String basePackage = getDefaultModulePackage(moduleName,true);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo,true);
        return docket;
    }
    
    @Bean
    public Docket fileSwaggerApi() {
        String moduleName = "file";
        String moduleDesc = "文件管理API";
        String basePackage = getDefaultModulePackage(moduleName,true);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo,true);
        return docket;
    }

}
