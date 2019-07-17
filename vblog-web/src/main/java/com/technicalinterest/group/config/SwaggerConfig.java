package com.technicalinterest.group.config;


import com.technicalinterest.group.constant.SwaggerConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
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
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
//        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
//            @Override
//            public boolean apply(RequestHandler input) {
//                Class<?> declaringClass = input.declaringClass();
//                if (declaringClass == BasicErrorController.class)// 排除
//                {
//                    return false;
//                }
//                if (declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
//                {
//                    return true;
//                }
//                if (input.isAnnotatedWith(ResponseBody.class)) // 被注解的方法
//                {
//                    return true;
//                }
//                return false;
//            }
//        };
//        ParameterBuilder param = new ParameterBuilder();
//        param.parameterType("header").name(UrlConstant.ACCESS_TOKEN_STRING).defaultValue("accessToken").description("user token").modelRef(new ModelRef("string")).required(false).build();
//        List<Parameter> parameters = new ArrayList<>();
//        parameters.add(param.build());
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).
//                useDefaultResponseMessages(false).select().apis(predicate).build().globalOperationParameters(parameters);
//
//

        Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2).enableUrlTemplating(true)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ccom.technicalinterest.group.api.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
        return swaggerSpringMvcPlugin;

    }

    @SuppressWarnings("deprecation")
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title(SwaggerConstant.TITLE_STRING)//大标题
//                .description(SwaggerConstant.DESCRIPTION_STRING).termsOfServiceUrl(SwaggerConstant.SERVICE_URL_STRING).contact(SwaggerConstant.CONTACT_STRING).version(SwaggerConstant.VERSION_STRING)//版本
//                .build();
//    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("账务还款服务接口文档",
                "账务还款服务接口",
                "",
                "http://fin-repayment.mljr.com",
                new Contact("fin-repayment", "", ""),
                "",
                "", new ArrayList<>());
        return apiInfo;
    }


}
