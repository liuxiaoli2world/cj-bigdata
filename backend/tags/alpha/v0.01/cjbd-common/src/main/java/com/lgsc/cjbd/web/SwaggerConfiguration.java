package com.lgsc.cjbd.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@SuppressWarnings("unchecked")
	@Bean
    public Docket demoApi() {
		return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .paths(or(regex("/book/.*"), regex("/expert/.*"), regex("/order/.*"), regex("/user/.*")))
                .build();
    }

	private ApiInfo demoApiInfo() {
		return new ApiInfoBuilder()
	            .title("Electronic Health Record(EHR) Platform API")//大标题
	            .description("EHR Platform's REST API, all the applications could access the Object model data via JSON.")//详细描述
	            .version("1.0")//版本
	            .termsOfServiceUrl("NO terms of service")
//	            .contact(new Contact("叶权进", "http://www.dcrays.cn/", "ms9527@163.com"))//作者
	            .license("The Apache License, Version 2.0")
	            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
	            .build();
    }

}
