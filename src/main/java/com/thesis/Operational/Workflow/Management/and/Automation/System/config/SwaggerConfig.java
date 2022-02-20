package com.thesis.Operational.Workflow.Management.and.Automation.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Configuration
@Import({ BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class })
@EnableSwagger2
public class SwaggerConfig{

    @NotNull
    private List<SecurityScheme> securitySchema() {
        return Collections.singletonList(
                OAuth2Scheme.OAUTH2_PASSWORD_FLOW_BUILDER
                        .name("Credentials")
                        .tokenUrl("/api/auth/login-swagger-method-not-seen-endpoint")
                        .scopes(Collections.singletonList(new AuthorizationScope("Default", "Default scope.")))
                        .build()
        );
    }

    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .operationSelector(
                        o -> PathSelectors
                                .ant("/api/auth/*")
                                .negate()
                                .test(o.requestMappingPattern())
                )
                .securityReferences(Collections.singletonList(new SecurityReference("Credentials", new AuthorizationScope[0])))
                .build();
    }

    /**
     * Creates the Swagger configuration bean.
     *
     * @return docket bean
     */
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.OAS_30).groupName("DELTA").select()
                .apis(RequestHandlerSelectors.basePackage("com.thesis.Operational.Workflow.Management.and.Automation.System.controllers"))
                .paths(PathSelectors.any()).build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(securitySchema())
                .apiInfo(apiInfo("Final Project API",
                        "This api adheres to the RESTfull properties and all entities can be accessed with pagination and sorting. Full table is not sent, only pages. "
                                + "For more info, search pagination on google.\n" + "\n"
                                + "## There are 2 kinds of items in this document, Entity and Controller. Entity items document the direct access to an entity for CRUD operations, while controllers provide custom endpoints.\n"
                                + "\n" + "## EDITING AN ENTRY\n"
                                + "For editing, use PATCH commands and give only the thin you edit. Put replaces the whole object, while patch only updates it with the given attributes.\n"
                                + "\n" + "## REFERENCING OBJECTS\n"
                                + "For example, while assigning a company to user, send a POST request to the /user/id/company address with the content type \"text/uri-list\" and body \"../api/companies/id\"."));
    }

    @Bean
    public UiConfiguration uiConfig() {

        return UiConfigurationBuilder.builder().operationsSorter(OperationsSorter.METHOD).build();
    }

    /**
     * Creates an object containing API information including author name,
     * email, version, license, etc.
     *
     * @param title API title
     * @param description API description
     * @return API information
     */
    private ApiInfo apiInfo(String title, String description) {

        return new ApiInfoBuilder().title(title).description(description).version("1.0.0-Snapshot").build();
    }

}