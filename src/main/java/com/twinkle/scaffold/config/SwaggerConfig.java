package com.twinkle.scaffold.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 模块 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_MODULE_PACKAGE = "com.twinkle.scaffold.modules.base";
    private static final String BIZ_MODULE_PACKAGE = "com.twinkle.scaffold.modules.biz";
    private static final String API_DEFAULT_PACKAGE = "api";
    
    @Bean
    public Docket moduleASwaggerApi() {
        String moduleName = "moduleA";
        String moduleDesc = "demo module";
        String basePackage = getDefaultModulePackage(moduleName,false);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo);
        return docket;
    }
    
    // 以下为基础模块配置
    @Bean
    public Docket mgmtSwaggerApi() {
        String moduleName = "mgmt";
        String moduleDesc = "服务管理";
        String basePackage = getDefaultModulePackage(moduleName,true);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo);
        return docket;
    }
    
    @Bean
    public Docket cfigSwaggerApi() {
        String moduleName = "cfig";
        String moduleDesc = "配置API";
        String basePackage = getDefaultModulePackage(moduleName,true);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo);
        return docket;
    }
    
    @Bean
    public Docket fileSwaggerApi() {
        String moduleName = "file";
        String moduleDesc = "文件管理API";
        String basePackage = getDefaultModulePackage(moduleName,true);
        ApiInfo apiInfo = createApiInfo(moduleName,moduleDesc);
        Docket docket = createDocket(moduleName,basePackage,apiInfo);
        return docket;
    }

    /**
     * 根据模块名，获取模块默认api的包名
     * */
    private String getDefaultModulePackage(String moduleName,boolean base){
        if(base){
            return BASE_MODULE_PACKAGE+"."+moduleName+"."+API_DEFAULT_PACKAGE;
        }
        return BIZ_MODULE_PACKAGE+"."+moduleName+"."+API_DEFAULT_PACKAGE;
    }
    
    
    private ApiInfo createApiInfo(String moduleName,String moduleDesc){
        ApiInfo apiInfo = new ApiInfoBuilder()
                         .title(moduleName)
                         .description(moduleDesc)
                         .build();
        return apiInfo;
    }
    
    private Docket createDocket(String moduleName,String basePackage,ApiInfo apiInfo){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                        .groupName(moduleName).apiInfo(apiInfo)
                        .genericModelSubstitutes(DeferredResult.class)
                        .useDefaultResponseMessages(false)
                        .forCodeGeneration(true)
                        .select()
                            .apis(RequestHandlerSelectors.basePackage(basePackage))
                            .paths(PathSelectors.any())
                        .build();
        return docket;
    }
}
