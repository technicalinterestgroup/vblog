package com.technicalinterest.group.config;

import com.technicalinterest.group.constant.SwaggerConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Path;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @package:com.ganinfo.common.configuration
 * @className:SwaggerConfig
 * @description:SwaggerConfig配置
 * @author:Shuyu.Wang
 * @date:2018-11-30 18:13
 * @version:V1.0
 **/
@Configuration
@ComponentScan("com.technicalinterest.group")
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2).enableUrlTemplating(true).apiInfo(apiInfo())
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.technicalinterest.group"))
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build();
		return swaggerSpringMvcPlugin;

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("开源系统接口文档", "开源系统服务接口", "", "", new Contact("vblog", "", ""), "", "", new ArrayList<>());
		return apiInfo;
	}

}
