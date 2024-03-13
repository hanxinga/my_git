package com.sike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket desertsApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sike.controller"))//按包扫描,
                .paths(PathSelectors.any())
                .build()
                .groupName("sike")
                .enable(true);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("用户管理系统说明API文档")//标题
                .description("用户管理系统说明API文档")//描述
                .contact(new Contact("smith", "https://www.sike.com", "12345@qq.com"))//作者信息
//                .termsOfServiceUrl("https://www.sike.com")
                .version("1.0")//版本号
                .build();
    }
}

