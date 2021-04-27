package com.infervision.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午6:16
 * @version:1.0 使用Swagger2
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Resource
    SwaggerProperties swaggerProperties;

    /** 
     * 注入docket
     * @return: Docket 
     * @author: fruiqi
     * @date: 19-2-18 下午6:20
     */ 
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePacakge()))
                .paths(PathSelectors.any())
                .build();
    }

    /** 
     * 创建api 
     * @return: springfox.documentation.service.ApiInfo 
     * @author: fruiqi
     * @date: 19-2-18 下午6:20
     */ 
    private ApiInfo createApiInfo(){
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }


}
