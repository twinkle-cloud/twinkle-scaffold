package com.twinkle.scaffold.modules.biz.moduleA.comfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twinkle.scaffold.config.AbstractSwaggerConfig;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 模块A 配置
 */
@Configuration
@EnableSwagger2
public class ModuleASwaggerConfig  extends AbstractSwaggerConfig{

    @Bean
    public Docket moduleASwaggerApi() {
        String moduleName = "moduleA";
        String moduleDesc = "demo module";
        String basePackage = getDefaultModulePackage(moduleName,false);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo,false);
        return docket;
    }
    
}
