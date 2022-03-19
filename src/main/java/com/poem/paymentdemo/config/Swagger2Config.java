package com.poem.paymentdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2     //Swagger2文件的注解
public class Swagger2Config {
    @Bean
    public Docket docket(){ //Swagger2中的文档对象
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder().title("微信支付项目文档").build());  //docket类型由构造函数中的参数进行定义
    }
}
