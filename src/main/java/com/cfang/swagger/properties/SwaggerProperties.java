package com.cfang.swagger.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @description：
 * @author cfang 2020年8月6日
 */
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {

	private Boolean enable;
	private String version;
	private String title;
	private String description;
	private String basePackage;
}
