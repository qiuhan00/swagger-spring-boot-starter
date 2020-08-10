package com.cfang.swagger.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cfang.swagger.properties.SwaggerProperties;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description：
 * @author cfang 2020年8月6日
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger",name = "enable")
@EnableSwagger2
@Slf4j
public class SwaggerConfiguration {

	@Autowired
	SwaggerProperties properties;
	
	@Bean
	public Docket createRestApi() {
		log.info("==> 开启swagger starter");
		return new Docket(DocumentationType.SWAGGER_2)
//				.securitySchemes(securitySchemes())
//				.securityContexts(securityContexts())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage())) 
                .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();
	}
	
	private ApiInfo apiInfo() {
       return new ApiInfoBuilder()
                   .title(properties.getTitle()) //设置文档的标题
                   .description(properties.getDescription()) // 设置文档的描述
                   .version(properties.getVersion()) // 设置文档的版本信息-> 1.0.0 Version information
                   .termsOfServiceUrl("javascript:void(0)")
                   .build();
	}
	
	private List<ApiKey> securitySchemes() {
        return new ArrayList<ApiKey>() {{
        	new ApiKey("Authorization", "Authorization", "header");
        }};
    }
	
	private List<SecurityContext> securityContexts() {
        return new ArrayList(){{
        	SecurityContext.builder()
        	.securityReferences(defaultAuth())
        	.forPaths(PathSelectors.regex("^(?!auth).*$"))
        	.build();
        }};
    }
	
	List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(){{
        	new SecurityReference("Authorization", authorizationScopes);
        }};
    }
}
