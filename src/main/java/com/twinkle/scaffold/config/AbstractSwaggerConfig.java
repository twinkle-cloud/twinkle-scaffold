package com.twinkle.scaffold.config;

import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger 配置 抽象类 <br/>
 * 基础模块命名： base:xxx <br/>
 * Date:    2019年9月15日 下午7:35:54 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public abstract class AbstractSwaggerConfig {

    private static final String BASE_MODULE_PACKAGE = "com.twinkle.scaffold.modules.base";
    private static final String BIZ_MODULE_PACKAGE = "com.twinkle.scaffold.modules.biz";
    private static final String API_DEFAULT_PACKAGE = "api";
    
    /**
     * 根据模块名，获取模块默认api的包名
     * */
    protected String getDefaultModulePackage(String moduleName,boolean base){
        if(base){
            return BASE_MODULE_PACKAGE+"."+moduleName+"."+API_DEFAULT_PACKAGE;
        }
        return BIZ_MODULE_PACKAGE+"."+moduleName+"."+API_DEFAULT_PACKAGE;
    }
    
    
    protected ApiInfo createApiInfo(String moduleName,String moduleDesc){
        ApiInfo apiInfo = new ApiInfoBuilder()
                         .title(moduleName)
                         .description(moduleDesc)
                         .build();
        return apiInfo;
    }
    
    protected Docket createDocket(String moduleName,String basePackage,ApiInfo apiInfo,boolean base){
        if(base){
            moduleName = "base:"+moduleName; 
        }
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
