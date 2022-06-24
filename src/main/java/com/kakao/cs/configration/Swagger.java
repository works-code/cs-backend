package com.kakao.cs.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@EnableSwagger2
@Configuration
public class Swagger {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kakao.cs"))
                .paths(PathSelectors.any()) // 모든 path에 대해 규격서를 만든다.
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "고객 문의 접수 및 답변 기능 API",
                "고객 문의 접수 및 답변 기능이 있는 API 입니다.",
                "API V1",
                "",
                new Contact("HONGYOOLEE", "https://co-de.tistory.com/", "im.yoolee@gmail.com"),
                "ⓒHONGYOOLEE CORP ALL RIGHTS RESERVED.", "license URL", Collections.emptyList());
    }
}
