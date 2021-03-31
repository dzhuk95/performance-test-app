package com.performance;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableSwagger2
public class ApiSwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(GET, globalResponseMessages())
                .globalResponseMessage(POST, globalResponseMessages())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.performance"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<ResponseMessage> globalResponseMessages() {
        return Arrays.asList(
                errorApiResponse(400, "Bad Request"),
                errorApiResponse(404, "Not Found"),
                errorApiResponse(500, "Internal Server Error")
        );
    }

    private ResponseMessage errorApiResponse(int code, String message) {
        return new ResponseMessageBuilder()
                .code(code)
                .message(message)
                .build();
    }
}