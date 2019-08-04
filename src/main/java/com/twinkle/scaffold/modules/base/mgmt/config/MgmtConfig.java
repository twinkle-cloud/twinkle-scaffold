package com.twinkle.scaffold.modules.base.mgmt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 模块 配置
 */
@Configuration
public class MgmtConfig {

    @Bean
    public Docket mgmtSwaggerApi() {
        String moduleName = "mgmt";
        String moduleDesc = "服务管理";
        String basePackage = "com.twinkle.scaffold.modules.base.mgmt.api";
        ApiInfo apiInfo = new ApiInfoBuilder().title(moduleName).description(moduleDesc)
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName(moduleName).apiInfo(apiInfo)
                .genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
                .select().apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any()).build();
        return docket;
    }

}
